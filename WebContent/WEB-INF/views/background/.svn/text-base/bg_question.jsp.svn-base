<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<SCRIPT>
$(document).ready(function() {
	dateInit();
});
function page(){
    $("div.holder").jPages({
        containerID : "questions",
        previous : "←",
        next : "→",
        perPage : 10,
        delay : 20
      });
}
function success(responseText, statusText, xhr, $form){
	var html="";
	$.each(responseText, function(itemNo, item) {
		html+="<tr >";
		html+="<td>"+item.id+"</td>";
		html+="<td>"+item.userid+"</td>";
		html+="<td>"+item.content+"</td>";
		html+="<td>"+item.createTime+"</td>";
		html+="</tr>";
	});
	$("#questions").html(html);
	page();
	dateInit();
}
</SCRIPT>
</head>
<body>
	<div class="holder"></div>
	<form id="myForm">
	<input type="text" id="from" name="from" class="time_from"
		placeholder="start " />
	<input type="text" id="to" name="to" class="time_to"
		placeholder="end " />
	<input type="button"
		onclick="options['success']=success;options['url']='<%=request.getContextPath()%>/bg_question/time';	$('#myForm').ajaxSubmit(options)"
		value="查询" />
		</form>
	<table class="table  table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>玩家id</th>
				<th>内容</th>
				<th>时间</th>
			</tr>
		</thead>

		<tbody id="questions">
		
		</tbody>
	</table>


</body>
</html>