package com.bankcomm.novem.bo;

import java.sql.Timestamp;

import lombok.Data;

//import com.bankcomm.novem.bo.date.RiskTimestamp;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-26
 * 
 */
@Data
public abstract class BaseBo {
	/** 创建时间 */
	private Timestamp createTime;
	/** 修改人 */
	private long modifyUser;
	/** 更新时间 */
	private Timestamp updateTime;
	/** 版本信息 */
	private int version;
}
