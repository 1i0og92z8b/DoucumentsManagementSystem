package com.bankcomm.novem.biz.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.bo.file.DownloadCountsBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;
import com.bankcomm.novem.bo.file.FileUpdateBo;
import com.bankcomm.novem.bo.file.FileUpdateCatBo;
import com.bankcomm.novem.bo.file.FileUpdateStateBo;
import com.bankcomm.novem.bo.file.FileUploadBo;
import com.bankcomm.novem.dao.file.IFileDownloadDao;
import com.bankcomm.novem.dao.file.IFileManageDao;

@Service
public class FileManageBizImpl implements IFileManageBiz {
	
	@Autowired
	private IFileManageDao fileManageDaoImpl;
	@Autowired
	private IFileDownloadDao fileDownloadDaoImpl;
	
	@Override
	public void insertFile(final FileUploadBo newFile) {
		fileManageDaoImpl.insertFile(newFile);
	}
	
	@Override
	public int selectFileId(final String fullName) {
		return fileDownloadDaoImpl.selectFileId(fullName);
	}
	
	@Override
	public void insertCounts(final DownloadCountsBo downloadCountsBo) {
		fileDownloadDaoImpl.insertCounts(downloadCountsBo);
	}
	
	@Override
	public boolean updateCounts(final DownloadCountsBo downloadCountsBo) {
		return fileDownloadDaoImpl.updateCounts(downloadCountsBo);
	}
	
	@Override
	public int selectCounts(final int fileId) {
		return fileDownloadDaoImpl.selectCounts(fileId);
	}
//	public void uploadFile(final FileUploadBo newFile, final String localPath) {
//		
//	}
	
//	@Override
//	public void uploadFile(final List<FileUploadBo> newFiles, final List<String> localPaths) {
//		
//	}
	
	@Override
	public void downloadFile(final int fileId, final String localPath) {
		
	}
	
//	@Override
//	public void downloadFile(final List<Integer> fileIds, final List<String> localPaths) {
//		
//	}
	
	@Override
	public void recDownload(final DownloadRecBo downloadRec, final DownloadCountsBo downloadCountsBo) {
		fileDownloadDaoImpl.insertRec(downloadRec);
		fileDownloadDaoImpl.updateCounts(downloadCountsBo);
//		if(fileDownloadDaoImpl.insertCounts(downloadCountsBo))
//		{
//			return;
//		}
//		else {
//			fileDownloadDaoImpl.updateCounts(downloadCountsBo);
//		}
//		fileDownloadDaoImpl.insertCounts(downloadCountsBo);
	}
	
	@Override
	public boolean deleteFile(final int fileId) {
		//操作文件
		
		return fileManageDaoImpl.deleteFile(fileId);
	}
	
	@Override
	public boolean deleteFile(final List<Integer> fileIds) {
		//操作文件
		
		return fileManageDaoImpl.deleteFiles(fileIds);
	}
	
	@Override
	public boolean updateFile(final FileUpdateBo updatedFile) {
		return fileManageDaoImpl.updateFile(updatedFile);
	}
	
	@Override
	public boolean updateFileByState(final FileUpdateStateBo fileState) {
		return fileManageDaoImpl.updateFileByState(fileState);
	}
	
	@Override
	public boolean updateFileByCat(final FileUpdateCatBo fileCat) {
		return fileManageDaoImpl.updateFileByCat(fileCat);
	}


}
