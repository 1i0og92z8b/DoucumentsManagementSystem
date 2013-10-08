package com.bankcomm.novem.dao.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.dao.annote.DbSchemaType;

@DbSchemaType("DMSDB")
@Repository
public class FileQueryDaoImpl implements IFileQueryDao {
	
	@Autowired
	private IFileQueryMapper iFileQueryMapper;

	@Override
	public List<FileBo> fileQueryByFileName(FileFieldBo fileFieldBo) {
		final int count = iFileQueryMapper.queryCount(fileFieldBo);
		PageCond pageCond = fileFieldBo.getPageCond();
		pageCond.setCOUNT(count);
		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.fileQueryByFileName(fileFieldBo);
	}

	@Override
	public List<FileBo> fileQueryByFileState(FileFieldBo fileFieldBo) {
		final int count = iFileQueryMapper.queryCount(fileFieldBo);
		PageCond pageCond = fileFieldBo.getPageCond();
		pageCond.setCOUNT(count);
		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.fileQueryByFileState(fileFieldBo);
	}

	@Override
	public List<FileBo> fileQueryByCategoryId(FileFieldBo fileFieldBo) {
		final int count = iFileQueryMapper.queryCount(fileFieldBo);
		PageCond pageCond = fileFieldBo.getPageCond();
		pageCond.setCOUNT(count);
		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.fileQueryByCategoryId(fileFieldBo);
	}

	@Override
	public List<FileBo> fileQueryByFileId(FileFieldBo fileFieldBo) {
//		final int count = iFileQueryMapper.queryCount(fileFieldBo);
//		PageCond pageCond = new PageCond();
//		pageCond.setCOUNT(count);
//		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.fileQueryByFileId(fileFieldBo);
	}

	@Override
	public List<FileBo> fileSearchByFileFieldBo(FileFieldBo fileFieldBo) {
		final int count = iFileQueryMapper.queryCount(fileFieldBo);
		PageCond pageCond = fileFieldBo.getPageCond();
		pageCond.setCOUNT(count);
		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.fileSearchByFileFieldBo(fileFieldBo);
	}

	@Override
	public List<Integer> categoryIdQuery(FileFieldBo fileFieldBo) {
//		final int count = iFileQueryMapper.queryCount(fileFieldBo);
//		PageCond pageCond = new PageCond();
//		pageCond.setCOUNT(count);
//		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.categoryIdQuery(fileFieldBo);
	}

	@Override
	public List<FileBo> fileQueryByFileNameAndFileState(FileFieldBo fileFieldBo) {
		final int count = iFileQueryMapper.queryCount(fileFieldBo);
		PageCond pageCond = new PageCond();
		pageCond.setCOUNT(count);
		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.fileQueryByFileNameAndFileState(fileFieldBo);
	}

	@Override
	public List<FileBo> fileQueryByUserIdAndFileStateAndFileName(FileFieldBo fileFieldBo) {
		final int count = iFileQueryMapper.queryCount(fileFieldBo);
		PageCond pageCond = fileFieldBo.getPageCond();
		pageCond.setCOUNT(count);
		fileFieldBo.setPageCond(pageCond);
		return iFileQueryMapper.fileQueryByUserIdAndFileStateAndFileName(fileFieldBo);
	}



}
