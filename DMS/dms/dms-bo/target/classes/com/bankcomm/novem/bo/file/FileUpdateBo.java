package com.bankcomm.novem.bo.file;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.file.FileUpdateCatBo;

/**
 * @author 赵扬  Sep 16, 2013
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileUpdateBo extends FileUpdateCatBo {
	/** 关键字 */
	private String keywords;
	/** 文件描述 */
	private String fileDesc;

}
