package com.bankcomm.novem.dao.annote;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 副本dao操作器
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-29
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DaoType {
	/**
	 * 指定dao的类型
	 * 
	 * @return dao类型
	 */
	DaoTypeEnum type();
}
