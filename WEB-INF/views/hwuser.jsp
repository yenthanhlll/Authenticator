<%-- 
	#
	#	Users page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" viewUriPrefix="/web/manager/hwuser/rest" hasDetailsBox="true">
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
</script><c:if test="${WEB_API_POLICY != 'GLOBAL_WIBEE'}"><script type="text/javascript">
//col2 : status
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	var html;
	if (rowData.status == 'OCCUPIED') {
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.statusDesc+'"><i class="fa fa-user text-light-blue"></i></span>';
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
		html = '<span class="lead" data-toggle="tooltip" title="'+rowData.biotpStatusDesc+'"><i class="fa fa-user-md text-orange"></i></span>';
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
// col5 : tsOccupied
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsOccupied;
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

function fillDetailCallback(data){
	$('.mmth-externalApiItems').hide();
	
	var details = data.details;
	
	$('#details-username').text(details.username);
	
	if (document.getElementById('details-biotpStatus') && details.biotpStatus) {
		$('#details-biotpStatus').text(details.biotpStatusDesc);	
	}
	
	
	if (document.getElementById('details-fidoStatus') && details.fidoStatus) {
		$('#details-fidoStatus').text(details.fidoStatusDesc);	
	}
	
	if (document.getElementById('details-tokenId')) {
		if (details.biotpStatus && details.biotpStatus == 'AVAILABLE' && details.tokenId) {
			$('#details-tokenId').text(details.tokenId).addClass('text-primary');
			$('.details_tokenId').show();
		} else {
			$('.details_tokenId').hide();
		}
	}
	
	if (document.getElementById('details-tsBiotpAuthLatest')) {
		if (details.biotpAuthManager && details.biotpAuthManager.tsLastAuth) {
			$('#details-tsBiotpAuthLatest').text(details.biotpAuthManager.tsLastAuth);
			
			if (details.biotpAuthManager.authFailCnt >= '${maxAuthFailCnt}') {
				$('#details-authFailCnt').text(details.biotpAuthManager.authFailCnt).addClass('text-red');	
			} else {
				$('#details-authFailCnt').text(details.biotpAuthManager.authFailCnt).removeClass('text-red');
			}
			
			$('.details_authFailCnt').show();
		} else {
			$('#details-tsBiotpAuthLatest').text('<fmt:message key="user.noAuth"/>');
			$('.details_authFailCnt').hide();
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
	
	if (!data.biotpLogs || data.biotpLogs.length < 1) {
		$('#msg-noBiotpLogs').show();
		$('#biotpLogList').hide();
	} else {
		var tableHandler = table('biotpLogContents').removeAllRows();
		tableHandler.addRows(data.biotpLogs, logCellFn, null, {'class' : 'text-center'})
		$('#msg-noBiotpLogs').hide();
		$('#biotpLogList').show();
	}
	
	if (!data.fidoLogs || data.fidoLogs.length < 1) {
		$('#msg-noFidoLogs').show();
		$('#fidoLogList').hide();
	} else {
		var tableHandler = table('fidoLogContents').removeAllRows();
		tableHandler.addRows(data.fidoLogs, logCellFn, null, {'class' : 'text-center'})
		$('#msg-noFidoLogs').hide();
		$('#fidoLogList').show();
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
			<li class="active"><fmt:message key="nav.title.hwotp"/></li>
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
            		<tag:filterItem value="AVAILABLE" msgKey="UserStatus.AVAILABLE" iconClass="fa fa-user text-light-blue"><span class="statsItem label bg-light-blue pull-right" data-key="available">0</span></tag:filterItem> <c:if test="${WEB_API_POLICY == 'GLOBAL_WIBEE'}">
            		<tag:filterItem value="LOST_STOLEN" msgKey="UserStatus.LOST_STOLEN" iconClass="fa fa-user-md text-orange"><span class="statsItem label bg-orange pull-right" data-key="lostStolen">0</span></tag:filterItem></c:if><c:if test="${WEB_API_POLICY != 'KIWOOM'}">
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
							<c:if test="${WEB_API_POLICY != 'GLOBAL_WIBEE' }"><th><fmt:message key="user.status" /></th></c:if>
							<c:if test="${WEB_API_POLICY == 'GLOBAL_WIBEE' }">
							<c:if test="${AUTH_METHOD_BIOTP_ENABLED}"><th><fmt:message key="user.biotpStatus"/></th></c:if>
							<c:if test="${AUTH_METHOD_FIDO_ENABLED}"><th><fmt:message key="user.fidoStatus"/></th></c:if></c:if>
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
										<c:if test="${AUTH_METHOD_BIOTP_ENABLED }">
										<div class="mmth-summary-title details_tsBiotpAuthLatest"><fmt:message key="user.latestAuthDateTime"><fmt:param><fmt:message key="AuthMethodTypes.BIOTP"/></fmt:param></fmt:message></div>
										<div class="mmth-summary-value details_tsBiotpAuthLatest" id="details-tsBiotpAuthLatest"></div>
										<div class="mmth-summary-title details_authFailCnt"><fmt:message key="user.AuthFailedCnt"><fmt:param><fmt:message key="AuthMethodTypes.BIOTP"/></fmt:param></fmt:message></div>
										<div class="mmth-summary-value details_authFailCnt" id="details-authFailCnt"></div>
										</c:if>
										<c:if test="${AUTH_METHOD_FIDO_ENABLED }">
										<div class="mmth-summary-title details_tsFidoAuthLatest"><fmt:message key="user.latestAuthDateTime"><fmt:param><fmt:message key="AuthMethodTypes.FIDO"/></fmt:param></fmt:message></div>
										<div class="mmth-summary-value details_tsFidoAuthLatest" id="details-tsFidoAuthLatest"></div>
										</c:if>
									</div>
								</div>
								
								
								<hr class="summary-delimiter"/>	
								<div class="mmth-summary">
									<div class="mmth-summary-icon"><i class="fa fa-registered"></i></div>
									<div class="mmth-summary-content">
										<c:if test="${AUTH_METHOD_BIOTP_ENABLED }">
										<div class="mmth-summary-title"><fmt:message key="user.biotpStatus"/></div>
										<div class="mmth-summary-value" id="details-biotpStatus"></div>
										<div class="mmth-summary-title details_tokenId"><fmt:message key="common.tokenId"/></div>
										<div class="mmth-summary-value details_tokenId" id="details-tokenId"></div>
										</c:if>
										<c:if test="${AUTH_METHOD_FIDO_ENABLED }">
										<div class="mmth-summary-title"><fmt:message key="user.fidoStatus"/></div>
										<div class="mmth-summary-value" id="details-fidoStatus"></div>
										</c:if>
									</div>
								</div>
																

							</div><!-- ./col-md-4 -->
							
							<div class="col-md-8">
								
								<div class="row">
									<div class="mmth-datalist col-md-12">
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
								<hr/>
								<div class="row">
									<div class="mmth-datalist col-md-12">
									<h3><fmt:message key="user.biotpLog"/></h3>
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
								<hr/>
								<div class="row">
									<div class="mmth-datalist col-md-12">
									<h3><fmt:message key="user.fidoLog"/></h3>
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