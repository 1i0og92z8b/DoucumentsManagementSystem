/**
 * 
 */
package com.bankcomm.novem.bo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 杨晓俊<Yang_ahstu007@163.com> 砾阳 2011-4-13上午09:27:01
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorMessageLogBo extends BaseBo {
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
