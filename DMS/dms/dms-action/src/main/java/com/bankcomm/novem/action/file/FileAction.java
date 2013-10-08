package com.bankcomm.novem.action.file;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.apache.poi.hdf.model.hdftypes.FileInformationBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.common.util.FileToolkit;
import com.bankcomm.novem.biz.file.IFileManageBiz;
import com.bankcomm.novem.biz.search.IFileSearchBiz;
import com.bankcomm.novem.biz.search.IQueryMethodSelectBiz;
import com.bankcomm.novem.bo.demo.DemoBo;
import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.bo.file.DownloadCountsBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;
import com.bankcomm.novem.bo.file.FileUpdateBo;
import com.bankcomm.novem.bo.file.FileUploadBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.special.ExportBo;
import com.bankcomm.novem.bo.special.ImportBo;


import com.bankcomm.novem.bo.statistics.DownloadedFileBo;

import com.bankcomm.novem.comm.PageCond;
//import com.bankcomm.novem.interceptor.FileUploadResolverInterceptor;

import com.bocom.jump.bp.core.Context;
import com.ibm.ws.webservices.xml.wassysapp.systemApp;

@Data
@Controller
public class FileAction extends BaseAction {
	
	@Autowired
	private IFileManageBiz fileManageBizImpl;
	@Autowired
	private IQueryMethodSelectBiz queryMethodSelectBizImpl;
	
	private Object frontData;
	
//	@Autowired
//	private FileUploadResolverInterceptor fileUploadResolverInterceptor = new FileUploadResolverInterceptor();
	
	public void insertFileInfo(final Context context, String fullName) {
		final FileUploadBo fileUploadBo = ContextExtractor.extractBean(context, "fileInfo", FileUploadBo.class);
//		final FileUploadBo fileUploadBo = new FileUploadBo();
//		fileUploadBo.setFileName((String)ContextExtractor.extractValue(context, "fileName"));
//		fileUploadBo.setKeywords((String)ContextExtractor.extractValue(context, "keywords"));
//		fileUploadBo.setCategoryId((Integer)ContextExtractor.extractValue(context, "categoryId"));
//		fileUploadBo.setUserId((Integer)ContextExtractor.extractValue(context, "userId"));
//		fileUploadBo.setFileState((Character)ContextExtractor.extractValue(context, "fileState")); 
//		fileUploadBo.setFilePath(filePath)
		fileUploadBo.setFullName(fullName);
		fileUploadBo.setFilePath("D:");
		fileUploadBo.setUploadTime(new Timestamp(System.currentTimeMillis()));
		fileUploadBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileUploadBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		fileManageBizImpl.insertFile(fileUploadBo);
		context.setData("RESULT", "上传成功！");
	}
	
	public void uploadFile(final Context context) {
		final Map<String, Object> map = context.getDataMap();
		final String fullName = (String) map.get("ATTACHMENTID");
//		final String fileName = map.get("fileName").toString();
//		FileToolkit.renameFile(importBo, fileName);
		insertFileInfo(context, fullName);
	}
	
	
	public void downloadFile(final Context context) {
		ExportBo exportBo = new ExportBo();
		FileFieldBo fileFieldBo = new FileFieldBo();
		
		fileFieldBo.setFileId(Integer.valueOf((String)ContextExtractor.extractValue(context, "fileId")));
		int userId = Integer.valueOf((String)ContextExtractor.extractValue(context, "userId"));
		FileBo fileBo = new FileBo();
		List<FileBo> fileBos = new ArrayList<FileBo>();
		if((fileBos = queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, 4)) != null)
		{
			fileBo = fileBos.get(0);
		}
		exportBo.setFileName(fileBo.getFileName());
		exportBo.setFullFilePath(fileBo.getFilePath() + '\\' + fileBo.getFullName());
		//context.setData("exportBo", exportBo);
		DownloadRecBo downloadRec = new DownloadRecBo();
		downloadRec.setFileId(fileBo.getFileId());
		downloadRec.setUserId(userId);
		downloadRec.setDownloadTime(new Timestamp(System.currentTimeMillis()));
		downloadRec.setCreateTime(new Timestamp(System.currentTimeMillis()));
		downloadRec.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		downloadRec.setModifyUser(userId);
		DownloadCountsBo downloadCountsBo = new DownloadCountsBo();
		downloadCountsBo.setFileId(fileBo.getFileId());
		downloadCountsBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		downloadCountsBo.setModifyUser(userId);
		fileManageBizImpl.recDownload(downloadRec, downloadCountsBo);
		context.setData("exportBo", exportBo);
	}
	
	public boolean updateFileInfo(final Context context) {
		FileUpdateBo fileUpdateBo = new FileUpdateBo();
		fileUpdateBo = ContextExtractor.extractBean(context, "fileInfo", FileUpdateBo.class);
		fileUpdateBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean updateSuccess = fileManageBizImpl.updateFile(fileUpdateBo);
		context.setData("RESULT", "更新成功！");
		return updateSuccess;
	}
	
	public void queryUploadedFiles(final Context context) {
		FileFieldBo fileFieldBo = ContextExtractor.extractBean(context, "PARAMS", FileFieldBo.class);
		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(fileFieldBo == null){
			fileFieldBo = new FileFieldBo();
		}
		fileFieldBo.setPageCond(pageCond);
		final List<FileBo> list = queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, 5);
		context.setData("list", list);
		context.setData("PAGE_COND", fileFieldBo.getPageCond());
	}

}
