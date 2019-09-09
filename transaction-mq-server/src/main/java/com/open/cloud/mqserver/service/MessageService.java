package com.open.cloud.mqserver.service;

import com.open.cloud.mqclient.pojo.param.MessageParam;
import com.open.cloud.mqclient.enums.MessageStatusEnum;
import com.open.cloud.mqclient.pojo.dto.MessageDTO;

import java.util.Date;
import java.util.List;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-29-上午9:18
 */
public interface MessageService {

    boolean sendMessage(MessageParam param);

    boolean sendMessage(List<MessageParam> params);

    boolean confirmCustomerMessage(Long messageId, String customerSystem);

    List<MessageDTO> findListByWaitingMessage(Integer limit);

    boolean confirmDieMessage(Long messageId);

    boolean incrSendCount(Long messageId, Date sendTime);

    boolean retrySendDieMessage();

    List<MessageDTO> pageMessage(Integer pageNum, Integer pageSize, MessageStatusEnum status);
}
