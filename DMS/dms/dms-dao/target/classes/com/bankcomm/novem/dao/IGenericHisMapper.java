package com.bankcomm.novem.dao;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.comm.history.QueryHistory;
import com.bankcomm.novem.dao.condition.QueryArgument;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-4-5
 * 
 */
@Repository
public interface IGenericHisMapper {

	/**
	 * 查询最小版本号
	 * 
	 * @param queryArgument
	 *            查询参数
	 * @return 版本号
	 */
	Object queryFirstVersion(final QueryArgument queryArgument);

	/**
	 * 查询最大版本号
	 * 
	 * @param queryArgument
	 *            查询参数
	 * @return 版本号
	 */
	Object queryLastVersion(final QueryArgument queryArgument);

	/**
	 * 更新历史
	 * 
	 * @param queryHistory
	 *            查询历史参数
	 * @return 更新条数
	 */
	int updateHistory(final QueryHistory queryHistory);

}
