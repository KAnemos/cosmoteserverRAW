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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link type="text/css" rel="stylesheet" href="newcss.css" />
<%--<link type="text/css" rel="stylesheet" href="contextMenu.css" />--%>
<title>stm32server-linux</title>
<%--<script src="http://www.openlayers.org/api/OpenLayers.js"></script>--%>
<link rel="stylesheet" href="https://openlayers.org/en/v4.6.5/css/ol.css" type="text/css">
<script src="https://openlayers.org/en/v4.6.5/build/ol.js" type="text/javascript"></script>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDtH3zNUkgIXArVU2pnpb-OOgP6jRxL7xE"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="googelmap.js"></script>
<script src="JVscript.js"></script>

</head>
<body>
<div id="container2">
    <div id="header">
        <strong><%=message%></strong></p> 
        <strong><%=username%></strong></p>
    </div>
 
    <div id="googleMap"></div>
    <div id="AK_list"> </div>



    <div id="footer">
  	<%-- <input id="mgt" type="button" class="button" value="Go To Management" onclick="window.open('http://localhost:8080/fgserver/management')"> --%> 
      <%--  <input id="mgt" type="button" class="button" value="Go To Management" onclick="window.open('http://fgserver.us.to:8080/fgserver/management')"> --%> 
       <%-- <input id="mgt" type="button" class="button" value="Go To Management" onclick="manage()"> 
	<input id="data" type="button" class="button" value="Show last data" onclick="show()" > 
	<input id="reqpos" type="button" class="button" value="Request Position" onclick="requestposition()" > --%>
    <br>
    <p>
    <input id="refresh" type="button" class="button" value="Refresh page" onclick="window.location.reload(); "> 
	<input id="reqposendless" type="button" class="button" value="MDF management" onclick="mdfmanage()" > 
        <%--<input id="stoppos" type="button" class="button" value="STOP" onclick="stopposition()"> --%>
        <input id="stoppos" type="button" class="button" value="STOP" onclick="download()"> 
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
<script>connect();</script>
<%--<script>startmap('40.640836', '22.945824');</script> --%>
<script>
var map;
var mapLat = -33.829357;
var mapLng = 150.961761;
var mapDefaultZoom = 10;

map = new ol.Map({
target: "googleMap",
layers: [
new ol.layer.Tile({
source: new ol.source.OSM({
url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png"
})
})
],
view: new ol.View({
center: ol.proj.fromLonLat([mapLng, mapLat]),
zoom: mapDefaultZoom
})
});

var lng=151.2093;
var lat=-33.8688;
var vectorLayer = new ol.layer.Vector({
	source:new ol.source.Vector({
	features: [new ol.Feature({
	geometry: new ol.geom.Point(ol.proj.transform([parseFloat(lng), parseFloat(lat)], 'EPSG:4326', 'EPSG:3857')),
	})]
	}),
	style: new ol.style.Style({
	image: new ol.style.Icon({
	anchor: [0.5, 0.5],
	anchorXUnits: "fraction",
	anchorYUnits: "fraction",
	src: "https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg"
	})
	})
	});

<%--add_map_point(-33.8688, 151.2093);--%>
map.addLayer(vectorLayer);


</script>
</script>   
</body>
</html>
