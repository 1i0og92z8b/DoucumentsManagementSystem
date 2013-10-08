package com.bankcomm.novem.dao.statistics;

import java.util.List;
import java.util.ArrayList;

import  com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import  com.bankcomm.novem.bo.statistics.UserRankingBo;
import com.bankcomm.novem.comm.PageCond;

import com.bankcomm.novem.dao.BaseDaoTest;
import com.bankcomm.novem.dao.annote.DbSchemaType;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;

@DbSchemaType("DMSDB")
@Repository

/**
 * 
 * @author 荆昊熠<jing_hy@bankcomm.com> 
 * 
 */

public class StatisticsDaoImplTest extends BaseDaoTest{
	@Autowired
	private IStatisticsDao iStatisticsDao;
	
	@Test
	@Rollback(false)
	public void topDownloaderTest(){
		PageCond pc=new PageCond(1,13);
		List<UserRankingBo> ur= new ArrayList<UserRankingBo>();
		
		System.out.println("paging start:1  end: 5 ");
		
		System.out.println("Test for topDownloader");
		
		ur = iStatisticsDao.topDownloader(pc);
		for (UserRankingBo a: ur) {
			System.out.println("-----------New item");
			System.out.println("UserId:  "+a.getUserId());
			System.out.println("UserName:  "+a.getUserName());
			System.out.println("Name:  "+a.getName());
			System.out.println("Sex:  "+a.getSex());
			System.out.println("Email:  "+a.getEmail());
			System.out.println("ExtNo:  "+a.getExtNo());
			System.out.println("Counts:  "+a.getCounts());
		}
		Assert.assertNotNull(ur);
	}
	
	@Test
	@Rollback(false)
	public void topDownloadedFileTest(){
		
		PageCond pc=new PageCond(1,13);	
		
		pc.setSTART(2);
		System.out.println("PageCond.START is set to 2");
		pc.setEND(7);
		System.out.println("PageCond.END is set to 7");
		
		List<DownloadedFileBo> df= new ArrayList<DownloadedFileBo>();
		
		System.out.println("Test for topDownloadedFile");
		
		df = iStatisticsDao.topDownloadedFile(pc);
		for (DownloadedFileBo a: df) {
			System.out.println("-----------New item");
			System.out.println("FileId:  "+a.getFileId());
			System.out.println("UserId:  "+a.getUserId());
			System.out.println("FileName:  "+a.getFileName());
			System.out.println("UserName:  "+a.getUserName());
			System.out.println("CategoryName:  "+a.getCategoryName());
			System.out.println("UploadTime:  "+a.getUploadTime());
			System.out.println("FilePath:  "+a.getFilePath());
			System.out.println("FileStat:  "+a.getFileState());
			System.out.println("FullName:  "+a.getFullName());
			System.out.println("CreateTime:  "+a.getCreateTime());
			System.out.println("UpdateTime:  "+a.getUpdateTime());
			System.out.println("Updator:  "+a.getUpdator());
			System.out.println("DownloadCounts:  "+a.getDownloadCounts());
		}
		Assert.assertNotNull(df);
	}
	
	@Test
	@Rollback(false)
	public void topUploaderByFileCountTest(){
		PageCond pc=new PageCond(1,13);
		List<UserRankingBo> ur= new ArrayList<UserRankingBo>();
		
		System.out.println("paging start:1  end: 5 ");
		System.out.println("Test for topUploaderByFileCount");
		
		ur = iStatisticsDao.topUploaderByFileCount(pc);
		for (UserRankingBo a: ur) {
			System.out.println("-----------New item");
			System.out.println("UserId:  "+a.getUserId());
			System.out.println("UserName:  "+a.getUserName());
			System.out.println("Name:  "+a.getName());
			System.out.println("Sex:  "+a.getSex());
			System.out.println("Email:  "+a.getEmail());
			System.out.println("ExtNo:  "+a.getExtNo());
			System.out.println("Counts:  "+a.getCounts());
		}
		Assert.assertNotNull(ur);
	}
	
	@Test
	@Rollback(false)
	public void topUploaderByDownloadCountTest(){
		PageCond pc=new PageCond(1,13);
		List<UserRankingBo> ur= new ArrayList<UserRankingBo>();
		
		System.out.println("paging start:1  end: 5 ");
		
		System.out.println("Test for topUploaderByDownloadCount");
		
		ur = iStatisticsDao.topUploaderByDownloadCount(pc);
		for (UserRankingBo a: ur) {
			System.out.println("-----------New item");
			System.out.println("UserId:  "+a.getUserId());
			System.out.println("UserName:  "+a.getUserName());
			System.out.println("Name:  "+a.getName());
			System.out.println("Sex:  "+a.getSex());
			System.out.println("Email:  "+a.getEmail());
			System.out.println("ExtNo:  "+a.getExtNo());
			System.out.println("Counts:  "+a.getCounts());
		}
		Assert.assertNotNull(ur);
	}
	
	@Test
	@Rollback(false)
	public void recentUploadedFileTest(){
		PageCond pc=new PageCond(1,13);
		List<DownloadedFileBo> df= new ArrayList<DownloadedFileBo>();
		
		System.out.println("paging start:1  end: 5 ");		
		System.out.println("Test for recentUploadedFile");
		
		df = iStatisticsDao.recentUploadedFile(pc);
		for (DownloadedFileBo a: df) {
			System.out.println("-----------New item");
			System.out.println("FileId:  "+a.getFileId());
			System.out.println("UserId:  "+a.getUserId());
			System.out.println("FileName:  "+a.getFileName());
			System.out.println("UserName:  "+a.getUserName());
			System.out.println("CategoryName:  "+a.getCategoryName());
			System.out.println("UploadTime:  "+a.getUploadTime());
			System.out.println("FilePath:  "+a.getFilePath());
			System.out.println("FileStat:  "+a.getFileState());
			System.out.println("FullName:  "+a.getFullName());
			System.out.println("CreateTime:  "+a.getCreateTime());
			System.out.println("UpdateTime:  "+a.getUpdateTime());
			System.out.println("Updator:  "+a.getUpdator());
			System.out.println("DownloadCounts:  "+a.getDownloadCounts());
		}
		Assert.assertNotNull(df);
	}
		
	
	@Test
	public void updateDownloadCountTest(){
		System.out.println("Test for updateDownloadCount");
		iStatisticsDao.updateDownloadCount(1);
	}	

}


