package com.bankcomm.novem.biz.file;

import java.util.List;

import com.bankcomm.novem.bo.file.DownloadCountsBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;
import com.bankcomm.novem.bo.file.FileUpdateBo;
import com.bankcomm.novem.bo.file.FileUpdateCatBo;
import com.bankcomm.novem.bo.file.FileUpdateStateBo;
import com.bankcomm.novem.bo.file.FileUploadBo;

public interface IFileManageBiz {
	
	/**
	 * 上传单个文件
	 */
	void insertFile(final FileUploadBo newFile);
	
	int selectFileId(final String fullName);
	
	void insertCounts(final DownloadCountsBo downloadCountsBo);
	
	boolean updateCounts(final DownloadCountsBo downloadCountsBo);
	
	int selectCounts(final int fileId);
//	/**
//	 * 上传多个文件
//	 */
//	void uploadFile(final List<FileUploadBo> newFiles, final List<String> localPaths);
	/**
	 * 下载单个文件
	 */
	void downloadFile(final int fileId, final String localPath);
//	/**
//	 * 下载多个文件
//	 */
//	void downloadFile(final List<Integer> fileIds, final List<String> localPaths);
	/**
	 * 记录单个文件下载信息
	 */
	void recDownload(final DownloadRecBo downloadRec, final DownloadCountsBo downloadCountsBo);
	/**
	 * 删除单个文件
	 */
	boolean deleteFile(final int fileId);
	/**
	 * 删除多个文件
	 */
	boolean deleteFile(final List<Integer> fileIds);
	/**
	 * 更新文件信息
	 */
	boolean updateFile(final FileUpdateBo updatedFile);
	/**
	 * 更新文件状态
	 */
	boolean updateFileByState(final FileUpdateStateBo fileState);
	/**
	 * 更新文件类别
	 */
	boolean updateFileByCat(final FileUpdateCatBo fileCat);

}
