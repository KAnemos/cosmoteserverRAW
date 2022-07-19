<html>
<head>
<title>Publisher</title>
<link rel="stylesheet" type="text/css" href="styles.css"/>
</head>
<body>
<h1>Register</h1>
<%
String message = (String) request.getAttribute("message");
if (message != null) {
out.println("<p>" + message + "</p>");
}
%>
<form method="post" action="">
<div>
Username: <input type="text" name="urn" size="36" />
</div>
<div>
Enter Password: <input type="password" name="p1" size="36" />
</div>
<div>
Re-enter Password: <input type="password" name="p2" size="36" />
</div>
<div>
<input type="submit" value="Register" />
</div>
</form>
</html>