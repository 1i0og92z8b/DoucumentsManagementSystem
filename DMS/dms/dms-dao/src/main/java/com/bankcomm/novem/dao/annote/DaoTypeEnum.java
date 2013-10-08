/**
 * 
 */
package com.bankcomm.novem.dao.annote;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-11-1
 * 
 */
public enum DaoTypeEnum {
	/**
	 * 副本
	 */
	COPY {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "_COPY";
		}
	},
	/**
	 * 历史时点信息
	 */
	HISTORY {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "_HIS";
		}
	},
	/**
	 * 正本
	 */
	ORIGIN {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "";
		}
	};
}
