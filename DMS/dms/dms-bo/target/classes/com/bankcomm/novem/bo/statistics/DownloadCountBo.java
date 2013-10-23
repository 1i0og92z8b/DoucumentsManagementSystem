package com.bankcomm.novem.bo.statistics;

import com.bankcomm.novem.bo.BaseBo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

public class DownloadCountBo extends BaseBo{

	private int fileId;
}
