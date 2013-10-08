package com.bankcomm.novem.dao.file;

import java.sql.Timestamp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.bo.file.DownloadCountsBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;
import com.bankcomm.novem.dao.BaseDaoTest;

public class FileDownloadDaoImplTest extends BaseDaoTest<DownloadRecBo> {
	
	@Autowired
	private FileDownloadDaoImpl fileDownloadDao;
	
	@Test
	@Rollback(false)
	public void testDownloadInfo(){
		DownloadRecBo downloadRecBo = new DownloadRecBo();
		downloadRecBo.setFileId(5);
		downloadRecBo.setUserId(100);
		downloadRecBo.setDownloadTime(new Timestamp(System.currentTimeMillis()));
		downloadRecBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		downloadRecBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		downloadRecBo.setModifyUser(100);
		
		DownloadCountsBo downloadCountsBo = new DownloadCountsBo();
		downloadCountsBo.setFileId(5);
		downloadCountsBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		downloadCountsBo.setModifyUser(100);
		
		fileDownloadDao.insertRec(downloadRecBo);
		fileDownloadDao.updateCounts(downloadCountsBo);
	}

}
