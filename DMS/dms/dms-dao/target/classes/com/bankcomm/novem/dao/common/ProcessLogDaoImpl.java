package com.bankcomm.novem.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.comm.ProcessLogQueryCondition;
import com.bankcomm.novem.dao.SingleTableDao;
import com.bankcomm.novem.entry.common.ProcessLogEntry;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 18, 2012
 * 
 */
@Repository
class ProcessLogDaoImpl extends SingleTableDao<ProcessLogEntry> implements
		IProcessLogDao {

	@Autowired
	private IProcessLogMapper processLogMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.dao.common.IProcessLogDao#queryProcessLogCount(com.
	 * bankcomm.risk.comm.operationalrisk.ProcessLogQueryCondition)
	 */
	@Override
	public int queryProcessLogCount(final ProcessLogQueryCondition condition) {
		return processLogMapper.queryProcessLogCount(condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.dao.common.IProcessLogDao#queryProcessLogIdList(com
	 * .bankcomm.risk.comm.operationalrisk.ProcessLogQueryCondition)
	 */
	@Override
	public List<Integer> queryProcessLogIdList(
			final ProcessLogQueryCondition condition) {
		Assert.notNull(condition, "查询条件不能为空");
		final int count = processLogMapper.queryProcessLogCount(condition);

		final PageCond pageCond = condition.getPageCond();
		if (pageCond != null) {
			pageCond.setCOUNT(count);
			condition.setPageCond(pageCond);
		}

		return processLogMapper.queryProcessLogIdList(condition);
	}

}
