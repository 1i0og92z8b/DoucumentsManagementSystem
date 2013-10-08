package com.bankcomm.novem.dao;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-14
 * 
 */
@Repository
public interface ICommonMapper {
	/**
	 * 查询当前数据库日期
	 * 
	 * @return 当前数据库日期
	 */
	@Select("SELECT CURRENT DATE FROM (VALUES 1) AS A")
	Date queryCurrentDate();

	/**
	 * 查询当前数据库时间
	 * 
	 * @return 当前数据库时间
	 */
	@Select("SELECT CURRENT TIMESTAMP FROM (VALUES 1) AS A")
	Timestamp queryCurTime();
}
