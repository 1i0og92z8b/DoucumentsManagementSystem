package com.bankcomm.novem.biz.Permission;

import java.util.List;

import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.comm.PageCond;

public interface IFileCheckBiz {

	/**
	 * 查看待审核文件列表
	 * @param
	 * @return 结果集
	 */
	List<FileBo> queryUncheckFiles(final FileFieldBo fileFieldBo);
	
	/**
	 * 审核通过
	 * @param  fileID
	 * @return 是否成功
	 */
	boolean checkPass(final int fileId,final int userId);
	
	/**
	 * 批量审核通过
	 * @param  fileID
	 * @return 是否成功
	 */
	boolean batchPass(final List<Integer> fileIdList,final int userId);
	
	/**
	 * 审核不通过
	 * @param  fileID
	 * @return 是否成功
	 */
	boolean checkRefuse(final int fileId,final int userId);
	
	/**
	 * 批量审核不通过
	 * @param  fileID
	 * @return 是否成功
	 */
	boolean batchRefuse(final List<Integer> fileIdList,final int userId);
	
	/**
	 * 删除文件
	 * @param  fileID
	 * @return 是否成功
	 */
	boolean deleteFile(final int fileId,final int userId);
	
	/**
	 * 批量删除文件
	 * @param  fileID
	 * @return 是否成功
	 */
	boolean batchDelete(final List<Integer> fileIdList,final int userId);
}
