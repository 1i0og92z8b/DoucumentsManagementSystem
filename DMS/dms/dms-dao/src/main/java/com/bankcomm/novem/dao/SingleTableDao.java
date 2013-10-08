/**
 * 
 */
package com.bankcomm.novem.dao;

import static com.bankcomm.novem.dao.condition.column.ConditionFactory.extractList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bankcomm.novem.comm.spring.ManageSpringBeans;
import com.bankcomm.novem.dao.condition.QueryArgument;
import com.bankcomm.novem.dao.condition.QuerySumArgument;
import com.bankcomm.novem.dao.condition.column.IColumnCondition;
import com.bankcomm.novem.dao.impl.EntryMetaContainer;
import com.bankcomm.novem.dao.impl.IRawCopyDao;
import com.bankcomm.novem.dao.utils.DBTransfer;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * 单表操作类，可以通过继承该类获取单表的多个通用操作方法，一般情况下，可以直接通过biz层访问public方法，
 * protected方法需要通过子类的包装再对外提供服务
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-9-28
 * @param <T>
 *            操作实体
 * 
 */
public abstract class SingleTableDao<T extends BaseEntry> extends BaseDao
		implements ISingleTableDao<T> {
	private TableMeta<T> meta;

	private EntryMetaContainer metaContainer;
	private IColumsDao rawDao;
	private ISingleTableMapper singleTableMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.ISingleTableDao#deleteByID(long)
	 */
	@Override
	public boolean deleteByID(final long entryID) {
		return getRawDao().deleteByEntryID(entryID,
				getMeta().getEntryClassInfo(), getMeta().getDaoType(),
				getMeta().getDbSchema());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.ISingleTableDao#getCondition()
	 */
	@Override
	public Class<T> getEntryInfo() {
		return getMeta().getEntryClassInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.ISingleTableDao#insert(com.bocom.egif.entry.BaseEntry)
	 */
	@Override
	public long insert(final T entry) {
		return getRawDao().insert(entry, getMeta().getDaoType(),
				getMeta().getDbSchema());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.ISingleTableDao#queryById(long)
	 */
	@Override
	public T queryByID(final long entryID) {
		return getRawDao().queryByEntryID(entryID,
				getMeta().getEntryClassInfo(), getMeta().getDaoType(),
				getMeta().getDbSchema());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.ISingleTableDao#updateEntry(com.bocom.egif.entry.BaseEntry
	 * )
	 */
	@Override
	public boolean update(final T tobeUpdate) {
		return getRawDao().update(tobeUpdate, getMeta().getDaoType(),
				getMeta().getDbSchema());
	}

	/***
	 * 为降低初始化时间，首次使用时才初始化
	 * 
	 * @return {@link #iSingleTableMapper}
	 */
	private ISingleTableMapper getMapper() {
		if (singleTableMapper == null) {
			singleTableMapper = ManageSpringBeans
					.getBean(ISingleTableMapper.class);
		}
		return singleTableMapper;
	}

	/**
	 * 为降低初始化时间，首次使用时才初始化
	 * 
	 * @return {@link #meta}
	 */
	private TableMeta<T> getMeta() {
		if (meta == null) {
			@SuppressWarnings("unchecked")
			final Class<? extends SingleTableDao<T>> daoClass = (Class<? extends SingleTableDao<T>>) this
					.getClass();
			meta = TableMeta.extractMeta(daoClass);
		}
		return meta;
	}

	/***
	 * 为降低初始化时间，首次使用时才初始化
	 * 
	 * @return {@link #rawDao}
	 */
	private IColumsDao getRawDao() {
		if (rawDao == null) {
			rawDao = ManageSpringBeans.getBean(IColumsDao.class);
		}
		return rawDao;
	}

	/***
	 * 为降低初始化时间，首次使用时才初始化
	 * 
	 * @return {@link #tableNameContainer}
	 */
	private EntryMetaContainer getTableNameContainer() {
		if (metaContainer == null) {
			metaContainer = ManageSpringBeans.getBean(EntryMetaContainer.class);
		}
		return metaContainer;
	}

	/**
	 * 数据copy操作，不记录历史
	 * 
	 * @param entry
	 *            待copy的数据
	 * 
	 * @return 主键
	 */
	protected long copyWithoutHistory(final T entry) {
		return ManageSpringBeans.getBean(IRawCopyDao.class).copyWithoutHistory(
				entry, getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 删除所有数据
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @return 删除数目
	 */
	protected int deleteAll() {
		return getRawDao().deleteAll(getMeta().getEntryClassInfo(),
				getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 使用列名值映射删除数据
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValue
	 *            列名值映射
	 * @return 删除条数
	 */
	protected int deleteByColumn(final Map<String, Object> columnValue) {
		return getRawDao().deleteByColumn(getMeta().getEntryClassInfo(),
				columnValue, getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 使用列名值映射删除数据
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValues
	 *            列名值映射
	 * @return 是否删除成功
	 */
	protected boolean deleteByColumns(final Map<String, Object> columnValues) {
		return getRawDao().deleteByColumns(getMeta().getEntryClassInfo(),
				columnValues, getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * @return 标准的sql mapper命名空间
	 */
	protected String extractStandardNameSpace() {
		return this.getClass().getName() + ".";
	}

	/**
	 * 新增列表
	 * 
	 * @param entryList
	 *            待新增数据
	 * @return 新增条数
	 */
	protected int insertListWithPK(final List<T> entryList) {
		return getRawDao().insertListWithPK(entryList, getMeta().getDaoType(),
				getMeta().getDbSchema());
	}

	/**
	 * 新增数据带主键
	 * 
	 * @param entry
	 *            待新增的数据实体
	 * 
	 * @return 主键
	 */
	protected long insertWithPk(final T entry) {
		return getRawDao().insertWithPK(entry, getMeta().getDaoType(),
				getMeta().getDbSchema());
	}

	/**
	 * 查询表内所有值
	 * 
	 * @return 对应的实体列表
	 */
	protected List<T> queryAll() {
		final QueryArgument condition = new QueryArgument(getMeta()
				.getTableName(), new ArrayList<IColumnCondition>());

		final List<Map<String, Object>> resultMap = getMapper().queryForList(
				condition);

		final List<T> resultList = new ArrayList<T>();
		for (final Map<String, Object> map : resultMap) {
			resultList.add(DBTransfer.transferToEntry(map, getMeta()
					.getEntryClassInfo()));
		}

		return resultList;
	}

	/**
	 * 查询表内数据数量
	 * 
	 * @return 总数
	 */
	protected int queryAllCount() {
		final QueryArgument condition = new QueryArgument(getMeta()
				.getTableName(), new ArrayList<IColumnCondition>());

		return getMapper().queryCount(condition);
	}

	/**
	 * 使用列名和值查询实体，原则上该方法不允许直接暴露给biz层使用
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValues
	 *            列值
	 * @return 对应的实体，如果不存在返回null
	 */
	protected T queryByColumns(final Map<String, Object> columnValues) {
		return getRawDao().queryByColumns(getMeta().getEntryClassInfo(),
				columnValues, getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 根据条件查询表内数据数量
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValues
	 *            条件
	 * @return 符合条件的数据数量
	 */
	protected int queryCountByColumns(final Map<String, Object> columnValues) {
		final QueryArgument condition = new QueryArgument(
				getTableNameContainer().extractTableName(
						getMeta().getEntryClassInfo(), getMeta().getDaoType(),
						getMeta().getDbSchema()), extractList(columnValues));

		return getMapper().queryCount(condition);
	}

	/**
	 * 查询对应于columnValues的对应数据实体主键
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValues
	 *            查询线索
	 * @return 对应的数据实体主键
	 */
	protected long queryEntryIdByColumns(final Map<String, Object> columnValues) {
		return getRawDao().queryEntryIdByColumns(getMeta().getEntryClassInfo(),
				columnValues, getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 查询对应于columnValues的对应数据实体主键列表
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * 类型信息
	 * 
	 * @param columnValues
	 *            查询线索
	 * @return 对应的数据实体主键
	 */
	protected List<Long> queryEntryIdListByColumns(
			final Map<String, Object> columnValues) {
		return getRawDao().queryEntryIdListByColumns(
				getMeta().getEntryClassInfo(), columnValues,
				getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 使用列名和值查询实体列表
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValues
	 *            列名和值映射，当值为列表时，采用in做为操作符
	 * @return 对应的实体列表，如果不存在返回null
	 */
	protected List<T> queryListByColumns(final Map<String, Object> columnValues) {
		return getRawDao().queryListByColumns(getMeta().getEntryClassInfo(),
				columnValues, getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 按照指定列名，使用列名和值查询实体列表
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValues
	 *            列名和值映射，当值为列表时，采用in做为操作符
	 * @param resultColumnsName
	 *            指定的返回列
	 * @return 对应的实体列表，如果不存在返回空列表
	 */
	protected List<T> queryListByColumns(
			final Map<String, Object> columnValues,
			final Set<String> resultColumnsName) {
		return getRawDao().queryListByColumns(getMeta().getEntryClassInfo(),
				columnValues, getMeta().getDaoType(), getMeta().getDbSchema(),
				resultColumnsName);
	}

	/**
	 * 根据条件查询表单列求和
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param columnValues
	 *            条件
	 * @param sumColumnName
	 *            求和列
	 * @return 符合条件的列之和
	 */
	protected double querySumByColumns(final Map<String, Object> columnValues,
			final String sumColumnName) {
		final QuerySumArgument condition = new QuerySumArgument(
				getTableNameContainer().extractTableName(
						getMeta().getEntryClassInfo(), getMeta().getDaoType(),
						getMeta().getDbSchema()), extractList(columnValues),
				sumColumnName);

		return getMapper().querySum(condition);
	}

	/**
	 * 
	 * 将通过手写sql,查询出来的map列表类型转换成实体类型list
	 * 
	 * @param sourceMap
	 *            要转换的list
	 * @return 指定entry类型的list
	 */
	protected List<T> transferMapToEntryList(
			final List<Map<String, Object>> sourceMap) {
		final List<T> resultList = new ArrayList<T>();
		for (final Map<String, Object> map : sourceMap) {
			resultList.add(DBTransfer.transferToEntry(map, getMeta()
					.getEntryClassInfo()));
		}
		return resultList;
	}

	/**
	 * 
	 * 将通过手写sql,查询出来的map列表类型转换成实体类型list
	 * 
	 * @param <E>
	 *            实体类型
	 * 
	 * @param sourceMap
	 *            要转换的list
	 * @param classInfo
	 *            目标类型
	 * @return 指定entry类型的list
	 */
	protected <E extends BaseEntry> List<E> transferMapToEntryList(
			final List<Map<String, Object>> sourceMap, final Class<E> classInfo) {
		final List<E> resultList = new ArrayList<E>();
		for (final Map<String, Object> map : sourceMap) {
			resultList.add(DBTransfer.transferToEntry(map, classInfo));
		}
		return resultList;
	}

	/**
	 * 使用条件更新数据
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param tobeUpdate
	 *            待更新数据
	 * @param columnValues
	 *            条件列
	 * @return 是否更新成功
	 */
	protected boolean updateByColumns(final T tobeUpdate,
			final Map<String, Object> columnValues) {
		return getRawDao().updateByColumns(tobeUpdate, columnValues,
				getMeta().getDaoType(), getMeta().getDbSchema());
	}

	/**
	 * 按照主键更新制定列值
	 * 
	 * 对biz层直接暴露该接口需要说明为什么一定要使用该接口
	 * 
	 * @param tobeUpdate
	 *            待更新的数据
	 * @param columnNameSet
	 *            列名集合
	 * @return 是否更新成功
	 */
	protected boolean updateColumns(final T tobeUpdate,
			final Set<String> columnNameSet) {
		return getRawDao().updateColumns(tobeUpdate, columnNameSet,
				getMeta().getDaoType(), getMeta().getDbSchema());
	}

}