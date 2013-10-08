package com.bankcomm.novem.dao;

import static com.bankcomm.novem.dao.Version.initVersion;
import static com.bankcomm.novem.dao.Version.nextVersion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.bankcomm.novem.dao.annote.DaoTypeProcessor;
import com.bankcomm.novem.dao.common.CommonDao;
import com.bankcomm.novem.dao.impl.GenericHisDaoImpl;
import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.VersionEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-12-8
 * @param <T>
 *            数据类型信息
 * 
 */
@Slf4j
public abstract class GenericHisDaoImplTest<T extends BaseEntry> extends
		BaseDaoTest<T> {
	private static final long MINUS_ID = -1;
	private static final long TEMP_ID = 1283890123098012309L;
	private static final long UNEXIST_ID = 12737980784798L;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private GenericHisDaoImpl dao;
	private final String dbSchema;

	/**
	 * 
	 */
	public GenericHisDaoImplTest() {
		dbSchema = DaoTypeProcessor.extractDaoSchema(this.getClass());
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsert() {
		final T toInsert = prepareData(initVersion());

		final T entry = dao.queryByIDAndVersion(toInsert.getPrimaryKey(),
				toInsert.getVersion(), getEntryInfo(), dbSchema);

		assertNotNull(entry);
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertForCreateTime() {
		final T toInsert = prepareData(initVersion());

		final T entry = dao.queryByIDAndVersion(toInsert.getPrimaryKey(),
				toInsert.getVersion(), getEntryInfo(), dbSchema);

		assertNotNull(entry.getCreateTime());
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertForUpdateTime() {
		final T toInsert = prepareData(initVersion());

		final T queryEntry = dao.queryByIDAndVersion(toInsert.getPrimaryKey(),
				toInsert.getVersion(), getEntryInfo(), dbSchema);

		assertNotNull(queryEntry.getUpdateTime());
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertForVersion() {
		final T toInsert = prepareData(initVersion());

		final T entry = dao.queryByIDAndVersion(toInsert.getPrimaryKey(),
				toInsert.getVersion(), getEntryInfo(), dbSchema);

		assertEquals(toInsert.getPrimaryKey(), entry.getPrimaryKey());
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertTwiceWithDiffVersion() {
		final T entry = prepareData(initVersion());

		prepareData(nextVersion(initVersion()));

		final List<T> entryList = dao.queryHisByID(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), entryList.get(0).getVersion());
		assertEquals(nextVersion(initVersion()), entryList.get(1).getVersion());
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertTwiceWithSameVersion() {
		final T entry = prepareData(initVersion());

		if (entry.getClass().isAnnotationPresent(VersionEntry.class)) {
			try {
				dao.insert(entry, dbSchema);
				checkExceptionRaise();
			} catch (final DuplicateKeyException e) {
				log.info(e.getMessage());
			}
		} else {
			dao.insert(entry, dbSchema);
			assertEquals(nextVersion(initVersion()), dao.queryLastVersion(
					entry.getPrimaryKey(), getEntryInfo(), dbSchema));
		}
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertWithCreateTime() {
		final T toInsert = createEntry();
		toInsert.setPrimaryKey(TEMP_ID);
		toInsert.setVersion(initVersion());
		toInsert.setCreateTime(commonDao.queryCurrentTime());
		final long entryID = dao.insert(toInsert, dbSchema);

		final T queryEntry = dao.queryByIDAndVersion(entryID,
				toInsert.getVersion(), getEntryInfo(), dbSchema);

		assertTrue(toInsert.getCreateTime().before(queryEntry.getCreateTime()));
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertWithMinusID() {
		final T entry = createEntry();
		entry.setPrimaryKey(MINUS_ID);
		entry.setVersion(initVersion());

		try {
			dao.insert(entry, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertWithMinusVersion() {
		final T entry = createEntry();
		entry.setVersion(-1);
		if (entry.getClass().isAnnotationPresent(VersionEntry.class)) {
			try {
				dao.insert(entry, dbSchema);
				checkExceptionRaise();
			} catch (final IllegalArgumentException e) {
				assertEquals("有版本信息的实体在做历史记录时版本号必须大于0", e.getMessage());
			}
		} else {
			dao.insert(entry, dbSchema);
			assertEquals(initVersion(), dao.queryLastVersion(
					entry.getPrimaryKey(), getEntryInfo(), dbSchema));
		}
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertWithOutID() {
		final T entry = createEntry();
		entry.setPrimaryKey(0);
		entry.setVersion(initVersion());

		try {
			dao.insert(entry, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertWithOutVersion() {
		final T entry = createEntry();
		if (entry.getClass().isAnnotationPresent(VersionEntry.class)) {
			try {
				dao.insert(entry, dbSchema);
				checkExceptionRaise();
			} catch (final IllegalArgumentException e) {
				assertEquals("有版本信息的实体在做历史记录时版本号必须大于0", e.getMessage());
			}
			return;
		}

		dao.insert(entry, dbSchema);
		assertEquals(initVersion(), dao.queryLastVersion(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema));
	}

	/**
	 * Test method for {@link GenericHisDaoImpl#insert(BaseEntry, String)}
	 */
	@Test
	public void testInsertWithUpdateTime() {
		final T toInsert = createEntry();
		toInsert.setPrimaryKey(TEMP_ID);
		toInsert.setVersion(initVersion());
		toInsert.setUpdateTime(commonDao.queryCurrentTime());
		final long entryID = dao.insert(toInsert, dbSchema);

		final T queryEntry = dao.queryByIDAndVersion(entryID,
				toInsert.getVersion(), getEntryInfo(), dbSchema);

		assertTrue(toInsert.getUpdateTime().before(queryEntry.getUpdateTime()));
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryByIDAndVersion(long, int, Class, String)}
	 */
	@Test
	public void testQueryByIDAndVersion() {
		final T entry = prepareData(initVersion());

		final T queryEntry = dao.queryByIDAndVersion(entry.getPrimaryKey(),
				initVersion(), getEntryInfo(), dbSchema);

		assertEquals(initVersion(), queryEntry.getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryByIDAndVersion(long, int, Class, String)}
	 */
	@Test
	public void testQueryByIDAndVersionForFirstVersionDataWithTwoVersionData() {
		final T entry = prepareData(initVersion());

		prepareData(nextVersion(initVersion()));

		final T queryEntry = dao.queryByIDAndVersion(entry.getPrimaryKey(),
				initVersion(), getEntryInfo(), dbSchema);

		assertEquals(initVersion(), queryEntry.getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryByIDAndVersion(long, int, Class, String)}
	 */
	@Test
	public void testQueryByIDAndVersionForSecondVersionData() {
		final T entry = prepareData(initVersion());
		prepareData(nextVersion(initVersion()));

		final T queryEntry = dao.queryByIDAndVersion(entry.getPrimaryKey(),
				entry.getVersion(), getEntryInfo(), dbSchema);

		assertEquals(entry.getVersion(), queryEntry.getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryByIDAndVersion(long, int, Class, String)}
	 */
	@Test
	public void testQueryByIDAndVersionWithMinusID() {
		try {
			dao.queryByIDAndVersion(MINUS_ID, initVersion(), getEntryInfo(),
					dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryByIDAndVersion(long, int, Class, String)}
	 */
	@Test
	public void testQueryByIDAndVersionWithOutData() {
		final T queryEntry = dao.queryByIDAndVersion(UNEXIST_ID, initVersion(),
				getEntryInfo(), dbSchema);

		assertNull(queryEntry);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryByIDAndVersion(long, int, Class, String)}
	 */
	@Test
	public void testQueryByIDAndVersionWithOutEntryInfo() {
		try {
			dao.queryByIDAndVersion(TEMP_ID, initVersion(), null, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkNullEntryInfoResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryByIDAndVersion(long, int, Class, String)}
	 */
	@Test
	public void testQueryByIDAndVersionWithUnExistVersion() {
		final T entry = prepareData(initVersion());

		final T queryEntry = dao.queryByIDAndVersion(entry.getPrimaryKey(),
				898908090, getEntryInfo(), dbSchema);

		assertNull(queryEntry);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstData(long, Class, String)}
	 */
	@Test
	public void testQueryFirstData() {
		final T entry = prepareData(initVersion());

		final T firstData = dao.queryFirstData(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), firstData.getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstData(long, Class, String)}
	 */
	@Test
	public void testQueryFirstDataWithMinusID() {
		try {
			dao.queryFirstData(MINUS_ID, getEntryInfo(), dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstData(long, Class, String)}
	 */
	@Test
	public void testQueryFirstDataWithOutData() {
		final T firstData = dao.queryFirstData(UNEXIST_ID, getEntryInfo(),
				dbSchema);

		assertNull(firstData);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstData(long, Class, String)}
	 */
	@Test
	public void testQueryFirstDataWithOutEntryInfo() {
		try {
			dao.queryFirstData(TEMP_ID, null, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkNullEntryInfoResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstData(long, Class, String)}
	 */
	@Test
	public void testQueryFirstDataWithTwoData() {
		final T entry = prepareData(initVersion());
		prepareData(nextVersion(initVersion()));

		final T firstData = dao.queryFirstData(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), firstData.getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstVersion(long, Class, String)}
	 */
	@Test
	public void testQueryFirstVersion() {
		final T entry = prepareData(initVersion());

		final int firstVersion = dao.queryFirstVersion(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), firstVersion);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstVersion(long, Class, String)}
	 */
	@Test
	public void testQueryFirstVersionWithMinusID() {
		try {
			dao.queryFirstVersion(MINUS_ID, getEntryInfo(), dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstVersion(long, Class, String)}
	 */
	@Test
	public void testQueryFirstVersionWithOutData() {
		final int firstVersion = dao.queryFirstVersion(UNEXIST_ID,
				getEntryInfo(), dbSchema);

		assertEquals(0, firstVersion);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstVersion(long, Class, String)}
	 */
	@Test
	public void testQueryFirstVersionWithOutEntryInfo() {
		try {
			dao.queryFirstVersion(TEMP_ID, null, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkNullEntryInfoResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryFirstVersion(long, Class, String)}
	 */
	@Test
	public void testQueryFirstVersionWithTwoData() {
		final T entry = prepareData(initVersion());
		prepareData(nextVersion(initVersion()));

		final int firstVersion = dao.queryFirstVersion(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), firstVersion);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryHisByID(long, Class, String)}
	 */
	@Test
	public void testQueryHisByID() {
		final T entry = prepareData(initVersion());

		final List<T> entryList = dao.queryHisByID(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), entryList.get(0).getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryHisByID(long, Class, String)}
	 */
	@Test
	public void testQueryHisByIDForOrderWithTwoData() {
		final T entry = prepareData(nextVersion(initVersion()));
		prepareData(initVersion());

		final List<T> entryList = dao.queryHisByID(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), entryList.get(0).getVersion());
		assertEquals(nextVersion(initVersion()), entryList.get(1).getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryHisByID(long, Class, String)}
	 */
	@Test
	public void testQueryHisByIDForSize() {
		final T entry = prepareData(initVersion());

		final List<T> entryList = dao.queryHisByID(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(1, entryList.size());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryHisByID(long, Class, String)}
	 */
	@Test
	public void testQueryHisByIDForSizeWithTwoData() {
		final T entry = prepareData(initVersion());
		prepareData(nextVersion(initVersion()));

		final List<T> entryList = dao.queryHisByID(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(2, entryList.size());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryHisByID(long, Class, String)}
	 */
	@Test
	public void testQueryHisByIDWithMinusID() {
		try {
			dao.queryHisByID(MINUS_ID, getEntryInfo(), dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryHisByID(long, Class, String)}
	 */
	@Test
	public void testQueryHisByIDWithOutData() {
		final List<T> entryList = dao.queryHisByID(UNEXIST_ID, getEntryInfo(),
				dbSchema);

		assertEquals(0, entryList.size());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryHisByID(long, Class, String)}
	 */
	@Test
	public void testQueryHisByIDWithOutEntryInfo() {
		try {
			dao.queryHisByID(TEMP_ID, null, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkNullEntryInfoResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastData(long, Class, String)}
	 */
	@Test
	public void testQueryLastData() {
		final T entry = prepareData(initVersion());

		final T lastData = dao.queryLastData(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), lastData.getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastData(long, Class, String)}
	 */
	@Test
	public void testQueryLastDataWithMinusID() {
		try {
			dao.queryLastData(MINUS_ID, getEntryInfo(), dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastData(long, Class, String)}
	 */
	@Test
	public void testQueryLastDataWithOutData() {
		final T lastData = dao.queryLastData(UNEXIST_ID, getEntryInfo(),
				dbSchema);

		assertNull(lastData);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastData(long, Class, String)}
	 */
	@Test
	public void testQueryLastDataWithOutEntryInfo() {
		try {
			dao.queryLastData(TEMP_ID, null, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkNullEntryInfoResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastData(long, Class, String)}
	 */
	@Test
	public void testQueryLastDataWithTwoData() {
		final T entry = prepareData(initVersion());
		prepareData(nextVersion(initVersion()));

		final T lastData = dao.queryLastData(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(nextVersion(initVersion()), lastData.getVersion());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastVersion(long, Class, String)}
	 */
	@Test
	public void testQueryLastVersion() {
		final T entry = prepareData(initVersion());

		final int lastVersion = dao.queryLastVersion(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(initVersion(), lastVersion);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastVersion(long, Class, String)}
	 */
	@Test
	public void testQueryLastVersionWithMinusID() {
		try {
			dao.queryLastVersion(MINUS_ID, getEntryInfo(), dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastVersion(long, Class, String)}
	 */
	@Test
	public void testQueryLastVersionWithOutData() {
		final int lastVersion = dao.queryLastVersion(UNEXIST_ID,
				getEntryInfo(), dbSchema);

		assertEquals(0, lastVersion);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastVersion(long, Class, String)}
	 */
	@Test
	public void testQueryLastVersionWithOutEntryInfo() {
		try {
			dao.queryLastVersion(TEMP_ID, null, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkNullEntryInfoResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryLastVersion(long, Class, String)}
	 */
	@Test
	public void testQueryLastVersionWithTwoData() {
		final T entry = prepareData(initVersion());

		prepareData(nextVersion(initVersion()));

		final int lastVersion = dao.queryLastVersion(entry.getPrimaryKey(),
				getEntryInfo(), dbSchema);

		assertEquals(nextVersion(initVersion()), lastVersion);
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionList() {
		final T entry = prepareData(initVersion());

		final List<Integer> versionList = dao.queryVersionList(
				entry.getPrimaryKey(), getEntryInfo(), dbSchema);

		assertEquals(initVersion(), versionList.get(0).intValue());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionListForOrder() {
		final T entry = createEntry();
		final int smallVersion = initVersion();
		final int largeVersion = nextVersion(smallVersion);

		entry.setVersion(largeVersion);
		dao.insert(entry, dbSchema);

		entry.setVersion(smallVersion);
		dao.insert(entry, dbSchema);

		final List<Integer> versionList = dao.queryVersionList(
				entry.getPrimaryKey(), getEntryInfo(), dbSchema);

		assertEquals(smallVersion, versionList.get(0).intValue());
		assertEquals(largeVersion, versionList.get(1).intValue());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionListForSize() {
		final T entry = prepareData(initVersion());

		final List<Integer> versionList = dao.queryVersionList(
				entry.getPrimaryKey(), getEntryInfo(), dbSchema);

		assertEquals(1, versionList.size());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionListForSizeWithTwoData() {
		final T entry = prepareData(initVersion());
		prepareData(nextVersion(initVersion()));

		final List<Integer> versionList = dao.queryVersionList(
				entry.getPrimaryKey(), getEntryInfo(), dbSchema);

		assertEquals(2, versionList.size());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionListWithMinusID() {
		try {
			dao.queryVersionList(MINUS_ID, getEntryInfo(), dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkMinusIDResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionListWithOutData() {
		final List<Integer> versionList = dao.queryVersionList(UNEXIST_ID,
				getEntryInfo(), dbSchema);

		assertEquals(0, versionList.size());
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionListWithoutEntryInfo() {
		try {
			dao.queryVersionList(TEMP_ID, null, dbSchema);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			checkNullEntryInfoResult(e);
		}
	}

	/**
	 * Test method for
	 * {@link GenericHisDaoImpl#queryVersionList(long, Class, String)}
	 */
	@Test
	public void testQueryVersionListWithTwoData() {
		final T entry = prepareData(initVersion());

		prepareData(nextVersion(initVersion()));

		final List<Integer> versionList = dao.queryVersionList(
				entry.getPrimaryKey(), getEntryInfo(), dbSchema);

		assertEquals(initVersion(), versionList.get(0).intValue());
		assertEquals(nextVersion(initVersion()), versionList.get(1).intValue());
	}

	/**
	 * @param e
	 */
	private void checkMinusIDResult(final IllegalArgumentException e) {
		assertEquals("主键必须大于0", e.getMessage());
	}

	/**
	 * @param e
	 */
	private void checkNullEntryInfoResult(final IllegalArgumentException e) {
		assertEquals("实体类型信息不能为null", e.getMessage());
	}

	/**
	 * @return
	 */
	private T createEntry() {
		try {
			final T entry = extractEntryClassInfo().newInstance();
			entry.setPrimaryKey(TEMP_ID);
			return entry;
		} catch (final Exception e) {
			assertTrue("实例化异常" + extractEntryClassInfo().getName(), false);
		}
		return null;
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
	 * @return
	 */
	private Class<T> getEntryInfo() {
		return extractEntryClassInfo();
	}

	/**
	 * 准备数据
	 * 
	 * @param version
	 *            数据版本
	 * @return 已插入数据库的数据
	 */
	private T prepareData(final int version) {
		final T entry = createEntry();
		entry.setVersion(version);
		dao.insert(entry, dbSchema);
		return entry;
	}
}