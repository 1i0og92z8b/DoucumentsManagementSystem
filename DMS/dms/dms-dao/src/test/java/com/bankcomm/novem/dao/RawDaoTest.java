package com.bankcomm.novem.dao;

import static com.bankcomm.novem.dao.Version.nextVersion;
import static com.bankcomm.novem.dao.annote.DaoTypeEnum.COPY;
import static com.bankcomm.novem.dao.annote.DaoTypeEnum.HISTORY;
import static com.bankcomm.novem.dao.annote.DaoTypeEnum.ORIGIN;
import static com.bankcomm.novem.dao.annote.DaoTypeProcessor.extractDaoType;
import static com.bankcomm.novem.dao.common.FillData.fillDataForClassFields;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.SneakyThrows;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.dao.annote.DaoTypeProcessor;
import com.bankcomm.novem.dao.common.CommonDao;
import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.EntryPk;
import com.bankcomm.novem.entry.annotation.History;
import com.bankcomm.novem.entry.annotation.VersionEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-11-2
 * 
 * @param <T>
 *            测试的实体类型
 */
public abstract class RawDaoTest<T extends BaseEntry> extends
		BaseDaoTest<SingleTableDao<T>> {
	private static final int ERROR_VERSION = 3000000;
	private static long UNEXIST_ENTRY_ID = 31720981782309L;
	@Autowired
	private TestBean2DaoBean<T> beanBuffer;
	@Autowired
	private CommonDao commonDao;

	/** 实体类型信息 */
	private final Class<T> entryClassInfo = extractEntryClassInfo();

	/**
	 * @return {@link #entryClassInfo}
	 */
	public Class<T> getEntryClassInfo() {
		return entryClassInfo;
	}

	/**
	 * Test method for {@link ISingleTableDao#deleteByID(long)}
	 */
	@Test
	public void testDeleteByID() {
		final long entryID = insert(createMinEntry());

		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			try {
				deleteByID(entryID);
				checkExceptionRaise();
			} catch (final UnsupportedOperationException e) {
				assertEquals("历史表不支持删除操作", e.getMessage());
				return;
			}
		}

		assertTrue(deleteByID(entryID));
	}

	/**
	 * Test method for {@link ISingleTableDao#deleteByID(long)}
	 */
	@Test
	public void testDeleteByIDForEntry() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持删除操作
			return;
		}
		final long entryID = insert(createMinEntry());

		deleteByID(entryID);

		final T deletedEntry = queryByID(entryID);

		assertNull(deletedEntry);
	}

	/**
	 * Test method for {@link ISingleTableDao#deleteByID(long)}
	 */
	@Test
	public void testDeleteByIDWithoutData() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持删除操作
			return;
		}
		final boolean isDeleted = deleteByID(UNEXIST_ENTRY_ID);

		assertFalse(isDeleted);
	}

	/**
	 * Test method for {@link ISingleTableDao#deleteByID(long)}
	 */
	@Test
	public void testDeleteByIDWithZero() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持删除操作
			return;
		}
		try {
			deleteByID(0);
		} catch (final IllegalArgumentException e) {
			assertEquals("实体主键必须大于0", e.getMessage());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsert() {
		final long entryID = insert(createMinEntry());

		assertTrue(entryID > 0);
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertForCreateTime() {
		final long entryID = insert(createMinEntry());

		final BaseEntry queryEntry = queryByID(entryID);

		assertNotNull(queryEntry.getCreateTime());
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertForEntryID() {
		final T entry = createMinEntry();
		insert(entry);

		assertTrue(entry.getPrimaryKey() > 0);
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertForUpdateTime() {
		final long entryID = insert(createMinEntry());

		final BaseEntry queryEntry = queryByID(entryID);

		assertNotNull(queryEntry.getUpdateTime());
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertForVersion() {
		final long entryID = insert(createMinEntry());

		final BaseEntry queryEntry = queryByID(entryID);

		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			assertEquals(Version.initVersion(), queryEntry.getVersion());
		} else {
			assertEquals(0, queryEntry.getVersion());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertForVersionOfInput() {
		final T toInsert = createMinEntry();

		insert(toInsert);

		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			assertEquals("版本化数据实体版本信息会被更新", Version.initVersion(),
					toInsert.getVersion());
		} else {
			assertEquals(0, toInsert.getVersion());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	@Rollback(false)
	public void testInsertWithCreateTime() {
		final T entry = createMinEntry();
		entry.setCreateTime(commonDao.queryCurrentTime());
		final long entryID = insert(entry);

		final BaseEntry queryEntry = queryByID(entryID);

//		assertTrue(entry.getCreateTime().before(queryEntry.getCreateTime()));
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithErrorVersionForVersion() {
		final T entry = createMinEntry();
		entry.setVersion(ERROR_VERSION);
		final long entryID = insert(entry);

		final BaseEntry queryEntry = queryByID(entryID);

		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			if ((DaoTypeProcessor.extractDaoType(getClass()) == null)) {
				assertEquals(Version.initVersion(), queryEntry.getVersion());
			} else {
				assertEquals(ERROR_VERSION, queryEntry.getVersion());
			}
		} else {
			assertEquals(0, queryEntry.getVersion());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithNull() {
		try {
			insert(null);
		} catch (final IllegalArgumentException e) {
			assertEquals("待新增的数据不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithoutVersion() {
		final T createEntry = createMinEntry();
		createEntry.setVersion(0);
		final long entryID = insert(createEntry);

		assertTrue(entryID > 0);
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithoutVersionForInput() {
		final T createEntry = createMinEntry();
		createEntry.setVersion(0);
		insert(createEntry);

		if (!entryClassInfo.isAnnotationPresent(VersionEntry.class)) {
			Assert.assertEquals(0, createEntry.getVersion());
			return;
		}

		final DaoTypeEnum daoType = extractDaoType(getClass());
		if (daoType == null) {
			Assert.assertEquals(Version.initVersion(), createEntry.getVersion());
		} else if (daoType == COPY) {
			Assert.assertEquals(Version.initVersion(), createEntry.getVersion());
		} else if (daoType == ORIGIN) {
			Assert.assertEquals(Version.initVersion(), createEntry.getVersion());
		} else if (daoType == HISTORY) {
			Assert.assertEquals(Version.initVersion(), createEntry.getVersion());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithoutVersionForResult() {
		final T createEntry = createMinEntry();
		createEntry.setVersion(0);
		final long entryID = insert(createEntry);

		final T queryEntry = queryByID(entryID);

		if (!entryClassInfo.isAnnotationPresent(VersionEntry.class)) {
			Assert.assertEquals(0, queryEntry.getVersion());
			return;
		}

		final DaoTypeEnum daoType = extractDaoType(getClass());
		if (daoType == null) {
			Assert.assertEquals(Version.initVersion(), queryEntry.getVersion());
		} else if (daoType == COPY) {
			Assert.assertEquals(Version.initVersion(), queryEntry.getVersion());
		} else if (daoType == ORIGIN) {
			Assert.assertEquals(Version.initVersion(), queryEntry.getVersion());
		} else if (daoType == HISTORY) {
			Assert.assertEquals(Version.initVersion(), queryEntry.getVersion());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithUpdateTime() {
		final T entry = createMinEntry();
		entry.setUpdateTime(commonDao.queryCurrentTime());
		final long entryID = insert(entry);

		final T queryEntry = queryByID(entryID);

		assertTrue(entry.getUpdateTime().before(queryEntry.getUpdateTime()));
	}

	/**
	 * Test method for {@link ISingleTableDao#queryByID(long)}
	 */
	@Test
	public void testQueryByID() {
		final long entryID = insert(createMinEntry());

		final T queryEntry = queryByID(entryID);

		assertEquals(entryID, queryEntry.getPrimaryKey());
	}

	/**
	 * Test method for {@link ISingleTableDao#queryByID(long)}
	 * 测试字段值的新增和查询，不测试null和主键<br>
	 * <br>
	 * 数据通过{@link #createMinEntry()}提供
	 * 
	 * @throws Exception
	 *             异常
	 * 
	 */
	@Test
	public void testQueryByIDForFieldValue() throws Exception {
		final T fullEntry = createFullEntry();
		int dataVersion = Version.initVersion();

		for (Class<?> current = entryClassInfo; (current != BaseEntry.class)
				&& (current != Object.class); current = current.getSuperclass()) {
			for (final Field field : current.getDeclaredFields()) {
				final boolean isAccessible = field.isAccessible();
				field.setAccessible(true);

				if (field.isAnnotationPresent(EntryPk.class)) {
					continue;
				}

				if (shouldIgnoreField(field.getName())) {
					continue;
				}

				final Object value = field.get(fullEntry);
				if (value == null) {
					continue;
				}

				final T toInsert = createMinEntry();
				field.set(toInsert, value);
				if (toInsert.getClass().isAnnotationPresent(VersionEntry.class)) {
					toInsert.setVersion(dataVersion);
				}
				final long entryID = insert(toInsert);

				final T queryEntry = queryByID(entryID);

				if (BigDecimal.class.isAssignableFrom(field.getType())) {
					final BigDecimal fullData = (BigDecimal) field
							.get(fullEntry);
					final BigDecimal queryData = (BigDecimal) field
							.get(queryEntry);
					Assert.assertTrue(field.getName(),
							fullData.compareTo(queryData) == 0);
				} else {
					assertEquals(field.getName(), field.get(fullEntry),
							field.get(queryEntry));
				}

				field.setAccessible(isAccessible);
				dataVersion = Version.nextVersion(dataVersion);
			}
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#queryByID(long)}
	 */
	@Test
	public void testQueryByIDWithOutData() {
		final T queryEntry = queryByID(UNEXIST_ENTRY_ID);

		assertNull(queryEntry);
	}

	/**
	 * Test method for {@link ISingleTableDao#queryByID(long)}
	 */
	@Test
	public void testQueryByIDWithZero() {
		try {
			queryByID(0);
		} catch (final IllegalArgumentException e) {
			assertEquals("主键必须大于0", e.getMessage());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdate() {
		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T toUpdate = createMinEntry();
		toUpdate.setVersion(getToUpdateVersion(toInsert.getVersion()));
		toUpdate.setPrimaryKey(entryID);

		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			try {
				update(toUpdate);
				checkExceptionRaise();
			} catch (final UnsupportedOperationException e) {
				assertEquals("历史表不支持更新操作", e.getMessage());
				return;
			}
		}

		assertTrue(update(toUpdate));
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateForCreateTime() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T beforeUpdateEntry = queryByID(entryID);

		final T toUpdate = createMinEntry();
		toUpdate.setPrimaryKey(entryID);
		toUpdate.setVersion(getToUpdateVersion(toInsert.getVersion()));

		update(toUpdate);

		final T queryEntry = queryByID(entryID);

		assertEquals(beforeUpdateEntry.getCreateTime(),
				queryEntry.getCreateTime());
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 * 测试字段值的更新，不测试null和主键 <br>
	 * <br>
	 * 数据通过{@link #createMinEntry()}提供
	 * 
	 * @throws Exception
	 *             异常
	 * 
	 */
	@Test
	public void testUpdateForFieldValue() throws Exception {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T fullEntry = createFullEntry();
		int dataVersion = Version.initVersion();

		final T toInsert = createMinEntry();
		if (toInsert.getClass().isAnnotationPresent(VersionEntry.class)) {
			toInsert.setVersion(dataVersion);
		}
		final long entryID = insert(toInsert);

		for (Class<?> current = entryClassInfo; (current != BaseEntry.class)
				&& (current != Object.class); current = current.getSuperclass()) {
			for (final Field field : current.getDeclaredFields()) {
				final boolean isAccessible = field.isAccessible();
				field.setAccessible(true);

				if (field.isAnnotationPresent(EntryPk.class)) {
					continue;
				}

				if (shouldIgnoreField(field.getName())) {
					continue;
				}

				final Object value = field.get(fullEntry);
				if (value == null) {
					continue;
				}

				dataVersion = getToUpdateVersion(dataVersion);
				dataVersion = updateField(field, value, entryID, dataVersion);

				final T queryEntry = queryByID(entryID);

				if (BigDecimal.class.isAssignableFrom(field.getType())) {
					final BigDecimal fullData = (BigDecimal) field
							.get(fullEntry);
					final BigDecimal queryData = (BigDecimal) field
							.get(queryEntry);
					Assert.assertTrue(field.getName(),
							fullData.compareTo(queryData) == 0);
				} else {
					assertEquals(field.getName(), field.get(fullEntry),
							field.get(queryEntry));
				}

				field.setAccessible(isAccessible);
			}
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateForNotExistEntry() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toUpdate = createMinEntry();
		toUpdate.setPrimaryKey(UNEXIST_ENTRY_ID);
		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			if (extractDaoType(getClass()) == null) {
				try {
					assertFalse(update(toUpdate));
					checkExceptionRaise();
				} catch (final IllegalArgumentException e) {
					assertEquals("版本化" + entryClassInfo + "数据版本号必须大于0",
							e.getMessage());
				}
			}
		} else {
			assertFalse(update(toUpdate));
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateForPrimaryKey() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T toUpdate = createMinEntry();
		toUpdate.setPrimaryKey(entryID);
		toUpdate.setVersion(getToUpdateVersion(toInsert.getVersion()));

		update(toUpdate);

		final T queryEntry = queryByID(entryID);
		assertEquals(entryID, queryEntry.getPrimaryKey());
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateForUpdateTime() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T beforeUpdate = queryByID(entryID);

		final T toUpdate = createMinEntry();
		toUpdate.setVersion(getToUpdateVersion(toInsert.getVersion()));
		toUpdate.setPrimaryKey(entryID);
		update(toUpdate);

		final T queryEntry = queryByID(entryID);

		assertTrue(beforeUpdate.getUpdateTime().before(
				queryEntry.getUpdateTime()));
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateForVersion() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T toUpdate = createMinEntry();
		toUpdate.setVersion(getToUpdateVersion(toInsert.getVersion()));
		toUpdate.setPrimaryKey(entryID);

		update(toUpdate);

		if (entryClassInfo.isAnnotationPresent(VersionEntry.class)) {
			if (extractDaoType(getClass()) == ORIGIN) {
				assertEquals(toUpdate.getVersion(), toUpdate.getVersion());
			} else {
				assertEquals(nextVersion(toInsert.getVersion()),
						toUpdate.getVersion());
			}
		} else {
			assertEquals(0, toUpdate.getVersion());
		}

	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateTwiceWithIncreaseVersionForDataResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final int firstVersion = getToUpdateVersion(toInsert.getVersion());
		final T firsetUpdate = toInsert;
		firsetUpdate.setPrimaryKey(entryID);
		firsetUpdate.setVersion(firstVersion);

		update(firsetUpdate);

		final int secondVersion = nextVersion(firstVersion);
		final T secondUpdate = createMinEntry();
		secondUpdate.setPrimaryKey(entryID);
		secondUpdate.setVersion(secondVersion);

		assertTrue(update(secondUpdate));
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateTwiceWithIncreaseVersionForResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final int firstVersion = getToUpdateVersion(toInsert.getVersion());
		final T firsetUpdate = createMinEntry();
		firsetUpdate.setPrimaryKey(entryID);
		firsetUpdate.setVersion(firstVersion);

		update(firsetUpdate);

		final int secondVersion = getToUpdateVersion(firsetUpdate.getVersion());
		final T secondUpdate = createMinEntry();
		secondUpdate.setPrimaryKey(entryID);
		secondUpdate.setVersion(secondVersion);
		update(secondUpdate);

		final T queryEntry = queryByID(entryID);
		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			if (DaoTypeProcessor.extractDaoType(getClass()) == ORIGIN) {
				assertEquals(secondVersion, queryEntry.getVersion());
			} else {
				assertEquals(nextVersion(secondVersion),
						queryEntry.getVersion());
			}
		} else {
			assertEquals(0, queryEntry.getVersion());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateTwiceWithSameDataForDataResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final int firstVersion = getToUpdateVersion(toInsert.getVersion());
		final T firstUpdate = createMinEntry();
		firstUpdate.setPrimaryKey(entryID);
		firstUpdate.setVersion(firstVersion);
		update(firstUpdate);

		final T secondUpdate = createMinEntry();
		secondUpdate.setPrimaryKey(entryID);
		secondUpdate.setVersion(firstVersion);

		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			if (entryClassInfo.isAnnotationPresent(History.class)) {
				final DaoTypeEnum daoType = extractDaoType(getClass());
				if (daoType != COPY) {
					return;
				}
			}
			update(secondUpdate);
			final T queryEntry = queryByID(entryID);
			if (extractDaoType(getClass()) == ORIGIN) {
				assertEquals(firstVersion, queryEntry.getVersion());
				return;
			}
			assertEquals(nextVersion(firstVersion), queryEntry.getVersion());
			return;
		}
		update(secondUpdate);
		final T queryEntry = queryByID(entryID);
		assertEquals(0, queryEntry.getVersion());
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateTwiceWithSameDataForReturn() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final int firstVersion = getToUpdateVersion(toInsert.getVersion());
		final T firstUpdate = createMinEntry();
		firstUpdate.setPrimaryKey(entryID);
		firstUpdate.setVersion(firstVersion);
		update(firstUpdate);
		final T secondUpdate = createMinEntry();
		secondUpdate.setPrimaryKey(entryID);
		secondUpdate.setVersion(firstVersion);
		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			final DaoTypeEnum daoType = extractDaoType(getClass());
			/*
			 * if (entryClassInfo.isAnnotationPresent(History.class)) { if
			 * (daoType != null) { if (daoType == HISTORY) { try {
			 * update(secondUpdate); checkExceptionRaise(); } catch (final
			 * DuplicateKeyException e) {
			 * assertTrue("有版本标志，同时有历史标志的信息不能进行错误版本更新", true); } return; } }
			 * else { assertFalse(update(secondUpdate)); }
			 * 
			 * }
			 */
			if (daoType == ORIGIN) {
				assertTrue(update(secondUpdate));
				return;
			}
			assertFalse(update(secondUpdate));
			return;
		}
		assertTrue(update(secondUpdate));
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithCreateTime() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T beforeUpdate = queryByID(entryID);

		final T toUpdate = createMinEntry();
		toUpdate.setCreateTime(commonDao.queryCurrentTime());
		toUpdate.setPrimaryKey(entryID);
		toUpdate.setVersion(getToUpdateVersion(toInsert.getVersion()));

		update(toUpdate);

		final T queryEntry = queryByID(entryID);

		assertEquals(beforeUpdate.getCreateTime(), queryEntry.getCreateTime());
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithErrorVersionForDataResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T toUpdate = createMinEntry();
		toUpdate.setPrimaryKey(entryID);
		final int firstVersion = toInsert.getVersion();
		toUpdate.setVersion(ERROR_VERSION);

		update(toUpdate);

		final T queryEntry = queryByID(entryID);
		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			final DaoTypeEnum daoType = extractDaoType(getClass());
			if (daoType != null) {
				switch (daoType) {
				case ORIGIN:
					assertEquals(ERROR_VERSION, queryEntry.getVersion());
					return;
				case COPY:
				default:
					assertEquals(firstVersion, queryEntry.getVersion());
					return;
				}
			}
			assertEquals(firstVersion, queryEntry.getVersion());
			return;

		}
		assertEquals(0, queryEntry.getVersion());
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithErrorVersionForResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final long entryID = insert(createMinEntry());

		final T updateEntry = createMinEntry();
		updateEntry.setPrimaryKey(entryID);
		updateEntry.setVersion(ERROR_VERSION);

		if (entryClassInfo.isAnnotationPresent(VersionEntry.class)) {
			final DaoTypeEnum daoType = extractDaoType(getClass());
			if ((daoType == null) || (daoType == COPY)) {
				assertFalse(update(updateEntry));
				return;
			}
		}
		assertTrue(update(updateEntry));
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithNull() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		try {
			update(null);
		} catch (final IllegalArgumentException e) {
			assertEquals("待更新数据不能为空", e.getMessage());
		}

	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithOutVersion() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final long entryID = insert(createMinEntry());

		final T toUpdate = createMinEntry();
		toUpdate.setPrimaryKey(entryID);
		toUpdate.setVersion(0);

		if (!entryClassInfo.isAnnotationPresent(VersionEntry.class)) {
			assertTrue(update(toUpdate));
			return;
		}

		if (!entryClassInfo.isAnnotationPresent(History.class)) {
			final DaoTypeEnum daoType = extractDaoType(getClass());
			if (daoType == null) {
				try {
					update(toUpdate);
					checkExceptionRaise();
				} catch (final IllegalArgumentException e) {
					assertEquals("版本化" + entryClassInfo + "数据版本号必须大于0",
							e.getMessage());
					return;
				}
			} else if (daoType == COPY) {
				try {
					update(toUpdate);
					checkExceptionRaise();
				} catch (final IllegalArgumentException e) {
					assertEquals("版本化" + entryClassInfo + "数据版本号必须大于0",
							e.getMessage());
					return;
				}
			} else if (daoType == ORIGIN) {
				assertTrue(update(toUpdate));
				return;
			}
		}

		final DaoTypeEnum daoType = extractDaoType(getClass());
		if (daoType == null) {
			try {
				update(toUpdate);
				checkExceptionRaise();
			} catch (final IllegalArgumentException e) {
				assertEquals("版本化" + entryClassInfo + "数据版本号必须大于0",
						e.getMessage());
				return;
			}
		} else if (daoType == COPY) {
			try {
				update(toUpdate);
				checkExceptionRaise();
			} catch (final IllegalArgumentException e) {
				assertEquals("版本化" + entryClassInfo + "数据版本号必须大于0",
						e.getMessage());
			}
			return;
		} else if (daoType == ORIGIN) {
			// 此时不更新历史
			assertTrue(update(toUpdate));
			return;
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithOutVersionForDataResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		toInsert.setVersion(0);
		final long entryID = insert(toInsert);
		final int afterInsertId = queryByID(entryID).getVersion();

		final T toUpdate = createMinEntry();
		toUpdate.setPrimaryKey(entryID);
		toUpdate.setVersion(0);

		if (!entryClassInfo.isAnnotationPresent(VersionEntry.class)) {
			update(toUpdate);
			assertEquals(0, queryByID(entryID).getVersion());
			return;
		}

		final DaoTypeEnum daoType = extractDaoType(getClass());
		if (daoType == null) {
			try {
				update(toUpdate);
				checkExceptionRaise();
			} catch (final IllegalArgumentException e) {
				assertEquals("版本化" + entryClassInfo + "数据版本号必须大于0",
						e.getMessage());
				return;
			}
		} else if (daoType == COPY) {
			try {
				update(toUpdate);
				checkExceptionRaise();
			} catch (final IllegalArgumentException e) {
				assertEquals("版本化" + entryClassInfo + "数据版本号必须大于0",
						e.getMessage());
			}
			return;
		} else if (daoType == ORIGIN) {
			update(toUpdate);
			assertEquals(afterInsertId, queryByID(entryID).getVersion());
			return;
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithUpdateTime() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T toUpdate = createMinEntry();
		final Timestamp updateTime = commonDao.queryCurrentTime();
		toUpdate.setUpdateTime(updateTime);
		toUpdate.setPrimaryKey(entryID);
		toUpdate.setVersion(getToUpdateVersion(toInsert.getVersion()));

		update(toUpdate);

		final T queryEntry = queryByID(entryID);

		assertTrue(queryEntry.getUpdateTime().after(updateTime));
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithVersionForDataResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final T toUpdate = createMinEntry();
		toUpdate.setPrimaryKey(entryID);
		final int firstVersion = getToUpdateVersion(toInsert.getVersion());
		toUpdate.setVersion(firstVersion);

		update(toUpdate);

		final T queryEntry = queryByID(entryID);
		if (getEntryClassInfo().isAnnotationPresent(VersionEntry.class)) {
			if (extractDaoType(getClass()) == ORIGIN) {
				assertEquals(firstVersion, queryEntry.getVersion());
			} else {
				assertEquals(nextVersion(firstVersion), queryEntry.getVersion());
			}
		} else {
			assertEquals(0, queryEntry.getVersion());
		}
	}

	/**
	 * Test method for {@link ISingleTableDao#update(BaseEntry)}
	 */
	@Test
	public void testUpdateWithVersionForResult() {
		if (DaoTypeProcessor.extractDaoType(this.getClass()) == DaoTypeEnum.HISTORY) {
			// 历史表不需要支持更新操作
			return;
		}

		final T toInsert = createMinEntry();
		final long entryID = insert(toInsert);

		final int firstVersion = getToUpdateVersion(toInsert.getVersion());

		final T updateEntry = toInsert;
		updateEntry.setPrimaryKey(entryID);
		updateEntry.setVersion(firstVersion);

		assertTrue(update(updateEntry));
	}

	/**
	 * 提取实体类型信息
	 * 
	 * @return 实体类型信息
	 */
	@SuppressWarnings("unchecked")
	private Class<T> extractEntryClassInfo() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 获取待更新的数据版本
	 * 
	 * @param insertVersion
	 * @return
	 */
	private int getToUpdateVersion(final int insertVersion) {
		final DaoTypeEnum daoType = extractDaoType(getClass());
		if (daoType == ORIGIN) {
			return insertVersion + 1;
		}
		return insertVersion;
	}

	/**
	 * 根据实体主键更新对应属性上的值
	 * 
	 * @param field
	 *            属性
	 * @param value
	 *            该属性对应的值
	 * @param entryID
	 *            实体主键
	 * @param version
	 *            版本
	 * @throws IllegalAccessException
	 */
	private int updateField(final Field field, final Object value,
			final long entryID, final int version)
			throws IllegalAccessException {
		final T updateEntry = createMinEntry();
		updateEntry.setVersion(version);
		field.set(updateEntry, value);
		updateEntry.setPrimaryKey(entryID);
		assertTrue(update(updateEntry));
		return updateEntry.getVersion();
	}

	/**
	 * 创建单表的满数据实例，用于测试单表数据操作
	 * 
	 * @return 单表的数据实例
	 */
	protected T createFullEntry() {
		final T entry = createMinEntry();
		return fillDataForClassFields(entry);
	}

	/**
	 * 满足单表测试的最小数据实体，当该表存在一些特殊字段需要测试（比如年份或者年月等）时，需要通过该函数提供这样的数据
	 * 
	 * @return 最小数据实体
	 */
	protected T createMinEntry() {
		return createNewInstance();
	}

	/**
	 * 获取单表实例，必须保证满足该表数据的最低要求
	 * 
	 * @return 单表的数据实例
	 */
	@SneakyThrows
	final protected T createNewInstance() {
		final T newInstance = entryClassInfo.newInstance();
		newInstance.setPrimaryKey(UNEXIST_ENTRY_ID++);
		return newInstance;
	}

	/**
	 * 使用主键删除实体
	 * 
	 * @param entryID
	 *            实体主键
	 * @return 是否删除成功
	 */
	protected boolean deleteByID(final long entryID) {
		return getDaoImpl().deleteByID(entryID);
	}

	/**
	 * 获取单表dao实现，以测试单表通用字段和行为
	 * 
	 * @return 单表dao
	 */
	protected ISingleTableDao<T> getDaoImpl() {
		@SuppressWarnings("unchecked")
		final Class<? extends RawDaoTest<T>> testBeanClass = (Class<? extends RawDaoTest<T>>) this
				.getClass();
		return beanBuffer.getDaoBean(testBeanClass, applicationContext);
	}

	/**
	 * 新增信息
	 * 
	 * @param entry
	 *            数据实体
	 * @return 主键
	 */
	protected long insert(final T entry) {
		return getDaoImpl().insert(entry);
	}

	/**
	 * 使用主键查询实体
	 * 
	 * @param entryID
	 *            主键id
	 * @return 对应的实体
	 */
	protected T queryByID(final long entryID) {
		return getDaoImpl().queryByID(entryID);
	}

	/**
	 * 判断在做属性检查时是否需要过滤固定属性名
	 * 
	 * @param fieldName
	 *            属性名称
	 * @return 是否过滤
	 */
	protected boolean shouldIgnoreField(final String fieldName) {
		return false;
	}

	/**
	 * 更新数据
	 * 
	 * @param toBeUpdate
	 *            待更新测试数据
	 * @return 是否更新成功
	 */
	protected boolean update(final T toBeUpdate) {
		return getDaoImpl().update(toBeUpdate);
	}
}