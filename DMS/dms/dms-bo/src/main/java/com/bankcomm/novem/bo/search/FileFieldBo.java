package com.bankcomm.novem.bo.search;


import java.util.List;

import com.bankcomm.novem.comm.PageCond;



import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class FileFieldBo {

	private String fileName;
	
	private String userName;
	
	private int categoryId;
	
	private int fileId;
	
	private int userId;
	
	private String startDate;
	
	private String endDate;
	
	private List<String> keywords;
	
	private char fileState;
	
	private PageCond pageCond = new PageCond();
}
