package com.bankcomm.novem.dao.file;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.bo.file.ConstantFileState;
import com.bankcomm.novem.bo.file.FileUpdateBo;
import com.bankcomm.novem.bo.file.FileUpdateCatBo;
import com.bankcomm.novem.bo.file.FileUpdateStateBo;
//import com.bankcomm.novem.bo.date.RiskTimestamp;
import com.bankcomm.novem.bo.file.FileUploadBo;
import com.bankcomm.novem.dao.BaseDaoTest;

public class FileManageDaoImplTest extends BaseDaoTest<FileUploadBo> {
	
	@Autowired
	private FileManageDaoImpl fileManageDao;
	
	@Test
	@Rollback(false)
	public void testInsertFile(){
		FileUploadBo fileUploadBo = new FileUploadBo();
		fileUploadBo.setFileName("firstFile");
		fileUploadBo.setKeywords("firstKeywords");
		fileUploadBo.setCategoryId(100);
		fileUploadBo.setUserId(100);
		//fileUploadBo.setUploadTime("2013-09-17 00:00:00");
		fileUploadBo.setUploadTime(new Timestamp(System.currentTimeMillis()));
		fileUploadBo.setFileState('1');
		fileUploadBo.setFileDesc("firstDescription");
		fileUploadBo.setFilePath("firstFilePath");
		fileUploadBo.setFullName("firstFullName");
		fileUploadBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		fileUploadBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		fileUploadBo.setModifyUser(100);
		fileManageDao.insertFile(fileUploadBo);
	}
	
	@Test
	@Rollback(false)
	public void testUpdateFile(){
		FileUpdateBo fileUpdateBo = new FileUpdateBo();
		fileUpdateBo.setKeywords("中秋节放假通知");
		fileUpdateBo.setFileDesc("test update fileDesc");
		fileUpdateBo.setCategoryId(100);
		//fileUpdateBo.setFilePath("testFilePath");
		fileUpdateBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		fileUpdateBo.setModifyUser(100);
		fileUpdateBo.setFileId(65);
		fileManageDao.updateFile(fileUpdateBo);
	}
	
	@Test
	@Rollback(false)
	public void testUpdateFileByState(){
		FileUpdateStateBo fileUpdateStateBo = new FileUpdateStateBo();
		fileUpdateStateBo.setFileState(ConstantFileState.DeniedState);
		fileUpdateStateBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		fileUpdateStateBo.setModifyUser(100);
		fileUpdateStateBo.setFileId(50);
		fileManageDao.updateFileByState(fileUpdateStateBo);
	}
	
	@Test
	@Rollback(false)
	public void testUpdateFileByCat(){
		FileUpdateCatBo fileUpdateCatBo = new FileUpdateCatBo();
		fileUpdateCatBo.setCategoryId(50);
		fileUpdateCatBo.setFilePath("filePath");
		fileUpdateCatBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		fileUpdateCatBo.setModifyUser(100);
		fileUpdateCatBo.setFileId(14);
		fileManageDao.updateFileByCat(fileUpdateCatBo);
	}
	
	@Test
	@Rollback(false)
	public void testDeleteFile(){
		int fileId = 70;
		fileManageDao.deleteFile(fileId);
	}
	
	@Test
	@Rollback(false)
	public void testDeleteFiles(){
		List<Integer> fileIds = new ArrayList<Integer>();
		fileIds.add(10);
		fileIds.add(16);
		fileIds.add(37);
		fileManageDao.deleteFiles(fileIds);
	}

}
