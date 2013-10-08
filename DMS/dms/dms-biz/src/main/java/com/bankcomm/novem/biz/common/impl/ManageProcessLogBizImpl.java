package com.bankcomm.novem.biz.common.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.common.IManageProcessLogBiz;
import com.bankcomm.novem.bo.common.ProcessLogBo;
import com.bankcomm.novem.dao.common.IProcessLogDao;
import com.bankcomm.novem.entry.common.ProcessLogEntry;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 18, 2012
 * 
 */
@Service
public class ManageProcessLogBizImpl extends BaseBiz implements
		IManageProcessLogBiz {

	@Autowired
	private Mapper mapper;
	@Autowired
	private IProcessLogDao processLogDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.novem.biz.common.IManageProcessLogBiz#insertProcessLog(com
	 * .bankcomm.risk.bo.common.ProcessLogBo)
	 */
	@Override
	public long insertProcessLog(final ProcessLogBo processLogBo) {
		final ProcessLogEntry processLogEntry = mapper.map(processLogBo,
				ProcessLogEntry.class);
		return processLogDao.insert(processLogEntry);
	}

}
