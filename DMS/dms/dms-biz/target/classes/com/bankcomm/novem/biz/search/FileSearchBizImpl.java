package com.bankcomm.novem.biz.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.dao.search.FileQueryDaoImpl;


@Service
public class FileSearchBizImpl implements IFileSearchBiz {
	
	@Autowired
	FileQueryDaoImpl fileQueryDaoImpl;

	@Override
	public List<FileBo> fileSearch(FileFieldBo fileFieldBo) {
		return fileQueryDaoImpl.fileSearchByFileFieldBo(fileFieldBo);
	}

}
