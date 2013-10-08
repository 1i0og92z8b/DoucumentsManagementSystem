package com.bankcomm.novem.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.comm.spring.AnnotationClassScanner;
import com.bankcomm.novem.comm.spring.MybatisMapperScanner;
import com.bankcomm.novem.comm.spring.SubCLassScanner;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-12-26
 * 
 */
@Slf4j
public class TestChecker {
	private static final String COM_BANKCOMM_RISK = "com/bankcomm/risk";

	private static final String TEST = "Test";

	/**
	 * 判断继承了SingleTableDao的所有类是否存在对应的测试用例
	 */
	@Test
	public void listAnnotationBeanWithoutTest() {
		@SuppressWarnings("rawtypes")
		final Collection<Class<SingleTableDao>> repositoryClassList = AnnotationClassScanner
				.scan(Repository.class, "com/bankcomm/risk/dao");

		final List<String> beansWithoutTest = listClassWithoutTest(repositoryClassList);

		Assert.assertEquals(
				beansWithoutTest.size() + "/" + repositoryClassList.size()
						+ " TestCase Class Not Found:" + beansWithoutTest, 0,
				beansWithoutTest.size());
	}

	/**
	 * 判断继承了SingleTableDao的所有类是否存在对应的测试用例
	 */
	@Test
	public void listDaoWithoutTest() {
		@SuppressWarnings("rawtypes")
		final Collection<Class<SingleTableDao>> daoClassList = SubCLassScanner
				.scan(SingleTableDao.class, "com/bankcomm/risk/dao");

		final List<String> daosWithoutTest = listClassWithoutTest(daoClassList);

		Assert.assertEquals(daosWithoutTest.size() + "/" + daoClassList.size()
				+ " TestCase Class Not Found:" + daosWithoutTest, 0,
				daosWithoutTest.size());
	}

	/**
	 * 判断是否在测试用例内测试了接口，而不是实现
	 */
	@Test
	public void listInterfaceInTestCase() {
		@SuppressWarnings("rawtypes")
		final Collection<Class<SingleTableDao>> daoClassList = SubCLassScanner
				.scan(SingleTableDao.class, COM_BANKCOMM_RISK);

		final List<String> testInterfaceList = new ArrayList<String>();
		for (@SuppressWarnings("rawtypes")
		final Class<SingleTableDao> daoClass : daoClassList) {
			final String className = daoClass.getName();
			final String testClassName = className + TEST;
			Class<?> testClass = null;
			try {
				testClass = Class.forName(testClassName);
			} catch (final ClassNotFoundException e) {
				continue;
			} catch (final NoClassDefFoundError e) {
				continue;
			}

			final Field[] fields = testClass.getDeclaredFields();
			if (fields.length == 0) {
				continue;
			}

			boolean hasFound = false;
			for (final Field field : fields) {
				if (field.getType() == daoClass) {
					hasFound = true;
					continue;
				}
			}
			if (hasFound) {
				continue;
			}

			log.error(testClass.getSimpleName() + " don't have a field of "
					+ daoClass.getSimpleName());

			testInterfaceList.add(testClass.getSimpleName());
		}

		Assert.assertEquals(
				testInterfaceList.size() + "/" + daoClassList.size()
						+ " TestCase Class test infterface:"
						+ testInterfaceList, 0, testInterfaceList.size());
	}

	/**
	 * 检查所有没有测试用例的mybatis mapper
	 */
	@Test
	public void listMybatisMapperWithoutTest() {
		final MybatisMapperScanner scanner = new MybatisMapperScanner();
		final Set<Class<Object>> mybatisMapperList = scanner
				.scanMapper("com.bankcomm.novem.dao");
		final List<String> mappersWithoutTest = listClassWithoutTest(mybatisMapperList);

		Assert.assertEquals(
				mappersWithoutTest.size() + "/" + mybatisMapperList.size()
						+ " Mybatis mapper interface without test:"
						+ mappersWithoutTest, 0, mappersWithoutTest.size());
	}

	/**
	 * 测试继承了SingleTableDao的实现是否是public，要求使用default
	 */
	@Test
	public void listPublicDao() {
		@SuppressWarnings("rawtypes")
		final Collection<Class<SingleTableDao>> daoClassList = SubCLassScanner
				.scan(SingleTableDao.class, "com/bankcomm/risk/dao");

		final List<String> publicList = new ArrayList<String>();
		for (@SuppressWarnings("rawtypes")
		final Class<SingleTableDao> daoClass : daoClassList) {
			final int modifiers = daoClass.getModifiers();
			if (Modifier.isPublic(modifiers)) {
				publicList.add(daoClass.getName());
				log.error("Public access:" + daoClass.getName());
			}
		}

		Assert.assertEquals(publicList.size() + "/" + daoClassList.size()
				+ " DaoImpl Class is public: " + publicList, 0,
				publicList.size());
	}

	/**
	 * 检查dao实现内是否以map做为接口上的参数
	 */
	@Test
	public void testMapInterface() {
		@SuppressWarnings("rawtypes")
		final Collection<Class<SingleTableDao>> daoClassList = SubCLassScanner
				.scan(SingleTableDao.class, COM_BANKCOMM_RISK);

		final List<String> methodWithMapParameterList = new ArrayList<String>();
		for (@SuppressWarnings("rawtypes")
		final Class<SingleTableDao> daoClass : daoClassList) {
			final Method[] methods = daoClass.getDeclaredMethods();
			for (final Method method : methods) {
				final Class<?>[] parameters = method.getParameterTypes();
				if (parameters.length > 1) {
					continue;
				}
				for (final Class<?> p : parameters) {
					if (Map.class.isAssignableFrom(p)) {
						methodWithMapParameterList.add(daoClass + "."
								+ method.getName());
					}
				}
			}
		}

		for (final String methodWithMapParameter : methodWithMapParameterList) {
			log.error(methodWithMapParameter);
		}

		Assert.assertEquals(methodWithMapParameterList.size() + "/"
				+ daoClassList.size() + " method has only one map parameter: "
				+ methodWithMapParameterList, 0,
				methodWithMapParameterList.size());
	}

	/**
	 * 测试继承了SingleTableDao的实现是否覆盖了singletabledao中的同名protected方法
	 */
	@Test
	public void testOverrideProtectedMethodOfSingleTableDao() {
		@SuppressWarnings("rawtypes")
		final Collection<Class<SingleTableDao>> daoClassList = SubCLassScanner
				.scan(SingleTableDao.class, "com/bankcomm/risk/dao");

		final List<String> protectedMethodNameList = new ArrayList<String>();
		for (final Method singleTableDaoMethod : SingleTableDao.class
				.getDeclaredMethods()) {
			if (Modifier.isProtected(singleTableDaoMethod.getModifiers())) {
				protectedMethodNameList.add(singleTableDaoMethod.getName());
			}
		}

		final List<String> overrideList = new ArrayList<String>();
		for (@SuppressWarnings("rawtypes")
		final Class<SingleTableDao> daoClass : daoClassList) {
			final Method[] methods = daoClass.getDeclaredMethods();
			for (final Method method : methods) {
				final String methodName = method.getName();
				if (!protectedMethodNameList.contains(methodName)) {
					continue;
				}

				overrideList.add(daoClass.getName());
				log.error(method + " may override " + methodName);
			}
		}

		Assert.assertEquals(
				overrideList.size()
						+ "/"
						+ daoClassList.size()
						+ " DaoImpl Class override protected method of SingleTableDao: "
						+ overrideList, 0, overrideList.size());
	}

	/**
	 * 判断继承了SingleTableDao的所有类是否使用了@Repository注解
	 */
	@Test
	public void testSingleTableDaoAnnotation() {
		@SuppressWarnings("rawtypes")
		final Collection<Class<SingleTableDao>> daoClassList = SubCLassScanner
				.scan(SingleTableDao.class, "com/bankcomm/risk/dao");

		final List<String> notRepositoryClass = new ArrayList<String>();
		for (@SuppressWarnings("rawtypes")
		final Class<SingleTableDao> daoCLass : daoClassList) {
			if (!daoCLass.isAnnotationPresent(Repository.class)) {
				notRepositoryClass.add(daoCLass.getName());
			}
		}

		Assert.assertEquals(
				notRepositoryClass.size()
						+ "/"
						+ daoClassList.size()
						+ " SingleTabelDao Class Not annotationed by @Repository!",
				0, notRepositoryClass.size());
	}

	/**
	 * 判断某个类的测试用是否存在
	 * 
	 * @param toCheck
	 * @return
	 */
	private boolean isTestExits(final Class<?> toCheck) {
		final String className = toCheck.getName();
		final String testClassName = className + TEST;

		try {
			Class.forName(testClassName);
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		} catch (final NoClassDefFoundError e) {
			return false;
		}
	}

	private <E> List<String> listClassWithoutTest(
			final Collection<Class<E>> daoClassList) {
		final List<String> notExistList = new ArrayList<String>();
		for (final Class<?> daoClass : daoClassList) {
			if (!isTestExits(daoClass)) {
				log.error("Can't found test for:" + daoClass.getName());
				notExistList.add(daoClass.getName());
			}
		}
		return notExistList;
	}
}
