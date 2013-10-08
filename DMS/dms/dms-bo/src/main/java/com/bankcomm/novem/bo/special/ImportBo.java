package com.bankcomm.novem.bo.special;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImportBo extends BaseBo {
	private String tempFileName;
	private String tempFilePath;
	private String fileName;
	private long fileSize;
	private String fileSuffix;

}
