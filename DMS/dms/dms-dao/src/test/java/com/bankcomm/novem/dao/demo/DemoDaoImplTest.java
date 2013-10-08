package com.bankcomm.novem.dao.demo;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.dao.SingleTableDaoTest;
import com.bankcomm.novem.entry.demo.DemoEntry;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-14
 * 
 */
public class DemoDaoImplTest extends SingleTableDaoTest<DemoEntry> {
	
	Logger LOG = Logger.getLogger(DemoDaoImplTest.class);

	@Autowired
	private DemoDaoImpl demoDaoImpl;
	
	@Ignore
	@Test
	public void testQueryDemoList(){
		final QueryDemoBo queryBo = new QueryDemoBo();
		final PageCond pageCond = new PageCond();
		pageCond.setSTART(0);
		pageCond.setEND(15);
		queryBo.setPageCond(pageCond);
		final List<DemoEntry> list = demoDaoImpl.queryDemoList(queryBo);
		Assert.assertNotNull(list);
		for (final DemoEntry entry:list){
			LOG.debug(entry);
		}
	}
	
	@Ignore
	@Test
	public void testQueryDemoDetail(){
		DemoEntry entry;
		final Integer demoId = 1;
		entry = demoDaoImpl.queryDemoDetail(demoId);
		Assert.assertNotNull(entry);
		LOG.debug(entry);
	}
	
	@Override
	@Rollback(false)
	public void testInsertWithCreateTime(){
		super.testInsertWithCreateTime();
	}
	
//	@Ignore
	@Test
	@Rollback(false)
	public void testAddDemo(){
		final DemoEntry entry = new DemoEntry();
		entry.setDemoId(4);
		entry.setDemoName("QW");
		entry.setDemoNo("21");
		final Integer demoId = demoDaoImpl.addDemo(entry);
		Assert.assertNotNull(demoId);
		LOG.debug("demoId:" + demoId);
	}
	
	@Test
	@Rollback(false)
	public void testUpdateDemo(){
		final DemoEntry entry = new DemoEntry();
		entry.setDemoId(2);
		entry.setDemoName("ASA");
		entry.setDemoNo("211");
		final Boolean rs = demoDaoImpl.update(entry);
		Assert.assertTrue(rs);
		LOG.debug("rs:" + rs);
	}
	
	@Test
//	@Rollback(false)
	public void testDeleteDemo(){
		final DemoEntry entry = new DemoEntry();
		entry.setDemoId(2);
		final Boolean rs = demoDaoImpl.deleteDemo(entry);
		Assert.assertTrue(rs);
		LOG.debug("rs:" + rs);
	}
	
	@Test
//	@Rollback(false)
	public void testDeleteDemoByIdList(){
		final Integer[] arr = {5,6};
		final List<Integer> demoIdList = Arrays.asList(arr);
		final Boolean rs = demoDaoImpl.deleteDemo(demoIdList);
		Assert.assertTrue(rs);
		LOG.debug("rs:" + rs);
	}

	/**
	 * 
	 */
	@Ignore
	@Test(timeout = 100000)
	public void testEfficienceOfInsert() {
		for (int i = 0; i < 10000; i++) {
			insert(createMinEntry());
		}
	}

	/**
	 * 
	 */
	@Ignore
	@Test(timeout = 100000)
	@Rollback(false)
	public void testEfficienceOfInsertWithoutRollBack() {
		for (int i = 0; i < 10000; i++) {
			insert(createMinEntry());
		}
	}

	/**
	 * 
	 */
	@Ignore
	@Test(timeout = 100000)
	public void testEfficienceOfUpdate() {
		final long id = insert(createMinEntry());
		final DemoEntry toBeUpdate = new DemoEntry();
		toBeUpdate.setDemoId(id);
		for (int i = 0; i < 10000; i++) {
			toBeUpdate.setDemoNo(String.valueOf(i));
			update(toBeUpdate);
		}
	}

	/**
	 * 
	 */
	@Ignore
	@Test(timeout = 100000)
	@Rollback(false)
	public void testEfficienceOfUpdateWithoutRollBack() {
		final long id = insert(createMinEntry());
		final DemoEntry toBeUpdate = new DemoEntry();
		toBeUpdate.setDemoId(id);
		for (int i = 0; i < 10000; i++) {
			toBeUpdate.setDemoNo(String.valueOf(i));
			update(toBeUpdate);
		}
	}

	/**
	 * 
	 */
	@Test
	public void testQueryByName() {
		final String demoName = "zhang";
		final List<DemoEntry> list = demoDaoImpl.queryByName(demoName);
		Assert.assertTrue(list.size() >= 0);
		for(final DemoEntry entry:list){
			LOG.debug(entry);
			LOG.debug(entry.getDemoNo());
			
		}
	}

	/**
	 * 
	 */
	@Test
	public void testQueryByNo() {
		final String demoNo = "No13";
		final List<DemoEntry> list = demoDaoImpl.queryByNo(demoNo);
		Assert.assertTrue(list.size() >= 0);
	}
}
