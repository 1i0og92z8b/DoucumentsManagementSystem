/**
 * 
 */
package com.bankcomm.novem.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.comm.spring.SubCLassScanner;
import com.bankcomm.novem.dao.common.ICommonDao;
import com.bankcomm.novem.dao.impl.GenericHisDaoImpl;
import com.bocom.jump.component.euif.dao.AuthorityDao;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2013-3-12
 * 
 */
@Slf4j
public class TestOrphanDao {
	private static final String COM_BANKCOMM_RISK = "com/bankcomm/risk";
	private static final String TEST = "Test";

	/**
	 * 检查所有不是SingleTableDao子类的dao实现
	 */
	@Test
	public void listOrphanDaos() {
		final Collection<Class<Object>> daoClassList = SubCLassScanner.scan(
				Object.class, "com.bankcomm.novem.dao");

		final List<Class<Object>> beanClassList = new ArrayList<Class<Object>>();
		for (final Class<Object> classInDao : daoClassList) {
			if (classInDao.isAnnotationPresent(Repository.class)) {
				beanClassList.add(classInDao);
			}
		}

		final Collection<Class<?>> ignoreParentClasses = createIgnoreParents();

		final List<String> orphanList = new ArrayList<String>();
		for (final Class<Object> classInDao : beanClassList) {
			boolean isOrphan = true;
			for (final Class<?> ignoreClass : ignoreParentClasses) {
				if (ignoreClass.isAssignableFrom(classInDao)) {
					isOrphan = false;
					break;
				}
			}

			if (isOrphan) {
				orphanList.add(classInDao.getName());
				log.error("Orphan DaoImpl not inherit from SingleTableDao:"
						+ classInDao.getName());
			}

		}

		Assert.assertEquals(orphanList.size() + "/" + daoClassList.size()
				+ " DaoImpl Classe are orphans: " + orphanList, 0,
				orphanList.size());
	}

	/**
	 * 所有继承了SingleTableDao的类存在对应的全表测试用例,
	 * 从SingleTableDaoTest或者SingleTableWithOutIncrementTest中继承
	 */
	@Test
	public void listOrphanTest() {
		@SuppressWarnings("rawtypes")
		final List<Class<SingleTableDao>> daoClassList = SubCLassScanner.scan(
				SingleTableDao.class, COM_BANKCOMM_RISK);

		final List<String> orphanList = new ArrayList<String>();
		for (final Class<?> daoClass : daoClassList) {
			final String className = daoClass.getName();
			final String testClassName = className + TEST;
			try {
				final Class<?> testClass = Class.forName(testClassName);
				if (SingleTableDaoTest.class.isAssignableFrom(testClass)) {
					continue;
				}
				if (SingleTableWithOutIncrementTest.class
						.isAssignableFrom(testClass)) {
					continue;
				}
				orphanList.add(daoClass.getName());
			} catch (final ClassNotFoundException e) {
				log.error("ClassNotFoundException:" + testClassName);
				continue;
			} catch (final NoClassDefFoundError e) {
				log.error("NoClassDefFoundError:" + testClassName);
				continue;
			}
		}

		Assert.assertEquals(
				orphanList.size()
						+ "/"
						+ daoClassList.size()
						+ "个Dao的测试用例没有继承RawDaoTest，请根据表类型选择继承SingleTableDaoTest或者SingleTableDaoTestWithoutIncrement:"
						+ orphanList, 0, orphanList.size());
	}

	/**
	 * @return
	 */
	private Collection<Class<?>> createIgnoreParents() {
		final Map<String, Class<?>> ignoreParentMap = new HashMap<String, Class<?>>();
		ignoreParentMap.put("公共dao只用于抽取时间,不是单表", ICommonDao.class);
		ignoreParentMap.put("枚举类没有单表操作", Enum.class);
		ignoreParentMap.put("已经是单表操作了", SingleTableDao.class);
		ignoreParentMap.put("通用历史dao", GenericHisDaoImpl.class);

		ignoreParentMap.put("该接口用于解决Mybatis升级问题引起的Jump Sqlmap异常，外系统表",
				AuthorityDao.class);
		return ignoreParentMap.values();
	}
}
