package com.bankcomm.novem.interceptor;

import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.core.CoreException;
import com.bocom.jump.bp.core.CoreRuntimeException;
import com.bocom.jump.bp.core.Interceptor;

//import com.bocom.guop.common.bean.UserInfo;
//import com.bocom.guop.common.utils.ErrorCodeConst;
//import com.bocom.guop.common.utils.GUOPConst;

/**
 * 
 * @Title: UserSessionInterceptor.java
 * @Package com.bankcomm.novem.interceptor
 * @Description: Session产生拦截器
 * @author 程东升 -------- E-mail: cheng_ds@bankcomm.com
 * @date 2013-8-30 下午4:04:25
 * @version V1.0
 */

public class UserSessionInterceptor implements Interceptor{
		public void onRequest() {			
		}

		public void onResponse() {
		}

		@Override
		public void onRequest(Context arg0) throws CoreException,
				CoreRuntimeException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onResponse(Context arg0, Throwable arg1) {
			// TODO Auto-generated method stub
			
		}
}
