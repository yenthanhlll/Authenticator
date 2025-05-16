<%@ include file="/WEB-INF/views/common/def.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags --%>
<c:set var="context_path" value="${requestScope['javax.servlet.forward.context_path']}"/>
<!-- Favicon  -->
<link rel="icon" type="image/x-ico" href="${context_path}/static/resources/images/favicon/motp-favicon.ico?" />
<link rel="shortcut icon" type="image/x-icon" href="${context_path}/static/resources/images/favicon/motp-favicon.ico?" />
<title><fmt:message key="common.title" /></title>

<!-- /////////////// base styles //////////////// -->
<link href="${context_path}/static/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${context_path}/static/resources/plugins/fontawesome/css/font-awesome.min.css" rel="stylesheet">
<!-- /////////////// Theme styles //////////////// -->
<link href="${context_path}/static/resources/plugins/adminLTE/css/AdminLTE.min.css" rel="stylesheet">
<link href="${context_path}/static/resources/css/custom.css" rel="stylesheet">
<link href="${context_path}/static/resources/plugins/adminLTE/css/skins/skin-blue.min.css" rel="stylesheet">

<!-- /////////////// Custom styles for this template //////////////// -->
<link href="${context_path}/static/resources/css/page.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script type="text/javascript">location.href="/unsupported";</script>
<![endif]-->

</head>
<!--[if gte IE 9]><!-->
<body class="hold-transition login-page">
	<div class="login-box">
  <div class="login-logo">
    <a href="#"><fmt:message key="header.logo" /></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
 
    <p class="login-box-msg"><fmt:message key="login.desc"/></p>
    <form id="loginForm" action="login" method="POST">
      <div class="form-group has-feedback" id="ctx-username">
        <input type="text" class="form-control" placeholder="<fmt:message key="login.username"/>" id="username" name="username" tabindex="1" <c:if test="${not empty username}">value="${username }"</c:if>/>
        <span class="fa fa-user form-control-feedback"></span>
        <span class="help-block"></span>
      </div>
      
      <div class="form-group has-feedback" id="ctx-password">
        <input type="password" class="form-control" autocomplete="off" placeholder="<fmt:message key="login.password"/>" id="password" name="password" tabindex="2"/>
        <span class="fa fa-unlock-alt form-control-feedback"></span>
      	<span class="help-block"></span>
      </div>
      
      <div class="form-group<c:if test="${empty error}"> hidden</c:if><c:if test="${not empty error}"> has-error</c:if>" id="ctx-message">
      	<label class="control-label"><i class="fa fa-times fa-lg"></i> <fmt:message key="${error}"/></label>
      </div>

      <div class="row">
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block btn-flat" tabindex="3"><fmt:message key="btn.login"/></button>
        </div>
        <!-- /.col -->
      </div>
    </form>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- /////////////////// base scripts ///////////////// -->
<script src="${context_path}/static/resources/plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${context_path}/static/resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

<script type="text/javascript">

$(document).ready(function(){
	
	$('#loginForm').submit(function(){
		var username = $('#username').val();
		
		if (validateParam('username') && validateParam('password')) {
			$('.has-error').removeClass('has-error').find('.help-block').text('');
			$('#ctx-message').removeClass('hidden').addClass('has-success').find('.control-label').html('<i class="fa fa-circle-o-notch fa-spin fa-lg fa-fw"></i> <fmt:message key="login.progressing"/>');
			return true;
		}
		
		return false;
	});
	
	$("#username").focus();
});

function validateParam(id){

	var username = $('#'+id).val();
	if (!username) {
		var $ctx = $('#ctx-' + id);
		$ctx.addClass('has-error');
		$ctx.find('.help-block').text('<fmt:message key="validate.required"/>');
		$('#'+id).focus();
		return false;
	}

	return true;
}

</script>
</body>
<!--<![endif]-->
</html>