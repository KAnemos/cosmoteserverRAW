<%-- 
    Document   : management
    Created on : 8 Μαρ 2015, 5:32:28 μμ
    Author     : koutote
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="username" scope="request" class="java.lang.String" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/global.css" />
        <title>System Management</title>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
       <script type="text/javascript">
           function update(){
           var $form = $( this ),
               // term = $form.find( "input[name='phone']" ).val(),
                url = $form.attr( "action" );
                var posting = $.post( url, { phone: "UPDATE", ADD: "1", REMOVE: "1" },"text/plain" );
                posting.done(function( data ) {
                    var last=document.getElementById("phonenumber");
                    var phonediv=document.getElementById("phonenumber");
                    last.innerHTML="";
                    var smsg=data.toString().split(' ');
                    for (i = 0; i < smsg.length-1; i=i+2) {
                     //   alert("Data: " + smsg[i]);
                        var newdiv = document.createElement('div');
                        newdiv.id='ph'+i;
                        newdiv.className="phelement";
                        newdiv.innerHTML=smsg[i]+"<-->"+smsg[i+1];
                        phonediv.appendChild(newdiv);
                        //last=newdiv;;
                    }
                });
           }
        </script>     
    </head>
<body onload="update()">
  <div id="container">
      <div id="header">
          <span> List of mobile phones for</span> 
          <span><%=username%></span>
      </div>

      <div id="datalistmgt">
        <div id="phonenumber">No phone </div>  
        <div id="last" style="display: none;"></div>
        <input type="hidden" id="num" value="1">
      </div>


  <div id="plaisio2">    
      <form method="post" action="" id="addForm">
        <input type="text" placeholder="Enter Phone" name="phone"
               class="validate[required,custom[onlyLetter],length[0,100]] feedback-input"
               placeholder="Name" id="phone" />
        <input type="text" placeholder="Enter Label" name="label"
               class="validate[required,custom[onlyLetter],length[0,100]] feedback-input"
               placeholder="Name" id="label" />
        <input type="submit" name="ADD" value="ADD" id="button-blue"/>
      </form>
     <form method="post" action="" id="removeForm">
        <input type="text" placeholder="Enter Phone" name="rphone"
               class="validate[required,custom[onlyLetter],length[0,100]] feedback-input"
               placeholder="Name" id="name" />
        <input type="submit" name="REMOVE" value="REMOVE" id="button-blue" />
     </form>
      </div>
</div>
<script>
// Attach a submit handler to the form
$("#addForm").submit(function( event ) {
// Stop form from submitting normally
event.preventDefault();
// Get some values from elements on the page:
var $form = $( this ),
labelterm=$form.find( "input[name='label']" ).val()
term = $form.find( "input[name='phone']" ).val(),
url = $form.attr( "action" );
// Send the data using post
var posting = $.post( url, { phone: term, label: labelterm, ADD: "ADD", REMOVE: "1" },"text/plain" );
posting.done(function( data ) {
    
    var message=data.toString();
    if (message=="MAXNUMBEROFPHONES"){
        alert('Adding new phone is not allowed,you reached maximum number');
        return;
    }
    //var last=document.getElementById("phonenumber");
    var phonediv=document.getElementById("phonenumber");
    while(phonediv.hasChildNodes()){
    phonediv.removeChild(phonediv.lastChild);
    }
   // last.innerHTML="";
    var smsg=data.toString().split(' ');
    for (i = 0; i < smsg.length-1; i=i+2) {
     //   alert("Data: " + smsg[i]);
        var newdiv = document.createElement('div');
        newdiv.id='ph'+i;
        newdiv.className="phelement";
        newdiv.innerHTML=smsg[i]+"<-->"+smsg[i+1];
        phonediv.appendChild(newdiv);
       // last=newdiv;;
 
    }
});
});
</script>
<script>
// Attach a submit handler to the form
$("#removeForm").submit(function( event ) {
// Stop form from submitting normally
event.preventDefault();
// Get some values from elements on the page:
var $form = $( this ),
term = $form.find( "input[name='rphone']" ).val(),
labelterm="nolabel",
url = $form.attr( "action" );
// Send the data using post
var posting = $.post( url, { phone: term,label: labelterm,ADD: "1", REMOVE: "REMOVE" },"text/plain" );
posting.done(function( data ) {
    //var last=document.getElementById("phonenumber");
    var phonediv=document.getElementById("phonenumber");
    while(phonediv.hasChildNodes()){
    phonediv.removeChild(phonediv.lastChild);
    }
    //last.innerHTML="";
    var smsg=data.toString().split(' ');
    for (i = 0; i < smsg.length-1; i=i+2) {
    //    alert("Data: " + smsg[i]);
        var newdiv = document.createElement('div');
        newdiv.id='ph'+i;
        newdiv.className="phelement";
        newdiv.innerHTML=smsg[i]+"<-->"+smsg[i+1];
        phonediv.appendChild(newdiv);
      //  last=newdiv;
    }
});
});
</script>
</body>
</html>
