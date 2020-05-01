package com.open.cloud.mqclient.pojo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-29-上午9:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageParam {

    @NotBlank
    @ApiModelProperty(value = "消息内容,以 JSON 数据存储")
    private String message;

    @NotBlank
    @ApiModelProperty(value = "队列名称")
    private String queue;

    @NotBlank
    @ApiModelProperty(value = "发送消息的系统")
    private String sendSystem;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
