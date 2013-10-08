package com.bankcomm.novem.bo.file;

import com.bankcomm.novem.bo.BaseBo;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class DownloadCountsBo extends BaseBo {
	/** 文件ID */
	private int fileId;

}
