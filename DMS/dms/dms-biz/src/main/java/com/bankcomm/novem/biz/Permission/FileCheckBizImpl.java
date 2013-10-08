package com.bankcomm.novem.biz.Permission;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.file.IFileManageBiz;
import com.bankcomm.novem.biz.search.IQueryMethodSelectBiz;
import com.bankcomm.novem.bo.file.ConstantFileState;
import com.bankcomm.novem.bo.file.FileUpdateStateBo;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.comm.PageCond;

@Service
public class FileCheckBizImpl extends BaseBiz implements IFileCheckBiz {
	
	@Autowired
	private IQueryMethodSelectBiz queryMethodSelectBizImpl;
	@Autowired
	private IFileManageBiz fileManageBizImpl;
	
	@Override
	public List<FileBo> queryUncheckFiles(FileFieldBo fileFieldBo)
	{
		char fileState = ConstantFileState.UncheckedState; //待审核状态
		fileFieldBo.setFileState(fileState);
		
		int flag = 0; //调用fileQueryByFileState方法
		return queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
	}

	@Override
	public boolean checkPass(final int fileId,final int userId)
	{
		final char fState = ConstantFileState.NormalState;
		FileUpdateStateBo fileState = new FileUpdateStateBo();
		fileState.setFileId(fileId);
		fileState.setFileState(fState);
		fileState.setModifyUser(userId);
		fileState.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return fileManageBizImpl.updateFileByState(fileState);
	}
	
	@Override
	public boolean batchPass(final List<Integer> fileIdList,int userId)
	{
		for(int fId:fileIdList)
		{
			final char fState = ConstantFileState.NormalState;
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
	public boolean checkRefuse(final int fileId,final int userId)
	{
		final char fState = ConstantFileState.DeniedState;
		FileUpdateStateBo fileState = new FileUpdateStateBo();
		fileState.setFileId(fileId);
		fileState.setFileState(fState);
		fileState.setModifyUser(userId);
		fileState.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return fileManageBizImpl.updateFileByState(fileState);
	}
	
	@Override
	public boolean batchRefuse(final List<Integer> fileIdList,int userId)
	{
		for(int fId:fileIdList)
		{
			final char fState = ConstantFileState.DeniedState;
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
		final char fState = ConstantFileState.DeletedState;
		FileUpdateStateBo fileState = new FileUpdateStateBo();
		fileState.setFileId(fileId);
		fileState.setFileState(fState);
		fileState.setModifyUser(userId);
		fileState.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return fileManageBizImpl.updateFileByState(fileState);
	}
	
	@Override
	public boolean batchDelete(final List<Integer> fileIdList,int userId)
	{
		for(int fId:fileIdList)
		{
			final char fState = ConstantFileState.DeletedState;
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
}
