package com.bankcomm.novem.dao.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.file.DownloadCountsBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;
import com.bankcomm.novem.dao.BaseDao;
import com.bankcomm.novem.dao.annote.DbSchemaType;

@DbSchemaType
@Repository
public class FileDownloadDaoImpl extends BaseDao implements IFileDownloadDao {
	
	@Autowired
	private IFileDownloadMapper iFileDownloadMapper;
	
	@Override
	public void insertRec(DownloadRecBo downloadRec) {
		iFileDownloadMapper.insertRec(downloadRec);
	}
	
	@Override
	public boolean updateCounts(DownloadCountsBo downloadCountsBo) {
		return iFileDownloadMapper.updateCounts(downloadCountsBo);
	}
	
	@Override
	public boolean insertCounts(DownloadCountsBo downloadCountsBo) {
		return iFileDownloadMapper.insertCounts(downloadCountsBo);
	}
//	public void insertRecAndUpdateCounts(DownloadRecBo downloadRec, DownloadCountsBo downloadCountsBo) {
//		iFileDownloadMapper.insertRecAndUpdateCounts(downloadRec, downloadCountsBo);
//	}

}
