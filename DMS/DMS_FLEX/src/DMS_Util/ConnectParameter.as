package DMS_Util
{
	import com.bankcomm.IConnectParameter;
	
	import flash.net.URLVariables;
	
	import mx.utils.URLUtil;

	public class ConnectParameter implements IConnectParameter
	{
		private var _url:String;
		private var _params:URLVariables;
		private var _callback:Function;
		private var _isJson:Boolean;
		private var _failback:Function;
		private var _sessionTimeoutURL:String;
		private var _isMaskAvailable:Boolean;
		private var _allowFailAlert:Boolean;
		private var _textContent:String;

		public function ConnectParameter(o:Object)
		{
			if (o == null)
			{
				throw new Error("请输入Connect参数");
			}
			if (!o.hasOwnProperty("url"))
			{
				throw new Error("Connect参数中缺少url");
			}
			else
			{
				this.url = o.url;
			}
			this.params = o.params;
			this.callback = o.callback;
			if (!o.hasOwnProperty("isJson"))
			{
				o.isJson = true;
			}
			this.isJson = o.isJson;
			this.failback = o.failback;

			if (o.hasOwnProperty("isMaskAvailable"))
			{
				if (o.isMaskAvailable is Boolean)
				{
					this.isMaskAvailable = o.isMaskAvailable;
				}
				else
				{
					throw new Error("isMaskAvailable参数类型不正确");
				}
			}
			else
			{
				this.isMaskAvailable = false;
			}			
			if (o.hasOwnProperty("allowFailAlert"))
			{
				if (o.allowFailAlert is Boolean)
				{
					this.allowFailAlert = o.allowFailAlert;
				}
				else
				{
					throw new Error("allowFailAlert参数类型不正确");
				}
			}
			else
			{
				this.allowFailAlert = true;
			}		
			if (o.hasOwnProperty("textContent"))
			{
				this.textContent = o.textContent;
			}
			else
			{
				this.textContent = "载入中...";
			}
		}

		public function set url(value:String):void
		{
			if(Global.debug){
				
				var urlProt:String = Global.IP;
				
				if(URLUtil.getProtocol(value) == "")
				{
					if(value.indexOf(".vxml") != -1 || value.indexOf(".json") != -1 || value.indexOf(".xml") != -1)
					{
						this._url = value;
						
					}else{
						
						this._url = urlProt+value;
						
					}
					
				}else{
					
					if(value.indexOf(".vxml") != -1 || value.indexOf(".json") != -1 || value.indexOf(".xml") != -1)
					{
						this._url = value.substring(urlProt.length,value.length);
						
					}else{
						
						this._url = value;
						
					}
					
				}
				
			}else{
				
				if (URLUtil.getProtocol(value) == "")
				{
					this._url = Global.IP + value;
					
				}else{
					
					this._url = value;
				}
			}
		}

		public function get url():String
		{
			return _url;
		}

		public function set params(value:URLVariables):void
		{
			this._params = value;
		}

		public function get params():URLVariables
		{
			return _params;
		}

		public function set callback(value:Function):void
		{
			this._callback = value;
		}

		public function get callback():Function
		{
			return _callback;
		}

		public function set isJson(value:Boolean):void
		{
			this._isJson = value;
		}

		public function get isJson():Boolean
		{
			return _isJson;
		}

		public function set failback(value:Function):void
		{
			this._failback = value;
		}

		public function get failback():Function
		{
			return _failback;
		}

		public function set sessionTimeoutURL(value:String):void
		{
			if (value)
			{
				this._sessionTimeoutURL = value;
			}
			this._sessionTimeoutURL = Global.IP + Global.indexURL;
		}

		public function get sessionTimeoutURL():String
		{
			return _sessionTimeoutURL;
		}

		public function get isMaskAvailable():Boolean
		{
			return _isMaskAvailable;
		}

		public function set isMaskAvailable(value:Boolean):void
		{
			this._isMaskAvailable = value;
		}
		
		public function get allowFailAlert():Boolean
		{
			return _allowFailAlert;
		}
		
		public function set allowFailAlert(value:Boolean):void
		{
			this._allowFailAlert = value;
		}

		public function get textContent():String
		{
			return _textContent;
		}

		public function set textContent(value:String):void
		{
			this._textContent = value;
		}
	}
}