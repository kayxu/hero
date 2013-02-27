<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>
<!--
	function submits(url) {
		//alert(url);
		var forms = document.myForm;
		forms.action = url;
		forms.submit();
	}
	
function view(id1, id2) {
	alert('');
	var obj1 = document.getElementById(id1);//开启
	var title1 = id1 + "_title";
	var obj2 = document.getElementById(id2);//隐藏
	var title2 = id2 + "_title";
	var obj1_t = document.getElementById(title1);
	var obj2_t = document.getElementById(title2);
	if (obj1.style.display == "block") {
		obj2.style.display = "none";
		obj1_t.style.color = "#FF0000";
		obj2_t.style.color = "#1E5494";
	} else {
		obj2.style.display = "none";
		obj1.style.display = "block";
		obj1_t.style.color = "#FF0000";
		obj2_t.style.color = "#1E5494";
	}
}
	

	//function closeWin(){
	//	var a = document.getElementById('flag');//隐藏
	//	if (a.value === 'close') {
	//		window.close();
	//	}
		//view('pro','equ');
	//}
	function addRow(id1,id2) {
		var obj1 = document.getElementById(id1);//开启
		var obj2 = document.getElementById(id2);;
		if(obj2.style.display == 'none'){
			$("#" + id1 + " tr:last").html('<td ><form:select path="equselect" items="${equselect}" itemLabel="name" itemValue="id" id="eid" name="eid" ></form:select></td><td ><input id="epl" name="epl" value="1" /></td><td ><input id="esl" name="esl" value="0" /></td><td ><input id="eqc" name="eqc" value="1" /></td>');
		}
		if(obj1.style.display == 'none'){
			$("#" + id2 + " tr:last").html('<td ><form:select path="propselect" items="${propselect}" itemLabel="name" itemValue="id" id="pid" name="pid" ></form:select></td><td ><input id="pcount" name="pcount" value="1" /></td>');
		}
	}
//-->
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
					<td><input type="button" onclick="addRow('equ','pro')"
						value="新 增" /></td>
				</tr>
			</tbody>
		</table>
		<table class="table" id="pro">
			<tr>
				<td>名称</td>
				<td>数量</td>
			</tr>
			<c:forEach items="${pcells}" var="prop">
				<tr>
					<td><c:out value="${prop.prototype.name}" /> <input
						id="pid" name="pid" value="${prop.prototype.id}" type="hidden" />
					</td>
					<td><input id="pcount" name="pcount" value="${prop.num}" /></td>
				</tr>
			</c:forEach>
		</table>
		<table class="table" id="equ">
			<tr>
				<td>名称</td>
				<td>装备等级</td>
				<td>强化等级</td>
				<td>品质</td>
			</tr>
			<c:forEach items="${ecells}" var="equ">
				<tr>
					<td><c:out value="${equ.prototype.name}" /> <input id="eid"
						name="eid" value="${equ.prototype.prototype.equipmentId}"
						type="hidden" /></td>
					<td><input id="epl" name="epl" size="5"
						value="${equ.prototype.prototype.equipmentLevel}" /></td>
					<td><input id="esl" name="esl" size="5"
						value="${equ.prototype.prototype.strengthenLevel}" /></td>
					<td><input id="eqc" name="eqc" size="5"
						value="${equ.prototype.prototype.qualityColor}" /></td>
				</tr>
			</c:forEach>
		</table>
		<table class="table">
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td><c:if test="${user.attriPolitics == 0}">
						<input type="button"
							onclick="submits('<%=request.getContextPath()%>/roledata.do?method=itemsave');"
							value="确  定" />
					</c:if></td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
//$(document).ready(function(){
//	alert(‘http://www.fengpiaoyu.com’);
	//});
</script>
</html>
