<%-- 
	#
	#	APP agent page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" viewUriPrefix="/web/manager/appagent/rest" hasDetailsBox="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
// cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};

	if (cellNum == 1) {
		attr['class'] = 'text-left';
	} else if (cellNum == 2 || cellNum == 4 ) {
		attr['class'] = 'lead text-center';
	} else if (cellNum == 3) {
		attr['class'] = 'text-left text-bold text-muted word-break';
	} else {
		attr['class'] = 'text-center';
	}

	return attr;
};

// Tabel contents generations
MMTHUtils.view.pagination.cellContentFuncs = new Array();
// col0 : idx
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return 0;
});

// col1 : alias
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){

	if (rowData.alias) {
		return '<span data-toggle="tooltip" title="'+ rowData.alias + '">' + rowData.alias.abbr(32) + '</span>';
	}

	return '';
});


// col2 : osType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	if (rowData.osType == '${ANDROID}') {
		return '<span class="text-green" data-toggle="tooltip" title="' + rowData.osTypeDesc + '"><i class="fa fa-android"></i></span>';
	} else if (rowData.osType == '${IOS}') {
		return '<span class="text-black" data-toggle="tooltip" title="' + rowData.osTypeDesc + '"><i class="fa fa-apple"></i></span>';
	} else {
		return '<span class="text-yellow" data-toggle="tooltip" title="' + rowData.osTypeDesc + '"><i class="fa fa-mobile"></i></span>';
	}
	
	return rowData.osTypeDesc;
});

// col3 : pkgUnique
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	if (rowData.pkgUnique) {
		return '<span data-toggle="tooltip" title="'+ rowData.pkgUnique + '">' + rowData.pkgUnique.abbr(64) + '</span>';
	}

	return '';
});

//col4 : disabled
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.disabled == '${DISABLED}') {
		return '<span class="text-danger" data-toggle="tooltip" title="<fmt:message key="appagent.label.disabled.DISABLED"/>"><i class="fa fa-ban"></i></span>';
	} else {
		return '<span class="text-success" data-toggle="tooltip" title="<fmt:message key="appagent.label.disabled.ENABLED"/>"><i class="fa fa-check-circle-o"></i></span>';
	}		
})

// col5 : tsReg
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsReg;
});

// col6 : details
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return '<button type="button" class="btn btn-sm btn-primary mmth-btn btn-detailsBox" data-id="'+rowData.id+'"><fmt:message key="btn.details"/></button>';
});
// -- End of contents generations


function init(){

	$('#btnCurrentAppAgent').popover({
		html : true,
	    placement:'bottom',
	    delay: { "show": 500, "hide": 100 },
	    content : function(){
	    	
	    	var caller = ajaxUtil(MMTHUtils.view.policyUrl());
	    	caller.setResponseDataHandler(function(resp){
	    		
	    		if (resp.data) {
	    			var contents = JSON.stringify(resp.data.policy, null, 2); 
		    		elmt('currentAppAgent').val(contents);
	    		} else {
	    			elmt('currentAppAgent').val('<fmt:message key="appagent.validate.invalidPolicy"/>');
	    		}
	    		
	    		
	    	});
	    	
	    	caller.execute();
	    	
	    
	        return '<pre id="currentAppAgent"></pre>';
	    }
	}).on('shown.bs.popover', function(e){
		
		// 버튼 위치를 기준으로 popover 위치를 재조정한다.
		var left = $(this).offset().left - 100;
		var popover = $('.popover'); 
		if(popover.hasClass('in')){
			var posLeft = popover.css('left');
		    popover.css({'display': 'inline-table', 'left' : left + 'px' });
		}
		
	});
}


function fillDetailsImp(details){
	var isNew = details.id == '${NEW_ID}';
	
	show('#ctx-disabled', !isNew);
	show('#ctx-tsReg', !isNew);
	show('#ctx-tsUpdated', !isNew);

	if (isNew) {
		$('#pkgUnique').removeAttr('disabled');
		$('#osType').removeAttr('disabled');
	}
	
	$('#pkgUnique').focus();

}


</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<sec:authorize access="hasAnyAuthority('DEV')">
	<section class="content-header">
		<h1><fmt:message key="appagent"/> <small><fmt:message key="appagent.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.policy"/></li>
			<li class="active"><i class="fa fa-android"></i> <fmt:message key="nav.policy.appagent" /></li>
		</ol>
	</section>
	</sec:authorize>
	
	<sec:authorize access="hasAnyAuthority('ADMIN')">
	<section class="content-header">
		<h1><fmt:message key="appagent"/> <small><fmt:message key="appagent.admin.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.admin.policy"/></li>
			<li class="active"><i class="fa fa-android"></i> <fmt:message key="nav.policy.admin.appagent" /></li>
		</ol>
	</section>
	</sec:authorize>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-12" id="statsWrap">
				<!-- Default box -->
				<tag:stats titleMsgKey="appagent.stats">
					<tag:statsItem statsKey="total" labelColor="default" msgKey="appagent.stats.total"/>
					<tag:statsItem statsKey="android" labelColor="success" msgKey="appagent.stats.android"/>
					<tag:statsItem statsKey="ios" labelColor="default bg-black" msgKey="appagent.stats.ios"/>
				</tag:stats>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
			
				<!-- Default box -->
				<div class="box mmth-box">
					<div class="box-header">
						<h3 class="box-title"><fmt:message key="appagent.list"/> &nbsp;&nbsp;<button id="btnCurrentAppAgent" class="btn btn-xs mmth-policy-btn" data-container="body" data-toggle="popover"><i class="fa fa-get-pocket"></i> <fmt:message key="btn.currentAppAgnet"/></button></h3>
						
						<table class="table mmth-pageSearch-field">

							<colgroup><col width="20%"><col width="50%"><col width="30%"></colgroup>
							
							<thead>
								<tr>
									<th>
									<sec:authorize access="hasAuthority('DEV')">
									<button class="btn btn-primary mmth-btn btn-sm btn-detailsBox" data-id="${NEW_ID}"><i class="fa fa-plus"></i>&nbsp;&nbsp;<fmt:message key="btn.add"/></button>
									</sec:authorize>
									</th>
									<th></th>
									<th>
										<tag:searchFilter id="appagent-searchField">
											<tag:filterItem value="pkgUnique" msgKey="appagent.label.pkgUnique"/>
											<tag:filterItem value="alias" msgKey="common.alias"/>
										</tag:searchFilter>
									</th>
								</tr>
							</thead>
						</table>

					</div>
					
					<tag:pagination noListMsgKey="appagent.noList" cols="7">
						<th><fmt:message key="common.idxLabel" /></th>
						<th><fmt:message key="common.alias" /></th>
						<th>
							<tag:pageFilter wrappedKey="appagent.list.osType" key="osType" id="appagent-osType"><c:forEach items="${AgentOsTypes }" var="type">
								<tag:filterItem wrappedKey="appagent.list.osType" value="${type.key }" msgKey="${type.value }"/></c:forEach>
							</tag:pageFilter>
						</th>
						<th><fmt:message key="appagent.label.pkgUnique" /></th>
						<th><tag:disabledFilter wrappedKey="appagent.list.disabled" id="appagent-disabled" enalbledKey="appagent.label.disabled.ENABLED" disabledKey="appagent.label.disabled.DISABLED"/></th>
						<th><fmt:message key="common.regDateTime" /></th>
						<th> </th>
					</tag:pagination>
		
				</div>
				<!-- /.box -->

			</div>
		</div>

		<sec:authorize access="hasAnyAuthority('ADMIN')">
		<div class="row">
			<div class="col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">				
				<tag:detailsBox titleKeyDetails="appagent.details" titleKeyNew="appagent.details.new" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true">			
					<!-- id -->
					<tag:formItem formField="true" valueKey="id" hidden="true"/>
					
					<!-- alias  -->
	              	<tag:formItem formField="true" valueKey="alias" labelKey="common.alias" maxLength="40" placeholderKey="common.desc.alias"/>
	              	
	              	<!-- osType  -->
	              	<tag:formItem formField="true" valueKey="osType" selectOptions="${AgentOsTypes}" labelKey="appagent.label.osType" disabled="true"/>
					
					<!-- pkgUnique -->
	              	<tag:formItem formField="true" valueKey="pkgUnique" labelKey="appagent.label.pkgUnique" disabled="true" maxLength="512" tooltipKey="appagent.label.pkgUnique.desc" placeholderKey="appagent.label.pkgUnique.placeholer"/>
	              	
	              	<!-- disabled  -->
	              	<tag:formItem formField="true" valueKey="disabled" selectOptions="${DisabledStatus}" labelKey="appagent.label.disabled" tooltipKey="appagent.label.disabled.desc"/>

					<!-- fcmAuthenticationKey  -->
	              	<tag:formItem formField="true" valueKey="fcmAuthenticationKey" labelKey="appagent.label.authorizationKey" tooltipKey="appagent.label.authorizationKey.desc" maxLength="2048"/>

					<!-- description -->
					<tag:formItem formField="true" valueKey="description" labelKey="common.description" textarea="true" maxLength="150"/>
					
					<!-- tsReg -->
					<tag:formItem formField="false" valueKey="tsReg" labelKey="common.regDateTime" readonly="true"/>
									
				</tag:detailsBox>
			</div>
		</div>
		</sec:authorize>
		
				<sec:authorize access="hasAnyAuthority('DEV')">
		<div class="row">
			<div class="col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">				
				<tag:detailsBox titleKeyDetails="appagent.details" titleKeyNew="appagent.details.new" useForm="true" useDelete="true">			
					<!-- id -->
					<tag:formItem formField="true" valueKey="id" hidden="true"/>
					
					<!-- alias  -->
	              	<tag:formItem formField="true" valueKey="alias" labelKey="common.alias" maxLength="40" placeholderKey="common.desc.alias"/>
	              	
	              	<!-- osType  -->
	              	<tag:formItem formField="true" valueKey="osType" selectOptions="${AgentOsTypes}" labelKey="appagent.label.osType" disabled="true"/>
					
					<!-- pkgUnique -->
	              	<tag:formItem formField="true" valueKey="pkgUnique" labelKey="appagent.label.pkgUnique" disabled="true" maxLength="512" tooltipKey="appagent.label.pkgUnique.desc" placeholderKey="appagent.label.pkgUnique.placeholer"/>
	              	
	              	<!-- disabled  -->
	              	<tag:formItem formField="true" valueKey="disabled" selectOptions="${DisabledStatus}" labelKey="appagent.label.disabled" tooltipKey="appagent.label.disabled.desc"/>

					<!-- fcmAuthenticationKey  -->
	              	<tag:formItem formField="true" valueKey="fcmAuthenticationKey" labelKey="appagent.label.authorizationKey" tooltipKey="appagent.label.authorizationKey.desc" maxLength="2048"/>

					<!-- description -->
					<tag:formItem formField="true" valueKey="description" labelKey="common.description" textarea="true" maxLength="150"/>
					
					<!-- tsReg -->
					<tag:formItem formField="false" valueKey="tsReg" labelKey="common.regDateTime" readonly="true"/>
					
					<!-- tsUpdate -->
					<tag:formItem formField="false" valueKey="tsUpdated" labelKey="common.updateDateTime" readonly="true"/>
					
				</tag:detailsBox>
			</div>
		</div>
		</sec:authorize>

	</section>
	<!-- /.content -->
</jsp:body>
</tag:page>