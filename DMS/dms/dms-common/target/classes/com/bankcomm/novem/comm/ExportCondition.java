package com.bankcomm.novem.comm;

import java.util.List;

import lombok.Data;

/**
 * 导出条件接口
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 May 16, 2012
 * 
 */
@Data
public class ExportCondition {
	/** 用户未选择机构时默认的机构ID */
	private long defaultOrgId;
	/** 机构ID列表 */
	private List<Long> orgIdList;
	/** 分页 */
	private PageCond pageCond;
	/** 递归标志 */
	private String recursionFlag;
	/** 前台查询选择的机构ID */
	private long selectedOrgId;
}
