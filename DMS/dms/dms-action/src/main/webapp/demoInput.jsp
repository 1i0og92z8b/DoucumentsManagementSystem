<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript" type="text/javascript">
	function receiveMsg()
	{
		var params = "receiveMsg.do?msg=jevons9999";
		window.location = params;
	}
	function insertData()
	{
		var params = "insertData.do?demono=abcde&demoname=woshiyigebin";
		window.location = params;
	}
	function updateData()
	{
		var params = "updateData.do?demoid=1234&demono=abcde&demoname=woshiyigebin";
		window.location = params;
	}
	function deleteData()
	{
		var params = "deleteData.do?demoid=1234";
		window.location = params;
	}
	function selectData()
	{
		var params = "selectData.do?demono=abcde";
		window.location = params;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DEMO测试输入页面</title>
</head>
<body>
DEMO测试输入页面
<br/>
<input type="button" id="receiveMsg" value="消息" onclick="receiveMsg()"/>
<input type="button" id="insertData" value="新增" onclick="insertData()"/>
<input type="button" id="updateData" value="修改" onclick="updateData()"/>
<input type="button" id="deleteData" value="删除" onclick="deleteData()"/>
<input type="button" id="selectData" value="查询" onclick="selectData()"/>
</body>
</html>
