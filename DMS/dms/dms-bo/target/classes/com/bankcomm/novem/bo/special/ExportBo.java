package com.bankcomm.novem.bo.special;

import com.bankcomm.novem.bo.BaseBo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExportBo extends BaseBo {
	/** 呈现文件名 */
	private String fileName;
	/** 文件全路径 :存储路径+全名
	 *  如果需要从前台获取该路径，则需要此属性 
	 */
	private String fullFilePath;
}
