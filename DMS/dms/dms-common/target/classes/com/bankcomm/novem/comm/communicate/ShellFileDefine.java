/**
 * 
 * 
 */
package com.bankcomm.novem.comm.communicate;

/**
 * 本接口用于存放shell文件，要求是全路径，参数由调用方自行控制， 不需要在此定义。
 * 
 * 如果有其他命令优先先转换为shell，再执行
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-10-26
 * 
 */
public enum ShellFileDefine {
	/** 数据库备份 */
	BACKUP_DB("backupDB.sh"),
	/** 检查表状态 */
	CHECK_TABLE_STATE("CheckTableState.sh"),
	/** 清理数据文件 */
	CLEAN_DATA_FILES("cleanDataFiles.sh"),
	/** 压缩接口文件 */
	COMPRESS_FILE("compress.sh"),
	/** 数据质量探测 */
	DATAQUALITY_CHECK("dataloadQualityCheck.sh", ShellFileDefine.BIN_PATH),
	/** 导出外发数据 */
	EXPORT_DATA_FOR_SEND("export.sh", ShellFileDefine.DATASEND_PATH),
	/** 批次加载数据 */
	LOAD_DATA("dataload.sh", ShellFileDefine.DATALOAD_PATH),
	/** 批次加载清空数据 */
	LOAD_DATA_EMPTY("dataloadEmpty.sh", ShellFileDefine.DATALOAD_PATH),
	/** 批次加载数据 */
	MID_LOAD_DATA("dataload.sh", ShellFileDefine.DATALOAD_PATH),
	/** 清理数据库备份 */
	PRE_LOAD_DATA("dataloadpr.sh"),
	/** 清理数据库备份 */
	REMOVE_DB_BACKUP("rmBackupFiles.sh"),
	/** reorg */
	REORG_CHECK("ReorgCheck.sh"),
	/** 通过CD软件外发数据 */
	SEND_DATA_BY_CD("sendByCD.sh", ShellFileDefine.DATASEND_PATH),
	/** 上传附件 */
	UPLOAD_ATTACH("dataUpload.sh");

	private static final String BIN_PATH = "/home/kiskuser/bin/";
	private static final String DATALOAD_PATH = "/home/kiskuser/bin/dataload/";
	private static final String DATASEND_PATH = "/home/kiskuser/bin/datasend/";
	/**
	 * 完整的shell路径
	 */
	private final String shellFullPath;

	/**
	 * @param fileName
	 *            shell文件名称，会和BIN_PATH组成全路径
	 */
	private ShellFileDefine(final String fileName) {
		this.shellFullPath = BIN_PATH + fileName;
	}

	/**
	 * @param fileName
	 *            shell文件名称，会和BIN_PATH组成全路径
	 * @param path
	 *            shell文件路径
	 */
	private ShellFileDefine(final String fileName, final String path) {
		this.shellFullPath = path + fileName;
	}

	/**
	 * 获取完整的shell路径
	 * 
	 * @return {@link #shellFullPath}
	 */
	public String getShellFullPath() {
		return shellFullPath;
	}
}
