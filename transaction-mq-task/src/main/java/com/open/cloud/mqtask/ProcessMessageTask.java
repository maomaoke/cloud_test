package com.open.cloud.mqtask;

import com.open.cloud.mqclient.TransactionMqRemoteClient;
import com.open.cloud.mqclient.pojo.dto.MessageDTO;
import com.open.cloud.mqclient.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@Service
public class ProcessMessageTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMessageTask.class);
	
	@Autowired
	private TransactionMqRemoteClient transactionMqRemoteClient;
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private RedissonClient redisson;
	
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
	
	private Semaphore semaphore = new Semaphore(20);
	
	public void start() {
		Thread th = new Thread(() -> {
			while(true) {
				final RLock lock = redisson.getLock("transaction-mq-task");
				lock.lock();
				try {
					log.info("开始发送消息:{}", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
					int sleepTime = process();
					if (sleepTime > 0) {
						Thread.sleep(10000);
					}
				} catch (Exception e) {
					LOGGER.error("", e);
				} finally {
					lock.unlock();
				}
			}
		});
		th.start();
	}
	
	private int process() throws Exception {
		int sleepTime = 10000;	//默认执行完之后等等10秒
		List<MessageDTO> messageList = transactionMqRemoteClient.findListByWaitingMessage(100);
		if (messageList.size() == 5000) {
			sleepTime = 0;
		}
		final CountDownLatch latch = new CountDownLatch(messageList.size());
		for (final MessageDTO message : messageList) {
			semaphore.acquire();
			fixedThreadPool.execute(() -> {
				try {
					doProcess(message);
				} catch (Exception e) {
					LOGGER.error("", e);
				} finally {
					semaphore.release();
					latch.countDown();
				}
			});
		}
		latch.await();
		return sleepTime;
	}
	
	private void doProcess(MessageDTO message) {
		//检查此消息是否满足死亡条件
		if (message.getSendCount() > message.getDieCount()) {
			transactionMqRemoteClient.confirmDieMessage(message.getId());
			return;
		}
		
		//距离上次发送时间超过一分钟才继续发送
		long currentTime = System.currentTimeMillis();
		long sendTime = 0;
		if (message.getSendTime() != null) {
			sendTime = message.getSendTime().getTime();
		}
		if (currentTime - sendTime > 60000) {
			System.out.println("发送具体消息：" + message.getId());
			
			//向MQ发送消息
			MessageMqDTO messageDto = new MessageMqDTO();
			messageDto.setMessageId(message.getId());
			messageDto.setMessage(message.getMessage());
			producer.send(message.getQueue(), JsonUtils.obj2Json(messageDto));
			
			//修改消息发送次数以及最近发送时间
			transactionMqRemoteClient.incrSendCount(message.getId(), new Date());
		}
	}
}
