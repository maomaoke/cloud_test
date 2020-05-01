package com.open.cloud.mqserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.open.cloud.mqclient.pojo.param.MessageParam;
import com.open.cloud.mqclient.enums.MessageStatusEnum;
import com.open.cloud.mqserver.mapper.MessageMapper;
import com.open.cloud.mqclient.pojo.dto.MessageDTO;
import com.open.cloud.mqserver.pojo.entity.Message;
import com.open.cloud.mqserver.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-29-上午9:19
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public boolean sendMessage(MessageParam param) {

        Message message = new Message();
        BeanUtils.copyProperties(param, message);
        int result = messageMapper.insert(message);
        return result > 0;
    }

    @Override
    public boolean sendMessage(List<MessageParam> params) {
        if (CollectionUtils.isEmpty(params)) {
            return false;
        }
        List<Message> messageList = params.stream().map(param -> {
            Message message = new Message();
            BeanUtils.copyProperties(param, message);
            message.setUpdateTime(new Date());
            return message;
        }).collect(Collectors.toList());
        int result = messageMapper.batchInsert(messageList);

        return result == params.size();
    }

    @Override
    public boolean confirmCustomerMessage(Long messageId, String customerSystem) {
        Message message = messageMapper.selectById(messageId);
        if (Objects.isNull(message)) {
            return false;
        }
        message.setCustomerTime(new Date());
        message.setStatus(MessageStatusEnum.OVER.getStatus());
        message.setCustomerSystem(customerSystem);
        message.setUpdateTime(new Date());
        int result = messageMapper.updateById(message);
        return result > 0;
    }

    @Override
    public List<MessageDTO> findListByWaitingMessage(Integer limit) {

        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", MessageStatusEnum.WAITING.getStatus());

        IPage<Message> page = new Page<>(0, limit);

        IPage<Message> selectPage = messageMapper.selectPage(page, queryWrapper);
        return selectPage.getRecords().stream().map(this::assembleMessageDTO).collect(Collectors.toList());
    }

    @Override
    public boolean confirmDieMessage(Long messageId) {
        Message message = messageMapper.selectById(messageId);
        if (Objects.isNull(message)) {
            return false;
        }
        message.setStatus(MessageStatusEnum.DIE.getStatus());
        message.setDieTime(new Date());
        message.setUpdateTime(new Date());
        int result = messageMapper.updateById(message);
        return result > 0;
    }

    @Override
    public boolean incrSendCount(Long messageId, Date sendTime) {
        Message message = messageMapper.selectById(messageId);
        if (Objects.isNull(message)) {
            return false;
        }

        Date lastUpdateTime = message.getUpdateTime();
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("update_time", lastUpdateTime);
        queryWrapper.eq("id", messageId);

        message.setSendTime(sendTime);
        message.setSendCount(message.getSendCount() + 1);
        message.setUpdateTime(new Date());

        int result = messageMapper.update(message, queryWrapper);

        return result > 0;
    }

    @Override
    public boolean retrySendDieMessage() {

        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", MessageStatusEnum.DIE.getStatus());

        Message message = new Message();
        message.setStatus(MessageStatusEnum.WAITING.getStatus());
        message.setUpdateTime(new Date());
        message.setSendCount(0);
        messageMapper.update(message, queryWrapper);
        return true;
    }

    @Override
    public List<MessageDTO> pageMessage(Integer pageNum, Integer pageSize, MessageStatusEnum status) {
        IPage<Message> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(status)) {
            queryWrapper.eq("status", status.getStatus());
        }
        IPage<Message> selectPage = messageMapper.selectPage(page, queryWrapper);

        return selectPage.getRecords().stream().map(this::assembleMessageDTO).collect(Collectors.toList());
    }

    private MessageDTO assembleMessageDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        BeanUtils.copyProperties(message, dto);
        return dto;
    }
}
