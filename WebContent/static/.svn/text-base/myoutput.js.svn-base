//弹出对话框
	function openDialog(data) {
		var html = "";
		html += '<table class="table"><tbody>';
		$.each(data, function(itemNo, item) {
			html += '<tr>';
			$.each(item, function(key, value) {
				html += '<td>' + key + ":" + value + '</td>';
			});
			html += '</tr>';
		});
		html += '</tbody></table>';
		$("#dialog").html(html);
		$("#dialog").dialog("open");
	}
	//展示登录统计的表格
	function showLogin(responseText, statusText, xhr, $form) {
		var time = new Array();//日期
		var num = new Array();//登录次数
		var num2 = new Array();//有效次数
		$.each(responseText, function(itemNo, item) {
			//alert(item['时间']+":"+item['总次数']+":"+item['有效次数']);
			time.push(item['时间']);
			num.push(parseInt(item['总次数']));//注意需要转换为数字
			num2.push(parseInt(item['有效次数']));
		});
		chartLogin.series[0].setData(num2);
		chartLogin.series[1].setData(num);
		chartLogin.xAxis[0].setCategories(time);
		$("#container").dialog("open");
	}
	var receiveData = [];
	var by = function(name) {
		return function(o, p) {
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
			} else {
				throw ("error");
			}
		}
	}
	//展示在线人数
	function showOnlineNum(responseText, statusText, xhr, $form) {
		$("#container2").dialog("open");
		$.each(responseText.data, function(time, num) {
			//alert(time+"="+num);
			receiveData.push({
				x : time,
				y : num
			});
		});
		receiveData.sort(by("x"));
		chartOnline.series[0].setData(receiveData);
	}
	//展示道具相关的列表
	var msgData;//保存数据的全局变量
	function showPropTable(responseText, statusText, xhr, $form) {
		msgData = responseText;
		createHtml();
		//生成分页列表
		page();
		$("#dialog").dialog("open");
	}
	//生成分页
	function page() {
		$("div.holder").jPages({
			containerID : "content",
			previous : "←",
			next : "→",
			perPage : 10,
			delay : 20
		});
	}
	function createHtml() {
		var html = "";
		html += '<table class="table">';
		html += ' <tr><th onclick="sortById();">id(点击排序)</th><th >名称</th><th onclick="sortByNum();">数量(点击排序)</th></tr>';
		$.each(msgData, function(itemNo, item) {
			html += '<tbody id="content">';
			html += '<tr>';
			html += '<td  >' + item['道具id'] + '</td>';
			html += '<td  >' + item['道具名称'] + '</td>';
			html += '<td >' + item['道具数量'] + '</td>';
			html += '</tr>';
			html += '</tbody>';
		});
		html += '</tbody></table><div class="holder"></div>';
		$("#dialog").html(html);
	}
	//按照id排序
	function sortById() {
		//alert("id");
		msgData.sort(function(x, y) {
			return parseInt(x['道具id']) - parseInt(y['道具id'])
		});
		createHtml();
		page();
	}
	//按照数量排序
	function sortByNum() {
		//alert("num");
		msgData.sort(function(x, y) {
			return parseInt(y['道具数量']) - parseInt(x['道具数量'])
		});
		createHtml();
		page();
	}
	function outputInit(){
			//Tab切换
			 $( "#tabs" ).tabs();
			
			//$('#myTab a').click(function(e) {
				//e.preventDefault();
				//$(this).tab('show');
			//});
			//初始表格...
			chartInit();
			dateInit();
			dialogInit();
			 //在线人数表格
	        chartOnline = new Highcharts.Chart({
	            chart: {
	                renderTo: 'container2',
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
	                    return "日期:"+formatDate("MM-DD h:m:s",d) +'<br/>'+"人数:"+this.y;
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

	}