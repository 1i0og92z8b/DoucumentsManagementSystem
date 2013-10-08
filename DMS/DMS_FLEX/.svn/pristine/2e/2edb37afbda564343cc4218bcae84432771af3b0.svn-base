package DMS_Util
{
	public class Global
	{
		private static var env:String = "DEV";
		
		//dev环境
		private static var IP_DEV:String="http://localhost:8088/dms-action/";
		//SIT环境
		private static var IP_SIT:String="http://docms.bankcomm.com:9080/dms/";
		//uat环境
		private static var IP_UAT:String="http://docms.bocomm.uat:9080/dms/"; 
		//prd环境
		private static var IP_PRD:String="http://docms.bocomm.com/dms/";
		
		[Bindable]
		public static var ICON_IP:String="";
		
		[Bindable]
		public static var indexURL:String="index.jsp";
		
		[Bindable]
		public static var session:Object={};
		
		[Bindable]
		public static var appName:String = "dms";
		
		public static function get debug():Boolean{
			
			if (env == "DEV"){
				
				return true;
				
			} else {
				
				return false;
			}
			
		}
		
		public static function get IP():String{
			
			if (env == "DEV"){
				
				return IP_DEV;
				
			} else if (env == "SIT"){
				
				return IP_SIT;
				
			} else if (env == "UAT"){
				
				return IP_UAT;
				
			} else if (env == "PRD"){
				
				return IP_PRD;
				
			} else {
				throw new Error(env + " is unknow Environment keywords");
			}
		}
		
		public static function set IP(value:String):void{
			
			if (env == "DEV"){
				
				IP_DEV = value;
				
			} else if (env == "SIT"){
				
				IP_SIT = value;
				
			} else if (env == "UAT"){
				
				IP_UAT = value;
				
			} else if (env == "PRD"){
				
				IP_PRD = value;
				
			} else {
				throw new Error(env + " is unknow Environment keywords");
			}
		}
		
	}
}