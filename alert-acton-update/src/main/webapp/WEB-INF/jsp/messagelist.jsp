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
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%
	pageContext.setAttribute("newLineChar", "\n");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String cur = request.getParameter("pageIndex");
	if (cur == null) {
		cur = "0";
	}
	int curIndex = Integer.parseInt(cur);
	int len = (Integer) pageContext.findAttribute("messageNum");
%>



<style>
html,body {
	height: 100%;
	
}

body{
    
	margin: 10px auto;
	min-width: 215px;
	min-height: 448px;
	align: center;
	}
tr.table {
	border-collapse: collapse;
}

tr.th {
	text-align: left;
}

tr.td {
	border-bottom: 1px solid #CCC;
}

tr.yellow td {
	border-top: 1px solid #FB7A31;
	border-bottom: 1px solid #FB7A31;
	background: #FFC;
}
</style>
<script type="text/javascript">
		function initTableCss() {
		var tab = document.getElementById("msgtable");
		for ( var i = 0; i < tab.rows.length; i++) {
			tab.rows[i].className = (i % 3 == 0) ? "yellow" : "";
		}
		
	}
	window.onload = initTableCss;
	function openWin(id)
	{
		myWindow=window.open('showMsg.html?msgId='+id,'msgList','width=600,height=600,scrollbars=yes');
		myWindow.focus();
	}
		
</script>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Messages</title>

</head>
<body>

	<table id=all width=100% height=100% valign="top">
	
		<tr height=5% valign="top">
			<td width=100% >
					<iframe name="unread" src="getunreadmsg.html" width=200p
						height=40px scrolling="no" frameborder=0> </iframe>
				</td>
		</tr>
		<tr height=80% valign="top">
			<td>
				<table id="msgtable" width=100% height=100% class=tr>

				<core:set var="pageStart" value="<%=curIndex%>" scope="page" />

						<core:if test="${!empty newMessageList}">

							<core:forEach items="${newMessageList}" var="messageForm">
								<core:if test="${messageForm.status==true}">

									<tr height=5% 
										onclick="openWin(${messageForm.messageId})">
										<td><i><strong>Date: </strong>${messageForm.messageDate}</i></td>
									</tr>
									<tr height=5% 
										onclick="openWin(${messageForm.messageId})">
										<td><i><strong>Pattern Name:</strong>${messageForm.patternName}</i></td>
									</tr>
									<tr height=5% 
										onclick="openWin(${messageForm.messageId})">
										<td><i><strong>Content:</strong>${messageForm.messageSubject}</i></td>
									</tr>
								</core:if>
								<core:if test="${messageForm.status==false}">
									<tr height=5% 
										onclick="openWin(${messageForm.messageId})">
										<td><strong>Date:</strong>${messageForm.messageDate}</td>
									</tr>
									<tr height=5% 
										onclick="openWin(${messageForm.messageId})">
										<td><strong>Pattern Name:</strong>${messageForm.patternName}</td>
									</tr>
									<tr height=5% 
										onclick="openWin(${messageForm.messageId})">
										<td><strong>Content: </strong>${messageForm.messageSubject}</td>
									</tr>
								</core:if>
							</core:forEach>
						</core:if>
					</table>
				</td>
		</tr>
		<tr  height=5% valign="top">
			<td>
				
					<table height=100%>
						<tr>

							<td><a <%if (curIndex > 0) {%>
								href="messagelist.html?pageIndex=<%=curIndex - 1%>" <%;
			}%>>BACK
							</a> <core:if test="${messageNum>pageStart*5+20}">
									<core:forEach var="i" begin="<%=curIndex%>"
										end="<%=curIndex + 4%>" step="1" varStatus="status">
										<a href="messagelist.html?pageIndex=${i}"> ${i+1}</a>
									</core:forEach>
								</core:if> <core:if test="${messageNum<=pageStart*5+20}">
									<core:forEach var="i" begin="<%=curIndex%>"
										end="${(messageNum-1)/5}" step="1" varStatus="status">
										<a href="messagelist.html?pageIndex=${i}"> ${i+1}</a>
									</core:forEach>
								</core:if> <a <%if (len > curIndex * 5 + 20) {%>
								href="messagelist.html?pageIndex=<%=curIndex + 1%>" <%;
			}%>>NEXT
							</a></td>
						</tr>



						</td>
		</tr>

	</table>
</body>
</html>
