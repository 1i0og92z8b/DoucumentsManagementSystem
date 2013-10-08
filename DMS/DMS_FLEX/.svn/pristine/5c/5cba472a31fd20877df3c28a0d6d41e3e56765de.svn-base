package DMS_Util
{
	import com.bankcomm.Connect;
	import flash.events.Event;
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;

	public class LazyComboBox extends MyComboBox
	{
		private var _strDataUrl:String;
		private var _strListNode:String;

		public function set dataUrl(strDataUrl:String):void
		{
			_strDataUrl=strDataUrl;
		}

		public function set listNode(strListNode:String):void
		{
			_strListNode=strListNode;
		}

		override protected function downArrowButton_buttonDownHandler(event:FlexEvent):void
		{
			if ((dataProvider as ArrayCollection).length == 0)
			{
				Connect.request(new ConnectParameter({url: _strDataUrl, params: null, callback: function(r:Object, e:Event):void
						{
							var arr:Array=(r[_strListNode] as Array);
							dataProvider=new ArrayCollection(arr);
							downArrowButton_buttonDownHandler(event);
						}}));
			}
			else
				super.downArrowButton_buttonDownHandler(event);
		}
	}
}