package com.bankcomm.novem.action.home;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.search.IFileSearchBiz;
import com.bankcomm.novem.biz.statistics.IStatisticsBiz;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;

@Controller
public class HomeAction extends BaseAction {
	@Autowired
	private IStatisticsBiz statisticsBizImpl;
	@Autowired
	private IFileSearchBiz fileSearchBizImpl;
	
	/**
	 * 根据文件状态查看回收站中文件
	 * @param context
	 *            上下文
	 */
	public void topDownLoadedFile(final Context context) {
			
//		final PageCond pageCond = ContextExtractor.extractPageCond(context);
//		PageCond item = ContextExtractor.extractBean(context, "PARAMS", PageCond.class);
		
		final int topParameter = (Integer)ContextExtractor.extractValue(context, "topParameter");
		
		final PageCond pageCond = new PageCond();
		pageCond.setLIMIT(topParameter);
		
		List<DownloadedFileBo> downloadedFileList = statisticsBizImpl.topDownloadedFile(pageCond);
		
		context.setData("downloadedFileList", downloadedFileList);	
	//	context.setData("PAGE_COND", pageCond);
	}
	
	public void topSearchableDownLoadedFile(final Context context) {
		
//		final PageCond pageCond = ContextExtractor.extractPageCond(context);
//		PageCond item = ContextExtractor.extractBean(context, "PARAMS", PageCond.class);
		
		final int topParameter = (Integer)ContextExtractor.extractValue(context, "topParameter");
		
		final PageCond pageCond = new PageCond();
		pageCond.setLIMIT(topParameter);
		
		List<DownloadedFileBo> downloadedFileList = statisticsBizImpl.topDownloadedFileOnlySearchable(pageCond);
		
		context.setData("downloadedFileList", downloadedFileList);	
	//	context.setData("PAGE_COND", pageCond);
	}
	
	public void recentUploadedFile(final Context context) {
		
//		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		final int recentFileParameter = (Integer)ContextExtractor.extractValue(context, "recentFileParameter");
		
		final PageCond pageCond = new PageCond();
		pageCond.setLIMIT(recentFileParameter);
		
		List<DownloadedFileBo> recentFileList = statisticsBizImpl.recentUploadedFile(pageCond);
		
		context.setData("recentFileList", recentFileList);	
//		context.setData("PAGE_COND", pageCond);
	}
	
	public void recentSearchableUploadedFile(final Context context) {
		
//		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		final int recentFileParameter = (Integer)ContextExtractor.extractValue(context, "recentFileParameter");
		
		final PageCond pageCond = new PageCond();
		pageCond.setLIMIT(recentFileParameter);
		
		List<DownloadedFileBo> recentFileList = statisticsBizImpl.recentUploadedFileOnlySearchable(pageCond);
		
		context.setData("recentFileList", recentFileList);	
//		context.setData("PAGE_COND", pageCond);
	}
}
