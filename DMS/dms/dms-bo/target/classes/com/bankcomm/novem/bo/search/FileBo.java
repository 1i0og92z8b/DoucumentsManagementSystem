package com.bankcomm.novem.bo.search;


import java.sql.Timestamp;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileBo{

	private int fileId;
	private int userId;
	private String fileName;
//	private int categoryId;
	private String categoryPath;
	private String userName;
	private Timestamp uploadTime;
	private String keywords;
	private String filePath;
	private char fileState;
	private String fileDesc;
	private String fullName;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int updator;
}
