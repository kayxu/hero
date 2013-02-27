<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<script src="${pageContext.request.contextPath}/static/mycheck.js"></script>

<SCRIPT>
	function submits(url) {
		//alert();
		if(check()){
			var numeric = document.getElementById("numerical").value;
			//alert("ok");
			var forms = document.myForm;
			forms.action = url;
			forms.submit();
		}
	}
	//导出EXCEL
	function doExport() {
		document.forms[0].action = "export";
		document.forms[0].submit();
	}
	function hides(name) {
		//alert(name)
		$("#"+name).hide();
	}
	function shows(name) {
		//alert(name)
		$("#"+name).show();

	}
	function hidd(s) {
		if(s == 3 || s == 4){
			shows("tip1");
			hides("choice");
			hides("tip2");
			
		}else if(s == 11 || s == 12){
			shows("tip2");
			hides("choice");
			hides("tip1");
		}else {
			shows("choice");
			shows("tip1");
			hides("tip2");
		}
		
	}
	function hidds() {
		var s = $("#types").val();
		if(s == 3 || s == 4){
			shows("tip1");
			hides("choice");
			hides("tip2");
		}else if(s == 11 || s == 12){
			shows("tip2");
			hides("choice");
			hides("tip1");
			
		}else {
			shows("choice");
			shows("tip1");
			hides("tip2");
			
		}
	}
	
	function check(){
		var bool = true;
		var s = $("#types").val();
		//中间符号不需要输入
		if(s ==11 || s == 12){
			var ff = $("#from").val();
			var tt = $("#to").val();
			if(isEmpty(ff)){
				alert('输入条件开始时间不能为空！');
				bool = false;
			}else if(isEmpty(tt)){
				alert('输入条件结束时间不能为空！');
				bool = false
			}
		}else if(s == 3){
			//条件为字符
			var nn = $("#numerical").val();
			if(isEmpty(nn)){
				alert('输入条件不能为空！');
				bool = false
			}
		}else{
			//条件为数字
			var nn = $("#numerical").val();
			if(isEmpty(nn)){
				alert('输入条件不能为空！');
				bool = false
			}else if(!isNumber(nn)){
				alert('输入条件必须为数字！');
				bool = false
			}
		}
		return bool;
	}
	
	//加载初始script
	$(document).ready(function() {
		//初始时间...
		dateInit();
		
	});
	
	$(function(){
		document.onkeydown = function(e){ 
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	submits('<%=request.getContextPath()%>/roledata?pages=1');
		     }
			}
		});  

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

	function showfloatall(num,id,name)//这个是显示要弹出的层
	{
	  	var range = getRange();
	  	// var divvv1="doing"+num;
	     var divvv2="divLogin"+num;
	     // document.getElementById(divvv1).style.width = range.width + "px";
	      //document.getElementById(divvv1).style.height = range.height + "px";
	     // document.getElementById(divvv1).style.display = "block";
	      document.getElementById(divvv2).style.display="block";
	      $("#useridss").val(id);
	      $("#names").val(name);
	}

	function shownoall(num)//这个是关闭层
	{
		// var divv1="doing"+num;
		    var divv2="divLogin"+num;
		  //  document.getElementById(divv1).style.display="none";
		    document.getElementById(divv2).style.display="none";
	}
	
	function send(){
		if(isEmpty($("#useridss").val())){
			alert('发送用户不能为空！');
			return 
		}else if(isEmpty($("#message").val())){
			alert('发送内容不能为空！');
			return 
		}
		var params = jQuery.param({
	        userids:$("#useridss").val(),
	        sendvalue:$("#message").val(),
	        //type:propType
	        });
		//alert(params)
		$.ajax({
		    type : "POST",
		    url : 'roledata.do?method=send',
		    cache : false,
		    dataType : "json",
		    async:false,
		   data : params,
		    error : function(XMLHttpRequest) {// 请求失败时调用函数
		    	 alert('发送失败');
		    },
		    success : function(json) {
		    	alert('发送成功');
		    }
		});
	}

</SCRIPT>
</head>
<body>
	<form method="post" name="myForm">
	<input id="pages" name="pages" value="<%=request.getParameter("pages")%>"
			type="hidden" />
		<table class="table">
			<tbody>
				<tr>
					<th>查询条件</th>
					<td><input type="button"
						onclick="submits('<%=request.getContextPath()%>/roledata?pages=1');"
						value="查询" /></td>
					<!--<th >
					 <td ><input type="button" value=" 导出EXCEL " onClick="javascript:doExport();"></th> -->
				</tr>
				<tr>
					<td>类型</td>
					<td><select name="types" id="types"
						onchange="hidd(this.options[this.options.selectedIndex].value)">
							<option value="0" <c:if test="${types == '0'}">selected</c:if>>...等级...</option>
							<option value="1" <c:if test="${types == '1'}">selected</c:if>>...金币...</option>
							<option value="2" <c:if test="${types == '2'}">selected</c:if>>...钻石...</option>
							<option value="3" <c:if test="${types == '3'}">selected</c:if>>...姓名...</option>
							<option value="4" <c:if test="${types == '4'}">selected</c:if>>...ID...</option>
							<option value="5" <c:if test="${types == '5'}">selected</c:if>>...功勋...</option>
							<option value="6" <c:if test="${types == '6'}">selected</c:if>>...筹码...</option>
							<option value="7" <c:if test="${types == '7'}">selected</c:if>>...政绩...</option>
							<option value="8" <c:if test="${types == '8'}">selected</c:if>>...军功...</option>
							<option value="9" <c:if test="${types == '9'}">selected</c:if>>...爵位...</option>
							<option value="10" <c:if test="${types == '10'}">selected</c:if>>...官位...</option>
							<option value="11" <c:if test="${types == '11'}">selected</c:if>>...建立时间...</option>
							<option value="12" <c:if test="${types == '12'}">selected</c:if>>...最后登录时间...</option>
					</select></td>
					<td id="choice" name="choice"><select name="symbol"
						id="symbol">
							<option value="0" <c:if test="${symbol == '0'}">selected</c:if>>&gt</option>
							<option value="1" <c:if test="${symbol == '1'}">selected</c:if>>=</option>
							<option value="2" <c:if test="${symbol == '2'}">selected</c:if>>&lt</option>
					</select></td>
					<td>输入条件</td>
					<td id="tip1" name="tip1"><input id="numerical" name="numerical"
						value="${numerical}"></input> <font color="red"><form:errors
								path="numerical" /></font></td>
						
					<td id="tip2" name="tip2"><input type="text" id="from" name="from" class="time_from" value="${from}" readonly="readonly"
								placeholder="start " /> <input type="text" id="to" name="to" class="time_to" value="${to}" readonly="readonly"
								placeholder="end " /></td>
								
				</tr>
			</tbody>

		</table>
	</form>
	<form id="myForm2">
		<table class=" table table-hover table-condensed">
			<tbody>
				<tr>
					<td>序号</td>
					<td>姓名</td>
					<td>等级</td>
					<td>金币</td>
					<td>钻石</td>
					<td>状态</td>
					<td>禁言</td>
					<td>冻结</td>
					<td>新页面</td>
					<td>发送消息</td>
				</tr>
				<c:forEach items="${userList}" var="role">
					<tr>
						<td>
						<!-- 
						<a onclick="submits('<%=request.getContextPath() %>/roledata?method=detail&id=<c:out value="${role.userid}"/>')" href="#"><c:out value="${role.userid}" /></a>
						 -->
						<c:out value="${role.userid}"/>
						</td>
						<td><c:out value="${role.name}" /></td>
						<td><c:out value="${role.level}" /></td>
						<td><c:out value="${role.gameMoney}" /></td>
						<td><c:out value="${role.joyMoney}" /></td>
						<td><c:if test="${role.attriPolitics == 0}">离线</c:if> <c:if
								test="${role.attriPolitics == 1}">在线</c:if></td>
						<td><input id="speak${role.userid}" type="button"
							value=<c:if  test="${role.canSpeak == 1}">"禁言中"</c:if>
							<c:if test="${role.canSpeak == 0}">"未禁言"</c:if>
							onclick="options['success']=showResponse2;options['url']='<%=request.getContextPath()%>/roledata/speak/${role.userid}';	$('#myForm2').ajaxSubmit(options)" /></td>
						<td><input id="login${role.userid}" type="button"
							value=<c:if  test="${role.canLogin == 1}">"冻结中"</c:if>
							<c:if test="${role.canLogin == 0}">"未冻结"</c:if>
							onclick="options['success']=showResponse2;options['url']='<%=request.getContextPath() %>/roledata/login/${role.userid}'; $('#myForm2').ajaxSubmit(options)" /></td>
						<!-- 
					<td ><a href="<%=request.getContextPath()%>/herodata/search/${role.userid}">跳转</a> </td>
					<td ><a href="<%=request.getContextPath()%>/builddata/search/${role.userid}">跳转</a> </td>
					<td ><a href="<%=request.getContextPath()%>/questdata/search/${role.userid}">跳转</a> </td>
					 -->
						<td><a
							href="<%=request.getContextPath() %>/roledata/search/${role.userid}">玩家详情</a>
						</td>
						<td><input type="button"
								onclick="showfloatall(6,${role.userid},'${role.name}');"
								value="回复" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="dede_pages">${page}</div>
		<script type="text/javascript">
			hidds();
		</script>
		
		
		<!--加一个登录层  读取专业信息--> 
    <div id="divLogin6"  style="border:solid 8px #74B1D6;display:none;background:#fff;width:800px;z-index:1001;position: absolute;top:50%; left:50%;margin:-300px 0 0 -400px;overflow:auto">
            <table class="table">
			<tbody>
				<tr>
					<td>id</td>
					<td><input id="useridss" name="useridss" value=""
						readonly="readonly" /></td>
					<td>name</td>
					<td><input id="names" name="names" value=""
						readonly="readonly" /></td>	
				</tr>
			</tbody>
		</table>
		
		<table class="table">
			<tr>
				<td>信息</td>
				<td><textarea rows="10" cols="100%" id="message"
					name="message" ></textarea>
				</td>
			</tr>
			
		</table>
		<table class="table">
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td>
				<input type="button" onclick="send()" value="发 送" />
				<td><input type="button" onclick="shownoall(6)"
			value="关 闭" /></td>
			</tr>
		</table>
           </div>
	</form>

</body>
</html>
