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
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>all the messages</title>
<style>
tr.table {
	font: 11px/24px Verdana, Arial, Helvetica, sans-serif;
	border-right: 1px solid Gray;
	border-left: 1px solid Gray;
}

tr.th {
	padding: 0 0.5em;
	text-align: left;
}

tr.td {
	border-bottom: 1px solid #CCC;
	padding: 0 0.5em;
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
		for ( var i = 3; i < tab.rows.length; i++) {
			tab.rows[i].className = (i % 2 == 1) ? "yellow" : "";
		}
	}
	window.onload = initTableCss;
</script>

</head>
<body>
	
	<table id="msgtable" bgcolor="#FFFFFF" class=tr>
		<tr height=10px bgcolor="#FBB901">
			<td ><strong><font color="#FFFFFF">Message
						Box</font> </strong>
			</td><td></td><td></td>
		</tr>
		<tr bgcolor="#F9B700"><td>Date</td><td>Pattern Name</td><td> Content</td></tr>
		<core:if test="${!empty newMessageList}">



			<core:forEach items="${newMessageList}" var="messageForm">
				<tr>
					<td>${messageForm.messageDate}</td>
				
		
					<td>${messageForm.patternName}</td>
				
					<td>${messageForm.messageContent}</td>
				</tr>
			</core:forEach>

		</core:if>
	</table>
</body>
</html>
