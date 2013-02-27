<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>
	function submits(url) {
		var forms = document.myForm;
		forms.action = url;
		forms.submit();
	}
</SCRIPT>
</head>

<body>
	<table class="table table-hover table-condensed">
		<tbody>
			<form method="post" name="myForm">
				<tr>

					<th>返回消息</th>
					<th>${msg}</th>
				</tr>
				<tr>
					<td>战斗类型</td>
					<td>
					<select name="fightType" id="fightType">
							<option value="0"
								<c:if test="${fightType == '0'}">selected</c:if>>...市争夺战...</option>
							<option value="1"
								<c:if test="${fightType == '1'}">selected</c:if>>...州争夺战...</option>
							<option value="2"
								<c:if test="${fightType == '2'}">selected</c:if>>...国争夺战...</option>
							<option value="3"
								<c:if test="${fightType == '3'}">selected</c:if>>...县争夺战...</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>开放 nation id</td>
					<td>
						<input type="text" id= "naid" name="naid">
					</td>
				</tr>
				<tr>
					<td >操作</td>
					<td ><input
						type="button"
						onclick="submits('<%=request.getContextPath()%>/wars.do?method=start');"
						value="开始" /></td>
					<td ><input
						type="button"
						onclick="submits('<%=request.getContextPath()%>/wars.do?method=stop');"
						value="结束" /></td>
				</tr>
			</form>
		</tbody>

	</table>
</body>
</html>