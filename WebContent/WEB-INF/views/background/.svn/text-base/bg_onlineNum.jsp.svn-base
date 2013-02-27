<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<SCRIPT>

	var by = function(name){
			    return function(o, p){
			        var a, b;
			        if (typeof o === "object" && typeof p === "object" && o && p) {
			            a = o[name];
			            b = p[name];
			            if (a === b) {
			                return 0;
			            }
			            if (typeof a === typeof b) {
			                return a < b ? -1 : 1;
			            }
			            return typeof a < typeof b ? -1 : 1;
			        }
			        else {
			            throw ("error");
			        }
			    }
			}
	   var chart; // globally available
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
		
		});
		
		document.getElementById("from").value=formatDate("YY-MM-DD",new Date());
	
		        Highcharts.setOptions({
		            global: {
		                useUTC: false
		            }
		        });
		    
		      
		        chart = new Highcharts.Chart({
		            chart: {
		                renderTo: 'container',
		                type: 'spline',
		                marginRight: 10,
		               
		            },
		            title: {
		                text: '在线人数统计'
		            },
		            xAxis: {
		                type: 'datetime',
		                //tickPixelInterval: 300,
		                //tickInterval:10,
		            },
		            yAxis: {
		                title: {
		                    text: 'Value'
		                },
		                plotLines: [{
		                    value: 0,
		                    width: 1,
		                    color: '#0xFFFFFF'
		                }]
		            },
		            tooltip: {
		                formatter: function() {
		                		var d=new Date();
		                		d.setTime(this.x);
		                        return "日期:"+formatDate("MM-DD h:m:s",d) +'<br/>'+"人数:"+
		                       this.y;
		                }
		            },
		            legend: {
		                enabled: false
		            },
		            exporting: {
		                enabled: false
		            },
		            series: [{
		                name: '在线人数',
		                data: (function() {
		                    // generate an array of random data
		                    var data = [],
		                        time = (new Date()).getTime(),
		                        i;
		    
		                    for (i = -2; i <= 0; i++) {
		                        data.push({
		                            x: time + i * 1000,
		                            y: 1
		                        });
		                    }
		                    return data;
		                })()
		            }]
		        });
		    	requestData();
	});	
	var receiveData=[];
	function requestData() {
		//alert("requestData");
		//alert(jQuery.now());
		var start=$("#from").val();
		    var params = jQuery.param({
            start:$("#from").val(),
            });
	
		//alert( document.getElementById("f_rangeStart").value ); 等价于 $("#f_rangeStart").val()
		 // chart1.showLoading();
	    $.ajax({
	        url: 'bg_onlineNum.do?method=search',
	        dataType : 'json',  
	        type : 'POST',  
	        data: params,
	        success: function(msg) {
	        	//alert(msg.name);	
	        	//var data=new Array();
	        //  var series = chart.series[0];
	        var max=0;
	        var all=0;
	         $.each(msg.data, function(time, num) {
              	//alert(itemNo);
              	//var num=new Array();
              //	num.push((new Date()).getTime());
            	//num.push(value);
              
            	//data.push(num);
	        	//series.addPoint([time, num], true, true);
	        	receiveData.push({
                    x: time,
                    y: num
                });
	        	if(num>max){
	        		max=num;
	        	}
	        	all+=num;
	        	// alert(time+":"+num);
	        	 //alert(   Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',time) );
           	 });
	         receiveData.sort(by("x"));
	        	 
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
	        	 chart.series[0].setData(receiveData);
	        	//设置登录次数
	        	  //chart1.xAxis[0].setCategories(time);
	        	   //取消loading条
	        	  // alert(chart);
	        	// alert(receiveData);
	        	 //chart1.hideLoading();
	        	 if(receiveData.length==0){
	        		  alert("未查询到数据");
	        		  return;
	        	  }
	        	 document.getElementById("time").innerHTML=msg.time;//获得td标签内的内容
	        	 document.getElementById("max").innerHTML=max;//获得td标签内的内容
	        	 document.getElementById("average").innerHTML=all/receiveData.length;//获得td标签内的内容
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
	<table class="table table-hover table-condensed">
		<tbody>
			<tr>
				<th>在线人数统计</th>
			</tr>
			<tr>

				<td>日期</td>
				<td id="time">0</td>
			</tr>
			<tr>
				<td>最高在线数</td>
				<td id="max">0</td>
			</tr>
			<tr>
				<td>平均在线数</td>
				<td id="average">0</td>
			</tr>
		</tbody>
	</table>

	<form class="form-inline">
		<input type="text" id="from" name="from" placeholder="start " />
		<button class="btn-small btn-primary" type="button"
			onclick="requestData();">查询</button>
	</form>
	<div id="container" style="width: 100%; height: 400px"></div>
</body>
</html>