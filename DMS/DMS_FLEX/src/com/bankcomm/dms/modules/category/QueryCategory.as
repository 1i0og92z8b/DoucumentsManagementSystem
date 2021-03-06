package com.bankcomm.dms.modules.category
{
	import DMS_Util.ConnectParameter;
	import DMS_Util.Global;
	
	import com.adobe.utils.StringUtil;
	import com.bankcomm.Connect;
	import com.bankcomm.URLParameter;
	import com.bankcomm.dms.modules.category.Vo.CategoryVo;
	import com.bankcomm.dms.bo.SessionBO;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	
	
	public class QueryCategory
	{
		
		public static var userRole:String=SessionBO.userInfo["userRole"];
		private var parentCategoryVo:CategoryVo;
		private var childrenList:ArrayCollection;//设置childrenList列表，存储categoryVo信息
		private var list:Array;
		private static var reqUrl:String;
		
		if(userRole=="companyUser"){
			reqUrl=Global.IP+"queryCategoryState.ajax";//公司用户只能查询分类状态为1的分类，即可见分类
		}else{
			reqUrl=Global.IP+"queryCategory.ajax";
		}
		

		
		
		public function QueryCategory(categoryVo:CategoryVo)
		{
			this.parentCategoryVo=categoryVo;
		}
		
		public function connect(callback:Function):void
		{
			
			var params:URLParameter = new URLParameter();
			
			
			params.reqBody={"categoryId":parentCategoryVo.categoryId}
				
			
			childrenList=new ArrayCollection();
			
			Connect.request(new ConnectParameter({url:reqUrl,
				params:params.toParameter(),//输入参数，查询CATEGORY_ID
				callback:function(r:Object,e:Event):void{//成功返回请求的参数
					var rspBody:Object=r["RSP_BODY"];
					list=rspBody["list"];
					if(list!=null){
						for each(var o:Object in list as Array){
							var categoryVo:CategoryVo=new CategoryVo(o);
							childrenList.addItem(categoryVo)//将当前categoryVo对象childrenList中
						}
					}
					//若返回列表长度大于0 则表示此当前节点包含子节点
					if(childrenList.length>0)
					{
						//将子节点添加到children属性中
						parentCategoryVo.children=childrenList.source;
					}
					else{
						parentCategoryVo.isLeaf=true;//当前节点为叶子节点，其下不存在子节点
					}
					//执行外部函数的回调函数
					callback(childrenList.source);
				},failback:function(r2:Object,e2:Event):void{
					//请求未成功的操作
				
				}
			}));
			
		}
	}
}