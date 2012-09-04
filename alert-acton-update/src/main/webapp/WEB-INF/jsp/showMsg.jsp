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
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="javax.xml.parsers.*"%>
<%@ page import="org.w3c.dom.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>


<%
	pageContext.setAttribute("newLineChar1", "\n");
	pageContext.setAttribute("newLineChar2", "<br>");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
body {
	weight: 600px;
	height: 100%;
	overflow: scroll;
}

.message-box {
	border: 1px solid;
	margin: 15px 0px;
	padding: 20px 20px 20px 20px;
	width: 500px;
	font: 14px;
	-moz-box-shadow: 0 0 5px #888;
	-webkit-box-shadow: 0 0 5px #888;
	box-shadow: 0 0 5px #888;
	text-shadow: 2px 2px 2px #ccc;
	-webkit-border-radius: 15px;
	-moz-border-radius: 15px;
	border-radius: 15px;
}

.blue {
	text-align: left;
	color: #00529B;
	background-color: #BDE5F8;
}

.green {
	text-align: left;
	color: #4F8A10;
	background-color: #DFF2BF;
}

.yellow {
	text-align: left;
	color: #9F6000;
	background-color: #FEEFB3;
}

.pink {
	text-align: left;
	color: black;
	background-color: pink;
}

.title {
	padding: 20px 20px 40px 20px;
	text-align: center;
	font: bolder 32px blue;
	font-stretch: extra-expanded;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Messages</title>

</head>
<body>


	<div class="message-box">
		<p>
			<strong>Message Date:</strong> ${message.messageDate}<br> <strong>Pattern
				Name:</strong>${message.patternName}<br> <strong>Subject:</strong>${message.messageSubject}<br>
			<strong>Summary:</strong>${message.messageSummary}<br>
		</p>
	</div>
	<core:choose>
		<core:when
			test="${fn:contains(message.messageContent,'<similarity>')}">
			<div class="title">
				<strong>Similar Issue</strong>
			</div>
			<x:parse xml="${message.messageContent}" var="xmldoc" />
			<x:forEach var="output" select="$xmldoc/items/item">
				<div class="message-box blue">
					<p>
						<strong>Subject: </strong>
						<x:out select="$output/subject" />
						<br> <strong>Summary: </strong>
						<x:set var="sum" select="string($output/shortContent)" />
						${fn:replace(sum, newLineChar1, newLineChar2)}<br> <strong>Similarity:
						</strong>
						<x:out select="$output/similarity" />
						<br> <strong>Link: </strong><a
							href="<x:out select="$output/url" /><x:out select="$output/url" />"><x:out
								select="$output/url" /> <x:out select="$output/url" /></a><br>
					</p>
				</div>
			</x:forEach>
		</core:when>


		<core:when test="${fn:contains(message.messageContent,'<identitys>')}">
			<div class="title">
				<strong>Identity Recommendation</strong>
			</div>
			<x:parse xml="${message.messageContent}" var="xmldoc" />
			<x:forEach var="output" select="$xmldoc/identitys/identity">
				<div class="message-box green">
					<p>
						<strong>Name: </strong>
						<x:out select="$output/name" />
						<br> <strong>Profile: </strong>
						<x:out select="$output/profile" />
						<br>
						 <strong>Link: </strong>
						<a href="<x:out select="$output/profile/url" />"><x:out select="$output/profile/url" /></a>
						<br>
						<core:url var="userimg" value="/default_user.png" />
						<x:set var="img" select="string($output/imgurl)" />
						<core:if test="${fn:contains(img,'.png')}">
							<core:remove var="userimg" />
							<core:url var="userimg" value="${img}" />
						</core:if>
						<img alt="${userimg}" src="${userimg}"
							style="margin: 0px 20px 0px 350px; width: 100px; height: 100px; align: right">

					</p>
				</div>
			</x:forEach>
		</core:when>

		<core:when test="${fn:contains(message.messageContent,'<issues>')}">
			<div class="title">
				<strong>Issue Recommendation</strong>
			</div>
			<x:parse xml="${message.messageContent}" var="xmldoc" />
			<x:forEach var="output" select="$xmldoc/issues/issue">
				<div class="message-box yellow">
					<p>
						<strong>Bug Id: </strong>
						<x:out select="$output/bugid" />
						<br> <strong>Subject: </strong>
						<x:out select="$output/subject" />
						<br> <strong>Summary: </strong>
						<x:out select="$output/summary" />
						<br> <strong>Link: </strong><a
							href="<x:out select="$output/url" />"><x:out
								select="$output/url" /></a><br>
					</p>
				</div>
			</x:forEach>
		</core:when>
		<core:when test="${fn:contains(message.messageContent,'<complexevents>')}">
			<div class="title">
				<strong>Complex Event</strong>
			</div>
			<x:parse xml="${message.messageContent}" var="xmldoc" />
			<x:forEach var="output" select="$xmldoc/complexevents/event">
				<div class="message-box pink">
					<p>
						<strong>Name: </strong>
						<x:out select="$output/name" />
						<br> <strong>Description: </strong>
						<x:out select="$output/description" />
						<br>
						<strong>Link: </strong><a
							href="<x:out select="$output/url" />"><x:out
								select="$output/url" /></a><br>
					</p>
				</div>
			</x:forEach>
		</core:when>

	<%-- 	<core:when test="${fn:contains(message.messageContent,'xmlfile')}">
			<core:import var="xmlurl" url="/similar.xml" />
			<div class="title">
				<strong>Similar Issue</strong>
			</div>
			<x:parse xml="${xmlurl}" var="xmldoc" />
			<x:forEach var="output" select="$xmldoc/items/item">
				<div class="message-box">
					<p>
					</p>
				</div>
			</x:forEach>

			

		</core:when> --%>
		<core:otherwise>
		
			<strong>Content:</strong>${fn:replace(message.messageContent, newLineChar1, newLineChar2)}<br>
			
		</core:otherwise>
	</core:choose>

</body>
</html>
