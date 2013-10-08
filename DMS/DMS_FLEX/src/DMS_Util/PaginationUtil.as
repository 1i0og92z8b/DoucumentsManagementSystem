package DMS_Util
{
	import mx.controls.AdvancedDataGrid;
	import mx.controls.DataGrid;

	public class PaginationUtil
	{
		/**
		 * 计算每页显示数据条数
		 * */
		public static function gridPageInit(grid:Object,gridPage:Object):void{
			if(grid is DataGrid || grid is AdvancedDataGrid)
			{
				var pagesize:int = 10;
				pagesize = (grid.height-grid.headerHeight)/grid.rowHeight-1;
				if(gridPage is GridPage||gridPage is SimpleGridPage)
				{
					gridPage.pagesize = pagesize>10?pagesize:10;
					if(gridPage is GridPage)
					{
						(gridPage as GridPage).ps.value = gridPage.pagesize;
						(gridPage as GridPage).loadData();
					}
					else if(gridPage is SimpleGridPage){
						(gridPage as SimpleGridPage).loadData();
					}
				}
			}
		}
		
		/**
		 * 计算每页显示数据条数-不进行查询
		 * */
		public static function gridPageInitWithoutQuery(grid:Object,gridPage:Object):void{
			if(grid is DataGrid)
			{
				var pagesize:int = 10;
				pagesize = (grid.height-grid.headerHeight)/grid.rowHeight-1;
				if(gridPage is GridPage||gridPage is SimpleGridPage)
				{
					gridPage.pagesize = pagesize>10?pagesize:10;
					if(gridPage is GridPage)
					{
						(gridPage as GridPage).ps.value = gridPage.pagesize;
					}
				}
			}
		}
	}
}
