package com.bankcomm.novem.dao.utils;

import static com.bankcomm.novem.dao.utils.NamingUtils.transferToCamelFormat;
import static com.bankcomm.novem.dao.utils.NamingUtils.transferToDbFormat;
import static com.bankcomm.novem.dao.utils.NumericChecker.isNumeric;
import static org.apache.commons.beanutils.BeanUtils.setProperty;
import static org.apache.commons.lang.StringEscapeUtils.escapeSql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConvertUtils;
import org.dozer.converters.DateConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bankcomm.novem.comm.SimpleEntry;
import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.EntryPk;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-9
 * 
 */
@Component
public class DBTransfer {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	static {
		ConvertUtils.register(new DateConverter(DATE_FORMAT),
				java.util.Date.class);
	}

	/**
	 * @param value
	 *            需要转换的对象
	 * @return 该对象对应的列值
	 */
	public static Object assembleColumnValue(final Object value) {
		if (value == null) {
			return null;
		}
		if (isNumeric(value.getClass())) {
			return value;
		}
		if ((value instanceof Timestamp) || (value instanceof java.sql.Date)) {
			return value;
		}

		return escapeSql(String.valueOf(value));
	}

	/**
	 * 提取列值
	 * 
	 * 
	 * @param tmpEntryClass
	 *            数据实体类信息
	 * @return 列名
	 */
	public static List<String> extractColumnName(
			final Class<? extends BaseEntry> tmpEntryClass) {
		final List<String> entryList = new ArrayList<String>();

		for (Class<?> current = tmpEntryClass; (current != BaseEntry.class)
				&& (current != Object.class); current = current.getSuperclass()) {
			for (final Field field : current.getDeclaredFields()) {
				final boolean isAccessible = field.isAccessible();
				field.setAccessible(true);

				final String fieldName = field.getName();
				Assert.hasLength(fieldName, "属性名必须非空");
				// 当使用coverage插件时，会产生以$开头的成员变量
				if (fieldName.startsWith("$")) {
					continue;
				}

				final String columnName = transferToDbFormat(fieldName);
				entryList.add(columnName);

				field.setAccessible(isAccessible);
			}
		}

		return entryList;
	}

	/**
	 * 提取列值
	 * 
	 * 
	 * @param entry
	 *            数据实体
	 * @param includePk
	 *            是否包含主键
	 * @return 列值
	 */
	public static List<Entry<String, Object>> extractColumnValue(
			final BaseEntry entry, final boolean includePk) {
		final List<Entry<String, Object>> entryList = new ArrayList<Map.Entry<String, Object>>();

		for (Class<?> current = entry.getClass(); (current != BaseEntry.class)
				&& (current != Object.class); current = current.getSuperclass()) {
			for (final Field field : current.getDeclaredFields()) {
				if (!includePk && field.isAnnotationPresent(EntryPk.class)) {
					continue;
				}

				final boolean isAccessible = field.isAccessible();
				field.setAccessible(true);

				final String fieldName = field.getName();
				Assert.hasLength(fieldName, "属性名必须非空");
				if (fieldName.startsWith("$")) {// 当使用coverage插件时，会产生以$开头的成员变量
					continue;
				}

				final String columnName = transferToDbFormat(fieldName);
				final Object columnValue = extractColumnValue(entry, field);

				entryList.add(new SimpleEntry<String, Object>(columnName,
						columnValue));

				field.setAccessible(isAccessible);
			}
		}

		return entryList;
	}

	/**
	 * 提取主键名,使用 {@link EntryPk}标志主键
	 * 
	 * @param classInfo
	 *            类型信息
	 * @return 主键列名
	 */
	public static String extractPkName(
			final Class<? extends BaseEntry> classInfo) {
		for (final Field field : classInfo.getDeclaredFields()) {
			if (field.isAnnotationPresent(EntryPk.class)) {
				Assert.hasText(field.getName());
				return transferToDbFormat(field.getName());
			}
		}
		Assert.isTrue(false, "无法提取主键名");
		return null;
	}

	/**
	 * 获取表名
	 * 
	 * @param entrySimpleName
	 *            entry的简单表名
	 * @param daoType
	 *            dao类型
	 * @param dbSchema
	 *            数据库schema
	 * 
	 * @return 对应的表名
	 */
	public static String extractTableName(final String entrySimpleName,
			final DaoTypeEnum daoType, final String dbSchema) {
		final String normalName = dbSchema + "."
				+ extractTableName(entrySimpleName);
		return daoType == null ? normalName : normalName + daoType.toString();
	}

	/**
	 * @return {@link #DATE_FORMAT}
	 */
	public static SimpleDateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	/**
	 * @return {@link #TIME_FORMAT}
	 */
	public static SimpleDateFormat getTimeFormat() {
		return TIME_FORMAT;
	}

	/**
	 * @param <T>
	 *            实体类型
	 * @param resultMap
	 *            用来提取数据的映射
	 * @param entryClassInfo
	 *            实体信息
	 * @return 组装好的数据实体
	 */
	public static <T extends BaseEntry> T transferToEntry(
			final Map<String, Object> resultMap, final Class<T> entryClassInfo) {
		final T result = newInstanse(entryClassInfo);

		for (final Entry<String, Object> entry : resultMap.entrySet()) {
			final String name = transferToCamelFormat(entry.getKey());
			try {
				setProperty(result, name, entry.getValue());
			} catch (final IllegalAccessException e) {
				throw new IllegalStateException("非法访问"
						+ entryClassInfo.getSimpleName() + "的属性" + name, e);
			} catch (final InvocationTargetException e) {
				throw new IllegalStateException("调用"
						+ entryClassInfo.getSimpleName() + "的属性" + name
						+ "set方法出错", e);
			} catch (final IllegalArgumentException e) {
				throw new IllegalStateException("调用"
						+ entryClassInfo.getSimpleName() + "的属性" + name
						+ "set方法出错：", e);
			}
		}
		return result;
	}

	/**
	 * 提取属性值
	 * 
	 * @param entry
	 *            数据实体
	 * @param field
	 *            属性
	 * @return 该实体内该属性的值
	 */
	private static <T extends BaseEntry> Object extractColumnValue(
			final T entry, final Field field) {
		Object value = null;
		try {
			value = field.get(entry);
		} catch (final Exception e) {
			Assert.isTrue(false, "无法获取实体" + entry.getClass().getName() + "属性"
					+ field.getName());
		}
		return assembleColumnValue(value);
	}

	/**
	 * 在实体类型上提取表名，删除了Entry，并在出首字母外每个大写字母前加_
	 * 
	 * @param simpleName
	 *            类名
	 * 
	 * @return 实体对应的表名
	 */
	private static String extractTableName(final String simpleName) {
		final String className = simpleName.endsWith("Entry") ? simpleName
				.replaceAll("Entry", "") : simpleName;
		return transferToDbFormat(className);
	}

	/**
	 * @param entryClassInfo
	 * @return
	 */
	private static <T extends BaseEntry> T newInstanse(
			final Class<T> entryClassInfo) {
		try {
			return entryClassInfo.newInstance();
		} catch (final InstantiationException e) {
			throw new IllegalStateException("不能实例化" + entryClassInfo.getName(),
					e);
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException("不能实例化" + entryClassInfo.getName()
					+ "可能默认构造函数私有", e);
		}
	}
}