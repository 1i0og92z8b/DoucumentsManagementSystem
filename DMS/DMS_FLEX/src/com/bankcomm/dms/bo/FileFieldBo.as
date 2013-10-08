package com.bankcomm.dms.bo
{
//	private String fileName;
//	private String userName;
//	private int categoryId;
//	private int fileId;
//	private Timestamp startDate;
//	private Timestamp endDate;
//	private String keywords;
//	private char fileState;
	public class FileFieldBo
	{
		
		private var _fileName:String;
		
		private var _userName:String;
		
		private var _categoryId:int;
		
		private var _fileId:int;
		
		private var _userId:int;
		
		private var _startDate:String;
		
		private var _endDate:String;
		
		private var _keywords:Array;
		
		private var _fileState:String;
		
		
		public function get fileName():String
		{
			return _fileName;
		}
		
		public function set fileName(value:String):void{
			this._fileName=value;
		}
		
		
		public function get userName():String
		{
			return _userName;
		}
		
		public function set userName(value:String):void{
			this._userName=value;
		}
		
		
		public function get categoryId():int
		{
			return _categoryId;
		}
		
		public function set categoryId(value:int):void{
			this._categoryId=value;
		}
		
		public function get fileId():int
		{
			return _fileId;
		}
		
		public function set fileId(value:int):void{
			this._fileId=value;
		}
		
		public function get userId():int
		{
			return _userId;
		}
		
		public function set userId(value:int):void{
			this._userId=value;
		}

		public function get startDate():String
		{
			return _startDate;
		}
		
		public function set startDate(value:String):void{
			this._startDate=value;
		}
		
		public function get endDate():String
		{
			return _endDate;
		}
		
		public function set endDate(value:String):void{
			this._endDate=value;
		}
		
		public function get keywords():Array
		{
			return _keywords;
		}
		
		public function set keywords(value:Array):void{
			this._keywords=value;
		}
		
		public function get fileState():String
		{
			return _fileState;
		}
		
		public function set fileState(value:String):void{
			this._fileState=value;
		}
		
		
		
		public function FileFieldBo()
		{
		}
	}
}