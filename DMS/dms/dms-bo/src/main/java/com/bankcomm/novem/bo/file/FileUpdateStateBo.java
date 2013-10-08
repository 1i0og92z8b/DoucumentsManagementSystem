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
public class FileUpdateStateBo extends BaseBo {
	/** 文件状态 */
	private char fileState;
	/** 文件ID */
	private int fileId;
	
	/*private Timestamp updateTime;
	private int updator;*/

}
