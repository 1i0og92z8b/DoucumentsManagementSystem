package com.bankcomm.novem.biz.common;

import com.bankcomm.novem.bo.common.ProcessLogBo;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 18, 2012
 * 
 */
public interface IManageProcessLogBiz {

	/**
	 * 插入操作监控实体
	 * 
	 * @param processLogBo
	 *            操作监控实体
	 * @return 插入后的主键
	 */
	long insertProcessLog(final ProcessLogBo processLogBo);

}
