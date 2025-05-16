<%-- 
	#
	#	Audit alarms page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init"   useDateRange="true" viewUriPrefix="/web/manager/issuance/rest">
<jsp:attribute name="javascripts">
<script type="text/javascript">
//cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};
	if (cellNum == 1) {
		attr['class'] = 'text-muted text-left';
	}else if (cellNum == 8) {
			attr['class'] = 'text-muted word-break';
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

// col2 : issueType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.issueTypeDesc;
});

// col3 : authType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.authTypeDesc;
});

// col4 : aaid
//MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
//	return rowData.aaid ?rowData.aaid : '-';
	//return rowData.deviceModelName ?rowData.deviceModelName : '-';
//});
</script><%--
 --%><c:if test="${AUTH_METHOD_BIOTP_ENABLED or AUTH_METHOD_SAOTP_ENABLED }">
<script type="text/javascript">
// col5 : tokenId
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tokenId ? rowData.tokenId : '-';
});

//col6 : device model name
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.deviceModel ? rowData.deviceModel : '-';
});

//col7 : device type
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return createDeviceTypeHtml(rowData.deviceType, rowData.osTypeDesc);
});

</script><%-- 
--%></c:if>
<script type="text/javascript">

//col8 : tsReg
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsIssue;
});


function init() {
	$('#mmth-issueTypeList > li:FIRST-CHILD').addClass('active');
}

function setTableContentDataImp(pageContents){
	
}
function initRestPageImp(data){
}

function createDeviceTypeHtml(deviceType, osTypeDesc){
	if (deviceType == 'ANDROID_DEVICE' || deviceType == 'ANDROID_WIFI' || deviceType == 'ANDROID_ID') {
		return '<span class="lead text-green" data-toggle="tooltip" title="' + osTypeDesc + '"><i class="fa fa-android"></i></span>';
	} else if (deviceType == 'IOS_UUID') {
		return '<span class="lead text-black" data-toggle="tooltip" title="' + osTypeDesc + '"><i class="fa fa-apple"></i></span>';
	} else {
		return '<span class="lead text-black" data-toggle="tooltip" title="' + osTypeDesc + '">-</span>';
	}
}

</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1><fmt:message key="issuance"/> <small><fmt:message key="issuance.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.log"/></li>
			<li class="active"><i class="fa fa-registered"></i> <fmt:message key="nav.issuance" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="row">
		
        <div class="col-md-2">

			<!-- 발급유형 -->
        	<div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title"><fmt:message key="issuance.issuanceType"/></h3>

              <div class="box-tools">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
              </div>
            </div>
            <div class="box-body no-padding">
            	<tag:listFilter key="issueType" id="mmth-issueTypeList" clazzNames="mmth-nav-light" useAll="true"><c:forEach items="${IssuanceTypes}" var="type">
            		<tag:filterItem value="${type.key}" msgKey="${type.value}"/></c:forEach>
            	</tag:listFilter>
            </div>
            <!-- /.box-body -->
          </div>


        </div>
        <!-- /.col -->
        
        
        
		<div class="col-md-10">
        	<div class="box mmth-box">
            	<div class="box-header with-border">
              	<h3 class="box-title"><fmt:message key="issuance.list"/></h3>
				<table class="table mmth-pageSearch-field">
					<colgroup><col width="40%"><col width="30%"><col width="30%"></colgroup>
					<thead>
						<tr>
							<th></th>
							<th><tag:dateRangeFieldFilter startDateFieldKey="startDate" labelMsgKey="common.regDate" endDateFieldKey="endDate" id="issuance-dateRange" /></th>
							<th><tag:singleSearchFilter fieldKey="username" msgKey="common.username" id="searchField" /></th>
						</tr>
					</thead>
				</table>
              <!-- /.box-tools -->
            </div>
            <!-- /.box-header --><c:set var="cols"><c:out value="${(AUTH_METHOD_BIOTP_ENABLED or AUTH_METHOD_SAOTP_ENABLED)? 8: 6}"/></c:set>
            <tag:pagination noListMsgKey="issuance.noList" cols="${cols}">
				<th><fmt:message key="common.idxLabel" /></th>
				<th><fmt:message key="common.username" /></th>
				<th><fmt:message key="issuance.issuanceType"/></th>
				<th>
					<tag:pageFilter wrappedKey="common.authMethod.arg" key="authType" id="log-authType"><c:forEach items="${AuthMethodTypes}" var="type">
						<tag:filterItem wrappedKey="common.authMethod.arg" value="${type.key}" msgKey="${type.value }"/></c:forEach>
					</tag:pageFilter>
				</th>
				<c:if test="${AUTH_METHOD_BIOTP_ENABLED or AUTH_METHOD_SAOTP_ENABLED }">
				<th><fmt:message key="common.tokenId" /></th></c:if>
				<th><fmt:message key="user.device.model" /></th>
				<th><fmt:message key="user.deviceType" /></th>
				<th><fmt:message key="common.regDateTime" /></th>
			</tag:pagination>

          </div> 
          <!-- /. box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
		
	</section>
	<!-- /.content -->

</jsp:body>
</tag:page>