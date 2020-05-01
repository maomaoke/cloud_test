package com.open.cloud.mqclient;

import com.open.cloud.mqclient.enums.MessageStatusEnum;
import com.open.cloud.mqclient.pojo.dto.MessageDTO;
import com.open.cloud.mqclient.pojo.param.MessageParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-29-下午2:23
 */
@FeignClient(value = "transaction-mq-server", path = "/message")
public interface TransactionMqRemoteClient {

    /**
     * 发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
     * @param param
     * @return
     */
    @PostMapping("/send")
    boolean sendMessage(@RequestBody MessageParam param);

    /**
     * 批量发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
     * @param params
     * @return
     */
    @PostMapping("/sends")
    boolean sendMessage(@RequestBody List<MessageParam> params);

    /**
     * 确认消息被消费
     * @param messageId
     * @param customerSystem
     * @return
     */
    @PostMapping("/confirm/customer/{messageId}")
    boolean confirmCustomerMessage(@PathVariable("messageId") Long messageId,
                                          @RequestParam("customerSystem") String customerSystem);

    /**
     * 查询最早没有被消费的消息
     * @param limit
     * @return
     */
    @GetMapping("/waiting")
    List<MessageDTO> findListByWaitingMessage(@RequestParam("limit") Integer limit);

    /**
     * 确认消息死亡
     * @param messageId
     * @return
     */
    @PostMapping("/confirm/die/{messageId}")
    boolean confirmDieMessage(@PathVariable("messageId") Long messageId);

    /**
     * 累加发送次数
     * @param messageId
     * @param sendTime
     * @return
     */
    @PostMapping("/incr-send-count/{messageId}")
    boolean incrSendCount(@PathVariable("messageId") Long messageId,
                                 @RequestParam("sendDate")
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date sendTime);

    /**
     * 重新发送当前已死亡的消息
     * @return
     */
    @PostMapping("/send/retry")
    boolean retrySendDieMessage();

    /**
     * 分页查询具体状态的消息
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/{pageNum}/{pageSize}")
    public List<MessageDTO> pageMessage(@PathVariable("pageNum") Integer pageNum,
                                        @PathVariable("pageSize") Integer pageSize,
                                        @RequestParam(value = "status", required = false) MessageStatusEnum status);
}
