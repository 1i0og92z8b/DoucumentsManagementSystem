/**
 * 
 */
package com.bankcomm.novem.dao;

import com.bankcomm.novem.entry.BaseEntry;

/**
 * 泛型dao
 * 
 * @author zhushq<risk_admin@bankcomm.com> 砾阳 Dec 5, 2012
 * @param <T>
 *            entry类型
 * 
 */
public interface IGenericDao<T extends BaseEntry> {
	/**
	 * 提取泛型dao中操作的实体类型
	 * 
	 * @return 对应的实体类型
	 */
	Class<T> getEntryInfo();
}
