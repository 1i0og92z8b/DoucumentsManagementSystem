package com.bankcomm.novem.dao.condition;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增条件
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-8
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InsertArgument extends AbstractSqlArgument {
	private List<String> columnNameList;
	private long generateID;
	private List<Object> valueList;

	/**
	 * @param tableName
	 *            {@link AbstractSqlArgumentWithColumns#tableName}
	 */
	public InsertArgument(final String tableName) {
		super(tableName);
	}
	
}