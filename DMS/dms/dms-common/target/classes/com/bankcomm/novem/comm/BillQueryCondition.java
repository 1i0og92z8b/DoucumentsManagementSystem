package com.bankcomm.novem.comm;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 Jul 24, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BillQueryCondition extends ExportCondition {
	/** 组合代码 */
	private String assembledCode;

	/** 组合类型 */
	private String assembledType;

	/** 清单类型 */
	//TODO 通过构造函数传入该成员变量方式，避免在子类里面覆盖
	private String billType;

	/** 业务组合 */
	private List<String> businessAssembled;

	/** 数据状态 */
	private String dataState;

	/** 结束时间 */
	private String endDate;

	/** 时点 */
	private String optDate;

	/** 机构组合 */
	private List<String> orgAssembled;

	/** 分页 */
	private PageCond pageCond;

	/** 历史与日常数据标示 */
	private String sourceFlag;

	/** 开始时间 */
	private String startDate;

	/** 年月 */
	private String yearmonth;
}
