package com.bankcomm.novem.dao;

import java.util.List;

import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-4-5
 * 
 */
public interface IGenericHisDao {
	/**
	 * 使用主键和版本号删除信息
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param entryID
	 *            实体主键
	 * @param version
	 *            实体版本号
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 该实体该版本的信息
	 */
	<E extends BaseEntry> boolean deleteByIDAndVersion(final long entryID,
			final int version, final Class<E> entryInfo, final String dbSchema);

	/**
	 * @param entry
	 *            待新增实体
	 * @param dbSchema
	 *            数据库schema
	 * @return 主键值
	 */
	long insert(final BaseEntry entry, final String dbSchema);

	/**
	 * 使用主键和版本号查询信息
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param entryID
	 *            实体主键
	 * @param version
	 *            实体版本号
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 该实体该版本的信息
	 */
	<E extends BaseEntry> E queryByIDAndVersion(final long entryID,
			final int version, final Class<E> entryInfo, final String dbSchema);

	/**
	 * 查询最小版本的数据
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param entryID
	 *            实体主键
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 最小版本数据
	 */
	<E extends BaseEntry> E queryFirstData(final long entryID,
			final Class<E> entryInfo, final String dbSchema);

	/**
	 * 查询最小版本值
	 * 
	 * @param entryID
	 *            实体主键
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 该实体的最小版本
	 */
	int queryFirstVersion(final long entryID,
			final Class<? extends BaseEntry> entryInfo, final String dbSchema);

	/**
	 * 查询数据历史列表
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param entryID
	 *            数据主键
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 该数据主键所有数据列表
	 */
	<E extends BaseEntry> List<E> queryHisByID(final long entryID,
			final Class<E> entryInfo, final String dbSchema);

	/**
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param entryID
	 *            实体主键
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 最新版本数据
	 */
	<E extends BaseEntry> E queryLastData(final long entryID,
			final Class<E> entryInfo, final String dbSchema);

	/**
	 * 查询最新的版本值
	 * 
	 * @param entryID
	 *            实体主键
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 该实体的最新版本值
	 */
	int queryLastVersion(final long entryID,
			final Class<? extends BaseEntry> entryInfo, final String dbSchema);

	/**
	 * 查询版本列表,升序排列
	 * 
	 * @param entryID
	 *            实体主键
	 * @param entryInfo
	 *            实体类型信息
	 * @param dbSchema
	 *            数据库schema
	 * @return 该实体的数据版本列表
	 */
	List<Integer> queryVersionList(final long entryID,
			final Class<? extends BaseEntry> entryInfo, final String dbSchema);
}
