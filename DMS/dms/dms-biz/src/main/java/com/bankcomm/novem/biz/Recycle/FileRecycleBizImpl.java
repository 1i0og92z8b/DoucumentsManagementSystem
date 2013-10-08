package com.bankcomm.novem.biz.Recycle;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.file.IFileManageBiz;
import com.bankcomm.novem.biz.search.IQueryMethodSelectBiz;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.file.ConstantFileState;
import com.bankcomm.novem.bo.file.FileUpdateStateBo;
import com.bankcomm.novem.comm.PageCond;

@Service
public class FileRecycleBizImpl extends BaseBiz implements IFileRecycleBiz {

	@Autowired
	private IQueryMethodSelectBiz queryMethodSelectBizImpl;
	
	@Autowired
	private IFileManageBiz fileManageBizImpl;
	
	@Override
	public List<FileBo> queryFileByState(final FileFieldBo fileFieldBo)
	{
		int flag = 0; //调用fileQueryByFileState方法		
		
		return queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
	}
	
	@Override
	public List<FileBo> queryFileByUserIdAndFileStateAndFileName(final FileFieldBo fileFieldBo)
	{
		int flag = 5; //调用fileQueryByFileState方法		
		
		return queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
	}
	
//	@Override
//	public List<FileBo> queryUnpassFileByFileName(String fileName)
//	{
//		int flag = 3;  //调用fileQueryByFileNameAndFileState方法
//
//		char fileState = ConstantFileState.DeniedState;  //查询审核未通过文件
//		FileFieldBo fileFieldBo = new FileFieldBo();
//		fileFieldBo.setFileName(fileName);
//		fileFieldBo.setFileState(fileState);
//		
//		return queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
//	}
//	
//	@Override
//	public List<FileBo> queryDeletedFileByFileName(String fileName)
//	{
//		int flag = 3;  //调用fileQueryByFileNameAndFileState方法
//
//		char fileState = ConstantFileState.DeletedState;  //查询已删除文件
//		FileFieldBo fileFieldBo = new FileFieldBo();
//		fileFieldBo.setFileName(fileName);
//		fileFieldBo.setFileState(fileState);
//		
//		return queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
//	}
	
	@Override
	public List<FileBo> queryRecycleFileByFileName(final FileFieldBo fileFieldBo)
	{
		int flag = 3;  //调用fileQueryByFileNameAndFileState方法

		return queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
	}
	
	@Override
	public boolean batchRecoverUnpassFiles(final List<Integer> fileIdList,final int userId)
	{
		for(int fId:fileIdList)
		{
			final char fState = ConstantFileState.UncheckedState;
			FileUpdateStateBo fileState = new FileUpdateStateBo();
			fileState.setFileId(fId);
			fileState.setFileState(fState);
			fileState.setModifyUser(userId);
			fileState.setUpdateTime(new Timestamp(System.currentTimeMillis()));

			if(!fileManageBizImpl.updateFileByState(fileState))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean batchRecoverDeletedFiles(final List<Integer> fileIdList,final int userId)
	{
		for(int fId:fileIdList)
		{
			final char fState = ConstantFileState.UncheckedState;
			FileUpdateStateBo fileState = new FileUpdateStateBo();
			fileState.setFileId(fId);
			fileState.setFileState(fState);
			fileState.setModifyUser(userId);
			fileState.setUpdateTime(new Timestamp(System.currentTimeMillis()));

			if(!fileManageBizImpl.updateFileByState(fileState))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean deleteFile(final int fileId,final int userId)
	{
		return fileManageBizImpl.deleteFile(fileId);
	}
	
	@Override
	public boolean batchDelete(final List<Integer> fileIdList,int userId)
	{
		return fileManageBizImpl.deleteFile(fileIdList);
	}
	
}
