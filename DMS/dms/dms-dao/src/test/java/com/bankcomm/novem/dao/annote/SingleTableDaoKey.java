/**
 * 
 */
package com.bankcomm.novem.dao.annote;

import lombok.Data;

import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2013-3-11
 * 
 */
@Data
public class SingleTableDaoKey {
	private Class<? extends BaseEntry> entryClass;
	private DaoTypeEnum type;
}
