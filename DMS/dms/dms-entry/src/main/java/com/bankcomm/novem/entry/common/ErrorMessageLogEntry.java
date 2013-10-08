/**
 * 
 */
package com.bankcomm.novem.entry.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.EntryPk;

/**
 * @author 杨晓俊<Yang_ahstu007@163.com> 砾阳 2011-4-12上午11:00:26 错误记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorMessageLogEntry extends BaseEntry {
	/**
	 * 出错的类名
	 */
	private String className;
	/**
	 * 错误代码
	 */
	private String errorCode;
	/**
	 * 错误上下文
	 */
	private String errorContext;
	/**
	 * 错误记录
	 */
	@EntryPk
	private long errorId;
	/**
	 * 错误消息
	 */
	private String errorMessage;

	/**
	 * 出错的方法名
	 */
	private String methodName;

	/**
	 * 操作者id
	 */
	private long userId;
	/**
	 * 操作者名
	 */
	private String userName;
}
