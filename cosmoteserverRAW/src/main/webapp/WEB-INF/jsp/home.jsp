<%-- 
    Document   : home
    Created on : 1 ??? 2015, 1:57:01 ??
    Author     : koutote
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="message" scope="request" class="java.lang.String" />
<jsp:useBean id="username" scope="request" class="java.lang.String" />
<jsp:useBean id="AKlabel" scope="request" class="java.lang.String" />
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link type="text/css" rel="stylesheet" href="newcss.css" />
<%--<link type="text/css" rel="stylesheet" href="contextMenu.css" />--%>
<title>cosmoteserver-linux</title>
<%--<script src="http://www.openlayers.org/api/OpenLayers.js"></script>--%>
<link rel="stylesheet" href="https://openlayers.org/en/v4.6.5/css/ol.css" type="text/css">
<script src="https://openlayers.org/en/v4.6.5/build/ol.js" type="text/javascript"></script>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDtH3zNUkgIXArVU2pnpb-OOgP6jRxL7xE"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="googelmap.js"></script>
<script src="JVscript.js"></script>

</head>
<body>
<div id="container">
    <div id="header">
        <strong><%=message%></strong> 
        <strong><%=username%></strong>
    </div>
 
    <div id="googleMap"></div>
    <div id="AK_list"> </div>



    <div id="footer">
       <div id="yourlogo">
       <img style='height: 100%; width: 100%; object-fit: contain' src="dark_logo_transparent.png" />

       <%--<p style="padding-left: 20px;">YourLogoHere</p>--%>
      </div>
    <div id="plaisioforbutton">
	  <input id="reqposendless" type="button" class="btn third" value="MDF management" onclick="mdfmanage()" > 
        <%--<input id="stoppos" type="button" class="button" value="STOP" onclick="stopposition()"> --%>
      <input id="stoppos" type="button" class="btn third" value="Logout" onclick="logoutf()"> 
    </div>
    </div>
</div>
<div id="last" style="display: none;"></div>
<input type="hidden" id="num" value="1">
<input type="hidden" id="Vnum" value="1">
<input type="hidden" id="AKnum" value="1">
<script type="text/javascript">
    var user1="<%=username%>";
</script>
<script>update();</script>
<%--<script>osmstartmap();</script>--%>
<script>connect();</script>
<%--<script>startmap('40.640836', '22.945824');</script> --%>
    <div id="popup" class="ol-popup">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>  



</body>
</html>

