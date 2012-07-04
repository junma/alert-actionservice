<!--
#-------------------------------------------------------------------------------
# Copyright 2012 FZI-HIWI
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#-------------------------------------------------------------------------------
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Alert Service!</title>
<%
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	java.util.Date currentTime = new java.util.Date();
	String str_date1 = formatter.format(currentTime);
%>
<style>
html,body {
	height: 100%;
}

body {
	margin: 10px auto;
	min-width: 1024px;
	min-height: 640px;
	font-size: 20px;
	font-family: Arial, Helvetica, sans-serif;
	bgcolor: #C0C0C0;
	align: center;
}
</style>
</head>
<body bgcolor="#AADF57">
	<core:url var="imgad2" value="logo.png" />
	<table align="center" width=100% height=100%>

		<tr height=100%>
			<td width=250px height=100% valign="top">
				<table id=user width=250px height=100%>
					<tr height=20px>
						<td valign="top"><a><img src="${imgad2}" alt="subscribed"
								style="width: 200px; height: 82px;" /> </a></td>
					</tr>
					<tr height=40px>
						<td valign="top">Hallo, <core:out
								value="${loginform.username}" /><br> <a>Your log in
								time: <%=str_date1%>.
						</a><br> 
						</td>
					</tr>
					<tr>
						<td><input type="button" value="LOGOUT"
							onclick="location.href='logout.html'" /></td>
					</tr>
					<tr height=20px>
						<td bgcolor="#CBE2EF"><strong><font color="#FFFFFF">Message
									Box</font> </strong></td>
					</tr>
					<tr height=540px>
						<td width=250px bgcolor="#FFFFDD"><iframe id="msglistfrm"
								scrolling="no" src="showmessagelist.html"
								style="width: 100%; height: 100%;" frameborder=0>
								<p>Your browser does not support iframes.</p>
							</iframe></td>
					</tr>
				</table>
			</td>


			<td width=80% valign="top" height=100%><iframe scrolling="no"
					src="listPattern.html" style="width: 100%; height: 100%;"
					frameborder=0>
					<p>Your browser does not support iframes.</p>
				</iframe></td>
		</tr>
	</table>
</body>
</html>
