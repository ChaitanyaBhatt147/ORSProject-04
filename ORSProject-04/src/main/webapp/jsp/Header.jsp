<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<!-- Include jQuery -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<!-- Include jQuery UI -->
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<!-- Include jQuery UI CSS -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="/ORSProject-04/js/checkbox.js"></script>
<script src="/ORSProject-04/js/datepicker.js"></script>
</head>
<body>
	<%
		UserBean user = (UserBean) session.getAttribute("user");
	%>
	<%
		if (user != null) {
	%>
	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>
	
	<a href="#"><b>Add User</b></a>
	<b>|</b>
	<a href="#"><b>User List</b></a>
	<b>|</b>
	<a href="#"><b>Add Role</b></a>
	<b>|</b>
	<a href="#"><b>Role List</b></a>
	<b>|</b>
	<a href="LoginCtl?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>Logout</b></a>
	<%
		} else {
	%>
	<h3>Hi, Guest</h3>
	<a href="WelcomeCtl"><b>Welcome</b></a> |
	<a href="LoginCtl"><b>Login</b></a>
	<%
		}
	%>
	<hr>
</body>
</html>
