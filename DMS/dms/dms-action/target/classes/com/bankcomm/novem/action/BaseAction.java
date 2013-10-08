/*
 * create this file at 上午10:20:14  2012-10-16
 */
package com.bankcomm.novem.action;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bocom.jump.bp.core.Action;

/**
 * 基础Action
 * 
 * @author <a href="mailto:zhang_ey@bankcomm.com"> 张恩宇 </a>
 */
public class BaseAction implements Action {

	/**
	 * dozer转换器
	 */
	@Autowired
	protected Mapper mapper;
	
	public Mapper getMapper() {
		return mapper;
	}
}
