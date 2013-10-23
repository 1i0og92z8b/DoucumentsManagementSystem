package com.bankcomm.novem.biz.Recycle;

import java.util.List;

import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.comm.PageCond;

public interface IFileRecycleBiz {

	/**
	 * 查看回收站中文件列表
	 * @param
	 * @return 结果集
	 */
	List<FileBo> queryFileByState(final FileFieldBo fileFieldBo);
	
	/**
	 * 根据用户ID查看回收站中文件列表
	 * @param
	 * @return 结果集
	 */
	List<FileBo> queryFileByUserIdAndFileStateAndFileName(final FileFieldBo fileFieldBo);
	
//	/**
//	 * 按文件名模糊查询审核未通过文件
//	 * @param fileName
//	 * @return 结果集
//	 */
//	List<FileBo> queryUnpassFileByFileName(final String fileName);
//	
//	/**
//	 * 按文件名模糊查询已删除文件
//	 * @param fileName
//	 * @return 结果集
//	 */
//	List<FileBo> queryDeletedFileByFileName(final String fileName);
	
	/**
	 * 按文件名模糊查询回收站中的文件
	 * @param fileName
	 * @return 结果集
	 */
	List<FileBo> queryRecycleFileByFileName(final FileFieldBo fileFieldBo);
	
	/**
	 * 恢复审核未通过文件
	 * @param fileID
	 * @return 成功与否
	 */
//	boolean recoverUnpassFile(final int fileId,final int userId);
	
	/**
	 * 批量恢复审核未通过文件
	 * @param fileID
	 * @return 成功与否
	 */
	boolean batchRecoverUnpassFiles(final List<Integer> fileIdList,final int userId);
	
	/**
	 * 恢复已删除文件
	 * @param fileID
	 * @return 成功与否
	 */
//	boolean recoverDeletedFile(final int fileId,final int userId);
	
	/**
	 * 批量恢复已删除文件
	 * @param fileID
	 * @return 成功与否
	 */
	boolean batchRecoverDeletedFiles(final List<Integer> fileIdList,final int userId);
	
	/**
	 * 彻底删除文件
	 * @param fileID,userID
	 * @return 成功与否
	 */
	boolean deleteFile(final int fileId);
	
	/**
	 * 批量彻底删除文件
	 * @param fileID,userID
	 * @return 成功与否
	 */
	boolean batchDelete(final List<Integer> fileIdList);
}
