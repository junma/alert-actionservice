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
tr.table {
	font: 11px/24px Verdana, Arial, Helvetica, sans-serif;
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

body {
	margin: 10px auto;
	width: 220px;
	height: 540px;
	font-size: 20px;
	font-family: Arial, Helvetica, sans-serif;
	bgcolor: #C0C0C0;
	align: center;
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
		myWindow=window.open('showMsg.html?msgId='+id,'msgList','width=600,height=1000');
		myWindow.focus();
	}
		
</script>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Messages</title>

</head>
<body>
	<table id=all style="TABLE-LAYOUT: fixed;" width=100% height=100%>
		<tr>
			<td><div style="position: absolute; left: 10px; top: 2px;">
					<iframe name="unread" src="getunreadmsg.html" width=200p
						height=40px scrolling="no" frameborder=0> </iframe>
				</div></td>
		</tr>
		<tr>
			<td>
				<DIV
					style="position: absolute; left: 10px; top: 30px; padding: 4px; width: 250px; height: 500px; overflow: buttom;">

					<table id="msgtable" bgcolor="#FFFFFF" class=tr>

						<core:set var="pageStart" value="<%=curIndex%>" scope="page" />

						<core:if test="${!empty newMessageList}">

							<core:forEach items="${newMessageList}" var="messageForm">
								<core:if test="${messageForm.status==true}">

									<tr
										onclick="openWin(${messageForm.messageId})">
										<td><i>${messageForm.messageDate}</i></td>
									</tr>
									<tr
										onclick="openWin(${messageForm.messageId})">
										<td><i>${messageForm.patternName}</i></td>
									</tr>
									<tr
										onclick="openWin(${messageForm.messageId})">
										<td><i><strong>Content: </strong>${messageForm.messageSubject}</i></td>
									</tr>
								</core:if>
								<core:if test="${messageForm.status==false}">
									<tr
										onclick="openWin(${messageForm.messageId})">
										<td>${messageForm.messageDate}</td>
									</tr>
									<tr
										onclick="openWin(${messageForm.messageId})">
										<td>${messageForm.patternName}</td>
									</tr>
									<tr
										onclick="openWin(${messageForm.messageId})">
										<td><strong>Content: </strong>${messageForm.messageSubject}</td>
									</tr>
								</core:if>
							</core:forEach>
						</core:if>
					</table>
				</DIV>
			</td>
		</tr>
		<tr>
			<td>
				<div style="position: absolute; left: 10px; bottom: 2px;">
					<table height=10px>
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



						<%-- <tr>
			<td > ${messageNum}</td>
		</tr>  --%>
					</table>
				</div>
			</td>
		</tr>

	</table>
</body>
</html>
