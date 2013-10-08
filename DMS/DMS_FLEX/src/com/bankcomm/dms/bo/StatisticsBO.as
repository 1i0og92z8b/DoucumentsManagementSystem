package com.bankcomm.dms.bo
	
import  com.bankcomm.novem.bo.statistics.DownloadedFileBo;
import  com.bankcomm.novem.bo.statistics.UserRankingBo;	
import  com.bankcomm.novem.comm.PageCond;
{
	public class StatisticsBO
	{	
		private static _pageCond: PageCond;
		
		private static _userRankingBo: UserRankingBo;
		
		private static _downloadedFileBo: DownloadedFileBo;
	
		public function get pageCond():PageCond
		{
			return _pageCond;
		}
		
		public function set pageCond(input:PageCond):void{
			this._pageCond=input;
		}
		
		public function get userRankingBo():UserRankingBo
		{
			return _userRankingBo;
		}
		
		public function set userRankingBo(input:UserRankingBo):void{
			this._userRankingBo=input;
		}
		
		public function get downloadedFileBo():DownloadedFileBo
		{
			return _downloadedFileBo;
		}
		
		public function set downloadedFileBo(input:DownloadedFileBo):void{
			this._downloadedFileBo=input;
		}
	
	}
}