package com.bankcomm.novem.entry;

import static org.springframework.util.Assert.isTrue;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import lombok.Data;

import org.springframework.util.Assert;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-28
 * 
 */
@Data
public abstract class BaseEntry {
	/** 建立时间 */
	private Timestamp createTime;
	/** 修改人 */
	private long modifyUser;
	/** 更新时间 */
	private Timestamp updateTime;
	/** 版本号 */
	private int version;

	/**
	 * 获取主键
	 * 
	 * @return 主键
	 */
	public long getPrimaryKey() {
		final Field primaryKeyField = getPrimaryKeyField();
		final boolean isAccessible = primaryKeyField.isAccessible();
		primaryKeyField.setAccessible(true);
		long primaryKey = -1;
		try {
			primaryKey = (Long) primaryKeyField.get(this);
		} catch (final IllegalAccessException e) {
			Assert.isTrue(false, "无法获取实体" + getClass().getName() + "主键");
		} finally {
			primaryKeyField.setAccessible(isAccessible);
		}
		return primaryKey;
	}

	/**
	 * 设置主键
	 * 
	 * @param primaryKey
	 *            主键
	 * 
	 */
	public void setPrimaryKey(final long primaryKey) {
		final Field primaryKeyField = getPrimaryKeyField();
		final boolean isAccessible = primaryKeyField.isAccessible();
		primaryKeyField.setAccessible(true);
		try {
			primaryKeyField.set(this, primaryKey);
		} catch (final IllegalAccessException e) {
			isTrue(false, "无法获取实体" + getClass().getName() + "主键");
		} finally {
			primaryKeyField.setAccessible(isAccessible);
		}
		return;
	}

	private Field getPrimaryKeyField() {
		return PrimaryKeyFieldContainer.extractPrimaryKeyField(this.getClass());
	}
}