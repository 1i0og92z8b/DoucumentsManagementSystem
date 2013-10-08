/**
 * 
 */
package com.bankcomm.novem.dao.impl;

import static com.bankcomm.novem.dao.Version.initVersion;
import static com.bankcomm.novem.dao.annote.DaoTypeEnum.COPY;
import static com.bankcomm.novem.dao.annote.DaoTypeEnum.ORIGIN;
import static org.springframework.util.Assert.isTrue;

import org.springframework.stereotype.Component;

import com.bankcomm.novem.comm.SimpleEntry;
import com.bankcomm.novem.dao.Version;
import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.VersionEntry;

/**
 * @author zhushq<zhusq@rionsoft.com> 砾阳 Nov 20, 2012
 * 
 */
@Component
public class VersionDecorator {
	private static final String VERSION_COLUMN = "VERSION";

	/**
	 * @param entry
	 *            实体
	 * @param daoType
	 *            dao类型
	 * @return 添加版本信息
	 */
	public SimpleEntry<String, Object> extractInsertVersion(
			final BaseEntry entry, final DaoTypeEnum daoType) {
		if (isVersionedEntry(entry.getClass())) {
			if ((daoType == null) || (entry.getVersion() == 0)) {
				entry.setVersion(initVersion());
			}
			return new SimpleEntry<String, Object>(VERSION_COLUMN,
					entry.getVersion());
		}
		return null;
	}

	/**
	 * 提取where条件内的版本信息
	 * 
	 * @param tobeUpdate
	 *            待更新数据
	 * @param daoType
	 *            dao类型
	 * @return 需要在where内做版本判断的版本号，如果不需要判断则返回0,
	 */
	public int extractWhereVersion(final BaseEntry tobeUpdate,
			final DaoTypeEnum daoType) {
		if (!isVersionedEntry(tobeUpdate.getClass())) {
			return 0;
		}

		if ((daoType == null) || (daoType == COPY)) {
			isTrue(tobeUpdate.getVersion() > 0, "版本化" + tobeUpdate.getClass()
					+ "数据版本号必须大于0");
			return tobeUpdate.getVersion();
		}

		return 0;
	}

	/**
	 * @param tobeUpdate
	 *            待更新数据
	 * @param daoType
	 *            dao类型
	 * @return 下一版本号
	 */
	public int nextVersion(final BaseEntry tobeUpdate, final DaoTypeEnum daoType) {
		if (isVersionedEntry(tobeUpdate.getClass())) {
			if (daoType == ORIGIN) {
				return tobeUpdate.getVersion();
			}
			return Version.nextVersion(tobeUpdate.getVersion());
		}
		return 0;
	}

	/**
	 * @param entryClass
	 *            实体类型
	 * @return
	 */
	private boolean isVersionedEntry(final Class<? extends BaseEntry> entryClass) {
		return entryClass.isAnnotationPresent(VersionEntry.class);
	}
}
