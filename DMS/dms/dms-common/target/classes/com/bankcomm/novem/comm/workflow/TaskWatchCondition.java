/**
 * 
 */
package com.bankcomm.novem.comm.workflow;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.comm.PageCond;

/**
 * 
 * @author zhouwei<risk_admin@bankcomm.com> 砾阳 Jul 16, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskWatchCondition {
	/** 分行org_id */
	private long orgId;
	/** 分行ID列表 */
	private List<Long> orgIdList;
	/** 分页 */
	private PageCond pageCond;
	/** 用户所属一级分行ID */
	private long userProvinceOrgId;
}
