<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>QuickStart示例:<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link href="${ctx}/static/bootstrap/2.1.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.9.0/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/quickstart.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/messages_bs_cn.js" type="text/javascript"></script>
<!-- http://www.malsup.com/jquery/form/ -->
 <script src="${ctx}/static/jquery.form.js"></script>
 
<script src="${ctx}/static/bootstrap/2.1.0/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctx}/static/bootstrap/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet">

<link href="${ctx}/static/jqueryui/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/jqueryui/js/jquery-ui-1.9.1.js"></script>

<!-- 图表 -->
<script src="${ctx}/static/highcharts/js/highcharts.js" ></script>
<script src="${ctx}/static/highcharts/js/gray.js"></script>

<!--  分页插件-->
<!-- http://luis-almeida.github.com/jPages/ -->
<link rel="stylesheet" href="${ctx}/static/jpages/css/jPages.css">
<link rel="stylesheet" href="${ctx}/static/jpages/css/animate.css">
<link rel="stylesheet" href="${ctx}/static/jpages/css/style.css">
<script src="${ctx}/static/jpages/js/jPages.min.js"></script>
<!-- 自定义 -->
<script src="${ctx}/static/mycharts.js"></script>
<script src="${ctx}/static/mydialog.js"></script>
<script src="${ctx}/static/mydate.js"></script>
<script src="${ctx}/static/mycheck.js"></script>
<!-- table sort 
http://mitya.co.uk/scripts/Animated-table-sort-REGEXP-friendly-111
-->
<script src="${ctx}/static/jquery.tableSort.js"></script>
<!--选中table的颜色
  .table-hover tbody tr:hover td, .table-hover tbody tr:hover th {
    	background-color: red;
	  }
-->
<style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
<sitemesh:head/>
</head>

<body>
	<%@ include file="/WEB-INF/layouts/header.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<%@ include file="/WEB-INF/layouts/left.jsp"%>
			<div class="span9">
				<sitemesh:body />
			</div>
		</div>
		<hr>
		<footer>
			<%@ include file="/WEB-INF/layouts/footer.jsp"%>
		</footer>
	</div>
</body>
<script>
	//$(function() {
	//	$("#accordion").accordion();
	//});
</script>
</html>