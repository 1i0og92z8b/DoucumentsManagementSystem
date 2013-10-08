package com.bankcomm.novem.comm.utils;

import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bankcomm.novem.comm.communicate.IExecOSCmd;
import com.bankcomm.novem.comm.communicate.ShellFileDefine;
import com.bankcomm.novem.comm.stream.StreamGobblerError;
import com.bankcomm.novem.comm.stream.StreamGobblerInfo;

/**
 * 执行操作命令工具类
 * 
 * @author jiarch<arms_admin@bankcomm.com> 2011-10-17
 * 
 */
@Component
@Slf4j
public class ExecOSCmdImpl implements IExecOSCmd {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.arms.dao.tmp.IExecOSCmd#execOSCmd(java.lang.String)
	 */
	@Override
	public int execOSCmd(final String cmd) {
		log.info("执行的操作命令是:" + cmd);
		final Process process = getProcess(cmd);
		getPrintInfo(process);
		return getWaitForResult(process);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.arms.dao.tmp.IExecOSCmd#execOSCmd(java.lang.String)
	 */
	@Override
	public int execOSCmd(final String[] cmdArray) {
		log.info("执行的操作命令是:" + StringUtils.join(cmdArray, ' '));
		final Process process = getProcess(cmdArray);
		getPrintInfo(process);
		return getWaitForResult(process);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.arms.common.IExecOSCmd#execShellCmd(com.bankcomm.arms.common
	 * .shell.ShellFileDefine)
	 */
	@Override
	public int execShellCmd(final ShellFileDefine shellFilePath) {
		final String shellCmd = "sh " + shellFilePath.getShellFullPath();
		return execOSCmd(shellCmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankcomm.arms.common.IExecOSCmd#execShellCmd(com.bankcomm.arms.common
	 * .shell.ShellFileDefine, java.lang.String)
	 */
	@Override
	public int execShellCmd(final ShellFileDefine shellFilePath,
			final String parameter) {
		String shellCmd = "sh " + shellFilePath.getShellFullPath();
		if (hasText(parameter)) {
			shellCmd += " " + parameter;
		}
		return execOSCmd(shellCmd);
	}

	/**
	 * 打印信息命令执行
	 * 
	 * @param process
	 *            执行进程
	 */
	private void getPrintInfo(final Process process) {
		final StreamGobblerError errorGobbler = new StreamGobblerError(
				process.getErrorStream());
		final StreamGobblerInfo infoGobbler = new StreamGobblerInfo(
				process.getInputStream());
		errorGobbler.start();
		infoGobbler.start();
	}

	/**
	 * 获取执行进程
	 * 
	 * @param cmd
	 *            执行命令
	 * @return 执行进程
	 */
	private Process getProcess(final String cmd) {
		try {
			return Runtime.getRuntime().exec(cmd);
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException("获取执行进程异常", e);
		}
	}

	/**
	 * 获取执行进程
	 * 
	 * @param cmd
	 *            执行命令
	 * @return 执行进程
	 */
	private Process getProcess(final String[] cmdArray) {
		try {
			return Runtime.getRuntime().exec(cmdArray);
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException("获取执行进程异常", e);
		}
	}

	/**
	 * 获取执行结果
	 * 
	 * @param process
	 *            执行进程
	 * @return 执行结果
	 */
	private int getWaitForResult(final Process process) {
		Assert.notNull(process, "执行命令没有获取到进程");
		try {
			return process.waitFor();
		} catch (final InterruptedException e) {
			throw new IllegalStateException("执行命令出现异常", e);
		}
	}
}