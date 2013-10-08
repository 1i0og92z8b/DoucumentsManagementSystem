package com.bankcomm.novem.action.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.demo.IDemoBiz;
import com.bankcomm.novem.bo.demo.DemoBo;
import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.service.annotation.Tx;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-8
 * 
 */
@Controller
public class DemoAction extends BaseAction {
	/**
	 * 
	 */
	private static final String DEMO_ID = "demoid";
	/**
	 * 
	 */
	private static final String REPLY_MSG = "replyMsg";
	
	@Autowired
	private IDemoBiz demoBizImpl;
	
	/**
	 * 查询项目列表
	 * @param context
	 *            上下文
	 */
	public void queryDemoList(final Context context) {
		QueryDemoBo queryBo = ContextExtractor.extractBean(context, "PARAMS", QueryDemoBo.class);
		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(queryBo == null){
			queryBo = new QueryDemoBo();
		}
		queryBo.setPageCond(pageCond);
		final List<DemoBo> list = demoBizImpl.queryDemoList(queryBo);
		context.setData("list", list);
		context.setData("PAGE_COND", queryBo.getPageCond());
		
	}
	
	/**
	 * 查询项目明细
	 * @param context
	 *            上下文
	 */
	public void queryDemoDetail(final Context context) {
		final Integer demoId = (Integer)ContextExtractor.extractValue(context, "demoId");
		final DemoBo demoBo = demoBizImpl.queryDemoDetail(demoId);
		context.setData("demoBo",demoBo);
	}
	
	/**
	 * 新增项目
	 * @param context
	 *            上下文
	 */
	public void addDemo(final Context context) {
		final DemoBo demoBo = ContextExtractor.extractBean(context, "demoBo", DemoBo.class);
		demoBizImpl.addDemo(demoBo);
	}
	
	/**
	 * 修改项目
	 * @param context
	 *            上下文
	 */
	public void updateDemo(final Context context) {
		final DemoBo demoBo = ContextExtractor.extractBean(context, "demoBo", DemoBo.class);
		demoBizImpl.updateDemo(demoBo);
	}
	
	/**
	 * 删除项目
	 * @param context
	 *            上下文
	 */
	public void deleteDemo(final Context context) {
		final List<Integer> demoIdList= (List<Integer>) ContextExtractor.extractArray(context, "demoIdList", Integer.class);
		demoBizImpl.deleteDemoByIdList(demoIdList);
	}

	/**
	 * @param context
	 *            上下文
	 */
	public void deleteData(final Context context) {
		final boolean rtn = demoBizImpl.deleteData(ContextExtractor
				.extractLongValue(context, DEMO_ID));
		if (rtn) {
			context.setData(REPLY_MSG, "删除成功");
		} else {
			context.setData(REPLY_MSG, "删除失败");
		}
	}

	/**
	 * @param context
	 *            上下文
	 */
	@Tx
	public void insertData(final Context context) {
		final Map<String, Object> map = context.getDataMap();
		final DemoBo demoBo = new DemoBo();
		demoBo.setDemoNo((String) map.get("demono"));
		demoBo.setDemoName((String) map.get("demoname"));
		final long rtn = demoBizImpl.insertData(demoBo);
		if (rtn > 0) {
			context.setData(REPLY_MSG, "新增成功");
		} else {
			context.setData(REPLY_MSG, "新增失败");
		}
	}

	/**
	 * @param context
	 *            上下文
	 */
	public void receiveMsg(final Context context) {
		final Map<String, Object> map = context.getDataMap();
		final String msg = (String) map.get("msg");
		context.setData(
				REPLY_MSG,
				((null != msg) && !"".equals(msg)) ? "Glad to revieve from you!"
						: "OH NO!");
	}

	/**
	 * @param context
	 *            上下文
	 */
	public void selectData(final Context context) {
		final Map<String, Object> map = context.getDataMap();
		final String demoNo = (String) map.get("demono");
		final List<DemoBo> list = demoBizImpl.selectData(demoNo);
		context.setData("list", list);
	}

	/**
	 * @param context
	 *            上下文
	 */
	public void updateData(final Context context) {
		final DemoBo demoBo = mapper.map(context.getDataMap(), DemoBo.class);
		final boolean rtn = demoBizImpl.updateData(demoBo);
		if (rtn) {
			context.setData(REPLY_MSG, "更新成功");
		} else {
			context.setData(REPLY_MSG, "更新失败");
		}
	}
	
}
