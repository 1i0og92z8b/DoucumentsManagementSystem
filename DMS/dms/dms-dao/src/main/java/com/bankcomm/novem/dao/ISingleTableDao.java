/**
 * 
 */
package com.bankcomm.novem.dao;

import com.bankcomm.novem.entry.BaseEntry;

/**
 * 单表操作接口
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-9-28
 * 
 * @param <T>
 *            实体类型
 */
public interface ISingleTableDao<T extends BaseEntry> extends IGenericDao<T> {

	/**
	 * 使用主键删除实体
	 * 
	 * @param entryID
	 *            实体主键
	 * @return 是否成功
	 */
	boolean deleteByID(final long entryID);

	/**
	 * 新增数据
	 * 
	 * @param entry
	 *            待新增的数据实体
	 * 
	 * @return 主键
	 */
	long insert(final T entry);

	/**
	 * 使用主键查询实体
	 * 
	 * @param entryID
	 *            实体主键
	 * @return 对应的实体，如果不存在返回null
	 */
	T queryByID(final long entryID);

	/**
	 * 更新数据
	 * 
	 * @param tobeUpdate
	 *            待更新数据
	 * @return 是否成功
	 */
	boolean update(final T tobeUpdate);
}