/**
 * 
 */
package com.bankcomm.novem.dao;

import java.util.List;

import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-11-2
 * 
 */
public interface ISimpleDao {
	/**
	 * 使用实体主键删除数据
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param entryID
	 *            实体主键
	 * @param entryClass
	 *            实体类型信息
	 * @param daoType
	 *            dao类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 是否成功删除
	 */
	<E extends BaseEntry> boolean deleteByEntryID(final long entryID,
			final Class<E> entryClass, final DaoTypeEnum daoType,
			final String dbSchema);

	/**
	 * 清空表
	 * 
	 * @param <E>
	 *            实体类型
	 * @param entryClass
	 *            实体类型信息
	 * @param daoType
	 *            dao类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 删除条数
	 */
	<E extends BaseEntry> int deleteAll(final Class<E> entryClass,
			final DaoTypeEnum daoType, final String dbSchema);

	/**
	 * 新增数据
	 * 
	 * @param entry
	 *            数据实体
	 * @param daoType
	 *            用来操作的dao类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 主键
	 */
	long insert(final BaseEntry entry, final DaoTypeEnum daoType,
			final String dbSchema);

	/**
	 * 新增列表
	 * 
	 * @param entryList
	 *            待新增数据
	 * @param daoType
	 *            dao类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 新增成功条数
	 */
	int insertListWithPK(final List<? extends BaseEntry> entryList,
			final DaoTypeEnum daoType, final String dbSchema);

	/**
	 * 带主键，新增数据
	 * 
	 * @param entry
	 *            数据实体
	 * @param daoType
	 *            用来操作的dao类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 主键
	 */
	long insertWithPK(final BaseEntry entry, final DaoTypeEnum daoType,
			final String dbSchema);

	/**
	 * 使用线索查询信息
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param entryID
	 *            实体主键
	 * @param entryClass
	 *            实体类型信息
	 * @param daoType
	 *            到类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 对应的实体数据
	 */
	<E extends BaseEntry> E queryByEntryID(final long entryID,
			final Class<E> entryClass, final DaoTypeEnum daoType,
			final String dbSchema);

	/**
	 * 使用主键更新信息
	 * 
	 * @param tobeUpdate
	 *            待更新的数据，含主键
	 * @param daoType
	 *            dao类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 是否更新成功
	 */
	boolean update(final BaseEntry tobeUpdate, final DaoTypeEnum daoType,
			final String dbSchema);

}