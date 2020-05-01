package com.open.cloud.mqclient.enums;

/**
 * 消息状态枚举类
 * @author yinjihuan
 *
 */
public enum MessageStatusEnum {
	/**
	 * 等待消费
	 */
	WAITING(0),
	
	/**
	 * 已消费
	 */
	OVER(1),
	
	/**
	 * 死亡
	 */
	DIE(2);
	
	private int status;
	
	public static MessageStatusEnum parse(int status) {
		for (MessageStatusEnum stat : values()) {
			if (stat.getStatus() == status) {
				return stat;
			}
		}
		return null;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private MessageStatusEnum(int status) {
		this.status = status;
	}
}