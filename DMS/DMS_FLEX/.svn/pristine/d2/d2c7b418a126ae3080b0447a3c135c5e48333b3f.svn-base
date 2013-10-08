package DMS_Util
{
	import mx.utils.StringUtil;
	import mx.validators.StringValidator;
	import mx.validators.ValidationResult;
	
	public class CNStringValidator extends StringValidator
	{
		public function CNStringValidator()
		{
			super();
		}
		
		override protected function doValidation(value:Object):Array
		{
			var results:Array = super.doValidation(value);
			
			var val:String = value ? String(value) : "";
			if (results.length > 0 || ((val.length == 0) && !required))
				return results;
			else
			{
				results = StringValidator.validateString(this, value, null).filter(function callback(item:*, index:int, array:Array):Boolean{
					return item.errorCode != "tooLong" && item.errorCode != "tooShort";
				});
				var maxLength:Number = Number(this.maxLength);
				var minLength:Number = Number(this.minLength);
				var CNLength:Number = getCNLength(val);
				if (!isNaN(maxLength) && CNLength > maxLength)
				{
					results.push(new ValidationResult(
						true, null, "tooLong",
						StringUtil.substitute(this.tooLongError, maxLength)));
				}
				if (!isNaN(minLength) && CNLength < minLength)
				{
					results.push(new ValidationResult(
						true, null, "tooShort",
						StringUtil.substitute(this.tooShortError, minLength)));
				}
				return results;
			}
		}
		
		/**检出中文字符的正则式*/
//		private static var reg:RegExp=new RegExp("[\u4e00-\u9fa5]","g");
		
		/**检出双字节字符（包括汉字）的正则式*/
		private static var reg:RegExp=/[^\x00-\x99]/g;
		
		/**默认编码*/
		private var _encode:String="UTF-8";
		
		/**中文字符占用的字符长度*/
		private var charEncodeLength:int=3;
		
		/**
		 *
		 * 获取输入字符串的长度，根据编码不同，中文字符占用的长度不同
		 *
		 * */
		private function getCNLength(val:String):Number
		{
			return val.match(reg).length*(charEncodeLength-1) + val.length;
		}
		
		/**
		 *
		 * 指定编码格式，默认为UTF-8
		 *
		 * */
		[Inspectable(defaultValue="UTF-8", enumeration="UTF-8,GBK", category="encode", type="String")]
		public function set encode(value:String):void
		{
			_encode=value;
			if (encode == "UTF-8")
			{
				charEncodeLength=3;
			}
			else
			{
				charEncodeLength=4;
			}
		}
		
		
		public function get encode():String
		{
			return _encode;
		}
	}
}