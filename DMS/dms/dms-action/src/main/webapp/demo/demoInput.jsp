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
	function startProcess()
	{
		var params = "startProcess.do";
		window.location = params;
	}
	function submitApplication()
	{
		var params = "submitApplication.do";
		window.location = params;
	}
	function queryTodoTask()
	{
		var params = "queryTodoTask.do";
		window.location = params;
	}
	function queryDoneTask()
	{
		var params = "queryDoneTask.do";
		window.location = params;
	}
	function receive()
	{
		var params = "receive.do";
		window.location = params;
	}
	function cancelReceive()
	{
		var params = "cancelReceive.do";
		window.location = params;
	}
	function approve()
	{
		var params = "approve.do";
		window.location = params;
	}
	function confirm()
	{
		var params = "confirm.do";
		window.location = params;
	}
	function reject()
	{
		var params = "reject.do";
		window.location = params;
	}
	function tempSave()
	{
		var params = "tempSave.do";
		window.location = params;
	}
	function cancelApplication()
	{
		var params = "cancelApplication.do";
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
<input type="button" id="startProcess" value="流程发起" onclick="startProcess()"/>
<input type="button" id="submitApplication" value="提交下一节点" onclick="submitApplication()"/>
<input type="button" id="queryTodoTask" value="待办任务" onclick="queryTodoTask()"/>
<input type="button" id="queryDoneTask" value="已办任务" onclick="queryDoneTask()"/>
<input type="button" id="receive" value="领取" onclick="receive()"/>
<input type="button" id="cancelReceive" value="取消领取" onclick="cancelReceive()"/>
<input type="button" id="approve" value="审批同意" onclick="approve()"/>
<input type="button" id="confirm" value="确认结束流程" onclick="confirm()"/>
<input type="button" id="reject" value="审批不同意（退回）" onclick="reject()"/>
<input type="button" id="tempSave" value="暂存" onclick="tempSave()"/>
<input type="button" id="cancelApplication" value="撤销流程" onclick="cancelApplication()"/>
</body>
</html>
