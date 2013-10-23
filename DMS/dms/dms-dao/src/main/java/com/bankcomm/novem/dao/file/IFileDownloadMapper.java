package com.bankcomm.novem.dao.file;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.file.DownloadCountsBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;

@Repository
public interface IFileDownloadMapper {
	/**
	 * 插入记录
	 */
	void insertRec(DownloadRecBo downloadRec);
	/**
	 * 更新下载次数
	 */
	boolean updateCounts(DownloadCountsBo downloadCountsBo);
	//void insertRecAndUpdateCounts(DownloadRecBo downloadRec, DownloadCountsBo downloadCountsBo);
	
	void insertCounts(DownloadCountsBo downloadCountsBo);
	
	int selectFileId(String fullName);
	
	int selectCounts(int fileId);

}
