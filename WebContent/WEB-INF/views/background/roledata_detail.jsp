<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<SCRIPT >	
	function change3(){
		alert("暂未实现");
	}
	function change5(){
		alert("暂未实现");	
	}
	//天梯
	var ladderTime;
	function changeLadder(){
		var date = new Date();
		var data=$("#ladder").val();
		var arr1 = data.split(";");
		ladderTime=arr1[3];
		date.setTime(ladderTime);
		var time = formatDate(" MM-DD h:m:s", date);
		var html="";
		html+='<table class="table">';
		html+='<tbody>';
		html+='<tr>';
		html+='<td>状态：<input id="ladder_id"  value="'+arr1[0]+'" readonly="readonly"/></td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td>免费次数：<input id="ladder_free"  value="'+arr1[1]+'" /></td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td>付费次数：<input id="ladder_charge"  value="'+arr1[2]+'" /></td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td>时间：<input id="ladder_time" value="'+time+'" readonly="readonly"</td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td>购买次数：<input id="ladder_buy" value="'+arr1[4]+'"</td>';
		html+='</tr>';
		html+='</tbody>';
		html+='</table>';
		$( "#dialog-ladder" ).html(html);
		$( "#dialog-ladder" ).dialog( "open" );
	}
	//战役时间
	var campTime;
	function changeCamp(){
		var date = new Date();
		var data=$("#camp").val();
		var arr1 = data.split(";");
		campTime=arr1[1];
		date.setTime(campTime);
		var time = formatDate(" MM-DD h:m:s", date);
		var html="";
		html+='<table class="table"><tbody>';
		html+='<tr>';
		html+='<td>进度：<input id="camp_id"  value="'+arr1[0]+'" /></td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td>时间：<input id="camp_time" value="'+time+'" readonly="readonly"</td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td>免费次数：<input id="camp_free"  value="'+arr1[2]+'" /></td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td>付费次数：<input id="camp_charge"  value="'+arr1[3]+'" /></td>';
		html+='</tr>';
		html+='</tbody></table>';
		$( "#dialog-camp" ).html(html);
		$( "#dialog-camp" ).dialog( "open" );
	}
	//已经接受的任务
	var questId;
	var questStatus;
	function changeQuest(){
		questId=new Array();
		questStatus=new Array();
		var data=$("#acceptedQuests").val();
		var arr1 = data.split(";");
		var html="";
		html+='<table class="table"><tbody>';
		html+="<thead><tr><th>id</th><th>状态</th></tr></thead>";
		$.each(arr1, function(key, value) {
			var arr2 = value.split(",");
			questId.push(arr2[0]);
			questStatus.push(arr2[1]);
			html+='<tr>';
			html+='<td><input id="'+("questId"+arr2[0])+'" value="'+arr2[0]+'"/></td>';
			html+='<td><select id="'+("questValue"+arr2[0])+'" ><option value=\'1\'>已接</option><option value=\'2\'>未完成</option><option value=\'3\'>完成</option></select></td>';
			html+='</tr>';
        });
		html+='</tbody></table>';
		$( "#dialog-quest" ).html(html);
		$.each(arr1, function(key, value) {
			var arr2 = value.split(",");
			$("#questValue"+arr2[0]).val(arr2[1]);//#quest5
			$("#questValue"+arr2[0]).change(function(){ 
		  		  //alert($("#quest"+arr2[0]).val());
		  		//alert(arr2[0]+","+arr2[1]);
				questStatus[key]=$("#questValue"+arr2[0]).val();
				//alert(questId[key]+","+questStatus[key]);
		  	});
		});
		$( "#dialog-quest" ).dialog( "open" );
	}
	
	function submits(url) {
		//alert(url);
		var forms = document.myForm;
		forms.action = url;
		forms.submit();
	}
	
	function submits1(url) {
		//alert(url);
		var forms = document.myForm1;
		forms.action = url;
		forms.submit();
	}

	function openDialog(url) {
		digStr = "dialogHeight:400px;dialogWidth:600px;center:yes"
		var ReturnValue = window.showModalDialog(url, "", digStr)
		//alert("你在B.html中的文本框中输入了：" + ReturnValue);
	}
	function setTime(id) {
		var t = $("#" + id).val();
		var date = new Date();
		date.setTime(t);
		var time = formatDate(" MM-DD h:m:s", date);
		$("#" + id).val(time);
	}
	$(document).ready(function() {
		setTime("lastLoginTim");
		setTime("leaveTim");
		//alert(time);

		$("#dialog-camp").dialog({
			autoOpen : false,
			height : 300,
			width : 350,
			modal : true,
			buttons : {
				"OK" : function() {
					//将修改后的数据重组
					var value1=$( "#camp_id" ).val();
					var value2=campTime;
					var value3=$( "#camp_free" ).val();
					var value4=$( "#camp_charge" ).val();
					//alert(value1+";"+value2+";"+value3+";"+value4);
					$("#camp").val(value1+";"+value2+";"+value3+";"+value4);
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
			}
		});
		$("#dialog-ladder").dialog({
			autoOpen : false,
			height : 300,
			width : 500,
			modal : true,
			buttons : {
				"OK" : function() {
					//将修改后的数据重组
					var value1=$( "#ladder_id" ).val();
					var value2=$( "#ladder_free" ).val();
					var value3=$( "#ladder_charge" ).val();
					var value4=ladderTime;
					var value5=$( "#ladder_buy" ).val();
					//alert(value1+";"+value2+";"+value3+";"+value4);
					$("#ladder").val(value1+";"+value2+";"+value3+";"+value4+";"+value5);
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
			}
		});
		$("#dialog-quest").dialog({
			autoOpen : false,
			height : 300,
			width : 500,
			modal : true,
			buttons : {
				"OK" : function() {
					//将修改后的数据重组
					var str="";
					$.each(questId, function(key, value) {
						var id=$("#questId"+questId[key]).val();
						str+=id+","+questStatus[key];
						if(key<questId.length-1){
							str+=";";
						}
					});
					//alert(str);
					var value1=$( "#acceptedQuests" ).val(str);
					//alert(value1+";"+value2+";"+value3+";"+value4);
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
			}
		});
	});
	
	 function shownoall(num)//这个是关闭层
	    {
	    var divv1="doing"+num;
	    var divv2="divLogin"+num;
	    document.getElementById(divv1).style.display="none";
	    document.getElementById(divv2).style.display="none";
	    }
	    
	    
	        function getRange()                      //得到屏幕的大小
	        {
	              var top     = document.body.scrollTop;
	              var left    = document.body.scrollLeft;
	              var height  = document.body.clientHeight;
	              var width   = document.body.clientWidth;

	              if (top==0 && left==0 && height==0 && width==0) 
	              {
	                top     = document.documentElement.scrollTop;
	                left    = document.documentElement.scrollLeft;
	                height  = document.documentElement.clientHeight;
	                width   = document.documentElement.clientWidth;
	              }
	              return  {top:top  ,left:left ,height:height ,width:width } ;
	        }
	        
	        function showfloatall(num)//这个是显示要弹出的层
		    {
		      var range = getRange();
		          var divvv1="doing"+num;
		           var divvv2="divLogin"+num;
		            document.getElementById(divvv1).style.width = range.width + "px";
		            document.getElementById(divvv1).style.height = range.height + "px";
		            document.getElementById(divvv1).style.display = "block";
		            document.getElementById(divvv2).style.display="block";
		            
		            var obj1 = document.getElementById('pro');//开启
		        	var obj2 = document.getElementById('equ');//隐藏
		        	obj1.style.display = "none";
		        	obj2.style.display = "none";
		        	var title1 = "pro_title";
		        	var title2 = "equ_title";
		        	var obj1_t = document.getElementById(title1);
		        	var obj2_t = document.getElementById(title2);
		        	obj2_t.style.color = "#1E5494";
		        	obj1_t.style.color = "#1E5494";
		        	
		        	 var addButton = document.getElementById("addbutton");//开启
				      addButton.style.display = "none";
		    }

	        
	        function view(id1, id2) {
		          var divvv2="divLogin6";
		          document.getElementById(divvv2).style.height = "600px";
		        
	        	var obj1 = document.getElementById(id1);//开启
	        	var title1 = id1 + "_title";
	        	var obj2 = document.getElementById(id2);//隐藏
	        	var title2 = id2 + "_title";
	        	var obj1_t = document.getElementById(title1);
	        	var obj2_t = document.getElementById(title2);
	        	if (obj1.style.display == "block") {
	        		obj2.style.display = "none";
	        		obj2_t.style.color = "#FF0000";
	        		obj1_t.style.color = "#1E5494";
	        	} else {
	        		obj2.style.display = "none";
	        		obj1.style.display = "block";
	        		obj1_t.style.color = "#FF0000";
	        		obj2_t.style.color = "#1E5494";
	        	}
	        	 var addButton = document.getElementById("addbutton");//开启
			      addButton.style.display = "block";
	        	 //alert();
	        }
	        	
	        	function addRow(id1,id2) {
	        		//alert(id1);
	        		//alert(id2);
	        		var obj1 = document.getElementById(id1);//开启
	        		var obj2 = document.getElementById(id2);
	        		if(obj2.style.display == 'none'){
	        			var trs = obj1.insertRow();
	        			$("#" + id1 + " tr:last").after('<tr><td ><form:select style="width:100px" path="equselect" items="${equselect}" itemLabel="name" itemValue="id" id="eid" name="eid" ></form:select></td><td ><input id="epl" maxlength="2"  onkeyup="this.value=this.value.replace(/\D/g,\'\')" name="epl" value="1" style="width:80px"/></td><td ><input id="esl" maxlength="2"  onkeyup="this.value=this.value.replace(/\D/g,\'\')" name="esl" value="0" style="width:80px"/></td><td ><input id="eqc" maxlength="2"  onkeyup="this.value=this.value.replace(/\D/g,\'\')" name="eqc" value="1" style="width:80px"/></td><td><a href="#" onclick="{if(confirm(\'确定要删除这个装备吗?\')) {deleteCurrentRow(this); }else {}}">删除</a></td></tr>');
	        		}
	        		if(obj1.style.display == 'none'){
	        			$("#" + id2 + " tr:last").after('<tr><td ><form:select style="width:100px" path="propselect" items="${propselect}" itemLabel="name" itemValue="id" id="pid" name="pid" ></form:select></td><td ><input id="pcount" maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,\'\')" name="pcount" value="1" style="width:80px"/></td><td><a href="#" onclick="{if(confirm(\'确定要删除这个道具吗?\')) {deleteCurrentRow(this); }else {}}">删除</a></td></tr>');
	        		}
	        	}
	        	
	        	   function deleteCurrentRow(obj){
	        		   var tr=obj.parentNode.parentNode;
	        		   var tbody=tr.parentNode;
	        		   tbody.removeChild(tr);
	        		     }
	        	   
</SCRIPT>
</head>
<body>
	<form method="post" name="myForm">
		<input id="types" name="types"
			value="<%=request.getParameter("types")%>" type="hidden" /> <input
			id="symbol" name="symbol" value="<%=request.getParameter("symbol")%>"
			type="hidden" /> <input id="numerical" name="numerical"
			value="<%=request.getParameter("numerical")%>" type="hidden" /> <input
			id="pages" name="pages" value="<%=request.getParameter("pages")%>"
			type="hidden" />
		<table class=" table table-hover table-condensed">
			<%
				String test = "123";
			%>
			<tbody>
				<tr>
					<td>ID<span></span></td>
					<td><input id="userid" name="userid" value="${user.userid}"
						readonly="readonly" /></td>
				</tr>
				<tr>
					<td>NAME<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="name" name="name" value="${user.name}"
								readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.name}</c:if></td>

					<td>性别<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="sex" name="sex" value="${user.sex}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.sex}</c:if></td>
				</tr>
				<tr>
					<td>头像<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="faction" name="faction" value="${user.faction}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.faction}</c:if></td>

					<td>等级<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="level" name="level" value="${user.level}" readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.level}</c:if></td>
				</tr>
				<tr>
					<td>经验<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="exp" name="exp" value="${user.exp}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.exp}</c:if></td>

					<td>金钱<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="gameMoney" name="gameMoney" value="${user.gameMoney}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.gameMoney}</c:if>
					</td>
				</tr>
				<tr>
					<td>钻石<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="joyMoney" name="joyMoney" value="${user.joyMoney}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.joyMoney}</c:if></td>

					<td>上次登录时间<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="lastLoginTim" name="lastLoginTim"
								value="${user.lastLoginTime}" readonly="readonly" />
							
							<input id="lastLoginTime" name="lastLoginTime"
								value="${user.lastLoginTime}" type="hidden" />	
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.lastLoginTime}</c:if>
					</td>
				</tr>
				<tr>
					<td>在线时间<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="onlineTime" name="onlineTime"
								value="${user.onlineTime}" readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.onlineTime}</c:if>
					</td>

					<td>上次退出时间<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="leaveTim" name="leaveTim" value="${user.leaveTime}"
								readonly="readonly" />
								<input id="leaveTime" name="leaveTime" value="${user.leaveTime}"
								type="hidden"/>
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.leaveTime}</c:if>
					</td>
				</tr>
				<tr>
					<td>活跃时间<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="activeEndTime" name="activeEndTime"
								value="${user.activeEndTime}" readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.activeEndTime}</c:if>
					</td>

					<td>单日总在线时间<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="intradayOnlineTime" name="intradayOnlineTime"
								value="${user.intradayOnlineTime}" readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.intradayOnlineTime}</c:if>
					</td>
				</tr>
				<tr>
					<td>功勋<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="award" name="award" value="${user.award}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.award}</c:if></td>

					<td>签名<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="note" name="note" value="${user.note}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.note}</c:if></td>
				</tr>
				<tr>
					<td>爵位<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="cityLevel" name="cityLevel" value="${user.cityLevel}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.cityLevel}</c:if>
					</td>

					<td>荣耀<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="credit" name="credit" value="${user.credit}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.credit}</c:if></td>
				</tr>

				<tr>
					<td>第二次刷将<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="heroRefresh2" name="heroRefresh2"
								value="${user.heroRefresh2}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.heroRefresh2}</c:if>
					</td>
					<td>战斗次数<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="warTimes" name="warTimes" value="${user.warTimes}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.warTimes}</c:if></td>
				</tr>
				<tr>
					<td>战役记录<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="camp" name="camp" value="${user.camp} "
								readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.camp}</c:if> <input
						type="button" onclick="changeCamp();" value="修改" /></td>

					<td>战役ID<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="campId" name="campId" value="${user.campId}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.campId}</c:if></td>
				</tr>
				<tr>
					<td>天梯记录<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="ladder" name="ladder" value="${user.ladder}"
								readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.ladder}</c:if><input
						type="button" onclick="changeLadder();" value="修改" /></td>

					<td>天梯进度<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="ladderId" name="ladderId" value="${user.ladderId}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.ladderId}</c:if></td>
				</tr>
				<tr>
					<td>籍贯<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="nativeId" name="nativeId" value="${user.nativeId}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.nativeId}</c:if></td>

					<td>是否修改名字<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="isChangName" name="isChangName"
								value="${user.isChangName}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.isChangName}</c:if>
					</td>
				</tr>
				<tr>
					<td>积分<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="score" name="score" value="${user.score}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.score}</c:if></td>

					<td>筹码<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="chip" name="chip" value="${user.chip}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.chip}</c:if></td>
				</tr>
				<tr>
					<td>弓兵<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="archerEqu" name="archerEqu" value="${user.archerEqu}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.archerEqu}</c:if>
					</td>

					<td>步兵<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="infantryEqu" name="infantryEqu"
								value="${user.infantryEqu}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.infantryEqu}</c:if>
					</td>
				</tr>
				<tr>
					<td>骑兵<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="cavalryEqu" name="cavalryEqu"
								value="${user.cavalryEqu}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.cavalryEqu}</c:if>
					</td>

					<td>特种兵<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="specialArms" name="specialArms"
								value="${user.specialArms}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.specialArms}</c:if>
					</td>
				</tr>
				<tr>
					<td>通天塔最大进度<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="ladderMax" name="ladderMax" value="${user.ladderMax}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.ladderMax}</c:if>
					</td>

					<td>排名<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="arenaId" name="arenaId" value="${user.arenaId}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.arenaId}</c:if></td>
				</tr>
				<tr>
					<td>连胜次数<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="arenaKill" name="arenaKill" value="${user.arenaKill}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.arenaKill}</c:if>
					</td>

					<td>CD时间<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="arenaTime" name="arenaTime" value="${user.arenaTime}"
								readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.arenaTime}</c:if>
					</td>
				</tr>
				<tr>
					<td>附属成数量<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="cityNum" name="cityNum" value="${user.cityNum}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.cityNum}</c:if></td>

					<td>军功<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="militaryMedals" name="militaryMedals"
								value="${user.militaryMedals}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.militaryMedals}</c:if>
					</td>
				</tr>
				<tr>
					<td>已完成任务<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="completedQuests" name="completedQuests"
								value="${user.completedQuests}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.completedQuests}</c:if>
						<input type="button" onclick="change3();" value="查看" /></td>

					<td>接受任务<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="acceptedQuests" name="acceptedQuests"
								value="${user.acceptedQuests}" readonly="readonly" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.acceptedQuests}</c:if>
						<input type="button" onclick="changeQuest();" value="修改" /></td>
				</tr>
				<tr>
					<td>每日任务<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="dailyQuests" name="dailyQuests"
								value="${user.dailyQuests}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.dailyQuests}</c:if>
						<input type="button" onclick="change5();" value="查看" /></td>

					<td>政绩<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="achieve" name="achieve" value="${user.achieve}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.achieve}</c:if></td>
				</tr>
				<tr>
					<td>职位<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="title" name="title" value="${user.title}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.title}</c:if></td>

					<td>第一次刷将<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="heroRefresh1" name="heroRefresh1"
								value="${user.heroRefresh1}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.heroRefresh1}</c:if>
					</td>
				</tr>
				<tr>
					<td>fightInfo<span></span></td>
					<td><c:if test="${user.attriPolitics == 0}">
							<input id="fightInfo" name="fightInfo" value="${user.fightInfo}" />
						</c:if> <c:if test="${user.attriPolitics == 1}">${user.fightInfo}</c:if>
					</td>

					<td><span></span></td>
					<td></td>
				</tr>
				<tr>
				
				</tr>
				
				<tr>
					<td><a href="#"
					onclick="showfloatall(6);">背包详细</a>
					</td>
					<td><textarea rows="8" cols="100%" id="playerCells"
							name="playerCells" readonly="readonly">${user.playerCells}</textarea>
					</td>
					<td></td>
					<td></td>
				</tr>

			</tbody>
		</table>
		<table class="table">
			<tbody>
				<tr>
					<td></td>
					<td><input type="button"
						onclick="submits('<%=request.getContextPath()%>/roledata.do');"
						value="返回" /></td>
					<!--onclick="submits('<%=request.getContextPath()%>/roledata.do?method=item&id=<c:out value="${user.userid}"/>');"  -->
					<td><c:if test="${user.attriPolitics == 0}">
							<input type="button"
								onclick="submits('<%=request.getContextPath()%>/roledata.do?method=save&id=${user.userid}');"
								value="修改" />
						</c:if></td>
				</tr>
			</tbody>
		</table>
		<div id="dialog-camp" title="战役进度"></div>
		<div id="dialog-ladder" title="天梯进度"></div>
		<div id="dialog-quest" title="已经接受的任务"></div>
	</form>
	<form method="post" name="myForm1">
	<input id="types" name="types"
			value="<%=request.getParameter("types")%>" type="hidden" /> <input
			id="symbol" name="symbol" value="<%=request.getParameter("symbol")%>"
			type="hidden" /> <input id="numerical" name="numerical"
			value="<%=request.getParameter("numerical")%>" type="hidden" /> <input
			id="pages" name="pages" value="<%=request.getParameter("pages")%>"
			type="hidden" />
		 <div id="doing6"  style="filter:alpha(opacity=50);-moz-opacity:0.3;opacity:0.3;background-color:#cccccc; display:none;width:100%;height:100%;z-index:1000;position: absolute;left:0;top:0;overflow: hidden;">
    </div>    
    <!--加一个登录层  读取专业信息--> 
    <div id="divLogin6"  style="border:solid 8px #74B1D6;display:none;background:#fff;width:800px;z-index:1001;position: absolute;top:50%; left:50%;margin:-300px 0 0 -400px;overflow:auto">
            <table class="table">
			<tbody>
				<tr>
					<td>id</td>
					<td><input id="userid" name="userid" value="${user.userid}"
						readonly="readonly" /></td>
					<td>name</td>
					<td><input id="name" name="name" value="${user.name}"
						readonly="readonly" /></td>
				</tr>
				<tr>
					<td><a href="#" onclick="view('pro','equ');" id="pro_title">道具</a>
					</td>
					<td><a href="#" onclick="view('equ','pro');" id="equ_title">装备</a>
					</td>
					<td><input id="addbutton" style="display:none;" type="button" onclick="addRow('equ','pro')"
						value="新 增" /></td>
					<td><input type="button" onclick="shownoall(6)"
						value="关 闭" /></td>
				</tr>
			</tbody>
		</table>
		<table class="table" id="pro" style="display:none">
			<tr>
				<td>名称</td>
				<td>数量</td>
			</tr>
			<c:forEach items="${pcells}" var="prop">
				<tr>
					<td style="width:100px"><c:out value="${prop.prototype.name}" />
					<input id="pid" name="pid" value="${prop.prototype.id}" type="hidden"/>
					</td>
					<td style="width:80px"><input id="pcount" name="pcount" maxlength=5 value="${prop.num}" style="width:80px" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
					<td style="width:40px"><a href="#" onclick="{if(confirm('确定要删除这个道具吗?')) {deleteCurrentRow(this); }else {}}">删除</a></td>
				</tr>
			</c:forEach>
		</table>
		<table class="table" id="equ" style="display:none">
			<tr>
				<td>名称</td>
				<td>装备等级</td>
				<td>强化等级</td>
				<td>品质</td>
			</tr>
			<c:forEach items="${ecells}" var="equ">
				<tr>
					<td style="width:100px"><c:out value="${equ.prototype.name}" /> <input id="eid"
						name="eid" value="${equ.prototype.prototype.equipmentId}"
						type="hidden" /></td>
					<td style="width:80px"><input id="epl" name="epl" size="5" maxlength=2  onkeyup="this.value=this.value.replace(/\D/g,'')"
						value="${equ.prototype.prototype.equipmentLevel}" style="width:80px" /></td>
					<td style="width:80px"><input id="esl" name="esl" size="5" maxlength=2 
						value="${equ.prototype.prototype.strengthenLevel}" style="width:80px" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
					<td style="width:80px"><input id="eqc" name="eqc" size="5" maxlength=2 
						value="${equ.prototype.prototype.qualityColor}" style="width:80px" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
					<td style="width:40px"><a href="#" onclick="{if(confirm('确定要删除这个装备吗?')) {deleteCurrentRow(this); }else {}}">删除</a></td>
				</tr>
				<tr></tr>
			</c:forEach>
		</table>
		<table class="table">
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td><c:if test="${user.attriPolitics == 0}">
						<input type="button"
							onclick="submits1('<%=request.getContextPath()%>/roledata.do?method=item&id='+${user.userid});"
							value="确  定" />
							<td><input type="button" onclick="shownoall(6)"
						value="关 闭" /></td>
					</c:if></td>
			</tr>
		</table>
           </div>
	</form>
</body>
</html>
