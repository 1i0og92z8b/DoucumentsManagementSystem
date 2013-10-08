package com.bankcomm.novem.bo.file;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 赵扬  Sep 16, 2013
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileUpdateCatBo extends BaseBo {
	/** 文件ID */
	private int fileId;
	/** 分类ID */
	private int categoryId;
	/** 文件路径 */
	private String filePath;
	/** 更新时间 *//*
	private RiskTimestamp updateTime;
	*//** 更新人 *//*
	private int updator;*/

	/*private Timestamp updateTime;
	private int updator;*/

}
