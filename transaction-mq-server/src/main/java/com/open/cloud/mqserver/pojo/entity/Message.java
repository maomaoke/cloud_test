package com.open.cloud.mqserver.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen-ke-chao
 * @since 2019-08-28
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_message")
@ApiModel(value="Message对象", description="")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "消息内容,以 JSON 数据存储")
    @TableField("message")
    private String message;

    @ApiModelProperty(value = "队列名称")
    @TableField("queue")
    private String queue;

    @ApiModelProperty(value = "发送消息的系统")
    @TableField("send_system")
    private String sendSystem;

    @ApiModelProperty(value = "发送时间")
    @TableField("send_time")
    private Date sendTime;

    @ApiModelProperty(value = "状态. 0: 等待消费; 1: 已消费; 2: 已死亡")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "死亡次数条件, 由使用方决定, 默认为发送10 次还没被消费则标记死亡, 人工介入")
    @TableField("send_count")
    private Integer sendCount;

    @ApiModelProperty(value = "消费时间")
    @TableField("customer_time")
    private Date customerTime;

    @ApiModelProperty(value = "消费系统")
    @TableField("customer_system")
    private String customerSystem;

    @ApiModelProperty(value = "死亡次数")
    @TableField("die_count")
    private Integer dieCount;

    @ApiModelProperty(value = "死亡时间")
    @TableField("die_time")
    private Date dieTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "最近一次更新时间")
    @TableField("update_time")
    private Date updateTime;

}
