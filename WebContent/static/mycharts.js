//我的表格
function chartInit(){
	//初始化表格
	  chartLogin = new Highcharts.Chart({
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
		  Highcharts.setOptions({
	            global: {
	                useUTC: false
	            }
	        });
}

