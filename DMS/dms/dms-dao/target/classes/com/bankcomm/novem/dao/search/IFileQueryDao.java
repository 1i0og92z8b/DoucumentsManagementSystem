package com.bankcomm.novem.dao.search;

import java.util.List;

import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.comm.PageCond;

public interface IFileQueryDao {

	List<FileBo> fileQueryByFileState(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByCategoryId(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByFileName(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByFileNameAndFileState(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByFileId(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByUserIdAndFileStateAndFileName(FileFieldBo fileFieldBo);
	
	List<FileBo> fileSearchByFileFieldBo(FileFieldBo fileFieldBo);
	
	List<Integer> categoryIdQuery(FileFieldBo fileFieldBo);
}
