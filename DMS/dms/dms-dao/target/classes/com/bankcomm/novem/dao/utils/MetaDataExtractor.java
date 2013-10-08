/**
 * 
 */
package com.bankcomm.novem.dao.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.bankcomm.novem.dao.SingleTableDao;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-9-24
 * 
 */
public final class MetaDataExtractor {

	/**
	 * 提取实体类型信息
	 * 
	 * @param <E>
	 *            继承baseEntry的entry
	 * 
	 * @param daoClass
	 *            dao类型
	 * 
	 * @return 实体类型信息
	 */
	public static <E extends BaseEntry> Class<E> extractEntryClassInfo(
			final Class<?> daoClass) {
		for (Class<?> current = daoClass; (current != SingleTableDao.class)
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

			final Type firstType = actualTypes[0];
			if (!(firstType instanceof Class)) {
				continue;
			}
			@SuppressWarnings("unchecked")
			final Class<E> firstClass = (Class<E>) firstType;
			if (BaseEntry.class.isAssignableFrom(firstClass)) {
				return firstClass;
			}
		}

		throw new IllegalStateException("无法获取有效的entry信息" + daoClass);
	}

	private MetaDataExtractor() {
	}
}
