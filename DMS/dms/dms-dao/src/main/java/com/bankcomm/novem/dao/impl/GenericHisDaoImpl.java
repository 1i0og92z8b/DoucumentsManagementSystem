package com.bankcomm.novem.dao.impl;

import static com.bankcomm.novem.dao.Version.nextVersion;
import static com.bankcomm.novem.dao.annote.DaoTypeEnum.HISTORY;
import static com.bankcomm.novem.dao.condition.column.ConditionFactory.extractList;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.comm.SimpleEntry;
import com.bankcomm.novem.dao.BaseDao;
import com.bankcomm.novem.dao.IColumsDao;
import com.bankcomm.novem.dao.IGenericHisDao;
import com.bankcomm.novem.dao.IGenericHisMapper;
import com.bankcomm.novem.dao.ISingleTableMapper;
import com.bankcomm.novem.dao.annote.DaoType;
import com.bankcomm.novem.dao.condition.InsertArgument;
import com.bankcomm.novem.dao.condition.QueryArgument;
import com.bankcomm.novem.dao.utils.DBTransfer;
import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.VersionEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-12-13
 * 
 */
@Repository
@DaoType(type = HISTORY)
public class GenericHisDaoImpl extends BaseDao implements IGenericHisDao {
	private static final String VERSION_COLUMN = "VERSION";
	@Autowired
	private IGenericHisMapper iGenericHisMapper;
	@Autowired
	private ISingleTableMapper iSingleTableMapper;
	@Autowired
	private IColumsDao rawDao;
	@Autowired
	private EntryMetaContainer tableNameBuffer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#deleteByIDAndVersion(long,
	 * int, java.lang.Class)
	 */
	@Override
	public <E extends BaseEntry> boolean deleteByIDAndVersion(
			final long entryID, final int version, final Class<E> entryInfo,
			final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);
		final Map<String, Object> columnValues = new HashMap<String, Object>();
		columnValues.put(tableNameBuffer.extractPkName(entryInfo), entryID);
		columnValues.put(VERSION_COLUMN, version);
		return rawDao.deleteByColumns(entryInfo, columnValues, HISTORY,
				dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.dao.collateral.IHisDao#insert(com.bankcomm.novem.entry
	 * .BaseEntry )
	 */
	@Override
	public long insert(final BaseEntry entry, final String dbSchema) {
		checkEntryID(entry.getPrimaryKey());
		if (entry.getClass().isAnnotationPresent(VersionEntry.class)) {
			isTrue(entry.getVersion() > 0, "有版本信息的实体在做历史记录时版本号必须大于0");
		}
		notNull(entry, "待新增的数据不能为空");
		isTrue(entry.getPrimaryKey() > 0, "带主键自增时实体主键必须大于0");

		final List<Entry<String, Object>> columnValue = DBTransfer
				.extractColumnValue(entry, true);

		int nextVersion = 0;
		if (entry.getClass().isAnnotationPresent(VersionEntry.class)) {
			nextVersion = entry.getVersion();
		} else {
			nextVersion = nextVersion(queryLastVersion(entry.getPrimaryKey(),
					entry.getClass(), dbSchema));
		}
		final Entry<String, Object> versionColumn = new SimpleEntry<String, Object>(
				VERSION_COLUMN, String.valueOf(nextVersion));
		columnValue.add(versionColumn);

		final InsertArgument condition = new InsertArgument(
				tableNameBuffer.extractTableName(entry.getClass(), HISTORY,
						dbSchema));
		final List<String> columnNameList = new ArrayList<String>();
		final List<Object> valueList = new ArrayList<Object>();
		for (final Entry<String, Object> valueEntry : columnValue) {
			columnNameList.add(valueEntry.getKey());
			valueList.add(valueEntry.getValue());
		}

		condition.setColumnNameList(columnNameList);
		condition.setValueList(valueList);

		if (iSingleTableMapper.insertWithPK(condition) != 1) {
			return -1;
		}

		return entry.getPrimaryKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#queryByIDAndVersion(long,
	 * int, java.lang.Class)
	 */
	@Override
	public <E extends BaseEntry> E queryByIDAndVersion(final long entryID,
			final int version, final Class<E> entryInfo, final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);
		isTrue(version > 0, "版本号必须大于0");

		final Map<String, Object> columns = new HashMap<String, Object>();
		columns.put(tableNameBuffer.extractPkName(entryInfo), entryID);
		columns.put(VERSION_COLUMN, version);
		return rawDao.queryByColumns(entryInfo, columns, HISTORY, dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#queryFirstData(long,
	 * java.lang.Class)
	 */
	@Override
	public <E extends BaseEntry> E queryFirstData(final long entryID,
			final Class<E> entryInfo, final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);
		final int firstVersion = queryFirstVersion(entryID, entryInfo, dbSchema);
		if (firstVersion <= 0) {
			return null;
		}
		return queryByIDAndVersion(entryID, firstVersion, entryInfo, dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#queryFirstVersion(long,
	 * java.lang.Class)
	 */
	@Override
	public int queryFirstVersion(final long entryID,
			final Class<? extends BaseEntry> entryInfo, final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);

		final Map<String, Object> columnValues = new HashMap<String, Object>();
		columnValues.put(tableNameBuffer.extractPkName(entryInfo), entryID);
		final QueryArgument condition = new QueryArgument(
				tableNameBuffer.extractTableName(entryInfo, HISTORY, dbSchema),
				extractList(columnValues));
		final Object firstVersion = iGenericHisMapper
				.queryFirstVersion(condition);
		return firstVersion == null ? 0 : Integer.valueOf(firstVersion
				.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#queryHisByID(long,
	 * java.lang.Class)
	 */
	@Override
	public <E extends BaseEntry> List<E> queryHisByID(final long entryID,
			final Class<E> entryInfo, final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);
		final Map<String, Object> columns = new HashMap<String, Object>();
		columns.put(tableNameBuffer.extractPkName(entryInfo), entryID);
		final List<E> resultList = rawDao.queryListByColumns(entryInfo,
				columns, HISTORY, dbSchema);
		Collections.sort(resultList, new HisEntryComparator());
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#queryLastData(long,
	 * java.lang.Class)
	 */
	@Override
	public <E extends BaseEntry> E queryLastData(final long entryID,
			final Class<E> entryInfo, final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);
		final int lastVersion = queryLastVersion(entryID, entryInfo, dbSchema);
		if (lastVersion <= 0) {
			return null;
		}
		return queryByIDAndVersion(entryID, lastVersion, entryInfo, dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#queryLastVersion(long,
	 * java.lang.Class)
	 */
	@Override
	public int queryLastVersion(final long entryID,
			final Class<? extends BaseEntry> entryInfo, final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);

		final Map<String, Object> columnValues = new HashMap<String, Object>();
		columnValues.put(tableNameBuffer.extractPkName(entryInfo), entryID);
		final QueryArgument condition = new QueryArgument(
				tableNameBuffer.extractTableName(entryInfo, HISTORY, dbSchema),
				extractList(columnValues));
		final Object lastVersion = iGenericHisMapper
				.queryLastVersion(condition);
		return lastVersion == null ? 0 : Integer
				.valueOf(lastVersion.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.collateral.IHisDao#queryVersionList(long,
	 * java.lang.Class)
	 */
	@Override
	public List<Integer> queryVersionList(final long entryID,
			final Class<? extends BaseEntry> entryInfo, final String dbSchema) {
		checkEntryID(entryID);
		checkEntryInfo(entryInfo);

		final Map<String, Object> columnValues = new HashMap<String, Object>();
		columnValues.put(tableNameBuffer.extractPkName(entryInfo), entryID);
		final Set<String> resultColumnsName = new HashSet<String>();
		resultColumnsName.add(VERSION_COLUMN);
		final List<Integer> resultList = new ArrayList<Integer>();
		for (final BaseEntry entry : rawDao.queryListByColumns(entryInfo,
				columnValues, HISTORY, dbSchema, resultColumnsName)) {
			resultList.add(entry.getVersion());
		}

		Collections.sort(resultList);
		return resultList;
	}

	/**
	 * 确认主键必须大于0
	 * 
	 * @param entryID
	 *            实体主键
	 */
	private void checkEntryID(final long entryID) {
		isTrue(entryID > 0, "主键必须大于0");
	}

	/**
	 * @param entryInfo
	 */
	private void checkEntryInfo(final Class<? extends BaseEntry> entryInfo) {
		notNull(entryInfo, "实体类型信息不能为null");
	}
}