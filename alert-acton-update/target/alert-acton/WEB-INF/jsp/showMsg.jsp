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
	pageContext.setAttribute("newLineChar1", "\n");
	pageContext.setAttribute("newLineChar2", "<br>");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Messages</title>

</head>
<body>
	<table>
		<tr>
			<td><i><strong>Date: </strong>${message.messageDate}</i></td>
		</tr>
		<br>
		<tr>
			<td><i><strong>Pattern: </strong>${message.patternName}</i></td>
		</tr>
		<br>
		<tr>
			<td><i><strong>Content: </strong>${fn:replace(message.messageContent, newLineChar1, newLineChar2)}</i></td>
		</tr>

	</table>
</body>
</html>
