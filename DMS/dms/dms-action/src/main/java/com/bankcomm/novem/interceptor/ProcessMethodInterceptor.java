package com.bankcomm.novem.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import com.bankcomm.novem.biz.common.IManageProcessLogBiz;
import com.bankcomm.novem.bo.common.ProcessLogBo;
import com.bankcomm.novem.bo.user.NovemUser;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.core.Interceptor;

/**
 * @author sunpu<sun.pu@bankcomm.com> Jul 18, 2012
 * 
 */
public class ProcessMethodInterceptor implements Interceptor {

	@Autowired
	private IManageProcessLogBiz manageProcessLogBiz;

	@Override
	public void onRequest(final Context context) {
		//
	}

	@Override
	public void onResponse(final Context context, final Throwable throwable) {
		String msg = "";
		String processFlag = "SUCCESS";
		long userId = 0;
		long orgId = 0;
		if (throwable != null) {
			processFlag = "FAIL";
			if (throwable instanceof Exception) {
				final Exception exception = (Exception) throwable;
				msg = exception.getCause() != null ? exception.getCause()
						.getMessage() : "";
			} else {
				msg = throwable.getMessage();
			}
		}
		if (context.getUser() != null) {
			final NovemUser novemUser = (NovemUser) context.getUser();
			userId = novemUser.getUId();
			orgId = novemUser.getOrgId();
		}
		final String processId = context.getProcessId();

		final ProcessLogBo processLogBo = buildProcessLogBo(processId,
				processFlag, msg, userId, orgId, null);
		manageProcessLogBiz.insertProcessLog(processLogBo);
	}

	private ProcessLogBo buildProcessLogBo(final String processId,
			final String processFlag, final String msg, final long processUser,
			final long processOrg, final String remark) {

		final ProcessLogBo processLogBo = new ProcessLogBo();
		processLogBo.setProcessMsg(msg);
		processLogBo.setProcessFlag(processFlag);
		processLogBo.setProcessRemark(remark);
		processLogBo.setProcessUser(processUser);
		processLogBo.setProcessOrg(processOrg);
		processLogBo.setProcessId(processId);
		return processLogBo;
	}

}
