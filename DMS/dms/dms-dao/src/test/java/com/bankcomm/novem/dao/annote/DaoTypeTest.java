/**
 * 
 */
package com.bankcomm.novem.dao.annote;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.bankcomm.novem.dao.BaseDaoTest;
import com.bankcomm.novem.dao.ISingleTableDao;
import com.bankcomm.novem.dao.SingleTableDao;
import com.bankcomm.novem.dao.utils.MetaDataExtractor;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * 测试dao类型
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-11-8
 * 
 */
public class DaoTypeTest extends BaseDaoTest<DaoType> {

	/**
	 * 测试是否存在重复的dao定义
	 */
	@Test
	public void testDupDaoType() {
		final Map<SingleTableDaoKey, ISingleTableDao<? extends BaseEntry>> daoContainer = new HashMap<SingleTableDaoKey, ISingleTableDao<? extends BaseEntry>>();
		@SuppressWarnings("rawtypes")
		final Collection<SingleTableDao> singleTableDaoMap = applicationContext
				.getBeansOfType(SingleTableDao.class).values();
		for (final ISingleTableDao<? extends BaseEntry> dao : singleTableDaoMap) {
			final Class<? extends BaseEntry> entryClass = MetaDataExtractor
					.extractEntryClassInfo(dao.getClass());
			final DaoTypeEnum daoType = DaoTypeProcessor.extractDaoType(dao
					.getClass());
			final SingleTableDaoKey key = new SingleTableDaoKey();
			key.setType(daoType);
			key.setEntryClass(entryClass);
			if (daoContainer.containsKey(key)) {
				Assert.assertEquals("每个entry只能有一个daotype定义的dao实现：" + key,
						daoContainer.get(key), dao);
			}
			daoContainer.put(key, dao);
		}
	}

	/**
	 * DaoTypeEnum.COPY和DaoTypeEnum.ORIGIN必须成对出现
	 */
	@Test
	public void testPairsDaoType() {
		final Map<Class<? extends BaseEntry>, SingleTableCluster> clusterMap = extractClusterMap();

		for (final SingleTableCluster typeMap : clusterMap.values()) {
			Assert.assertTrue(
					typeMap
							+ "没有被正确定义，DaoTypeEnum.COPY和DaoTypeEnum.ORIGIN必须成对出现",
					typeMap.containsKey(DaoTypeEnum.COPY)
							&& typeMap.containsKey(DaoTypeEnum.ORIGIN));
		}
	}

	/**
	 * @return
	 */
	private Map<Class<? extends BaseEntry>, SingleTableCluster> extractClusterMap() {
		final Map<Class<? extends BaseEntry>, SingleTableCluster> clusterMap = new HashMap<Class<? extends BaseEntry>, SingleTableCluster>();
		@SuppressWarnings("rawtypes")
		final Collection<SingleTableDao> singleTableDaoMap = applicationContext
				.getBeansOfType(SingleTableDao.class).values();
		for (final ISingleTableDao<? extends BaseEntry> dao : singleTableDaoMap) {
			final DaoTypeEnum daoType = DaoTypeProcessor.extractDaoType(dao
					.getClass());
			if ((daoType == null) || (daoType == DaoTypeEnum.HISTORY)) {
				continue;
			}

			final Class<? extends BaseEntry> classEntry = MetaDataExtractor
					.extractEntryClassInfo(dao.getClass());
			if (!clusterMap.containsKey(classEntry)) {
				clusterMap.put(classEntry, new SingleTableCluster());
			}

			final SingleTableCluster typeMap = clusterMap.get(classEntry);
			typeMap.put(daoType, dao);
		}
		return clusterMap;
	}
}
