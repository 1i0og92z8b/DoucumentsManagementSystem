<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
  String path = request.getRealPath("/");
  String debugMode =  request.getParameter("debugMode");
  File   fpath   =   new File(path+"/icms/index.swf");   
  long   timeStamp   =   fpath.lastModified();  
  SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddmmss");   
  String   ts   =   formatter.format(new   Date(timeStamp))   ;  
  //boolean timeout = (session != null && session.getAttribute("login") != null) ? false : true;
  String userName =  request.getParameter("UID");
%>
<!-- saved from url=(0014)about:internet -->
<html lang="en">

<head><TITLE>交通银行押品管理系统</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style type="text/css" media="screen"> 
	html, body	{ height:100%; }
	body { margin:0; padding:0; overflow:hidden; text-align:center; }   
	#flashContent { display:none; }
</style>
 
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--script src="history/history.js" language="javascript"></script--> 
<script src="javascript/swfobject.js" language="javascript"></script>

<script language="JavaScript" type="text/javascript">

	var ua = navigator.userAgent.toLowerCase();
	var isStrict = document.compatMode == "CSS1Compat",
	isOpera = ua.indexOf("opera") > -1,
	isChrome = ua.indexOf("chrome") > -1,
	isSafari = !isChrome && (/webkit|khtml/).test(ua),
	isSafari3 = isSafari && ua.indexOf('webkit/5') != -1,
	isIE = !isOpera && ua.indexOf("msie") > -1,
	isIE7 = !isOpera && ua.indexOf("msie 7") > -1,
	isIE8 = !isOpera && ua.indexOf("msie 8") > -1,
	isGecko = !isSafari && !isChrome && ua.indexOf("gecko") > -1,
	isGecko3 = isGecko && ua.indexOf("rv:1.9") > -1,
	isBorderBox = isIE && !isStrict,
	isWindows = (ua.indexOf("windows") != -1 || ua.indexOf("win32") != -1),
	isMac = (ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") != -1),
	isAir = (ua.indexOf("adobeair") != -1),
	isLinux = (ua.indexOf("linux") != -1),
	isSecure = window.location.href.toLowerCase().indexOf("https") === 0;
		 
	if(swfobject.getFlashPlayerVersion().major<10&&isIE)
	{
		var alternateContent = "系统检测到您的浏览器Flash插件版本不正确，可能导致系统某些功能无法正常使用。<br>请下载安装最新版本的插件： <a href=plugins/flash_4ie.exe>下载</a>";
		document.write(alternateContent);    
	}
	else if(!isIE&&swfobject.getFlashPlayerVersion().major<10)
	{
		var alternateContent = "系统检测到您的浏览器Flash插件版本不正确，可能导致系统某些功能无法正常使用。<br>请下载安装最新版本的插件： <a href=plugins/flash_4ff.exe>下载</a>";
	    document.write(alternateContent);
	}
	else 
	{
		var swfVersionStr = "10.0.0";
	    var xiSwfUrlStr = "playerProductInstall.swf";
	    var flashvars = {userName:"<%=userName%>",debugMode:"<%=debugMode%>"};
	    var params = {};
	    params.quality = "high";
	    params.bgcolor = "#ffffff";
	    params.allowscriptaccess = "sameDomain";
	    var attributes = {};
	    attributes.id = "index";
	    attributes.name = "index";
	    attributes.align = "middle";
	    swfobject.embedSWF(
	        "index.swf?date=<%=ts%>", "flashContent", 
	        "100%", "100%", 
	        swfVersionStr, xiSwfUrlStr, 
	        flashvars, params, attributes);
		swfobject.createCSS("#flashContent", "display:block;text-align:left;");
	}
</script>
</head>
<body>
	<div id="flashContent"></div>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="plugins/swflash.cab#version=10,0,32,18"
			id="index" width="100%" height="100%">
			<param name="movie" value="index.swf?date=<%=ts%>" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="flashVars" value="userName=<%=userName%>&debugMode=<%=debugMode%>" />
			<param name="allowScriptAccess" value="sameDomain" />
	</object>
</noscript>
<OBJECT ID="ICMSBARCODE1" WIDTH=0 HEIGHT=0 CODEBASE="plugins/ICMSBARCODE.cab#version=1,0,0,4" CLASSID="CLSID:FF7015FA-2474-4179-988D-1FE476DF55F8">
    <PARAM NAME="_Version" VALUE="65536">
    <PARAM NAME="_ExtentX" VALUE="2646">
    <PARAM NAME="_ExtentY" VALUE="1323">
    <PARAM NAME="_StockProps" VALUE="0">
</OBJECT>
</body>
</html>