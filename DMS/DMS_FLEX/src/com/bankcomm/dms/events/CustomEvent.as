package com.bankcomm.dms.events
{
	import flash.events.Event;
	
	public class CustomEvent extends Event
	{
		public static const USER_EVENT:String = "userEvent";
		
		private var _data:Object;
		
		public function get data():Object{
			return _data;
		}
		
		public function CustomEvent(type:String, data:Object, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			this._data = data;
		}
		
		override public function clone():Event
		{
			return new CustomEvent(type, _data, bubbles, cancelable);
		}
		
	}
}