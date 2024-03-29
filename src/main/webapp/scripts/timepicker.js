//	Written by Tan Ling wee
//	on 19 June 2005
//	email :	info@sparrowscripts.com 
//      url : www.sparrowscripts.com

/////////////////////////////////Here is the fix from Sihui Wu 
// More Fixes by Mahesh, 5/2007
// - Handles a wider variety of short time formats
// - When multiple time elements are used, ensures that if the picker button 
//   is pressed, the previously pressed button is unpressed.
// - Widget shows time closest to time in textbox element
// - Widget is modal and throws a curtain over other screen elements
// - If AM/PM is not specified, all hours within 9-11 inclusive are considered to be AM, and the rest PM.
//   This is consistent with usage of time in speech, for business hours.
// - Fixed so that pressing ESC closes the timepicker
// - Fixed script so that it works with Yahoo! maps AJAX API v.3.4. Variable dom inteferes with the Yahoo! script, so
// - has been renamed to docGeid.


  // modify image path to suit your application.
  // var imagePath='widgets/timePicker/images/';
  var imagePath="images/";

  var ie=document.all;
  var docGeid=document.getElementById;
  //var ns4=document.layers;
  var bShow=false;
  var textCtl; // "bogus" form field?? what is this for???
  
  var debugging=false; // for use in logging.

  //function tpDId(eleId) { return document.getElementById(eleId); }

  // t is an element.
  function setTimePicker(t) {
    textCtl.value=t;
    // if (typeof(validateTime)!="undefined")
    //  validateTime(textCtl); // try to get rid of this... this is reference OUTSIDE of the timepicker!
    //validateDatePicker(textCtl);
    closeTimePicker();
  }

  /* nearestTime: find the nearest time within 15 mins.
     assumption: time is formatted as xx:xx AM/PM or xx:xx:xx AM/PM, and is a valid time.
  */
  function nearestTime(n) {
    if (debugging) logger("nearestTime");
    var t = new Object();
    t.value = n;
    // t is not a screen object, so call vDP2. vDP itself expects to be given a reference to a textbox.
    validateDatePicker2(t);
    
    // time is now validated; should be in xx:xx AM/PM format.
    var arr = t.value.split(":");
    var a2=arr[1].split(" ");
    var mn=parseInt(a2[0],10);
    var AMPM=a2[1];
    
    // get nearest minute boundary, within 15 mins.
    var nMin= parseInt((mn+7)/15, 10)*15;
    
    return arr[0]+":"+padZero(nMin)+" "+AMPM;
    
    
  }


  /*
    mode: AM or PM
    tm: time selected, must be properly formatted: xx:xx AM/PM, e.g. 12:30 PM
    
    if tm is provided, then mode is ignored.
  */
  function refreshTimePicker(mode, tm) {
    // was a selected time provided?
	if (tm===undefined) { // is ===undefined an error, or is this correct javascript syntax?
        if (mode==0)
          { 
            suffix="AM"; 
          }
        else
          { 
            suffix="PM"; 
          }
    } else {
      tm = nearestTime(tm); // get time to nearest 15 min. interval.
      suffix=tm.split(" ")[1];
      if (suffix=="AM") 
        mode = 0; 
      else 
        mode = 1;
    }

	// sHTML = "<table><tr><td><table cellpadding=3 cellspacing=0 bgcolor='#f0f0f0'>";
    sHTML = "<table><tr><td><table cellpadding=3 cellspacing=0 bgcolor='#FFFFFF'>";
    for (i=8;i<18;i++) {

      sHTML+="<tr align=right style='font-family:verdana;font-size:11px;color:#000000;'>";

      if (i>12) {
        hr = i-12;
		suffix = "PM";
      }
      else {
        hr=i;
		suffix = "AM"
		if (i==12)
		suffix = "PM";
      }  

      for (j=0;j<2;j++) {
        var thisTime=hr+":"+padZero(j*30)+" " + suffix;
        var bgcolor = "";
        if (thisTime==tm) {bgcolor="bgcolor='#F7C8E3'";}
        
		
        sHTML+="<td " + bgcolor + "width=57 style='cursor:hand;font-family:verdana;font-size:11px;' onmouseover='this.style.backgroundColor=\"#66CCFF\"' onmouseout='this.style.backgroundColor=\"\"' onclick='setTimePicker(\""+ hr + ":" + padZero(j*30) + "&nbsp;" + suffix 
        + "\")'><a style='text-decoration:none;color:#000000' href='javascript:setTimePicker(\""+ hr + ":" + padZero(j*30) + " " + suffix + "\")'>" + hr + ":"+padZero(j*30) +"&nbsp;"+ suffix + "&nbsp;</a></td>";

      }

      sHTML+="</tr>";
    }
    sHTML += "</table></td></tr></table>";
    document.getElementById("timePickerContent").innerHTML = sHTML;
  }

  if (docGeid){
    document.write ("<div id='timepicker' style='z-index:9;position:absolute;visibility:hidden;'><table style='border-width:3px;border-style:solid;border-color:#0033AA' bgcolor='#ffffff' cellpadding=0><tr bgcolor='#0033AA'><td><table cellpadding=0 cellspacing=0 width='100%' background='" + imagePath + "titleback.gif'><tr valign=bottom height=5><td style='font-family:verdana;font-size:11px;color:#ffffff;padding:3px' valign=center><b>&nbsp;Select&nbsp;Time </b></td><td></td><td></td><td align=right valign=center>&nbsp;<img onclick='closeTimePicker()' src='" + imagePath + "close.gif'  STYLE='cursor:hand'>&nbsp;</td></tr></table></td></tr><tr><td colspan=2><span id='timePickerContent'></span></td></tr></table></div>");
    refreshTimePicker(0);
  }

  var crossobj=(docGeid)?document.getElementById("timepicker").style : ie? document.all.timepicker : document.timepicker;
  var currentCtl;


/*
  // capture window resize event
  function register(e) {
    curtain.setScreenerSize();
	  return true;
  }
*/

  // get absolute position of a control. use with overlays, dropdowns, etc.
  function getAbsPos(ctl) {
    var leftpos=0
    var toppos=0
    var aTag = ctl
    do {
      aTag = aTag.offsetParent;
      leftpos  += aTag.offsetLeft;
      toppos += aTag.offsetTop;
    } while(aTag.tagName!="BODY" && aTag.tagName!='HTML');
    
    var o= new Object();
    o.left=leftpos
    o.top = toppos;
    return o;
  }    

  
  // show time picker. ctl is the timepicker button (image) pressed. ctl2 is the text element to be populated with the time.
  function selectTime(ctl,ctl2) {
    if (debugging) logger("selectTime:"+ctl2.id);

    textCtl=ctl2;
    
    /* Modification below by MV, 5/2007. If you have multiple time pickers on a page, 
    and click one picker, and then click another one without closing the first, this modification
    ensures that the Button for the first picker reverts to unpressed. In the original script,
    if timePicker buttons were pressed one after the other, they would all show up as pressed. */
    

    if ((currentCtl != ctl) && (currentCtl != null)) {// not the same
        currentCtl.src=imagePath + "timepicker.gif"; // prev button in released state
    }
    currentCtl = ctl;
    currentCtl.src=imagePath + "timepicker2.gif"; // curr button pressed state

    // let the timepicker show a time as close to the current choice as possible, if the time
    // in the textbox is valid.
	
    if (ctl2.value!="") {
        var res=validateDatePicker2(ctl2);
        if (res)
            refreshTimePicker(0, ctl2.value);
        else
            refreshTimePicker(0);
    }
    
    aPos = getAbsPos(ctl);
    
    crossobj.left =  ctl.offsetLeft + aPos.left + "px";
    crossobj.top = ctl.offsetHeight +  aPos.top + "px"; 
    crossobj.visibility=(docGeid||ie)? "visible" : "show"
    hideElement( 'SELECT', document.getElementById("timepicker") );
    hideElement( 'APPLET', document.getElementById("timepicker") );
    
	//alert("Left: " + crossobj.left);
    
    // make the time picker modal.
    curtain.show();
    
    bShow = true;
  }

  // hides <select> and <applet> objects (for IE only)
  function hideElement( elmID, overDiv ){
    if( ie ){
      for( i = 0; i < document.all.tags( elmID ).length; i++ ){
        obj = document.all.tags( elmID )[i];
        if( !obj || !obj.offsetParent ){
            continue;
        }
          // Find the element's offsetTop and offsetLeft relative to the BODY tag.
          objLeft   = obj.offsetLeft;
          objTop    = obj.offsetTop;
          objParent = obj.offsetParent;
          while( objParent.tagName.toUpperCase() != "BODY" )
          {
          objLeft  += objParent.offsetLeft;
          objTop   += objParent.offsetTop;
          objParent = objParent.offsetParent;
          }
          objHeight = obj.offsetHeight;
          objWidth = obj.offsetWidth;
          if(( overDiv.offsetLeft + overDiv.offsetWidth ) <= objLeft );
          else if(( overDiv.offsetTop + overDiv.offsetHeight ) <= objTop );
          else if( overDiv.offsetTop >= ( objTop + objHeight + obj.height ));
          else if( overDiv.offsetLeft >= ( objLeft + objWidth ));
          else
          {
          obj.style.visibility = "hidden";
          }
      }
    }
  }
     
  //unhides <select> and <applet> objects (for IE only)
  function showElement( elmID ){
    if( ie ){
      for( i = 0; i < document.all.tags( elmID ).length; i++ ){
        obj = document.all.tags( elmID )[i];
        if( !obj || !obj.offsetParent ){
            continue;
        }
        obj.style.visibility = "";
      }
    }
  }

  function closeTimePicker() {
    bShow=false;
    crossobj.visibility="hidden"
    showElement( 'SELECT' );
    showElement( 'APPLET' );
    currentCtl.src=imagePath + "timepicker.gif"
    
    curtain.hide();
  }

/*
  document.onkeypress = function hideTimePicker1 (event) { 
    if (event.keyCode==27){
      //if (!bShow){
      if (bShow){
        closeTimePicker();
      }
    }
  }
  
  */
  document.onkeypress = function(e) {
    var keynum;
    if (window.event) // IE
      keynum=window.event.keyCode;
    else // Netscape/Firefox/Opera
      keynum = e.keyCode; 
      
    if (keynum == 27) 
      if (bShow)
        closeTimePicker();
  }

  function isDigit(c) {
    
    return ((c=='0')||(c=='1')||(c=='2')||(c=='3')||(c=='4')||(c=='5')||(c=='6')||(c=='7')||(c=='8')||(c=='9'))
  }

  function isNumeric(n) {
    
    num = parseInt(n,10);

    return !isNaN(num);
  }

  function padZero(n) {
    v="";
    if (n<10){ 
      return ('0'+n);
    }
    else
    {
      return n;
    }
  }

   // if the hour is between 8 to 11, assume it is AM. if it is between 12-7, assume it is PM
  function amOrPm(hr) {
    if ((parseInt(hr,10)>=8) && (parseInt(hr)<=11))
         return "AM"
    else
        return "PM"
  }

    // validate whether the contents of a textbox represent a valid time.
   function validateDatePicker(ctl) {
        if (debugging) logger("validateDatePicker");
        var res=validateDatePicker2(ctl);
        if (res!=true)
            ctl.style.color="#FF0000";
        else
            ctl.style.color="#000000";
        return res;
   }

  // validate the time
  function validateDatePicker2(ctl) {
    if (debugging) logger("validateDatePicker2");
    t=ctl.value.toLowerCase();
    t=t.replace(" ","");
    t=t.replace(".",":");
    t=t.replace("-","");

    if ((isNumeric(t))&&(t.length==4))
    {
      t=t.charAt(0)+t.charAt(1)+":"+t.charAt(2)+t.charAt(3);
    }

    var t=new String(t);
    tl=t.length;

    if (tl==1 ) {
      if (isDigit(t)) {
        if (t=="0") 
            ctl.value="12:00 AM";
        else
            ctl.value=t+":00" +amOrPm(t);
      }
      else {
        return false;
      }
    }
    else if (tl==2) {
      if (isNumeric(t)) {
        if (parseInt(t,10)<13){
          if (t.charAt(1)!=":") {
            ctl.value= t + ':00' + amOrPm(t);
          }
          else {
            if (t.charAt(0)=="0")
                ctl.value="12:00 AM";
            else
                ctl.value= t + '00' + amOrPm(t);
          }
        }
        else if (parseInt(t,10)==24) {
          ctl.value= "12:00 AM";
        }
        else if (parseInt(t,10)<24) {
          if (t.charAt(1)!=":") {
            ctl.value= (t-12) + ':00 PM';
          } 
          else {
            ctl.value= (t-12) + '00 PM';
          }
        }
        else if (parseInt(t,10)<=60) {
          ctl.value= '0:'+padZero(t)+' AM';
        }
        else {
          ctl.value= '1:'+padZero(t%60)+' AM';
        }
      }
      else
           {
        if ((t.charAt(0)==":")&&(isDigit(t.charAt(1)))) {
          ctl.value = "0:" + padZero(parseInt(t.charAt(1),10)) + " AM";
        }
        else {
          return false;
        }
      }
    }
    else if (tl>=3) {

        // 3-digit time modification by MV, 5/2007
        if ((tl==3) && (!isNumeric(t))) return false;
        if ((tl==3) && (isNumeric(t))) {
            // time is in format, say 330, for 330 AM or PM
            var tHour=t.charAt(0);
            var tMin=t.charAt(1)+t.charAt(2);
            hr=parseInt(tHour,10);
            mn=parseInt(tMin,10);
            if (isNaN(mn)) mn=0; // e.g. if "7qq" is entered, this becomes 7:00PM
            if ((mn<0) || (mn>59))
                return false;
            if (hr==0) {
                hr=12;
                mode="AM";
            } else
                mode=amOrPm(tHour);            
            
            ctl.value=hr+":"+padZero(mn)+" "+mode;
            return true;
        }

      // now tl>3
      var arr = t.split(":");
      if (t.indexOf(":") > 0)
      {
        hr=parseInt(arr[0],10);
        mn=parseInt(arr[1],10);

        if (t.indexOf("PM")>0)
          mode="PM";
        else if (t.indexOf("AM")>0)
          mode="AM";
        else
          mode=amOrPm(hr);

        if (isNaN(hr)) {
          return false;
          hr=0;
        } else {
          if (hr>24) {
            return false;
          }
          else if (hr==24) {
            mode="AM";
            hr=0;
          }
          else if (hr>12) {
            mode="PM";
            hr-=12;
          }
        }
      
        if (isNaN(mn)) {
          mn=0;
        }
        else {
          if (mn>60) {
            mn=mn%60;
            hr+=1;
          }
        }
      } else {

        hr=parseInt(arr[0],10);

        if (isNaN(hr)) {
          return false;
          // hr=0;
        } else {
          if (hr>24) {
            return false;
          }
          else if (hr==24) {
            mode="AM";
            hr=0;
          }
          else if (hr>12) {
            mode="PM";
            hr-=12;
          }
        }

        mn = 0;
      }
      
      if (hr==24) {
        hr=0;
        mode="AM";
      }
      ctl.value=hr+":"+padZero(mn)+" "+mode;
    }
    return true;
  }

/**********************************************************************************************
  Curtain object & methods. Throw a translucent curtain on screen objects.
 **********************************************************************************************/

  var Curtain = function(id){
    document.write("<div id='"+id+"' style='z-index:8;visibility:hidden;background: #333; opacity: 0.10; filter: alpha(opacity=10); position: absolute; top:0; left:0; width: 100%; height: 100%;'>&nbsp;</div>");
    this.id=id;
  };
  
  Curtain.prototype.show = function () {
    this._scrn().style.visibility="visible";
    this._autoResize();
  }

  Curtain.prototype.hide = function() {
    this._scrn().style.visibility="hidden";
  }
  
  // Modal overlay screen element
  Curtain.prototype._scrn = function() {
      return document.getElementById(this.id);
/*      var ie=document.all;
      return (document.getElementById)?
        document.getElementById(this.id) : 
        ie? document.all.screener : document.screener;
*/        
  }
 

  // set gray overlay screener size to document size, including scrollbars
  Curtain.prototype._autoResize = function() {
    // try to set to 0, so that the overlay does not influence the document size. this may or may not work...
    // seems to work in firefox, but the height does not readjust in IE6.
    this._scrn().style.width=0;
    this._scrn().style.height=0;
    if (ie) {
        this._scrn().style.width=document.body.scrollWidth;
        this._scrn().style.height=document.body.scrollHeight;
    } else {
        this._scrn().style.width=document.body.scrollWidth;
        this._scrn().style.height=document.documentElement.scrollHeight;
    }
  }

  // capture window onresize, so that curtain can be resized. 
  window.onresize = function() { curtain._autoResize(); }
  
  curtain = new Curtain();

 
/**********************************************************************************************/
