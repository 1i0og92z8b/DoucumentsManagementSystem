/**
 * 
 */
package com.bankcomm.novem.dao.common;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.dao.BaseDao;
import com.bankcomm.novem.dao.ICommonMapper;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-9-28
 * 
 */
@Repository
public class CommonDao extends BaseDao {
	@Autowired
	private ICommonMapper iCommonMapper;

	/**
	 * 查询当前数据库时间
	 * 
	 * @return 当前数据库时间
	 */
	public Date queryCurrentDate() {
		return iCommonMapper.queryCurrentDate();
	}

	/**
	 * 查询当前数据库时间
	 * 
	 * @return 当前数据库时间
	 */
	public Timestamp queryCurrentTime() {
		return iCommonMapper.queryCurTime();
	}
}