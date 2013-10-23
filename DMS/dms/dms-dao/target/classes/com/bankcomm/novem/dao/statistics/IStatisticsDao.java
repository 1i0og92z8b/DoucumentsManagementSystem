package com.bankcomm.novem.dao.statistics;

import java.util.HashMap;
import java.util.List;

import  com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import  com.bankcomm.novem.bo.statistics.UserRankingBo;
import com.bankcomm.novem.bo.statistics.FilePathBo;
import com.bankcomm.novem.comm.PageCond;

public interface IStatisticsDao {
	 
	 List<UserRankingBo> topDownloader(PageCond pageCond);	
	 List<DownloadedFileBo> topDownloadedFile(PageCond pageCond);	
	 List<DownloadedFileBo> topDownloadedFileOnlySearchable(PageCond pageCond);
	 List<UserRankingBo> topUploaderByFileCount(PageCond pageCond);	 
	 List<UserRankingBo> topUploaderByDownloadCount(PageCond pageCond);	 
	 List<DownloadedFileBo> recentUploadedFile(PageCond pageCond);
	 List<DownloadedFileBo> recentUploadedFileOnlySearchable(PageCond pageCond);
	 Boolean updateDownloadCount(int fileId);	
     Boolean updateDownloadCountList(HashMap<String,Object> hm);	
     Boolean insertDownloadCount(int fileId);
     List<FilePathBo> filePathQueryByFileId(List<Integer> fileIdList);
}
