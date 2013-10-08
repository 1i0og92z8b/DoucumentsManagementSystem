package com.bankcomm.novem.comm;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 19, 2012
 * 
 */
@Data
public class ProcessLogQueryCondition {
	/** 结束时间 */
	private String endDate;
	/** 分页 */
	private PageCond pageCond;
	/** 机构号 */
	private long queryOrgId;
	/** 查询条件：机构列表 */
	private List<Long> queryOrgIdList;
	/** 待查询人员 */
	private long queryUserId;
	/** 递归标志 */
	private String recursionFlag;
	/** 开始时间 */
	private String startDate;

}
