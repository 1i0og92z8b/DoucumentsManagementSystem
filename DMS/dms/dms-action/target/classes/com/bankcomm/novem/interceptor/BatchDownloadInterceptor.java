package com.bankcomm.novem.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankcomm.novem.biz.common.util.ExportImportUtils;
import com.bocom.jump.bp.JumpException;
import com.bocom.jump.bp.channel.ChannelContext;
import com.bocom.jump.bp.channel.http.interceptors.ServletChannelInterceptor;
import com.bocom.jump.bp.core.ContextEx;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatchDownloadInterceptor implements ServletChannelInterceptor {

	@Override
	public void onRequest(
			final ChannelContext<HttpServletRequest, HttpServletResponse> arg0,
			final ContextEx arg1) throws JumpException {

	}

	@Override
	public void onResponse(
			final ChannelContext<HttpServletRequest, HttpServletResponse> paramChannelContext,
			final ContextEx paramExContext, final Throwable paramThrowable) {

		final String zipFilePath = paramExContext.getData("zipFilePath");
		if (zipFilePath == null) {
			return;
		}

		setResponse(paramChannelContext.getResponse(), zipFilePath.replaceAll(".*[/\\\\]", ""));
		transportFile(paramChannelContext.getResponse(),zipFilePath);
		paramExContext.setData("X-processType", "X-DownLoad");
	
	}

	/**
	 * 
	 * @param localHttpServletResponse
	 * @param fileName
	 */
	private void setResponse(
			final HttpServletResponse localHttpServletResponse,
			final String fileName) {
		localHttpServletResponse.setContentType("application/x-download;charset=UTF-8");
	}

	private void transportFile(final HttpServletResponse httpServletResponse,
			final String fullPath) {
		final File file = new File(fullPath);
		int bomHeadbytesLength = 0;
		bomHeadbytesLength = ExportImportUtils.BOM_HEAD_BYTES.length;
		httpServletResponse.setHeader("Content-Length",
				String.valueOf(file.length() + bomHeadbytesLength));

		OutputStream toClient;
		try {
			toClient = httpServletResponse.getOutputStream();
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException("ServletOutputStream异常", e);
		}

		try {
			final InputStream fileStream = new FileInputStream(file);
			final byte[] buffer = new byte[10240];
			while (fileStream.available() > 0) {
				fileStream.read(buffer);
				toClient.write(buffer);
			}

			fileStream.close();
			toClient.flush();
			
			File fi = new File(fullPath);
			fi.delete();//下载成功后删除临时文件
			
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
			File fi = new File(fullPath);
			fi.delete();//遇到异常后也要删除临时文件
			throw new IllegalStateException("文件异常", e);
		} finally {
			try {
				toClient.close();
			} catch (final IOException e) {
				log.error(e.getMessage(), e);
				throw new IllegalStateException("ServletOutputStream异常", e);
			}
		}
	}
}
