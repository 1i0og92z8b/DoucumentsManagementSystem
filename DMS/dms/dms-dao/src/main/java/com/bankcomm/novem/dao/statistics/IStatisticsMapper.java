package com.bankcomm.novem.dao.statistics;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import  com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import  com.bankcomm.novem.bo.statistics.FilePathBo;
import  com.bankcomm.novem.bo.statistics.UserRankingBo;
import  com.bankcomm.novem.comm.PageCond;

@Repository

public interface IStatisticsMapper {
	 List<UserRankingBo> topDownloader(PageCond pageCond);	
	 int topDownloaderTotalCount();
	 List<DownloadedFileBo> topDownloadedFile(PageCond pageCond);		
	 int topDownloadedFileTotalCount();
	 List<DownloadedFileBo> topDownloadedFileOnlySearchable(PageCond pageCond);
	 int topDownloadedFileOnlySearchableTotalCount();
	 List<UserRankingBo> topUploaderByFileCount(PageCond pageCond);	 
	 int topUploaderByFileCountTotalCount();	
	 List<UserRankingBo> topUploaderByDownloadCount(PageCond pageCond);	 
	 int topUploaderByDownloadCountTotalCount();
	 List<DownloadedFileBo> recentUploadedFile(PageCond pageCond);
	 int recentUploadedFileTotalCount();	
	 List<DownloadedFileBo> recentUploadedFileOnlySearchable(PageCond pageCond);
	 int recentUploadedFileOnlySearchableTotalCount();
	 Boolean updateDownloadCount(int fileId);	
	 Boolean updateDownloadCountList(HashMap<String,Object> hm);	
	 Boolean insertDownloadCount(int fileId);
     List<FilePathBo> filePathQueryByFileId(List<Integer> fileIdList);
}
