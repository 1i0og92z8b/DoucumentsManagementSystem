package com.bankcomm.novem.bo.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.comm.PageCond;

@Data
@EqualsAndHashCode(callSuper = false)
public class QueryDemoBo {

	private String keyword;
	
	private PageCond pageCond;
	
}
