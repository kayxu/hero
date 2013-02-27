<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>
	
</SCRIPT>
</head>
<body>
	<form action="${ctx}/herodata/search/${userId}">
	<table class=" table table-hover table-condensed">
		<thead>
			<tr>
				<td>序号</td>
				<td>姓名</td>
				<td>等级</td>
				<td>属性</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="data">
				<tr>
					<td ><c:out value="${data.id}" /></td>
					<td ><c:out value="${data.name}" /></td>
					<td ><c:out value="${data.level}" /></td>
					<td ><c:out value="${data.attri}" /></td>
			</c:forEach>
		</tbody>
	</table>
		<button>返回</button>
	</form>
</body>
</html>
