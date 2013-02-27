<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>
<!--
	var chart1; // globally available
	var chart2;
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
		});
	});	
	function requestDetail(){
		//var s=[1,2,3,4,5];
	    var params = jQuery.param({
            id:  [1541,1455],
            });
 
		  $.ajax({
		        url: 'bg_login.do?method=detail',
		        dataType : 'json',  
		        type : 'POST',  
		        data: params,
		        success: function(msg) {
		           
		        },
		        error:function(XMLHttpRequest, error, errorThrown){
		        	  alert(error);  
		              alert(errorThrown);  
		        },
		        cache: false
		    });
	}
	function requestData() {
		//alert("requestData");
		//alert(jQuery.now());
		var start=$("#from").val();
		var end=$("#to").val();
		//alert("开始时间："+start);
		//alert("结束时间："+end);
		if(start==end){
			//alert("开始结束时间不能相同");
			//return;
		}
		    var params = jQuery.param({
            start:$("#from").val(),
            end:$("#to").val(),
            });
	
		//初始化表格
		  chart1 = new Highcharts.Chart({
		         chart: {
		            renderTo: 'container',
		            type: 'column' //line,area,pie,column,bar
		         },
		         title: {
		            text: '登录统计'
		         },
		         xAxis: {
		            categories: ['10.8', '10.9', '10.10','10.11']
		         },
		         yAxis: {
		            title: {
		               text: ' num'
		            }
		         },
		         tooltip: {
		             formatter: function() {
		                 return '<b>'+ this.series.name +'</b><br/>'+
		                    '次数:'+ this.y;
		             }
		         },
		         series: [{
		            name: '登录用户',
		            data: [10, 100, 1000,10000]
		         },{
			            name: '登录次数',
			            data: [100, 1000, 10000,100000]
			         }]
		      });
		  chart2 = new Highcharts.Chart({
		         chart: {
		            renderTo: 'container2',
		            type: 'column' //line,area,pie,column,bar
		         },
		         title: {
		            text: '流失统计'
		         },
		         xAxis: {
		            categories: ['10.8', '10.9', '10.10','10.11']
		         },
		         yAxis: {
		            title: {
		               text: ' num'
		            }
		         },
		         tooltip: {
		             formatter: function() {
		                 return '<b>'+ this.series.name +'</b><br/>'+
		                    '次数:'+ this.y;
		             }
		         },
		         series: [{
		            name: '登录用户',
		            data: [10, 100, 1000,10000]
		         },]
		      });
		
		  chart1.showLoading();
	    $.ajax({
	        url: 'bg_login.do?method=search',
	        dataType : 'json',  
	        type : 'POST',  
	        data: params,
	        success: function(msg) {
	        	//alert(msg.name);	
	        	var time=new Array();
	        	var num=new Array();
	        	var num2=new Array();
	        	var ids=new Array();
	        	  $.each(msg.data, function(itemNo, item) {
              	//alert(item.set.length);
              	time.push(item.time);
              	num.push(item.num);
              	num2.push(item.set.length);
              	ids.push(item.set);
            });
	        	 
	        	//for(var key in num){
	        		//alert(time[key]);
	        	//}
	        	//取得第组数据
	        	 // var series = chart1.series[0];
	        	 //sees.name="hi";
	        	  //alert(series.name);
	        	 // alert(series.data[0].x);
	        	 //更新数值=1
	        	 //参考http://api.highcharts.com/highcharts#object-Point
	        	  //series.data[0].update(1);
	        	 //更新全部数值
	        	//  series.setData([1,1,1]);
	        	 //
	        	 //设置标题
	        	// chart1.setTitle({ text: 'New title '});
	        	//设置登录用户
	        	  chart1.series[0].setData(num2);
	        	//设置登录次数
	        	  chart1.series[1].setData(num);
	        	  chart1.xAxis[0].setCategories(time);
	        	  
	        	  chart2.xAxis[0].setCategories(time);
	        	  var newNum=new Array();
	        	  //数组中的每一个id都去ids[0]中查找是否有相同的，有的话则统计数量
	        	  var d=ids[0];
	        	  $.each(ids,function(key,value){
	        		  //数组中的每一个id都去ids[0]中查找是否有相同的，有的话则统计数量
	        		  var n=0;
	        		  for(var j=0;j<value.length;j++){
	        			 // alert(value[j]);
	        			  for(var i=0;i<d.length;i++){
	    	        		  //  alert(ids[0][i]);
	    	        		  if(value[j]==d[i]){
	    	        			  n++;
	    	        			  break;
	    	        		  }
	    	        	  }
	        		  }
	        		  newNum.push(n);
 					
	        	  });
	        	  chart2.series[0].setData(newNum.reverse());
	        	   //取消loading条
	        	 chart1.hideLoading();
	        	 if(time.length==0){
	        		  alert("未查询到数据");
	        		  return;
	        	  }
	           
	        },
	        error:function(XMLHttpRequest, error, errorThrown){
	        	  alert(error);  
	              alert(errorThrown);  
	        },
	        cache: false
	    });
	}
//-->
</SCRIPT>
</head>
<body>
	<table class="table table-hover table-condensed">

		<tbody>
			<tr>

				<td>登录统计</td>
				<td>${time}</td>
			<tr>
				<td>当日登录用户</td>
				<td>${num_toady}</td>
			</tr>
			<tr>
				<td>当日登录次数
				</td>
				<td>${num_toady2}</td>
			</tr>
			<!-- 
			<tr>
				<td>账号流失，统计7天前登陆的玩家，在本周内未登陆的比例</td>
				<td>${num_lost}%</td>
				<td>消耗时间:${consume}秒</td>
			</tr>
			<tr>
				<td><input type="button" onclick="requestDetail();"
					value="玩家详情查询" /></td>
				<td><input type="button" onclick="getXml();" value="XML查询" /></td>
			</tr>
			 -->
		</tbody>

	</table>
	<form class="form-inline">
		<input type="text" id="from" name="from" placeholder="start " /> <input
			type="text" id="to" name="to" placeholder="end " />
		<button class="btn-small btn-primary" type="button"
			onclick="requestData();">查询</button>
	</form>
	<!-- 图表1 -->
	<div id="container" style="width: 100%; height: 400px"></div>
	<!-- 图表2 -->
	<div id="container2" style="width: 100%; height: 400px"></div>
</body>
</html>