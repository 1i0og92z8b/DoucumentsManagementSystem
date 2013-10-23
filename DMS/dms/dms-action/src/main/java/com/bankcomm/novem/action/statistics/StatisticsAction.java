package com.bankcomm.novem.action.statistics;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.statistics.IStatisticsBiz;
import com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import com.bankcomm.novem.bo.statistics.FilePathBo;
import com.bankcomm.novem.bo.statistics.UserRankingBo;
import com.bankcomm.novem.bo.file.DownloadRecBo;
import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.bankcomm.novem.dao.file.IFileDownloadDao;

import org.apache.tools.zip.ZipOutputStream;
import org.apache.tools.zip.ZipEntry;

/**
 * 
 * @author 荆昊熠<jing_hy@bankcomm.com> 
 * 
 */
@Controller
public class StatisticsAction extends BaseAction{
	
	@Autowired
	private IStatisticsBiz iStatisticsBiz;
	
//	@Autowired
//	private IFileManageBiz ifileManageBiz;
	
	@Autowired
	private IFileDownloadDao iFileDownloadDao;
	
	public void topDownloader(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<UserRankingBo> list = iStatisticsBiz.topDownloader(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);//这里的pageCond包含总条目数
	}
	
	public void topDownloadedFileOnlySearchable(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<DownloadedFileBo> list = iStatisticsBiz.topDownloadedFileOnlySearchable(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);	//这里的pageCond包含总条目数	
	}
	
	public void topDownloadedFile(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<DownloadedFileBo> list = iStatisticsBiz.topDownloadedFile(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);	//这里的pageCond包含总条目数	
	}
	
	public void topUploaderByFileCount(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<UserRankingBo> list = iStatisticsBiz.topUploaderByFileCount(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);//这里的pageCond包含总条目数
	}
	
	public void topUploaderByDownloadCount(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<UserRankingBo> list = iStatisticsBiz.topUploaderByDownloadCount(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);//这里的pageCond包含总条目数
	}
	
	public void recentUploadedFile(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<DownloadedFileBo> list = iStatisticsBiz.recentUploadedFile(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);	//这里的pageCond包含总条目数	
	}
	
	public void recentUploadedFileOnlySearchable(final Context context)
	{
		PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(pageCond == null){
			pageCond = new PageCond();
		}
		final List<DownloadedFileBo> list = iStatisticsBiz.recentUploadedFileOnlySearchable(pageCond);//总条目数通过pageCond返回
		context.setData("list", list);
		context.setData("PAGE_COND", pageCond);	//这里的pageCond包含总条目数	
	}
	
	public void updateDownloadCount(final Context context)
	{		
		final int fileId =  ContextExtractor.extractValue(context, "fileId");
     	iStatisticsBiz.updateDownloadCount(fileId);
	}
	
	public void updateDownloadCountList(final Context context)//接收需要更新下载量文件的List
	{		
		final List<Integer> fileIdList =  (List<Integer>) ContextExtractor.extractArray(context, "fileIdList",Integer.class);
		final int userId = ContextExtractor.extractValue(context,"userId");
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		String str = "(";
		for (int i=0;i<fileIdList.size();i++)
		{
			str+=fileIdList.get(i);
			if(i!=fileIdList.size()-1)
				str+=",";
		}
		str+=")";
		for (Integer itemId : fileIdList )
		{
		DownloadRecBo downloadRec = new DownloadRecBo();
		downloadRec.setFileId(itemId);
		downloadRec.setUserId(userId);
		downloadRec.setDownloadTime(ts);
		downloadRec.setCreateTime(ts);
		downloadRec.setUpdateTime(ts);
		downloadRec.setModifyUser(userId);
		iFileDownloadDao.insertRec(downloadRec);
		}
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("updateTime", ts);
		hm.put("modifyUser", userId);
		hm.put("downloadCountList", str);//把要找寻文件id的字符串传进去
		iStatisticsBiz.updateDownloadCountList(hm);
	}
	
	public void insertDownloadCount(final Context context)
	{
		final int fileId =  ContextExtractor.extractValue(context, "fileId");
		iStatisticsBiz.insertDownloadCount(fileId);		
	}	
	
	public void batchDownload(final Context context){
		final String zipFilePath = (String) ContextExtractor.extractValue(context, "zipFilePath");//接收参数包含绝对路径名
		context.setData("zipFilePath", zipFilePath);
	}
	
	public void deleteTempFile(final Context context){
		final String zipFilePath = (String) ContextExtractor.extractValue(context, "zipFilePath");//接收参数包含绝对路径名 
		File fi = new File(zipFilePath);
		fi.delete();
	}
	  
	  public void compressFile(final Context context)//接收前台返回的带路径文件名列表，提供打包好的Zip文件的下载
	  {
		  String baseDirPath = "D:\\";
		  final List<Integer> fileIdList = (List<Integer>) ContextExtractor.extractArray(context, "fileIdList", Integer.class);
		  final String zipFileName = baseDirPath + (String) ContextExtractor.extractValue(context, "zipFileName");//包含路径部分
		  
		  try {
		  byte data[] = new byte[8192];
			
		  List<Integer> fid = new ArrayList<Integer>();
		  List<File> fl = new ArrayList<File>(); 
		  List<String> fnl = new ArrayList<String>(); 
		  List<FilePathBo> filePathBoList = iStatisticsBiz.filePathQueryByFileId(fileIdList);

          for (int i = 0; i<filePathBoList.size();i++)//遍历返回的查询结果	
		  {	
        	  int id= filePathBoList.get(i).getFileId();
			  File f = new File(filePathBoList.get(i).getFilePath()+"\\"+filePathBoList.get(i).getFullName());
			  System.out.println(filePathBoList.get(i).getFilePath()+"\\"+filePathBoList.get(i).getFullName());
			  String s = new String(filePathBoList.get(i).getFileName());
			  if (f.exists()==true)//不添加不存在的文件
			  {
				  fid.add(id);
				  fl.add(f);
				  fnl.add(s);
			  }
		  }
          
          if (fnl.size()>0)//文件数量不为0时才压缩
    	  {
          BufferedInputStream origin = null;
    	  String tempZipfileName = zipFileName;//压缩文件名称，返回该文件下载
		  FileOutputStream dest = new FileOutputStream(tempZipfileName);
		  ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(dest));
          for (int i = 0; i < fl.size(); i++) {
              FileInputStream fi = new FileInputStream(fl.get(i));
              origin = new BufferedInputStream(fi, 8192);
              ZipEntry entry = new ZipEntry(fnl.get(i));
              output.putNextEntry(entry);
              int count;
              while ((count = origin.read(data, 0, 8192)) != -1) {
            	  output.write(data, 0, count);
              }
              origin.close();
          }
          output.close(); 
    	  }
          context.setData("returnFileNameList",fnl); 
          context.setData("returnFileNameCount",fnl.size());
          context.setData("returnFileIdList",fid);
	  }
	  catch (Exception e) {
          e.printStackTrace();
     }
		  
		  
	 }
}
