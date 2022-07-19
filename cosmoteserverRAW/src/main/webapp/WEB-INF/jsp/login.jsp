<html>
<head>
<title>CloudFieldServer</title>
<link rel="stylesheet" type="text/css" href="logincss.css"/>
</head>
<%
response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); //HTTP 1.1
response.setHeader("Expires", "0"); //proxie
response.setHeader("Pragma", "no-cache"); //HTTP 1.0

String message = (String) request.getAttribute("message");
if (message != null) {
out.println("<p>" + message + "</p>");
}
%>
<form method="post" action="">
<div class='wrap'>
  Login to Cosmote Server
    
        <input type='text' id='username' name="username" placeholder='Username'>
        <input type='password' id='password' name="password" placeholder='Password'>
  <button class='forgot'>FORGOT PASSWORD ?</button> <button class='login'>LOG IN</button>
</div>
</form>
</html>    