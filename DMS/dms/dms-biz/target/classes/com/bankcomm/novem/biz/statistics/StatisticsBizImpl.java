package com.bankcomm.novem.biz.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import  com.bankcomm.novem.bo.statistics.UserRankingBo;
import  com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.dao.annote.DbSchemaType;
import  com.bankcomm.novem.dao.statistics.IStatisticsDao;

/**
 * 
 * @author 荆昊熠<jing_hy@bankcomm.com> 
 * 
 */

@DbSchemaType("DMSDB")
//@Repository
@Service
public class StatisticsBizImpl implements IStatisticsBiz{
	@Autowired
	private IStatisticsDao  iStatisticsDao;
	
	@Override 
	public List<UserRankingBo> topDownloader(PageCond pageCond){
		
		return iStatisticsDao.topDownloader(pageCond);
	}
	
	@Override 
	public List<DownloadedFileBo> topDownloadedFile(PageCond pageCond){
		return iStatisticsDao.topDownloadedFile(pageCond);
	}
	
	@Override 
	public List<UserRankingBo> topUploaderByFileCount(PageCond pageCond){
		return iStatisticsDao.topUploaderByFileCount(pageCond);
	}
	
	@Override 
	public List<UserRankingBo> topUploaderByDownloadCount(PageCond pageCond){
		return iStatisticsDao.topUploaderByDownloadCount(pageCond);
	}
	
	@Override 
	public List<DownloadedFileBo> recentUploadedFile(PageCond pageCond){
		return iStatisticsDao.recentUploadedFile(pageCond);
	}
	
	@Override 
	public List<DownloadedFileBo> recentUploadedFileOnlySearchable(PageCond pageCond){
		return iStatisticsDao.recentUploadedFileOnlySearchable(pageCond);
	}

}
