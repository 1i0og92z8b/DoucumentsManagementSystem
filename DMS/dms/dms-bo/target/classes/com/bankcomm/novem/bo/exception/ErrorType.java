package com.bankcomm.novem.bo.exception;

/**
 * 异常处理
 * 
 * @author 张国明 zhanggm@rionsoft.com 砾阳 2011-3-24 下午03:26:39
 * 
 */
public enum ErrorType {
	/** 访问属性异常! */
	ACCESS_PROPERTY_ERROR,
	/** 从合同编号与押品ID关联已被占用 */
	ACONT_NO_COLLATERAL_ID_USED,
	/** 从合同编号已被占用 */
	ACONT_NO_USED,
	/** 业务数据操作异常 */
	APPLICATION_EXCEPTION,
	/** 不允许进行绑定排他锁数据 */
	BIND_EXCLUSIVE_LOCK_DATA,
	/** 不允许进行绑定内部锁数据 */
	BIND_INTERNAL_LOCK_DATA,
	/** 锁定的信息不支持绑定操作 */
	BIND_LOCKED_DATA,
	/** 生效后的数据做过更改，不允许退回 */
	CANCEL_EFFECT_FAILED_BECAUSE_DATA_HAS_BEEN_CHANGED,
	/** 未生效过的数据不允许退回 */
	CANCEL_EFFECT_FAILED_BECAUSE_DATA_HAS_NOT_BEEN_TAKE_EFFECT,
	/** 绑定的数据不能退回生效 */
	CANCEL_EFFECT_FAILED_BECAUSE_DATA_IS_BINDED,
	/** 查询不到相关CMIS信息 */
	CMIS_INFO_IS_NOT_EXIST,
	/** 押品编号已被占用 */
	// TODO 已被占用为什么是NO_USED
	COLLATERAL_NO_USED,
	/** 主合同编号与从合同编号已被占用 */
	// TODO 已被占用为什么是NO_USED
	CONTRACT_ACONT_NO_USED,
	/** 主合同数据异常 */
	CONTRACT_DATA_ERROR,
	/** 主合同编号已被占用 */
	// TODO 已被占用为什么是NO_USED
	CONTRACT_NO_USED,
	/** 同步数据失败 */
	COPY_DATA_FAIL,
	/** 日期解析字符串异常 */
	DATE_PARSE_ERROR,
	/** 删除数据失败 */
	DELETE_FAIL,
	/** 正本信息不允许删除 */
	DELETE_ORIGIN_DATA,
	/** 执行删除操作失败，数据使用中 */
	DELETE_USING_DATA,
	/** 已绑定数据不能加排他锁 */
	EXCLUSIVE_LOCK_BINDED_DATA,
	/** 排他锁的数据不能退回生效 */
	EXCLUSIVE_LOCK_DATA_CANT_CANCEL_EFFECT,
	/** 排他锁数据不能再加排他锁 */
	EXCLUSIVE_LOCK_EXCLUSIVE_LOCKED_DATA,
	/** 内部锁数据不允许进行排他锁定操作 */
	EXCLUSIVE_LOCK_INTERNAL_LOCKED_DATA,
	/** 已锁定数据不能加排他锁 */
	EXCLUSIVE_LOCK_LOCKED_DATA,
	/** 导出数据失败 */
	EXPORT_FAIL,
	/** 导出被锁定 */
	EXPORT_LOCK,
	/** 文件或文件目录不存在 */
	FILE_NOT_FOUND,
	/** 导入文件数据行插入异常 */
	IMPORT_DATA_INSERT_EXCEPTION,
	/** 读取导入文件数据行异常 */
	IMPORT_DATA_READ_EXCEPTION,
	/** 文件reader关闭异常 */
	IMPORT_FILEREADER_CLOSE_EXCEPTION,
	/** 导入被锁定 */
	IMPORT_LOCK,
	/** 读取导入临时文件异常 */
	IMPORT_TEMPFILE_READ_EXCEPTION,
	/** 导入模板编码错误失败 */
	IMPORT_TEMPLATE_ENCODE_ERROR,
	/** 读取导入文件表头异常 */
	IMPORT_TITLE_READ_EXCEPTION,
	/** 新增数据失败 */
	INSERT_FAIL,
	/** 内部锁定的数据不允许绑定 */
	INTERNAL_LOCK_BOUND_DATA,
	/** 排他锁数据不允许进行内部锁定操作 */
	INTERNAL_LOCK_EXCLUSIVE_LOCKED_DATA,
	/** 内部锁定的数据不能再内部锁定 */
	INTERNAL_LOCK_INTERNAL_LOCKED_DATA,
	/** 锁定数据不允许进行内部锁定操作 */
	INTERNAL_LOCK_LOCKED_DATA,
	/** 调用目标方法异常 */
	INVOKE_TARGET_METHOD_EXCEPTION,
	/** 贷款编号与贷款账号已被占用 */
	LOAN_ACCOUNT_NO_USED,
	/** 锁定的数据不能退回生效 */
	LOCK_DATA_CANT_CANCEL_EFFECT,
	/** 锁数据错误 */
	LOCK_DATA_ERROR,
	/** 排他锁数据不允许进行锁定操作 */
	LOCK_EXCLUSIVE_LOCKED_DATA,
	/** 内部锁数据不允许进行锁定操作 */
	LOCK_INTERNAL_LOCKED_DATA,
	/** 数据不存在 */
	NOT_FOUND_DATA,
	/** 查询数据失败 */
	QUERY_FAIL,
	/** 释放数据失败 */
	RELEASE_FAIL,
	/** 数据被锁定，无法释放 */
	RELEASE_LOCKED_DATA,
	/** 请求外系统数据异常 */
	REQ_EX_SYSTEM_DATA_ERROR,
	/** 请求外系统输入错误异常 */
	REQ_INPUT_ERROR,
	/** 操作风险未知异常 */
	RISK_UNKNOW_ERROR,
	/** 绑定的锁信息不能直接生效 */
	TAKE_EFFECT_BIND_DATA,
	/** 排他锁不允许进行生效操作 */
	TAKE_EFFECT_EXCLUSIVE_LOCK_DATA,
	/** 不允许针对空状态的锁信息生效 */
	TAKE_EFFECT_NULL_LOCK_DATA,
	/** 排他锁不允许进行解除绑定操作 */
	UNBIND_EXCLUSIVE_LOCK_DATA,
	/** 解绑数据失败 */
	UNBIND_FAIL,
	/** 内部锁定状态信息不支持解除绑定 */
	UNBIND_INTERNAL_LOCKED_DATA,
	/** 锁定状态信息不支持解除绑定 */
	UNBIND_LOCKED_DATA,
	/** 不允许针对空状态的锁信息解除绑定 */
	UNBIND_NULL_LOCK_DATA,
	/** 绑定的信息不支持解除锁定 */
	UNLOCK_BIND_DATA,
	/** 内部锁定数据不支持解除锁定 */
	UNLOCK_INTERNAL_LOCKED_DATA,
	/** 不允许针对空状态的锁信息解除锁定 */
	UNLOCK_NULL_LOCK_DATA,
	/** 数据已经被删除 */
	UPDATE_ALREADY_DATA,
	/** 修改数据失败 */
	UPDATE_FAIL,
	/** 数据被锁定，无法更新 */
	UPDATE_LOCKED_DATA,
	/** 用户登出 */
	USER_LOGOUT,
	/** 用户不存在 */
	USER_NOT_EXIST,
	/** 用户没有登陆 */
	USER_NOT_LOGIN,
	/** 用户名或密码不正确！ */
	USERNAME_OR_PASSWORD_INCORRECT,
	/** 流程状态异常 */
	WF_EXCEPTION
}