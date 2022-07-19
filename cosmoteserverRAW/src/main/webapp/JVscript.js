/* 13/12/2019 websocket onclose comment the event codes
 * .
 *  
 * 
To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
           var winRef=null;
           var relaystatusF1="undefined";
           var relaystatusF2="undefined";
           var relaystatusF3="undefined";
           var numofAKs=0;
           var map;
           var vectorSource;
           var content = document.getElementById('popup-content');
           var closer = document.getElementById('popup-closer');
           
           

           
           function download(){
              var a = document.createElement("a");
              document.body.appendChild(a);
              a.style = "display: none";
              
              var url = "downloadfile";
               var geting = $.get( url );
               geting.done(function( data ) {
                  alert("OK");
                  
                 // var byteNumbers = new Uint8Array(data.length);
                 var byteNumbers= data.split(" ");
               //    for (var i = 0; i < data.length; i++) {
               //     byteNumbers[i] = data[i];
               //    }
                 // var blob = new Blob(byteNumbers, {type: "octet/stream"});
                  var blob = new Blob(byteNumbers, {type: "text/javascript"});
                  url = window.URL.createObjectURL(blob);
                  a.href = url;
                  a.download = "example.txt";
                  a.click();
                  window.URL.revokeObjectURL(url);
               });
                         
           }
           function update(){
           
               // term = $form.find( "input[name='phone']" ).val(),
                var url = "home";
                var posting = $.post( url, { AK: "UPDATE", ADD: "1", REMOVE: "1" },"text/plain" );
                posting.done(function( data ) {
                    var last=document.getElementById("AK_list");
                    last.innerHTML="";
                    var smsg=data.toString().split(' ');
                    for (i = 0; i < smsg.length-1; i++) {
                     //   alert("Data: " + smsg[i]);
                     /*   var newdiv = document.createElement('div');
                        newdiv.id='ph'+i;
                        newdiv.class="phone1";
                        newdiv.innerHTML=smsg[i];
                        last.appendChild(newdiv);*/
                        
                        var checkbox = document.createElement('input');
                        checkbox.type = 'radio';
                        checkbox.name = 'check';
                        checkbox.value = 'Checkboxd'+i;
                        checkbox.id = "id"+i;
                        
                        var label = document.createElement('label');
                        var status= document.createElement('span');
                        status.id="statusid"+i;
                        status.name='status';
                        status.className="status red flash";
                        status.appendChild(document.createTextNode("    offline"));
                        
                        var rstatus= document.createElement('span');
                        rstatus.id="rstatusid"+i;
                        rstatus.name='rstatus';
                        rstatus.className="checkmark";
                        
                        //status.innerHTML="    offline";
                      //  var status = document.createTextNode("   DOWN");
                        label.htmlFor = "id"+i;
                        label.id="lid"+i;
                        label.className='rcontainer';
                        label.appendChild(document.createTextNode(smsg[i]));

                        label.appendChild(checkbox);
                        label.appendChild(rstatus);
                        label.appendChild(status);
                        last.appendChild(label);
                        
                       //last.appendChild(checkbox);                        
                       //last.appendChild(label);
                       // last.appendChild(status);

                       // last.appendChild(status);
                        last.appendChild(document.createElement('br'));                      
                       // last.appendChild(document.createElement('br'));

                    }
                    osmstartmap();
                });
                
           }

                      

        function hidenewbuttons(phone){
            
            var radios = document.getElementsByName('check');

            for (var i = 0, length = radios.length; i < length; i++) {
             
        // do whatever you want with the checked radio
                var selector = 'label[for=' + radios[i].id + ']';
                var label = document.querySelector(selector);
                var text = label.innerHTML;
                if(text===phone){
                   // alert(text);
                    var rp=document.getElementById("reqpos");
                    rp.style.visibility="hidden";
                    var rp=document.getElementById("reqposendless");
                    rp.style.visibility="hidden";
                    var rp=document.getElementById("stoppos");
                    rp.style.visibility="hidden";
                    break;
                }
                
                       
          }
        }
           function updatestatus(phone,action){
            
            var radios = document.getElementsByName('check');
            
  
            for (var i = 0, length = radios.length; i < length; i++) {
             
        // do whatever you want with the checked radio
                var div = document.getElementById("last");
                var span = document.getElementById("statusid"+i);
                var selector = 'label[for=' + radios[i].id + ']';
                var label = document.querySelector(selector);
                
                var text = label.innerHTML;
                if(text===phone){
                   var x = document.getElementById(radios[i].id).textContent;
                   if(action==='mobile'){
                    span.className="status green";
                    span.innerHTML="    online";
                   }
                   if(action==='disconnected'){                
                    span.className="status red flash";
                    span.innerHTML="    offline";
                   } 
                 //  alert('status of'+x+'changed!!');
                    
                }
                
                       
          }
        } 
        function findAKlist(){
            var radios = document.getElementsByName('check');
            var text="";
            for (var i = 0, length = radios.length; i < length; i++) {
             var selector = 'label[for=' + radios[i].id + ']';
             var label = document.querySelector(selector);
             
             text =text+ label.childNodes[0].nodeValue+" ";
             }
            numofAKs=length; 
            return text; 
        }
        function updatestatusSTM(AKname,action){
            
            var radios = document.getElementsByName('check');
            
  
            for (var i = 0, length = radios.length; i < length; i++) {
             
        // do whatever you want with the checked radio
                var div = document.getElementById("last");
                var span = document.getElementById("statusid"+i);
                var selector = 'label[for=' + radios[i].id + ']';
                var label = document.querySelector(selector);
                //var text = label.innerHTML;
                var text =label.childNodes[0].nodeValue;
                if(text===AKname){
                   //var x = document.getElementById(radios[i].id).textContent;
                   if(action==='STM32isUP'){
                    span.className="status green";
                    span.innerHTML="    online";
                   }
                   if(action==='STM32disconnected'){                
                    span.className="status red flash";
                    span.innerHTML="    offline";
                   } 
                 //  alert('status of'+x+'changed!!');
                    
                }
                
                       
          }
        }       
         function shownewbuttons(phone){
            
            var radios = document.getElementsByName('check');

            for (var i = 0, length = radios.length; i < length; i++) {
             
        // do whatever you want with the checked radio
                var selector = 'label[for=' + radios[i].id + ']';
                var label = document.querySelector(selector);
                var text = label.innerHTML;
                if(text===phone){
                   // alert(text);
                    var rp=document.getElementById("reqpos");
                    rp.style.visibility="visible";
                    var rp=document.getElementById("reqposendless");
                    rp.style.visibility="visible";
                    var rp=document.getElementById("stoppos");
                    rp.style.visibility="visible";
                    break;
                }
                
                       
          }
        }     
        
         
        function osmstartmap(){
        	var mapLat = -33.829357;
        	var mapLng = 150.961761;
        	var gmapLat=40.582367;
        	var gmapLng=22.962935;
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
        	center: ol.proj.fromLonLat([gmapLng, gmapLat]),
        	zoom: mapDefaultZoom
        	})
        	});

        	var lng=151.2093;
        	var lat=-33.8688;
        	//create markers for AKlist
        	var data=findAKlist();
        	var smsg=data.toString().split(' ');
        	var markers=[];
            for (i = 0; i < smsg.length-1; i++) {
        	 var marker = new ol.Feature({
        		  geometry: new ol.geom.Point(
        		    ol.proj.fromLonLat([lng,lat])
        		  ),  // Cordinates of New York's City Hall
        		  description: smsg[i],
        		  state: 'Unknown'
        		});
        	    marker.setStyle(new ol.style.Style({
                    image: new ol.style.Icon(({
                    crossOrigin: 'anonymous',
                    //src: 'https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg'
                    src: 'green.png'
                    }))
                }));
        	    
        	 markers.push(marker);   

            }
       	     vectorSource = new ol.source.Vector({
	    	  features: markers
	    	 });
        	 var markerVectorLayer = new ol.layer.Vector({
        	    	  source: vectorSource,
        	    	});
        	 map.addLayer(markerVectorLayer);

        	/*var vectorLayer = new ol.layer.Vector({
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
        		});*/

        	//add_map_point(-33.8688, 151.2093);
        	//map.addLayer(vectorLayer);
        	 
         /*	var lng2=151.2093;
        	var lat2=-31.8688;
        	var marker2 = new ol.Feature({
        		  geometry: new ol.geom.Point(
        		    ol.proj.fromLonLat([lng2,lat2])
        		  ),  // Cordinates of New York's City Hall
        		  description:'Big Ben'
        		});
        	    marker2.setStyle(new ol.style.Style({
                    image: new ol.style.Icon(({
                    crossOrigin: 'anonymous',
                    src: 'https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg'
                    }))
                }));
        	    
        	    vectorSource.addFeature(marker2);  
        	    map.setView(new ol.View({
                 	center: ol.proj.fromLonLat([lng2, lat2]),
                	zoom: mapDefaultZoom
                	}));*/
        	    
        	    /**
        	     * Elements that make up the popup.
        	     */
        	    var container = document.getElementById('popup');
        	    var content = document.getElementById('popup-content');
        	    var closer = document.getElementById('popup-closer');
                /**
                 * Add a click handler to hide the popup.
                 * @return {boolean} Don't follow the href.
                 */
                closer.onclick = function() {
                  overlay.setPosition(undefined);
                  closer.blur();
                  return false;
                };

        	    /**
        	     * Create an overlay to anchor the popup to the map.
        	     */
        	    var overlay = new ol.Overlay({
        	      element: container,
        	      autoPan: true,
        	      autoPanAnimation: {
        	        duration: 250
        	      }
        	    });
        	    
        	    /**
        	     * Add a click handler to hide the popup.
        	     * @return {boolean} Don't follow the href.
        	     
        	    closer.onclick = function() {
        	      overlay.setPosition(undefined);
        	      closer.blur();
        	      return false;
        	    };*/
        	    map.addOverlay(overlay);
        	    
        	    var hasFeature = false;
        	    map.on('pointermove', function (evt) {
        	        map.forEachFeatureAtPixel(evt.pixel, function (feature) {
        	            var coordinate = evt.coordinate;
        	            overlay.setPosition(coordinate);
        	            hasFeature = true;
        	            var featureName = feature.get('description');
        	            var status=feature.get('state');
        	            content.innerHTML = '<p>'+featureName+'</p><p>'+status+'</p><code>';
        	        });
        	        if (!hasFeature) {
        	            overlay.setPosition(undefined);
        	        }
        	    });
        	    
        	    map.on('singleclick', function(evt) {
        	        var coordinate = evt.coordinate;
        	        var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(
        	              coordinate, 'EPSG:3857', 'EPSG:4326'));
        	        var content = document.getElementById('popup-content');
        	          content.innerHTML = '<p>You clicked here:</p><code>' + hdms +
        	              '</code>';
        	          overlay.setPosition(coordinate);
        	        });
        	    

        }
        
        function initmarkerinfo(AKname,action,xlong,ylat){
        	/*var extent = map.getView().calculateExtent(map.getSize());
        	vectorSource.forEachFeatureInExtent(extent, function(feature){
        		var properties=feature.getProperties();
        			//feature.setProperties({'description':action});
            		if(properties.description=AKname){
            			feature.setProperties({'geometry': new ol.geom.Point(ol.proj.fromLonLat([xlong,ylat])),
            			        'state':action})                    
            		}

        		
        	});   */
        	
        	var features = vectorSource.getFeatures();
        	var long=parseFloat(xlong);
        	var lat=parseFloat(ylat);
        	var i=0
        	for (i=0; i<features.length;i++) {
        	    var feature = features[i];
        	    var featureName = feature.get('description');
        	    if(featureName===AKname){
        	    	feature.setProperties({
        	    		'geometry': new ol.geom.Point(ol.proj.fromLonLat([lat,long])),
    			        'state':action});
        	    	if(action==='STM32disconnected'){
        	    		feature.setStyle(new ol.style.Style({
                             image: new ol.style.Icon(({
                                crossOrigin: 'anonymous',
                                //src: 'https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg'
                                src: 'red.png'
                                }))
                            }));
        	    	}
        	    	else{// STM32 is up
        	    		feature.setStyle(new ol.style.Style({
                            image: new ol.style.Icon(({
                               crossOrigin: 'anonymous',
                               //src: 'https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg'
                               src: 'green.png'
                               }))
                           }));
        	    	}
        	    		
        	    	return;
        	    }
        	    
        	}
        }
        function updatemarkerinfo(AKname,action){

        	var features = vectorSource.getFeatures();
        	var i=0
        	for (i=0; i<features.length;i++) {
        	    var feature = features[i];
        	    var featureName = feature.get('description');
        	    if(featureName===AKname){
        	    	feature.setProperties({
    			        'state':action});
        	    	if(action==='STM32disconnected'){
        	    		feature.setStyle(new ol.style.Style({
                             image: new ol.style.Icon(({
                                crossOrigin: 'anonymous',
                                //src: 'https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg'
                                src: 'red.png'
                                }))
                            }));
        	    	}
        	    	else{// STM32 is up
        	    		feature.setStyle(new ol.style.Style({
                            image: new ol.style.Icon(({
                               crossOrigin: 'anonymous',
                               //src: 'https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg'
                               src: 'green.png'
                               }))
                           }));
        	    	}
        	    		
        	    	return;
        	    }
        	    
        	}
        }

        function mdfmanage(){
            
            var radios = document.getElementsByName('check');
            for (var i = 0, length = radios.length; i < length; i++) {
             if (radios[i].checked) {
        // do whatever you want with the checked radio
                var selector = 'label[for=' + radios[i].id + ']';
                var label = document.querySelector(selector);
                //var text = label.innerHTML;
                var text =label.childNodes[0].nodeValue;
               // alert(text);
                var user=user1;
                var url_safe2=encodeURIComponent(user);
                var AKlabel=text;
                var url_safe3=encodeURIComponent(AKlabel);
                var relaystatus=relaystatusF1+relaystatusF2+relaystatusF3;
                var url_safe4=encodeURIComponent(relaystatus);
               // var url = "http://fgserver.us.to:8080/stm32server/mdfmanage?username="+url_safe2+"&AKnum="+url_safe3 +"&relaystatus="+url_safe4;
                var url = "http://cloudfieldservices.com:8080/cosmoteserver/mdfmanage?username="+url_safe2+"&AKnum="+url_safe3 +"&relaystatus="+url_safe4;
               // var url = "http://localhost:8080/stm32server/mdfmanage?username="+url_safe2+"&AKnum="+url_safe3 +"&relaystatus="+url_safe4;
                    //if youw want to create several new windows:
                    window.open(url);
                   // var url = "http://fgserver.us.to:8080/stm32server/mdfmanage?username="+url_safe2+"&AKnum="+url_safe3 ;
                    //if you want one popup (same for every xxx_box...klm_box, bz_box..etc
                 ///   winRef = window.open(url, 'winPop', 'sampleListOfOptions');
                 //
                ///    if(winRef === null || winRef.closed){
                //if you want one new (not popup) (same for every xxx_box...klm_box, bz_box..etc
                 ////        winRef = window.open(url,"MyWindowName");
                ///         winRef.onbeforeunload = function(){
                ///                winRef = null;
               ///          };

               ///          }
               ///     else
               ///     {
                        //give it focus (in case it got burried)
               ///         winRef.focus();
               ///     } 
               // window.open("http://localhost:8080/stm32server/mdfmanage?username="+url_safe2+"&AKnum="+url_safe3);
               // window.open("http://fgserver.us.to:8080/stm32server/mdfmanage?username="+url_safe2+"&AKnum="+url_safe3);
              //  window.open("http://fgserver.us.to:8080/fgserver/management?username="+url_safe2);
               // window.open("http://fgserver-anemos.rhcloud.com:8080/fgserver/management?username="+url_safe2); //openshift
             
            }
        }
      }
        
        function requestposition(){
            
            var radios = document.getElementsByName('check');

            for (var i = 0, length = radios.length; i < length; i++) {
             if (radios[i].checked) {
        // do whatever you want with the checked radio
                var selector = 'label[for=' + radios[i].id + ']';
                var label = document.querySelector(selector);
                var text = label.innerHTML;
                alert(text);
                var user=user1;
                var msg =user+" REQUESTone "+text;
                server.send(msg);
                return;
             
            }
            
          }
        }

        function stopposition(){
            
            var radios = document.getElementsByName('check');

            for (var i = 0, length = radios.length; i < length; i++) {
             if (radios[i].checked) {
        // do whatever you want with the checked radio
                var selector = 'label[for=' + radios[i].id + ']';
                var label = document.querySelector(selector);
                var text = label.innerHTML;
                alert(text);
                var span = document.getElementById("statusid"+i);
                span.className="status red flash";
                span.innerHTML="    offline";
                var user=user1;
                var msg =user+" STOP "+text;
                server.send(msg);
                return;
             
            }
            
          }
        }
    function blink2() {
    $("#AK_list").children("label").each(function () {
        $(this).data("DefaultBGColor", $(this).css("background-color"));
        $(this).mouseover(function() {
            $(this).css ("background-Color", "#c0c0c0");
            var label_id=$(this).attr('id');
            context_menu2(label_id);
            
        });
        $(this).mouseout(function () {
            $(this).css("background-Color", "#BCA9F5");
            var id=$(this).attr('id');
           
        }); 
    });
   // context_menu();
    return;
    }
    function context_menu(label_id){
        
    $(function() {
         $.contextMenu({
             selector: '#'+label_id, 
              callback: function(key, options) {
              //var msg = "clicked: " + key;
             // window.console && console.log(msg) || alert(msg); 
             markermanagement(label_id,key);
            },
            items: {
             "hide"     : { name: "hide",icon: "hide" },
             "zoom"     : { name: "zoom",icon: "copy" },
             "quit"     : { name: "quit",icon: "quit" }
            }
        });

       $('#'+label_id).on('click', function(e){
           //console.log('clicked', this);
           markermanagement(label_id);
       })
    });
 }
  function context_menu2(label_id){
        $(function () {
            "use strict";
            $.contextMenu({
               // selector: '#context-menu-abc',
                selector: '#'+label_id,
                callback: function (key, options) {
                    if (key === 'hide') {
                        // specific code for your first option
                    } else if (key === 'zoom') {
                        // specific code for your second option
                    } else if (key === 'quit') {
                        // specific code for your third option
                    }
                    markermanagement(label_id,key);
                },
                items: {
                    'hide': { name: "hide", icon: "hide" },
                    'zoom': { name: "zoom", icon: "zoom" },
                    'return': { name: "return", icon: "return" }
                }
            });
        });
  }
     var server=null;
     var timer_logout=null;
     var timer_onopen=null;
     
     function sendupdate(){    	 
         var user=user1;
         var AKlist=findAKlist();
         var msg =user+" UPDATE "+numofAKs+" "+AKlist;
         server.send(msg);
         alert('update in progress');
         server.send(msg);
         clearInterval(timer_onopen);
         if(timer_onopen!=null) clearInterval(timer_logout);
         timer_onopen=null;
        // server.close();
        // blink2(); //context
     }
     
     function deleteAllCookies() {
 	    var cookies = document.cookie.split(";");

 	    for (var i = 0; i < cookies.length; i++) {
 	        var cookie = cookies[i];
 	        var eqPos = cookie.indexOf("=");
 	        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
 	        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
 	    }
 	}
     
     function logoutf(){    	 
         var user=user1;
         var AKname='dummy';
         var msg =user+" LOGOUTUSER "+AKname;
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
                   //  server=new WebSocket("ws://localhost:8080/stm32server/echo");
                    // server=new WebSocket("ws://fgserver.us.to:8080/stm32server/echo");
                    server=new WebSocket("ws://cloudfieldservices.com:8080/cosmoteserver/echo");
                   // server=new WebSocket("ws://fgserver.us.to:8080/fgserver/echo");
                   // server=new WebSocket("ws://fgserver-anemos.rhcloud.com:8000/fgserver/echo"); //openshift
                    
                    
		}catch(error){
                    return;
		}	
		server.onopen=function(event){
                    alert('Connection Established....');
                    //var user="<%=username%>";
                   // var user=user1;
                   // var AKlist=findAKlist();
                   // var msg =user+" UPDATE "+numofAKs+" "+AKlist;
                   // server.send(msg);
                   // blink2(); //contextmenu
                    //timer_logout=setInterval(logoutf, 60000*15); //every 15mins 
                    timer_onopen=setInterval(sendupdate,2000);//update after 2sec
                    return;
		}		
		server.onclose=function(event){
                  // if(!event.wasClean||event.code !=1000){ 13/12/2019
                    //var user="<%=username%>";
                    //var user=user1;
                    //var msg =user+" REMOVE";
                    //server.send(msg);
                   // alert('closing connection');
                    if(timer_logout!=null) clearInterval(timer_logout);
                    if(timer_onopen!=null) clearInterval(timer_logout);
                    timer_onopen=null;timer_logout=null;
                    alert('websocket CLOSED'); 
                    server=null;
                    deleteAllCookies();
                    window.location.replace("http://cloudfieldservices.com:8080/cosmoteserver/login")
                    return;
                   // } 13/12/2019
		}
		server.onmessage=function(event){
                    var msg=event.data;
                    var strmsg=msg.toString();
                    var newstr=msg.toString().split(' ');
                    var AKname=newstr[0];
                    var action=newstr[1];
                    var relaystatusF1=newstr[2];
                    var relaystatusF2=newstr[3];
                    var relaystatusF3=newstr[4];
                    var xlong=newstr[5];
                    var ylat=newstr[6];
                    //check for STM32 CP state
                    if (action===("STM32isUP") || action===("STM32disconnected") ){
                        updatestatusSTM(AKname, action);
                        if(xlong==null){
                            updatemarkerinfo(AKname,action);
                          }
                        else //valid coordinates from server
                          {
                        	initmarkerinfo(AKname,action,xlong,ylat);	
                          }
                    } 
                    
                    
                    return;	
                    //check the case of NO GPS and NO NETWORK
                    if(strmsg.startsWith('NO GPS')){
                        //do nothing
                        //alert(strmsg);
                        return;
                    }
                    var smsg=msg.toString().split(' ');
                    var ylat=smsg[0];
                    var xlong=smsg[1];
                    var actina=smsg[2];
                    var speed=smsg[3];
                    var t1=smsg[4];
                    var t2=smsg[5];
                    var phone=smsg[6];
                    var count=smsg[7];
                    var action=smsg[8]; 
                    var num=document.getElementById("num").value;
                    var inum=parseInt(num);
                    //if(inum==2){
                    if (action=="mobile"){
                        //update colour of phone and show new button
                        updatestatus(phone,action);
                        return;
                    }
                    if (action=="disconnected"){
                        //update colour of phone and show new button
                        //hidenewbuttons(phone);
                        updatestatus(phone,action);
                        return;
                    }
                    if (action=="init"){
                        initialize(ylat,xlong,actina,phone,count,t1,t2,speed);
                    }
                    else{//action==update
                        updatemarker(ylat,xlong,actina,phone,count,t1,t2,speed);
                    }
                 
                
                    return;	
		}
        }
        
/**
 * 
 */