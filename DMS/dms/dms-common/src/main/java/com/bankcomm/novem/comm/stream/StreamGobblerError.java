package com.bankcomm.novem.comm.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiarch<arms_admin@bankcomm.com>liyang 2011-11-21
 * 
 */
@Slf4j
public class StreamGobblerError extends Thread {
	private final InputStream inputStream;

	/**
	 * @param inputStream
	 *            数据流
	 */
	public StreamGobblerError(final InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		final InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream);
		final BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				log.error(line);
			}
		} catch (final IOException ioe) {
			throw new IllegalStateException("处理数据输出时出现异常", ioe);
		} finally {
			try {
				inputStreamReader.close();
				bufferedReader.close();
			} catch (final IOException e) {
				throw new IllegalStateException("关闭输出流异常", e);
			}
		}
	}
}
