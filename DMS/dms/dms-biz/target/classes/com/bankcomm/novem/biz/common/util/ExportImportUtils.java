/**
 * 
 */
package com.bankcomm.novem.biz.common.util;

//import static org.springframework.util.Assert.notNull;
//
//import java.io.File;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.util.Assert;

//import com.bankcomm.novem.biz.batch.parameter.IManageSystemParameterBiz;
//import com.bankcomm.novem.comm.spring.ManageSpringBeans;

/**
 * @author jrc<arms_admin@bankcomm.com> Mar 31, 2012
 * 
 */
public final class ExportImportUtils {

	/** UTF-8的bom头 */
	public static final String BOM_HEAD = new String(new byte[] { (byte) 0xEF,
			(byte) 0xBB, (byte) 0xBF });
	/** UTF-8 bom头字节 */
	public static final byte[] BOM_HEAD_BYTES = new byte[] { (byte) 0xEF,
			(byte) 0xBB, (byte) 0xBF };

//	/**
//	 * 判断指定值是否在参数中存在
//	 * 
//	 * @param values
//	 *            参数
//	 * @param value
//	 *            指定值
//	 * @return 结果
//	 */
//	public static boolean checkNameExist(final String[] values,
//			final String value) {
//		if (values == null) {
//			return false;
//		}
//
//		for (final String param : values) {
//			if (StringUtils.equalsIgnoreCase(param, value)) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 
//	 * @param suffix
//	 *            后缀名
//	 * @return 文件
//	 */
//	public static File createTmpImportFile(final String suffix) {
//		final IManageSystemParameterBiz manageSystemParameterBiz = ManageSpringBeans.getBean(IManageSystemParameterBiz.class);
//		final long currentTimeInMillis = manageSystemParameterBiz.queryCurrentTimeInMillis();
//		final String path = manageSystemParameterBiz.queryImportFilePath();
//		Assert.notNull(path, "文件路径为空");
//		final File filePath = new File(path);
//		if (!filePath.exists()) {
//			filePath.mkdirs();
//		}
//
//		final String fileName = currentTimeInMillis + suffix;
//		File file = new File(filePath, fileName);
//		if (file.exists()) {
//			file.delete();
//			file = new File(filePath, fileName);
//		}
//		return file;
//	}
//
//	/**
//	 * @param filePath
//	 *            文件路径
//	 * @return 删除结果
//	 */
//	public static boolean deleteFile(final String filePath) {
//		notNull(filePath, "文件路径不能为空");
//		final File file = new File(filePath);
//		if (file.exists()) {
//			file.delete();
//		}
//		return !file.exists();
//	}
//
//	/**
//	 * 
//	 * @return 字符集编码
//	 */
//	public static String queryCharsetEncode() {
//		final IManageSystemParameterBiz manageSystemParameterBiz = ManageSpringBeans
//				.getBean(IManageSystemParameterBiz.class);
//		return manageSystemParameterBiz.queryCharsetEncode();
//	}
//
//	/**
//	 * 
//	 * @return 导出阀值
//	 */
//	public static int queryExportDataLimit() {
//		final IManageSystemParameterBiz manageSystemParameterBiz = ManageSpringBeans
//				.getBean(IManageSystemParameterBiz.class);
//		return manageSystemParameterBiz.queryExportDataLimit();
//	}
//
//	/**  */
//	private ExportImportUtils() {
//		super();
//	}
}
