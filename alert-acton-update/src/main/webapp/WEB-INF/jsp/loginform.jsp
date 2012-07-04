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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<SCRIPT language="javascript">
function submitTest(){

	if  (document.getElementById("a").value=="") return false;
	if  (document.getElementById("b").value=="") return false;
}
</SCRIPT>
</head>
<body>
<center>
<h3>Login Page</h3>
<br/>
<form:form method="POST" commandName="loginform" onsubmit="return submitTest()">
Username: 
<form:input id="a" path="username"/>
Email: 
<form:input id="b" path="email"/>
Uid: 
<form:input id="c" path="uid"/>
<input type="submit" value="login"/>
</form:form>
</center>
</body>
</html>
