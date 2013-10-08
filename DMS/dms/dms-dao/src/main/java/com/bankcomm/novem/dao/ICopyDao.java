/**
 * 
 */
package com.bankcomm.novem.dao;

import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-12-29
 * @param <T>
 *            副本数据操作类型
 * 
 */
public interface ICopyDao<T extends BaseEntry> extends ISingleTableDao<T> {

	/**
	 * 从正本复制数据到副本，需要带主键，不带历史操作
	 * 
	 * @param entry
	 *            待复制的数据实体
	 * 
	 * @return 主键
	 */
	long copy(final T entry);

}