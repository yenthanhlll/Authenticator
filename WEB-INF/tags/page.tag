<%--
    MMTH define tag file
    @author uy song
--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%-- 
--%><%@attribute name="styles" fragment="true" %><%-- 해당 페이지의 style --%><%-- 
--%><%@attribute name="javascripts" fragment="true" %><%-- 해당 페이지의 javascripts --%><%-- 
--%><%@attribute name="js" %><%-- 해당 페이지의 js plugin --%><%-- 
--%><%@attribute name="css" %><%-- 해당 페이지의 css plugin --%><%-- 
--%><%@attribute name="onload" %><%-- 해당 페이지의 시작 스크립트 함수 명 --%><%-- 
--%><%@attribute name="viewUriPrefix" type="java.lang.String" %><%-- ManagerViewController를 호출하며 기본적인 Page CRUD를 수행하고자 할 때 사용함. ManagerViewController 상속하지 않는 경우 입력하지 않아도 된다.  --%><%--
--%><%@attribute name="hasDetailsBox"  type="java.lang.Boolean" %><%-- icheck 사용여부 --%><%--
--%><%@attribute name="useIcheck"  type="java.lang.Boolean" %><%-- icheck 사용여부 --%><%--
--%><%@attribute name="useDateRange"  type="java.lang.Boolean" %><%-- dateRange 사용여부 --%><%--
--%><%@attribute name="useFilestyle"  type="java.lang.Boolean" %><%-- dateRange 사용여부 --%><%--    
--%><c:set var="timePeriodTypes" value="${applicationScope['com.dreammirae.mmth.runtime.code.TimePeriodTypes']}"/><%-- 
--%><c:set var="servlet_path" value="${requestScope['javax.servlet.forward.servlet_path']}"/><%-- 
--%><c:set var="context_path" value="${requestScope['javax.servlet.forward.context_path']}"/><%-- 
--%><c:if test="${not empty viewUriPrefix}"><c:set var="viewUriPrefix">${context_path}${viewUriPrefix}</c:set></c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags --%>
<!-- Favicon  -->	
<link rel="icon" type="image/x-ico" href="../../static/resources/images/favicon/motp-favicon.ico?"/> 
<link rel="shortcut icon" type="image/x-icon" href="../../static/resources/images/favicon/motp-favicon.ico?"/>
<title><fmt:message key="common.title"/></title> <%-- 
	권한 별 Color Theme을 다르게 가져가기 위해.. 
--%><sec:authorize access="hasAuthority('DEV')"><c:set var="skin_theme" value="black-light"/></sec:authorize><%-- 
--%><sec:authorize access="hasAuthority('PERMITTED')"><c:set var="skin_theme" value="purple"/></sec:authorize><%-- 
--%><sec:authorize access="hasAuthority('ADMIN')"><c:set var="skin_theme" value="blue"/></sec:authorize><%-- 
--%><sec:authorize access="hasAuthority('SUPER')"><c:set var="skin_theme" value="green"/></sec:authorize><%-- 
--%><sec:authorize access="hasAuthority('OPTEAM')"><c:set var="skin_theme" value="red"/></sec:authorize><%-- 
--%><sec:authorize access="hasAuthority('CSTEAM')"><c:set var="skin_theme" value="purple"/></sec:authorize>

<!-- /////////////// base styles //////////////// -->
<!--[if gt IE 9]><!--><script src="../../static/resources/plugins/pace/pace.js"></script><link href="../../static/resources/plugins/pace/themes/${skin_theme}/pace-theme-loading-bar.css" rel="stylesheet" /><!--<![endif]-->
<link href="../../static/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../../static/resources/plugins/fontawesome/css/font-awesome.min.css" rel="stylesheet">
<link href="../../static/resources/plugins/ionicons/css/ionicons.css" rel="stylesheet">
<link href="../../static/resources/plugins/icomoon/IcoMoon.css" rel="stylesheet">
<!-- /////////////// Theme styles //////////////// -->
<link href="../../static/resources/plugins/adminLTE/css/AdminLTE.min.css" rel="stylesheet">
<link href="../../static/resources/plugins/bootstrap-dialog/css/bootstrap-dialog.min.css" rel="stylesheet">

<link href="../../static/resources/plugins/adminLTE/css/skins/_all-skins.min.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script type="text/javascript">location.href="/unsupported";</script>
<![endif]-->
<!--[if lt IE 9]>
  <script src="../../static/resources/plugins/bootstrap/js/html5shiv.min.js"></script>
  <script src="../../static/resources/plugins/bootstrap/js/respond.min.js"></script>
<![endif]-->


<!-- /////////////// Styles for pages //////////////////////// -->

<c:forEach items="${css}" var="cssname"><%-- attribute for css --%>
<link href="../../static/resources/${cssname }.css" type="text/css" rel="stylesheet"/></c:forEach>
<c:if test="${useIcheck}">
<link href="../../static/resources/plugins/icheck/skins/square/${skin_theme}.css" type="text/css" rel="stylesheet"/>
</c:if>
<c:if test="${useFilestyle}">
<link href="../../static/resources/plugins/filestyle/css/fileinput.min.css" type="text/css" rel="stylesheet"/>
</c:if>
<c:if test="${useDateRange}">
<link href="../../static/resources/plugins/bootstrap-daterangepicker/daterangepicker.css" type="text/css" rel="stylesheet"/>
</c:if>

<!-- /////////////// Custom styles for this template //////////////// -->
<link href="../../static/resources/css/custom.css" rel="stylesheet">
<link href="../../static/resources/css/page.css" rel="stylesheet">
<jsp:invoke fragment="styles"/><%-- attribute for styles --%>
</head>
<body class="hold-transition skin-${skin_theme} sidebar-mini">

	<!-- Page wrapper -->
	<div class="wrapper">
	
		<%-- /////////////////////////////////////////// --%>
		<%-- PAGE HEADER --%>
		<%-- /////////////////////////////////////////// --%>
		<!-- header-wrapper -->
		<header class="main-header">
			
			<!-- Logo -->
			<a href="${not empty sessionScope['MMTH.ADMIN.HOME_URL'] ? sessionScope['MMTH.ADMIN.HOME_URL'] : '/'}" class="logo">
				<span class="logo-mini"><!-- mini logo for sidebar mini 50x50 pixels --><fmt:message key="header.logo.min" /></span> 
				<span class="logo-lg"><!-- logo for regular state and mobile devices --><fmt:message key="header.logo" /></span>
			</a>
			
			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button"> <span class="sr-only">Toggle navigation</span></a><!-- ./toggle button-->

				<div class="navbar-custom-menu">

					<ul class="nav navbar-nav">
						<%-- 상황에 따라 구현 적용, 우선 주석처리..
						<!-- Alarms-->
						<li class="dropdown notifications-menu" data-toggle="tooltip" title="<fmt:message key="header.alarm"/>" data-placement="left">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-flag"></i> <span class="label label-warning">1</span></a>
							<ul class="dropdown-menu">
								<li class="header"><fmt:message key="header.alarm"/></li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<li><a href="#"> <i class="fa fa-users text-aqua"></i>To be continue...</a></li>
									</ul>
								</li>
								<li class="footer"><a href="#"><fmt:message key="header.alarm.footer"/></a></li>
							</ul>
						</li>
						--%>
						<!-- Language -->
						<li class="dropdown messages-menu"  data-toggle="tooltip" title="<fmt:message key="header.lang"/>" data-placement="left">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-language"></i> <span class="label label-info"><c:out value="${currentLang }"/></span></a>
							<ul class="dropdown-menu">
								<li class="header"><fmt:message key="header.lang"/></li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<!-- start message --> 
										<c:forEach items="${supportedLanguages }" var="supLang">
										<li><a href="${context_path}${servlet_path}?locale=${supLang.key}"><c:out value="${supLang.value}"/></a></li></c:forEach>
										<!-- end message --> 
									</ul>
								</li>
							</ul>
						</li>

						<%-- 상황에 따라 구현 적용, 우선 주석처리..
						<!-- Help -->
						<li class="dropdown messages-menu help-menu" data-toggle="tooltip" title="<fmt:message key="header.help"/>" data-placement="left">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-info-circle"></i>
						</a>
							<ul class="dropdown-menu">
								<li class="header"><fmt:message key="header.help"/></li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<!-- start message --> 
										<li>
											To be continue... 
										</li>
										<!-- end message --> 
									</ul>
								</li>
								<li class="footer"><a href="#"><fmt:message key="header.help.footer"/></a></li>
							</ul>
						</li>
						--%>

						<!-- User Account: style can be found in dropdown.less -->
						<sec:authorize access="isAuthenticated()">
						<li class="dropdown user user-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
								<i class="moon moon-user-tie text-gray"></i> 
								<span class="hidden-xs"><c:out value="${sessionScope['MMTH.ADMIN.USERNAME']}"></c:out></span>
							</a>
							<ul class="dropdown-menu">
								<!-- User image -->
								<li class="user-header">
									<p><i class="moon moon-user-tie"></i> <span><c:out value="${sessionScope['MMTH.ADMIN.USERNAME']}"></c:out></span></p>
									<ul>
										<li><i class="fa fa-clock-o"></i> <fmt:message key="header.admin.lastLogin"><fmt:param value="${sessionScope['MMTH.LOGON.DATETIME']}"/></fmt:message></li>
										<li><i class="fa fa-circle"></i> <fmt:message key="header.admin.ip"><fmt:param value="${sessionScope['MMTH.ADMIN.LAST_REMOTE_ADDR']}"/></fmt:message></li>
									</ul>
								</li>
								 
								<!-- Menu Body -->
								<sec:authorize access="!(hasAnyAuthority('OPTEAM','CSTEAM'))">
								<li class="user-body">
									<div class="row">
										<div class="col-xs-12 text-center">
											<a href="#" data-toggle="modal" data-target="#adminProfileModal"><i class="fa fa-edit"></i> <fmt:message key="header.admin.profile"/></a>
										</div>
									</div> <!-- /.row -->
								</li>
								</sec:authorize>
								<!-- User Footer-->
								<li class="user-footer">
									
									<div class="pull-left">
										<a href="${context_path}${servlet_path}?homeUri=ok" class="btn btn-default btn-flat" data-toggle="tooltip" title="<fmt:message key="header.admin.homeurl.tooltip"/>" data-placement="bottom">
											<fmt:message key="header.admin.homeurl"/>
										</a>
									</div>
									
									<div class="text-center">
										<a href="${context_path}/logout" class="btn btn-danger btn-flat"><fmt:message key="header.admin.logout"/></a>
									</div>
								</li>
							</ul>
						</li>
						</sec:authorize>
					</ul>
				</div>
			</nav>
		</header>
		
		<%-- /////////////////////////////////////////// --%>
		<%-- PAGE SIDE BAR MENU --%>
		<%-- /////////////////////////////////////////// --%>
		
		
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu" data-widget="tree">
					
					<!-- FIDO -->
					<sec:authorize access="hasAnyAuthority('PERMITTED')">
					<li class="header"><fmt:message key="nav.title.user"/></li>
					</sec:authorize>
<%-- 					<li<c:if test="${servlet_path eq '/web/manager/hwuser'}"> class="active"</c:if>><a href="${context_path}/web/manager/hwuser"><i class="fa fa-users"></i> <span><fmt:message key="nav.user"/></span></a></li>					 --%>
					<sec:authorize access="hasAnyAuthority('OPTEAM','CSTEAM')">
					<li<c:if test="${servlet_path eq '/web/manager/branch'}"> class="active"</c:if>><a href="${context_path}/web/manager/branch"><i class="fa fa-users"></i> <span><fmt:message key="nav.manager"/></span></a></li>
					</sec:authorize>
					<sec:authorize access="hasAnyAuthority('PERMITTED','OPTEAM','CSTEAM')">
					<li<c:if test="${servlet_path eq '/web/manager/user'}"> class="active"</c:if>><a href="${context_path}/web/manager/user"><i class="fa fa-users"></i> <span><fmt:message key="nav.user"/></span></a></li>
					</sec:authorize>
					<sec:authorize access="hasAnyAuthority('OPTEAM')">
					<li<c:if test="${servlet_path eq '/web/manager/branchlog'}"> class="active"</c:if>><a href="${context_path}/web/manager/branchlog"><i class="fa fa-list-alt"></i> <span><fmt:message key="nav.branch.serviceLog"/></span></a></li>				
					</sec:authorize>
					
					<sec:authorize access="hasAnyAuthority('PERMITTED')">
					<li class="header"><fmt:message key="nav.title.biotp"/></li>
					<c:if test="${AUTH_METHOD_BIOTP_ENABLED or AUTH_METHOD_SAOTP_ENABLED }">
					<li<c:if test="${servlet_path eq '/web/manager/token'}"> class="active"</c:if>><a href="${context_path}/web/manager/token"><i class="fa fa-key"></i> <span><fmt:message key="nav.swtoken"/></span></a></li>
					<li<c:if test="${servlet_path eq '/web/manager/hwtoken'}"> class="active"</c:if>><a href="${context_path}/web/manager/hwtoken"><i class="fa fa-key"></i> <span><fmt:message key="nav.hwtoken"/></span></a></li>
					</c:if>
					<c:if test="${WEB_API_POLICY eq 'GLOBAL_WIBEE' }">
					<li<c:if test="${servlet_path eq '/web/manager/countrycode'}"> class="active"</c:if>><a href="${context_path}/web/manager/countrycode"><i class="fa fa-globe"></i> <span><fmt:message key="nav.countrycode"/></span></a></li>
					
					</c:if>
					<li class="header"><fmt:message key="nav.title.log"/></li>
					<li<c:if test="${servlet_path eq '/web/manager/servicelog'}"> class="active"</c:if>><a href="${context_path}/web/manager/servicelog"><i class="fa fa-list-alt"></i> <span><fmt:message key="nav.serviceLog"/></span></a></li>
					<li<c:if test="${servlet_path eq '/web/manager/issuance'}"> class="active"</c:if>><a href="${context_path}/web/manager/issuance"><i class="fa fa-registered"></i> <span><fmt:message key="nav.issuance"/></span></a></li>
					</sec:authorize>
					
					<sec:authorize access="hasAnyAuthority('ADMIN')">
					<li class="header"><fmt:message key="nav.title.admin.policy"/></li>
					<c:if test="${AUTH_METHOD_BIOTP_ENABLED or AUTH_METHOD_FIDO_ENABLED }">
					<li<c:if test="${servlet_path eq '/web/manager/metadata'}"> class="active"</c:if>><a href="${context_path}/web/manager/metadata"><i class="fa fa-certificate"></i> <span><fmt:message key="nav.policy.admin.metadata"/></span></a></li>
					<li<c:if test="${servlet_path eq '/web/manager/facets'}"> class="active"</c:if>><a href="${context_path}/web/manager/facets"><i class="fa fa-shield"></i> <span><fmt:message key="nav.policy.admin.facets"/></span></a></li>
					</c:if>
					<li<c:if test="${servlet_path eq '/web/manager/appagent'}"> class="active"</c:if>><a href="${context_path}/web/manager/appagent"><i class="fa fa-android"></i> <span><fmt:message key="nav.policy.admin.appagent"/></span></a></li>
					</sec:authorize>
					
					<sec:authorize access="hasAnyAuthority('DEV')">
					<li class="header"><fmt:message key="nav.title.policy"/></li>
					<c:if test="${AUTH_METHOD_BIOTP_ENABLED or AUTH_METHOD_FIDO_ENABLED }">
					<li<c:if test="${servlet_path eq '/web/manager/metadata'}"> class="active"</c:if>><a href="${context_path}/web/manager/metadata"><i class="fa fa-certificate"></i> <span><fmt:message key="nav.policy.metadata"/></span></a></li>
					<li<c:if test="${servlet_path eq '/web/manager/facets'}"> class="active"</c:if>><a href="${context_path}/web/manager/facets"><i class="fa fa-shield"></i> <span><fmt:message key="nav.policy.facets"/></span></a></li>
					</c:if>
					<li<c:if test="${servlet_path eq '/web/manager/appagent'}"> class="active"</c:if>><a href="${context_path}/web/manager/appagent"><i class="fa fa-android"></i> <span><fmt:message key="nav.policy.appagent"/></span></a></li>
					</sec:authorize>
					
					<!-- System settings -->
					<sec:authorize access="hasAnyAuthority('ADMIN', 'DEV')">
					<li class="header"><fmt:message key="nav.title.env"/></li>
					<li<c:if test="${servlet_path eq '/web/manager/audit'}"> class="active"</c:if>><a href="${context_path}/web/manager/audit"><i class="fa fa-flag"></i> <span><fmt:message key="nav.env.audit"/></span></a></li>
					<li<c:if test="${servlet_path eq '/web/manager/systemSettings'}"> class="active"</c:if>><a href="${context_path}/web/manager/systemSettings"><i class="fa fa-cogs"></i> <span><fmt:message key="nav.env.settings"/></span></a></li>
					<sec:authorize access="hasAuthority('SUPER')">
					<li<c:if test="${servlet_path eq '/web/manager/administrator'}"> class="active"</c:if>><a href="${context_path}/web/manager/administrator"><i class="fa fa-black-tie"></i> <span><fmt:message key="nav.env.admin"/></span></a></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('DEV')">
					<li<c:if test="${servlet_path eq '/web/manager/sql'}"> class="active"</c:if>><a href="${context_path}/web/manager/sql"><i class="fa fa-database"></i> <span><fmt:message key="nav.env.sql"/></span></a></li>
					</sec:authorize>
					</sec:authorize>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<%-- /////////////////////////////////////////// --%>
		<%-- PAGE CONTENTS --%>
		<%-- /////////////////////////////////////////// --%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<jsp:doBody />
		</div>
		<!-- /.content-wrapper -->

		<%-- /////////////////////////////////////////// --%>
		<%-- PAGE FOOTER --%>
		<%-- /////////////////////////////////////////// --%>

		<!-- main-footer -->
		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<fmt:message key="footer.ver"><fmt:param><c:out value="${applicationScope['application.version']}"/></fmt:param></fmt:message>
			</div>
			<strong class="text-muted"><fmt:message key="footer.copyright"><fmt:param><fmt:message key="footer.copyright.since"/></fmt:param></fmt:message></strong>
		</footer>
		<!-- ./main-footer -->

	</div>
	<!-- ./wrapper -->
	
	<!-- Modal ChangePwd -->
	<div class="modal fade" id="adminProfileModal" tabindex="-1" role="dialog" aria-labelledby="adminProfileModalLabel">
		<div class="modal-dialog modal-lg" role="document">
	    	<div class="modal-content">
	    
	      		<div class="modal-header mmth-popup">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title" id="adminProfileModalLabel"><fmt:message key="common.sessionAdmin.title" /></h4>
	      		</div>
		  		<form class="form-horizontal" id="adminProfileContext">
	      		<div class="modal-body"><br/>
	      			
	      			<!-- username -->	      			
	      			<div class="form-group">
	      				<label class="col-md-4 col-sm-5 control-label"><fmt:message key="administrator.label.username"/></label>
	      				<div class="col-md-7 col-sm-6"><input type="text" class="form-control formField" value="${sessionScope['MMTH.ADMIN.USERNAME']}" readonly="readonly" disabled="disabled" data-readonly="true"></div>
	      			</div>
	      			
	      			<div class="form-group">
	      				<label class="col-md-4 col-sm-5 control-label"><fmt:message key="administrator.label.adminType"/></label>
	      				<div class="col-md-7 col-sm-6"><input type="text" class="form-control formField" value="${sessionScope['MMTH.ADMIN.TYPE']}" readonly="readonly" disabled="disabled" data-readonly="true"></div>
	      			</div>
	      	
	              	<div class="form-group">
	      				<label class="col-md-4 col-sm-5 control-label"><fmt:message key="administrator.label.tsLastLogin"/></label>
	      				<div class="col-md-7 col-sm-6"><input type="text" class="form-control formField" value="${sessionScope['MMTH.LOGON.DATETIME']}" readonly="readonly" disabled="disabled" data-readonly="true"></div>
	      			</div>
	              	
	              	<div class="form-group">
	      				<label class="col-md-4 col-sm-5 control-label"><fmt:message key="administrator.label.lastRemoteAddr"/></label>
	      				<div class="col-md-7 col-sm-6"><input type="text" class="form-control formField" value="${sessionScope['MMTH.ADMIN.LAST_REMOTE_ADDR']}" readonly="readonly" disabled="disabled" data-readonly="true"></div>
	      			</div>
	      			
	      			<div class="form-group">
	      				<label class="col-md-4 col-sm-5 control-label"><fmt:message key="common.regDateTime"/></label>
	      				<div class="col-md-7 col-sm-6"><input type="text" class="form-control formField" value="${sessionScope['MMTH.ADMIN.REG']}" readonly="readonly" disabled="disabled" data-readonly="true"></div>
	      			</div>
	      			
	      			<div class="form-group">
	      				<label class="col-md-4 col-sm-5 control-label"><fmt:message key="common.updateDateTime"/></label>
	      				<div class="col-md-7 col-sm-6"><input type="text" class="form-control formField" value="${sessionScope['MMTH.ADMIN.UPDATE']}" readonly="readonly" disabled="disabled" data-readonly="true"></div>
	      			</div>
	      			
	      			<hr/>
	      			
	      			<!-- password  -->
	              	<tag:formItem formField="false" valueKey="admin-password-prev" password="true" labelKey="administrator.label.password" maxLength="100" placeholderKey="administrator.label.password.placeholer"/>
					<!-- password  -->
	              	<tag:formItem formField="false" valueKey="admin-password" password="true" labelKey="administrator.changePassword.password" maxLength="100" placeholderKey="administrator.label.password.placeholer"/>
	              	
	              	<!-- password confirm -->
	              	<tag:formItem formField="false" valueKey="admin-passwordConfirm" password="true" labelKey="administrator.label.password.confirm" maxLength="100"/>
	              	
	              	
	              	<div class="form-group">
	      				<div class="col-md-offset-4 col-sm-offset-5 col-md-7 col-sm-6 text-right">
	      					<button type="button" id="btn-adminProfileSave" class="btn btn-md btn-primary mmth-btn " style=""><i class="fa fa-save"></i> &nbsp;<fmt:message key="btn.update"/></button>
	      				</div>
	      			</div>
	              	
			
	      		</div>
				</form>
	    	</div>
		</div>
	</div>

<div id="overlay" style="display: none;text-align: center">
	<img src="../../static/resources/images/loading-img.gif" style="display: none;" id="overlay-loading"/>
</div>
<!-- /////////////////// base scripts ///////////////// -->
<script src="../../static/resources/plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="../../static/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<!-- /////////////// Theme scripts //////////////// -->
<script src="../../static/resources/plugins/slimScroll/jquery.slimscroll.js"></script>
<script src="../../static/resources/plugins/adminLTE/js/adminlte.min.js"></script>
<script src="../../static/resources/plugins/bootstrap-dialog/js/bootstrap-dialog.js"></script>
<c:forEach items="${js}" var="jsname"><%-- attribute for js --%>
<script type="text/javascript" src="../../static/resources/${jsname}.js"></script></c:forEach>
<c:if test="${useIcheck}">
<script src="../../static/resources/plugins/icheck/icheck.min.js"></script>
</c:if>
<c:if test="${useFilestyle}">
<script src="../../static/resources/plugins/filestyle/js/bootstrap-filestyle.min.js"></script>
</c:if>
<c:if test="${useDateRange}">
<script src="../../static/resources/plugins/bootstrap-daterangepicker/moment.js"></script>
<script src="../../static/resources/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
</c:if>
<!-- /////////////// scripts for pages //////////////////////// -->
<script src="../../static/resources/js/mmthUtils.js"></script>
<script type="text/javascript">MMTHUtils.isDebug = false;</script>
<script type="text/javascript"><!-- //////////// message bundle //////////// -->
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DEFAULT] = '<fmt:message key="common.alert.information"/>';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_INFO] = '<fmt:message key="common.alert.information"/>';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_PRIMARY] = '<fmt:message key="common.alert.information"/>';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_SUCCESS] = '<fmt:message key="common.alert.success"/>';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_WARNING] = '<fmt:message key="common.alert.warning"/>';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DANGER] = '<fmt:message key="common.alert.danger"/>';
BootstrapDialog.DEFAULT_TEXTS['OK'] = '<fmt:message key="btn.ok"/>';
BootstrapDialog.DEFAULT_TEXTS['CANCEL'] = '<fmt:message key="btn.cancel"/>';
BootstrapDialog.DEFAULT_TEXTS['CONFIRM'] = '<fmt:message key="btn.confirmation"/>';
BootstrapDialog.configDefaultOptions({ animate: false});
MMTHUtils.bundle['common.desc.saved'] = '<fmt:message key="common.desc.saved"/>';
MMTHUtils.bundle['common.desc.notSaved'] = '<fmt:message key="common.desc.notSaved"/>';
MMTHUtils.bundle['common.desc.updated'] = '<fmt:message key="common.desc.updated"/>';
MMTHUtils.bundle['common.desc.changed'] = '<fmt:message key="common.desc.changed"/>';
MMTHUtils.bundle['common.desc.deleted'] = '<fmt:message key="common.desc.deleted"/>';
MMTHUtils.bundle['common.desc.required'] = '<fmt:message key="common.desc.required"/>';
MMTHUtils.bundle['common.desc.invalid'] = '<fmt:message key="common.desc.invalid"/>';
MMTHUtils.bundle['common.desc.init'] = '<fmt:message key="common.desc.init"/>';
MMTHUtils.bundle['common.desc.dereg'] = '<fmt:message key="common.desc.dereg"/>';
MMTHUtils.bundle['common.desc.duplicate'] = '<fmt:message key="common.desc.duplicate"/>';
MMTHUtils.bundle['common.blank'] = '<fmt:message key="common.blank"/>';
MMTHUtils.view.urlPrefix = '${viewUriPrefix}';
MMTHUtils.view.pagination.currentPage = 1;
MMTHUtils.view.skinName = '${skin_theme}';
</script>
<jsp:invoke fragment="javascripts"/><%-- attribute for javascripts --%>
<script type="text/javascript">
var pageOnload = '${onload}';
$(document).ready(function(){
	
	// 상세보기 box를 설정함
	if (typeof(configDetailsBox) != 'undefined') {
		configDetailsBox();	
	}
	
	// 검색 조건 이벤트를 생성함
	addSearchFieldEvents();

	// 버튼 이벤트 활성화
	addBtnEvents();
	
	// plugin 로드
	addPlugins();

	// page init
	if (MMTHUtils.view.urlPrefix) {
		initRestPage();	
	}
	
	// 페이지 로드 시 페이지 별 함수 호출
	if (pageOnload) {
		executeFunctionByName(pageOnload, window);		
	}
	
	$("body").tooltip({ selector: '[data-toggle=tooltip]' });
	
	$('.searchText').on('click', function(){
		$(this).val('');
	});
	
	//////////// logout timer ////////////
	logoutStarter(parseInt("${SESSION_TIMEOUT}"), function() { location.href='${context_path}/logout';});
});


//
// / Request to server .. 
//

// init request
function initRestPage() {
	
	// init url을 호출하여 페이지 리스트와 수치 데이터를 조회한다.
	var caller = ajaxUtil(MMTHUtils.view.initUrl());
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			// table data
			if (resp.data.pageContents) {
				setTableContentData(resp.data.pageContents);
			}
			
			if (resp.data.stats) {
				// 수치정보
				setStatsData(resp.data.stats);	
			}

			if (typeof initRestPageImp == 'function') {
				initRestPageImp(resp.data);
			}
		}
	});
	caller.execute();	
}

 // 페이지 조회
function getPageList(pageNum){
	 
	var param = {
			pageNum : pageNum
	};
	
	$('.mmth-fieldFilter button.fieldFilter').each(function(){
		var $el = $(this);
		var value = $el.data('value');
		
		if (value !== '') {
			param[$el.data('key')] = value;
		}

	});
	
	$('.mmth-searchFieldFilter').each(function(){
		var $el = $(this); 
		var key = $el.find('.searchLabel').data('key');
		var value = $el.find('input.searchText').val();
		if (value !== '') {
			param[key] = value;	
		}
	});
	
	$('.mmth-listFilter > li.active > a').each(function(){
		var value = $(this).data('value');
		if (value !== '') {
			var key = $(this).parent().parent().data('key');
			param[key] = value;	
		}
	});
	
	if (typeof $.fn.daterangepicker != 'undefined') {
		
		var $dr = $('.mmth-dateRangeFieldFilter').find('input.dateRangePicker');
		
		if ($dr && !$dr.val().isEmpty()) {
			var dateRange = $dr.data('daterangepicker');
			param[$dr.data('startkey')] = dateRange.startDate._d.formatDate();
			param[$dr.data('endkey')] = dateRange.endDate._d.formatDate();
		}	
	}
	
	if (typeof getPageListImp != 'undefined') {
		getPageListImp(param);
	}

	var caller = ajaxUtil(MMTHUtils.view.pageListUrl());
	caller.setData(param);
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else {
			setTableContentData(resp.data.pageContents);
		}
	});
	caller.execute();
}
 
//Ajax for details 
function getDetails(id){
	
	resetDetailsData();
	
	var caller = ajaxUtil(MMTHUtils.view.detailsUrl());
	caller.setData({'id' : id});

	caller.setResponseDataHandler(function(resp) {
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else {
			if (typeof fillDetailCallback == 'function') {
				fillDetailCallback(resp.data);
			} else {
				fillDetails(resp.data.details);	
			}
		}
	});

	caller.execute(); 
}

function exportExcel(){
	 
	var param = {};
	
	$('.mmth-fieldFilter button.fieldFilter').each(function(){
		var $el = $(this);
		var value = $el.data('value');
		
		if (value !== '') {
			param[$el.data('key')] = value;
		}

	});
	
	$('.mmth-searchFieldFilter').each(function(){
		var $el = $(this); 
		var key = $el.find('.searchLabel').data('key');
		var value = $el.find('input.searchText').val();
		if (value !== '') {
			param[key] = value;	
		}
	});
	
	$('.mmth-listFilter > li.active > a').each(function(){
		var value = $(this).data('value');
		if (value !== '') {
			var key = $(this).parent().parent().data('key');
			param[key] = value;	
		}
	});
	
	if (typeof $.fn.daterangepicker != 'undefined') {
		
		var $dr = $('.mmth-dateRangeFieldFilter').find('input.dateRangePicker');
		
		if ($dr && !$dr.val().isEmpty()) {
			var dateRange = $dr.data('daterangepicker');
			param[$dr.data('startkey')] = dateRange.startDate._d.formatDate();
			param[$dr.data('endkey')] = dateRange.endDate._d.formatDate();
		}	
	}
	
	if (typeof getPageListImp != 'undefined') {
		getPageListImp(param);
	}

	var caller = ajaxUtil(MMTHUtils.view.exportUrl());
	caller.setData(param);
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else {
			if (resp.data.file != null){
				
			}
// 			setTableContentData(resp.data.pageContents);
		}
	});
	caller.execute();
}

function saveDetails(obj){
	
	if (obj) {
		if (typeof saveDetailsImp == 'function') {
			if (!saveDetailsImp(obj)) {
				return;
			}
		}
	}

	var caller = ajaxUtil(MMTHUtils.view.saveUrl());
	caller.setContextFormId('mmth-detailsContext');
	caller.setData(obj);
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages) {
			if (resp.generalMessages)
				handleGeneralMessage(resp.generalMessages);
		} else {
			fillDetails(resp.data.details);

			if (resp.data.stats) {
				setStatsData(resp.data.stats);
			}
			
			if (resp.data.pageContents) {
				setTableContentData(resp.data.pageContents);
				resetFiledFilter();
			}


			BootstrapDialog.alert({
			    type: BootstrapDialog.TYPE_SUCCESS,
			    message: MMTHUtils.bundle['common.desc.saved']
			});
		}
	});
	
	caller.execute();
}

function deleteData(id){
	
	if (typeof deleteDataImp == 'function') {
		if (!deleteDataImp()) {
			return;
		}
	}
	
	BootstrapDialog.confirm({
		type: BootstrapDialog.TYPE_WARNING,
	    message: '<fmt:message key="common.desc.deleteConfirm"/>',
	    callback : function(result) {
	    	if (result) {
	    		callDeleteData(id);
	    	}
	    }
	});
}

function callDeleteData(id){
	
	var caller = ajaxUtil(MMTHUtils.view.deleteUrl());
	caller.setData({'id' : id});
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages) {
			if (resp.generalMessages)
				handleGeneralMessage(resp.generalMessages);
		} else {
			if (resp.data.stats) {
				setStatsData(resp.data.stats);
			}
			
			if (resp.data.pageContents) {
				setTableContentData(resp.data.pageContents);
				resetFiledFilter();
			}
			
			$('#mmth-detailsBox').boxWidget('collapse');
			
			BootstrapDialog.alert({
			    type: BootstrapDialog.TYPE_SUCCESS,
			    message: MMTHUtils.bundle['common.desc.deleted']
			});
		}

	});
	
	caller.execute();
}

//
// / callback functions
//

// 수치 정보
function setStatsData(stats){
	$('.mmth-statsInfo .statsItem').each(function(){
		var key = $(this).data('key');
		$(this).text(stats[key]);
	});
	
	if (typeof setStatsDataImp == 'function') {
		setStatsDataImp(stats);
	}
}

//pagination contents 생성
function setTableContentData(pageContents){

	var tableHandler = table('mmth-page-body').removeAllRows();

	if (pageContents && pageContents.total > 0) {
		// 현재페이지 저장
		MMTHUtils.view.pagination.currentPage = pageContents.currentPage;
		
		var totLen = pageContents.total;
		var stIdx = pageContents.startIdx;

		// 테이블 인덱스
		MMTHUtils.view.pagination.cellContentFuncs[0] = function(rowData, cellNum, rowNum){
			return totLen - (stIdx - 1) - rowNum;
		};
		
		var cellAttrFunc =  MMTHUtils.view.pagination.cellAttrFunc || {'class' : 'text-center'};
		
		// 테이블 데이터 채우기
		tableHandler.addRows(pageContents.contents, MMTHUtils.view.pagination.cellContentFuncs, null, cellAttrFunc);
		
		// 페이지 처리
		pagination('mmth-page-pagination', pageContents, function(){
			$('#mmth-page-pagination a').on('click', function(){
				getPageList($(this).data('pagenum'));
			})
		});

		// footer 및 message
		$('#paginationFooter').removeClass('hidden');
		$('#mmth-list-empty-Message').addClass('hidden');
	} else {
		// footer 및 message
		$('#paginationFooter').addClass('hidden');
		$('#mmth-list-empty-Message').removeClass('hidden');
	}
	
	
	
	if (typeof setTableContentDataImp == 'function') {
		setTableContentDataImp(pageContents);
	}
	
	setDateRange(pageContents);
	
	$('.btn-detailsBox').off('click');
	
	$('.btn-detailsBox').on('click', function(){
		getDetails($(this).data('id'));
	});
}

// pagination 설정
function pagination(elementId, pageContents, callbackGotoEvent){
	
	var el = elmt(elementId).removeChildren();
	
	var currentPage = pageContents.currentPage;
	var totalPageSize = pageContents.totalPageSize;

	var stIdx = (Math.floor((currentPage-1) / 5) * 5) + 1 ;
	
	var aTag;
	if (stIdx > 1) {
		var laquo = el.appendAndGetChild('li');
		aTag = laquo.appendAndGetChild('a', {'href' : '#', 'data-pagenum' : (stIdx - 1) });
		aTag.val('&laquo;');	
	}

	for (var i = 0, pageNum, liTag, attr; i < 5; i++) {
		
		pageNum = (stIdx + i);
		
		if (pageNum > totalPageSize) {
			break;
		}
		
		attr = pageNum == currentPage ? {'class' : 'active'} : null;

		var li = el.appendAndGetChild('li', attr);
		
		aTag = li.appendAndGetChild('a', {'href' : '#', 'data-pagenum' : pageNum });
		aTag.val(''+pageNum);
		
	}
	
	if (totalPageSize > (stIdx + 4)) {
		var raquo = el.appendAndGetChild('li');
		aTag = raquo.appendAndGetChild('a', {'href' : '#', 'data-pagenum' : (stIdx + 5) });
		aTag.val('&raquo;');
	}
	
	callbackGotoEvent();
}

function setDateRange(pageContents){

	if (typeof $.fn.daterangepicker != 'undefined' && pageContents.tsFrom && pageContents.tsTo) {
		
		var $dr = $('.mmth-dateRangeFieldFilter').find('input.dateRangePicker');
		
		$dr.data('daterangepicker').setStartDate(pageContents.tsFrom);
		$dr.data('daterangepicker').setEndDate(pageContents.tsTo);

	}
}


//상세정보 
function fillDetails(details){
	
	setContextData(details);
	
	if (details.id == '${NEW_ID}') {
		$('#mmth-detailsBox').find('.titleDetails').hide();
		$('#mmth-detailsBox').find('.titleNew').show();
		
		$('#mmth-detailsBox').find('#ctx-btn-detailsDelete').hide();
		
		changeToEditable();
	} else {
		$('#mmth-detailsBox').find('.titleDetails').show();
		$('#mmth-detailsBox').find('.titleNew').hide();
		
		$('#mmth-detailsBox').find('#ctx-btn-detailsDelete').show();
		
		changeToEditDisable();
	}
	
	if (typeof fillDetailsImp == 'function') {
		fillDetailsImp(details);
	}
	
	$('#mmth-detailsBox').boxWidget('expand');

}

//
// / Page events
//

// Search field form events
function addSearchFieldEvents(){
	
	// list field filter
	$('.mmth-fieldFilter ul.dropdown-menu > li > a').on('click', function(){
		
		var $el = $(this);
		var $btn = $el.parents('.btn-group').find('.btn');
		
		$btn.children().first().text($el.data('label'));
		$btn.data('value', $el.data('value'));
		
		getPageList(1);
		
		
	});

	// search field filter
	var data = $('.mmth-searchFieldFilter ul.dropdown-menu > li:first-child > a').data();
	
	if (data) {
		var $btn = $('.mmth-searchFieldFilter > .input-group-btn > button');
		$btn.data('key', data['value']);
		$btn.children().first().text(data['label']);
	}

	$('.mmth-searchFieldFilter ul.dropdown-menu > li > a').on('click', function(){
		var $el = $(this);
		var $btn = $el.parents('.input-group-btn').find('.btn');
		
		$btn.children().first().text($el.data('label'));
		$btn.data('key', $el.data('value'));

		$el.parents('.mmth-searchFieldFilter').find('input.searchText').val('');
		
	});
	
	
	$('.mmth-searchFieldFilter').find('.btn-search').on('click', function(){
		getPageList(1);
	});
	
	$('.mmth-searchFieldFilter').find('.searchText').keyup(function(event){
		if (event.keyCode == 13) { // when press an 'Enter'
			getPageList(1);
		}
	});
	
	$('.mmth-listFilter > li > a').on('click', function(){
		$('.mmth-listFilter > li.active').removeClass('active');
		$(this.offsetParent).addClass('active');
		getPageList(1);
	});
}

function resetFiledFilter(){
	$('.mmth-fieldFilter').each(function(){
		var data = $(this).find('ul.dropdown-menu > li:first-child > a').data();
		var btn = $(this).find('button');
		btn.data('value', data.value);
		btn.find('span:FIRST-CHILD').text(data.label);
	});
	
	$('.mmth-searchFieldFilter').find('input.searchText').val('');
	
	var data = $('.mmth-searchFieldFilter ul.dropdown-menu > li:first-child > a').data();
	
	if (data) {
		var $btn = $('.mmth-searchFieldFilter > .input-group-btn > button');
		$btn.data('key', data['value']);
		$btn.children().first().text(data['label']);
	}
}


//Button events
function addBtnEvents() {
	
	// 수정하기 버튼
	$('#btn-detailEdit').on('click', function(){
		changeToEditable();
	});
	
	// 저장하기 버트
	$('#btn-detailSave').on('click', function(){
	
		var obj = new Object();
		$('#mmth-detailsBox .formField').each(function(){
			if (this.type == 'checkbox') {
				obj[this.name] = this.checked ? true : false;
			} else {
				obj[this.name] = this.value;
			}
		});
		
		saveDetails(obj);
	});
	
	// 삭제하기 버튼
	$('#btn-detailsDelete').on('click', function(){
		var id = $('#mmth-detailsBox #id').val();
		deleteData(id);
	});
	
	$('#btn-adminProfileSave').on('click', function(){
		removeFieldMessage('admin-passwordConfirm');
		removeFieldMessage('admin-password');
		removeFieldMessage('admin-password-prev');
		
		var pwd = $('#admin-password').val();
		var prev = $('#admin-password-prev').val();
		// init url을 호출하여 페이지 리스트와 수치 데이터를 조회한다.
		var caller = ajaxUtil('/web/manager/sessionAdmin/rest/adminChangePassword');
		caller.setData({'admin-password' : pwd, "admin-password-prev" : prev});
		caller.setResponseDataHandler(function(resp){
			
			if (resp.hasMessages && resp.generalMessages) {
				handleGeneralMessage(resp.generalMessages);
			} else if(!resp.hasMessages){
				$('#admin-password').val('');
				$('#admin-passwordConfirm').val('');
				$('#admin-password-prev').val('');
				BootstrapDialog.alert({
				    type: BootstrapDialog.TYPE_SUCCESS,
				    message: MMTHUtils.bundle['common.desc.saved']
				});
			}
		});
		caller.execute();
		
	});
	$('#admin-password, #admin-passwordConfirm').keyup(function() {
		var value = $('#admin-password').val();
		var confirm = $('#admin-passwordConfirm').val();
		
		if (value == confirm) {
			removeFieldMessage('admin-passwordConfirm');
			setFieldMessage('admin-passwordConfirm', '<fmt:message key="administrator.validate.password.match"/>', true);
		} else {
			setFieldMessage('admin-passwordConfirm', '<fmt:message key="administrator.validate.password.missmatch"/>');
		}
	});

}

//  plugin customization if it is loaded..
function addPlugins(){
	
	if (typeof(addIcheckPlugin) != 'undefined'){
		addIcheckPlugin();
	}
	
	if (typeof(addDateRangePlugin) != 'undefined'){
		addDateRangePlugin();
	}
	
	if (typeof(addFilestylePlugin) != 'undefined'){
		addFilestylePlugin();
	}
}


//상세보기 초기화
function resetDetailsData() {
	$('#mmth-detailsContext').find('.form-control').val('');
	removeContextMessages(byId('mmth-detailsContext'));
}

//폼 변경 가능하게()
function changeToEditable(){
	
	var $detailsBox = $('#mmth-detailsBox');

	$detailsBox.find('input.form-control:not([data-readonly])').removeAttr('readonly');
	$detailsBox.find('select.form-control:not([data-readonly])').removeAttr('disabled');
	$detailsBox.find('textarea.form-control:not([data-readonly])').removeAttr('disabled');
	$detailsBox.find('.form-control[data-disabled]').attr('disabled', 'disabled');
	
	/*
	if (typeof $.fn.iCheck != 'undefined') {
		$detailsBox.find('input.form-control[type=checkbox]:not([data-readonly])').iCheck('enable');	
	} else {
		$detailsBox.find('input.form-control[type=checkbox]:not([data-readonly])').removeAttr('disabled');
	}*/
	
	$detailsBox.find('#btn-detailEdit').hide();
	$detailsBox.find('#btn-detailSave').show();
		
}

//폼 변경 불가능하게
function changeToEditDisable(){

	var $detailsBox = $('#mmth-detailsBox');

	$detailsBox.find('input.form-control:not([data-readonly])').attr('readonly', 'readonly');
	$detailsBox.find('select.form-control:not([data-readonly])').attr('disabled', 'disabled');
	$detailsBox.find('textarea.form-control:not([data-readonly])').attr('disabled', 'disabled');
	
	$detailsBox.find('#btn-detailEdit').show();
	$detailsBox.find('#btn-detailSave').hide();
}

// element show/hide
function show(el, visiable) {
	if (visiable) {
		$(el).show();
	} else {
		$(el).hide();
	}
}

// 일반 에러 메시지 처리
function handleGeneralMessage(generalMessages){
	
	var arr = [];
	for (var i = 0, len = generalMessages.length; i < len; i++) {
		arr.push('<p>' +generalMessages[i]+ '</p>')
	}
	
	arr.push('<p class="label label-info"><fmt:message key="common.desc.confirm"/></p>');
	
	
	BootstrapDialog.confirm({
		type: BootstrapDialog.TYPE_WARNING,
	    message: arr.join(''),
	    callback : function(result) {
	    	if (result) {
	    		location.reload();
	    	}
	    }
	});
	
}
</script>
<c:if test="${hasDetailsBox}">
<script type="text/javascript">
function configDetailsBox(){

	$('#mmth-detailsBox').on('expanded.boxwidget', function(params){
		$(params.currentTarget).show();
		location.hash = params.currentTarget.id; 
	}).on('collapsed.boxwidget', function(params){
		$(params.currentTarget).find('.form-control').val('');
		$(params.currentTarget).hide();
		location.hash = '#mmth-pageList';
	});
	
	$('#mmth-detailsBox').find('.form-control[data-readonly]').attr('readonly', 'readonly');
}
</script>
</c:if>
<c:if test="${useIcheck}">
<script type="text/javascript">
function addIcheckPlugin(){
	// check box
	if (typeof $.fn.iCheck != 'undefined') {
		$('input[type=checkbox]').iCheck({
			 checkboxClass: 'icheckbox_square-${skin_theme}',
		});
	} // ./ iCheck
	
}
</script>
</c:if>
<c:if test="${useDateRange}">
<script type="text/javascript">
function addDateRangePlugin(){
	// date picker
	if (typeof $.fn.daterangepicker != 'undefined') {
		// 조회 조건의 날짜 범위를 localization을 위한 설정

		$('input.dateRangePicker').daterangepicker({
			locale: {
				format: 'YYYY-MM-DD',
				separator: ' ~ ',
				daysOfWeek: [
							'<fmt:message key="common.week.shorten.SUN"/>',
		                    '<fmt:message key="common.week.shorten.MON"/>',
		                    '<fmt:message key="common.week.shorten.TUE"/>',
		                    '<fmt:message key="common.week.shorten.WED"/>',
		                    '<fmt:message key="common.week.shorten.THU"/>',
		                    '<fmt:message key="common.week.shorten.FRI"/>',
		                    '<fmt:message key="common.week.shorten.SAT"/>'
		                 ],
				monthNames : [
							'<fmt:message key="common.mon.jan"/>',
							'<fmt:message key="common.mon.feb"/>',
							'<fmt:message key="common.mon.mar"/>',
							'<fmt:message key="common.mon.apr"/>',
							'<fmt:message key="common.mon.may"/>',
							'<fmt:message key="common.mon.jun"/>',
							'<fmt:message key="common.mon.jul"/>',
							'<fmt:message key="common.mon.aug"/>',
							'<fmt:message key="common.mon.sep"/>',
							'<fmt:message key="common.mon.oct"/>',
							'<fmt:message key="common.mon.nov"/>',
							'<fmt:message key="common.mon.dec"/>',
		                 ],
				cancelLabel: '<fmt:message key="btn.cancel"/>',
				applyLabel: '<fmt:message key="btn.apply"/>',
				customRangeLabel : '<fmt:message key="common.range.custom"/>'
		    },
		    autoApply: false,
		    minDate: moment("${applicationScope['application.since']}"),
		    maxDate: moment(),
		    autoUpdateInput: true,
		    alwaysShowCalendars : true,
		    applyClass : 'btn-info mmth-btn',
		    ranges: {
		    	'<fmt:message key="common.range.today"/>': [moment(), moment()],
		        '<fmt:message key="common.range.yesterday"/>': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
		        '<fmt:message key="common.range.last7days"/>': [moment().subtract(6, 'days'), moment()],
		        '<fmt:message key="common.range.thisMonth"/>': [moment().startOf('month'), moment()],
		        '<fmt:message key="common.range.lastMonth"/>': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		    }
		});
		
		$('input.dateRangePicker').on('apply.daterangepicker', function(ev, picker) {
			
			if (typeof getDateRangeResult == 'function') {
				getDateRangeResult();
			} else {
				getPageList(1);
			}
		      
		});

	} // ./ daterangepicker
}
</script>
</c:if>

<c:if test="${useFilestyle}">
<script type="text/javascript">
function addFilestylePlugin(){
	if (typeof $.fn.filestyle != 'undefined') {
		$(":file").filestyle({
				buttonText : ' <fmt:message key="btn.chooseFile"/>', 
				placeholder : '<fmt:message key="common.desc.nofile"/>',
				iconName : 'fa fa-folder-open'
			});
	} // ./ filestyle
}
</script>
</c:if>
</body>
</html>
