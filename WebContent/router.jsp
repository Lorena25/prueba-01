<%@page import=" java.util.ArrayList" %>
<%@page import=" java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Agente para configurar el router</title>
</head>
<body>
<h1>ROUTER</h1>
 <%
 //Boolean p=(boolean)session.getAttribute("pasar");
String p=(String)session.getAttribute("pasar");
%><li> AGENT LORENA dice: <%= p %></li>

<li></li>
<% 


ArrayList<String> m=(ArrayList)session.getAttribute("opciones");
//ArrayList<String> m= (String)session.getAttribute("opciones");
Iterator<String> nombreIterator = m.iterator();
while(nombreIterator.hasNext()){
	String elemento = nombreIterator.next();
	%><li> MENU ROUTER: <%= elemento %></a></li><% 
	
}

 
%>



</body>
</html>