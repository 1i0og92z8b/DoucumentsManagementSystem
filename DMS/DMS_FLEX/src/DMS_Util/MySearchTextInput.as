package DMS_Util
{
		import flash.events.Event;
		import flash.events.FocusEvent;
		import flash.events.MouseEvent;
		
		import mx.controls.Button;
		import mx.controls.TextInput;
		import mx.core.EdgeMetrics;
		import mx.core.FlexGlobals;
		import mx.core.IFlexDisplayObject;
		import mx.core.mx_internal;
		import mx.managers.IFocusManagerComponent;
		import mx.skins.RectangularBorder;
		import mx.styles.CSSStyleDeclaration;
		import mx.styles.IStyleManager2;
		import mx.styles.StyleManager;
		
		/**
		 * The <code>PromptingTextInput</code> component is a small enhancement to
		 * standard <code>TextInput</code>.  It adds the ability to specify a prompt
		 * value that displays when the text is empty, similar to how the prompt
		 * property of the <code>ComboBox</code> behaves when there is no selected value.
		 */
		[Event(name="txChange", type="flash.events.Event")]
		public class MySearchTextInput extends TextInput
		{
			//////////////////////////////////////////////////////////////
			//	DEFAULT STYLES
			//////////////////////////////////////////////////////////////
			
//			/**
//			 * @private
//			 */
//			private static var defaultStylesInitialized:Boolean = setDefaultStyles();
//			
//			/**
//			 * @private
//			 */
//			private static function setDefaultStyles ():Boolean
//			{
//				//copy over old styles if applicable
//				var oldS:CSSStyleDeclaration = FlexGlobals.topLevelApplication.styleManager.getStyleDeclaration("com.bocomm.portfolio.util.MySearchTextInput");
//				var s:CSSStyleDeclaration = new CSSStyleDeclaration();
//				
//				if (oldS)
//				{
//					//copy over old styles
//					s = oldS;
//					
//					//clear old style
//					FlexGlobals.topLevelApplication.styleManager.clearStyleDeclaration("com.bocomm.portfolio.util.MySearchTextInput", true);
//				}
//				
//				//create embedded skin vars
//				[Embed(source="/assets/closeButtonSkins.swf", symbol="CloseButton_disabledSkin")]
//				var disabledSkin:Class;
//				
//				[Embed(source="/assets/closeButtonSkins.swf", symbol="CloseButton_downSkin")]
//				var downSkin:Class;
//				
//				[Embed(source="/assets/closeButtonSkins.swf", symbol="CloseButton_overSkin")]
//				var overSkin:Class;
//				
//				[Embed(source="/assets/closeButtonSkins.swf", symbol="CloseButton_upSkin")]
//				var upSkin:Class;
//				
//				//add new default styles
//				s.setStyle("horizontalGap", 5);
//				s.setStyle("disabledSkin", disabledSkin);
//				s.setStyle("downSkin", downSkin);
//				s.setStyle("overSkin", overSkin);
//				s.setStyle("upSkin", upSkin);
//				
//				FlexGlobals.topLevelApplication.styleManager.setStyleDeclaration("com.bocomm.portfolio.util.MySearchTextInput", s, true);
//				
//				return true;
//			}

			/** Flag to indicate if the text is empty or not */
			private var _textEmpty:Boolean;
			
			/** 
			 * Flag to prevent us from re-inserting the prompt if the text is cleared
			 * while the component still has focus.
			 */
			private var _currentlyFocused:Boolean = false;
			
			protected var clearBtnVisible:Boolean=false;
			
			
			/**
			 * Constructor
			 */
			public function MySearchTextInput()
			{
				_textEmpty = true;
			
				/*限制模糊搜索时输入反斜扛，否则查询会出现数据库异常*/
				if(!restrict){
					restrict = "^\\\\";
				}
				
				/*等于0表示可以无限输入，将可输入字符数限制在50个字符内，避免模糊查询出现数据库异常*/
				if(maxChars == 0 || maxChars > 50){
					maxChars = 50;
				}
				
				addEventListener( Event.CHANGE, handleChange );
				addEventListener( FocusEvent.FOCUS_IN, handleFocusIn );
				addEventListener( FocusEvent.FOCUS_OUT, handleFocusOut );
				addEventListener(MouseEvent.ROLL_OVER, handleRollOver );
				addEventListener(MouseEvent.ROLL_OUT, handleRollOut );
			}
			
			//////////////////////////////////////////////////////////////
			//	CLEAR BUTTON
			//////////////////////////////////////////////////////////////
			
			/**
			 * @private
			 */
			protected var clearBtn:Button;
			
			//////////////////////////////////////////////////////////////
			//	SHOW CLEAR BUTTON
			//////////////////////////////////////////////////////////////
			
			/**
			 * @private
			 */
			protected var showClearBtn:Boolean = true;
			
			/**
			 * @private
			 */
			protected var showClearBtnChanged:Boolean = false;
			
			/**
			 * Flag indicating whether or not to show the clear button for the text field.
			 */
			[Bindable("showClearButtonChanged")]
			public function get showClearButton ():Boolean
			{
				return showClearBtn;
			}
			
			/**
			 * @private
			 */
			public function set showClearButton (value:Boolean):void
			{
				if (showClearBtn != value)
				{
					showClearBtn = value;
					
					showClearBtnChanged = true;
					
					invalidateProperties();
					invalidateSize();
					invalidateDisplayList();
					
					dispatchEvent(new Event("showClearButtonChanged"));
				}
			}
			
			//////////////////////////////////////////////////////////////
			//	FOCUS ON CLEAR
			//////////////////////////////////////////////////////////////
			
			/**
			 * @private
			 */
			protected var focusOnClearChanged:Boolean = false;
			
			/**
			 * @private
			 */
			protected var willFocusOnClear:Boolean = true;
			
			/**
			 * Flag indicating if the text field should gain focus after a clear event.  The default is true.
			 */
			public function get focusOnClear ():Boolean
			{
				return willFocusOnClear;
			}
			
			/**
			 * @private
			 */
			public function set focusOnClear (value:Boolean):void
			{
				if (willFocusOnClear != value)
				{
					willFocusOnClear = value;
					/*focusOnClearChanged = true;
					
					invalidateProperties();*/
				}
			}

			/**
			 * @private
			 */
			protected var enabledChanged:Boolean = false;
			
			/**
			 * @private
			 */
			override public function get enabled ():Boolean
			{
				return super.enabled;
			}
			/**
			 * @private
			 */
			override public function set enabled (value:Boolean):void
			{
				if (super.enabled != value)
				{
					super.enabled = value;
					enabledChanged = true;
					
					invalidateProperties();
				}
			}
			
			// ==============================================================
			//	prompt
			// ==============================================================
			
			/** Storage for the prompt property */
			private var _prompt:String = "";
			
			/** 
			 * The string to use as the prompt value
			 */
			public function get prompt():String
			{
				return _prompt;
			}
			
			[Bindable]
			public function set prompt( value:String ):void
			{
				_prompt = value;
				
				invalidateProperties();
				invalidateDisplayList();
			}
			
			// ==============================================================
			//	promptFormat
			// ==============================================================
			
			/** Storage for the promptFormat property */
			private var _promptFormat:String = '<font color="#999999">[prompt]</font>';
			
			/** 
			 * A format string to specify how the prompt is displayed.  This is typically
			 * an HTML string that can set the font color and style.  Use <code>[prompt]</code>
			 * within the string as a replacement token that will be replaced with the actual
			 * prompt text.
			 * 
			 * The default value is "&lt;font color="#999999"&gt;&lt;i&gt;[prompt]&lt;/i&gt;&lt;/font&gt;"
			 */
			public function get promptFormat():String
			{
				return _promptFormat;
			}
			
			public function set promptFormat( value:String ):void
			{
				_promptFormat = value;
				// Check to see if the replacement code is found in the new format string
				if ( _promptFormat.indexOf( "[prompt]" ) < 0 )
				{
					// TODO: Log error with the logging framework, or just use trace?
					//trace( "PromptingTextInput warning: prompt format does not contain [prompt] replacement code." );	
				}
				
				invalidateDisplayList();
			}
			
			// ==============================================================
			//	text
			// ==============================================================
			
			
			/**
			 * Override the behavior of text so that it doesn't take into account
			 * the prompt.  If the prompt is displaying, the text is just an empty
			 * string.
			 */
			[Bindable("textChanged")]
			[CollapseWhiteSpace]
			[NonCommittingChangeEvent("change")]
			override public function set text( value:String ):void
			{
				// changed the test to also test for null values, not just 0 length
				// if we were passed undefined or null then the zero length test would 
				// still return false. - Doug McCune
				_textEmpty = (!value) || value.length == 0;
				super.text = value;
				invalidateDisplayList();
			}
			
			override public function get text():String
			{
				// If the text has changed
				if (_textEmpty && super.text == _prompt) 
				{
					// Skip the prompt text value
					return "";
				}
				else
				{
					return super.text;
				}
			}
			
			
			
			/**
			 * We store a local copy of displayAsPassword. We need to keep this so that we can
			 * change it to false if we're showing the prompt. Then we change it back (if it was 
			 * set to true) once we're no longer showing the prompt.
			 */
			private var _displayAsPassword:Boolean = false;
			
			override public function set displayAsPassword(value:Boolean):void {
				_displayAsPassword = value;
				super.displayAsPassword = value;
			}
			override public function get displayAsPassword():Boolean {
				return _displayAsPassword;
			}
			
			// ==============================================================
			//	overriden methods
			// ==============================================================
			
			/**
			 * @private
			 */
			override protected function commitProperties ():void
			{
				super.commitProperties();
//				
//				if (textChanged)
//				{
//					textChanged = false;
//				}
				
				if (showClearBtnChanged)
				{
					clearBtn.mouseEnabled = showClearBtn;
					clearBtn.visible = showClearBtn;
					
					showClearBtnChanged = false;
				}
				
				if (enabledChanged)
				{
					clearBtn.enabled = enabled;
					
					enabledChanged = false;
				}
			}
			
			/**
			 * @private
			 */
			override protected function createChildren ():void
			{
				super.createChildren();
				
				//add new listeners so text can affect clearBtn
				if (!clearBtn)
				{
//					clearBtn = new Button();
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
					//clearBtn.focusEnabled = false;
					clearBtn.mouseFocusEnabled = false;
					
					clearBtn.setActualSize(18, 18);
					clearBtn.styleName = this;
					
					clearBtn.addEventListener(MouseEvent.CLICK, onClick_clearBtnHandler);
					
					addChild(clearBtn);
				}
				
			}

			
			/**
			 * @private
			 * 
			 * Determines if the prompt needs to be displayed.
			 */
			override protected function updateDisplayList( unscaledWidth:Number, unscaledHeight:Number ):void
			{
				// If the text is empty and a prompt value is set and the
				// component does not currently have focus, then the component
				// needs to display the prompt
				if ( _textEmpty && _prompt != "" && !_currentlyFocused )
				{
					if ( _promptFormat == "" )
					{
						super.text = _prompt;
					} 
					else
					{
						super.htmlText = _promptFormat.replace( /\[prompt\]/g, _prompt );
					}
					
					if(super.displayAsPassword) {
						//If we're showing the prompt and we have displayAsPassword set then
						//we need to set it to false while the prompt is showing.
						var oldVal:Boolean = _displayAsPassword;
						super.displayAsPassword = false;
						_displayAsPassword = oldVal;
					}
				}
				else {
					if(super.displayAsPassword != _displayAsPassword) {
						super.displayAsPassword = _displayAsPassword;
					}
				}
				
				if(!_textEmpty && enabled && showClearBtn){
					clearBtnVisible = true;
				}else{
					clearBtnVisible = false;
				}
				
				super.updateDisplayList( unscaledWidth, unscaledHeight );
				
				//gather info about the border
				var bm:EdgeMetrics;
				var b:IFlexDisplayObject = mx_internal::border;
				
				if (b)
				{
					b.setActualSize(unscaledWidth, unscaledHeight);
					bm = b is RectangularBorder ?
						RectangularBorder(b).borderMetrics : EdgeMetrics.EMPTY;
				}
				else
				{
					bm = EdgeMetrics.EMPTY;
				}
				
				//resize the textField and reposition the clear button to fit within the border if applicable
				var tw:Number;
				var hGap:Number = getStyle("horizontalGap");
				
				textField.x = bm.left;
				textField.y = bm.top;
				
				if (showClearBtn)
				{
					tw = Math.max(0, unscaledWidth - (bm.left + bm.right) - hGap - clearBtn.width);
					
					var tx:Number = bm.left + tw + hGap;
					var ty:Number = (unscaledHeight - clearBtn.height) / 2;
					
					clearBtn.move(tx, ty);
				}
					
				else
				{
					tw = Math.max(0, unscaledWidth - (bm.left + bm.right));
				}
				
				textField.width = tw; 
				textField.height = Math.max(0, unscaledHeight - (bm.top + bm.bottom + 1));
			}
			
			/**
			 * @private
			 */
			override public function styleChanged (styleProp:String):void
			{
				super.styleChanged(styleProp);
				
				var allStyles:Boolean = !styleProp || styleProp == "styleName";
				if (clearBtn && allStyles)
				{
					var upSkin:Class = getStyle("upSkin");
					var overSkin:Class = getStyle("overSkin");
					var downSkin:Class = getStyle("downSkin");
					var disabledSkin:Class = getStyle("disabledSkin");
					
					clearBtn.setStyle("upSkin", upSkin);
					clearBtn.setStyle("overSkin", overSkin);
					clearBtn.setStyle("downSkin", downSkin);
					clearBtn.setStyle("disabledSkin", disabledSkin);
				}
				
				if (clearBtn && styleProp == "upSkin" ||
					styleProp == "overSkin" ||
					styleProp == "downSkin" ||
					styleProp == "disabledSkin")
				{
					var btnSkin:Class = getStyle(styleProp);
					clearBtn.setStyle(styleProp, btnSkin);
				}
				
				invalidateDisplayList();
			}
			
			// ==============================================================
			//	event handlers
			// ==============================================================
			
			/**
			 * @private
			 */
			protected function handleChange( event:Event ):void
			{
				_textEmpty = super.text.length == 0;
				dispatchEvent(new Event('txChange'));
			}
			
			/**
			 * @private
			 * 
			 * When the component recevies focus, check to see if the prompt
			 * needs to be cleared or not.
			 */
			protected function handleFocusIn( event:FocusEvent ):void
			{
				_currentlyFocused = true;
				
				// If the text is empty, clear the prompt
				if ( _textEmpty )
				{
					super.htmlText = "";
					// KLUDGE: Have to validate now to avoid a bug where the format 
					// gets "stuck" even though the text gets cleared.
					validateNow();
				}
			}
			
			/**
			 * @private
			 * 
			 * When the component loses focus, check to see if the prompt needs
			 * to be displayed or not. 
			 */
			protected function handleFocusOut( event:FocusEvent ):void
			{
				_currentlyFocused = false;
				clearBtnVisible = false;
				clearBtn.visible = false;
				
				// If the text is empty, put the prompt back
				invalidateDisplayList();
				
			}
			
			/**
			 * @private
			 */
			protected function onClick_clearBtnHandler (evt:MouseEvent):void
			{
				text = "";
				htmlText = "";
				clearBtnVisible = false;
				clearBtn.visible = false;
				
				if (focusOnClear)
				{
					/*var nextFocus:IFocusManagerComponent = focusManager.getNextFocusManagerComponent(true);
					focusManager.setFocus(nextFocus);*/
					
					var nextFocus:IFocusManagerComponent = IFocusManagerComponent(this);
					focusManager.setFocus(nextFocus);
				}
				
				dispatchEvent(new Event('clear'));
			}
			
			
			private function handleRollOver(event:MouseEvent):void{
				if (enabled)
				{
					if (clearBtnVisible)
						clearBtn.visible=true;
					else
						clearBtn.visible=false;
				}
			}
			
			private function handleRollOut(event:MouseEvent):void{
				if (enabled && !_currentlyFocused)
				{
					clearBtn.visible=false;
				}
			}
			
			
		} // end class
}