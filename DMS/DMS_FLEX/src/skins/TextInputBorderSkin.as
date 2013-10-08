////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2004-2007 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package skins
{
	import flash.display.GradientType;
	import flash.display.Graphics;
	import mx.skins.Border;
	import mx.skins.halo.HaloColors;
	import mx.utils.ColorUtil;

	/**
	 *  The skin for all the states of the button in a ComboBox.
	 *
	 *  @langversion 3.0
	 *  @playerversion Flash 9
	 *  @playerversion AIR 1.1
	 *  @productversion Flex 3
	 */
	public class TextInputBorderSkin extends Border
	{

		//--------------------------------------------------------------------------
		//
		//  Class variables
		//
		//--------------------------------------------------------------------------

		/**
		 *  @private
		 */
		private static var cache:Object={};

		//--------------------------------------------------------------------------
		//
		//  Class methods
		//
		//--------------------------------------------------------------------------

		/**
		 *  @private
		 *  Several colors used for drawing are calculated from the base colors
		 *  of the component (themeColor, borderColor and fillColors).
		 *  Since these calculations can be a bit expensive,
		 *  we calculate once per color set and cache the results.
		 */
		private static function calcDerivedStyles(themeColor:uint, borderColor:uint, fillColor0:uint, fillColor1:uint):Object
		{
			var key:String=mx.skins.halo.HaloColors.getCacheKey(themeColor, borderColor, fillColor0, fillColor1);

			if (!cache[key])
			{
				var o:Object=cache[key]={};

				// Cross-component styles.
				mx.skins.halo.HaloColors.addHaloColors(o, themeColor, fillColor0, fillColor1);
			}

			return cache[key];
		}

		//--------------------------------------------------------------------------
		//
		//  Constructor
		//
		//--------------------------------------------------------------------------

		/**
		 *  Constructor.
		 *
		 *  @langversion 3.0
		 *  @playerversion Flash 9
		 *  @playerversion AIR 1.1
		 *  @productversion Flex 3
		 */
		public function TextInputBorderSkin()
		{
			super();
		}

		//--------------------------------------------------------------------------
		//
		//  Overridden properties
		//
		//--------------------------------------------------------------------------

		//----------------------------------
		//  measuredWidth
		//----------------------------------

		/**
		 *  @private
		 */
		override public function get measuredWidth():Number
		{
			return 22;
		}

		//----------------------------------
		//  measuredHeight
		//----------------------------------

		/**
		 *  @private
		 */
		override public function get measuredHeight():Number
		{
			return 22;
		}

		//--------------------------------------------------------------------------
		//
		//  Overridden methods
		//
		//--------------------------------------------------------------------------

		/**
		 *  @private
		 */
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);

			var borderColor:uint=getStyle("borderColor");
			var cornerRadius:Number=getStyle("cornerRadius");
			var dropdownBorderColor:Number=getStyle("dropdownBorderColor");
			var fillAlphas:Array=getStyle("fillAlphas");
			var fillColors:Array=getStyle("fillColors");

			styleManager.getColorNames(fillColors);
			var highlightAlphas:Array=getStyle("highlightAlphas");

			// The dropdownBorderColor is currently only used
			// when displaying an error state.

			if (borderColor == 0)
			{
				borderColor=0x000000;
			}

			if (!isNaN(dropdownBorderColor))
				borderColor=dropdownBorderColor;

			var borderColorDrk1:uint=ColorUtil.adjustBrightness2(borderColor, -10);


			var cornerRadius1:Number=Math.max(cornerRadius - 1, 0);
			var cr:Object={tl: 0, tr: cornerRadius, bl: 0, br: cornerRadius};
			var cr1:Object={tl: 0, tr: cornerRadius1, bl: 0, br: cornerRadius1};

			var arrowOnly:Boolean=true;

			// If our name doesn't include "editable", we are drawing the non-edit
			// skin which spans the entire control
			if (name.indexOf("editable") < 0)
			{
				arrowOnly=false;
				cr.tl=cr.bl=cornerRadius;
				cr1.tl=cr1.bl=cornerRadius1;
			}

			var g:Graphics=graphics;

			g.clear();


			var overFillColors:Array;
			if (fillColors.length > 2)
				overFillColors=[fillColors[2], fillColors[3]];
			else
				overFillColors=[fillColors[0], fillColors[1]];

			var overFillAlphas:Array;
			if (fillAlphas.length > 2)
				overFillAlphas=[fillAlphas[2], fillAlphas[3]];
			else
				overFillAlphas=[fillAlphas[0], fillAlphas[1]];

			// border
			drawRoundRect(0, 0, w, h, cr, [borderColor, borderColorDrk1], 1, verticalGradientMatrix(0, 0, w, h), GradientType.LINEAR, null, {x: 1, y: 1, w: w - 2, h: h - 2, r: cr1});

			// button fill
			drawRoundRect(1, 1, w - 2, h - 2, cr1, overFillColors, overFillAlphas, verticalGradientMatrix(1, 1, w - 2, h - 2));

			// top highlight
			drawRoundRect(1, 1, w - 2, (h - 2) / 2, {tl: cornerRadius1, tr: cornerRadius1, bl: 0, br: 0}, [0xFFFFFF, 0xFFFFFF], highlightAlphas, verticalGradientMatrix(0, 0, w - 2, (h - 2) / 2));
		}
	}
}