package com.bankcomm.novem.biz.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.bo.demo.DemoBo;
import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.dao.demo.IDemoDao;
import com.bankcomm.novem.entry.demo.DemoEntry;
import com.bocom.jump.bp.service.annotation.Tx;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-8
 * 
 */
@Service
public class DemoBizImpl extends BaseBiz implements IDemoBiz {

	@Autowired
	private IDemoDao demoDaoImpl;
	
	/* (non-Javadoc)
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#queryDemoList(com.bankcomm.novem.bo.demo.QueryDemoBo)
	 */
	@Override
	public List<DemoBo> queryDemoList(final QueryDemoBo queryBo) {
		final List<DemoEntry> entryList = demoDaoImpl.queryDemoList(queryBo);
		return super.map(entryList,DemoBo.class);
	}

	/* (non-Javadoc)
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#queryDemoDetail(java.lang.Integer)
	 */
	@Override
	public DemoBo queryDemoDetail(final Integer demoId) {
		final DemoBo demoBo = super.map(demoDaoImpl.queryDemoDetail(demoId),DemoBo.class);
		return demoBo;
	}

	/* (non-Javadoc)
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#addDemo(com.bankcomm.novem.bo.demo.DemoBo)
	 */
	@Override
	public void addDemo(final DemoBo demoBo) {
		final DemoEntry entry = super.map(demoBo, DemoEntry.class);
		demoDaoImpl.addDemo(entry);
	}

	/* (non-Javadoc)
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#updateDemo(com.bankcomm.novem.bo.demo.DemoBo)
	 */
	@Override
	public void updateDemo(final DemoBo demoBo) {
		final DemoEntry tobeUpdate = super.map(demoBo, DemoEntry.class);
		demoDaoImpl.update(tobeUpdate);
	}

	/* (non-Javadoc)
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#deleteDemo(com.bankcomm.novem.bo.demo.DemoBo)
	 */
	@Override
	public void deleteDemo(final DemoBo demoBo) {
		final DemoEntry entry = super.map(demoBo, DemoEntry.class);
		demoDaoImpl.deleteDemo(entry);
	}
	
	/* (non-Javadoc)
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#deleteDemoByIdList(java.util.List)
	 */
	@Override
	public void deleteDemoByIdList(final List<Integer> demoIdList) {
		demoDaoImpl.deleteDemo(demoIdList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#deleteData(long)
	 */
	@Override
	public boolean deleteData(final long demoId) {
		return demoDaoImpl.deleteByID(demoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.biz.demo.IDemoBiz#insertData(com.bankcomm.novem.bo.demo
	 * .DemoBo)
	 */
	@Override
	@Tx
	public long insertData(final DemoBo demoBo) {
		final DemoEntry demoEntry = new DemoEntry();
		demoEntry.setDemoNo(demoBo.getDemoNo());
		demoEntry.setDemoName(demoBo.getDemoName());
		return demoDaoImpl.insert(demoEntry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.biz.demo.IDemoBiz#selectData(String)
	 */
	@Override
	public List<DemoBo> selectData(final String demoNo) {
		final List<DemoEntry> listEntry = demoDaoImpl.queryByNo(demoNo);
		final List<DemoBo> listBo = new ArrayList<DemoBo>(listEntry.size());
		for (final DemoEntry entry : listEntry) {
			final DemoBo demoBo = new DemoBo();
			demoBo.setDemoId(entry.getDemoId());
			demoBo.setDemoNo(entry.getDemoNo());
			demoBo.setDemoName(entry.getDemoName());
			listBo.add(demoBo);
		}
		return listBo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.biz.demo.IDemoBiz#updateData(com.bankcomm.novem.bo.demo
	 * .DemoBo)
	 */
	@Override
	public boolean updateData(final DemoBo demoBo) {
		final DemoEntry demoEntry = new DemoEntry();
		demoEntry.setDemoId(demoBo.getDemoId());
		demoEntry.setDemoNo(demoBo.getDemoNo());
		demoEntry.setDemoName(demoBo.getDemoName());
		return demoDaoImpl.update(demoEntry);
	}

}
