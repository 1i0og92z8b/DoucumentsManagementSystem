package DMS_Util
{
	import com.adobe.utils.StringUtil;

	public class ExtentedStringUtil
	{
		/**
		 * "%"和"_"转化成"\%"和"\_"
		 */
		public static function symbolTranslation(value:String):String{
			value = com.adobe.utils.StringUtil.trim(value);
			var myPattern:RegExp = new RegExp("%","g");
			value = value.replace(myPattern, "\\%"); 
			var myPattern2:RegExp = new RegExp("_","g");
			value = value.replace(myPattern2, "\\_"); 
			return value;
		}
	}
}