package DMS_Util
{
	import flash.errors.*;
	import flash.events.*;
	import flash.external.*;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.net.navigateToURL;	
	import mx.controls.DataGrid;
	
	public class ExcelExport
	{
        /**
         * Convert the datagrid to a html table
         * Styling etc. can be done externally
         * 
         * @param: dg Datagrid Contains the datagrid that needs to be converted
         * @returns: String
         */
          
        private function convertDGToHTMLTable(dg:DataGrid):String {
        	//Set default values
        	var font:String = dg.getStyle('fontFamily');
        	var size:String = dg.getStyle('fontSize');
        	var str:String = '';
        	var colors:String = '';
        	var style:String = 'style="font-family:'+font+';font-size:'+size+'pt;border:.5pt solid windowtext"';				
		
        	
        	//Set the htmltabel based upon knowlegde from the datagrid
        	str+= '<table width="'+dg.width+'"><thead><tr width="'+dg.width + '">';
        	
        	//Set the tableheader data (retrieves information from the datagrid header				
        	for(var i:int = 0;i<dg.columns.length;i++) {
        		colors = dg.getStyle("themeColor");
        			
        		if(dg.columns[i].headerText != undefined) {
        			str+="<th "+style+">"+dg.columns[i].headerText+"</th>";
        		} else {
        			str+= "<th "+style+">"+dg.columns[i].dataField+"</th>";
        		}
        	}
        	str += "</tr></thead><tbody>";
        	colors = dg.getStyle("alternatingRowColors");
        	
        	//Loop through the records in the dataprovider and 
        	//insert the column information into the table
        	for(var j:int =0;j<dg.dataProvider.length;j++) {					
        		str+="<tr width=\""+Math.ceil(dg.width)+"\">";
        			
        		for(var k:int=0; k < dg.columns.length; k++) {
        			
        			//Do we still have a valid item?						
        			if(dg.dataProvider.getItemAt(j) != undefined && dg.dataProvider.getItemAt(j) != null) {
        				
        				//Check to see if the user specified a labelfunction which we must
        				//use instead of the dataField
        				if(dg.columns[k].labelFunction != undefined) {
        					str += "<td style='border:.5pt solid windowtext' width=\""+Math.ceil(dg.columns[k].width)+"\" "+style+">"+dg.columns[k].labelFunction(dg.dataProvider.getItemAt(j),dg.columns[k].dataField)+"</td>";					
        				} 
        				else if(dg.columns[k].hasOwnProperty("dict")&&dg.columns[k].dict != "") {
        					str += "<td style='border:.5pt solid windowtext' width=\""+Math.ceil(dg.columns[k].width)+"\" "+style+">"+dg.columns[k].getDictLabel(dg.dataProvider.getItemAt(j)[dg.columns[k].dataField])+"</td>";  					
        				} 
        				else {
        					//Our dataprovider contains the real data
        					//We need the column information (dataField)
        					//to specify which key to use.
        					var text:String = dg.dataProvider.getItemAt(j)[dg.columns[k].dataField]== null ?'':dg.dataProvider.getItemAt(j)[dg.columns[k].dataField]
        					str += "<td style='border:.5pt solid windowtext' width=\""+Math.ceil(dg.columns[k].width)+"\" "+style+">"+text+"</td>";
        				}
        			}
        		}
        		str += "</tr>";
        	}
        	str+="</tbody></table>";
        
        	return str;
        }
        
		/**
		 * Load a specific datagrid into Excel
		 * This method passes the htmltable string to an backend script which then
		 * offers the excel download to the user.
		 * The reason for not using a copy to clipboard and then javascript to
		 * insert it into Excel is that this mostly will fail because of the user
		 * setup (Webbrowser configuration).
		 * 
		 * @params: dg Datagrid The Datagrid that will be loaded into Excel
		 */
		public static function toExcel(dg:DataGrid,fileName:String="excel"):void {

			//Pass the htmltable in a variable so that it can be delivered
			//to the backend script
			var variables:URLVariables = new URLVariables(); 
		    var xlscvt:ExcelExport = new ExcelExport();
			variables.htmltable	= xlscvt.convertDGToHTMLTable(dg);
			variables.fileName = fileName;
			//Setup a new request and make sure that we are 
			//sending the data through a post
			 
			var nocache:Date=new Date(); 
			var u:URLRequest = new URLRequest(Global.IP+"toExcel.jsp"+ "?_nocache=" + String(nocache.getTime()));
			u.data = variables; //Pass the variables
			u.method = URLRequestMethod.POST; //Don't forget that we need to send as POST
			 
			//Navigate to the script
			//We can use _self here, since the script will through a filedownload header
			//which results in offering a download to the user (and still remaining in you Flex app.)
            navigateToURL(u,"_self");
        }       
	}
}