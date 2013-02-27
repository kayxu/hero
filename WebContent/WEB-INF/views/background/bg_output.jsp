\<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<!-- 样式与脚本参考  http://twitter.github.com/bootstrap/javascript.html#tabs -->
<head>
<script src="${ctx}/static/myoutput.js"></script>

<SCRIPT>
	function search(success,url){
		options['success']=success;
		options['url']='<%=request.getContextPath()%>'+url;
		$('#myForm').ajaxSubmit(options);
	}
	function exportFile(url){
		url='<%=request.getContextPath()%>' + url;
		$('#myForm').attr('action', url);
		$('#myForm').submit();
	}
	//加载初始script
	$(document).ready(function() {
		outputInit();
	});
</SCRIPT>
</head>
<body>
	<form id="myForm">
		选择查询时间<input type="text" id="from" name="from" class="time_from"
			placeholder="start " /> <input type="text" id="to" name="to"
			class="time_to" placeholder="end " />
		
			<input type="button"
			value="生成数据" onclick="search(refresh,'/bg_server/data/create')" />
				<input type="button"
			value="生成全部任务数据" onclick="search(refresh,'/bg_server/data/questsave')" />
				<input type="button"
			value="测试用户数" onclick="search(showResponse,'/bg_server/data/test')" />
		<div id="tabs">
			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a data-toggle="tab" href="#basic">基本统计</a></li>
				<li><a data-toggle="tab" href="#active">活跃统计</a></li>
				<li><a data-toggle="tab" href="#lost">流失统计</a></li>
				<li><a data-toggle="tab" href="#charge">收费统计</a></li>
				<li><a data-toggle="tab" href="#economic">经济统计</a></li>
				<li><a data-toggle="tab" href="#system">系统统计</a></li>
				<li><a data-toggle="tab" href="#test">测试</a>
			</ul>


			<div class="tab-pane active" id="basic">

				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tr>
						<td>客户端下载量</td>
						<td><input type="button" value="查询"
							onclick="search(showResponse,'/bg_server/download/search')" /> <input
							type="button" value="导出"
							onclick="exportFile('/bg_server/download/export')" /></td>
					</tr>
					<tr>
						<td>新增用户数</td>
						<td><input type="button" value="查询"
							onclick="search(showResponse,'/bg_login/search/new')" /> <input
							type="button" value="导出"
							onclick="exportFile('/bg_login/export/new')" /></td>

					</tr>
					<tr>
						<td>登录用户</td>
						<td><input type="button" value="查询"
							onclick="search(showResponse,'/bg_login/search/login')" /> <input
							type="button" value="导出"
							onclick="exportFile('/bg_login/export/login')" /> <input
							type="button" value="图表"
							onclick="search(showLogin,'/bg_login/search/login')" /></td>
					</tr>
				</table>
			</div>

			<div class="tab-pane" id="active">
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tr>
						<td>在线时长</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_onlineTime/search')" value="查询" />
							<input type="button"
							onclick="exportFile('/bg_onlineTime/export')" value="导出" /></td>
					</tr>
					<tr>
						<td>在线人数</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_onlineNum/search')" value="查询" />
							<input type="button" onclick="exportFile('/bg_onlineNum/export')"
							value="导出" /> <input type="button"
							onclick="search(showOnlineNum,'/bg_onlineNum/search')" value="图表" /></td>
					</tr>
					<tr>
						<td>活跃用户数(指定时间-之前的7天内登陆过的用户)</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_login/search/active')"
							value="查询" /> <input type="button"
							onclick="exportFile('/bg_login/export/active')" value="导出" /></td>
					</tr>
					<tr>
						<td>忠诚用户数(指定时间-之前的7天内登录过3次以上的用户)</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_login/search/loyal')"
							value="查询" /> <input type="button"
							onclick="exportFile('/bg_login/export/loyal')" value="导出" /></td>
					</tr>
					<tr>
						<td>注册用户等级分布</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/level/reg/search')"
							value="查询" /> <input type="button"
							onclick="exportFile('/bg_user/level/reg/export')" value="导出" />
						</td>
					</tr>
					<tr>
						<td>活跃用户等级分布</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/level/active/search')"
							value="查询" /> <input type="button"
							onclick="exportFile('/bg_user/level/active/export')" value="导出" /></td>
					</tr>
					<tr>
						<td>忠诚用户等级分布</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/level/loyal/search')"
							value="查询" /> <input type="button"
							onclick="exportFile('/bg_user/level/loyal/export')" value="导出" /></td>
					</tr>
				</table>
			</div>
			<div class="tab-pane" id="lost">
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tr>
						<td>留存率（开始时间-之后的14天内的新增用户的登录情况）</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/lost/search')" value="查询" />
							<button>导出</button></td>
					</tr>
					<tr>
						<td>活跃用户流失（开始时间-之后的7天没有登录过的用户）</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/activelost/search')"
							value="查询" />
							<button>导出</button></td>
					</tr>
					<tr>
						<td>流失活跃用户任务(第一次要点击活跃用户流失,只获得开始时间的数据)</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/activelost/task/search')"
							value="查询" />
							<button>导出</button></td>
					</tr>
					<tr>
						<td>忠诚用户流失</td>
					</tr>
					<tr>
						<td>付费用户流失</td>
					</tr>
				</table>
			</div>
			<div class="tab-pane" id="charge">
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tr>
						<td>新增付费用户数</td>
					</tr>
					<tr>
						<td>付费用户数</td>
					</tr>
					<tr>
						<td>付费总额</td>
					</tr>
					<tr>
						<td>付费渠道</td>
					</tr>
					<tr>
						<td>注册转付费</td>
					</tr>
				</table>
			</div>
			<div class="tab-pane" id="economic">
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tr>
						<td>消费钻石用户数</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_prop/search/0')" value="查询" />
							<input type="button" onclick="exportFile('/bg_prop/export/0')"
							value="导出" /></td>
					</tr>
					<tr>
						<td>道具销售</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_prop/search/1')" value="查询" />
							<input type="button" onclick="exportFile('/bg_prop/export/1')"
							value="导出" /> <input type="button"
							onclick="search(showPropTable,'/bg_prop/search/1')" value="图表" /></td>
					</tr>
					<tr>
						<td>道具使用</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_prop/search/2')" value="查询" />
							<input type="button" onclick="exportFile('/bg_prop/export/2')"
							value="导出" /> <input type="button"
							onclick="search(showPropTable,'/bg_prop/search/2')" value="图表" /></td>
					</tr>
					<tr>
						<td>消费钻石数</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_prop/search/3')" value="查询" />
							<input type="button" onclick="exportFile('/bg_prop/export/3');"
							value="导出" /> <input type="button"
							onclick="search(showPropTable,'/bg_prop/search/3')" value="图表" /></td>
					</tr>
					<tr>
						<td>活跃用户金钱总量</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/gamemoney/search')" value="查询" />
							<input type="button"
							onclick="exportFile('/bg_user/gamemoney/export')" value="导出" /></td>
					</tr>
					<tr>
						<td>活跃用户钻石总量</td>
						<td><input type="button"
							onclick="search(showResponse,'/bg_user/joymoney/search')" value="查询" />
							<input type="button"
							onclick="exportFile('/bg_user/joymoney/export');" value="导出" /></td>
					</tr>
				</table>
			</div>
			<div class="tab-pane" id="system">
				<table class="table  table-bordered">
					<tbody>
						<tr>
							<input type="text" name="online_time" placeholder="在线时间设置 " />
							<input type="text" name="heart_num" placeholder="心跳次数设置 " />
							<input type="button"
								onclick="search(showResponse,'/bg_onlineTime/search/new');"
								value="查询" />
						</tr>
						<tr>
							<input type="button"
								onclick="search(showResponse,'/bg_server/arenacheck');"
								value="竞技场检测" />
						</tr>
					</tbody>
				</table>

			</div>
			<div class="tab-pane" id="test">
				<input type="button"
								onclick="search(showResponse,'/bg_monitor/status');"
								value="mongodb数据库状态" />
				<div class=" alert alert-error">测试功能，请勿执行</div>
				<textarea rows="10" id="script" name="script"></textarea>
				<input type="button"
					onclick="search(showResponse,'/bg_server/script');" value="执行" />
				<table class="table  table-bordered">
					<thead>
						<tr>
							<th>说明</th>
							<th>代码</th>
						</tr>
					</thead>

					<tbody id="message">
						<tr>
							<td>数据库状态</td>
							<td>db.activeUser.stats()</td>
						</tr>
						<tr>
							<td>数据查询</td>
							<td>db.foo.findOne({'name':'ysz'})</td>
							<td><a
								href="http://www.cnblogs.com/cxd4321/archive/2011/06/24/2089051.html">
									参考文档</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form>
	<!-- 弹出框 -->
	<div id="dialog" title="查询结果"></div>
	<!-- 图表1 -->
	<div id="container" style="width: 100%; height: 400px"></div>
	<!-- 图表2 -->
	<div id="container2" style="width: 100%; height: 400px"></div>
</body>
</html>