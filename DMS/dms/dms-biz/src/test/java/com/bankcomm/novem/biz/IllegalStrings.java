/*
 * create this file at 下午2:33:30  2012-9-3
 */
package com.bankcomm.novem.biz;

import java.util.ArrayList;
import java.util.List;

/**
 * 空字符串列表
 * 
 * @author <a href="mailto:zhang_ey@bankcomm.com"> 张恩宇 </a>
 */
public class IllegalStrings {

	/** 空字符串 */
	public static final String BLANK = "";
	/** 空字符串列表 */
	public static final List<String> ILLEGAL_STRING_LIST = new ArrayList<String>();
	/** null */
	public static final String NULL_STRING = null;
	/** 空格字符串 */
	public static final String SPACE = "    ";
	static {
		ILLEGAL_STRING_LIST.add(BLANK);
		ILLEGAL_STRING_LIST.add(SPACE);
		ILLEGAL_STRING_LIST.add(NULL_STRING);
	}
}
