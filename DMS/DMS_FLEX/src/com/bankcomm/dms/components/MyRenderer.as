

package com.bankcomm.dms.components
{
	import mx.core.mx_internal;
	import mx.events.FlexEvent;
	import mx.core.mx_internal;
	import mx.controls.Label;
	import mx.core.IDataRenderer;
	import mx.controls.listClasses.IListItemRenderer;
	public class MyRenderer extends mx.controls.Label implements mx.core.IDataRenderer,mx.controls.listClasses.IListItemRenderer
	{	
		override public function set data(value:Object):void
		{
			　     super.data = value;
			if(value.fileState == '0'){  
				setStyle('color',0x000000);
				setStyle('fontWeight','bold');
				setStyle('fontSize',12.5);
			}  
			if(value.fileState == '1'){  
				setStyle('color',0x00008B); 
				setStyle('fontWeight','bold');
				setStyle('fontSize',12.5);
			} 
			if(value.fileState == '2'){  
				setStyle('color',0x696969);
				setStyle('fontWeight','bold');
				setStyle('fontSize',12.5);
			} 
			if(value.fileState == '3'){  
				setStyle('color',0xDC143C); 
				setStyle('fontWeight','bold');
				setStyle('fontSize',12.5);
			}

		}
	}
}