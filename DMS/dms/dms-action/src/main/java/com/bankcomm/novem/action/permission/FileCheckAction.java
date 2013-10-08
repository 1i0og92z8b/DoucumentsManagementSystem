package com.bankcomm.novem.action.permission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.Permission.IFileCheckBiz;
import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;


@Controller
public class FileCheckAction extends BaseAction {
	/**
	 * 
	 */
//	private static final String FILE_ID = "fileid";
	/**
	 * 
	 */
//	private static final String REPLY_MSG = "replyMsg";
	
	@Autowired
	private IFileCheckBiz fileCheckBizImpl;
	
	/**
	 * 查看待审核文件
	 * @param context
	 *            上下文
	 */
	public void queryFileByState(final Context context) {
		
//		final char fileState = (Character)ContextExtractor.extractValue(context, "fileState");  //必须先转换为包类型
		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		
		FileFieldBo fileFieldBo = new FileFieldBo();
		fileFieldBo.setPageCond(pageCond);
		
		List<FileBo> fileList = new ArrayList<FileBo>();
		fileList.addAll(fileCheckBizImpl.queryUncheckFiles(fileFieldBo));
		
		context.setData("list", fileList);	
		context.setData("PAGE_COND", fileFieldBo.getPageCond());
		
	}
	
	/**
	 * 审核通过
	 * @param  fileID
	 * @return 是否成功
	 */
	public void checkPass(final Context context)
	{
		final int fileId = (Integer)ContextExtractor.extractValue(context, "fileId");
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		
		fileCheckBizImpl.checkPass(fileId,userId);
	}
	
	/**
	 * 批量审核通过
	 * @param  fileID
	 * @return 是否成功
	 */
	public void batchPass(final Context context)
	{
		final List<Integer> fileIdList= (List<Integer>) ContextExtractor.extractArray(context, "fileIdList", Integer.class);
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		
		fileCheckBizImpl.batchPass(fileIdList,userId);
	}
	
	/**
	 * 审核不通过
	 * @param  fileID
	 * @return 是否成功
	 */
	public void checkRefuse(final Context context)
	{
		final int fileId = (Integer)ContextExtractor.extractValue(context, "fileId");
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		
		fileCheckBizImpl.checkRefuse(fileId, userId);
	}
	
	/**
	 * 批量审核不通过
	 * @param  fileID
	 * @return 是否成功
	 */
	public void batchRefuse(final Context context)
	{
		final List<Integer> fileIdList= (List<Integer>) ContextExtractor.extractArray(context, "fileIdList", Integer.class);
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		
		fileCheckBizImpl.batchRefuse(fileIdList,userId);
	}
	
	/**
	 * 删除文件
	 * @param  fileID
	 * @return 是否成功
	 */
	public void deleteFile(final Context context)
	{
		final int fileId = (Integer)ContextExtractor.extractValue(context, "fileId");
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		
		fileCheckBizImpl.deleteFile(fileId, userId);
	}
	
	/**
	 * 批量删除文件
	 * @param  fileID
	 * @return 是否成功
	 */
	public void batchDelete(final Context context)
	{
		final List<Integer> fileIdList= (List<Integer>) ContextExtractor.extractArray(context, "fileIdList", Integer.class);
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		
		fileCheckBizImpl.batchDelete(fileIdList, userId);
	}
}
