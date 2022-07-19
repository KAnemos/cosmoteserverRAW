var container;
var last;
var first;
var index=0;var arraycont=new Array();var numoflines=10;

function toast_old2(text)
{
    var pl = document.getElementById("plaisio2");
    var label = document.createElement('label');
    var status= document.createElement('span');
    status.id="statusid"+i;
    status.name='status';
    status.className="toast";
    status.appendChild(document.createTextNode("    offline"));
    label.htmlFor = "id"+i;
    label.id="lid"+i;
    label.appendChild(document.createTextNode(text));                       
    pl.appendChild(label);
    pl.appendChild(status);
    pl.appendChild(document.createElement('br'));                      
    pl.appendChild(document.createElement('br'));
    i=i+1;
    

}
function toast_old(text)
{
    var container = $(document.createElement("div"));
    container.addClass("toast");
    
    var message = $(document.createElement("div"));
    message.addClass("message");
    message.text(text);
    message.appendTo(container);
    var pl = document.getElementById("plaisio2");
    container.appendTo(pl);
    
    container.delay(10).fadeIn("slow", function()
    {
        $(this).delay(50).fadeOut("slow", function() { $(this).remove(); });
    });
}
function toast(text)
{
    
    container = $(document.createElement("div"));
    container.addClass("toast");
    container.id="cid"+index.toString();
    container.name="con"+index.toString();
    arraycont[index]=container;

    if (index===0) {
        last=document.getElementById("plaisio2");
        cid0=container.id;
    }   
    var message = $(document.createElement("div"));
    message.addClass("message");
    message.text(text);
    message.appendTo(container);
 //   message.appendTo(container2);
  
    if(index<numoflines) container.appendTo(last);

    if(index===numoflines) {

      var remdiv=arraycont[index-numoflines];
      remdiv.remove();
      //$('.toast').css('background','red');
      //shift the container objects up
     for (var j = 0; j <= (index-1); j++) {
         var help=arraycont[j+1];
         arraycont[j]=help;
     }
     arraycont[index].appendTo(last);
    }
    if(index<numoflines) index=index+1; //go up to numoflines
  
}/**
 * 
 */