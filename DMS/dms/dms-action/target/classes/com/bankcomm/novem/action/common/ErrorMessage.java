package com.bankcomm.novem.action.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Data;
import lombok.extern.log4j.Log4j;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-4-14
 * 
 */
@Data
@Log4j
public class ErrorMessage {
	private static String ip = "";
	static {
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (final UnknownHostException e) {
			log.error("获取localhost失败", e);
		}
	}

	/** 错误信息 */
	private final String message;
	/** 错误头 */
	private final String title;

	/**
	 * @param title
	 *            {@link #title}
	 * @param message
	 *            {@link #message}
	 */
	public ErrorMessage(final String title, final String message) {
		super();
		this.message = "[" + ip + "]" + message;
		this.title = title;
	}
}