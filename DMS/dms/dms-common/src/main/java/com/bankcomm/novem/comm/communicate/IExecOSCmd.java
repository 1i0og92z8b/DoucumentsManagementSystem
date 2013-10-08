package com.bankcomm.novem.comm.communicate;


/**
 * @author jiarch<arms_admin@bankcomm.com> 2011-10-17
 * 
 */
public interface IExecOSCmd {
	/**
	 * 执行操作命令
	 * 
	 * @param cmd
	 *            执行命令
	 * @return 返回结果
	 */
	int execOSCmd(final String cmd);

	/**
	 * 执行操作命令 当参数中包含空格时只能使用此方法
	 * 
	 * @param cmdArray
	 *            执行命令数据
	 * @return 返回结果
	 */
	int execOSCmd(final String[] cmdArray);

	/**
	 * 执行shell命令
	 * 
	 * @param shellFilePath
	 *            执行命令
	 * 
	 * @return 返回结果
	 */
	int execShellCmd(final ShellFileDefine shellFilePath);

	/**
	 * 执行shell命令
	 * 
	 * @param shellFilePath
	 *            执行命令
	 * @param parameter
	 *            参数
	 * @return 返回结果
	 */
	int execShellCmd(final ShellFileDefine shellFilePath, final String parameter);
}
