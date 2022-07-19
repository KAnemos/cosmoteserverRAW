<%-- 
    Document   : home
    Created on : 1 ??? 2015, 1:57:01 ??
    Author     : koutote
--%>

<jsp:useBean id="message" scope="request" class="java.lang.String" />
<jsp:useBean id="username" scope="request" class="java.lang.String" />
<%-- <jsp:useBean id="xcoord" scope="request" class="java.lang.String" />
<jsp:useBean id="ycoord" scope="request" class="java.lang.String" />--%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/new.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/contextMenu.css" />
<title>fgserver</title>
<head>
<script src="http://maps.googleapis.com/maps/api/js"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="googelmap.js"></script>
<script src="JVscript.js"></script>     
</head>

<body>
 <div class="container">
  <div class="header"><a href="#"><img src="" alt="Insert Logo Here" name="Insert_logo" width="180" height="90" id="Insert_logo" style="background-color: #C6D580; display:block;" /></a>
    <p><strong><%=message%></strong></p>
    <p><input name="user" style="border-color:#0C3" type="text" value=<%=username%> readonly="readonly" /></p>     
    <!-- end .header --></div>
    
  <div class="phone1" id="phone1">No phone</div>
  <p>&nbsp;</p>
   <%-- <input id="mgt" type="button" value="Go To Management" onclick="window.open('http://localhost:8080/fgserver/management')">--%>
  <%-- <input id=  "mgt" type="button" value="Go To Management" onclick="window.open('http://fgserver.us.to:8080/fgserver/management')">
   <%-- <input id="mgt" type="button" value="Go To Management" onclick="window.open('http://fgserver-anemos.rhcloud.com:8080/fgserver/management')">--%>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <input id="data" type="button" value="Show last data" onclick="show()" > 
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <input id="reqpos" type="button" value="Request Position" onclick="requestposition()" > 
  <p>&nbsp;</p>
  <input id="reqposendless" type="button" value="Request Position Endless" onclick="requestpositionendless()" > 
  <p>&nbsp;</p>
  <input id="stoppos" type="button" value="STOP" onclick="stopposition()"> 
  <p>&nbsp;</p>
</div>
<div id="googleMap"></div>
<div id="last" style="display: none;"></div>
<input type="hidden" id="num" value="1">
<input type="hidden" id="Vnum" value="1">
<script type="text/javascript">
    var user1="<%=username%>";
</script>
<script>update();</script>
<script>connect();</script>
<%-- <script>initialize('40.640836', '22.945824','1000.','WELCOME','3');</script>--%>
<script>startmap('40.640836', '22.945824');</script>
<%--<script>initialize('40.640836', '22.945824','1000.','WELCOME','3');</script>--%>
<%--<script>addmarker('40.593943', '22.785879','1000.','WELCOME','3');</script>--%>
<%--<script>initialize('40.118347', '22.490192','1000.','WELCOME','2');</script>--%>
</body>
</html>