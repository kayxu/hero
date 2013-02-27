<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<SCRIPT>
	<!-- 详细使用方法见 http://docs.jquery.com/UI/Dialog#overview-->
	$(document).ready(function(){
	});	
	function setText(text){
		//alert(text)
		$('#input').val(text);
	}
</SCRIPT>
</head>
<body>

<div class=" alert fade in">
			<strong>可以点击已经发送的内容：
			</strong>
		</div>
	<form id="myForm">
		系统公告 <input id="input" type="text" placeholder="输入文本" name="message">
		<input type="button"
			onclick="options['success']=refresh;options['url']='<%=request.getContextPath()%>/bg_message/world';	$('#myForm').ajaxSubmit(options)"
			value="发布" />
		<input type="button"
			onclick="options['success']=refresh;options['url']='<%=request.getContextPath()%>/bg_message/world?add=1';	$('#myForm').ajaxSubmit(options)"
			value="添 加" />

		<table class="table  table-bordered">
			<thead>
				<tr>
					<th>内容</th>
					<th>时间</th>
				</tr>
			</thead>

			<tbody id="message">
				<c:forEach items="${list}" var="data">
					<c:if test="${data.isAuto == 'F'}">
						<tr>
						<td><a href="javascript:setText('${data.message}')">${data.message}</a></td>
						<td><c:out value="${data.date}" /></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		<table class="table  table-bordered">
			<thead>
				<tr>
					<th>内容</th>
					<th>时间</th>
					<th>操作</th>
				</tr>
			</thead>

			<tbody id="message">
				<c:forEach items="${list}" var="data">
					<c:if test="${data.isAuto == 'T'}">
						<tr>
						<td><a href="javascript:setText('${data.message}')">${data.message}</a></td>
						<td><c:out value="${data.date}" /></td>
						<td><input type="button"
					onclick="options['success']=refresh;options['url']='<%=request.getContextPath()%>/bg_message/delete?id=${data.id}';	$('#myForm').ajaxSubmit(options)"
						value="删 除" />
						</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</form>

</body>
</html>