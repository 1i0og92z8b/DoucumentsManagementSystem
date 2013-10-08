package com.bankcomm.novem.biz.demo;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bankcomm.novem.biz.BaseBizTest;
import com.bankcomm.novem.bo.demo.DemoBo;
import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.dao.demo.IDemoDao;
import com.bankcomm.novem.entry.demo.DemoEntry;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-8
 * 
 */
public class DemoBizImplTest extends BaseBizTest<DemoBizImpl> {

	Logger LOG = Logger.getLogger(DemoBizImplTest.class);
	
	@Autowired
	private IDemoDao demoDaoImpl;
	
	@Autowired
	private IDemoBiz demoBizImpl;

	@Test
	public void testQueryDemoList(){
		final QueryDemoBo queryBo = new QueryDemoBo();
		final PageCond pageCond = new PageCond();
		pageCond.setSTART(0);
		pageCond.setEND(15);
		queryBo.setPageCond(pageCond);
		final List<DemoBo> list = demoBizImpl.queryDemoList(queryBo);
		Assert.assertNotNull(list);
		for (final DemoBo bo:list){
			LOG.debug(bo);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testDelete() {

		final DemoEntry demoEntry = new DemoEntry();
		demoEntry.setDemoNo("demo");
		demoEntry.setDemoName("我是一个兵");
		demoDaoImpl.insert(demoEntry);

		final boolean rtn = demoDaoImpl.deleteByID(demoEntry
				.getPrimaryKey());
		Assert.assertTrue(rtn);
	}

	/**
	 * 
	 */
	@Test
	public void testInsert() {
		final DemoEntry demoEntry = new DemoEntry();
		demoEntry.setDemoNo("demo");
		demoEntry.setDemoName("我是一个兵");
		final long rtn = demoDaoImpl.insert(demoEntry);
		Assert.assertTrue(rtn >= 1);
	}

	/**
	 * 
	 */
	@Test
	public void testSelect() {
		final String demoNo = "abc";
		final List<DemoEntry> list = demoDaoImpl.queryByNo(demoNo);
		Assert.assertTrue(list.size() >= 0);
	}

	/**
	 * 
	 */
	@Test
	public void testUpdate() {
		final DemoEntry demoEntry = new DemoEntry();
		demoEntry.setDemoNo("demo");
		demoEntry.setDemoName("我是一个兵");
		demoDaoImpl.insert(demoEntry);

		demoEntry.setDemoName("我是一个兵2");

		final boolean rtn = demoDaoImpl.update(demoEntry);
		Assert.assertTrue(rtn);
	}

}
