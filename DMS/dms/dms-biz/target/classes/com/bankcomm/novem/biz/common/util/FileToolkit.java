package com.bankcomm.novem.biz.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.SneakyThrows;

import com.bankcomm.novem.bo.special.ImportBo;

public class FileToolkit {
	/**
	 * @param is
	 *            inputStream
	 * @param tmpFile
	 *            需要生成的临时文件
	 * @return 是否成功
	 */
	@SneakyThrows({ IOException.class })
	public static boolean createFile(final InputStream is, final File tmpFile) {
		final FileOutputStream fos = new FileOutputStream(tmpFile);
		final byte[] buffer = new byte[10240];
		int b = 0;
		while ((b = is.read(buffer)) != -1) {
			fos.write(buffer, 0, b);
		}
		fos.flush();
		is.close();
		fos.close();

		return true;
	}
	
	public static boolean renameFile(final ImportBo importBo, final String fileName) {
		String tempFilePath = importBo.getTempFilePath();
		String tempFileName = importBo.getTempFileName();
		String path = tempFilePath.substring(0, tempFilePath.length()-tempFileName.length());
		path = path+fileName;
		File file1 = new File(tempFilePath);  
		File file2 = new File(path);  
		return file1.renameTo(file2);
	}
	
	public static boolean deleteFile(final String filepath) {
		
		return true;
	}
}