package com.bankcomm.novem.biz.search;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bankcomm.novem.biz.BaseBizTest;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;


public class FileSearchBizImplTest extends BaseBizTest {
	@Autowired
	private IFileSearchBiz fileSearchBizImpl;

	/**
	 * 
	 */
	@Test
	public void testFileSearch() {

		FileFieldBo ffb = new FileFieldBo();
		List<FileBo> fbs = new ArrayList<FileBo>();
		ffb.setFileName("书");
		ffb.setFileState('1');
		
		fbs.addAll(fileSearchBizImpl.fileSearch(ffb));
//		System.out.println("here");
		for(FileBo f :fbs){
			System.out.println(f.toString());
		}
		
	}

	/**
	 * 
	 */
}
