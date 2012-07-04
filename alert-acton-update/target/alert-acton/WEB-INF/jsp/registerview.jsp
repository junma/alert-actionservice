<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Register</title>
<SCRIPT language="javascript">
	function submitTest() {

		
		if (document.getElementById("c").value == "")
			return false;
	}
</SCRIPT>
</head>
<body>
	<center>
		<h3>Login Page</h3>
		<br />
		<form:form method="POST" commandName="register"
			onsubmit="return submitTest()">
Username:  ${loginform.username}<br/>
Email: ${loginform.email} <br />
User-ID:
<input id="c" name="newuid"/>
			<input type="submit" value="Register" />
		</form:form>
	</center>
</body>
</html>