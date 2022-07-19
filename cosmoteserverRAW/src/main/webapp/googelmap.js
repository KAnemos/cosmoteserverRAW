var map;
var myCircle=[];
var marker;
var divmarker=[];
var images=['images/blue.png','images/brown.png','images/green.png','images/purple.png','images/red.png'];
var colours=['#6699cc','#990033','#66FF66','#8A2E8A','#FF0000'];

function add_map_point(lat, lng) {

}


function startmap(ypoint,xpoint){
   var mapProp = {
    center:new google.maps.LatLng(ypoint,xpoint),
    zoom:5,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
 map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
 return;
}

function initialize(ypoint,xpoint,actina,phone,count,t1,t2,speed) {
  var myLatlng = new google.maps.LatLng(ypoint,xpoint);
 // var mapProp = {
 //   center:new google.maps.LatLng(ypoint,xpoint),
 //   zoom:5,
 //   mapTypeId:google.maps.MapTypeId.ROADMAP
 // };
 //map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

 //var image = 'images/green.png';
 var image=images[count];
 var info=phone+t1+t2;
// marker = new google.maps.Marker({
 divmarker[count] = new google.maps.Marker({    
      position: myLatlng,
      map: map,
      icon:image,
      title: info
  });/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
  myCircle[count] = new google.maps.Circle({
  center:myLatlng,
  radius:parseInt(actina),
  strokeColor:colours[count],
  strokeOpacity:0.8,
  strokeWeight:2,
  fillColor:colours[count],
  fillOpacity:0.2
});
 //marker.setMap(map);
 divmarker[count].setMap(map);
 myCircle[count].setMap(map);
 return;
}
function updatemarker(ypoint,xpoint,actina,phone,count,t1,t2,speed){
    var myLatlng = new google.maps.LatLng(ypoint,xpoint);
    var image=images[count];
    var info=phone+t1+t2;
    myCircle[count].setMap(null);
//    var image = 'images/van.png';
//    var marker = new google.maps.Marker({
//      position: myLatlng,
//      map: map,
//      icon:image,
//      title: phone
//  });
  myCircle[count] = new google.maps.Circle({
  center:myLatlng,
  radius:parseInt(actina),
  strokeColor:colours[count],
  strokeOpacity:0.8,
  strokeWeight:2,
  fillColor:colours[count],
  fillOpacity:0.2
});
  //  marker.setMap(map);
  //  myCircle.setMap(map);
  divmarker[count].setPosition(myLatlng);
  divmarker[count].setTitle(info);
  myCircle[count].setMap(map);
  return;
}

function markermanagement(label_id,action){
    
    if (action.toString()==="return") return;
    
    var strlid=label_id.toString();
    var id=strlid.substring(3); //lid0, lid1, lid2 etc....
    
    if(divmarker.length==0){ //no marker just exit
        return;
    }
    else {
        switch(action){
            case "hide":
                divmarker[id].setVisible(false);
                myCircle[id].setVisible(false);
                break;
            case  "zoom":
                divmarker[id].setVisible(true);
                myCircle[id].setVisible(true);
                map.setZoom(20);
                map.panTo(divmarker[id].position);
                break;
        }
        
    }
    
   return;
}
//google.maps.event.addDomListener(window, 'load', initialize);
//google.maps.event.addDomListener(window, "resize", function() {
// var center = map.getCenter();
// google.maps.event.trigger(map, "resize");
// map.setCenter(center); 
//});