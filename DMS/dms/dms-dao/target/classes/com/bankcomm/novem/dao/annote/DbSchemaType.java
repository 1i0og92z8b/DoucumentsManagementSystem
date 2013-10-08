package com.bankcomm.novem.dao.annote;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库schema的类型处理器
 * 
 * @author sunpu<sun.pu@bankcomm.com> May 15, 2012
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DbSchemaType {
	/** 默认数据库schema */
//	String DEFAULT_SCHEMA = "KISK";
	String DEFAULT_SCHEMA = "db2iport";

	/** 默认的数据库SCHEMA为RISK */
	String value() default DEFAULT_SCHEMA;
}
