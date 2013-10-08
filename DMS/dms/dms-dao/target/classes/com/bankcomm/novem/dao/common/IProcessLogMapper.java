package com.bankcomm.novem.dao.common;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.comm.ProcessLogQueryCondition;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 19, 2012
 * 
 */
@Repository
public interface IProcessLogMapper {

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
