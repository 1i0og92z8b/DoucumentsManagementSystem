/**
 * 
 */
package com.bankcomm.novem.entry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bankcomm.novem.entry.annotation.EntryPk;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-12-27
 * 
 */
@Component
class PrimaryKeyFieldContainer implements IPrimaryKeyFieldContainer {
	private static final Map<Class<? extends BaseEntry>, Field> PK_FIELDS = new HashMap<Class<? extends BaseEntry>, Field>();

	/**
	 * 获取主键域
	 * 
	 * @param entryClass
	 *            实体类型
	 * @return 该实体类型被标注过annotation的字段
	 */
	public static Field extractPrimaryKeyField(
			final Class<? extends BaseEntry> entryClass) {
		if (PK_FIELDS.containsKey(entryClass)) {
			return PK_FIELDS.get(entryClass);
		}

		for (final Field field : entryClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(EntryPk.class)) {
				PK_FIELDS.put(entryClass, field);
				return field;
			}
		}

		Assert.isTrue(false, "无法获取实体" + entryClass.getName() + "主键");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.entry.IPrimaryKeyFieldContainer#getPrimaryKeyFieldName
	 * (java.lang.Class)
	 */
	@Override
	public String getPrimaryKeyFieldName(
			final Class<? extends BaseEntry> entryClass) {
		return PK_FIELDS.get(entryClass).getName();
	}
}