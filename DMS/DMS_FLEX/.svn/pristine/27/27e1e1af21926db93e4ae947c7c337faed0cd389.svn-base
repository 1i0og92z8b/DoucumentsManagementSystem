package com.bankcomm.dms.bo
{
	import DMS_Util.Global;
	
	import com.bankcomm.DataDictContainer;

	public class SessionBO
	{
		/**
		 * 存放用户信息
		 * */
		private static var _userInfo:Object;
		
		public static function set userInfo(value:Object):void{
			if (!DataDictContainer.Dictionary.hasOwnProperty([Global.appName])){
				DataDictContainer.Dictionary[Global.appName] = new Object();
			}
			if (!DataDictContainer.Dictionary[Global.appName].hasOwnProperty("userInfo")){
				DataDictContainer.Dictionary[Global.appName]["userInfo"] = value;
			}
		}
		
		public static function get userInfo():Object{
			if (!DataDictContainer.Dictionary.hasOwnProperty([Global.appName])){
				return null;
			}
			return DataDictContainer.Dictionary[Global.appName]["userInfo"];
		}
	}
}