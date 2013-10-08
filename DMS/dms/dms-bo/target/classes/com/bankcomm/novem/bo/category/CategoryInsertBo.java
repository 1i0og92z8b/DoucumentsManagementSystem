package com.bankcomm.novem.bo.category;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @description 该Bo主要是用来获取插入分类的自增ID
 * @author Yanping
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CategoryInsertBo extends BaseBo{
	private int categoryId;
	private String categoryName;
	private int parentId;
}
