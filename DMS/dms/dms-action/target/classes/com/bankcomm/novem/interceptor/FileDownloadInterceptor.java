package com.bankcomm.novem.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.lang.StringUtils;
//import org.dozer.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;

import com.bankcomm.novem.biz.common.util.ExportImportUtils;
import com.bankcomm.novem.bo.special.ExportBo;
import com.bocom.jump.bp.JumpException;
import com.bocom.jump.bp.channel.ChannelContext;
import com.bocom.jump.bp.channel.http.interceptors.ServletChannelInterceptor;
import com.bocom.jump.bp.core.ContextEx;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileDownloadInterceptor implements ServletChannelInterceptor {
//	private static final String DOT = ".";
//	
//	@Autowired
//	private Mapper mapper; 
	/*
	 * (non-Java *
	 * 
	 * @see
	 * com.bocom.jump.bp.channel.ChannelInterceptor#onRequest(com.bocom.jump
	 * .bp.channel.ChannelContext, com.bocom.jump.bp.core.ContextEx)
	 */
	@Override
	public void onRequest(
			final ChannelContext<HttpServletRequest, HttpServletResponse> arg0,
			final ContextEx arg1) throws JumpException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.jump.bp.channel.ChannelInterceptor#onResponse(com.bocom.jump
	 * .bp.channel.ChannelContext, com.bocom.jump.bp.core.ContextEx,
	 * java.lang.Throwable)
	 */
	@Override
	public void onResponse(
			final ChannelContext<HttpServletRequest, HttpServletResponse> paramChannelContext,
			final ContextEx paramExContext, final Throwable paramThrowable) {
		/*
		if (paramThrowable instanceof RiskBusinessException) {
			throw (RiskBusinessException) paramThrowable;
		}
		*/

		final ExportBo exportBo = paramExContext.getData("exportBo");
		if (exportBo == null) {
			return;
		}

		setResponse(paramChannelContext.getResponse(), exportBo.getFileName());

		transportFile(paramChannelContext.getResponse(),exportBo.getFullFilePath());
		paramExContext.setData("X-processType", "X-DownLoad");
		// 如果是导出类型而不是下载
//		if (exportBo.getDownloadType().matches("0")) {
//			deleteTmpFileExsits(exportBo.getFullFilePath());
//		}
	}
//
//	/**
//	 * 
//	 * @param filePath
//	 */
//	private void deleteTmpFileExsits(final String filePath) {
//		final File file = new File(filePath);
//		if (file.exists()) {
//			file.delete();
//		}
//	}
//
	/**
	 * 
	 * @param localHttpServletResponse
	 * @param fileName
	 */
	private void setResponse(
			final HttpServletResponse localHttpServletResponse,
			final String fileName) {
		localHttpServletResponse.setContentType("application/x-download;charset=UTF-8");
		/*
		try {
			final String encodeFileName = URLEncoder.encode(fileName,ExportImportUtils.queryCharsetEncode());
			localHttpServletResponse.setHeader("Content-Disposition","attachment; filename=" + encodeFileName);
		} catch (final UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException("字符集异常", e);
		}
		*/
	}
//
//	/**
//	 * 
//	 * @param fullPath
//	 * @param localServletOutputStream
//	 */
	private void transportFile(final HttpServletResponse httpServletResponse,
			final String fullPath) {
		final File file = new File(fullPath);
//		final String suffix = DOT
//				+ StringUtils.substringAfterLast(file.getName(), DOT);
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
			/*
			if (SUFFIX_LIST.contains(suffix.toLowerCase())) {
				httpServletResponse.setCharacterEncoding(ExportImportUtils
						.queryCharsetEncode());
				toClient.write(ExportImportUtils.BOM_HEAD_BYTES);
			}
			*/
			final byte[] buffer = new byte[10240];
			while (fileStream.available() > 0) {
				fileStream.read(buffer);
				toClient.write(buffer);
			}

			fileStream.close();
			toClient.flush();
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
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
