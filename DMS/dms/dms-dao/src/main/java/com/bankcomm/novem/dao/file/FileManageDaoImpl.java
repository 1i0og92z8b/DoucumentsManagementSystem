package com.bankcomm.novem.dao.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.file.FileUpdateBo;
import com.bankcomm.novem.bo.file.FileUpdateCatBo;
import com.bankcomm.novem.bo.file.FileUpdateStateBo;
import com.bankcomm.novem.bo.file.FileUploadBo;
import com.bankcomm.novem.dao.BaseDao;
import com.bankcomm.novem.dao.annote.DbSchemaType;

@DbSchemaType
@Repository
public class FileManageDaoImpl extends BaseDao implements IFileManageDao {
	
	@Autowired
	private IFileManageMapper iFileManageMapper;
	
	@Override
	public int insertFile(final FileUploadBo newFile) {
		return iFileManageMapper.insertFile(newFile);
	}
	
	@Override
	public boolean deleteFile(final int fileId) {
		return iFileManageMapper.deleteFile(fileId);
	}
	
	@Override
	public boolean deleteFiles(final List<Integer> fileIds) {
		return iFileManageMapper.deleteFiles(fileIds);
	}
	/*@Override
	public boolean deleteFile(final List<Integer> fileIds) {
		return iFileManageMapper.deleteFile(fileIds);
	}*/

	@Override
	public boolean updateFile(final FileUpdateBo updatedFile) {
		return iFileManageMapper.updateFile(updatedFile);
	}

	@Override
	public boolean updateFileByState(final FileUpdateStateBo fileState) {
		return iFileManageMapper.updateFileByState(fileState);
	}

	@Override
	public boolean updateFileByCat(final FileUpdateCatBo fileCat) {
		return iFileManageMapper.updateFileByCat(fileCat);
	}

}
