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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="Refresh" CONTENT="3">
<title>Insert title here</title>

<%
	int un = (Integer)pageContext.findAttribute("unReadMsgNum");
%>

<%-- <% 
int i=0;
String s = (String) session.getValue("count");
if (s!=null) { 
i = Integer.parseInt(s);
out.println("<h2>Counter is: "+i+"<h2>" );
i++;
}
session.putValue("count",new Integer(i).toString());
%> --%>

<script type="text/javascript">
function reload(){
	//parent.location.reload();
	//parent.location.href("/alert/spring/showUnreadedMsg.html");
	//alert("parent.location.reload");
	parent.location="showUnreadedMsg.html";
	}
function reload2(){
	//alert("no parent.location.reload");
	//parent.location="/alert/spring/main.html";
	
	}
</script>

</head>
<body>
<%=un%>
<% if (un != 0){ %>
<u><a onclick="reload()">New Message: ${unReadMsgNum}</a></u>
<%} %>
<% if (un == 0){ %>
<u><a onclick="reload2()">New Message: ${unReadMsgNum}</a></u>
<%} %>
</body>
</html>
