package com.bankcomm.novem.biz.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.dao.search.FileQueryDaoImpl;

@Service
public class QueryMethodSelectBizImpl implements IQueryMethodSelectBiz {

	@Autowired
	FileQueryDaoImpl fileQueryDaoImpl;

	@Override
	public List<FileBo> QueryMethodSelectByFlag(FileFieldBo fileFieldBo,int flag) {
		
		if (0 == flag) {
			return fileQueryDaoImpl.fileQueryByFileState(fileFieldBo);
		} 
		else if (1 == flag) {

			return fileQueryDaoImpl.fileQueryByCategoryId(fileFieldBo);
		} 
		else if (2 == flag) {
			return fileQueryDaoImpl.fileQueryByFileName(fileFieldBo);
		} 
		else if (3 == flag) {
			return fileQueryDaoImpl.fileQueryByFileNameAndFileState(fileFieldBo);
		} 
		else if (4 == flag) {
			return fileQueryDaoImpl.fileQueryByFileId(fileFieldBo);
		} 
		else {
			return fileQueryDaoImpl.fileQueryByUserIdAndFileStateAndFileName(fileFieldBo);
		} 

	}

}
