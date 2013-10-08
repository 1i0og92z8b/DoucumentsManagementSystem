/**
 * 
 */
package com.bankcomm.novem.biz.common.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.common.IErrorMessageLogBiz;
import com.bankcomm.novem.bo.common.ErrorMessageLogBo;
import com.bankcomm.novem.dao.common.IErrorMessageLogDao;
import com.bankcomm.novem.entry.common.ErrorMessageLogEntry;

/**
 * @author 杨晓俊<Yang_ahstu007@163.com> 砾阳 2011-4-13上午09:44:59
 * 
 */
@Service
public class ErrorMessageLogBizImpl extends BaseBiz implements
		IErrorMessageLogBiz {
	@Autowired
	private IErrorMessageLogDao errorLogDao;
	@Autowired
	private Mapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.biz.error.IErrorMessageLogBiz#save(com.bocom.egif.bo.error
	 * .ErrorMessageLogBo)
	 */
	@Override
	public long save(final ErrorMessageLogBo errorLogBo) {
		return errorLogDao.insert(mapper.map(errorLogBo,
				ErrorMessageLogEntry.class));
	}

}
