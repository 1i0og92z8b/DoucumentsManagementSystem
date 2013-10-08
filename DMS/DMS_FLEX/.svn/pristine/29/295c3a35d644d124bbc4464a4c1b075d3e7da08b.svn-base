package DMS_Util
{
	import com.bankcomm.IDataDictObject;
	
	import mx.modules.Module;
	
	public class MyModule extends Module implements IDataDictObject
	{
		private var _params:Object;
		
		public function MyModule()
		{
			super();
		}
		
		public function loadDict(o:Object):void
		{
			var dict:String=o.dict ? o.dict : "";
			var callback:Function=o.callback ? o.callback : null;
			if (dict != "")
			{
				DataDict.init(dict.split(","), callback);
			}
		}
		
		public function loadGlobalIP(globalIP:String):void
		{
			Global.IP=globalIP;
		}
		
		public function get params():Object
		{
			return _params;
		}
		
		public function set params(value:Object):void
		{
			_params=value;
		}
	}
}