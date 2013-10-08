<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<% String fileName = request.getParameter("fileName")+".xls";
	if ( fileName == null )
		fileName = (String)request.getAttribute("fileName")+".xls";
response.setHeader("Content-Disposition", "attachment; filename="+fileName); %>
<%@page language="java" import="java.util.*" %>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<%
	String htmltable = request.getParameter("htmltable");
	if ( htmltable == null )
		htmltable = (String)request.getAttribute("htmltable");

	out.println(htmltable);
%>