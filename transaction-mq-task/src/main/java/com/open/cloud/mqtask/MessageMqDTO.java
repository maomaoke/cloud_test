package com.open.cloud.mqtask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-29-下午2:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageMqDTO {

    private Long messageId;

    private String message;
}
