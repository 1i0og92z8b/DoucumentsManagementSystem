package com.bankcomm.dms.components
{
	import DMS_Util.Global;
	
	import com.bankcomm.URLParameter;
	
	import flash.events.Event;
	import flash.net.FileReference;
	import flash.net.URLLoader;
	import flash.net.URLLoaderDataFormat;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	
	import mx.controls.Alert;

	public class FileDownload
	{
		public function FileDownload()
		{
		}
		
		public function downloadFile(fileName:String, fileId:String, userId:String):void
		{
			var loader:URLLoader = new URLLoader();
			loader.dataFormat = URLLoaderDataFormat.BINARY;
			var req:URLRequest = new URLRequest(Global.IP + "downloadFile.ajax");
			var params:URLParameter = new URLParameter();
			params.reqBody = {"fileId":fileId,"userId":userId};
			req.data = params.toParameter();
			req.method = URLRequestMethod.POST;
			var fileRef:FileReference = new FileReference();
			fileRef.addEventListener(Event.COMPLETE, completeDownloadHandler);
			fileRef.download(req, fileName);
		}
		
		public function completeDownloadHandler(event:Event):void{
			mx.controls.Alert.show("下载成功");
		}
	}
}