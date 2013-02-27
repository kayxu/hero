<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>
	//按照id排序
	function sortById(){
		//alert("id");
		msgData.sort(function (x,y){return x.id-y.id});
		createHtml();
		page();
	}
	//按照数量排序
	function sortByNum(){
		//alert("num");
		msgData.sort(function (x,y){return y.num-x.num});
		createHtml();
		page();
	}
	<!-- 详细使用方法见 http://docs.jquery.com/UI/Dialog#overview-->
	$(document).ready(function(){
		$(function() {
			$( "#from" ).datepicker({
				//defaultDate: "+1w",
				changeMonth: true,
				numberOfMonths: 1,
				dateFormat:"yy-mm-dd",
				onSelect: function( selectedDate ) {
					$( "#to" ).datepicker( "option", "minDate", selectedDate );
				}
			});
			$( "#to" ).datepicker({
				//defaultDate: "+1w",
				changeMonth: true,
				numberOfMonths: 1,
				dateFormat:"yy-mm-dd",
				onSelect: function( selectedDate ) {
					$( "#from" ).datepicker( "option", "maxDate", selectedDate );
				}
			});
			send();
		});
	});	
	 //详细参考 http://luis-almeida.github.com/jPages/documentation.html
	function page(){
        $("div.holder").jPages({
	        containerID : "movies",
	        previous : "←",
	        next : "→",
	        perPage : 20,
	        delay : 20
	      });
	}
	function serachById(){
		var id=$("#sid").val();
		 $.each(msgData, function(itemNo, item) {
			 if(item.id==id){
				 alert("id="+item.id+" name="+item.name+" num="+item.num);
				 return;
			 }
		 });
	}
	function serachByName(){
		var name=$("#sname").val();
		alert(name);
		 $.each(msgData, function(itemNo, item) {
			 if(item.name==name){
				 alert("id="+item.id+" name="+item.name+" num="+item.num);
			 }
		 });
	}
	var msgData;//保存数据的全局变量
	//生成数据页面
	function createHtml(){
		   var html="";
	      	  $.each(msgData, function(itemNo, item) {
	      		html+='<tr>';
	        	html+='<td  >'+item.id+'</td>';
	        	html+='<td  >'+item.name+'</td>';
	        	html+='<td >'+item.num+'</td>';
	        	html+='</tr>';
	          });
	          $( "#movies" ).html(html);
	          if(propType==0){
	        	  $("#propType").html("道具购买");  
	          }else if(propType==1){
	        	  $("#propType").html("道具使用");
	          }else{
	        	  $("#propType").html("钻石使用");
	          }
	          
	}
	var propType=0;//0购买，1使用，2钻石
	//请求数据
	function send() {
			//alert("ok");
		 var params = jQuery.param({
            start:$("#from").val(),
            end:$("#to").val(),
            type:propType
            });
	
	    $.ajax({
	        url: 'bg_prop.do?method=search',
	        dataType : 'json',  
	        type : 'POST',  
	        data: params,
	        success: function(msg) {
	         //生成列表
	         msgData=msg.data;
	         //加入到页面中
	         createHtml();
	         //生成分页列表
	          page();
	        },
	        error:function(XMLHttpRequest, error, errorThrown){
	        	  alert(error);  
	              alert(errorThrown);  
	        },
	        cache: false
	    });
	}

</SCRIPT>

</head>

<body>
	<div id="content">
		<table class="table table-hover table-condensed">
			<tr>
				<td>当前查询类型</td>
				<td id="propType"></td>
				<td><input type="submit" value="道具购买"
					onclick="propType=0;send();" /><input type="submit" value="道具使用"
					onclick="propType=1;send();" /><input type="submit" value="钻石使用"
					onclick="propType=2;send();" /></td>
			</tr>
			<tr>
				<td>开始时间<input type="text" id="from" name="from" /></td>
				<td>结束时间<input type="text" id="to" name="to" /></td>
				<td><input type="submit" value="查询" onclick="send();" /></td>
			</tr>
			<tr>
				<td>按照id查找</td>
				<td><input type="text" id="sid" name="to" /></td>
				<td><input type="submit" value="查询" onclick="serachById();" /></td>
			</tr>
			<tr>
				<td>按照name查找</td>
				<td><input type="text" id="sname" name="to" /></td>
				<td><input type="submit" value="查询" onclick="serachByName();" /></td>
			</tr>
			<tr>
				<th onclick="sortById();">id(点击排序)
				</th>
				<th >名称</th>
				<th onclick="sortByNum();">数量(点击排序)
				</th>
			</tr>

			<tbody id="movies">
				<!-- 
			<c:forEach items="${props}" var="prop">
				<tr>
					<td width="20%" class="td_bg"><c:out value="${prop.id}" /></td>
					<td width="40%" class="td_bg"><c:out value="${prop.name}" /></td>
					<td width="40%" class="td_bg"><c:out value="${prop.num}" /></td>
				</tr>
			</c:forEach>
			 -->
			</tbody>

		</table>
		<div class="holder"></div>
	</div>

</body>
</html>