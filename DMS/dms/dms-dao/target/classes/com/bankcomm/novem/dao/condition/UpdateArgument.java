package com.bankcomm.novem.dao.condition;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新条件
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateArgument extends AbstractSqlArgumentWithColumns {
	private List<String> columnNameList;
	private List<Object> valueList;

	/**
	 * @param tableName
	 *            {@link AbstractSqlArgumentWithColumns#tableName}
	 */
	public UpdateArgument(final String tableName) {
		super(tableName);
	}
}