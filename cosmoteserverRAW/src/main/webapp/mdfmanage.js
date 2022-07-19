/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
     var server=null;
     var timer_logout=null;
     var timer_onopen=null;
     
     function deleteAllCookies() {
    	    var cookies = document.cookie.split(";");

    	    for (var i = 0; i < cookies.length; i++) {
    	        var cookie = cookies[i];
    	        var eqPos = cookie.indexOf("=");
    	        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
    	        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    	    }
    	}
     function sendupdate(){    	 
         var user=user1;
         var AKname=AK1;
         var msg =user+" UPDATE2 "+AKname;
         alert('update in progress');
         server.send(msg);
         clearInterval(timer_onopen);
         if(timer_onopen!=null) clearInterval(timer_logout);
         timer_onopen=null;
        // server.close();
        // blink2(); //context
     }
     
     function logoutf(){    	 
         var user=user1;
         var AKname=AK1;
         var msg =user+" LOGOUT "+AKname;
    	 alert('15 minutes passed, please logout');
    	 //logout will be trigered server side
    	 server.send(msg);
    	 if(timer_logout!=null) clearInterval(timer_logout);
    	 timer_logout=null;
        // server.close();
        // blink2(); //context
     }
     
	 function connect(){
		if (!('WebSocket' in window)) {
                    alert('WebSocket is not supported by this browser.');
                    return;
		}
	
		try {	
                    // server=new WebSocket("ws://localhost:8080/stm32server/echo");
                   //  server=new WebSocket("ws://fgserver.us.to:8080/stm32server/echo");
                   server=new WebSocket("ws://cloudfieldservices.com:8080/cosmoteserver/echo");
                   // server=new WebSocket("ws://fgserver.us.to:8080/fgserver/echo");
                   // server=new WebSocket("ws://fgserver-anemos.rhcloud.com:8000/fgserver/echo"); //openshift
                    
                    
		}catch(error){
                    return;
		}	
		server.onopen=function(event){
                    alert('Connection Established....');
                    ///var user="<%=username%>";
                    //var user=user1;
                   // var AKname=AK1;
                   // var msg =user+" UPDATE2 "+AKname;
                   // server.send(msg);
                   // blink2(); //contextmenu
                    //start timer for logout at expiry!
                  //  timer_logout=setInterval(logoutf, 60000*15); //every 15mins  
                    timer_onopen=setInterval(sendupdate,2000);//update after 2sec
                    return;
		}		
		server.onclose=function(event){
                   // if(!event.wasClean||event.code !=1000){
                    //var user="<%=username%>";
                    //removed has been commented on 29/05/2019	
                    //var user=user1;
                    //var msg =user+" REMOVE";
                    //server.send(msg);
                   // alert('closing connection');
                   // server.close();
                   //stop timer
                   // clearInterval(timer_keepalive);
                    if(timer_logout!=null) clearInterval(timer_logout);
                    if(timer_onopen!=null) clearInterval(timer_logout);
                    timer_onopen=null;timer_logout=null;
                    alert('websocket CLOSED');   
                    server = null;
                    deleteAllCookies();
                    return;
                    //}
		}
		server.onmessage=function(event){
                    var msg=event.data;
                    var newstr=msg.toString().split(' ');
                    var AKname=newstr[0];
                    var action=newstr[1];
                    var relaystatusF1=newstr[2];
                    var relaystatusF2=newstr[3];
                    var relaystatusF3=newstr[4];
                    //check for STM32 CP state
                   // if (action==="STM32isUP" || "STM32disconnected" ){
                    if (action===("STM32isUP") || action===("STM32disconnected") ){
                        toast(action);
                        toast("relay state at F1: "+relaystatusF1);
                        toast("relay state at F2: "+relaystatusF2);
                        toast("relay state at F3: "+relaystatusF3);                        
                        updatestatusSTM(AKname, action);
                        return;
                    } //else if action is CMDisNOTIDLE,CPisDOWN    
                       // updatestatusSTM(AKname, action);
                        toast(action);
                   // }

                   // updaterelaystatus(relaystatus);
                    return;	
		}
		
		server.onerror=function(event){
			
			toast(event);
			alert('error in websocket protocol!');
			
			}
        }
        function close_websocket(){
            toast("Please click 'Stay on this Page' and we will give you candy");
            server.close();
        }
         function updatestatusSTM(AKname,action){
            
                var state = document.getElementById("statusid");
                   if(action==='STM32isUP'){
                    state.className="status green";
                    state.innerHTML="    online";
                   }
                   if(action==='STM32disconnected'){                
                    state.className="status red flash";
                    state.innerHTML="    offline";
                    
                }  
            }
        function update_header(user,AK){
            var parent  = document.getElementById('plaisio3');
            var userlabel= document.createElement('label');
            var userl=document.createElement('label');
            userl.appendChild(document.createTextNode("USER:   "));
            userlabel.appendChild(document.createTextNode(user));
            parent.appendChild(userl);
            parent.appendChild(userlabel);
            var parent  = document.getElementById('plaisio4');
            var AKlabel= document.createElement('label');
            var status= document.createElement('span');
            status.id="statusid";
            status.name="status";
            status.className="status red flash";
            status.appendChild(document.createTextNode("    offline"));
            AKlabel.appendChild(document.createTextNode(AK));
            parent.appendChild(AKlabel); 
            parent.appendChild(status); 
 
        }
           function updaterelaystatus(relaystatus){
               if(relaystatus==="undefined") return; //nothing to update!
               var radios = document.getElementsByName('CheckboxT2');
               switch(relaystatus){
                    case "000":
                     F1G=F2G=F3G="0";
                     break;
                    case "100":
                     F2G=F3G="0"; 
                     F1G="1";
                     radios[0].checked=true;
                     break;
                    case "010":
                     F1G=F3G="0"; 
                     F2G="1";
                     radios[4].checked=true;
                     break;
                    case "110":
                     F3G="0"; 
                     F1G=F2G="1";
                     break;
                    case "001":
                     F1G=F2G="0"; 
                     F3G="1";
                     break;
                    case "101":
                     F2G="0"; 
                     F1G=F3G="1";
                     break;
                    case "011":
                     F1G="0"; 
                     F2G=F3G="1";
                     break;
                    case "111": 
                     F1G=F2G=F3G="1";
                     break;    
                        
               } 
               
           }
function createTable1(){
                var data = [["----","Fissa 1", "Fissa 2", "Fissa 3"], //headers
                ["Fissa1", "", ""], 
                ["Fissa2", "", ""], 
                ["Fissa3", "", ""]];
            // table headings
                var columnHeadings = data[0];
 
            // Get the count of columns.
                var columnCount = columnHeadings.length;
 
            // The count of rows.
                var rowCount = data.length;
        // Create table. 
        var table = document.createElement('table');
        document.getElementById("plaisio1").appendChild(table);
        // Add the header row. 
        var header = table.createTHead();
        var row = header.insertRow(-1); 
        for (var i = 0; i < columnCount; i++) { 
            var headerCell = document.createElement('th');
            headerCell.innerText = columnHeadings[i].toUpperCase(); 
            row.appendChild(headerCell); 
        }
        // Create table body. 
        var tBody = document.createElement('tbody'); 
        table.appendChild(tBody);
        // Add the data rows to the table body.
        for (var i = 1; i < rowCount; i++) { // each row   
            row = tBody.insertRow(-1);   
            for (var j = 0; j < columnCount; j++) { // each column   
            var cell = row.insertCell(-1);
            //cell.setAttribute('pretty-table', columnHeadings[j].toUpperCase());
            var obj = data[i];  
            if(j>0){
                  //insert check box 
                //var cell0 = table.rows[1].cells[1];
                var chkBox = document.createElement('input');
                chkBox.type='checkbox';
                chkBox.name='CheckboxT1';
                chkBox.id='chkT1id' + 'i'+'j';
                chkBox.checked=false;
                cell.appendChild(chkBox);     
            }
            else{
                cell.innerText =obj[j];
            }
            } 
        }
        table.setAttribute('class','pretty-table');
}

function createTable2(){
                var data = [["--","OPEN", "SHORT","SIN", "NEW NET"], //headers
                ["Fissa1", "undef", "undef","undef","undef"], 
                ["Fissa2", "undef", "undef","undef","undef"], 
                ["Fissa3", "undef", "undef","undef","undef"]];
            // table headings
                var columnHeadings = data[0];
 
            // Get the count of columns.
                var columnCount = columnHeadings.length;
 
            // The count of rows.
                var rowCount = data.length;
        // Create table. 
        var table = document.createElement('table');
        table.id="ActionTable";
        document.getElementById("plaisio1").appendChild(table);
        // Add the header row. 
        var header = table.createTHead();
        var row = header.insertRow(-1); 
        for (var i = 0; i < columnCount; i++) { 
            var headerCell = document.createElement('th');
            headerCell.scope='col';
            headerCell.innerText = columnHeadings[i].toUpperCase(); 
            row.appendChild(headerCell); 
        }
        // Create table body. 
        var tBody = document.createElement('tbody'); 
        table.appendChild(tBody);
        // Add the data rows to the table body.
        for (var i = 1; i < rowCount; i++) { // each row   
            row = tBody.insertRow(-1);   
            for (var j = 0; j < columnCount; j++) { // each column   
              var cell = row.insertCell(-1);
                //cell.setAttribute('pretty-table', columnHeadings[j].toUpperCase());
              var obj = data[i];     
            if(j>0){
                  //insert check box 
                //var cell0 = table.rows[1].cells[1];
                var chkBox = document.createElement('input');
                chkBox.type='checkbox';
                chkBox.name='CheckboxT2';
                chkBox.id='chkT2id' +  i.toString()+j.toString();
                chkBox.checked=false;
                cell.appendChild(chkBox);     
            }
            else{
                cell.innerText =obj[j];
            }
            } 
        }
        //table.setAttribute('class','flat-table');
        var cbs = document.querySelectorAll('[type="checkbox"]');
        [].forEach.call(cbs, function (cbs) {
            cbs.addEventListener("click", function(){
            	resetother(this.id);
                //console.log(this.id);
            });
        });
 }

function resetother(chkBoxid){
	var rowCount=4;var columnCount=4;
	var lastChar = chkBoxid.substr(chkBoxid.length - 1); // => check if last column
	//var rowChar= chkBoxid.substr(ckBoxid.length - 1);
	if(lastChar=='4'){
		return; 			
	}
	//asign onclick event for each checkbox
	for (var i = 1; i < rowCount; i++) { // each column 
	    	for (var j = 1; j < columnCount; j++) {
	    		var chkBox2 = document.getElementById('chkT2id' +i.toString()+j.toString());
	    		if(chkBoxid==chkBox2.id){
	    			//do nothing
	    		}
	    		else { //reset the rest checkboxes
	    			chkBox2.checked=false;
	    	    }
	    
	         }
	 }
	}
 function transmit(){
   var radios = document.getElementsByName('CheckboxT2');
   for (var i = 0, length = radios.length; i < length; i++) {
   if (radios[i].checked) {
    // Send the data using post
      var text="ACTION"+i;
      var user=user1;
      var ak=AK1;
      var msg =user+" "+ak+" "+text;
      server.send(msg);
      return;
     }
    }
 }
function twebsocket(){
  var radios = document.getElementsByName('CheckboxT2');
  var msg="";
 for (var i = 0, length = radios.length; i < length; i++) {
   if (radios[i].checked) {
      switch (i){
          
          //fissa1
          case 0: msg=msg+"1 ";break;
          case 1: msg=msg+"1 ";break;
          case 2: msg=msg+"1 ";break;
          case 3: msg=msg+"1 ";break;
          //fissa2
          case 4: msg=msg+"1 ";break;
          case 5: msg=msg+"1 ";break;
          case 6: msg=msg+"1 ";break;
          case 7: msg=msg+"1 ";break; 
          //fissa3
          case 8: msg=msg+"1 ";break;
          case 9: msg=msg+"1 ";break;
          case 10: msg=msg+"1 ";break;
          case 11: msg=msg+"1 ";break; 
      }
    }
    else {
        switch (i){
          //fissa3  
          case 0: msg=msg+"0 ";break;
          case 1: msg=msg+"0 ";break;
          case 2: msg=msg+"0 ";break;
          case 3: msg=msg+"0 ";break;
          //fissa2
          case 4: msg=msg+"0 ";break;
          case 5: msg=msg+"0 ";break;
          case 6: msg=msg+"0 ";break;
          case 7: msg=msg+"0 ";break; 
          //fissa3
          case 8: msg=msg+"0 ";break;
          case 9: msg=msg+"0 ";break;
          case 10: msg=msg+"0 ";break;
          case 11: msg=msg+"0 ";break;     
        
      }
    }
 }     
    var user=user1;
    var ak=AK1; 
    var msg2=user+" COMMAND "+ak+" "+msg;
    if(server.readyState === server.OPEN) {
    	server.send(msg2);}
    else { alert('cannot execute command, websocket is closed!');}
}
function transmit_command(){
	
//if no websockets (closed due to 2nd login try
//even the following code is POST we don't allow
//any command:
if (server===null) return;

var $form = $( this ),
url = $form.attr( "action" );
url = "mdfmanage";
var table = document.getElementById("ActionTable");
var radios = document.getElementsByName('CheckboxT2');
var msg="";
 for (var i = 0, length = radios.length; i < length; i++) {
   if (radios[i].checked) {
      switch (i){
          
          //fissa1
          case 0: msg=msg+"1 ";break;
          case 1: msg=msg+"1 ";break;
          case 2: msg=msg+"1 ";break;
          case 3: msg=msg+"1 ";break;
          //fissa2
          case 4: msg=msg+"1 ";break;
          case 5: msg=msg+"1 ";break;
          case 6: msg=msg+"1 ";break;
          case 7: msg=msg+"1 ";break; 
          //fissa3
          case 8: msg=msg+"1 ";break;
          case 9: msg=msg+"1 ";break;
          case 10: msg=msg+"1 ";break;
          case 11: msg=msg+"1 ";break; 
      }
    }
    else {
        switch (i){
          //fissa3  
          case 0: msg=msg+"0 ";break;
          case 1: msg=msg+"0 ";break;
          case 2: msg=msg+"0 ";break;
          case 3: msg=msg+"0 ";break;
          //fissa2
          case 4: msg=msg+"0 ";break;
          case 5: msg=msg+"0 ";break;
          case 6: msg=msg+"0 ";break;
          case 7: msg=msg+"0 ";break; 
          //fissa3
          case 8: msg=msg+"0 ";break;
          case 9: msg=msg+"0 ";break;
          case 10: msg=msg+"0 ";break;
          case 11: msg=msg+"0 ";break;     
        
      }
    }
 }  
   // we have observed that button stucked in disabled mode
   // and in chrome even after reload it will be disabled and not send command
   // so we 'removed' this functionality
   //
   // document.getElementById("button-blue").disabled = true; 
    var user=user1;
    var ak=AK1;
    // Send the data using post
    var posting = $.post( url, {user: user, ak: ak, msgvar: msg },"text/plain" );
      posting.done(function( data ) {
    	  inform_user(data);
    	 // document.getElementById("button-blue").disabled = false; 
     //   var message=data.toString();
     //   if (message=="CP IS DOWN"){
     //       alert('cannot execute command, cp is down');
     //   return;
     //   }
     //   if(message=="CP is not IDLE"){
     //       alert('cannot execute command, cmd is not IDLE');
     //       return;
     //   }
     // ;
     });    
    
}

function inform_user(data){
    var msg=data;
    var newstr=msg.toString().split(' ');
    var AKname=newstr[0];
    var action=newstr[1];
    var relaystatus=newstr[2];
    //check for STM32 CP state
   // if (action==="STM32isUP" || "STM32disconnected" ){
    if (action===("STM32isUP") || action===("STM32disconnected") ){
        toast(action);
        toast("relay state changed to: "+relaystatus);
        updatestatusSTM(AKname, action);
        return;
    } //else if action is CMDisNOTIDLE,CPisDOWN    
       // updatestatusSTM(AKname, action);
        toast(action);
   // }

   // updaterelaystatus(relaystatus);
    return;	
}



 
