package com.bankcomm.novem.biz.Permission;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bankcomm.novem.biz.BaseBizTest;
import com.bankcomm.novem.biz.Permission.IFileCheckBiz;
import com.bankcomm.novem.bo.search.FileBo;

public class FileCheckBizImplTest extends BaseBizTest{

	@Autowired
	private IFileCheckBiz fileCheckBizImpl;

	@Test
	public void testFileCheck()
	{
//		System.out.println("Here");
		
		List<FileBo> fos = new ArrayList<FileBo>();
//		fos.addAll(fileCheckBizImpl.queryUncheckFiles());

		for(FileBo fo : fos)
		{
//			System.out.println(fo);
		
			System.out.print(fo.getFileId()+"	");
			System.out.print(fo.getFileName()+"	");
			System.out.print(fo.getKeywords()+"	");
			System.out.print(fo.getUploadTime()+"	");
			System.out.print(fo.getFileState()+"	");
			System.out.print(fo.getFileDesc()+"	");
			System.out.print(fo.getFilePath()+"	");
			System.out.print(fo.getFullName()+"	");
			System.out.print(fo.getCreateTime()+"	");
			System.out.print(fo.getUpdateTime()+"	");
			System.out.print(fo.getUpdator()+"	");
			System.out.println();
		}
	}
}
