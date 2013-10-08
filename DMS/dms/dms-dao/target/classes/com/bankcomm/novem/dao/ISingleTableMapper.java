package com.bankcomm.novem.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.dao.condition.DeleteArgument;
import com.bankcomm.novem.dao.condition.InsertArgument;
import com.bankcomm.novem.dao.condition.InsertListArgument;
import com.bankcomm.novem.dao.condition.QueryArgument;
import com.bankcomm.novem.dao.condition.QuerySumArgument;
import com.bankcomm.novem.dao.condition.UpdateArgument;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-14
 * 
 */
@Repository
public interface ISingleTableMapper {
	/**
	 * 新增列表
	 * 
	 * @param insertListArgument
	 *            新增参数
	 * @return 新增的条数
	 */
	int insertList(final InsertListArgument insertListArgument);

	/**
	 * 删除
	 * 
	 * @param deleteArgument
	 *            删除参数
	 * @return 删除条数
	 */
	int delete(final DeleteArgument deleteArgument);

	/**
	 * 清空表
	 * 
	 * @param deleteArgument
	 *            删除参数
	 * @return 删除条数
	 */
	int deleteAll(final DeleteArgument deleteArgument);

	/**
	 * 插入不含主键（主键自增）
	 * 
	 * @param insertArgument
	 *            插入参数
	 * @return 插入条数
	 */
	int insert(final InsertArgument insertArgument);

	/**
	 * 插入含主键
	 * 
	 * @param insertArgument
	 *            插入参数
	 * @return 插入条数
	 */
	int insertWithPK(final InsertArgument insertArgument);

	/**
	 * 查询条数
	 * 
	 * @param queryArgument
	 *            查询参数
	 * @return 总条数
	 */
	int queryCount(final QueryArgument queryArgument);

	/**
	 * 查询结果集
	 * 
	 * @param queryArgument
	 *            查询参数
	 * @return 结果集
	 */
	List<Map<String, Object>> queryForList(final QueryArgument queryArgument);

	/**
	 * 根据条件查询表单列求和
	 * 
	 * @param queryArgument
	 *            查询参数
	 * @return 符合条件的列之和
	 */
	double querySum(final QuerySumArgument queryArgument);

	/**
	 * 更新
	 * 
	 * @param updateArgument
	 *            更新参数
	 * @return 更新条数
	 */
	int update(final UpdateArgument updateArgument);
}
