package com.bankcomm.novem.comm.history;

import lombok.Data;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-4-5
 * 
 */
@Data
public class QueryHistory {
	/** 欲更新的列名 */
	private String columnName;
	/** 欲更新的值 */
	private String columnValue;
	/** 欲更新的表名 */
	private String tableName;
	/** 设定查询条件的列名 */
	private String whereColumnName;
	/** 设定查询条件的值 */
	private long whereColumnValue;
}