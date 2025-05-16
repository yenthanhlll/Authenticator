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
<link href="${context_path}/static/resources/plugins/fontawesome/css/font-awesome.min.css" rel="stylesheet">
<!-- /////////////// Theme styles //////////////// -->
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="${context_path}/static/resources/plugins/bootstrap/js/html5shiv.min.js"></script>
  <script src="${context_path}/static/resources/plugins/bootstrap/js/respond.min.js"></script>
<![endif]-->
<script src="${context_path}/static/resources/plugins/jquery/jquery.min.js" ></script>
</head>
<body class="hold-transition layout-error">

	<div class="container">
		<div class="header clearfix"><h3 class="text-muted"><fmt:message key="header.logo" /></h3></div>
		
		<form role="form">
		<div class="box-body">
		  <div class="form-group">
		    <label for="userName">User ID</label>
		    <input type="text" class="form-control" id="userName" value="eunjeong01">
		  </div>
		  
		  <div class="form-group">
		    <label for="otpSn">OTP SN</label>
		    <input type="text" class="form-control" id="otpSn" value="99900003">
		  </div>
		  
		  <div class="form-group">
		    <label for="transaction">Transaction</label>
		    <select class="form-control" id="transaction"></select>
		  </div>
		  
		  <div class="form-group">
		    <label for="otp">OTP NUM</label>
		    <input type="text" class="form-control" id="otp" maxlength="6">
		  </div>
		</div>
		<!-- /.box-body -->
		
		<div class="box-footer">
		<button type="button" class="btn btn-primary" id="btnSend">Send Transaction</button>
		<button type="button" class="btn btn-danger" id="btnOTP">Verify OTP</button>
		</div>
		</form>
	</div>
	<script src="${context_path}/static/resources/js/mmthUtils.js"></script>
	<script type="text/javascript">
	var data = [
{ org : '41C0BAC7E0233132332A352A372A393023C8AB2AB5BF233530302C303030BFF823', sha : '716CE11994E18472CF164BC3EC1BC1CC8ED5D953990AE6853F71DB7BD54DDF1B', text : 'A은행#123*5*7*90#홍*동#500,000원#', yn : "Y"},
{ org : '42C0BAC7E0233233342A362A382A303123C7E32A23312C3030302C303030BFF823', sha : '968743313C12924E728642EFC855CA863D53B34DC98532C2C7E8A4B4D7897520', text : 'B은행#234*6*8*01#허*#1,000,000원#', yn : "Y"},
{ org : '43C0BAC7E0233334352A372A392A313223BDC52AC0D3B4E723322C3030302C303030BFF823', sha : 'E0AD631353AE522BF1AA103D2355E6974CDEECCD5EDDBACF441DFDD5742697A6', text : 'C은행#345*7*9*12#신*임당#2,000,000원#', yn : "Y"},
{ org : '41C1F5B1C7233132332A352A372A393023C8AB2AB5BF23332C3030302C303030BFF823', sha : 'B5641DCF95944386FE8F5E9550E4E84F803A9540A24A94DF95241363A1509422', text : 'A증권#123*5*7*90#홍*동#3,000,000원#', yn : "Y"},
{ org : '42C1F5B1C7233233342A362A382A303123C7E32A23342C3030302C303030BFF823', sha : '2F62CF9901C5579A79462717950666BF7986F2D208A0CD4674FDC138C55A8135', text : 'B증권#234*6*8*01#허*#4,000,000원#', yn : "Y"},
{ org : '23C8AB2AB5BFB4D4C0C720B0F8C0CEC0CEC1F5BCADB8A620C0E7B9DFB1DEC7D5B4CFB4D92E', sha : '8848F1D1AB6EBFC6E80C3F934E0DD34AF9E460CE32504F1392F0A198D453D634', text : '#홍*동님의 공인인증서를 재발급합니다.', yn : "N"},
{ org : '23C0CC2ABDC5B4D4C0C720B0F8C0CEC0CEC1F5BCADB8A620C0E7B9DFB1DEC7D5B4CFB4D92E', sha : '64851C19712070CCF33546CBEFAB5A4EE1CB5834401431590DD6B04E3807A395', text : '#이*신님의 공인인증서를 재발급합니다.', yn : "N"}

				];
	
	$(document).ready(function(){
		var trSelect = $('#transaction');
		
		for (var idx = 0; idx < data.length; idx++) {
			trSelect.append('<option data-sha="'+data[idx].sha+'" data-org="'+data[idx].text+'" value="'+ data[idx].sha +'" data-yn="'+data[idx].yn+'">'+data[idx].text+'</option>');
		}
		
		
		$('#btnSend').on('click', function(){
			var option = $('#transaction').find('option:selected');
			var org = option.data('org');
			var sha = option.data('sha');
			var yn = option.data('yn');
			var userName = $('#userName').val();
			var otpSn = $('#otpSn').val();
			
			var payload = '';
			payload = fillPayload(payload, userName, 32);
			payload = fillPayload(payload, otpSn, 12);
			payload = fillPayload(payload, yn, 1);
			payload = fillPayload(payload, sha, 64);
			payload = fillPayload(payload, org, org.length);
			
			$.ajax({
				  type: "POST",
				  url: '/dev/tran',
				  data: payload,
				  contentType : 'text/plain; charset=MS949',
				  success: function(resp){
					  alert(resp);
				  },
				  dataType: 'text',
				  error : function(){
					  alert('error');
				  }
				});
		});		
		
		$('#btnOTP').on('click', function(){
			var option = $('#transaction').find('option:selected');
			var sha = option.data('sha');
			var userName = $('#userName').val();
			var otpSn = $('#otpSn').val();
			var otp = $('#otp').val();
			
			var payload = '';
			payload = fillPayload(payload, userName, 32);
			payload = fillPayload(payload, otp, 6);
			payload = fillPayload(payload, otpSn, 12);
			payload = fillPayload(payload, sha, 64);
			
			$.ajax({
				  type: "POST",
				  url: '/dev/votp',
				  data: payload,
				  contentType : 'text/plain; charset=MS949',
				  success: function(resp){
					  alert(resp);
				  },
				  dataType: 'text',
				  error : function(){
					  alert('error');
				  }
				});
		});		
		
		
	});
	
	function fillPayload(payload, val, size) {

		payload += val;
		
		if (val.length < size) {
			
			var len = size - val.length;
			var pad = ' '.repeat(len);
			payload += pad;
		}
		
		return payload;
	}
	</script>
</body>
</html>