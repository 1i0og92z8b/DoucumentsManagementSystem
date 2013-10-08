package DMS_Util
{
	import com.adobe.serialization.json.JSON;
	import com.adobe.utils.StringUtil;
	import com.bankcomm.Connect;
	import com.bankcomm.DataDictContainer;
	import com.bankcomm.GUIPMyModuleLoader;
	import com.bankcomm.GuipPublic;
	import com.bankcomm.IModuleData;
	import com.bankcomm.MyModuleLoader;
	import com.bankcomm.MyPanel;
	import com.bankcomm.interfaces.IModuleUnloader;
	
	import components.GuipTabNavigator;
	
	import controls.tabBarClasses.GuipTab;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.external.ExternalInterface;
	import flash.net.LocalConnection;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import flash.system.ApplicationDomain;
	import flash.xml.XMLDocument;
	
	import mx.containers.Canvas;
	import mx.containers.Panel;
	import mx.containers.TabNavigator;
	import mx.containers.TitleWindow;
	import mx.controls.SWFLoader;
	import mx.core.Container;
	import mx.core.FlexGlobals;
	import mx.core.IFlexDisplayObject;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.modules.ModuleLoader;
	import mx.rpc.xml.SimpleXMLDecoder;
	import mx.utils.URLUtil;
	
	public class Public
	{
		public static function emptyFn():void
		{
		}
		
		public static function getInitParams(o:Object):Object
		{
			var initParams:Object={}
			var mark:Boolean=true;
			var params:Array=o.loaderInfo.url.toString().replace(/.*\?/, "").split("&");
			for (var i:int=0; i < params.length; i++)
			{
				var temp:Array=params[i].split("=");
				if (temp[0] == "initParams")
				{
					initParams=com.adobe.serialization.json.JSON.decode(unescape(String(temp[1])));
					mark=false;
				}
			}
			if (mark)
				return null;
			else
				return initParams;
		}
		
		public static function dateFormat(text:String):Date
		{
			if (text == "")
			{
				return new Date(new Date().setHours(0, 0, 0, 0));
			}
			var date:Date=new Date();
			var dateStr:String=text;
			dateStr=dateStr.replace(/-/g, "/");
			date=new Date(Date.parse(dateStr));
			return date
		}
		
		public static function toTimestamp(date:String):String
		{
			return (date != null && date != "") ? date.concat(" 00:00:00") : "";
		}
		
		public static function popUp(o:IFlexDisplayObject,parent:DisplayObject=null,modal:Boolean=true):void
		{
			if(!parent){
				parent=FlexGlobals.topLevelApplication as DisplayObject;
			}
			if (o is TitleWindow)
				
			{
				PopUpManager.addPopUp(o,parent,modal);
				PopUpManager.centerPopUp(o);
				o.addEventListener(CloseEvent.CLOSE, function():void
				{
					removePopUp(o);
				})
			}
			else if (o is Panel)
			{
				o.x=(FlexGlobals.topLevelApplication.width - o.width) / 2;
				o.y=(FlexGlobals.topLevelApplication.height - o.height) / 2;
				var c:Canvas=new Canvas();
				c.isPopUp=false;
				c.percentHeight=100;
				c.percentWidth=100;
				c.addChild(o as DisplayObject);
				PopUpManager.addPopUp(c, FlexGlobals.topLevelApplication as DisplayObject, true);
				o.addEventListener(CloseEvent.CLOSE, function():void
				{
					removePopUp(c);
					o=null;
				})
			}
		}
		
		public static function removePopUp(o:IFlexDisplayObject):void
		{
			PopUpManager.removePopUp(o);
			o=null;
		}
		
		
		/**
		 * 
		 * @param moduleData 加载模块参数
		 * 
		 * 包括：label：显示的标题字段；
		 * 		 icon:标题显示的图片字段；
		 *       uid：模块的唯一标志字段；
		 * 		 systemId：该模块所在系统的唯一标志；
		 *       url：待加载的模块路径；
		 * 		 params：请求的其他参数；
		 * 		 callback：模块加载的回调函数
		 * 
		 * @param container 添加module的容器
		 * 
		 * 此方法用于点击菜单加载Module并添加到容器中
		 * 
		 * 
		 */
		public static function loadModule(moduleData:IModuleData,container:Container,rootURL:String=null,useCache:Boolean=false):void{
			if(rootURL==null) rootURL="";
			if(Global.debug){
				rootURL = "";	
			}else{
				rootURL = Global.IP;
			}
			var nocache:Date=new Date();
			var systemId:String=moduleData.systemId;
			//如果全局变量没有设置保存配置文件内容的属性，则先初始化属性。
			if (!DataDictContainer.Dictionary.hasOwnProperty(Global.appName)){
				DataDictContainer.Dictionary[Global.appName] = new Object();
			}
			//如果没有加载component配置文件，则先加载components.vxml,将配置文件内容保存在数据字典对象中
			if(!DataDictContainer.Dictionary[Global.appName].hasOwnProperty(systemId+"_components")){
				var loader:URLLoader = new URLLoader(new URLRequest(rootURL+"components.vxml" + "?_nocache=" + String(nocache.getTime())));
				loader.addEventListener(Event.COMPLETE,	function(event:Event):void{
					DataDictContainer.Dictionary[Global.appName][systemId+"_components"]=String(loader.data);
					load(rootURL,moduleData,container,useCache);
				});
				loader.addEventListener(IOErrorEvent.IO_ERROR,	function(event:IOErrorEvent):void{
					load(rootURL,moduleData,container,useCache);
				});
			}else{
				load(rootURL,moduleData,container,useCache);
			}
		}
		/**
		 * 
		 * @param rootURL 系统对应的根目录，如：http://www.bankcomm.com/guip/;
		 * @param systemId 模块对应的系统标志符
		 * @param parameter 加载模块的参数
		 * @param container 添加module的容器
		 * 
		 */
		
		private static function load(rootURL:String,moduleData:IModuleData,container:Container,useCache:Boolean):void{
			var nocache:Date=new Date();
			var systemId:String = moduleData.systemId;
			var label:String=moduleData.label;
			var icon:Class=moduleData.icon;
			var type:String ;
			var uid:String=moduleData.uid;
			var url:String= rootURL + moduleData.url;
			//请求Module对应的配置文件并处理数据字典
			Connect.request(new ConnectParameter({url: url + "?_nocache=" + String(nocache.getTime()), callback: function(rs:Object, event:Event):void
			{
				var resultObj:Object=new SimpleXMLDecoder(true).decodeXML(new XMLDocument(rs.toString()));
				resultObj.module.name=label;
				resultObj.module.systemId=systemId;
				resultObj.module.id=uid;
				resultObj.module.icon=icon;
				resultObj.module.launch=type;
				resultObj.module.params=moduleData.params;
				resultObj.module.callback=moduleData.callback;
				if (resultObj.module.file)
				{
					if(!resultObj.module.dict){
						resultObj.module.dict="";
					}
					var cmpObj:Object;
					if(DataDictContainer.Dictionary[Global.appName].hasOwnProperty(systemId+"_components")){
						try{
							cmpObj = new SimpleXMLDecoder(true).decodeXML(new XMLDocument(DataDictContainer.Dictionary[Global.appName][systemId+"_components"])).components;
						}catch(err:Error){
							throw new Error("components.vxml配置文件格式不正确!");
						}
						if(cmpObj!=0 && cmpObj.global && cmpObj.global.ip && URLUtil.getProtocol(cmpObj.global.ip) != ""){
							resultObj.module.globalIP = cmpObj.global.ip;
						}
					}
					//如果配有components数据字典，则将components字典中的dict追加到原数据字典中
					if(resultObj.module.components){
						if(!DataDictContainer.Dictionary[Global.appName].hasOwnProperty(systemId+"_components")){
							throw new Error("找不到系统公共组件配置文件components.vxml！");
						}
						var cmp:Array=resultObj.module.components.split(",");
						for(var p:String in cmpObj){
							for each(var item:String in cmp){
								if(p==item && StringUtil.trim(cmpObj[p].dict)!=""){
									resultObj.module.dict+=","+cmpObj[p].dict;
								}
							}
						}
					}
					if (useCache && GuipPublic.cacheType == 2)
					{				 
						Connect.request(new ConnectParameter({url: rootURL+ "lastModified.jsp?swf=" + resultObj.module.file, callback: function(r:Object, e:Event):void
						{
							resultObj.module.file=rootURL+resultObj.module.file + "?_nocache=" + r["RSP_BODY"].last;
							addTab(resultObj.module,container);
						}}));			 
					}
					else 
					{
						resultObj.module.file=rootURL+resultObj.module.file + "?_nocache=" + String(nocache.getTime());
						addTab(resultObj.module,container);
					}
				}
			}, isJson: false}));
		}
		
		/**
		 * 
		 * @param menuObject
		 * @param container
		 * 
		 * 加载Module并添加到TabNavigator容器中
		 * 
		 */
		public static function addTab(menuObject:Object, container:Container):void{
			var uid:String = menuObject.id;
			var appId:String = menuObject.systemId;
			var type:String = menuObject.launch; 
			//如果未指定模块唯一标志符，则取模块名作为唯一标志符，用于判断该模块是否已经打开
			if(uid==null){
				var pathSplit:Array=menuObject.file.split("/");
				var swfName:String=pathSplit[pathSplit.length-1];
				uid=swfName.split(".")[0];
			}
			var loadParam:Object={};
			var param:Object=menuObject.params;
			if(param){
				param.funcId = uid;
			}else{
				param = {"funcId" : uid};
			}
			if (container is TabNavigator){
				if(!container.getChildByName(uid)){
					if (menuObject.name == "")
					{
						menuObject.name="(Untitled)";
					}
					var curNum:Number=container.numChildren + 1;
					
					if (menuObject.file && menuObject.type == "module")
					{
						var module:GUIPMyModuleLoader=new GUIPMyModuleLoader();
						module.percentHeight=100;
						module.percentWidth=100;
						module.url=menuObject.file;
						module.applicationDomain=new ApplicationDomain(ApplicationDomain.currentDomain);
						module.label = menuObject.name;
						module.title = menuObject.name;
						module.name=uid;
						module.callback=menuObject.callback;
						loadParam.params=param;
						loadParam.dict=menuObject.dict;
						loadParam.globalIP = menuObject.globalIP;
						module.params=loadParam;
						if (menuObject.icon)
						{
							module.icon=menuObject.icon;
						}
						else
						{
							//							module.icon=IconSkin.document_icon;
						}
						container.addChild(module);
						if(menuObject.showCloseButton != null  && !menuObject.showCloseButton){
							module.showCloseButton = false;
							if(container is GuipTabNavigator){
								var tab: GuipTabNavigator = container as GuipTabNavigator;
								tab.setClosePolicyForTab(tab.getChildIndex(tab.getChildByName(uid)), GuipTab.CLOSE_NEVER);
							}
						}
						if(type){
							recordLog(appId,uid,type);
						}
					}
					else if(menuObject.file)
					{
						var child:Container=menuObject.file as Container;
						child.name=uid;
						child.label=menuObject.name;
						if(menuObject.icon) {
							child.icon=menuObject.icon;
						}else{
							//							child.icon=IconSkin.document_icon;
						}
						if(menuObject.title){
							(child as Panel).title = menuObject.title;
						}
						container.addChild(child);
					}
					(container as TabNavigator).selectedIndex=container.numChildren - 1;
				}
				else
				{
					(container as TabNavigator).selectedIndex=container.getChildIndex(container.getChildByName(uid));
				}
			}else{
				if (menuObject.file && menuObject.type == "module")
				{
					var loader:MyModuleLoader=new MyModuleLoader();
					loader.percentHeight=100;
					loader.percentWidth=100;
					loader.url=menuObject.file;
					loader.applicationDomain=new ApplicationDomain(ApplicationDomain.currentDomain);
					loader.label = menuObject.name;
					loader.name=uid;
					loader.callback=menuObject.callback;
					loadParam.params=param;
					loadParam.dict=menuObject.dict;
					loadParam.globalIP = menuObject.globalIP;
					loader.params=loadParam;
					container.addChild(loader);
					if(type){
						recordLog(appId,uid,type);
					}
				}
			}
			loadParam=null;
		}
		public static function closeTab(o:Object):void{
			if (o.className == "Canvas")
			{
				((o as Canvas).getChildAt(0) as SWFLoader).source="";
				((o as Canvas).getChildAt(0) as SWFLoader).load(null);
			}
			if(o.className == "MyPanel"){
				if((o as MyPanel).numChildren>0){
					o = (o as MyPanel).getChildAt(0);
				}
			}
			if (o.className == "ModuleLoader")
			{
				if((o as ModuleLoader).child is IModuleUnloader){
					((o as ModuleLoader).child as IModuleUnloader).unload();
				}
				(o as ModuleLoader).unloadModule();
				(o as ModuleLoader).url=null;
				(o as ModuleLoader).removeAllChildren();
			}
			if (o.className == "MyModuleLoader")
			{
				if((o as MyModuleLoader).child is IModuleUnloader){
					((o as MyModuleLoader).child as IModuleUnloader).unload();
				}
				(o as MyModuleLoader).unloadModule();
				(o as MyModuleLoader).url=null;
				(o as MyModuleLoader).removeAllChildren();
			}
			if (o.className == "GUIPMyModuleLoader")
			{
				if((o as GUIPMyModuleLoader).child is IModuleUnloader){
					((o as GUIPMyModuleLoader).child as IModuleUnloader).unload();
				}
				(o as GUIPMyModuleLoader).unloadModule();
				(o as GUIPMyModuleLoader).url=null;
				(o as GUIPMyModuleLoader).removeAllChildren();
			}
			try
			{
				new LocalConnection().connect("foo");
				new LocalConnection().connect("foo");
			}
			catch (e:*)
			{
			}
		}
		
		/**
		 * 将当前操作记录到日志表中。
		 * */
		public static function recordLog(appId:String,funcId:String,type:String):void{
		}
	}
}