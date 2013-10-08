package com.bankcomm.novem.dao.impl;

import static com.bankcomm.novem.dao.condition.column.ConditionFactory.extractList;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bankcomm.novem.comm.SimpleEntry;
import com.bankcomm.novem.dao.BaseDao;
import com.bankcomm.novem.dao.IColumsDao;
import com.bankcomm.novem.dao.IGenericHisDao;
import com.bankcomm.novem.dao.ISingleTableMapper;
import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.dao.condition.DeleteArgument;
import com.bankcomm.novem.dao.condition.InsertArgument;
import com.bankcomm.novem.dao.condition.InsertListArgument;
import com.bankcomm.novem.dao.condition.QueryArgument;
import com.bankcomm.novem.dao.condition.UpdateArgument;
import com.bankcomm.novem.dao.utils.DBTransfer;
import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.History;
import com.bankcomm.novem.entry.annotation.ModifyUserEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-11-1
 * 
 */
@Component
class RawDao extends BaseDao implements IColumsDao, IRawCopyDao {
	private static final int DELETED_SIZE = 1;
	private static final String MODIFY_USER_COLUMN = "MODIFY_USER";
	private static final int UPDATED_SIZE = 1;
	private static final String VERSION_COLUMN = "VERSION";
	@Autowired
	private IGenericHisDao hisDao;
	@Autowired
	private ISingleTableMapper iSingleTableMapper;
	@Autowired
	private EntryMetaContainer tableNameContainer;
	@Autowired
	private VersionDecorator versionDecorator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.dao.impl.ICopyableDao#copy(com.bankcomm.novem.entry.
	 * BaseEntry, com.bankcomm.novem.dao.annote.DaoTypeEnum, java.lang.String)
	 */
	@Override
	public long copyWithoutHistory(final BaseEntry entry,
			final DaoTypeEnum daoType, final String dbSchema) {
		notNull(entry, "待新增的数据不能为空");
		isTrue(entry.getPrimaryKey() > 0, "复制时实体主键必须大于0");

		final InsertArgument condition = assembleInsertCondition(entry,
				daoType, dbSchema, true);

		if (iSingleTableMapper.insertWithPK(condition) != 1) {
			return -1;
		}

		return entry.getPrimaryKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.impl.IColumsDao#deleteAll( java.lang.Class,
	 * com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> int deleteAll(final Class<E> entryClass,
			final DaoTypeEnum daoType, final String dbSchema) {
		return iSingleTableMapper.deleteAll(new DeleteArgument(
				extractTableName(entryClass, daoType, dbSchema), null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.IColumsDao#deleteByColumn(java.lang.Class,
	 * java.util.Map, com.bankcomm.novem.dao.annote.DaoTypeEnum,
	 * java.lang.String)
	 */
	@Override
	public <E extends BaseEntry> int deleteByColumn(final Class<E> classInfo,
			final Map<String, Object> columnValue, final DaoTypeEnum daoType,
			final String dbSchema) {
		notEmpty(columnValue, "用来删除的映射不能为空");

		if (daoType == DaoTypeEnum.HISTORY) {
			throw new UnsupportedOperationException("历史表不支持删除操作");
		}

		return iSingleTableMapper.delete(new DeleteArgument(extractTableName(
				classInfo, daoType, dbSchema), extractList(columnValue)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.IColumsDao#deleteByColumns(java.lang.Class,
	 * java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> boolean deleteByColumns(
			final Class<E> classInfo, final Map<String, Object> columnValues,
			final DaoTypeEnum daoType, final String dbSchema) {
		notEmpty(columnValues, "用来删除的映射不能为空");

		if (daoType == DaoTypeEnum.HISTORY) {
			throw new UnsupportedOperationException("历史表不支持删除操作");
		}

		return DELETED_SIZE == iSingleTableMapper.delete(new DeleteArgument(
				extractTableName(classInfo, daoType, dbSchema),
				extractList(columnValues)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.impl.IColumsDao#deleteByEntryID(long,
	 * java.lang.Class, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> boolean deleteByEntryID(final long entryID,
			final Class<E> entryClass, final DaoTypeEnum daoType,
			final String dbSchema) {
		Assert.isTrue(entryID > 0, "实体主键必须大于0");

		final Map<String, Object> columnValues = new HashMap<String, Object>();
		columnValues.put(extractPkName(entryClass), entryID);
		return deleteByColumns(entryClass, columnValues, daoType, dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.impl.IColumsDao#insert(com.bocom.egif.entry.BaseEntry,
	 * com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public long insert(final BaseEntry entry, final DaoTypeEnum daoType,
			final String dbSchema) {
		notNull(entry, "待新增的数据不能为空");
		final InsertArgument condition = assembleInsertCondition(entry,
				daoType, dbSchema, false);

		if (iSingleTableMapper.insert(condition) != 1) {
			return -1;
		}
		entry.setPrimaryKey(condition.getGenerateID());

		saveHistory(entry, daoType, dbSchema);

		return entry.getPrimaryKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.novem.dao.ISimpleDao#insertListWithPK(List, DaoTypeEnum,
	 * String)
	 */
	@Override
	public int insertListWithPK(final List<? extends BaseEntry> entryList,
			final DaoTypeEnum daoType, final String dbSchema) {
		notEmpty(entryList, "待新增的数据不能为空");

		final InsertListArgument condition = assembleInsertCondition(entryList,
				daoType, dbSchema, true);

		return iSingleTableMapper.insertList(condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.impl.IColumsDao#insertWithPK(com.bocom.egif.entry.
	 * BaseEntry, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public long insertWithPK(final BaseEntry entry, final DaoTypeEnum daoType,
			final String dbSchema) {
		notNull(entry, "待新增的数据不能为空");
		isTrue(entry.getPrimaryKey() > 0, "带主键自增时实体主键必须大于0");

		final InsertArgument condition = assembleInsertCondition(entry,
				daoType, dbSchema, true);

		if (iSingleTableMapper.insertWithPK(condition) != 1) {
			return -1;
		}

		saveHistory(entry, daoType, dbSchema);

		return entry.getPrimaryKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.impl.IColumsDao#queryByColumns(java.lang.Class,
	 * java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> E queryByColumns(final Class<E> classInfo,
			final Map<String, Object> columnValues, final DaoTypeEnum daoType,
			final String dbSchema) {
		return queryByColumns(classInfo, columnValues, daoType, dbSchema, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.IColumsDao#queryByColumns(java.lang.Class,
	 * java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum, java.util.Set)
	 */
	@Override
	public <E extends BaseEntry> E queryByColumns(final Class<E> classInfo,
			final Map<String, Object> columnValues, final DaoTypeEnum daoType,
			final String dbSchema, final Set<String> resultColumnsName) {
		notEmpty(columnValues, "用来查询的映射不能为空");
		final QueryArgument condition = new QueryArgument(extractTableName(
				classInfo, daoType, dbSchema), extractList(columnValues),
				resultColumnsName);
		final List<Map<String, Object>> queryList = iSingleTableMapper
				.queryForList(condition);
		final Map<String, Object> resultMap = queryList.isEmpty() ? null
				: queryList.get(0);

		return resultMap == null ? null : DBTransfer.transferToEntry(resultMap,
				classInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.impl.IColumsDao#queryByEntryID(long,
	 * java.lang.Class, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> E queryByEntryID(final long entryID,
			final Class<E> entryClass, final DaoTypeEnum daoType,
			final String dbSchema) {
		Assert.isTrue(entryID > 0, "主键必须大于0");

		final Map<String, Object> columns = new HashMap<String, Object>();
		columns.put(extractPkName(entryClass), entryID);
		return queryByColumns(entryClass, columns, daoType, dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.IColumsDao#queryEntryIdByColumns(java.lang.Class,
	 * java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> long queryEntryIdByColumns(
			final Class<E> classInfo, final Map<String, Object> columnValues,
			final DaoTypeEnum daoType, final String dbSchema) {
		final Set<String> resultColumnsName = new HashSet<String>(1);
		resultColumnsName.add(extractPkName(classInfo));
		final E queryResult = queryByColumns(classInfo, columnValues, daoType,
				dbSchema, resultColumnsName);
		return queryResult == null ? 0 : queryResult.getPrimaryKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.IColumsDao#queryEntryIdListByColumns(java.lang.Class,
	 * java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> List<Long> queryEntryIdListByColumns(
			final Class<E> classInfo, final Map<String, Object> columnValues,
			final DaoTypeEnum daoType, final String dbSchema) {
		final Set<String> resultColumnsName = new HashSet<String>(1);
		resultColumnsName.add(extractPkName(classInfo));
		final List<E> queryResult = queryListByColumns(classInfo, columnValues,
				daoType, dbSchema, resultColumnsName);
		if (isEmpty(queryResult)) {
			return new ArrayList<Long>();
		}

		final ArrayList<Long> idResult = new ArrayList<Long>();
		for (final E e : queryResult) {
			idResult.add(e.getPrimaryKey());
		}
		return idResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.impl.IColumsDao#queryListByColumns(java.lang.Class,
	 * java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public <E extends BaseEntry> List<E> queryListByColumns(
			final Class<E> classInfo, final Map<String, Object> columnValues,
			final DaoTypeEnum daoType, final String dbSchema) {
		return queryListByColumns(classInfo, columnValues, daoType, dbSchema,
				null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.IColumsDao#queryListByColumns(java.lang.Class,
	 * java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum, java.util.Set)
	 */
	@Override
	public <E extends BaseEntry> List<E> queryListByColumns(
			final Class<E> classInfo, final Map<String, Object> columnValues,
			final DaoTypeEnum daoType, final String dbSchema,
			final Set<String> resultColumnsName) {
		notEmpty(columnValues, "用来查询的映射不能为空");

		final QueryArgument condition = new QueryArgument(extractTableName(
				classInfo, daoType, dbSchema), extractList(columnValues),
				resultColumnsName);

		final List<Map<String, Object>> resultMap = iSingleTableMapper
				.queryForList(condition);

		final List<E> resultList = new ArrayList<E>();
		for (final Map<String, Object> map : resultMap) {
			resultList.add(DBTransfer.transferToEntry(map, classInfo));
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.ISimpleDao#update(E,
	 * com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public boolean update(final BaseEntry tobeUpdate,
			final DaoTypeEnum daoType, final String dbSchema) {
		notNull(tobeUpdate, "待更新数据不能为空");
		isTrue(tobeUpdate.getPrimaryKey() > 0, "用来更新的数据实体"
				+ tobeUpdate.getClass().getName() + "主键必须大于0");

		final Map<String, Object> columnValues = new HashMap<String, Object>();
		columnValues.put(extractPkName(tobeUpdate.getClass()),
				tobeUpdate.getPrimaryKey());
		return updateByColumns(tobeUpdate, columnValues, daoType, dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.impl.IColumsDao#updateByColumns(com.bocom.egif.entry
	 * .BaseEntry, java.util.Map, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public boolean updateByColumns(final BaseEntry tobeUpdate,
			final Map<String, Object> whereCondition,
			final DaoTypeEnum daoType, final String dbSchema) {
		notNull(tobeUpdate, "待更新数据不能为空");
		notEmpty(whereCondition, "更新条件不能为空");
		if (daoType == DaoTypeEnum.HISTORY) {
			throw new UnsupportedOperationException("历史表不支持更新操作");
		}

		final List<Entry<String, Object>> toUpdateValue = DBTransfer
				.extractColumnValue(tobeUpdate, false);

		final int nextVersion = versionDecorator.nextVersion(tobeUpdate,
				daoType);
		if (nextVersion > 0) {
			toUpdateValue.add(new SimpleEntry<String, Object>(VERSION_COLUMN,
					nextVersion));
		}

		final SimpleEntry<String, Object> modifyUserColumn = extractModifyUser(tobeUpdate);
		if (modifyUserColumn != null) {
			toUpdateValue.add(modifyUserColumn);
		}

		final List<String> columnNameList = new ArrayList<String>();
		final List<Object> valueList = new ArrayList<Object>();
		for (final Entry<String, Object> valueEntry : toUpdateValue) {
			columnNameList.add(valueEntry.getKey());
			valueList.add(valueEntry.getValue());
		}

		final String tableName = extractTableName(tobeUpdate.getClass(),
				daoType, dbSchema);
		final UpdateArgument condition = new UpdateArgument(tableName);
		condition.setColumnNameList(columnNameList);
		condition.setValueList(valueList);

		final int whereVersion = versionDecorator.extractWhereVersion(
				tobeUpdate, daoType);
		if (whereVersion > 0) {
			whereCondition.put(VERSION_COLUMN, whereVersion);
		}
		condition.setWhereColumns(extractList(whereCondition));

		if (iSingleTableMapper.update(condition) != UPDATED_SIZE) {
			return false;
		}

		tobeUpdate
				.setVersion(versionDecorator.nextVersion(tobeUpdate, daoType));
		return saveHistory(tobeUpdate, daoType, dbSchema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.IColumsDao#updateColumns(com.bocom.egif.entry.BaseEntry
	 * , java.util.Set, com.bocom.egif.dao.annote.DaoTypeEnum)
	 */
	@Override
	public boolean updateColumns(final BaseEntry tobeUpdate,
			final Set<String> columnNameSet, final DaoTypeEnum daoType,
			final String dbSchema) {
		if (daoType == DaoTypeEnum.HISTORY) {
			throw new UnsupportedOperationException("历史表不支持更新操作");
		}

		notNull(tobeUpdate, "待更新数据不能为空");
		isTrue(tobeUpdate.getPrimaryKey() > 0, "用来更新的数据实体"
				+ tobeUpdate.getClass().getName() + "主键必须大于0");
		notEmpty(columnNameSet, "至少需要制定一个待更新列");

		final List<Entry<String, Object>> toUpdateValue = extractUpdateValueList(
				tobeUpdate, columnNameSet, daoType);
		final List<String> columnNameList = new ArrayList<String>();
		final List<Object> valueList = new ArrayList<Object>();
		for (final Entry<String, Object> valueEntry : toUpdateValue) {
			columnNameList.add(valueEntry.getKey());
			valueList.add(valueEntry.getValue());
		}

		final String tableName = extractTableName(tobeUpdate.getClass(),
				daoType, dbSchema);
		final UpdateArgument condition = new UpdateArgument(tableName);
		condition.setColumnNameList(columnNameList);
		condition.setValueList(valueList);
		final Map<String, Object> whereCondition = assembleWhereCondition(
				tobeUpdate, daoType);
		condition.setWhereColumns(extractList(whereCondition));

		if (iSingleTableMapper.update(condition) != UPDATED_SIZE) {
			return false;
		}

		tobeUpdate
				.setVersion(versionDecorator.nextVersion(tobeUpdate, daoType));
		return saveHistory(tobeUpdate, daoType, dbSchema);
	}

	/**
	 * @param entry
	 * @param daoType
	 * @param includePK
	 * 
	 * @return
	 */
	private InsertArgument assembleInsertCondition(final BaseEntry entry,
			final DaoTypeEnum daoType, final String dbSchema,
			final boolean includePK) {
		final List<Entry<String, Object>> columnValue = DBTransfer
				.extractColumnValue(entry, includePK);

		final SimpleEntry<String, Object> versionColumn = versionDecorator
				.extractInsertVersion(entry, daoType);
		if (versionColumn != null) {
			columnValue.add(versionColumn);
		}

		final SimpleEntry<String, Object> modifyUserColumn = extractModifyUser(entry);
		if (modifyUserColumn != null) {
			columnValue.add(modifyUserColumn);
		}

		final List<String> columnNameList = new ArrayList<String>();
		final List<Object> valueList = new ArrayList<Object>();
		for (final Entry<String, Object> valueEntry : columnValue) {
			columnNameList.add(valueEntry.getKey());
			valueList.add(valueEntry.getValue());
		}

		final InsertArgument insertArgument = new InsertArgument(
				extractTableName(entry.getClass(), daoType, dbSchema));
		insertArgument.setColumnNameList(columnNameList);
		insertArgument.setValueList(valueList);

		return insertArgument;
	}

	/**
	 * @param entryList
	 * @param daoType
	 * @param includePK
	 * 
	 * @return
	 */
	private InsertListArgument assembleInsertCondition(
			final List<? extends BaseEntry> entryList,
			final DaoTypeEnum daoType, final String dbSchema,
			final boolean includePK) {
		final List<String> columnNameList = new ArrayList<String>();
		final List<List<Object>> insertList = new ArrayList<List<Object>>();
		for (final BaseEntry baseEntry : entryList) {
			isTrue(baseEntry.getPrimaryKey() > 0, "插入列表时实体主键必须大于0");
			final List<Entry<String, Object>> columnValue = DBTransfer
					.extractColumnValue(baseEntry, includePK);

			final SimpleEntry<String, Object> versionColumn = versionDecorator
					.extractInsertVersion(baseEntry, daoType);
			if (versionColumn != null) {
				columnValue.add(versionColumn);
			}

			final SimpleEntry<String, Object> modifyUserColumn = extractModifyUser(baseEntry);
			if (modifyUserColumn != null) {
				columnValue.add(modifyUserColumn);
			}

			final List<Object> valueList = new ArrayList<Object>();
			for (final Entry<String, Object> valueEntry : columnValue) {
				valueList.add(valueEntry.getValue());
			}
			insertList.add(valueList);

			if (CollectionUtils.isEmpty(columnNameList)) {
				for (final Entry<String, Object> valueEntry : columnValue) {
					columnNameList.add(valueEntry.getKey());
				}
			}
		}

		return new InsertListArgument(extractTableName(entryList.get(0)
				.getClass(), daoType, dbSchema), columnNameList, insertList);
	}

	/**
	 * @param tobeUpdate
	 * @param daoType
	 * @return
	 */
	private Map<String, Object> assembleWhereCondition(
			final BaseEntry tobeUpdate, final DaoTypeEnum daoType) {
		final Map<String, Object> whereCondition = new HashMap<String, Object>();
		whereCondition.put(extractPkName(tobeUpdate.getClass()),
				tobeUpdate.getPrimaryKey());

		final int whereVersion = versionDecorator.extractWhereVersion(
				tobeUpdate, daoType);
		if (whereVersion > 0) {
			whereCondition.put(VERSION_COLUMN, whereVersion);
		}
		return whereCondition;
	}

	/**
	 * @param entry
	 * @param daoType
	 * @return
	 */
	private SimpleEntry<String, Object> extractModifyUser(final BaseEntry entry) {
		if (!entry.getClass().isAnnotationPresent(ModifyUserEntry.class)) {
			return null;
		}
		final long modifyUser = entry.getModifyUser();
		return new SimpleEntry<String, Object>(MODIFY_USER_COLUMN,
				String.valueOf(modifyUser));
	}

	/**
	 * @param entryClass
	 * @return
	 */
	private String extractPkName(final Class<? extends BaseEntry> entryClass) {
		return tableNameContainer.extractPkName(entryClass);
	}

	/**
	 * @param classInfo
	 * @param daoType
	 * @return
	 */
	private String extractTableName(final Class<? extends BaseEntry> classInfo,
			final DaoTypeEnum daoType, final String dbSchema) {
		return tableNameContainer
				.extractTableName(classInfo, daoType, dbSchema);
	}

	/**
	 * @param tobeUpdate
	 * @param columnNameSet
	 * @param daoType
	 * @return
	 */
	private List<Entry<String, Object>> extractUpdateValueList(
			final BaseEntry tobeUpdate, final Set<String> columnNameSet,
			final DaoTypeEnum daoType) {
		final List<Entry<String, Object>> toUpdateValue = new ArrayList<Map.Entry<String, Object>>();
		for (final Entry<String, Object> entry : DBTransfer.extractColumnValue(
				tobeUpdate, false)) {
			if (columnNameSet.contains(entry.getKey())) {
				toUpdateValue.add(entry);
			}
		}
		notEmpty(toUpdateValue, "待更新的值不能为空");

		final int nextVersion = versionDecorator.nextVersion(tobeUpdate,
				daoType);
		if (nextVersion > 0) {
			toUpdateValue.add(new SimpleEntry<String, Object>(VERSION_COLUMN,
					nextVersion));
		}

		final SimpleEntry<String, Object> modifyUserColumn = extractModifyUser(tobeUpdate);
		if (modifyUserColumn != null) {
			toUpdateValue.add(modifyUserColumn);
		}
		return toUpdateValue;
	}

	/**
	 * @param entry
	 * @param daoType
	 * @return
	 */
	private boolean saveHistory(final BaseEntry entry,
			final DaoTypeEnum daoType, final String dbSchema) {
		if (!entry.getClass().isAnnotationPresent(History.class)) {
			return true;
		}

		if (daoType == DaoTypeEnum.HISTORY) {
			return true;
		}
		if (daoType == DaoTypeEnum.ORIGIN) {
			return true;
		}

		return hisDao.insert(entry, dbSchema) > 0;
	}

}