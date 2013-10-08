package com.bankcomm.novem.action.search;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.search.IFileSearchBiz;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;



@Controller
public class FileSearchAction extends BaseAction {

	@Autowired
	private IFileSearchBiz IFileSearchBizImpl;
	
	public void fileSearch(final Context context){
		FileFieldBo fileFieldBo = ContextExtractor.extractBean(context, "PARAMS", FileFieldBo.class);
		if(fileFieldBo == null){
			fileFieldBo = new FileFieldBo();
		}
		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		fileFieldBo.setPageCond(pageCond);
		System.out.println("startdate = "+ fileFieldBo.getStartDate());
		List<FileBo> fileBo = new ArrayList<FileBo>();
//		String fileName = context.getData("fileName");
//		System.out.println("fileName = "+fileName);
//		fileFieldBo.setFileName(fileName);
//		fileFieldBo.setCategoryId(1);
		fileBo.addAll( IFileSearchBizImpl.fileSearch(fileFieldBo) );
		for(FileBo f:fileBo){
			System.out.println(f.toString());
		}
		System.out.println(fileFieldBo.getPageCond().toString());
		context.setData("list", fileBo);
		context.setData("PAGE_COND", fileFieldBo.getPageCond());
		

	}
}
