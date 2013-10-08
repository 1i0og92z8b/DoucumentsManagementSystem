package com.bankcomm.novem.biz;

import static org.springframework.util.Assert.notNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.bankcomm.novem.bo.BaseBo;
import com.bankcomm.novem.bo.date.RiskDate;
import com.bankcomm.novem.dao.utils.NumericChecker;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * 数据填充
 * 
 * @author 张俊博<zhangjb@rionsoft.com> 砾阳 2010-11-4
 */
@Slf4j
public final class FillData {

	/**
	 * 向类中每个field内填充数据
	 * 
	 * @param <E>
	 *            实体类型
	 * @param clazz
	 *            实体实例
	 * @return Object实例
	 */
	public static <E extends Object> E fillDataForClassFields(
			final Class<E> clazz) {
		return fillDataForClassFields(clazz, 1);
	}

	/**
	 * 向类中每个field内填充数据
	 * 
	 * @param <E>
	 *            实体类型
	 * @param clazz
	 *            实体实例
	 * @param seeds
	 *            种子
	 * @return Object实例
	 */
	public static <E extends Object> E fillDataForClassFields(
			final Class<E> clazz, final int seeds) {
		final E instance = createInstance(clazz);
		return fillFields(seeds, instance);
	}

	/**
	 * 向类中每个field内填充数据
	 * 
	 * @param <E>
	 *            实体类型
	 * @param instance
	 *            实体实例
	 * @return Object实例
	 */
	public static <E extends Object> E fillDataForClassFields(final E instance) {
		return fillFields(1, instance);
	}

	/**
	 * 格式化日期-->java.util.Date
	 * 
	 * @param tmpSeed
	 * @return
	 */
	private static java.util.Date assembleDate(final int tmpSeed) {
		return new Date(tmpSeed);
	}

	/**
	 * @return
	 */
	private static List<Object> assembleList() {
		return Arrays.asList(new Object());
	}

	/**
	 * @param seeds
	 * @return
	 */
	private static RiskDate assembleRiskDate(final int seeds) {
		final RiskDate riskDate = new RiskDate(seeds);
		return riskDate;
	}

	/**
	 * 格式化时间-->Time、Timestamp
	 * 
	 * @param tmpSeed
	 * @return
	 */
	private static Timestamp assembleTime(final int tmpSeed) {
		return new Timestamp(tmpSeed * 10000);
	}

	/**
	 * @param <E>
	 * @param instance
	 * @param fieldName
	 * @param seed
	 */
	private static <E> void assembleValue(final E instance,
			final String fieldName, final Object seed) {
		try {
			BeanUtils.setProperty(instance, fieldName, seed);
		} catch (final IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		} catch (final InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	private static <E> E createInstance(final Class<E> clazz) {
		try {
			return clazz.newInstance();
		} catch (final InstantiationException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		} catch (final IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @param seeds
	 * @param instance
	 * @return
	 */
	private static <E> E fillFields(final int seeds, final E instance) {
		int tmp = seeds;
		for (Class<?> current = instance.getClass(); (current != BaseEntry.class)
				&& (current != Object.class) && (current != BaseBo.class); current = current
				.getSuperclass()) {
			for (final Field field : current.getDeclaredFields()) {
				// 部分覆盖率工具会生成以$开头的成员变量，一般不会自己去写这样的变量
				if (StringUtils.startsWith(field.getName(), "$")) {
					continue;
				}
				final boolean isAccessible = field.isAccessible();
				field.setAccessible(true);

				if (NumericChecker.isNumeric(field.getType())) {
					assembleValue(instance, field.getName(), seeds);
				} else if ((field.getType() == Date.class)
						|| (field.getType() == java.sql.Date.class)) {
					assembleValue(instance, field.getName(),
							assembleDate(seeds));
				} else if (field.getType() == RiskDate.class) {
					assembleValue(instance, field.getName(),
							assembleRiskDate(seeds));
				} else if (field.getType() == List.class) {
					assembleValue(instance, field.getName(), assembleList());
				} else if ((field.getType() == Timestamp.class)
						|| (field.getType() == Time.class)) {
					assembleValue(instance, field.getName(),
							assembleTime(seeds));
				} else if (isNullFieldValue(instance, field)) {
					if (field.getType().getSuperclass() == BaseBo.class) {
						fillDataForClassFields(field.getType());
					} else {
						assembleValue(instance, field.getName(),
								String.valueOf((char) (('a' + seeds) % 256)));

					}
				}

				field.setAccessible(isAccessible);

				tmp = nextSeeds(tmp);
			}
		}

		notNull(instance);
		return instance;
	}

	/**
	 * @param instance
	 * @param name
	 * @return
	 */
	private static <E> boolean isNullFieldValue(final E instance,
			final Field field) {
		try {
			return field.get(instance) == null;
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @param seeds
	 * @return
	 */
	private static int nextSeeds(final int seeds) {
		return (seeds + 1) % 10;
	}

	/**
	 * 
	 */
	private FillData() {
		super();
	}
}