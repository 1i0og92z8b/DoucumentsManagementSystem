package com.bankcomm.novem.dao.file;

import com.bankcomm.novem.bo.file.DownloadCountsBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;

public interface IFileDownloadDao {
	/**
	 * 插入记录
	 */
	void insertRec(DownloadRecBo downloadRec);
	/**
	 * 更新下载次数
	 */
	boolean updateCounts(DownloadCountsBo downloadCountsBo);
	//void insertRecAndUpdateCounts(DownloadRecBo downloadRec, DownloadCountsBo downloadCountsBo);
	
	boolean insertCounts(DownloadCountsBo downloadCountsBo);

}
