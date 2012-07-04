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
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.xml.parsers.*"%>
<%@ page import="org.w3c.dom.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>


<head>
<style>
html,body {
	height: 100%;
}

body {
	margin: 10px auto;
	min-width: 800px;
	min-height: 640px;
	align: center;
}
</style>
<script type="text/javascript">
	var a = document.body.clientWidth;
	var b = document.body.clientHeight;

	document.write(a);
	function reload() {
		this.location = "listPattern.html";

	}

	function en() {
		var a = document.getElementById("aa");
		a.disabled = false;
		a.checked = true;
		var b = document.getElementById("bb");
		b.disabled = false;
		var c = document.getElementById("cc");
		c.disabled = false;
		var sub = document.getElementById("sub");
		sub.checked = true;

	}
	function dis() {
		var a = document.getElementById("aa");
		a.disabled = true;
		var b = document.getElementById("bb");
		b.disabled = true;
		var c = document.getElementById("cc");
		c.disabled = true;
		var unsub = document.getElementById("unsub");
		unsub.checked = true;
	}
	function ensub() {
		var sub = document.getElementById("sub");
		sub.checked = true;

	}
	function insMsg() {

		var msgAccount = document.getElementById("xmpp");
		if (msgAccount.value == "") {
			alert("Please insert your instant message account!");
		} else {
			alert("Your Instant Message Account is " + msgAccount.value + "!");
			window.location = "modifyMsgAccount?msgAccount=" + msgAccount.value;
		}

	}

	function mouseover(x) {
		document.getElementById(x).style.background = "#FFFF99"
	}
	function mouseout(x) {
		document.getElementById(x).style.background = "#F3F6F9"
	}
	function mouseout2(x) {
		document.getElementById(x).style.background = "#00FFCC"
	}

	function ViewbyStatus() {
		var tab = document.getElementById("pattable");
		//document.getElementById("pattable").refresh();
		document.getElementById("pattable").style.display = "none";
		document.getElementById("pattable2").style.display = "";

	}
	function ViewbyPattern() {
		var tab = document.getElementById("pattable");
		//document.getElementById("pattable").refresh();
		document.getElementById("pattable2").style.display = "none";
		document.getElementById("pattable").style.display = "";

	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Action Page</title>

</head>
<body STYLE='OVERFLOW-X: HIDDEN' SCROLL=auto>
	<iframe name=patternUpdate src="ifModify.html" scrolling="no"
		frameborder=0 style="display: none"></iframe>

	<core:url var="imgad" value="imgad.png" />
	<core:url var="rssimg" value="rssimg.jpg" />

	<table width=100% height=100% align="left">

		<tr valign="top" height=100%>
			<td width=40% bgcolor="#F2FDDB">
				<table id="pattable" bgcolor="#F3F6F9" border=0 width=100%>
					<tr height=30px valign="top" bgcolor="#5EAA54">
						<td><strong><font color="#FFFFFF">Pattern
									list</font> </strong></td>
						<td></td>
					</tr>
					<core:if test="${!empty patternList}">
						<tr style="height: 30px;">
							<td width=30% onclick="ViewbyStatus()" style="cursor: pointer;">Status</td>
							<td width=70% onclick="ViewbyPattern()" style="cursor: pointer;">Pattern
								Name</td>
						</tr>

						<core:forEach items="${patternList}" var="patternForm">

							<tr height=30px>
								<core:if test="${patternForm.status!='unsubscribed'}">
									<td><a><img src="${imgad}" alt="subscribed"
											style="width: 30px; height: 26px;" /> </a></td>
								</core:if>
								<core:if test="${patternForm.status=='unsubscribed'}">
									<td></td>
								</core:if>
								<core:if test="${patternForm.patternName!=sPattern}">
									<td onmouseover="mouseover(this.id)"
										id="${patternForm.patternName}" onmouseout="mouseout(this.id)"
										onclick="click(this.id)"><a
										href="choosedPattern?patternName=${patternForm.patternName}">${patternForm.patternName}</a>

									</td>
								</core:if>
								<core:if test="${patternForm.patternName==sPattern}">
									<td bgcolor="#00FFCC" onmouseover="mouseover(this.id)"
										id="${patternForm.patternName}"
										onmouseout="mouseout2(this.id)" onclick="click(this.id)"><a
										href="choosedPattern?patternName=${patternForm.patternName}">${patternForm.patternName}
									</a></td>
								</core:if>
							</tr>


						</core:forEach>

					</core:if>

				</table>

				<table id="pattable2" bgcolor="#F3F6F9" border=0 width=100%
					height=100% style="display: none">
					<tr height=30px valign="top" bgcolor="#5EAA54">
						<td><strong><font color="#FFFFFF">Pattern
									list</font> </strong></td>
						<td></td>
					</tr>
					<core:if test="${!empty patternList}">
						<tr style="height: 30px;">
							<td width=30% onclick="ViewbyStatus()" style="cursor: pointer;">Status</td>
							<td width=70% onclick="ViewbyPattern()" style="cursor: pointer;">Pattern
								Name</td>
						</tr>

						<core:forEach items="${patternList}" var="patternForm">

							<tr height=30px>
								<core:if test="${patternForm.status!='unsubscribed'}">
									<td><a><img src="${imgad}" alt="subscribed"
											style="width: 30px; height: 26px;" /> </a></td>

									<core:if test="${patternForm.patternName!=sPattern}">
										<td onmouseover="mouseover(this.id)"
											id="${patternForm.patternName}"
											onmouseout="mouseout(this.id)" onclick="click(this.id)"><a
											href="choosedPattern?patternName=${patternForm.patternName}">${patternForm.patternName}</a>

										</td>
									</core:if>
									<core:if test="${patternForm.patternName==sPattern}">
										<td bgcolor="#00FFCC" onmouseover="mouseover(this.id)"
											id="${patternForm.patternName}"
											onmouseout="mouseout2(this.id)" onclick="click(this.id)"><a
											href="choosedPattern?patternName=${patternForm.patternName}">${patternForm.patternName}
										</a></td>
									</core:if>
								</core:if>
							</tr>


						</core:forEach>
						<core:forEach items="${patternList}" var="patternForm">

							<tr height=30px>

								<core:if test="${patternForm.status=='unsubscribed'}">
									<td></td>

									<core:if test="${patternForm.patternName!=sPattern}">
										<td onmouseover="mouseover(this.id)"
											id="${patternForm.patternName}"
											onmouseout="mouseout(this.id)" onclick="click(this.id)"><a
											href="choosedPattern?patternName=${patternForm.patternName}">${patternForm.patternName}</a>

										</td>
									</core:if>
									<core:if test="${patternForm.patternName==sPattern}">
										<td bgcolor="#00FFCC" onmouseover="mouseover(this.id)"
											id="${patternForm.patternName}"
											onmouseout="mouseout2(this.id)" onclick="click(this.id)"><a
											href="choosedPattern?patternName=${patternForm.patternName}">${patternForm.patternName}
										</a></td>
									</core:if>
								</core:if>
							</tr>


						</core:forEach>

					</core:if>

				</table>

			</td>


			<td width=60% height=100%>

				<table id=ptdetails width="100%" height=100% bgcolor="#F3F6F9">
					<tr bgcolor="#5EAA54" height=30px>
						<td colspan="2"><font color="#FFFFFF"><strong>Pattern
									Description: ${sPattern}</strong> </font></td>
					</tr>
					<tr bgcolor="#E8F5FE" valign="top">

						<td>${patternDescription}<core:if test="${empty sPattern}">Please select a pattern first!</core:if>
							<!-- <table>
							<tr>
								<td>XML Tree</td>
							</tr>
							<tr>
								<td>servlet-name</td>
								<td>servlet-class:</td>
							</tr>
							<%/* try {
			String PatternId = request.getParameter("sPattern");
			String filename = "";
				filename = getServletConfig().getServletContext().getRealPath(
						PatternId+".xml");
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(new File(filename));
				NodeList nl = doc.getElementsByTagName("component");
				for (int i = 0; i < nl.getLength(); i++) {
					Element node = (Element) nl.item(i);
					out.print("<tr><td>name:");
					out.println(node.getAttribute("name"));
					out.print("</td></tr>");
					out.print("<tr><td>meta:");
					NodeList ml = doc.getElementsByTagName("meta");
					for (int j = 0; j < ml.getLength(); j++) {
						Element metanode= (Element) ml.item(j);
						NodeList pl = doc.getElementsByTagName("property");
						out.print("<tr><td>property name:");
						for (int k = 0; k < pl.getLength(); k++) {
							Element pnode= (Element) pl.item(k);
							out.println(pnode.getAttribute("name"));
							}
						out.print("</td></tr>");
					}
					out.println(node.getElementsByTagName("meta")
							.item(0).getFirstChild().getNodeValue());										
					out.print("</td><td>eventTyp:");
					out.println(node.getElementsByTagName("eventType")
							.item(0).getFirstChild().getNodeValue());
					out.print("</td></tr>");
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} */%>
						</table> -->
						</td>
					</tr>
					<tr height=220px>
						<td><form:form action="action" commandName="preferences"
								method="POST">

								<table id=actionfm width="100%" height=20%>
									<tr height=10px>
										<td><b>Please select an Action for ${sPattern}</b></td>
									</tr>
									<tr>
										<td>Action:</td>
									</tr>
									<tr>
										<td onclick="en()"><form:radiobutton id="sub"
												path="subRadio" value="subscription" />Subscription</td>
										<td onclick="dis()"><form:radiobutton id="unsub"
												path="subRadio" value="unsubscription" />UnSubscription</td>

									</tr>
									<tr>
										<span class=""></span>
										<td onclick="ensub()"><form:checkbox id="aa"
												path="subChBx" value="WebMessage" />Message</td>
									</tr>
									<tr>
										<td onclick="ensub()"><form:checkbox id="bb"
												path="subChBx" value="Email" />Email</td>
									</tr>
									<tr>


										<td onclick="ensub()"><form:checkbox id="cc"
												path="subChBx" value="InstantMessage" />Instant Message</td>
										<td>
												<input id="xmpp" name="ins" type="text"
													value="${messageAccount}" />
											 <input type="button" value="ok" onclick="insMsg()" />
									</tr>

									<core:if test="${empty sPattern}">
										<tr>
											<td><img src="unrssimg.jpg" alt="subscribed" /></td>
										</tr>
										<tr>
											<td><input type="submit" onclick="reload()" disabled
												value="update"></td>
											<td>Please select a pattern first!</td>
										</tr>
									</core:if>
									<core:if test="${!empty sPattern}">
										<tr>
											<td><a href="rssFeed?patternName=${sPattern}"
												target="_blank"><img src="rssimg.jpg" alt="subscribed" />
											</a></td>
										</tr>
										<tr>
											<td><input type="submit" value="update"
												onclick="reload()"></td>
										</tr>
									</core:if>
								</table>
							</form:form></td>
					</tr>

				</table>
			</td>
	</table>
</body>
</html>