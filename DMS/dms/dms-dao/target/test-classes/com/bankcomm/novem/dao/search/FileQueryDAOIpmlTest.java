package com.bankcomm.novem.dao.search;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;

import a.a.a.F;

import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.dao.BaseDaoTest;
import com.bankcomm.novem.dao.annote.DbSchemaType;


public class FileQueryDAOIpmlTest extends BaseDaoTest {
	@Autowired
	private IFileQueryDao ifileQueryDAOImpl;

	
	@Test
	@Rollback(false)
	public void testFileQueryByFileState() {
		String inputStr = "C";
		String inputStr2 = null;
//		PageCond pageCond = new PageCond(1,3);
		List<FileBo> fileBo = new ArrayList<FileBo>();
		FileFieldBo fieldBo = new FileFieldBo();
		Timestamp startDate = new Timestamp(System.currentTimeMillis());
//		fieldBo.setStartDate(startDate);
//		fieldBo.setFileName("a");
//		fieldBo.setCategoryId(1);
//		fieldBo.setUserName("a");
		List<String> keywords = new ArrayList<String>();
		keywords.add("1");
		keywords.add("t");
//		fieldBo.setKeywords(keywords);
//		System.out.println(fieldBo.getCategoryId());
//		System.out.println(fieldBo.getFileId());
//		System.out.println(fieldBo.getFileName());
//		System.out.println(fieldBo.getStartDate());
//		System.out.println(fieldBo.getFileState());
		fieldBo.setUserId(1);
		
		fieldBo.setCategoryId(1);
		fieldBo.setFileState('0');
//		fieldBo.setPageCond(pageCond);
//		System.out.println(fieldBo.getFileState());
//		fileBo.addAll(ifileQueryDAOImpl.fileQueryByFileName(fieldBo));
//		fileBo.addAll(ifileQueryDAOImpl.fileQueryByFileNameAndFileState(fieldBo));
//		fileBo.addAll(ifileQueryDAOImpl.fileSearchByFileFieldBo(fieldBo));

//		System.out.println(fieldBo.getFileName());
//		System.out.println(inputStr);
//		fileBo.addAll(ifileQueryDAOImpl.fileQueryByCategoryId(fieldBo));
		fileBo.addAll(ifileQueryDAOImpl.fileQueryByUserIdAndFileStateAndFileName(fieldBo));
//		fileBo.addAll(ifileQueryDAOImpl.fileQueryByFileState(fieldBo));

//		fileBo.addAll(ifileQueryDAOImpl.fileQueryByFileState(fieldBo));

//		fileBo.addAll(ifileQueryDAOImpl.fileQueryByFileId(fieldBo));
//		fileBo.addAll(ifileQueryDAOImpl.fileQueryByFileId(1));
		for(FileBo f :fileBo){
			System.out.println(f.toString());
		}
		System.out.println(fieldBo.getPageCond().toString());
		

	}
}
