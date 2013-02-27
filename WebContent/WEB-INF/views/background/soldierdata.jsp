<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>
	
</SCRIPT>
</head>
<body>
	<form action="<%=request.getContextPath()%>/roledata.do">
	<table class=" table table-hover table-condensed">
		<thead>
			<tr>
				<td>序号</td>
				<td>姓名</td>
				<td>品级</td>
				<td>等级</td>
				<td>攻击</td>
				<td>防御</td>
				<td>生命</td>
				<td>带兵数</td>
				<td>装备</td>
				<td>技能</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="data">
				<tr>
					<td ><c:out value="${data.id}" /></td>
					<td ><c:out value="${data.name}" /></td>
					<td ><c:out value="${data.color}" /></td>
					<td ><c:out value="${data.level}" /></td>
					<td ><c:out value="${data.attack}" /></td>
					<td ><c:out value="${data.defence}" /></td>
					<td ><c:out value="${data.hp}" /></td>
					<td ><c:out value="${data.soldierNum}" /></td>
					<td ></td>
					<td ><a href="${ctx}/skilldata/search/${data.id}">查看</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<button>返回</button>
	</form>
</body>
</html>
