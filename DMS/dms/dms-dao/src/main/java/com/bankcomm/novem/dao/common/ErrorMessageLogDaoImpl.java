/**
 * 
 */
package com.bankcomm.novem.dao.common;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.dao.SingleTableDao;
import com.bankcomm.novem.dao.annote.DbSchemaType;
import com.bankcomm.novem.entry.common.ErrorMessageLogEntry;

/**
 * @author 杨晓俊<Yang_ahstu007@163.com> 砾阳 2011-4-12下午04:52:49
 * 
 */
@DbSchemaType("db2iport")
@Repository
class ErrorMessageLogDaoImpl extends SingleTableDao<ErrorMessageLogEntry>
		implements IErrorMessageLogDao {
	//
}
