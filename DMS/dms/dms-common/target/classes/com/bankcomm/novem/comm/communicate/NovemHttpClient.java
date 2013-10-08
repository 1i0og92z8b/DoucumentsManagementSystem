package com.bankcomm.novem.comm.communicate;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 May 11, 2012
 * 
 */
@Slf4j
public final class NovemHttpClient {
	/**  */
	public static final String ERROR_MESSAGE = "error!";
	/**  */
	public static final String SUCCESS_MESSAGE = "success";

	/**
	 * @param url
	 *            http访问地址
	 * @return 是否成功
	 */
	public static String receiveMsg(final String url) {
		// 构造HttpClient的实例
		final HttpClient httpClient = new HttpClient();
		final HttpState state = httpClient.getState();
		state.clearCookies();
		// 创建GET方法的实例
		final GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		getMethod
				.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2");
		getMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		try {
			// 执行getMethod
			final int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + getMethod.getStatusLine());
			}
			// 读取内容
			final byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			log.info(new String(responseBody));
			return SUCCESS_MESSAGE;
		} catch (final HttpException e) {
			log.error(e.getMessage(), e);
			return ERROR_MESSAGE;
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
			return ERROR_MESSAGE;
		} finally {
			getMethod.releaseConnection();
		}
	}

	/**  */
	private NovemHttpClient() {
		super();
	}
}