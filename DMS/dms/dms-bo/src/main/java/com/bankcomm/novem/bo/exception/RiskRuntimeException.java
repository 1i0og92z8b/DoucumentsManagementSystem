package com.bankcomm.novem.bo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳软件 2010-12-29
 * 
 * 
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RiskRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 2688032168051922679L;

	private ErrorType error;

	private String message;

	/**
	 * 这个接口不要使用，使用带错误类型的，涉及到错误码的处理
	 */
	@Deprecated()
	public RiskRuntimeException() {
		super();
	}

	/**
	 * @param error
	 *            错误码
	 */
	public RiskRuntimeException(final ErrorType error) {
		this.error = error;
	}

	/**
	 * @param error
	 *            错误码
	 * @param message
	 *            错误信息
	 */
	public RiskRuntimeException(final ErrorType error, final String message) {
		this.error = error;
		this.message = message;
	}

	/**
	 * @param error
	 *            错误码
	 * @param cause
	 *            异常
	 */
	public RiskRuntimeException(final ErrorType error, final Throwable cause) {
		super(cause);
		this.error = error;
	}
}
