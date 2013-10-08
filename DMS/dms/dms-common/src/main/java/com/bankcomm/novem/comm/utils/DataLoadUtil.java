package com.bankcomm.novem.comm.utils;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 Dec 5, 2012
 * 
 */
public class DataLoadUtil {
	/**
	 * 取得数据加载文件全路径
	 * 
	 * @param dataLoadPath
	 *            加载根目录
	 * @param dataSource
	 *            来源系统
	 * @param optDateStr
	 *            处理日期字符串
	 * @param fileName
	 *            文件名称
	 * @return 数据加载文件全路径
	 */
	public static final String getDataLoadFileAbsolutePath(
			final String dataLoadPath, final String dataSource,
			final String optDateStr, final String fileName) {
		return dataLoadPath + dataSource + "/" + optDateStr + "/" + fileName;
	}
}
