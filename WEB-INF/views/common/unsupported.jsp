<%--
	// ERROR PAGE 
--%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%><%--
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
--%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%--
--%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%--
--%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %><%--
--%><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><%--
--%><%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="context_path" value="${requestScope['javax.servlet.forward.context_path']}"/>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags --%>
<!-- Favicon  -->
<link rel="icon" type="image/x-ico" href="${context_path}/static/resources/images/favicon/star-favicon.ico?" />
<link rel="shortcut icon" type="image/x-icon" href="${context_path}/static/resources/images/favicon/star-favicon.ico?" />
<title><fmt:message key="common.title" /></title>
<!-- /////////////// base styles //////////////// -->
<link href="${context_path}/static/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${context_path}/static/resources/plugins/adminLTE/css/AdminLTE.min.css" rel="stylesheet">
<link href="${context_path}/static/resources/plugins/fontawesome/css/font-awesome.min.css" rel="stylesheet">
<!-- /////////////// Theme styles //////////////// -->
<style type="text/css">
.layout-error {
	background: #d2d6de;
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

.jumbotron p {
	font-size: 18px;
}

.layout-error .jumbotron h1 i.fa {
	font-size: 120px;
}

.browser-icon {
	border-top-left-radius: 2px;
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 2px;
    display: block;
    height: 90px;
    width: 90px;
    text-align: center;
    font-size: 45px;
    line-height: 90px;
}
.browser-icon.ie {
	background:#00c0ef !important; 
}
.browser-icon.ie {
	background:#00c0ef !important; 
}
.browser-icon.chrome {
	background:#ffc107 !important; 
}
.browser-icon.firefox {
	background:#ff5722 !important; 
}
a, a:visited {
	color : #fff !important;
	text-decoration: none;
}

a:hover, a:active {
	color : #fefefe !important;
}

</style>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if ie 6]><c:set var="ver" value="IE6"/><![endif]-->
<!--[if ie 7]><c:set var="ver" value="IE7"/><![endif]-->
<!--[if ie 8]><c:set var="ver" value="IE8"/><![endif]-->
</head>
<body class="hold-transition layout-error">

	<div class="container">
		<div class="header clearfix"><h3 class="text-muted"><fmt:message key="header.logo" /></h3></div>
		<div class="jumbotron">
			<h1><img src="${context_path}/static/resources/images/ban.png"/></h1>
			<h1><fmt:message key="common.error.browser"/></h1>
			<p class="lead"><fmt:message key="common.error.browser.details"><fmt:param><c:out value="${ver}"/></fmt:param></fmt:message></p>
		
			<hr/>
			<br/>
			
			<table width="100%" border="0">
				<tr>
					<td align="center">
						<a href="https://www.google.com/intl/ko_ALL/chrome/" title="Move to Chrome browser download page"><img src="${context_path}/static/resources/images/chrome.png" style="width:90px;"/></a>
					</td>
					<td align="center">
						<a href="https://www.mozilla.org/ko/firefox/new/" title="Move to Fire-fox browser download page"><img src="${context_path}/static/resources/images/firefox.png" style="width:90px;"/></a>
					</td>
					<td align="center">
						<a href="https://support.microsoft.com/ko-kr/help/17621/internet-explorer-downloads" title="Move to Internet Explorer browser download page"><img src="${context_path}/static/resources/images/ie.png" style="width:90px;"/></a>
					</td>
				</tr>
			</table>
			<br/>
			<br/>
		</div>
	</div>
</body>
</html>