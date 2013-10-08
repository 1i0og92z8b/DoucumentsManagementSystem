package com.bankcomm.novem.biz.Recycle;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bankcomm.novem.biz.BaseBizTest;
import com.bankcomm.novem.biz.Recycle.IFileRecycleBiz;
import com.bankcomm.novem.bo.search.FileBo;

public class FileRecycleBizImplTest extends BaseBizTest {

	@Autowired
	private IFileRecycleBiz FileRecycleBizImpl;
	
	@Test
	public void testFileRecycle()
	{
		//测试queryFileByState方法
		char fileState = '2';    //查看审核未通过（2）或已删除（3）的文件
		List<FileBo> fos = new ArrayList<FileBo>();
//		fos.addAll(FileRecycleBizImpl.queryFileByState(fileState));

		for(FileBo fo : fos)
		{		
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
