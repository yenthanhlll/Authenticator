<%-- 
	#
	#	Service LOG page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init"  useDateRange="true" viewUriPrefix="/web/manager/servicelog/rest/branchlog" hasDetailsBox="true">
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

// col2  : authType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return '<strong class="text-info">' +  rowData.authTypeDesc + '</strong>';
});

// col3   : otp serial number
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return  '<strong class="text-primary">' + (rowData.tokenId != null? rowData.tokenId:'-')+ '</strong>';
});

// col4 : opType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return  '<strong class="text-primary">' + rowData.opTypeDesc+ '</strong>';
});

// //col3 : reqType
// MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
// 	return '<span class="label label-default">' + rowData.reqTypeDesc + '</span>';
// });


// // col3 : actionType
// MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
// 	if (rowData.actionType == 'SUCCESS') {
// 		return '<span class="label label-primary">' +rowData.actionTypeDesc+ '</span>';	
// 	} else if (rowData.actionType == 'FAIL') {
// 		return '<span class="label label-danger">' +rowData.actionTypeDesc+ '</span>';
// 	} else {
// 		return '<span class="label bg-yellow">' +rowData.actionTypeDesc+ '</span>';
// 	}
// });

// col5 : returnCode
// MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
// 	if (rowData.returnCode) {
// 		if (rowData.returnCodeRaw == '0000') {
// 			return '<code data-toggle="tooltip" title="'+ rowData.returnCodeDesc + '">' + rowData.returnCodeRaw + '</code>';
// 		} else {
// 			return '<code data-toggle="tooltip" title="'+ rowData.returnCodeDesc + ' ('+rowData.description+')' + '">' + rowData.returnCodeRaw + '</code>';	
// 		}
// 	}

// 	return '';
// });

//col5 : tsReg
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsReg;
});


//col6 : Detail
// MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
// 	return '<button type="button" class="btn btn-sm btn-primary mmth-btn btn-detailsBox" data-id="'+rowData.id+'"><fmt:message key="btn.details"/></button>';
// });


function init() {
	$("#exportExcelBtn").on('click', function(){
		GPTWR_DOWNLOAD('/web/manager/servicelog/rest//branchlog/export', 'branchLogForm');
	});

}

function GPTWR_DOWNLOAD(action, srcFormId){
	$("#formDownload").remove();
	var formDownload = $("#"+srcFormId).clone();
	
	formDownload.hide();
	$("body").append(formDownload);
	
	formDownload.attr('id', 'formDownload');
	formDownload.attr('action', action);
	formDownload.attr('target', 'HIDDEN_IFRAME');
	
	var param = {
			
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
// 			param[$dr.data('startkey')] = dateRange.startDate._d.formatDate();
			$('input[name="startDate"]').val(dateRange.startDate._d.formatDate());
// 			param[$dr.data('endkey')] = dateRange.endDate._d.formatDate();
			$('input[name="endDate"]').val(dateRange.endDate._d.formatDate());
		}	
	}
	
	//colone 해도 값은 복사가 안되어서..
	$(':input[name]', "#" + srcFormId).each(function () {              
		//console.log( $(this).attr('name') )
		$('[name=' + $(this).attr('name') + ']', "#formDownload").val($(this).val());
	});
	
	$('input[name="searchParams"]').val(JSON.stringify(param));
	
	$("#formDownload").submit(function(){
		console.log("submit...");
		console.log($("#formDownload").attr("action"))
		return true;
	});
	formDownload.submit();
}

function convertLongtoIP(ipl){
	if (ipl)
	{
		return ( (ipl>>>24) +'.' +
			      (ipl>>16 & 255) +'.' +
			      (ipl>>8 & 255) +'.' +
			      (ipl & 255) );
	}
	else
	{		
		return '0.0.0.0';
	}
};

function fillDetailCallback(data){
	
	$details = $('#detailsItemList');
	$details.find('span').html('');
		
	var details = data.details;
	
	if (!details) {
		return;
	}
	
	$details.find('#username').html(details.username);
	$details.find('#opTypeDesc').html(details.opTypeDesc);
	$details.find('#reqTypeDesc').html('<span class="label label-default">' +details.reqTypeDesc+ '</span>');
	if (details.result == 'SUCCESS') {
		$details.find('#actionTypeDesc').html('<span class="label label-primary">' +details.actionTypeDesc+ '</span>');
	} else if (details.actionType == 'FAIL') {
		$details.find('#actionTypeDesc').html('<span class="label label-danger">' +details.actionTypeDesc+ '</span>');
	} else {
		$details.find('#actionTypeDesc').html('<span class="label bg-yellow">' +details.actionTypeDesc+ '</span>');
	}

	if (details.returnCode) {
		$details.find('#returnCodeDesc').html(details.returnCodeDesc);
    }
	
	if (details.deviceType) {
		
		if (details.deviceType === 'ANDROID_DEVICE' || details.deviceType === 'ANDROID_WIFI') {
			$details.find('#deviceDesc').html('<span class="text-green" data-toggle="tooltip" title="' + details.deviceTypeDesc + '"><i class="fa fa-android"></i></span>');
		} else if (details.deviceType === 'IOS_UUID') {
			$details.find('#deviceDesc').html('<span class="text-black" data-toggle="tooltip" title="' + details.deviceTypeDesc + '"><i class="fa fa-apple"></i></span>');
		} else {
			$details.find('#deviceDesc').html('<span class="text-yellow" data-toggle="tooltip" title="' + details.deviceTypeDesc + '"><i class="fa fa-mobile"></i></span>');
		}
		$details.find('#ctx-deviceDesc').show();
	} else {
		$details.find('#ctx-deviceDesc').hide();
	}

	var $table = $('#deviceDetailInfo');
	$table.empty();
	
	if(details.customData) {
		$('<tr><td style="border-bottom: 1px solid #f4f4f4; padding:10px; width: 200px;" class="text-center text-bold text-muted">IP</td><td style="border-bottom: 1px solid #f4f4f4; width: 200px;">' + convertLongtoIP(details.customData.ip) + '</td>' +
			'<td style="border-bottom: 1px solid #f4f4f4; padding:10px; width: 200px;" class="text-center text-bold text-muted">Mac Address</td><td class="text-center" style="border-bottom: 1px solid #f4f4f4;">' + (details.customData.macAddress == null? '-':details.customData.macAddress) + '</td></tr>').appendTo($table);
	}
	
	if (details.deviceId != null){
		$('<tr><td style="padding:10px; width: 200px;" class="text-center text-bold text-muted">Device ID</td><td style="width: 200px;">' + details.deviceId + '</td>' +
		'<td style="padding:10px; width: 200px;" class="text-center text-bold text-muted">Device Model</td><td class="text-center">' + (typeof details.model == 'undefined'? '-': details.model) + '</td></tr>').appendTo($table);
	}
	
	if (details.description && details.description !== '-') {
		$details.find('#description').html(details.description);
		$details.find('#ctx-description').show();
	} else {
		$details.find('#ctx-description').hide();
	}
	
	$details.find('#tsReg').html(details.tsReg);

	$('#mmth-detailsBox').boxWidget('expand');
	
}

</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1><fmt:message key="branch.servicelog"/> <small><fmt:message key="branch.servicelog.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.biotp"/></li>
			<li class="active"><i class="fa fa-list-alt"></i> <fmt:message key="nav.branch.serviceLog" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="row">
		
        <div class="col-md-2">

			<!-- 등록/해지 -->
        	<div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title"><fmt:message key="servicelog.opType"/></h3>
              

              <div class="box-tools">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
              </div>
            </div>
            <div class="box-body no-padding">
            	<tag:listFilter key="opType" id="mmth-opTypeRegList" clazzNames="mmth-nav-light">
            		<li class="active"><a href="#mmth-pageList" data-value=""><i class="fa fa-list-alt"></i> <fmt:message key="common.all"/></a></li><c:forEach items="${LogOperationTypes}" var="type">
            		<tag:filterItem value="${type.key }" msgKey="${type.value }"/></c:forEach>
            	</tag:listFilter>
            </div>
            <!-- /.box-body -->
          </div>
        
		 

        </div>
        <!-- /.col -->
		
		
		<form id="branchLogForm" method="POST" class="form-inline" role="form">
			<input type="hidden" name="startDate" value=""/>
			<input type="hidden" name="endDate" value=""/>
			<input type="hidden" name="searchParams" value=""/>
		</form>
		<div class="col-md-10">
        	<div class="box mmth-box">
            	<div class="box-header with-border">
              	<h3 class="box-title"><fmt:message key="branch.servicelog.list"/></h3>
              	
				<button type="button" id="exportExcelBtn" style="float: right;" class="btn mmth-filter-btn btn-sm">Export<i style="margin-left: 7px;" class="fa fa-file-excel-o"></i></button>
				
            		
				<table class="table mmth-pageSearch-field">
					<colgroup><col width="40%"><col width="30%"><col width="30%"></colgroup>
					<thead>
						<tr>
							<th></th>
							<th><tag:dateRangeFieldFilter startDateFieldKey="startDate" labelMsgKey="common.regDate" endDateFieldKey="endDate" id="log-dateRange" /></th>
							<th><tag:singleSearchFilter fieldKey="username" msgKey="common.username" id="searchField" /></th>
						</tr>
					</thead>
				</table>
              <!-- /.box-tools -->
            </div>
            <!-- /.box-header -->
            <tag:pagination noListMsgKey="servicelog.noList" cols="8">
				<th><fmt:message key="common.idxLabel" /></th>
				<th><fmt:message key="common.username" /></th>
				<th><fmt:message key="common.authMethod" /></th>
				<th><fmt:message key="common.tokenId" /></th>
				<th><fmt:message key="servicelog.opType"/></th>
<!-- 				<th> -->
<%-- 					<tag:pageFilter wrappedKey="servicelog.list.reqType" key="reqType" id="log-reqType"><c:forEach items="${OpRequstTypes}" var="type"> --%>
<%-- 						<tag:filterItem wrappedKey="servicelog.list.reqType" value="${type.key}" msgKey="${type.value }"/></c:forEach> --%>
<%-- 					</tag:pageFilter> --%>
<!-- 				</th> -->
				
<!-- 				<th> -->
<%-- 					<tag:pageFilter wrappedKey="servicelog.list.actionType" key="actionType" id="log-actionType"><c:forEach items="${LogActionTypes}" var="type"> --%>
<%-- 						<tag:filterItem wrappedKey="servicelog.list.actionType" value="${type.key}" msgKey="${type.value }"/></c:forEach> --%>
<%-- 					</tag:pageFilter> --%>
<!-- 				</th> -->
<%-- 				<th><fmt:message key="servicelog.returnCode" /></th> --%>
				<th><fmt:message key="branch.time" /></th>
			</tag:pagination>

          </div> 
          <!-- /. box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
      
<!--       <div class="row"> -->
<!-- 		<div class="col-md-offset-2 col-md-10"> -->
<%-- 			<tag:detailsBox titleKeyDetails="servicelog.details" excludeFooter="true" boxBodyClazzName="no-padding"> --%>
<!-- 				<ul class="nav nav-pills nav-stacked no-pointer" id="detailsItemList"> -->
<%-- 					<li id="ctx-username"><a href="#"><i class="fa fa-user"></i><fmt:message key="common.username"/> &nbsp;<span class="pull-right text-navy" id="username"></span></a></li> --%>
<%-- 					<li id="ctx-opTypeDesc"><a href="#"><i class="fa fa-map-signs"></i><fmt:message key="servicelog.opType"/> &nbsp;<span class="pull-right text-light-blue" id="opTypeDesc"></span></a></li> --%>
<%-- 					<li id="ctx-reqTypeDesc"><a href="#"><i class="fa fa-bullhorn"></i><fmt:message key="servicelog.reqType"/> &nbsp;<span class="pull-right text-light-blue" id=reqTypeDesc></span></a></li> --%>
<%-- 					<li id="ctx-actionTypeDesc"><a href="#"><i class="fa fa-gavel"></i><fmt:message key="servicelog.actionType"/> &nbsp;<span class="pull-right text-light-blue" id="actionTypeDesc"></span></a></li> --%>
<%-- 					<li id="ctx-returnCodeDesc"><a href="#"><i class="fa fa-dot-circle-o"></i><fmt:message key="servicelog.returnCode"/> &nbsp;<span class="pull-right" id="returnCodeDesc"></span></a></li> --%>
<%-- 					<li id="ctx-deviceDesc"><a href="#"><i class="fa fa-mobile"></i><fmt:message key="servicelog.device"/> &nbsp;<span class="pull-right text-muted" id="deviceDesc"></span></a></li> --%>
<!-- 					<li id="ctx-deviceInfo"> -->
<!-- 						<div class="box-body"><table class="table table-bordered table-hover mmth-pageList" id="deviceDetailInfo" style="border: 5px solid #f4f4f4;"></table></div> -->
<!-- 					</li> -->
<%-- 					<li id="ctx-description"><a href="#"><i class="fa fa-quote-left"></i><fmt:message key="common.description"/> &nbsp;<br/><br/><span class="text-muted" id="description"></span></a></li> --%>
<%-- 					<li id="ctx-tsReg"><a href="#"><i class="fa fa-clock-o"></i><fmt:message key="common.regDateTime"/> &nbsp;<span class="pull-right text-black" id="tsReg"></span></a></li> --%>
<!-- 				</ul> -->
<%-- 			</tag:detailsBox> --%>
<!-- 		</div> -->
<!-- 	</div> -->
		
	</section>
	<!-- /.content -->

</jsp:body>
</tag:page>