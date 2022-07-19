<%-- 
    Document   : mdfmanage
    Created on : 3 Φεβ 2018, 8:20:30 μμ
    Author     : koutote
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="username" scope="request" class="java.lang.String" />
<jsp:useBean id="AKlabel" scope="request" class="java.lang.String" />
<jsp:useBean id="relaystatus" scope="request" class="java.lang.String" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <link type="text/css" rel="stylesheet" href="mdfmgt.css" />
        <link type="text/css" rel="stylesheet" href="messagenew.css" />
        <%-- <link type="text/css" rel="stylesheet" href="newcss.css" />--%>
        <title>MDF Management</title>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
        <script src="mdfmanage.js"></script>
        <script src="message.js"></script>
        <link type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Arvo&display=swap" rel="stylesheet">
    </head>
<body>
  <div id="container1">
      <div id="header">
       <%--    <span> STM32 box</span>
          <span>for<%=username%></span>
          <span>Working on <%=AKlabel%></span> --%>
          <div id="plaisio3"></div>
          <div id="plaisio4"></div>
          
      </div>
       <script type="text/javascript">
            var i=0;
            var last=null;
       </script>    
      
      <div id="plaisio1">
       <%-- <div id="phonenumber">No phone </div>  
        <div id="last" style="display: none;"></div>
  
        <input type="hidden" id="num" value="1">>--%>
       <script>createTable2();</script>
       <input id="button-blue" type="button" value="Transmit Command" onclick="transmit_command()" >

<div class="inputWithIcon inputIconBg">
  <input type="text" placeholder="  Fyssa1:">
  <i class="fa fa-cloud fa-2x" aria-hidden="true"></i>
</div>

<div class="inputWithIcon inputIconBg">
  <input type="text" placeholder="  Fyssa2:">
  <i class="fa fa-cloud fa-2x" aria-hidden="true"></i>
</div>

<div class="inputWithIcon inputIconBg">
  <input type="text" placeholder="  Fyssa3:">
  <i class="fa fa-cloud fa-2x" aria-hidden="true"></i>
</div>

      </div>
      
      <div id="plaisio2">
      	<div id="yourlogo">
      	<img style='height: 100%; object-fit: contain' src="dark_logo_transparent.png" />
      	 <%--	<p style="padding-left: 20px;">YourLogoHere</p>--%>
      	</div> 
      </div>  
      </div>
        


   
 
        

        

<script type="text/javascript">
    var user1="<%=username%>";
    var AK1="<%=AKlabel%>";
    var relaystatus1="<%=relaystatus%>";
    if (relaystatus1==="1") alert(relaystatus);
</script>
<script type="text/javascript">
    var user2="<%=username%>";
    
    update_header(user2,AK1);
</script>
<script>connect();</script>


</body>
</html>
