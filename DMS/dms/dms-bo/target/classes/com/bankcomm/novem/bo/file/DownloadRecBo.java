package com.bankcomm.novem.bo.file;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 赵扬  Sep 16, 2013
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DownloadRecBo extends BaseBo {
	/** 文件ID */
	private int fileId;
	/** 用户ID */
	private int userId;
	/** 下载时间 */
	private Timestamp downloadTime;
	
	/** 自增主键？ */
	private int seqId;
	
	/*private Timestamp createTime;
	private Timestamp updateTime;
	private int updator;*/

}
