<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
<!--
BODY 		{ margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; }


 /* FONT COLORS */


TABLE		{ COLOR: #000000; FONT: 11px arial, sans-serif; font-weight: normal }

.title		{ COLOR: #0033FF; FONT: 12px arial, sans-serif; font-weight: bold; }

#NewsDiv	{ position: absolute; left: 0; top: 0; width: 100% }

 /* PAGE LINK COLORS */

a:link		{ color: #0033FF; text-decoration: underline; }

a:visited	{ color: #6633FF; text-decoration: underline; }

a:active	{ color: #0033FF; text-decoration: underline; }

a:hover		{ color: #6699FF; text-decoration: none; }

-->
</style>
</head>
<body BGCOLOR="#F0F0F0" TEXT="#000000" onMouseover="scrollspeed=0" onMouseout="scrollspeed=current" OnLoad="NewsScrollStart();">
<div id="NewsDiv">
<table cellpadding="5" cellspacing="0" border="0" width="100%"><tr><td>






<!-- SCROLLER CONTENT STARTS HERE -->


<span class="title">
Website News<br>
</span>
You can list as many news paragraphs in this window as you need. You will edit the "news_win.htm" to change the text shown in this area. <a href="SAMPLE-frame.htm" target="_top">Sample Link</a>

<br><br><br>



<span class="title">
Display Options<br>
</span>
To have the scroller reload at the top for the second time through, change the "frameheight" to "2" and change the "nextdelay" to "1" or "2".

<br><br><br>





<span class="title">
Resizing the Window<br>
</span>
If you change the size of the IFrame window edit the "frameheight" variable in the "news_win.htm" to match your new window size.

<br><br>

The IFrame size and "frameheight" variable should match so the text re-scrolls from the bottom of this window. <a href="SAMPLE-frame.htm" target="_top">Sample Link</a>

<br>



<!-- SCROLLER CONTENT ENDS HERE -->






</td></tr></table>
</div>




<!-- YOU DO NOT NEED TO EDIT BELOW THIS LINE -->




<script language="JavaScript" type="text/javascript">


var scrollspeed		= "1"		// SET SCROLLER SPEED 1 = SLOWEST
var speedjump		= "30"		// ADJUST SCROLL JUMPING = RANGE 20 TO 40
var startdelay 		= "2" 		// START SCROLLING DELAY IN SECONDS
var nextdelay		= "0" 		// SECOND SCROLL DELAY IN SECONDS 0 = QUICKEST
var topspace		= "2px"		// TOP SPACING FIRST TIME SCROLLING
var frameheight		= "200px"	// IF YOU RESIZE THE WINDOW EDIT THIS HEIGHT TO MATCH



current = (scrollspeed)


function HeightData(){
AreaHeight=dataobj.offsetHeight
if (AreaHeight==0){
setTimeout("HeightData()",( startdelay * 1000 ))
}
else {
ScrollNewsDiv()
}}

function NewsScrollStart(){
dataobj=document.all? document.all.NewsDiv : document.getElementById("NewsDiv")
dataobj.style.top=topspace
setTimeout("HeightData()",( startdelay * 1000 ))
}

function ScrollNewsDiv(){
dataobj.style.top=parseInt(dataobj.style.top)-(scrollspeed)
if (parseInt(dataobj.style.top)<AreaHeight*(-1)) {
dataobj.style.top=frameheight
setTimeout("ScrollNewsDiv()",( nextdelay * 1000 ))
}
else {
setTimeout("ScrollNewsDiv()",speedjump)
}}




</script>

</body>
</html>