<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>
	<!-- 详细使用方法见 http://docs.jquery.com/UI/Dialog#overview-->
	$(document).ready(function(){
		$(function() {
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
	
	//生成数据页面
	function createHtml(){
		   var html="";
	      	  $.each(msgData, function(itemNo, item) {
	      		var status;
	      		if(item.gameState==1){
	      			status="普通";
	      		}else if(item.gameState==2){
	      			status="关闭";
	      		}else if(item.gameState==0){
	      			 status="拥挤";
	      		}
	      		//alert(item.gameState);
	      		var type="最新";
	      		if(item.gameType==1){
	      			type="推荐";
	      		}else if(item.gameType==2){
	      			type="其他"
	      		}
	      		html+='<tr >';
	        	html+='<td >'+item.gameName+'</td>';
	        	html+='<td ><select id="'+("type"+itemNo)+'" ><option value=\'0\'>最新</option><option value=\'1\'>推荐</option><option value=\'2\'>其他</option></select>'+'</td>';
	        	html+='<td >'+status+'</td>';
	        	html+='</tr>';
	          });
	          $( "#movies" ).html(html);
	          //添加触发监控
	          $.each(msgData, function(itemNo, item) {
	        	  //alert($("#type"+itemNo).val());
	        	  $("#type"+itemNo).val(item.gameType);
	        	  $("#type"+itemNo).change(function(){ 
	        		  var newType=$("#type"+itemNo).val();
	        		  //alert($("#type"+itemNo).val());
	        	  var params = jQuery.param({
            			type:newType,
            	  });
	        		  $.ajax({
	        		        url: 'bg_server.do?method=change',
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
	        	  });
	          });
	          
	}
	var msgData;//保存数据的全局变量
	var time;
	//请求数据
	function send() {
	    $.ajax({
	        url: 'bg_server.do?method=search',
	        dataType : 'json',  
	        type : 'POST',  
	        success: function(msg) {
	         //生成列表
	         msgData=msg.data;
	         //加入到页面中
	         createHtml();
	         //生成分页列表
	         page();
	         time=msg.time;
	     	var d=new Date();
    		d.setTime(time);//取得服务器时间
    		var date1=new Date();  //本地时间
    		$("#time").html("<strong>本地与服务器时间差="+(d.getTime()-date1.getTime())+"毫秒</strong>");
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
	<div id="time" class=" alert alert-error">
	</div>
		<table class="table table-hover table-condensed">
			<thead>
				<tr>
					<th>服务器名称</th>
					<th>服务器类型</th>
					<th>服务器状态</th>
				</tr>
			</thead>

			<tbody id="movies">
			</tbody>
		</table>
			<div class="holder"></div>
	</div>
	
</body>
</html>