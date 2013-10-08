/**
 * 
 */
package com.bankcomm.novem.dao.annote;

import java.util.EnumMap;

import lombok.Data;
import lombok.Delegate;

import com.bankcomm.novem.dao.ISingleTableDao;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-9-9
 * 
 */
@Data
public class SingleTableCluster {
	/**
	 * 一个简单的mapper访问接口
	 * 
	 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-9-9
	 * 
	 */
	static interface SimpleMap {
		/**
		 * 使用dao类型获取对应的单表操作实例
		 * 
		 * @param daoType
		 *            dao类型
		 * @return 单表操作实例
		 */
		ISingleTableDao<? extends BaseEntry> get(final DaoTypeEnum daoType);

		/**
		 * 添加dao实例
		 * 
		 * @param key
		 *            dao类型
		 * @param value
		 *            实例
		 * @return 添加好的dao实例
		 */
		ISingleTableDao<? extends BaseEntry> put(final DaoTypeEnum key,
				final ISingleTableDao<? extends BaseEntry> value);

		/**
		 * 是否包含该类型的dao实例
		 * 
		 * @param key
		 *            dao类型
		 * @return true-包含该类型的实例
		 */
		boolean containsKey(final DaoTypeEnum key);

		/**
		 * 移除某个key
		 * 
		 * @param key
		 *            待移除的key
		 * @return 被移除的值
		 */
		ISingleTableDao<? extends BaseEntry> remove(DaoTypeEnum key);
	}

	/**  */
	@Delegate(types = SimpleMap.class)
	final private EnumMap<DaoTypeEnum, ISingleTableDao<? extends BaseEntry>> typeMap = new EnumMap<DaoTypeEnum, ISingleTableDao<? extends BaseEntry>>(
			DaoTypeEnum.class);
}