<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<SCRIPT>
	var TITLE = [ "平民", "县长", "市长", "州长", "国家领导 ", "县级官员", "市级官员", "省级官员",
			"国级官员" ];
	$(document).ready(function() {
		//Tab切换
		href = location.href;
		$("#tabs").tabs();
		$('#myTab a').click(function(e) {
			e.preventDefault();
			//alert($(this).attr("href"));
			//alert(location.href);
			location.href = $(this).attr("href");
			$(this).tab('show');
		})
		$('a[data-toggle="tab"]').on('shown', function(e) {
			e.target // activated tab
			e.relatedTarget // previous tab
		})
		//隐藏数据
		$("#data_hide").hide();
		//处理初始数据
		$("#nation").val(Math.floor($("#nation").val()));
		$("#state").val(Math.floor($("#state").val()));
		$("#city").val(Math.floor($("#city").val()));
		var canLogin = $("#data_canLogin").html();
		if (canLogin == 0) {
			$("#canLogin").val("未冻结");
		} else {
			$("#canLogin").val("已冻结");
		}
		// 爵位0:平民/1:骑士/2:准男爵/3:男爵/4:子爵/5: 伯爵/6:侯爵/7:公爵
		//alert($("#data_city").html());
		$("#cityLevel").val($("#data_city").html());
		//0--平民，1--县长，2--市长，3--州长，4--国家领导 5--县级官员,6--市级官员,7--省级官员,8--国级官员
		//alert($("#data_title").html());
		//alert(TITLE[parseInt($("#data_title").html())]);
		$("#usertitle").val(TITLE[parseInt($("#data_title").html())]);
		//处理登录时间
		var loginTime = parseInt($("#data_login").html());
		//alert("logtin="+loginTime);
		var date = new Date();
		date.setTime(loginTime);
		//alert("date="+date);
		var time = formatDate(" MM-DD h:m:s", date);
		//alert(time);
		$("#loginTime").val(time);
		//隐藏物品信息
		$('#table_prop').hide();
		$('#table_equip').hide();
		//建筑状态
		//public static final byte COMPLETE_BUILD = 1;
		//public static final byte LEVELUP_BUILD = 2;
		//public static final byte COMPLETE_LEVELUP = 3;
		$("#table_build tr:gt(0)").each(function() {
			var status = $("td:eq(3)", this).html();
			//alert(status);
			if (status == 2) {
				$("td:eq(3)", this).html("升级中");
			} else {
				$("td:eq(3)", this).html("升级完成");
			}
		});
		//技能,装备隐藏
		$("#hero_equip").hide();
		$("#hero_skill").hide();
		//将领装备和技能弹出
		$(".hero_dialog").dialog({
			autoOpen : false,
			height : 500,
			width : 800,
			modal : true,
			buttons : {
				Cancel : function() {
					$(this).dialog("close");
					hero_reset();
				}
			},
			close : hero_reset
		});
		//采用jqueryui 提示
		//$(document).tooltip();
		//显示战报时间转换
		//$(".fight").hover(
		// function () {
		//alert($(this).html());
		// var date = new Date();
		// date.setTime(parseInt($(this).html()));
		//var time=formatDate(" MM-DD h:m:s", date);
		//  $(this).attr("title",time);
		//  }
		//);
		$("#table_fight").tooltip({
			placement : 'top',
			selector : "a[rel=tooltip]",
			trigger : 'hover',
			title : 'hihidsadas'
		});
		page();
	});
	//隐藏所有的装备和技能，并且重置相应按键的颜色
	function hero_reset() {
		$(".equip").hide();
		$(".skill").hide();
		$(".skill_button").css("color", "black");
		$(".equip_button").css("color", "black");
	}
	function bag_reset() {
		$(".bag").hide();
		$(".bag_button").css("color", "black");
	}
	//对加入的道具进行遍历
	function doSort(type){
		//alert(type);
		$("#table_cell_prop tr:gt(0)").each(function() {
			var status = $("td:eq(1)", this).html();
			//alert(status);
			if (parseInt(status) == type) {
				//$("td:eq(3)", this).html("升级中");
				$(this).show();
			} else {
				$(this).hide();
				//$("td:eq(3)", this).html("升级完成");
			}
		});
	}
	//检测加入的道具
	function addProp(){
		$("#table_cell_prop tr:gt(0)").each(function() {
			//注意这里获得的是dom对象，不能使用val()方法
			var id=$("td:eq(0)", this).html();
			var num = $("td:eq(3)", this).children().get(0).value;
			if(num>0){
				alert(id+":"+num);				
			}
		});
	}
	function clearForm(){
		$("#table_cell_prop tr:gt(0)").each(function() {
			//注意这里获得的是dom对象，不能使用val()方法
			var num=$("td:eq(3)", this).children().get(0).value;
			$("td:eq(3)", this).children().get(0).value=0;
			//alert(num);
		});
	}
	function page(){
        $("div.holder").jPages({
	        containerID : "propCell",
	        previous : "←",
	        next : "→",
	        perPage : 20,
	        delay : 20
	      });
	}
	//检测是否可以进行修改操作
	function checkLogin(){
		var canLogin = $("#data_canLogin").html();
		if (canLogin == 0) {
			//$("#canLogin").val("未冻结");
			alert("必须先冻结账户才可以进行操作");
			return false;
		} else {
			//$("#canLogin").val("已冻结");
		}
		return true;
	}
	
	function addBuild(){
		var obid = $("#obid").value;
		if(!checkLogin()){return;};
		options['success']=refresh;options['url']='<%=request.getContextPath() %>/roledata/addBuild/${user.userid}/obid';
		$('#addBuildForm').ajaxSubmit(options); 
	}

</SCRIPT>
</head>
<body>
	<div id="tabs">
		<div class=" alert fade in">
			<strong>必须先更改状态为冻结才可以进行修改! 当前状态：
				<input id="canLogin" title="必须先更改状态为冻结才可以进行修改" value="${user.canLogin}"
					type="button"
					onclick="options['success']=refresh;options['url']='<%=request.getContextPath()%>/roledata/login/${user.userid}'; $('#basicForm').ajaxSubmit(options)">
			</strong>
		</div>
		<!-- tab 列表 -->
		<ul class="nav nav-tabs" id="myTab">
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#basic">基本信息</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#bag">背包</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#build">建筑</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#hero">武将</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#soldier">士兵</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#quest">任务</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#fight">战报</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#cell_prop">物品</a></li>
			<li><a data-toggle="tab"
				href="<%=request.getContextPath() %>/roledata/search/${user.userid}#cell_equip">装备</a></li>
		</ul>
		<!-- 基本信息 -->
		<div class="tab-pane active" id="basic">
			<form id="basicForm">
				<table id="table_basic" class=" table  table-condensed">
					<tbody>
						<tr>
							<td>I D<input id="userid" class=" disabled" type="text"
								disabled="" placeholder="${user.userid}" /></td>
							<td>爵位 <select id="cityLevel" name="cityLevel">
									<option value="0">平民</option>
									<option value="1">骑士</option>
									<option value="2">准男爵</option>
									<option value="3">男爵</option>
									<option value="4">子爵</option>
									<option value="5">伯爵</option>
									<option value="6">侯爵</option>
									<option value="7">公爵</option>
							</select>
							</td>
							<td>官位<input id="usertitle" class=" disabled" type="text"
								disabled="" /></td>
						</tr>
						<tr>
							<td>姓名 <input id="username" class=" disabled" type="text"
								disabled="" placeholder="${user.name}" />
							<td>军功<input id="militaryMedals" name="militaryMedals"
								value="${user.militaryMedals}" /></td>
							<td>国家<input id="nation" class=" disabled" type="text"
								disabled="" placeholder="${user.nativeId/1000}" /></td>
						</tr>
						<tr>
							<td>级别<input id="userlevel" class=" disabled" type="text"
								disabled="" placeholder="${user.level}" /></td>
							<td>政绩<input id="achieve" name="achieve"
								value="${user.achieve}" /></td>
							<td>州<input id="state" class=" disabled" type="text"
								disabled="" placeholder="${user.nativeId/100}" /></td>
						</tr>
						<tr>
							<td>经验<input id="exp" name="exp" value="${user.exp}" /></td>
							<td>充值<input id="" class=" disabled" type="text" disabled=""
								placeholder="0" /></td>
							<td>市<input id="city" class=" disabled" type="text"
								disabled="" placeholder="${user.nativeId/10}" /></td>
						</tr>
						<tr>
							<td>金币<input id="gameMoney" name="gameMoney"
								value="${user.gameMoney}" /></td>
							<td>上次登录<input id="loginTime" class=" disabled" type="text"
								disabled="" placeholder="" /></td>
							<td>县<input id="area" class=" disabled" type="text"
								disabled="" placeholder="${user.nativeId}" /></td>
						</tr>
						<tr>
							<td>钻石<input id="joyMoney" name="joyMoney"
								value="${user.joyMoney}" /></td>
							<td>总在线时间<input id="onlineTime" value="${user.onlineTime}"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<td>筹码<input id="chip" name="chip" value="${user.chip}" /></td>
						</tr>
					</tbody>
				</table>

				<input id="saveBasic" title="必须先更改状态为冻结才可以进行修改" value="保存"
					type="button"
					onclick="if(!checkLogin()){return;};options['success']=refresh;options['url']='<%=request.getContextPath() %>/roledata/basic/${user.userid}'; $('#basicForm').ajaxSubmit(options)">
			</form>
		</div>
		<!-- 背包信息 -->
		<div class="tab-pane active" id="bag">
			<button class="bag_button"
				onclick="bag_reset();$('#table_prop').show();$(this).css('color','red');">道具</button>
			<button class="bag_button"
				onclick="bag_reset();$('#table_equip').show();$(this).css('color','red');">装备</button>
			<i class="icon-plus"></i>
			<table id="table_prop" class=" table table-hover table-condensed bag">
				<thead>
					<tr>
						<th><a
							href="javascript:$('#table_prop').sortTable({onCol: 1, keepRelationships: true, sortType: 'numeric'})">序号</a></th>
						<th>名称</th>
						<th><a
							href="javascript:$('#table_prop').sortTable({onCol: 3, keepRelationships: true, sortType: 'numeric'})">数量</a></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${propdata}" var="data">
						<tr>
							<td><c:out value="${data.id}" /></td>
							<td><c:out value="${data.name}" /></td>
							<td><c:out value="${data.num}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table id="table_equip"
				class=" table table-hover table-condensed bag">
				<thead>
					<tr>
						<th><a
							href="javascript:$('#table_equip').sortTable({onCol: 1, keepRelationships: true, sortType: 'numeric'})">序号</a></th>
						<th>名称</th>
						<th><a
							href="javascript:$('#table_equip').sortTable({onCol: 3, keepRelationships: true, sortType: 'numeric'})">数量</a></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${equipdata}" var="data">
						<tr>
							<td><c:out value="${data.id}" /></td>
							<td><c:out value="${data.name}" /></td>
							<td><c:out value="${data.num}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 建筑 -->
		<div class="tab-pane active" id="build">
			<form id="addBuildForm">
			OriginalBuilding XML 对应id:<input type="text" name="obid" value="${obid}" />
			<button class="build_button"
				onclick="if(!checkLogin()){return;};options['success']=refresh;options['url']='<%=request.getContextPath() %>/roledata/addBuild/${user.userid}';$('#addBuildForm').ajaxSubmit(options);">添加</button>
			</form>
			<table id="table_build" class="table table-hover table-condensed">
			
				<thead>
					<tr>
						<th><a
							href="javascript:$('#table_build').sortTable({onCol: 1, keepRelationships: true, sortType: 'numeric'})">序号</a></th>
						<th>名称</th>
						<th>等级</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${builddata}" var="data">
						<tr>
							<td><c:out value="${data.id}" /></td>
							<td><c:out value="${data.name}" /></td>
							<td><c:out value="${data.level}" /></td>
							<td><c:out value="${data.status}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
		<!-- 将领 -->
		<div class="tab-pane active" id="hero">
			<table id="table_hero" class=" table table-hover table-condensed">
				<thead>
					<tr>
						<th><a
							href="javascript:$('#table_hero').sortTable({onCol: 1, keepRelationships: true, sortType: 'numeric'})">序号</a></th>
						<th>姓名</th>
						<th><a
							href="javascript:$('#table_hero').sortTable({onCol: 3, keepRelationships: true, sortType: 'numeric'})">品级</a></th>
						<th><a
							href="javascript:$('#table_hero').sortTable({onCol: 4, keepRelationships: true, sortType: 'numeric'})">等级</a></th>
						<th><a
							href="javascript:$('#table_hero').sortTable({onCol: 5, keepRelationships: true, sortType: 'numeric'})">攻击</a></th>
						<th><a
							href="javascript:$('#table_hero').sortTable({onCol: 6, keepRelationships: true, sortType: 'numeric'})">防御</a></th>
						<th><a
							href="javascript:$('#table_hero').sortTable({onCol: 7, keepRelationships: true, sortType: 'numeric'})">生命</a></th>
						<th><a
							href="javascript:$('#table_hero').sortTable({onCol: 8, keepRelationships: true, sortType: 'numeric'})">带兵数</a></th>
						<th>装备</th>
						<th>技能</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${herodata}" var="data">
						<tr>
							<td><c:out value="${data.id}" /></td>
							<td><c:out value="${data.name}" /></td>
							<td><c:out value="${data.color}" /></td>
							<td><c:out value="${data.level}" /></td>
							<td><c:out value="${data.attack}" /></td>
							<td><c:out value="${data.defence}" /></td>
							<td><c:out value="${data.hp}" /></td>
							<td><c:out value="${data.soldierNum}" /></td>
							<td><button class="equip_button"
									onclick="javascript:hero_reset();$('#equip${data.id}').show();$(this).css('color','red');$('#hero_equip').dialog('open');">查看</button></td>
							<td><button class="skill_button"
									onclick="javascript:hero_reset();$('#skill${data.id}').show();$(this).css('color','red');$('#hero_skill').dialog('open');">查看</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- 将领装备 -->
			<div id="hero_equip" class="hero_dialog">
				<c:forEach items="${heroequip}" var="list">
					<table id="equip${list.id}" class="equip table" title="将领装备">
						<thead>
							<tr>
								<td>序号</td>
								<td>名称</td>
								<td>等级</td>
								<td>品质</td>
								<td>强化</td>
								<td>攻击</td>
								<td>防御</td>
								<td>生命</td>
								<td>带兵数</td>
								<td>加制效果</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list.equip}" var="data">
								<tr>
									<td><c:out value="${data.prototype.id}" /></td>
									<td><c:out value="${data.prototype.equipmentName}" /></td>
									<td><c:out value="${data.prototype.equipmentLevel}" /></td>
									<td><c:out value="${data.prototype.qualityColor}" /></td>
									<td><c:out value="${data.prototype.strengthenLevel}" /></td>
									<td><c:out value="${data.prototype.attackPiont}" /></td>
									<td><c:out value="${data.prototype.defensePiont}" /></td>
									<td><c:out value="${data.prototype.liftPiont}" /></td>
									<td><c:out value="${data.prototype.soldierCount}" /></td>
									<td><c:out value="${data.effectId}" /></td>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
			</div>
			<!-- 将领技能 -->
			<div id="hero_skill" class="hero_dialog">
				<c:forEach items="${heroskill}" var="list">
					<table id="skill${list.id}" class="skill table" title="将领技能">
						<thead>
							<tr>
								<td>序号</td>
								<td>名称</td>
								<td>等级</td>
								<td>属性</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list.skill}" var="data">
								<tr>
									<td><c:out value="${data.id}" /></td>
									<td><c:out value="${data.name}" /></td>
									<td><c:out value="${data.level}" /></td>
									<td><c:out value="${data.attri}" /></td>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
			</div>
		</div>
		<!-- 士兵信息 -->
		<div class="tab-pane active" id="soldier">
			<form id="soldierForm">
				<table id="table_soldier" class=" table table-hover table-condensed">
					<thead>
						<tr>
							<th><a
								href="javascript:$('#table_soldier').sortTable({onCol: 1, keepRelationships: true, sortType: 'numeric'})">序号</a></th>
							<th>名称</th>
							<th><a
								href="javascript:$('#table_soldier').sortTable({onCol: 3, keepRelationships: true, sortType: 'numeric'})">数量</a></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${soldierdata}" var="data">
							<tr>
								<td><c:out value="${data.id}" /></td>
								<td><c:out value="${data.name}" /></td>
								<td><input name="${data.id}" type="text"
									value="${data.num}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<input id="saveSoldier" title="必须先更改状态为冻结才可以进行修改" value="保存"
					type="button"
					onclick="if(!checkLogin()){return;};options['success']=refresh;options['url']='<%=request.getContextPath() %>/roledata/soldier/${user.userid}'; $('#soldierForm').ajaxSubmit(options)">
			</form>
		</div>
		<!-- 任务 -->
		<div class="tab-pane active" id="quest">
			<form id="questForm">
				<table id="table_quest" class=" table table-hover table-condensed">
					<thead>
						<tr>
							<th><a
								href="javascript:$('#table_quest').sortTable({onCol: 1, keepRelationships: true, sortType: 'numeric'})">序号</a></th>
							<th>名称</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${questdata}" var="data">
							<tr>
								<td><c:out value="${data.id}" /></td>
								<td><c:out value="${data.name}" /></td>
								<td><c:if test="${data.status == '1'}">已接</c:if> <c:if
										test="${data.status == '2'}">未完成</c:if> <c:if
										test="${data.status == '3'}">完成</c:if> <c:if
										test="${data.status != 3}">
										<input value="完成" type="button"
											onclick="options['success']=refresh;options['url']='<%=request.getContextPath() %>/roledata/quest/${user.userid}/${data.id}'; $('#questForm').ajaxSubmit(options)">
									</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 战报 -->
		<div class="tab-pane active" id="fight">
			<table id="table_fight" class=" table table-hover table-condensed">
				<thead>
					<tr>
						<th>序号</th>
						<th><a
							href="javascript:$('#table_fight').sortTable({onCol: 2, keepRelationships: true, sortType: 'numeric'})">将领id</a></th>
						<th>(0攻击方/1防守方)</th>
						<th><a
							href="javascript:$('#table_fight').sortTable({onCol: 4, keepRelationships: true, sortType: 'numeric'})">时间</a></th>
						<th>类型</th>
						<th>结果(0失败/胜利)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${fightdata}" var="data">
						<tr>
							<td><c:out value="${data.id}" /></td>
							<td><c:out value="${data.heroId}" /></td>
							<td><c:out value="${data.isAttack}" /></td>
							<td><a href="#" rel="tooltip">${data.fightTime}</a></td>
							<td><c:out value="${data.type}" /></td>
							<td><c:out value="${data.result}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 道具-->
		<div class="tab-pane" id="cell_prop">
			<%
				int types[] = new int[40];
				for (int i = 0; i < types.length; i++) {
					types[i] = i;
				}
				pageContext.setAttribute("ary", types);
			%>
			<p>
				<c:forEach var="i" items="${ary}">
					<button class="bag_button" onclick="doSort(${i});page()">${i}</button>
				</c:forEach>
			</p>
			<p>
				<input
					onclick="if(!checkLogin()){return;};options['success']=refresh;options['url']='<%=request.getContextPath() %>/roledata/addProp/${user.userid}'; $('#addPropForm').ajaxSubmit(options)"
					type="button" value="保存" /> <input onclick="clearForm();"
					type="button" value="清空" />

			</p>
			<div class="holder"></div>
			<form id="addPropForm">
				<table id="table_cell_prop"
					class=" table table-hover table-condensed">
					<thead>
						<tr>
							<th><a
								href="javascript:$('#table_cell_prop').sortTable({onCol: 1, keepRelationships: true, sortType: 'numeric'})">序号</a></th>
							<th><a
								href="javascript:$('#table_cell_prop').sortTable({onCol: 2, keepRelationships: true, sortType: 'numeric'})">类型</a></th>
							<th>名称</th>
							<th>数量</th>
						</tr>
					</thead>
					<tbody id="propCell">
						<c:forEach items="${cell_prop}" var="data">
							<tr>
								<td><c:out value="${data.id}" /></td>
								<!-- <td><a href="javascript:doSort(${data.propsType})">${data.propsType}</a></td> -->
								<td><c:out value="${data.propsType}" /></td>
								<td><c:out value="${data.name}" /></td>
								<td><input name="${data.id}" value=0 /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div class="tab-pane" id="cell_equip"></div>
	</div>

	<!-- 隐藏的div，用于保存数据 -->
	<div id="data_hide">
		<div id="data_city">${user.cityLevel}</div>
		<div id="data_title">${user.title}</div>
		<div id="data_login">${user.lastLoginTime}</div>
		<div id="data_canLogin">${user.canLogin}</div>
	</div>
</body>
</html>