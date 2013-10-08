package com.bankcomm.novem.dao.search;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.comm.PageCond;

@Repository
public interface IFileQueryMapper {

	List<FileBo> fileQueryByFileState(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByCategoryId(FileFieldBo fileFieldBo);
	
	
	List<FileBo> fileQueryByFileName(FileFieldBo fileFieldBo);
//	List<FileBo> fileQueryByFileName(@Param(value="fileName")String fileName,@Param(value="pageCond") PageCond pageCond);
	
	List<FileBo> fileQueryByFileNameAndFileState(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByFileId(FileFieldBo fileFieldBo);
	
	List<FileBo> fileQueryByUserIdAndFileStateAndFileName(FileFieldBo fileFieldBo);
	
	List<FileBo> fileSearchByFileFieldBo(FileFieldBo fileFieldBo);
	
	List<Integer> categoryIdQuery(FileFieldBo fileFieldBo);
	
	//分页使用
	int queryCount(FileFieldBo fileFieldBo);
}
