package com.open.cloud.mqclient.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-29-上午10:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {

    private Long id;

    @ApiModelProperty(value = "消息内容,以 JSON 数据存储")
    private String message;

    @ApiModelProperty(value = "队列名称")
    private String queue;

    @ApiModelProperty(value = "发送消息的系统")
    private String sendSystem;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    @ApiModelProperty(value = "状态. 0: 等待消费; 1: 已消费; 2: 已死亡")
    private Integer status;

    @ApiModelProperty(value = "死亡次数条件, 由使用方决定, 默认为发送10 次还没被消费则标记死亡, 人工介入")
    private Integer sendCount;

    @ApiModelProperty(value = "消费时间")
    private Date customerTime;

    @ApiModelProperty(value = "消费系统")
    private String customerSystem;

    @ApiModelProperty(value = "死亡次数")
    private Integer dieCount;

    @ApiModelProperty(value = "死亡时间")
    private Date dieTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最近一次更新时间")
    private Date updateTime;
}
