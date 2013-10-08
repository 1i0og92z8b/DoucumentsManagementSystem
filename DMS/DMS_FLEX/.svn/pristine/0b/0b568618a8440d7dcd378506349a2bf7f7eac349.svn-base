package com.bankcomm.dms.components
{
	import DMS_Util.ConnectParameter;
	
	import com.bankcomm.URLParameter;
	import com.bankcomm.Connect;
	
	import mx.controls.Alert;
	import flash.events.Event;
	import com.bankcomm.dms.bo.SessionBO;
	
	public class FileDelete
	{
		public function FileDelete()
		{
			
		}
		
		public function deleteFile(fileId:int):void
		{
			// TODO Auto-generated method stub
			var params:URLParameter = new URLParameter();	
			params.reqBody = {"fileId":fileId,"userId":SessionBO.userInfo.userId};
			
			Connect.request(new ConnectParameter({url:"deleteFile.ajax",params:params.toParameter(),callback:function(r:Object,e:Event):void{
				
			},isMaskAvailable:true
			}));
		}
	}
}