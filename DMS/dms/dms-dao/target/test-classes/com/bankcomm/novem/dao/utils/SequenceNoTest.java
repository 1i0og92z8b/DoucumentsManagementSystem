package com.bankcomm.novem.dao.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.incrementer.DB2SequenceMaxValueIncrementer;

import com.bankcomm.novem.dao.BaseDaoTest;

/**
 * 
 * 
 * @author tangg@bankcomm.com 交通银行 2012-7-23
 * 
 */
public class SequenceNoTest extends BaseDaoTest<DB2SequenceMaxValueIncrementer> {
	@Autowired
	@Qualifier("opRiskEventSeqNo")
	private DB2SequenceMaxValueIncrementer opRiskEventSeqNo;

	/**
	 * getNextIntValue for ORISK.S_EVENT_NO
	 */
	@Test
	public void testNextIntValue() {
		final int eventNo = opRiskEventSeqNo.nextIntValue();
		final int eventNo2 = opRiskEventSeqNo.nextIntValue();
		Assert.assertEquals(eventNo + 1, eventNo2);
	}

	/**
	 * nextStringValue for ORISK.S_EVENT_NO
	 */
	@Test
	public void testNextStringValue() {
		final String eventNoStr = opRiskEventSeqNo.nextStringValue();
		final String eventNoStr2 = opRiskEventSeqNo.nextStringValue();
		Assert.assertEquals(Integer.valueOf(eventNoStr).intValue() + 1, Integer
				.valueOf(eventNoStr2).intValue());
	}
}
