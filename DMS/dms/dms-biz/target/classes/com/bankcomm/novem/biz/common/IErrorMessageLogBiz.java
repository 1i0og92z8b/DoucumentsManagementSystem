/**
 * 
 */
package com.bankcomm.novem.biz.common;

import com.bankcomm.novem.bo.common.ErrorMessageLogBo;

/**
 * @author 杨晓俊<Yang_ahstu007@163.com> 砾阳 2011-4-13上午09:24:37
 * 
 */
public interface IErrorMessageLogBiz {
	/**
	 * @param errorLogBo
	 *            错误记录对象
	 * @return 实体主键
	 */
	long save(final ErrorMessageLogBo errorLogBo);
}
