package com.bankcomm.novem.dao.statistics;

import java.util.HashMap;
import java.util.List;

import  com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import  com.bankcomm.novem.bo.statistics.FilePathBo;
import  com.bankcomm.novem.bo.statistics.UserRankingBo;
import  com.bankcomm.novem.comm.PageCond;

import com.bankcomm.novem.dao.annote.DbSchemaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@DbSchemaType("DMSDB")
@Repository

public class StatisticsDaoImpl implements IStatisticsDao{
	@Autowired
	private IStatisticsMapper  iStatisticsMapper;
	
	@Override 
	public List<UserRankingBo> topDownloader(PageCond pageCond){
		final int count = iStatisticsMapper.topDownloaderTotalCount(); 
		pageCond.setCOUNT(count);		
		return iStatisticsMapper.topDownloader(pageCond);
	}
	
	@Override 
	public List<DownloadedFileBo> topDownloadedFile(PageCond pageCond){
		final int count = iStatisticsMapper.topDownloadedFileTotalCount();
		pageCond.setCOUNT(count);
		return iStatisticsMapper.topDownloadedFile(pageCond);
	}
	
	@Override 
	public List<DownloadedFileBo> topDownloadedFileOnlySearchable(PageCond pageCond){
		final int count = iStatisticsMapper.topDownloadedFileOnlySearchableTotalCount();
		pageCond.setCOUNT(count);
		return iStatisticsMapper.topDownloadedFileOnlySearchable(pageCond);
	}

	@Override 
	public List<UserRankingBo> topUploaderByFileCount(PageCond pageCond){
		final int count = iStatisticsMapper.topUploaderByFileCountTotalCount();
		pageCond.setCOUNT(count);
		return iStatisticsMapper.topUploaderByFileCount(pageCond);
	}
	
	@Override 
	public List<UserRankingBo> topUploaderByDownloadCount(PageCond pageCond){
		final int count = iStatisticsMapper.topUploaderByDownloadCountTotalCount();
		pageCond.setCOUNT(count);
		return iStatisticsMapper.topUploaderByDownloadCount(pageCond);
	}
	
	@Override 
	public List<DownloadedFileBo> recentUploadedFile(PageCond pageCond){
		final int count = iStatisticsMapper.recentUploadedFileTotalCount();
		pageCond.setCOUNT(count);
		return iStatisticsMapper.recentUploadedFile(pageCond);
	}
	
	@Override 
	public List<DownloadedFileBo> recentUploadedFileOnlySearchable(PageCond pageCond){
		final int count = iStatisticsMapper.recentUploadedFileOnlySearchableTotalCount();
		pageCond.setCOUNT(count);
		return iStatisticsMapper.recentUploadedFileOnlySearchable(pageCond);
	}
	
	
	@Override
	public Boolean updateDownloadCount(int fileId){
		return iStatisticsMapper.updateDownloadCount(fileId);
	}
	
	@Override
	public Boolean updateDownloadCountList(HashMap<String,Object> hm){
		return iStatisticsMapper.updateDownloadCountList(hm);
	}
	
	@Override
	public Boolean insertDownloadCount(int fileId){
		return iStatisticsMapper.insertDownloadCount(fileId);
	}	
	
	@Override
	public List<FilePathBo> filePathQueryByFileId(List<Integer> fileIdList){
		return iStatisticsMapper.filePathQueryByFileId(fileIdList);
	}

}
