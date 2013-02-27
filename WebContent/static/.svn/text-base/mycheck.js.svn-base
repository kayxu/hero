var isIE = false;
var isFF = false;
var isSa = false;

if ((navigator.userAgent.indexOf("MSIE")>0) && (parseInt(navigator.appVersion) >=4))isIE = true;
if(navigator.userAgent.indexOf("Firefox")>0)isFF = true;
if(navigator.userAgent.indexOf("Safari")>0)isSa = true; 


function isCc(s)
{
    var patrn = /^[A-Za-z0-9-_]+$/;
    if (!patrn.exec(s)) return false 
    return true
}


function isCo(s)
{
    var patrn = /^[A-Za-z0-9_]+$/;
    if (!patrn.exec(s)) return false 
    return true
}
function isCn(s)
{
    var patrn = /^[A-Za-z0-9]+$/;
    if (!patrn.exec(s)) return false 
    return true
}

function isFloatStore(s){
	var regex = /^[+|-]?\d*\.?\d*$/;
    if (!regex.exec(s)) return false 
    return true	
}

function isNumber(val)
{ 
	var patrn=/^\d+$/; 
	if(!isEmpty(val)){
		if (!patrn.exec(val)){
			return false;
		} else {
		    return true;
		}
	}
	
	return true;
} 

function isRealNum(val){
	var patrn=/^(^-?\d+$)$/; 
	if(!isEmpty(val)){
		if (!patrn.exec(val)){
			return false;
		} else {
		    return true;
		}
	}
	return true;	
}

function isMoney(val)
{ 
	var patrn=/^\d{1,8}$|\.\d{1,2}$/;
	if(!isEmpty(val)){
		if (!patrn.exec(val)){
			return false;
		} else {
		    return true;
		}
	}
	
	return true;
} 

function isSpace(s)
{
   var patrn = /^\S+$/;
   if (!patrn.exec(s)) return false 
    return true    
}

function isPasswd(s) 
{ 
    var patrn=/^(\w){4,20}$/; 
    if (!patrn.exec(s)) return false 
    return true 
} 

function isTel(s) 
{ 
    var patrn = /^[0-9-,]+$/;
    if (!patrn.exec(s)) return false 
    return true 
} 
function isEmpty (str) { 
if ((str==null)||(str.length==0)) return true; 
else return(false); 
}

function validateForm(add){    
	var alertMessage = "";
	var elArr = eval(document.forms[0].name).elements;	
	var strYY = "";
	var strMM = "";
	var strDD = "";
	for(var i = 0; i < elArr.length; i++){	    
		if(elArr[i].id){		   
		  var aryList = (elArr[i].id).split(',');		
		  var aryListTitle = (elArr[i].title).split(',');		 
		  for(var j=0; j<aryList.length; j++){			           
		  	  if(aryList[j].substring(0,2) == "NU"){			  	       	   
		  	  	if(elArr[i].value==""){	  	 		  	  	    
		  	  		alertMessage =  alertMessage + eval(aryListTitle[j]) +"\n" ;
		  	  	}
		  	  }
		  	  if(aryList[j].substring(0,2) == "CO"){		  	    
		  	  	if(elArr[i].value !=""){		  	  	   
    		  	  	if(isCo(elArr[i].value)==false){       		  	  	     		  	  	   
    		  	  		alertMessage = alertMessage + eval(aryListTitle[j])+ "\n";    		  	  		
    		  	  	}
		  		 }
		  	  }
		  	  if(aryList[j].substring(0,2) == "CN"){		  	    
		  	  	if(elArr[i].value !=""){		  	  	   
    		  	  	if(isCn(elArr[i].value)==false){ 
    		  	  		alertMessage = alertMessage + eval(aryListTitle[j])+ "\n";    		  	  		
    		  	  	}
		  		 }
		  	  }
		  	  if(aryList[j].substring(0,2) == "TL"){		  	    
		  	  	if(elArr[i].value !=""){		  	  	   
    		  	  	if(isTel(elArr[i].value)==false){       		  	  	     		  	  	   
    		  	  		alertMessage = alertMessage + eval(aryListTitle[j])+ "\n";    		  	  		
    		  	  	}
		  		 }
		  	  }	
		  	  if(aryList[j].substring(0,2) == "SP"){		  	    
		  	  	if(elArr[i].value !=""){		  	  	   
    		  	  	if(isSpace(elArr[i].value)==false){       		  	  	     		  	  	   
    		  	  		alertMessage = alertMessage + eval(aryListTitle[j])+ "\n";    		  	  		
    		  	  	}
		  		 }
		  	  }	  	  
		  	  if(aryList[j].substring(0,2) == "LE"){
		  	  	 if(elArr[i].value !=""){
		  	       var eleLength = elArr[i].value.length;
		  	       var ordLength = aryList[j].substring(3,100);		  	    
		  	  	   if( eleLength > ordLength){		  	  	    
		  	  	      alertMessage = alertMessage + eval(aryListTitle[j]) + "\n";		  	  		
		  	  	   }
		  		  }
		  	  }
		   }
		}
	}	
	if(add == "true"){
	     alertMessage =  addMessage(alertMessage);
	}	
	if(alertMessage != ""){	 
	   
		alert(alertMessage);
		return false;
	}else{
		return true;
	}
}
function checkboxChecked(checkboxName)
{     
    var count=0;
    var formname = document.forms[0].name;
	if(eval("document." + formname + "." + checkboxName)!=null)
	{
		if(eval("document." + formname + "." + checkboxName).length!=null)
		{
			for (var i = 0; i < eval("document." + formname + "." + checkboxName).length; i++)       
			{
				if (eval("document." + formname + "." + checkboxName)[i].checked == true)
				{
					count=count+1;
				}
			}
		}
		else
		{    
			if (eval("document." + formname + "." + checkboxName).checked == true)
			{
				count=count+1;
			}
		} 
	}
    return count;
}

function checkboxEmpty (checkboxName)
{     
    var checkbox = document.all(checkboxName);
	if(checkbox!=null)
	{
		if( checkbox.length!=null)
		{
			for (var i = 0; i < checkbox.length; i++)       
			{
				checkbox[i].checked = false;
			}
		}
		else
			checkbox.checked = false;
	}
}
function checknum(obj)
{
	if (isNaN(obj.value))
	{
		alert("???????????");
		obj.value="";
		//obj.focus();
		return false;
	}
	else
	{
		return true;
	}
}
function isPlus(obj)
{
	obj.style.imeMode   =   "disabled"; 
	var retNum = obj.value;
	var tempVal = obj.value;
	var tempNum = "";
	var flag = false;
	if(true == isNumber(obj.value))
	{
		for(var i=0;i<tempVal.length;i++)
		{
			tempNum = tempVal.substring(0,1);
			if(tempNum=="0" || tempNum==" ")
			{
				tempVal = tempVal.substring(1,tempVal.length);
				flag = true;
			}
		}
		if(true==flag)retNum = tempVal;
		obj.value = retNum;
		if(obj.value>0)
		return true;
		else 
		return false;
	}
	else
	{		
		return false;
	}
}

function isPlus1(val)
{
	var retNum = val;
	var tempVal = val;
	var tempNum = "";
	var flag = false;
	if(true == isNumber(val))
	{
		for(var i=0;i<tempVal.length;i++)
		{
			tempNum = tempVal.substring(0,1);
			if(tempNum=="0" || tempNum==" ")
			{
				tempVal = tempVal.substring(1,tempVal.length);
				flag = true;
			}
		}
		if(true==flag)retNum = tempVal;
		val = retNum;
		if(val>0)
		return true;
		else 
		return false;
	}
	else
	{		
		return false;
	}
}

function onlyPlusNumber(e,obj)
{
	obj.style.imeMode   =   "disabled"; 
	gernalCheckNumber(e,1);
    var tempVal = obj.value;
	for(var i=0;i<tempVal.length;i++)
	{
		tempNum = tempVal.substring(0,1);
		if(tempNum=="0" || tempNum==" ")
		{
			tempVal = tempVal.substring(1,tempVal.length);
			break;
		}
	}
	obj.value = tempVal;
} 

function onlyPlusNumberPoint(e,obj)
{
	obj.style.imeMode   =   "disabled"; 
	gernalCheckNumber(e,2);
    var tempVal = obj.value;
	if(tempVal.length>=2)
	{
		for(var i=0;i<tempVal.length;i++)
		{
			tempNum = tempVal.substring(0,1);
			tempNum1 = tempVal.substring(1,2);			
			if(tempNum=="0" && tempNum1!=".")
			{
				tempVal = tempVal.substring(1,tempVal.length);
				break;
			}
		}
		obj.value = tempVal;
	}
} 

function charlength(xx){
	var x = xx.length;     
	var y = 0;
	for ( var i = 0; i < x; i++){
		if(xx.charCodeAt(i)<0 || xx.charCodeAt(i)>255){
			y += 2;
		} else {
			y += 1;
		}
    }
    return y;
}
function onlyNumber(e)
{
	gernalCheckNumber(e,1);
} 
function onlyNumberPoint(e)
{
	gernalCheckNumber(e,2);
} 
function onlyFNumberPoint(e){
	gernalCheckNumber(e,5);
}

function onlyNumberColon(e,obj)
{
	gernalCheckNumber(e,3);
	var tempVal = obj.value;
	for(var i=0;i<tempVal.length;i++)
	{
		tempNum = tempVal.substring(0,1);
		if(tempNum=="0" || tempNum==" ")
		{
			tempVal = tempVal.substring(1,tempVal.length);
			break;
		}
	}
	obj.value = tempVal;
} 
function gernalCheckNumber(e,flag)
{
	var isIE = false;
	var isFF = false;
	var isSa = false;
	if ((navigator.userAgent.indexOf("MSIE")>0) && (parseInt(navigator.appVersion) >=4))isIE = true;
	if(navigator.userAgent.indexOf("Firefox")>0)isFF = true;
	if(navigator.userAgent.indexOf("Safari")>0)isSa = true;
	var key;
    iKeyCode = window.event?e.keyCode:e.which;
    var blFlag  = false;
    if(flag==1)
    {
    	blFlag = (((iKeyCode >= 48) && (iKeyCode <= 57)) || (iKeyCode == 13) ||   (iKeyCode == 37) || (iKeyCode == 8));
    }
    else  if(flag==2)
    {
    	blFlag = (((iKeyCode >= 48) && (iKeyCode <= 57)) || (iKeyCode == 13) || (iKeyCode == 46) ||  (iKeyCode == 37)  || (iKeyCode == 8));	
    }
    else  if(flag==3)
    {
    	blFlag = (((iKeyCode >= 48) && (iKeyCode <= 57)) || (iKeyCode == 58) ||(iKeyCode == 32) || (iKeyCode == 13) || (iKeyCode == 46) ||  (iKeyCode == 37)|| (iKeyCode == 8));	
    }
    if(flag==5)
    {
    	blFlag = (((iKeyCode >= 48) && (iKeyCode <= 57)) || (iKeyCode == 13) ||   (iKeyCode == 37) || (iKeyCode == 8) || (iKeyCode == 45));
    }    
    if(!blFlag)
    {    
        if (isIE)
        {
            e.returnValue=false;
        }
        else
        {
            e.preventDefault();
        }
    }
}
function replaceSpace(str)
{
	str = str.replace(" ","");
	str = str.replace("??","");
	return str;
}

function valToUpperCase(obj)
{
	if(obj!=undefined)
	{
		obj.style.imeMode   =   "disabled"; 
		obj.value = replaceSpace(obj.value);
		obj.value = obj.value.toUpperCase();
	}
	if(event.keyCode == 9){
		obj.select();
	}
}

function  checkIsFloat(obj){   
  	if(obj!=undefined)
	{
		obj.style.imeMode   =   "disabled"; 
	}
    var nc = event.keyCode;   
    if((nc>=48) && (nc<=57)){   
    }else if(nc==46){   
        var s=obj.value;   
        for(var i=0;i<s.length;i++){   
            if(s.charAt(i)=='.'){   
               event.keyCode=0;   return;   
            } 
        }   
    }else{   
        event.keyCode=0;return;   
    }   
}   


function newValToInt(obj)
{
	if(obj!=undefined)
	{
		if(obj.value=="")
		obj.value = "";
	}
}

function newvalue(obj){
var value=obj.value;
var value2=value.split(":");
 var temp="";
  for(var i=0;i<value2.length-1;i++){
  if(value2[i+1]!=""){
   temp=temp+value2[i]+":";
   }else{
    temp=temp+value2[i];
   }
  }
  if(value2[value2.length-1]!=""){
  temp=temp+value2[value2.length-1];
  }
  	obj.value = temp;
}


function focusNext(ipName,idx){
	if(event.keyCode==13){
		var objIp = document.getElementsByName(ipName);
	    idx = (idx+1);
		if(idx<objIp.length){
			objIp[idx].focus();
		}
	}
}

function numberOnly(obj){
	var val = obj.value;
	var tempString = "";
	for(var i=0;i<val.length;i++){
		var tmp = val.charAt(i);
		if(isNumber(tmp)){
			tempString = tempString+ tmp;
		}
	}
	while(tempString.charAt(0)=='0'){
		tempString = tempString.substring(1,tempString.length);
	}
	obj.value = tempString;
}



function  sumColByNames(arrColNames){
	var arrColRes = new Array();

	for(var i=0;i<arrColNames.length;i++){
		var objCol = document.getElementsByName(arrColNames[i]);
		arrColRes[i] = 0;
		for(var j=0;j<objCol.length;j++){
			if(objCol[j].value!=""){
				arrColRes[i] = arrColRes[i] + roundAmount(objCol[j].value);
			}
		}
	}
	return arrColRes;
}

function genAmount(price,piece)
{
	//if(!isNumber(price)){
	//price = 0;
	//}
	var amount = roundAmount(price*piece);
	//alert(">>>>>>>amount"+amount+"=price"+price+"*piece"+piece);
	return amount;
	}
function   roundAmount(n){   
  var s = n * 1;
  s = s.toFixed(2);
  return parseFloat(s);
	}

function formatCash(cash) 
{ 
	return parseFloat(cash).toLocaleString();
}

function ltrim(s){ 
return s.replace( /^\s*/, ""); 
} 
function rtrim(s){ 
return s.replace( /\s*$/, ""); 
} 
function trim(s){ 
return rtrim(ltrim(s)); 
}
function checkLength6(obj){

if(obj.value.length>6){
obj.value=obj.value.substring(0,6);
}

}
function checkLength9(obj){

if(obj.value.length>9){
obj.value=obj.value.substring(0,9);
}

}
function checkLength8(obj){

if(obj.value.length>8){
obj.value=obj.value.substring(0,8);
}

}

function genNumRange(obj,maxNum){
   	var val = obj.value;	
	var patrn=/^[0-9]*[0-9][0-9]*$/; 	
	if(!isEmpty(val)){
		if(Number(val) <= Number(maxNum)){
			if(patrn.test(val)){
				return true;	  
			}
		}
	}
	return false;	
}
function getChnLen(param1)
{
	l =0;
	for (var i=0;i<param1.length;i++)
	{
		if (param1.charCodeAt(i)<128)
			l=l+1;
		else
			l=l+2;
	}
	return l;
}

function genNum(obj)
{     
  var val =  obj.value;
      var tempString="";
      var count = 0;
      for (var i = 0; i < val.length; i++) {
        var tmp = val.charAt(i);
        if ( tmp == '0' || tmp == '1' || tmp == '2' || tmp == '3' || tmp == '4'
           || tmp == '5' || tmp == '6' || tmp == '7' || tmp == '8' || tmp == '9') {
             if(tmp == '.'){
	  				count++;
	  		     if(count>1){
	  		     	break;
	  		     }
	  		}
	  		 tempString = tempString +  tmp;
         }  
        
      }
      var temp=tempString;
	  var tempNum = temp.substring(0,0);
	  if(tempNum==" ")
	  {
		   val = temp.substring(1,temp.length);
	  }	else{
	  	val = tempString;
	  }	  
  obj.value = val;
}
