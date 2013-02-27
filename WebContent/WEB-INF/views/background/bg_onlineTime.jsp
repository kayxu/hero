<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>

	var chart1; // globally available
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
		document.getElementById("from").value=formatDate("YY-MM-DD",new Date());
		document.getElementById("to").value=formatDate("YY-MM-DD",new Date());
		requestData();
	});	
	function requestData() {
		//alert("requestData");
		//alert(jQuery.now());
		    var params = jQuery.param({
            start:$("#from").val(),
            end:$("#to").val()
            });
	
		//alert( document.getElementById("f_rangeStart").value ); 等价于 $("#f_rangeStart").val()
		
		  chart1 = new Highcharts.Chart({
		         chart: {
		            renderTo: 'container',
		            type: 'column' //line,area,pie,column,bar
		           // zoomType: 'x'
		         },
		         title: {
		            text: '在线时间统计'
		         },
		         xAxis: {
		        	    type: 'datetime',
		         },
		         yAxis: {
		            title: {
		               text: ' num'
		            }
		         },
		         tooltip: {
		             formatter: function() {
		                 return '<b>'+ this.series.name +'</b><br/>'+this.x+
		                    ':'+ this.y+"秒";
		             }
		         },
		         series: [{
		        	//pointStart: Date.UTC(2012, 10, 25),//起始时间设置
		        	//pointInterval: 3600 * 1000,// 点距设置
		            name: '最大在线时间',
		            data: [10, 100, 1000,10000]
		         },{
			        	//pointStart: Date.UTC(2012, 10, 25),//起始时间设置
			        	//pointInterval: 3600 * 1000,// 点距设置
			            name: '平均在线时间',
			            data: [10, 100, 1000,10000]
			         }]
		      });
		  chart1.showLoading();
	    $.ajax({
	        url: 'bg_onlineTime.do?method=search',
	        dataType : 'json',  
	        type : 'POST',  
	        data: params,
	        success: function(msg) {
	        	//alert(msg.time);	
	        	var date=new Array();
	        	var max=new Array();
	        	var average=new Array();
	        	var m=0;
	        	var total=0;
	       	    var av=0;
	         $.each(msg.data, function(key, value) {
              	//alert(itemNo);
              	//var num=new Array();
              	date.push(value.time);
              	max.push(value.max);
              	if(m<value.max){
              		m=value.max;
              	}
              	total+=value.total;
              	if(value.set.length>0){
              		var t=value.total/value.set.length;
            	 	average.push(t);
              	 	av+=t;
              	}
        //   	alert("max="+value.max+" total="+value.total+" num="+value.set.length);
           	 });
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
	        	 chart1.series[0].setData(max);
	        	 chart1.series[1].setData(average);
	        	//设置登录次数
	        	 chart1.xAxis[0].setCategories(date);
	        	   //取消loading条
	        	 //  alert(msg.time);
	        	 chart1.hideLoading();
	        	 if(time.length==0){
	        		  alert("未查询到数据");
	        		  return;
	        	  }
	        	 document.getElementById("time").innerHTML=msg.time;//获得td标签内的内容
	        	 document.getElementById("max").innerHTML=m+"秒";//获得td标签内的内容
	        	 document.getElementById("total").innerHTML=total+"秒";//获得td标签内的内容
	        	 if(max.length>0){
	        		 av=av/max.length;
	        	 }
	        	 document.getElementById("average").innerHTML=av+"秒";//获得td标签内的内容
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
	<table class="table table-hover table-condensed" >
		<tbody>
				<tr>
					<th >在线时间统计</th>
					<th ></th>
				</tr>
				<tr>

					<td >日期</td>
					<td  id="time" >0</td>
				</tr>
				<tr>
					<td >最高在线时间</td>
					<td id="max">0</td>
				</tr>
				<tr>
					<td >总在线时间</td>
					<td id="total" >0</td>
				</tr>
				<tr>
					<td >平均在线时间</td>
					<td id="average">0</td>
				</tr>
		</tbody>
	</table>
	<form class="form-inline">
		<input type="text" id="from" name="from" placeholder="start " /> <input
			type="text" id="to" name="to" placeholder="end " />
		<button class="btn-small btn-primary" type="button"
			onclick="requestData();">查询</button>
	</form>
	<div id="container" style="width: 100%; height: 400px"></div>
</body>
</html>