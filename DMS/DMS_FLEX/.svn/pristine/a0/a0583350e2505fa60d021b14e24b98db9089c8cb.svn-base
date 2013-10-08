<%@ page import="java.io.*"%><%@ page import="java.util.*"%><%@ page import="java.text.*"%><%
  String path = request.getRealPath("/");
  String name = request.getParameter("swf");  
  File   fpath   =   new File(path+"/"+name);   
  long   timeStamp   =   fpath.lastModified();  
  SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddmmss");   
  String   tsForm   =   formatter.format(new   Date(timeStamp))   ;   
%>{"RSP_HEAD":{"TRAN_SUCCESS":"1"},"RSP_BODY":{"last":"<%=tsForm%>","echo":"success"}}