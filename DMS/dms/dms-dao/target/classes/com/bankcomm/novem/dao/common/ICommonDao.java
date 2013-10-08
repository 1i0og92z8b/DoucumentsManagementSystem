/**
 * 
 */
package com.bankcomm.novem.dao.common;

import java.sql.Timestamp;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-10-24
 * 
 */
public interface ICommonDao {
	/**
	 * 查询当前数据库时间
	 * 
	 * @return 当前数据库时间
	 */
	Timestamp queryCurrentTime();
}
