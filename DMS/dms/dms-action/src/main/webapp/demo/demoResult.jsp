<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DEMO测试输出页面</title>
</head>
<body>
返回结果为：${replyMsg}
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <c:forEach var="list" varStatus="row" items="${list}">
	  <tr>
		<td width="30%" height="30" align="left"  style="line-height:240%" nowrap="nowrap">
			[${list.demoId}]
		</td>
		<td width="30%" nowrap="nowrap" align="center">
			[${list.demoNo}]
		</td>
		<td width="40%" nowrap="nowrap" align="center">
			[${list.demoName}]
		</td>
	  </tr>
  </c:forEach>
</table>
</body>
</html>