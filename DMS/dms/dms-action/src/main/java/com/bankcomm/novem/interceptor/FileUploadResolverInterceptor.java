package com.bankcomm.novem.interceptor;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.resource.spi.IllegalStateException;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.bankcomm.novem.biz.common.util.FileToolkit;
import com.bankcomm.novem.bo.file.FileUploadBo;
import com.bankcomm.novem.bo.special.ImportBo;
import com.bocom.jump.bp.channel.http.interceptors.upload.FileUploadResolver;
import com.bocom.jump.bp.transform.bytes.Var;

@Log4j
public class FileUploadResolverInterceptor implements FileUploadResolver {
	
	private static final String DOT = ".";
//	@Autowired
//	private final String PATH = "D:\\ChromeDownloads\\test1";

	private final String PATH = "D:";
	
	@Override
	public Object doWriteFiles(final Map<String, MultipartFile> paramMap, final String paramString) {
		final MultipartFile fileData = paramMap.get("Filedata");
		Assert.notNull(fileData, "上传文件信息为空");
		final File file = createImportFile(fileData);
		Object object = new Object();
		object = file.getName();
		return object;
		//return assembleImportBo(fileData, file);
	}
	
	/**
	 * 组装导入信息
	 * 
	 * @param fileData
	 *            文件数据
	 * @param file
	 *            文件
	 * @return
	 */
//	private ImportBo assembleImportBo(final MultipartFile fileData, final File file) {
//		final ImportBo importBo = new ImportBo();
//		importBo.setTempFileName(file.getName());
//		importBo.setTempFilePath(file.getAbsolutePath());
//		importBo.setFileName(fileData.getOriginalFilename());
//		importBo.setFileSize(fileData.getSize());
//		importBo.setFileSuffix(DOT
//				+ StringUtils.substringAfterLast(
//						fileData.getOriginalFilename(), DOT));
//		return importBo;
//	}
	
//	private FileUploadBo assembleFileUploadBo(final MultipartFile fileData, final File file) {
//		final FileUploadBo fileUploadBo  = new FileUploadBo();
//	}
	
	/**
	 * 创建临时文件
	 * 
	 * @param fileData
	 *            文件数据
	 * @return 文件file
	 */
	private File createImportFile(final MultipartFile fileData) {
		final String suffix = DOT + StringUtils.substringAfterLast(fileData.getOriginalFilename(), DOT);
		
		final File filePath = new File(PATH);
		if(!filePath.exists()) {
			filePath.mkdir();
		}
		UUID uuid = UUID.randomUUID();
		final String fileName = uuid + suffix;
		File file = new File(filePath, fileName);
		if(file.exists()) {
			file.delete();
			file = new File(filePath, fileName);
		}
		
		try {
			FileToolkit.createFile(fileData.getInputStream(), file);
		}
		catch (final IOException ex) {
			log.error("Reading or writing errors" + ex);
			try {
				throw new IllegalStateException("存取文件异常", ex);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return file;
	}

}
