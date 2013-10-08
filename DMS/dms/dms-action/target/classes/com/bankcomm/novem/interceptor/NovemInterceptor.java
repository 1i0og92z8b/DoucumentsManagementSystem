package com.bankcomm.novem.interceptor;

import static com.bankcomm.novem.bo.exception.ErrorType.RISK_UNKNOW_ERROR;

import java.io.UnsupportedEncodingException;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bankcomm.novem.action.common.ErrorMessage;
import com.bankcomm.novem.biz.common.IErrorMessageLogBiz;
import com.bankcomm.novem.biz.common.IPromptMessageTranslationBiz;
import com.bankcomm.novem.bo.common.ErrorMessageLogBo;
import com.bankcomm.novem.bo.common.PromptMessageTranslationBo;
import com.bankcomm.novem.bo.exception.ErrorType;
import com.bankcomm.novem.bo.exception.RiskRuntimeException;
import com.bankcomm.novem.bo.user.NovemUser;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.core.CoreRuntimeException;
import com.bocom.jump.bp.core.Interceptor;

/**
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳软件 2010-12-27
 * 
 */
@Log4j
public class NovemInterceptor implements Interceptor {
	private static final String DEFAULT_LANGUAGE = "chinese";
	@Autowired
	private IErrorMessageLogBiz errorLogBiz;
	@Autowired
	private IPromptMessageTranslationBiz tranBiz;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.jump.bp.core.Interceptor#onRequest(com.bocom.jump.bp.core.Context
	 * )
	 */
	@Override
	public void onRequest(final Context context) {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.jump.bp.core.Interceptor#onResponse(com.bocom.jump.bp.core.
	 * Context, java.lang.Throwable)
	 */
	@Override
	public void onResponse(final Context context, final Throwable throwable) {
		if (throwable == null) {
			return;
		}

		final Throwable cause = throwable.getCause();
		if (cause == null) {
			final ErrorMessage error = assembleDefaultMessage(throwable);
			throw new CoreRuntimeException(error.getTitle(), error.getMessage());
		}

		log.debug(cause.getMessage());
		final boolean isBizException = cause instanceof RiskRuntimeException;
		if (!isBizException) {
			final ErrorMessage error = assembleDefaultMessage(cause);
			throw new CoreRuntimeException(error.getTitle(), error.getMessage());
		}

		final RiskRuntimeException riskException = (RiskRuntimeException) cause;
		if (riskException.getError() == null) {
			final ErrorMessage error = assembleDefaultMessage(riskException);
			throw new CoreRuntimeException(error.getTitle(), error.getMessage());
		}

		final PromptMessageTranslationBo translateMessage = tranBiz
				.queryPromptMsgTranByCode(riskException.getError().name(),
						DEFAULT_LANGUAGE);
		final ErrorMessage error = assembleNormalError(riskException,
				translateMessage);
		final long saveErrorLog = saveErrorLog(context, cause, translateMessage);
		throw new CoreRuntimeException(error.getTitle(), "[" + saveErrorLog
				+ "]" + error.getMessage());
	}

	/**
	 * @param ex
	 * @return
	 */
	private ErrorMessage assembleDefaultMessage(final Throwable ex) {
		final PromptMessageTranslationBo translateMessage = tranBiz
				.queryPromptMsgTranByCode(RISK_UNKNOW_ERROR.name(),
						DEFAULT_LANGUAGE);
		final String errorTitle = assembleErrorTitle(RISK_UNKNOW_ERROR,
				translateMessage);

		return new ErrorMessage(errorTitle, ex.getMessage());
	}

	/**
	 * @param error
	 * @param translateMessage
	 * @return
	 */
	private String assembleErrorTitle(final ErrorType error,
			final PromptMessageTranslationBo translateMessage) {
		if (translateMessage == null) {
			return error.name();
		}
		return translateMessage.getSystemCode()
				+ translateMessage.getModelCode() + translateMessage.getsNo();
	}

	/**
	 * @param ex
	 * @return
	 */
	private ErrorMessage assembleNormalError(final RiskRuntimeException ex,
			final PromptMessageTranslationBo translateMessage) {
		Assert.notNull(ex);
		Assert.notNull(ex.getError());

		final StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append(ex.getError().name());
		messageBuffer.append("：");
		if ((translateMessage != null)
				&& (translateMessage.getPromptMessageName() != null)) {
			messageBuffer.append(translateMessage.getPromptMessageName());
		}
		if (ex.getMessage() != null) {
			messageBuffer.append("——>");
			messageBuffer.append(ex.getMessage());
		}

		final String message = StringUtils.removeEnd(messageBuffer.toString(),
				"|");
		final String errorTitle = assembleErrorTitle(ex.getError(),
				translateMessage);
		return new ErrorMessage(errorTitle, message);
	}

	/**
	 * 控制字符串的长度
	 * 
	 * @param str
	 *            字符串
	 * @param length
	 *            长度
	 * @return 控制后的字符串
	 */
	private String controlStrLength(final String str, final int length) {
		try {
			final byte[] bytes = str.getBytes("utf-8");
			if (bytes.length > length) {
				return new String(ArrayUtils.subarray(bytes, 0, length), "utf8");
			}
			return str;
		} catch (final UnsupportedEncodingException e) {
			return str.substring(0, 50);
		}
	}

	/**
	 * @param errorElements
	 * @return
	 */
	private String extractDefaultErrorContext(
			final StackTraceElement[] errorElements) {
		final StringBuffer contextStr = new StringBuffer();
		for (final StackTraceElement e : errorElements) {
			if (!e.getFileName().endsWith("Action.java")) {
				contextStr.append(e.toString()).append(",");
			} else {
				break;
			}
		}
		return contextStr.substring(0, contextStr.length() - 1);
	}

	/**
	 * @param errorElements
	 * @return
	 */
	private StackTraceElement extractTraceElement(
			final StackTraceElement[] errorElements) {
		StackTraceElement element = null;
		for (final StackTraceElement e : errorElements) {
			if ((e == null) || (e.getFileName() == null)) {
				continue;
			}
			if (e.getFileName().endsWith("Action.java")) {
				element = e;
				break;
			}
		}
		return element;
	}

	/**
	 * @param context
	 * @param cause
	 * @param translateMessage
	 * @return
	 */
	private long saveErrorLog(final Context context, final Throwable cause,
			final PromptMessageTranslationBo translateMessage) {
		final StackTraceElement[] errorElements = cause.getStackTrace();
		final StackTraceElement element = extractTraceElement(errorElements);
		if (element == null) {
			return -1;
		}
		final ErrorMessageLogBo errorLogBo = new ErrorMessageLogBo();

		final NovemUser novemUser = context.getUser();
		if (novemUser != null) {
			errorLogBo.setUserName(novemUser.getUserName());
			errorLogBo.setUserId(novemUser.getUId());
		}

		errorLogBo.setClassName(element.getFileName());
		errorLogBo.setMethodName(element.getMethodName());
		errorLogBo.setErrorContext(controlStrLength(context.getDataMap()
				.toString(), 1000));

		final boolean isBizException = cause instanceof RiskRuntimeException;
		if (isBizException) {
			errorLogBo.setErrorMessage(translateMessage.getPromptMessageName());
			errorLogBo.setErrorCode(translateMessage.getPromptMessageCode());
			return errorLogBiz.save(errorLogBo);
		}

		final StringBuffer contextStrTemp = new StringBuffer();
		contextStrTemp.append(errorLogBo.getErrorContext()).append("{")
				.append(extractDefaultErrorContext(errorElements)).append("}");
		errorLogBo.setErrorContext(controlStrLength(contextStrTemp.toString(),
				1000));
		errorLogBo.setErrorCode(RISK_UNKNOW_ERROR.name());
		errorLogBo.setErrorMessage(controlStrLength(cause.getMessage(), 256));
		return errorLogBiz.save(errorLogBo);

	}
}
