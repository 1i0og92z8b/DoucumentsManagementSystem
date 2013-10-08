package DMS_Util
{
	import com.bankcomm.DataDictContainer;	
	import mx.controls.dataGridClasses.DataGridColumn;
	
	public class MyDataGridColumn extends DataGridColumn
	{
		private var _dict:Array=[];
		private var _dictName:String="";

		public function set dict(value:String):void
		{
			_dictName = value;
			_dict = DataDictContainer.Dictionary[Global.appName][value];
		}

		public function get dict():String
		{
			return _dictName;
		}

		public function getDictLabel(value:String):String
		{
			if (_dict == null)
				return null;
			for(var i:int=0; i < _dict.length; i++)
			{
				var item:String=_dict[i].data;
				if (item == value)
				{
					return _dict[i].label;
					break;
				}
			}
			return value;
		}

		override public function itemToLabel(data:Object):String
		{
			if (typeof(data) == "object" || typeof(data) == "xml")
			{
				try
				{
					if (dataField.indexOf(".") > -1)
					{
						var arr:Array=dataField.split(".");
						for(var e:String in arr)
						{
							data=data[arr[e]];
						}
						try
						{
							return getDictLabel(data.toString());
						}
						catch(e:Error)
						{
						}
					}
					if (dataField.indexOf("[*]") > -1)
					{
						var arrs:Array=dataField.split("[*]");
						var temp:Array=data[arrs[0]];
						var datas:String="";
						for(var i:int=0; i < temp.length; i++)
						{
							datas=datas + temp[i][arrs[1]] + "\n"
						}
						try
						{
							return getDictLabel(datas.toString());
						}
						catch(e:Error)
						{
						}
					}
				}
				catch(e:Error)
				{
					data=null;
				}
			}
			return getDictLabel(super.itemToLabel(data));
		}
	}
}