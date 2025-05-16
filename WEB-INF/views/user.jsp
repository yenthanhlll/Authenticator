<%-- 
	#
	#	Users page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" viewUriPrefix="/web/manager/user/rest" hasDetailsBox="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
//cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};
	if (cellNum == 1) {
		attr['class'] = 'text-muted text-left';
	} else {
		attr['class'] = 'text-center';
	}

	return attr;
};
//Tabel contents generations
MMTHUtils.view.pagination.cellContentFuncs = new Array();

// col0 : idx
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowNum + 1;
});

// col1 : username
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.username;
});

//col3 : productType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.productType == 'BIOTP') {
		return 'Software OTP';
	}
	else if (rowData.productType == 'MATRIX') {
		return 'Matrix';
	}
	else if (rowData.productType == 'HWOTP') {
		return 'Hardware OTP';
	}
	else {
		return '-';
	}
});

</script><c:if test="${WEB_API_POLICY != 'GLOBAL_WIBEE'}"><script type="text/javascript">
//col2 : status
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	var html;
	if (rowData.status == '${NOT_AVAILABLE}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.statusDesc+'"><i class="fa fa-user text-gray"></i></span>';
	} else if (rowData.status == '${AVAILABLE}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.statusDesc+'"><i class="fa fa-user text-light-blue"></i></span>';
	} else if (rowData.status == '${LOST_STOLEN}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.statusDesc+'"><i class="fa fa-user-md text-green"></i></span>';
	} else if (rowData.status == '${SUSPEND}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.statusDesc+'"><i class="fa fa-user text-yellow"></i></span>';
	} else {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.statusDesc+'"><i class="fa fa-user-times text-red"></i></span>';
	}

	return html;
});
</script></c:if><c:if test="${WEB_API_POLICY == 'GLOBAL_WIBEE'}"><c:if test="${AUTH_METHOD_BIOTP_ENABLED}"><script type="text/javascript">
// col3 : biotpstatus
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	var html;
	if (rowData.biotpStatus == '${NOT_AVAILABLE}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.biotpStatusDesc+'"><i class="fa fa-user text-gray"></i></span>';
	} else if (rowData.biotpStatus == '${AVAILABLE}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.biotpStatusDesc+'"><i class="fa fa-user text-light-blue"></i></span>';
	} else if (rowData.biotpStatus == '${LOST_STOLEN}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.biotpStatusDesc+'"><i class="fa fa-user-md text-green"></i></span>';
	} else if (rowData.biotpStatus == '${SUSPEND}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.biotpStatusDesc+'"><i class="fa fa-user text-yellow"></i></span>';
	} else {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.biotpStatusDesc+'"><i class="fa fa-user-times text-red"></i></span>';
	}

	return html;
});
</script></c:if><c:if test="${AUTH_METHOD_FIDO_ENABLED}"><script type="text/javascript">
// col4 : fidostatus
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	var html;
	if (rowData.fidoStatus == '${NOT_AVAILABLE}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.fidoStatusDesc+'"><i class="fa fa-user text-gray"></i></span>';
	} else if (rowData.fidoStatus == '${AVAILABLE}') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.fidoStatusDesc+'"><i class="fa fa-user text-light-blue"></i></span>';
	} else {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.fidoStatusDesc+'"><i class="fa fa-user-times text-red"></i></span>';
	}

	return html;
});
</script></c:if></c:if><script type="text/javascript">

//Hwtoken type
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.tokenType == '04')
	{
		return '<span class="label label-danger">Card</span>';
	} else if (rowData.tokenType == '05'){
		return '<span class="label label-primary">Token</span>';
	}
	else {
		return '-';
	}
});

// multiLoginYN
// MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
// 	return rowData.multiLoginYN;
// });
// col5 : tsReg
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsReg;
});

// col6 : tsUpdated
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsUpdated;
});


//col7 : details
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return '<button type="button" class="btn btn-sm btn-primary mmth-btn btn-detailsBox" data-id="'+rowData.id+'"><fmt:message key="btn.details"/></button>';
});

var logCellFn = new Array();
logCellFn.push(function(rowData, cellNum, rowNum){
	return rowData.opTypeDesc;
});
logCellFn.push(function(rowData, cellNum, rowNum){
	return '<code>' + rowData.actionType + '</code>';
});
logCellFn.push(function(rowData, cellNum, rowNum){
	return rowData.tsReg;
});

function init() {
	if ($('.content').has('#mmth-userStatusList').length) {
		$('#mmth-userStatusList > li:FIRST-CHILD').addClass('active');	
	} 
}

function setTableContentDataImp(pageContents){	
}
function initRestPageImp(data){
}

function setTableContentOTPInfo(otpType)
{
	var isSuccess = false;
	var otpArray = new Array();
	otpArray = otpType.hwotpLogs;
	for(var i in otpArray)
	{
		if(otpArray[i].opType == "OTP_AUTH")
		{
			$('#details-tsBiotpAuthLatest').text(otpArray[i].tsReg);
			isSuccess = true;
			break;
		}
	}	

	// If find to otp latest auth logs
	if(isSuccess == true)
	{
		if (otpType.hwtokens == null) {
			$('#details-tsBiotpAuthLatest').text('<fmt:message key="user.noAuth"/>');
			$('.details_authFailCnt').hide();
		}else {
			$('#details-authFailCnt').text(otpType.hwtokens.unlockCnt);
			$('.details_authFailCnt').show();
		}		
	}
	else
	{	
		$('#details-tsBiotpAuthLatest').text('<fmt:message key="user.noAuth"/>');
		$('.details_authFailCnt').hide();	
	}	
}

function setTableContentMatrixInfo(data)
{
	var isSuccess = false;
	for(var i in data.matrixLogs)
	{
		if( data.matrixLogs[i].opType == "AUTH")
		{
			$('#details-tsBiotpAuthLatest').text( data.matrixLogs[i].tsReg);
			isSuccess = true;
			break;
		}
	}
	
	$('.mmth-summary-title.details_authFailCnt').css('display', 'none');
	
	if (isSuccess == false)
	{
		$('#details-tsBiotpAuthLatest').text('<fmt:message key="user.noAuth"/>');
	}
}

function hideDetailLists()
{
	$('#div_deviceList').hide();
	$('#div_biotpLogList').hide();
	$('#div_matrixLogList').hide();
	$('#div_hwotpLogList').hide();
	$('#div_fidoLogList').hide();
}

function fillDetailCallback(data){
	$('.mmth-externalApiItems').hide();
	hideDetailLists();
	
	var details = data.details;
	$('#details-username').text(details.username);
	
	var lastestTimeTxt = $('.mmth-summary-title.details_tsBiotpAuthLatest').text();
	lastestTimeTxt = lastestTimeTxt.replace('<fmt:message key="AuthMethodTypes.MATRIX"/>', '<fmt:message key="AuthMethodTypes.OTP"/>');
	$('.details_tsBiotpAuthLatest').text(lastestTimeTxt);
	
	// Modify hide with productType by sgjeong - 20190701
	if(details.productType == 'HWOTP'){
		$('#mmth-detailsBox').boxWidget('expand');
		$('#div_hwotpLogList').show();
		$('.datails_status').text('<fmt:message key="user.otpStatus"/>');		
	} else if(details.productType == 'BIOTP')		{
		$('#mmth-detailsBox').boxWidget('expand');		
		$('#div_deviceList').show();
		$('#div_biotpLogList').show();
		$('.datails_status').text('<fmt:message key="user.otpStatus"/>');		
	}else if(details.productType == 'MATRIX')		{
		$('#mmth-detailsBox').boxWidget('expand');			
		$('#div_matrixLogList').show();
		lastestTimeTxt = lastestTimeTxt.replace('<fmt:message key="AuthMethodTypes.OTP"/>', '<fmt:message key="AuthMethodTypes.MATRIX"/>');
		$('.details_tsBiotpAuthLatest').text(lastestTimeTxt);
		$('.datails_status').text('<fmt:message key="user.matrixStatus"/>');		
	}else {
		$('#mmth-detailsBox').boxWidget('remove');
		
		BootstrapDialog.alert({
		    type: BootstrapDialog.TYPE_WARNING,
		    message: '<fmt:message key="common.desc.invalidMethodEnabled"/>',
		    callback : function(){
			    	window.location.reload();
			    }
		});	
		
		return;
	}	
	
	// details-biotpStatus -> details-otpStatus
	if (document.getElementById('details-otpStatus') && details.biotpStatus) {
		$('#details-otpStatus').text(details.biotpStatusDesc);	
	}
	if (document.getElementById('details-otpStatus') && details.status) {
		$('#details-otpStatus').text(details.statusDesc);	
	}	
	if (document.getElementById('details-fidoStatus') && details.fidoStatus) {
		$('#details-fidoStatus').text(details.fidoStatusDesc);	
	}
	
	if (document.getElementById('details-tokenId')) {
		if (details.biotpStatus && details.biotpStatus == 'AVAILABLE' && details.tokenId) {
			$('#details-tokenId').text(details.tokenId).addClass('text-primary');
			$('.details_tokenId').show();
		}		
		
		else {
			$('.details_tokenId').hide();
		}
	}
	
	if (document.getElementById('details-tsBiotpAuthLatest')) {
		$('.details_authFailCnt').show();
		
		if (details.biotpAuthManager && details.biotpAuthManager.tsLastAuth) {
			$('#details-tsBiotpAuthLatest').text(details.biotpAuthManager.tsLastAuth);
			
			if (details.biotpAuthManager.authFailCnt >= '${maxAuthFailCnt}') {
				$('#details-authFailCnt').text(details.biotpAuthManager.authFailCnt).addClass('text-red');	
			}else {
				$('#details-authFailCnt').text(details.biotpAuthManager.authFailCnt).removeClass('text-red');
			}			
		} 
		else if(data.hwotpLogs.length > 0)
		{
			setTableContentOTPInfo(data);			
		}	
		else if(data.matrixLogs.length > 0)
		{
			setTableContentMatrixInfo(data);		
		}		
		else {
			$('#details-tsBiotpAuthLatest').text('<fmt:message key="user.noAuth"/>');
		}
	}	
	
	
	if (document.getElementById('details-tsFidoAuthLatest')) {
		if (details.fidoAuthManager && details.fidoAuthManager.tsLastAuth) {
			$('#details-tsFidoAuthLatest').text(details.fidoAuthManager.tsLastAuth);	
		} else {
			$('#details-tsFidoAuthLatest').text('<fmt:message key="user.noAuth"/>');
		}		
	}
	

	if (details.annotation) {

		if (details.annotation.displayName != null && details.annotation.displayName.trim().length > 1) {
			$('#details_annotation_displayName').text(details.annotation.displayName);
			$('.details_annotation_displayName').show();
		}
		
		if (details.annotation.extUnique != null && details.annotation.extUnique.trim().length > 1) {
			$('#details_annotation_extUnique').text(details.annotation.extUnique);
			$('.details_annotation_extUnique').show();
		}
		
		if (details.annotation.customerCode != null && details.annotation.customerCode.trim().length > 1) {
			$('#details_annotation_customerCode').text(details.annotation.customerCode);
			$('.details_annotation_customerCode').show();
		}
		
		if (details.annotation.countryCodeDesc != null && details.annotation.countryCodeDesc.trim().length > 1) {
			$('#details_annotation_countryCode').text(details.annotation.countryCodeDesc);
			$('.details_annotation_countryCode').show();
		}
	} 
	
	if (!data.devices || data.devices.length < 1) {
		$('#msg-noDevice').show();
		$('#deviceList').hide();

	} else {
		$('#msg-noDevice').hide();
		$('#deviceList').show();
		var tableHandler = table('deviceContents').removeAllRows();
		tableHandler.addRows(data.devices, 
				[function(rowData, cellNum, rowNum){
					return rowData.model;
				},
				function(rowData, cellNum, rowNum){
					return createDeviceTypeHtml(rowData.deviceType, rowData.osTypeDesc);
				},
				function(rowData, cellNum, rowNum){
					return createDeviceStatusHtml(rowData.biotpStatus, rowData.biotpStatusDesc);
				},
				function(rowData, cellNum, rowNum){
					return rowData.tsReg;
				},
				function(rowData, cellNum, rowNum){
					return rowData.tsUpdated;
				}], null, {'class' : 'text-center'});
	}
	
	// Set data for log
	
	// 1. biotp logs
	if (!data.biotpLogs || data.biotpLogs.length < 1) {
		$('#msg-noBiotpLogs').show();
		$('#biotpLogList').hide();
	} else {
		var tableHandler = table('biotpLogContents').removeAllRows();
		tableHandler.addRows(data.biotpLogs, logCellFn, null, {'class' : 'text-center'})
		$('#msg-noBiotpLogs').hide();
		$('#biotpLogList').show();
	}
	
	// 2. fido logs
	if (!data.fidoLogs || data.fidoLogs.length < 1) {
		$('#msg-noFidoLogs').show();
		$('#fidoLogList').hide();
	} else {
		var tableHandler = table('fidoLogContents').removeAllRows();
		tableHandler.addRows(data.fidoLogs, logCellFn, null, {'class' : 'text-center'})
		$('#msg-noFidoLogs').hide();
		$('#fidoLogList').show();
	}
	
	// 3. hwotp logs
	if (!data.hwotpLogs || data.hwotpLogs.length < 1) {
		$('#msg-nohwotpLogs').show();
		$('#hwotpLogList').hide();
	} else {
		var tableHandler = table('hwotpLogContents').removeAllRows();
		tableHandler.addRows(data.hwotpLogs, logCellFn, null, {'class' : 'text-center'})
		$('#msg-nohwotpLogs').hide();
		$('#hwotpLogList').show();
	}
	
	// 4. matrix logs
	if (!data.matrixLogs || data.matrixLogs.length < 1) {
		$('#matrixLogList').hide();
		$('#msg-nomatrixLogs').show();
	} else {
		var tableHandler = table('matrixLogContents').removeAllRows();
		tableHandler.addRows(data.matrixLogs, logCellFn, null, {'class' : 'text-center'})
		$('#matrixLogList').show();
		$('#msg-nomatrixLogs').hide();
	}
		
	$('#mmth-detailsBox').boxWidget('expand');
}

function createDeviceStatusHtml(status, desc){
	if (status == '${NOT_AVAILABLE}') {
		return '<span class="label label-default">' +desc+ '</span>';
	} else if (status == '${AVAILABLE}') {
		return '<span class="label label-primary">' +desc+ '</span>';
	} else if (status == '${LOST_STOLEN}') {
		return '<span class="label label-warning">' +desc+ '</span>';
	} else {
		return '<span class="label label-danger">' +desc+ '</span>';
	}
}
function createDeviceTypeHtml(deviceType, osTypeDesc){
	if (deviceType == 'ANDROID_DEVICE' || deviceType == 'ANDROID_WIFI') {
		return '<span class="lead text-green" data-toggle="tooltip" title="' + osTypeDesc + '"><i class="fa fa-android"></i></span>';
	} else if (deviceType == 'IOS_UUID') {
		return '<span class="lead text-black" data-toggle="tooltip" title="' + osTypeDesc + '"><i class="fa fa-apple"></i></span>';
	} else {
		return '<span class="lead text-yellow" data-toggle="tooltip" title="' + osTypeDesc + '"><i class="fa fa-mobile"></i></span>';
	}
}
</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1><fmt:message key="user"/> <small><fmt:message key="user.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.biotp"/></li>
			<li class="active"><i class="fa fa-users"></i> <fmt:message key="nav.user" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="row">
		
		<c:if test="${WEB_API_POLICY != 'GLOBAL_WIBEE' }">
        <div class="col-md-3">

			<!-- 사용자 상태 -->
        	<div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title"><fmt:message key="user.status"/></h3>

            </div>
            <div class="box-body no-padding">
            	<tag:listFilter key="status" id="mmth-userStatusList" clazzNames="mmth-nav-light" useAll="true" allIconClazz="fa-users text-black" useStats="true">
            		<tag:filterItem value="NOT_AVAILABLE" msgKey="UserStatus.NOT_AVAILABLE" iconClass="fa fa-user text-gray"><span class="statsItem label bg-gray-light text-black pull-right" data-key="notAvailable">0</span></tag:filterItem>
            		<tag:filterItem value="AVAILABLE" msgKey="UserStatus.AVAILABLE" iconClass="fa fa-user text-light-blue"><span class="statsItem label bg-light-blue pull-right" data-key="available">0</span></tag:filterItem>
            		<tag:filterItem value="LOST_STOLEN" msgKey="UserStatus.LOST_STOLEN" iconClass="fa fa-user-md text-green"><span class="statsItem label bg-green pull-right" data-key="lostStolen">0</span></tag:filterItem><c:if test="${WEB_API_POLICY != 'KIWOOM'}">
            		<!--<tag:filterItem value="SUSPEND" msgKey="UserStatus.SUSPEND" iconClass="fa fa-user text-yellow"><span class="statsItem label bg-orange pull-right" data-key="suspend">0</span></tag:filterItem>-->
            		<tag:filterItem value="DISCARD" msgKey="UserStatus.DISCARD" iconClass="fa fa-user-times text-red"><span class="statsItem label bg-red pull-right" data-key="discard">0</span></tag:filterItem></c:if>
            	</tag:listFilter>
            </div>
            <!-- /.box-body -->
          </div>


        </div>
        <!-- /.col -->
        </c:if>
        
        <c:set var="col_size" value="${WEB_API_POLICY eq 'GLOBAL_WIBEE' ? 'col-md-12' : 'col-md-9'}"></c:set>
        
		<div class="${col_size }">
		
			<div class="row">
				<div class="col-md-12">
					<div class="box mmth-box">
			           	<div class="box-header with-border">
			              	<h3 class="box-title"><fmt:message key="user.list"/></h3>
							<table class="table mmth-pageSearch-field">
								<colgroup><col width="60%"><col width="40%"></colgroup>
								<thead>
									<tr>
										<th></th>
										<th><tag:singleSearchFilter fieldKey="username" msgKey="common.username" id="searchField" /></th>
									</tr>
								</thead>
							</table>
			              <!-- /.box-tools -->
			            </div>
			            <!-- /.box-header -->
			            <tag:pagination noListMsgKey="user.noList" cols="7">
							<th><fmt:message key="common.idxLabel" /></th>
							<th><fmt:message key="common.username" /></th>		
							<th><fmt:message key="user.productTypeCode" /></th>
							<c:if test="${WEB_API_POLICY != 'GLOBAL_WIBEE' }"><th><fmt:message key="user.status" /></th></c:if>
							<c:if test="${WEB_API_POLICY == 'GLOBAL_WIBEE' }">
							<c:if test="${AUTH_METHOD_BIOTP_ENABLED}"><th><fmt:message key="user.biotpStatus"/></th></c:if>
							<c:if test="${AUTH_METHOD_FIDO_ENABLED}"><th><fmt:message key="user.fidoStatus"/></th></c:if></c:if>
							<th><fmt:message key="hwtoken.type" /></th>
<%-- 							<c:if test="${WEB_API_POLICY == 'MIRAE_ASSET_VT' }"><th><fmt:message key="user.multiLoginYN" /></th></c:if>							 --%>
							<th><fmt:message key="common.regDateTime" /></th>
							<th><fmt:message key="common.updateDateTime" /></th>
							<th> </th>
						</tag:pagination>
			
			        </div> 
        			 <!-- /. box -->
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-md-12">
					<tag:detailsBox titleKeyDetails="user.details" excludeFooter="true">
						<div class="container">
						<div class="row">
						
							<div class="col-md-4">

								<div class="mmth-summary">
									<div class="mmth-summary-icon"><i class="fa fa-vcard-o"></i></div>
									<div class="mmth-summary-content">
										<div class="mmth-summary-title"><fmt:message key="common.username" /></div>
										<div class="mmth-summary-value" id="details-username"></div>
										<div class="mmth-summary-title mmth-externalApiItems details_annotation_displayName"><fmt:message key="user.displayName" /></div>
										<div class="mmth-summary-value mmth-externalApiItems details_annotation_displayName" id="details_annotation_displayName"></div>
										<div class="mmth-summary-title mmth-externalApiItems details_annotation_extUnique"><fmt:message key="user.extUserName" /></div>
										<div class="mmth-summary-value mmth-externalApiItems details_annotation_extUnique" id="details_annotation_extUnique"></div>
									</div>
								</div>
								<hr class="summary-delimiter"/>	
								<div class="mmth-summary mmth-externalApiItems details_annotation_customerCode">
									<div class="mmth-summary-icon"><i class="fa fa-building-o"></i></div>
									<div class="mmth-summary-content">
										<div class="mmth-summary-title"><fmt:message key="user.customerCode" /></div>
										<div class="mmth-summary-value" id="details_annotation_customerCode"></div>
									</div>
								</div>
								<hr class="summary-delimiter mmth-externalApiItems details_annotation_customerCode"/>
								
								<div class="mmth-summary mmth-externalApiItems details_annotation_countryCode">
									<div class="mmth-summary-icon"><i class="fa fa-globe"></i></div>
									<div class="mmth-summary-content">
										<div class="mmth-summary-title"><fmt:message key="user.countryCode" /></div>
										<div class="mmth-summary-value" id="details_annotation_countryCode"></div>
									</div>
								</div>
								<hr class="summary-delimiter mmth-externalApiItems details_annotation_countryCode"/>
								
								<div class="mmth-summary">
									<div class="mmth-summary-icon"><i class="fa fa-clock-o"></i></div>
									<div class="mmth-summary-content">
										<div class="mmth-summary-title details_tsBiotpAuthLatest"><fmt:message key="user.latestAuthDateTime"><fmt:param><fmt:message key="AuthMethodTypes.OTP"/></fmt:param></fmt:message></div>
										<div class="mmth-summary-value details_tsBiotpAuthLatest" id="details-tsBiotpAuthLatest"></div>
										<div class="mmth-summary-title details_authFailCnt"><fmt:message key="user.AuthFailedCnt"><fmt:param><fmt:message key="AuthMethodTypes.OTP"/></fmt:param></fmt:message></div>
										<div class="mmth-summary-value details_authFailCnt" id="details-authFailCnt"></div>
									</div>
								</div>
								
								<hr class="summary-delimiter"/>	
								<div class="mmth-summary">
									<div class="mmth-summary-icon"><i class="fa fa-registered"></i></div>
									<div class="mmth-summary-content">
										<div class="mmth-summary-title datails_status"><fmt:message key="user.otpStatus"/></div>
										<div class="mmth-summary-value" id="details-otpStatus"></div>
										<div class="mmth-summary-title details_tokenId"><fmt:message key="common.tokenId"/></div>
										<div class="mmth-summary-value details_tokenId" id="details-tokenId"></div>
									</div>
								</div>
																

							</div><!-- ./col-md-4 -->
							
							<div class="col-md-8">
								
								<div class="row" id="div_deviceList">
									<div class="mmth-datalist col-md-12" >
									<h3><fmt:message key="user.deviceList"/></h3>
									<table class="table table-condensed" id="deviceList"> 
										<thead>
											<tr> 
												<th><fmt:message key="user.device.model"/></th> 
												<th><fmt:message key="user.deviceType"/></th> 
												<th><fmt:message key="user.biotpStatus"/></th>
												<th><fmt:message key="common.regDateTime"/></th>
												<th><fmt:message key="common.updateDateTime"/></th>
											</tr> 
										</thead> 
										<tbody id="deviceContents"></tbody> 
									</table>
									<div class="mmth-alert mmth-alert-warning" role="alert" id="msg-noDevice"><i class="fa fa-exclamation-triangle"></i> <fmt:message key="user.noDevice"/></div>
									</div>
									
								</div>
								<c:if test="${AUTH_METHOD_BIOTP_ENABLED }">
								
								<div class="row" id="div_biotpLogList">
								<hr id="detail_hrItem_1" />
									<div class="mmth-datalist col-md-12">
									<h3><fmt:message key="user.latestUserLog"/></h3>
									<table class="table table-condensed" id="biotpLogList"> 
										<thead>
											<tr>
												<th><fmt:message key="user.log.opType"/></th> 
												<th><fmt:message key="user.log.actionType"/></th>
												<th><fmt:message key="common.regDateTime"/></th> 
											</tr> 
										</thead> 
										<tbody id="biotpLogContents"></tbody> 
									</table>
									<div class="mmth-alert mmth-alert-warning" role="alert" id="msg-noBiotpLogs"><i class="fa fa-exclamation-triangle"></i> <fmt:message key="user.noAuth"/></div>
									</div>
									
								</div>
								</c:if><c:if test="${AUTH_METHOD_FIDO_ENABLED }">
							
								<div class="row" id="div_fidoLogList">
									<hr id="detail_hrItem_2"/>
									<div class="mmth-datalist col-md-12">
									<h3 id="detail_h3_fidoLogList"><fmt:message key="user.latestUserLog"/></h3>
									<table class="table table-condensed" id="fidoLogList"> 
										<thead>
											<tr>	
												<th><fmt:message key="user.log.opType"/></th> 
												<th><fmt:message key="user.log.actionType"/></th>
												<th><fmt:message key="common.regDateTime"/></th> 
											</tr>  
										</thead> 
										<tbody id="fidoLogContents"></tbody>
									</table>	
									<div class="mmth-alert mmth-alert-warning" role="alert" id="msg-noFidoLogs"><i class="fa fa-exclamation-triangle"></i> <fmt:message key="user.noAuth"/></div>
									</div>
								</div>
								</c:if>
								
								<!-- H/W OTP detail -->
								<div class="row" id="div_hwotpLogList">
									<div class="mmth-datalist col-md-12">
									<h3 id="detail_h3_hwotpLogList"><fmt:message key="user.latestUserLog"/></h3>
									<table class="table table-condensed" id="hwotpLogList"> 
										<thead>
											<tr>	
												<th><fmt:message key="user.log.opType"/></th> 
												<th><fmt:message key="user.log.actionType"/></th>
												<th><fmt:message key="common.regDateTime"/></th> 
											</tr>  
										</thead> 
										<tbody id="hwotpLogContents"></tbody>
									</table>	
									<div class="mmth-alert mmth-alert-warning" role="alert" id="msg-nohwotpLogs"><i class="fa fa-exclamation-triangle"></i> <fmt:message key="user.noAuth"/></div>
									</div>
								</div>
								<!-- H/W OTP END -->
								
								<!-- MATRIX detail -->
								<div class="row" id="div_matrixLogList">
									<div class="mmth-datalist col-md-12">
									<h3 id="detail_h3_matrixLogList"><fmt:message key="user.latestUserLog"/></h3>
									<table class="table table-condensed" id="matrixLogList"> 
										<thead>
											<tr>	
												<th><fmt:message key="user.log.opType"/></th> 
												<th><fmt:message key="user.log.actionType"/></th>
												<th><fmt:message key="common.regDateTime"/></th> 
											</tr>  
										</thead> 
										<tbody id="matrixLogContents"></tbody>
									</table>	
									<div class="mmth-alert mmth-alert-warning" role="alert" id="msg-nomatrixLogs"><i class="fa fa-exclamation-triangle"></i> <fmt:message key="user.noAuth"/></div>
									</div>
								</div>
								<!-- MATRIX END -->			
													
							</div><!-- ./col-md-8 -->
						</div><!--  ./row -->
						</div> <!--  ./ container -->
					</tag:detailsBox>
				</div>
			</div>

        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
		
	</section>
	<!-- /.content -->

</jsp:body>
</tag:page>