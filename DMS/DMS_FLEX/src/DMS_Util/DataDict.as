package DMS_Util
{
	import com.bankcomm.Connect;
	import com.bankcomm.DataDictContainer;
	import com.bankcomm.URLParameter;
	import flash.events.Event;

	/**
	 *
	 * 数据字典实现<br />
	 * <i>暂时采用JUMP的数据字典测试案例设计。</i>
	 *
	 * */

	public class DataDict
	{
		/**
		 *
		 * 数据字典Map
		 *
		 * */

		/**
		 *
		 * 通过数据字典名称、值来获取对应的名称
		 * @param dict 数据字典名称
		 * @param id 值
		 * @param data 值所在的域
		 * @param label 名称所在的域
		 * @return String 返回值
		 *
		 * */
		public static function getDictName(dict:String, id:String, data:String="data", label:String="label"):String
		{
			if (id != null)
			{
				var dictArr:Array=dict.split(",");
				for each (var m:String in dictArr)
				{
					if (DataDictContainer.Dictionary[Global.appName].hasOwnProperty(m))
					{
						for each (var n:Object in DataDictContainer.Dictionary[Global.appName][m])
						{
							if (n[data].toString() == id)
								return n[label];
						}
					}
				}
			}
			return id;
		}

		/**
		 *
		 * 数据字典初始化。数据字典请求参数格式、请求URL、返回值处理均采用<i>JUMP测试案例</i>。
		 * @param names 数据字典名称，采用数组的形式传入。
		 * @param callback 默认为值null，回调函数，可以在数据字典初始化完成后调用，调用时不会传递参数。
		 *
		 * */
		public static function init(names:Array, callback:Function=null):void
		{
			var dictName:Array=[];
			if (!DataDictContainer.Dictionary.hasOwnProperty(Global.appName))
			{
				DataDictContainer.Dictionary[Global.appName]=new Object();
			}
			for each (var m:String in names)
			{
				if (!DataDictContainer.Dictionary[Global.appName].hasOwnProperty(m) && dictName.indexOf(m) == -1)
				{
					dictName.push(m);
				}
			}
			if (dictName.length > 0)
			{
				//参数处理
				var respBody:Object=new Object();
				respBody={DICTLIST: dictName};
				var params:URLParameter=new URLParameter();
				params.reqBody=respBody;
				//后台交互
				Connect.request(new ConnectParameter({url: "getDictList.json", params: params.toParameter(), callback: function(r:Object, e:Event):void
						{
							var rspBody:Object=r["RSP_BODY"];
							for each (var obj:Object in dictName)
							{
								DataDictContainer.Dictionary[Global.appName][obj.toString()]=rspBody[obj.toString()];
							}
							if (callback != null)
							{
								callback();
							}
						}}));
			}
			else
			{
				if (callback != null)
				{
					callback();
				}
			}
		}

		/**
		 *
		 * 增加一个数据字典
		 * @param o 数据字典的具体内容，一般为数组形式
		 * @param dictName 数据字典名称
		 *
		 * */
		public static function addDict(o:Object, dictName:String):void
		{
			DataDictContainer.Dictionary[Global.appName][dictName]=o;
		}
		
		/**
		 * 某个字典是否已经拿到前台
		 * @return
		 */
		public static function isDictAvailable(dictCode:String):Boolean
		{
			return DataDictContainer.Dictionary[Global.appName].hasOwnProperty(dictCode);
		}
	}
}