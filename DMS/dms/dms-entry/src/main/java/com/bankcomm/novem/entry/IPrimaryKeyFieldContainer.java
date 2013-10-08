/**
 * 
 */
package com.bankcomm.novem.entry;

/**
 * @author zhushq<risk_admin@bankcomm.com> 砾阳 2013-3-5
 * 
 */
public interface IPrimaryKeyFieldContainer {
	/**
	 * 获取主键域
	 * 
	 * @param entryClass
	 *            实体类型
	 * @return 该实体类型被标注过annotation的字段名称
	 */
	String getPrimaryKeyFieldName(final Class<? extends BaseEntry> entryClass);
}
