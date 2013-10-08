package DMS_Util
{
	import com.bankcomm.DataDictContainer;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import mx.controls.Button;
	import mx.controls.ComboBox;
	import mx.core.EdgeMetrics;
	import mx.core.IFlexDisplayObject;
	import mx.core.mx_internal;
	import mx.events.ListEvent;
	import mx.skins.RectangularBorder;

	[Event(name="clear", type="flash.events.Event")]

	public class MyComboBox extends mx.controls.ComboBox
	{
		private var _selectedValue:String;
		private var _dict:String;
		private var _cascadeCombo:MyComboBox;
		private var _valueField:String="data";
		private var showClearBtn:Boolean=true;

		public function set showClearButton(value:Boolean):void
		{
			if (showClearBtn != value)
			{
				showClearBtn=value;
				invalidateProperties();
				invalidateSize();
				invalidateDisplayList();
			}
		}

		protected var clearBtn:Button;
		protected var clearBtnVisible:Boolean=false;

		override protected function createChildren():void
		{
			super.createChildren();
			if (showClearBtn)
			{
				if (this.enabled)
				{
					if (!clearBtn)
					{
						[Embed(source="assets/closeButtonSkins.swf", symbol="CloseButton_disabledSkin")]
						var disabledSkin:Class;

						[Embed(source="assets/closeButtonSkins.swf", symbol="CloseButton_downSkin")]
						var downSkin:Class;

						[Embed(source="assets/closeButtonSkins.swf", symbol="CloseButton_overSkin")]
						var overSkin:Class;

						[Embed(source="assets/closeButtonSkins.swf", symbol="CloseButton_upSkin")]
						var upSkin:Class;
						clearBtn=new Button();
						clearBtn.setStyle("disabledSkin", disabledSkin);
						clearBtn.setStyle("downSkin", downSkin);
						clearBtn.setStyle("overSkin", overSkin);
						clearBtn.setStyle("upSkin", upSkin);
						clearBtn.mouseFocusEnabled=false;

						clearBtn.addEventListener(MouseEvent.CLICK, function(evt:MouseEvent):void
						{
							selectedIndex=-1;
							clearBtn.visible=false;
							clearBtnVisible=false;
							dispatchEvent(new ListEvent(ListEvent.CHANGE))
							dispatchEvent(new Event('clear'));
						});
						this.addEventListener(MouseEvent.ROLL_OVER, function():void
						{
							if (enabled)
							{
								if (clearBtnVisible)
									clearBtn.visible=true;
								else
									clearBtn.visible=false;
							}
						});
						this.addEventListener(MouseEvent.ROLL_OUT, function():void
						{
							if (enabled)
							{
								clearBtn.visible=false;
							}
						});
						this.addEventListener(ListEvent.CHANGE, function():void
						{
							if (enabled)
							{
								if (selectedIndex == -1)
								{
									clearBtnVisible=false;
								}
								else
								{
									clearBtnVisible=true;
								}
							}
						})
						addChild(clearBtn);
						clearBtn.visible=false;
						clearBtnVisible=false;
					}
				}
			}
			this.height=23;
		}

		public function set dict(value:String):void
		{
			_dict=value;
			this.prompt="请选择...";
			callLater(function():void
			{
				dataProvider=DataDictContainer.Dictionary[Global.appName][value];
			});
		}

		/**
		 * 重写updateDisplayList,对clearBtn进行定位。
		 * @private
		 * */
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			if (null != clearBtn)
			{
				clearBtn.setActualSize(18, 18);
				var bm:EdgeMetrics;
				var b:IFlexDisplayObject=mx_internal::border;

				if (b)
				{
					b.setActualSize(unscaledWidth, unscaledHeight);
					bm=b is RectangularBorder ? RectangularBorder(b).borderMetrics : EdgeMetrics.EMPTY;
				}
				else
				{
					bm=EdgeMetrics.EMPTY;
				}
				var tw:Number;
				var hGap:Number=getStyle("horizontalGap");
				tw=Math.max(0, unscaledWidth - (bm.left + bm.right) - hGap - clearBtn.width);
				var tx:Number=bm.left + tw + hGap - 20;
				var ty:Number=(unscaledHeight - clearBtn.height) / 2;
				clearBtn.move(tx, ty);
			}
		}

		override public function set enabled(value:Boolean):void
		{
			super.enabled=value;
			if (value)
			{
				this.prompt="请选择..."
			}
		}

		override public function invalidateDisplayList():void
		{
			super.invalidateDisplayList();
			if (textInput)
			{
				if (!this.enabled && selectedIndex == -1)
				{
					textInput.alpha=0;

				}
				else
				{
					textInput.alpha=1;
				}
			}
		}

		public function get dict():String
		{
			return _dict;
		}

		public function set cascade(cascadeCombo:MyComboBox):void
		{
			_cascadeCombo=cascadeCombo;
			var o:MyComboBox=this;
			this.addEventListener(ListEvent.CHANGE, function():void
			{
				var keys:Array=new Array();
				for each (var i:Object in DataDictContainer.Dictionary[Global.appName][_cascadeCombo.dict])
				{
					if (o.selectedIndex > -1)
						if (i.parent == o.selectedItem.data)
							keys.push(i);
				}
				_cascadeCombo.dataProvider=keys;
				_cascadeCombo.selectedIndex=-1;
				_cascadeCombo.dispatchEvent(new ListEvent(ListEvent.CHANGE))
			});
		}

		public function set valueField(value:String):void
		{
			_valueField=value;
		}

		private var bSelectedValueSet:Boolean=false;
		private var bDataProviderSet:Boolean=false;

		// Override committ, this may be called repeatedly 
		override protected function commitProperties():void
		{
			// If value set and have dataProvider 
			if (bSelectedValueSet && bDataProviderSet)
			{
				if (_selectedValue != null)
				{
					// Set flag to false so code won't be called until selectedValue is set again 
					bSelectedValueSet=false;
					// Loop through dataProvider 
					for (var i:int=0; i < this.dataProvider.length; i++)
					{
						// Get this item's data 
						var item:String=this.dataProvider[i][_valueField];

						// Check if is selectedValue 
						if (item == _selectedValue)
						{
							this.selectedIndex=i;
							this.dispatchEvent(new ListEvent(ListEvent.CHANGE));
							break;
						}
						this.selectedIndex=-1;
					}
				}
			}
			super.commitProperties();
		}

		override public function set dataProvider(o:Object):void
		{
			super.dataProvider=o;

			// This may get called before dataProvider is set, so make sure not null and has entries 
			if (o != null && o.length)
			{
				// Got it, set flag 
				bDataProviderSet=true;
			}
		}

		[Bindable("change")]
		public function get selectedValue():Object
		{
			return this.selectedItem != null ? this.selectedItem[this._valueField] : null;
		}

		public function set selectedValue(s:Object):void
		{
			if (s != null)
			{
				bSelectedValueSet=true;
				_selectedValue=s.toString();
				invalidateProperties();
			}
		}
	}
}