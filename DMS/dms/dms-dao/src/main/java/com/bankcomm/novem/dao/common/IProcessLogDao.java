package com.bankcomm.novem.dao.common;

import java.util.List;

import com.bankcomm.novem.comm.ProcessLogQueryCondition;
import com.bankcomm.novem.dao.ISingleTableDao;
import com.bankcomm.novem.entry.common.ProcessLogEntry;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 18, 2012
 * 
 */
public interface IProcessLogDao extends ISingleTableDao<ProcessLogEntry> {

	/**
	 * 查询操作实体
	 * 
	 * @param condition
	 *            参数对象
	 * @return 操作实体集合
	 */
	List<Integer> queryProcessLogIdList(final ProcessLogQueryCondition condition);

	/**
	 * 查询操作实体数目
	 * 
	 * @param condition
	 *            参数对象
	 * @return 操作实体数目
	 */
	int queryProcessLogCount(final ProcessLogQueryCondition condition);

}
