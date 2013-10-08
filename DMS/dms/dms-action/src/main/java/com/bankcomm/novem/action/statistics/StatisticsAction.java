package com.bankcomm.novem.action.statistics;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.statistics.IStatisticsBiz;
import com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import com.bankcomm.novem.bo.statistics.UserRankingBo;
import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;

/**
 * 
 * @author 荆昊熠<jing_hy@bankcomm.com> 
 * 
 */
@Controller
public class StatisticsAction extends BaseAction{
	
	@Autowired
	private IStatisticsBiz iStatisticsBiz;
	
	public void topDownloader(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<UserRankingBo> list = iStatisticsBiz.topDownloader(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);//这里的pageCond包含总条目数
	}
	
	public void topDownloadedFile(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<DownloadedFileBo> list = iStatisticsBiz.topDownloadedFile(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);	//这里的pageCond包含总条目数	
	}
	
	public void topUploaderByFileCount(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<UserRankingBo> list = iStatisticsBiz.topUploaderByFileCount(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);//这里的pageCond包含总条目数
	}
	
	public void topUploaderByDownloadCount(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<UserRankingBo> list = iStatisticsBiz.topUploaderByDownloadCount(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);//这里的pageCond包含总条目数
	}
	
	public void recentUploadedFile(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<DownloadedFileBo> list = iStatisticsBiz.recentUploadedFile(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);	//这里的pageCond包含总条目数	
	}
	
	public void recentUploadedFileOnlySearchable(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<DownloadedFileBo> list = iStatisticsBiz.recentUploadedFileOnlySearchable(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);	//这里的pageCond包含总条目数	
	}
}
