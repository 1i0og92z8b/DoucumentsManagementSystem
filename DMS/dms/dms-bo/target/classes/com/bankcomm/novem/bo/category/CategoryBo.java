package com.bankcomm.novem.bo.category;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * 
 * @author Yanping at 2013-09-16
 *
 */
//
@Data
@EqualsAndHashCode(callSuper=false)
public class CategoryBo extends BaseBo{
	private int categoryId;
	private int parentId;
	private String categoryName;
	private Boolean isLeaf;
	private Boolean categoryState;
	private String categoryPath;
	private String categoryDesc;
}
