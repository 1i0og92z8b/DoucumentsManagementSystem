package com.bankcomm.novem.dao.demo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.dao.SingleTableDao;
import com.bankcomm.novem.dao.annote.DbSchemaType;
import com.bankcomm.novem.entry.demo.DemoEntry;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-14
 * 
 */
@DbSchemaType("db2idms")
@Repository
class DemoDaoImpl extends SingleTableDao<DemoEntry> implements IDemoDao {

	final static Logger LOG = Logger.getLogger(DemoDaoImpl.class);
	@Autowired
	private IDemoMapper iDemoMapper;
	
	/* (non-Javadoc)
	 * @see com.bankcomm.novem.dao.demo.IDemoDao#queryDemoList(com.bankcomm.novem.bo.demo.QueryDemoBo, com.bankcomm.novem.comm.PageCond)
	 */
	@Override
	public List<DemoEntry> queryDemoList(final QueryDemoBo queryBo) {

		final PageCond pageCond = queryBo.getPageCond();
		if (pageCond != null) {
			final int count = iDemoMapper.queryDemoCount(queryBo);
			pageCond.setCOUNT(count);
			queryBo.setPageCond(pageCond);
		}

		return iDemoMapper.queryDemoList(queryBo);
	}
	
	/* (non-Javadoc)
	 * @see com.bankcomm.novem.dao.demo.IDemoDao#queryDemoDetail(java.lang.Integer)
	 */
	@Override
	public DemoEntry queryDemoDetail(final Integer demoId) {
		final DemoEntry entry = queryByID(demoId);
		return entry;
	}
	
	/* (non-Javadoc)
	 * @see com.bankcomm.novem.dao.demo.IDemoDao#addDemo(com.bankcomm.novem.entry.demo.DemoEntry)
	 */
	@Override
	public Integer addDemo(final DemoEntry entry) {
		entry.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entry.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		entry.setModifyUser(1);
		Assert.assertNotNull(entry.getCreateTime());
		LOG.debug(entry.getCreateTime());
		final long demoId = super.insert(entry);
		return Integer.valueOf(new Long(demoId).toString());
	}

	/* (non-Javadoc)
	 * @see com.bankcomm.novem.dao.demo.IDemoDao#updateDemo(com.bankcomm.novem.entry.demo.DemoEntry)
	 */
	@Override
	public Boolean updateDemo(final DemoEntry entry) {
		entry.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return super.update(entry);
	}

	/* (non-Javadoc)
	 * @see com.bankcomm.novem.dao.demo.IDemoDao#deleteDemo(com.bankcomm.novem.entry.demo.DemoEntry)
	 */
	@Override
	public Boolean deleteDemo(final DemoEntry entry) {
		return super.deleteByID(entry.getDemoId());
	}
	
	@Override
	public Boolean deleteDemo(final List<Integer> demoIdList) {
//		final Map<String,List<Integer>> map = new HashMap<String,List<Integer>>();
//		map.put("demoIdList", demoIdList);
//		return iDemoMapper.deleteDemo(map);
		return iDemoMapper.deleteDemo(demoIdList);
	}

	@Override
	public List<DemoEntry> queryByName(final String demoName) {
		return iDemoMapper.queryByName(demoName);
	}

	@Override
	public List<DemoEntry> queryByNo(final String demoNo) {
		final Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("DEMO_NO", demoNo);
		System.out.print("dd");
		return queryListByColumns(condition);
	}

}
