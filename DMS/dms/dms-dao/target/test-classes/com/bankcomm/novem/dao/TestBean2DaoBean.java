/**
 * 
 */
package com.bankcomm.novem.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.comm.spring.SubCLassScanner;
import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.dao.annote.DaoTypeProcessor;
import com.bankcomm.novem.dao.utils.MetaDataExtractor;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * 该类用于从测试类中提取出待测试的dao的类型信息，并做好缓存
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-12-13
 * 
 * @param <T>
 *            待测试entry
 * 
 */
@Service
@Slf4j
class TestBean2DaoBean<T extends BaseEntry> {
	/**
	 * 用于支持各种类型的daotype
	 */
	private static final Map<DaoTypeEnum, String> CLASS_NAME_REPLACE = new HashMap<DaoTypeEnum, String>();

	static {
		CLASS_NAME_REPLACE.put(null, "DaoImpl");
		CLASS_NAME_REPLACE.put(DaoTypeEnum.ORIGIN, "DaoImpl");
		CLASS_NAME_REPLACE.put(DaoTypeEnum.COPY, "CopyDaoImpl");
		CLASS_NAME_REPLACE.put(DaoTypeEnum.HISTORY, "HisDaoImpl");
	}

	private final Map<Class<? extends RawDaoTest<T>>, Class<ISingleTableDao<T>>> classBuffer = new HashMap<Class<? extends RawDaoTest<T>>, Class<ISingleTableDao<T>>>();

	/**
	 * 从spring上下文内根据测试bean获取对应的daobean，同时会对dao和测试用例做一些规范检查
	 * 
	 * @param testBeanClass
	 *            测试bean
	 * @param context
	 *            springcontext
	 * @return 对应的daobean
	 */
	public ISingleTableDao<T> getDaoBean(
			final Class<? extends RawDaoTest<T>> testBeanClass,
			final ApplicationContext context) {
		final Class<ISingleTableDao<T>> daoClass = extractTestClass(testBeanClass);
		// 此处必须使用org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests.applicationContext
		// 如果使用com.bankcomm.novem.comm.batch.ManageSpringBeans.applicationContext取，则拿到的不是一个bean。
		// 上述两个applicationcontext不是同一个
		final ISingleTableDao<T> daoBean = context.getBean(daoClass);
		return daoBean;
	}

	/**
	 * 每个测试类如果存在成员变量，必须有至少一个对应的被测试类而不能仅存在该类的接口
	 * 
	 * @param testClass
	 * @param daoClass
	 */
	private void assertDaoImplInTestClass(
			final Class<? extends RawDaoTest<T>> testClass,
			final Class<ISingleTableDao<T>> daoClass) {
		final Field[] fields = testClass.getDeclaredFields();
		if (fields.length == 0) {
			return;
		}
		if (fields.length == 1) {
			// 部分覆盖率工具会生成以$开头的成员变量，一般不会自己去写这样的变量
			if (StringUtils.startsWith(fields[0].getName(), "$")) {
				return;
			}
		}

		boolean hasFound = false;
		for (final Field field : fields) {
			if (StringUtils.startsWith(fields[0].getName(), "$")) {
				continue;
			}
			if (field.getType() == daoClass) {
				hasFound = true;
				break;
			}
		}

		Assert.assertTrue(testClass.getSimpleName() + " don't have a field of "
				+ daoClass.getSimpleName(), hasFound);
	}

	/**
	 * 确认dao实现类不是public class定义
	 * 
	 * @param daoClass
	 * @return
	 */
	private int assertIsNotPublicDaoImpl(
			final Class<ISingleTableDao<T>> daoClass) {
		final int modifiers = daoClass.getModifiers();
		Assert.assertFalse("Public access:" + daoClass.getName(),
				Modifier.isPublic(modifiers));
		return modifiers;
	}

	/**
	 * 确认在实现类上没有定义以map为参数的方法
	 * 
	 * @param daoClass
	 */
	private void assertNotExistsOfMapParameter(
			final Class<ISingleTableDao<T>> daoClass) {
		final Method[] methods = daoClass.getDeclaredMethods();
		for (final Method method : methods) {
			final Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length > 1) {
				continue;
			}
			for (final Class<?> p : parameters) {
				final boolean isMapParameter = java.util.Map.class
						.isAssignableFrom(p);
				Assert.assertFalse(daoClass + "." + method.getName()
						+ " has a map parameter", isMapParameter);
			}
		}
	}

	/**
	 * @param daoClass
	 */
	private void assertPresentofRepositoryAnnotation(
			final Class<ISingleTableDao<T>> daoClass) {
		Assert.assertTrue(daoClass.getName()
				+ " don't has a @Repository present",
				daoClass.isAnnotationPresent(Repository.class));
	}

	/**
	 * 所有daotype定义的dao不能被重复定义，copy和origin必须成对出现
	 */
	private void checkPairsDaoType(final Type entryType) {
		@SuppressWarnings("rawtypes")
		final Map<DaoTypeEnum, Class<SingleTableDao>> cluster = new HashMap<DaoTypeEnum, Class<SingleTableDao>>();
		@SuppressWarnings("rawtypes")
		final List<Class<SingleTableDao>> daoList = SubCLassScanner.scan(
				SingleTableDao.class, "com/bankcomm/risk/dao");
		for (@SuppressWarnings("rawtypes")
		final Class<SingleTableDao> daoClass : daoList) {
			final DaoTypeEnum daoType = DaoTypeProcessor
					.extractDaoType(daoClass);
			if (entryType != MetaDataExtractor.extractEntryClassInfo(daoClass)) {
				continue;
			}
			Assert.assertFalse("每个DaoTypeEnum只能出现一次" + cluster,
					cluster.containsKey(daoType));
			cluster.put(daoType, daoClass);
		}

		cluster.remove(null);
		cluster.remove(DaoTypeEnum.HISTORY);

		if (cluster.size() > 0) {
			Assert.assertTrue(
					entryType
							+ "的dao实现没有被正确定义，DaoTypeEnum.COPY和DaoTypeEnum.ORIGIN必须成对出现",
					cluster.containsKey(DaoTypeEnum.COPY)
							&& cluster.containsKey(DaoTypeEnum.ORIGIN));
		}
	}

	/**
	 * @param testClassName
	 * @param daoClassName
	 */
	private void checkTestClassNamePrefix(final String testClassName,
			final String daoClassName) {
		Assert.assertTrue("测试类名称[" + testClassName + "]没有以dao实现类名称["
				+ daoClassName + "]+[Test]为前缀",
				StringUtils.startsWith(testClassName, daoClassName + "Test"));
	}

	/**
	 * 从测试类中提取出待测试dao的类型信息
	 * 
	 * 请按规则命名，目前规则例子如下
	 * {@link com.bankcomm.novem.entry.batch.sqltrack.MailInfoEntry}
	 * {@link com.bankcomm.novem.dao.batch.sqltrack.MailInfoDaoImpl}
	 * {@link com.bankcomm.novem.dao.batch.sqltrack.MailInfoDaoImplTest}
	 * 
	 * @param testClass
	 *            测试类类型
	 * 
	 * @return dao类型
	 */
	private Class<ISingleTableDao<T>> extractTestClass(
			final Class<? extends RawDaoTest<T>> testClass) {
		if (classBuffer.get(testClass) != null) {
			return classBuffer.get(testClass);
		}

		for (Class<?> current = testClass; (current != RawDaoTest.class)
				|| (current != Object.class); current = current.getSuperclass()) {
			final Type genericSuperType = current.getGenericSuperclass();
			if (!(genericSuperType instanceof ParameterizedType)) {
				continue;
			}

			final ParameterizedType genericSuperClass = (ParameterizedType) genericSuperType;
			final Type[] actualTypes = genericSuperClass
					.getActualTypeArguments();
			if (actualTypes.length == 0) {
				continue;
			}

			final Type entryType = actualTypes[0];
			if (!(entryType instanceof Class)) {
				continue;
			}

			@SuppressWarnings("unchecked")
			final Class<ISingleTableDao<T>> daoClass = findDaoClass(
					(Class<T>) entryType,
					DaoTypeProcessor.extractDaoType(testClass));

			checkTestClassNamePrefix(testClass.getName(), daoClass.getName());
			assertIsNotPublicDaoImpl(daoClass);
			assertDaoImplInTestClass(testClass, daoClass);
			assertNotExistsOfMapParameter(daoClass);
			assertPresentofRepositoryAnnotation(daoClass);
			checkPairsDaoType(entryType);

			if (ISingleTableDao.class.isAssignableFrom(daoClass)) {
				classBuffer.put(testClass, daoClass);
				return daoClass;
			}
		}

		Assert.fail("无法提取到有效的dao信息:" + testClass);
		return null;
	}

	/**
	 * 从测试类中提取出待测试dao的类型信息
	 * 
	 * 请按规则命名，目前规则例子如下
	 * {@link com.bankcomm.novem.entry.batch.sqltrack.MailInfoEntry}
	 * {@link com.bankcomm.novem.dao.batch.sqltrack.MailInfoDaoImpl}
	 * {@link com.bankcomm.novem.dao.batch.sqltrack.MailInfoDaoImplTest}
	 * 
	 * @param entryClass
	 * @return
	 */
	private final Class<ISingleTableDao<T>> findDaoClass(
			final Class<T> entryClass, final DaoTypeEnum daoType) {
		final String entryClassName = entryClass.getSimpleName();
		Assert.assertTrue(StringUtils.endsWith(entryClassName, "Entry"));
		final String daoClassName = StringUtils.substringBeforeLast(
				entryClassName, "Entry") + CLASS_NAME_REPLACE.get(daoType);

		final String entryPackageName = entryClass.getPackage().getName();
		final List<String> daoClassPackageNameList = new ArrayList<String>();
		final String baseDaoPackage = StringUtils.replace(entryPackageName,
				"com.bankcomm.novem.entry", "com.bankcomm.novem.dao", 1);
		daoClassPackageNameList.add(baseDaoPackage);
		daoClassPackageNameList.add(baseDaoPackage + ".impl");

		for (final String daoClassPackageName : daoClassPackageNameList) {
			try {
				@SuppressWarnings("unchecked")
				final Class<ISingleTableDao<T>> daoClass = (Class<ISingleTableDao<T>>) Class
						.forName(daoClassPackageName + "." + daoClassName);
				return daoClass;
			} catch (final ClassNotFoundException e) {
				log.info("找不到entry对应的dao实现类:[" + entryClass.getName()
						+ "] -> [" + daoClassPackageName + "." + daoClassName
						+ "]");
			}
		}

		final String errorMessage = "不能按规则找到entry对应的dao实现类:["
				+ entryClass.getName() + "] -> [" + daoClassName + "]。"
				+ "请将daoImpl放到dao层entry同名包下或者同名包的impl下";
		Assert.fail(errorMessage);
		return null;
	}
}