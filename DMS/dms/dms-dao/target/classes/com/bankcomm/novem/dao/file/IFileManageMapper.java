package com.bankcomm.novem.dao.file;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.file.FileUpdateBo;
import com.bankcomm.novem.bo.file.FileUpdateCatBo;
import com.bankcomm.novem.bo.file.FileUpdateStateBo;
import com.bankcomm.novem.bo.file.FileUploadBo;

@Repository
public interface IFileManageMapper {
	/**
	 * 插入文件
	 */
	int insertFile(final FileUploadBo newFile);
	/**
	 * 删除文件
	 */
	boolean deleteFile(final int fileId);
	/**
	 * 批量删除文件
	 */
	boolean deleteFiles(final List<Integer> fileIds);
	//boolean deleteFile(final List<Integer> fileIds);
	/**
	 * 更新文件信息
	 */
	//boolean updateFile(final FileUpdateBo updatedFile, final int fileId);
	boolean updateFile(final FileUpdateBo updatedFile);
	/**
	 * 更新文件状态
	 */
	boolean updateFileByState(final FileUpdateStateBo fileState);
	/**
	 * 更新文件类别
	 */
	boolean updateFileByCat(final FileUpdateCatBo fileCat);

}
