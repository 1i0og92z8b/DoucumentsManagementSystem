package skins
{
	import flash.display.DisplayObject;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.text.StyleSheet;
	
	import mx.core.FlexGlobals;
	import mx.core.mx_internal;
	import mx.effects.Blur;
	import mx.graphics.BitmapFill;
	import mx.skins.ProgrammaticSkin;
	import mx.skins.halo.CheckBoxIcon;
	
	public class RepeatBackground extends ProgrammaticSkin {  
		public function RepeatBackground() {
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void {  
			super.updateDisplayList(w,h);  
			
			graphics.clear();
			var b:BitmapFill = new BitmapFill();  
			b.source = getStyle("backgroundImage");
			b.begin(graphics,new Rectangle(0,0,w,h),new Point(0,0));  
			graphics.drawRect(0,0,w,h); 
			
			b.end(graphics);
		
		}  
	}
}