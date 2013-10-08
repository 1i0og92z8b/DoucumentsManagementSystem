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
public class FileUploadBo extends BaseBo {
	private String fileName;
	private String keywords;
	private int categoryId;
	private int userId;
	private Timestamp uploadTime;
	//private Timestamp uploadTime;
	private char fileState;
	private String fileDesc;
	private String filePath;
	private String fullName;
	/*private Timestamp createTime;
	private Timestamp updateTime;
	private int updator;*/
	
}
