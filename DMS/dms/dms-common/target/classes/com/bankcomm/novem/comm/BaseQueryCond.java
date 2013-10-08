package com.bankcomm.novem.comm;

import lombok.Data;

/**
 * 
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳软件 2010-12-17
 * 
 * 
 * 
 */
@Data
public abstract class BaseQueryCond {
	/**
	 *  分页
	 */
	protected final PageCond pageCond;

	/**
	 * @param pageCond 分页对象
	 */
	protected BaseQueryCond(final PageCond pageCond) {
		super();
		this.pageCond = pageCond;
	}

}
