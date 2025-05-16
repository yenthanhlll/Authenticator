<%@ include file="/WEB-INF/views/common/def.jsp"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:forTokens items="${path}" delims="/" varStatus="st"><c:if test="${st.index > 0 }"><c:set var="deep" value="${deep}../"/></c:if></c:forTokens>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags --%>
<!-- Favicon  -->
<link rel="icon" type="image/x-ico" href="${deep}static/resources/images/favicon/star-favicon.ico?" />
<link rel="shortcut icon" type="image/x-icon" href="${deep}static/resources/images/favicon/star-favicon.ico?" />
<title><fmt:message key="common.title" /></title>
<!-- /////////////// base styles //////////////// -->
<link href="${deep}static/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${deep}static/resources/plugins/fontawesome/css/font-awesome.min.css" rel="stylesheet">
<link href="${deep}static/resources/plugins/ionicons/css/ionicons.css" rel="stylesheet">
<link href="${deep}static/resources/plugins/icomoon/IcoMoon.css" rel="stylesheet">
<!-- /////////////// Theme styles //////////////// -->
<link href="${deep}static/resources/plugins/adminLTE/css/AdminLTE.min.css" rel="stylesheet">
<link href="${deep}static/resources/css/custom.css" rel="stylesheet">
<link href="${deep}static/resources/plugins/adminLTE/css/skins/skin-blue.min.css" rel="stylesheet">
<!-- /////////////// Custom styles for this template //////////////// -->
<link href="${deep}static/resources/css/page.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="${deep}static/resources/plugins/bootstrap/js/html5shiv.min.js"></script>
  <script src="${deep}static/resources/plugins/bootstrap/js/respond.min.js"></script>
<![endif]-->
<style type="text/css">
.layout-error {
	background: rgba(211, 211, 211, 0.7);
}
.layout-error > .container {
	width: 750px;
}
.layout-error .jumbotron {
	text-align: center;
}
.layout-error .jumbotron h1 {
	font-size: 42px;
}
.layout-error .jumbotron i.glyphicon {
	font-size: 120px;
}
</style>
</head>
<body class="hold-transition layout-error">
	<div class="container">
		<div class="header clearfix"><h3 class="text-muted"><fmt:message key="header.logo" /></h3></div>
		<div class="jumbotron">
			<h1 class="text-red"><i class="glyphicon glyphicon-ban-circle"></i></h1>
			<h1><fmt:message key="common.error"/></h1>
			<p class="lead"><fmt:message key="common.error.details"/></p>
		</div>
	</div>
<!-- /////////////////// base scripts ///////////////// -->
<script src="${deep}static/resources/plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${deep}static/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>