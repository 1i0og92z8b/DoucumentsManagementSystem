package com.bankcomm.novem.bo.statistics;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 * @author 荆昊熠<jing_hy@bankcomm.com> 
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DownloadedFileBo {
	
	private int fileId;
	private int userId;
	private String fileName;
	private String userName;
	private String categoryName;
	private Timestamp uploadTime;
	private String filePath;
	private char fileState;
	private String fullName;
	private String keywords;
	private String filedesc;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int updator;
	private int downloadCounts;
}
