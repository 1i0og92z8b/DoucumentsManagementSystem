package com.bankcomm.dms.bo
{
	public class DemoBO
	{
		private var _demoId:int;
		
		private var _demoName:String;
		
		private var _demoNo:String;
		
		public function get demoId():int
		{
			return _demoId;
		}
		
		public function set demoId(value:int):void{
			this._demoId = value;
		}
		
		public function get demoName():String
		{
			return _demoName;
		}
		
		public function set demoName(value:String):void
		{
			this._demoName = value;
		}
		
		public function set demoNo(value:String):void
		{
			this._demoNo = value;
		}
		
		public function get demoNo():String
		{
			return _demoNo;
		}
		
	}
}