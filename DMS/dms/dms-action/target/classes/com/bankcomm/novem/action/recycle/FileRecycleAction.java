package com.bankcomm.novem.action.recycle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.Recycle.IFileRecycleBiz;
import com.bankcomm.novem.biz.file.IFileManageBiz;
import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;

@Controller
public class FileRecycleAction extends BaseAction {
	
	@Autowired
	private IFileRecycleBiz fileRecycleBizImpl;
	
	/**
	 * 根据文件状态查看回收站中文件
	 * @param context
	 *            上下文
	 */
	public void queryRecycleFile(final Context context) {
			
		FileFieldBo fileFieldBo = ContextExtractor.extractBean(context, "PARAMS", FileFieldBo.class);
		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		fileFieldBo.setPageCond(pageCond);
				
		List<FileBo> fileList = new ArrayList<FileBo>();
		if(fileFieldBo.getUserId() == 0)
		{
			if(fileFieldBo.getFileName() == null)
				fileList.addAll(fileRecycleBizImpl.queryFileByState(fileFieldBo));
			else
				fileList.addAll(fileRecycleBizImpl.queryRecycleFileByFileName(fileFieldBo));
		}
		else {
				fileList.addAll(fileRecycleBizImpl.queryFileByUserIdAndFileStateAndFileName(fileFieldBo));		
		}
		
		context.setData("list", fileList);	
		context.setData("PAGE_COND", fileFieldBo.getPageCond());
	}
	
//	/**
//	 * 根据文件名查看待审核文件
//	 * @param context
//	 *            上下文
//	 */
//	public void queryUnpassFileByFileName(final Context context) {
//		
////		final String fileName = (String)ContextExtractor.extractValue(context, "fileName");
//
////		final PageCond pageCond = ContextExtractor.extractPageCond(context);
//		
//		String fileName = "Python";
//		
//		List<FileBo> fileList = new ArrayList<FileBo>();
//		fileList.addAll(fileRecycleBizImpl.queryUnpassFileByFileName(fileName));
//		
//		for(FileBo f :fileList){
//			System.out.print(f.getFileId()+"	");
//			System.out.print(f.getFileName()+"	");
//			System.out.print(f.getKeywords()+"	");
//			System.out.print(f.getUserName()+"	");	
//			System.out.print(f.getCategoryPath()+"	");
//			System.out.print(f.getUploadTime()+"	");
//			System.out.print(f.getFileState()+"	");
//			System.out.print(f.getFileDesc()+"	");
//			System.out.print(f.getFilePath()+"	");
//			System.out.print(f.getFullName()+"	");
//			System.out.print(f.getCreateTime()+"	");
//			System.out.print(f.getUpdateTime()+"	");
//			System.out.print(f.getUpdator()+"	");
//			System.out.println();
//		}
//		context.setData("fileList", fileList);
//	}
//
//	/**
//	 * 根据文件名查看已删除文件
//	 * @param context
//	 *            上下文
//	 */
//	public void queryDeletedFileByFileName(final Context context) {
//		
////		final String fileName = (String)ContextExtractor.extractValue(context, "fileName");
//
////		final PageCond pageCond = ContextExtractor.extractPageCond(context);
//		
//		String fileName = "C";
//		
//		List<FileBo> fileList = new ArrayList<FileBo>();
//		fileList.addAll(fileRecycleBizImpl.queryDeletedFileByFileName(fileName));
//		
//		for(FileBo f :fileList){
//			System.out.print(f.getFileId()+"	");
//			System.out.print(f.getFileName()+"	");
//			System.out.print(f.getKeywords()+"	");
//			System.out.print(f.getUserName()+"	");	
//			System.out.print(f.getCategoryPath()+"	");
//			System.out.print(f.getUploadTime()+"	");
//			System.out.print(f.getFileState()+"	");
//			System.out.print(f.getFileDesc()+"	");
//			System.out.print(f.getFilePath()+"	");
//			System.out.print(f.getFullName()+"	");
//			System.out.print(f.getCreateTime()+"	");
//			System.out.print(f.getUpdateTime()+"	");
//			System.out.print(f.getUpdator()+"	");
//			System.out.println();
//		}
//		context.setData("fileList", fileList);
//	}

	/**
	 * 批量恢复审核未通过文件
	 * @param context
	 *            上下文
	 */
	public void batchRecoverUnpassFiles(final Context context) {
	
		final List<Integer> fileIdList= (List<Integer>) ContextExtractor.extractArray(context, "fileIdList", Integer.class);
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
	
		fileRecycleBizImpl.batchRecoverUnpassFiles(fileIdList, userId);
	}
	
	/**
	 * 批量恢复已删除文件
	 * @param context
	 *            上下文
	 */
	public void batchRecoverDeletedFiles(final Context context) {
		
		final List<Integer> fileIdList= (List<Integer>) ContextExtractor.extractArray(context, "fileIdList", Integer.class);
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
	
		fileRecycleBizImpl.batchRecoverDeletedFiles(fileIdList, userId);
	}
	
	/**
	 * 批量彻底删除文件
	 * @param context
	 *            上下文
	 */
	public void deleteFile(final Context context) {
		
		final int fileId = (Integer)ContextExtractor.extractValue(context, "fileId");
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
	
		fileRecycleBizImpl.deleteFile(fileId, userId);
	}
	
	/**
	 * 批量彻底删除文件
	 * @param context
	 *            上下文
	 */
	public void batchDelete(final Context context) {
		
		final List<Integer> fileIdList= (List<Integer>) ContextExtractor.extractArray(context, "fileIdList", Integer.class);
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
	
		fileRecycleBizImpl.batchDelete(fileIdList, userId);
	}
}
