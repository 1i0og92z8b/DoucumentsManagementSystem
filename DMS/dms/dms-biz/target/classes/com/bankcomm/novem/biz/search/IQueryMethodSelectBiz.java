package com.bankcomm.novem.biz.search;

import java.util.List;

import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;

public interface IQueryMethodSelectBiz {

	List<FileBo> QueryMethodSelectByFlag(FileFieldBo fileFieldBo,int flag);
}
