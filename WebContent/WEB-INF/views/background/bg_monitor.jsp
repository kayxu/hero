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
	   var LoadChart;//负载表格
	   var OnlineChart;
	<!-- 详细使用方法见 http://docs.jquery.com/UI/Dialog#overview-->
	$(document).ready(function(){
		//Tab切换
		$("#tabs").tabs();
		        Highcharts.setOptions({
		            global: {
		                useUTC: false
		            }
		        });
		    
		      
		        chart = new Highcharts.Chart({
		            chart: {
		                renderTo: 'mem',
		                type: 'spline',
		                marginRight: 10,
		                events: {
		                    load: function() {
		                        
		                        // set up the updating of the chart each second
		                       // var series = this.series[0];
		                        setInterval(requestData, 5000);
		                    }
		                }
		            },
		            title: {
		                text: '内存监控'
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
		                        return '<b>'+ this.series.name +'</b><br/>'+"日期:"+formatDate("hh-MM-DD h:m:s",d) +'<br/>'+"Memory:"+
		                       this.y+"k";
		                }
		            },
		            legend: {
		                enabled: false
		            },
		            exporting: {
		                enabled: false
		            },
		            series: [{
		                name: 'freeMemory',
		                data: (function() {
		                    // generate an array of random data
		                    var data = [],
		                        time = (new Date()).getTime(),
		                        i;
		    
		                    for (i = -20; i <= 0; i++) {
		                        data.push({
		                            x: time + i * 5000,
		                            y: 100000
		                        });
		                    }
		                    return data;
		                })()
		            },
		            {
		                name: 'totalMemory',
		                data: (function() {
		                    // generate an array of random data
		                    var data = [],
		                        time = (new Date()).getTime(),
		                        i;
		    
		                    for (i = -20; i <= 0; i++) {
		                        data.push({
		                            x: time + i * 5000,
		                            y: 100000
		                        });
		                    }
		                    return data;
		                })()
		            }
		            ]
		        });
		        //负载图表
		        LoadChart = new Highcharts.Chart({
		            chart: {
		                renderTo: 'load',
		                type: 'spline',
		                marginRight: 10,
		                events: {
		                    load: function() {
		                        
		                        // set up the updating of the chart each second
		                       // var series = this.series[0];
		                        //setInterval(requestData, 5000);
		                    }
		                }
		            },
		            title: {
		                text: '负载监控'
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
		                        return '<b>'+ this.series.name +'</b><br/>'+"日期:"+formatDate("hh-MM-DD h:m:s",d) +'<br/>'+"负载:"+
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
		                name: 'cpu负载',
		                data: (function() {
		                    // generate an array of random data
		                    var data = [],
		                        time = (new Date()).getTime(),
		                        i;
		    
		                    for (i = -20; i <= 0; i++) {
		                        data.push({
		                            x: time + i * 5000,
		                            y: 100
		                        });
		                    }
		                    return data;
		                })()
		            },
		            {
		                name: '系统负载',
		                data: (function() {
		                    // generate an array of random data
		                    var data = [],
		                        time = (new Date()).getTime(),
		                        i;
		    
		                    for (i = -20; i <= 0; i++) {
		                        data.push({
		                            x: time + i * 5000,
		                            y: 100
		                        });
		                    }
		                    return data;
		                })()
		            }
		            ]
		        });
				//在线人数图表
		        OnlineChart = new Highcharts.Chart({
		            chart: {
		                renderTo: 'num',
		                type: 'spline',
		                marginRight: 10,
		                events: {
		                    load: function() {
		                        
		                        // set up the updating of the chart each second
		                       // var series = this.series[0];
		                        //setInterval(requestData, 5000);
		                    }
		                }
		            },
		            title: {
		                text: '在线人数监控'
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
		                        return '<b>'+ this.series.name +'</b><br/>'+"日期:"+formatDate("hh-MM-DD h:m:s",d) +'<br/>'+"人数:"+
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
		    
		                    for (i = -20; i <= 0; i++) {
		                        data.push({
		                            x: time + i * 5000,
		                            y: 100
		                        });
		                    }
		                    return data;
		                })()
		            },
		            ]
		        });
	});
	//发送请求
	var receiveData=[];
	function requestData() {
	    $.ajax({
	        url: 'bg_monitor.do?method=update',
	        dataType : 'json',  
	        type : 'POST',  
	        success: function(msg) {
	        	//alert(msg);
	        	var x = (new Date()).getTime();
	        	chart.series[0].addPoint([x, msg.freeMemory], true, true);
	        	//alert("send");
	        	chart.series[1].addPoint([x, msg.totalMemory], true, true);
	        	
	        	LoadChart.series[0].addPoint([x, 100*msg.cpuLoad], true, true);
	        	//alert("send");
	        	LoadChart.series[1].addPoint([x, 100*msg.sysLoad], true, true);
	        	
	        	OnlineChart.series[0].addPoint([x, msg.onlineNum], true, true);
	        	if(msg.isOpen==1){
	        		$("#isOpen").val("登录开启");
	        	}else{
	        		$("#isOpen").val("登录关闭");
	        	}
	        
	        },
	        error:function(XMLHttpRequest, error, errorThrown){
	        	  //alert(error);  
	              //alert(errorThrown);  
	        },
	        cache: false
	    });
	}
	function showLogin(responseText, statusText, xhr, $form) {
		//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +         '\n\nThe output div should have already been updated with the responseText.'); 
    	if(responseText['isOpen']==1){
    		$("#isOpen").val("登录开启");
    	}else{
    		$("#isOpen").val("登录关闭");
    	}
	}
	function showTip(responseText, statusText, xhr, $form){
		alert(responseText['success']);
	}
</SCRIPT>

</head>
<body>
	<div id="tabs">
			<ul class="nav nav-tabs" id="myTab">
			<li class="active"><a data-toggle="tab" href="#mem">内存</a></li>
			<li><a data-toggle="tab" href="#load">负载</a></li>
			<li><a data-toggle="tab" href="#num">在线人数</a></li>
			<li><a data-toggle="tab" href="#open">登录开启</a></li>
		</ul>
		<div id="mem" style="width:1000px; height: 400px"></div>
		<div id="load" style="width: 1000px; height: 400px"></div>
		<div id="num" style="width: 1000px; height: 400px"></div>
		<div id="open" style="width: 1000px; height: 400px">
			<form id="myForm">
			<input id="isOpen" value ="登录开启" onclick="options['success']=showLogin;options['url']='<%=request.getContextPath() %>/bg_monitor/login'; $('#myForm').ajaxSubmit(options)" type="button">
			<input id="isOpen" value ="踢出所有玩家" onclick="options['success']=showLogin;options['url']='<%=request.getContextPath() %>/bg_monitor/kickall'; $('#myForm').ajaxSubmit(options)" type="button">
			<input id="isOpen" value ="重置玩家缓存" onclick="options['success']=showTip;options['url']='<%=request.getContextPath() %>/bg_monitor/reload'; $('#myForm').ajaxSubmit(options)" type="button">
			<input id="isOpen" value ="关闭服务器" onclick="options['success']=showTip;options['url']='<%=request.getContextPath() %>/bg_monitor/shutdown'; $('#myForm').ajaxSubmit(options)" type="button">
			<input id="isOpen" value ="重启服务器" onclick="alert('暂未实现')" type="button">
			</form>
		</div>
	</div>
</body>
</html>