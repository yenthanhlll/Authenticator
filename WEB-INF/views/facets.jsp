<%-- 
	#
	#	Trusted facets page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" viewUriPrefix="/web/manager/facets/rest" hasDetailsBox="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
// cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};

	if (cellNum == 1) {
		attr['class'] = 'text-left text-bold text-muted';
	} else if (cellNum == 2) {
		attr['class'] = 'text-left';
	} else if (cellNum == 4 ) {
		attr['class'] = 'lead text-center';
	} else {
		attr['class'] = 'text-center';
	}

	return attr;
};

// Tabel contents generations
MMTHUtils.view.pagination.cellContentFuncs = new Array();
//col0 : idx
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return 0;
});

// col1 : facetId
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.facetId;
});

// col2 : alias
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){

	if (rowData.alias) {
		return '<span data-toggle="tooltip" title="'+ rowData.alias + '">' + rowData.alias.abbr(20) + '</span>';
	}

	return '';
});

// col3 : version
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.majorVer + '.' + rowData.minorVer;
});

// col4 : disabled
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.disabled == '${DISABLED}') {
		return '<span class="text-danger" data-toggle="tooltip" title="<fmt:message key="facet.label.disabled.DISABLED"/>"><i class="fa fa-ban"></i></span>';
	} else {
		return '<span class="text-success" data-toggle="tooltip" title="<fmt:message key="facet.label.disabled.ENABLED"/>"><i class="fa fa-check-circle-o"></i></span>';
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

	$('#btnCurrentTrustedFacets').popover({
		html : true,
	    placement:'bottom',
	    delay: { "show": 500, "hide": 100 },
	    content : function(){
	    	
	    	var caller = ajaxUtil(MMTHUtils.view.policyUrl());
	    	caller.setResponseDataHandler(function(resp){
	    		
	    		if (resp.data) {
	    			var contents = JSON.stringify(resp.data.policy, null, 2); 
		    		elmt('currentTrustedFacets').val(contents);
	    		} else {
	    			elmt('currentTrustedFacets').val('<fmt:message key="facet.validate.invalidPolicy"/>');
	    		}
	    	});
	    	
	    	caller.execute();
	    	
	    
	        return '<pre id="currentTrustedFacets"></pre>';
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
		$('#facetId').removeAttr('disabled');
	}
	
	$('#facetId').focus();

}


</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<sec:authorize access="hasAnyAuthority('ADMIN')">
	<section class="content-header">
		<h1><fmt:message key="facet"/> <small><fmt:message key="facet.admin.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.admin.policy"/></li>
			<li class="active"><i class="fa fa-shield"></i> <fmt:message key="nav.policy.admin.facets" /></li>
		</ol>
	</section>
	</sec:authorize>
	
	<sec:authorize access="hasAnyAuthority('DEV')">
	<section class="content-header">
		<h1><fmt:message key="facet"/> <small><fmt:message key="facet.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.policy"/></li>
			<li class="active"><i class="fa fa-shield"></i> <fmt:message key="nav.policy.facets" /></li>
		</ol>
	</section>
	</sec:authorize>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-12" id="statsWrap">
				<!-- Default box -->
				<tag:stats titleMsgKey="facet.stats">
					<tag:statsItem statsKey="total" labelColor="default" msgKey="facet.stats.total"/>
					<tag:statsItem statsKey="trustedFacet" labelColor="success" msgKey="facet.stats.enabled"/>
				</tag:stats>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
			
				<!-- Default box -->
				<div class="box mmth-box">
					<div class="box-header">
						<h3 class="box-title"><fmt:message key="facet.list"/> &nbsp;&nbsp;<button id="btnCurrentTrustedFacets" class="btn btn-xs mmth-policy-btn" data-container="body" data-toggle="popover"><i class="fa fa-get-pocket"></i> <fmt:message key="btn.currentFacets"/></button></h3>
						
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
										<tag:searchFilter id="facet-searchField">
											<tag:filterItem value="facetId" msgKey="facet.label.facetId"/>
											<tag:filterItem value="alias" msgKey="common.alias"/>
										</tag:searchFilter>
									</th>
								</tr>
							</thead>
						</table>

					</div>
					
					<tag:pagination noListMsgKey="facet.noList" cols="8">
						<th><fmt:message key="common.idxLabel" /></th>
						<th><fmt:message key="facet.label.facetId" /></th>
						<th><fmt:message key="common.alias" /></th>
						<th><fmt:message key="facet.label.version" /></th>
						<th><tag:disabledFilter wrappedKey="facet.list.disabled" id="facet-disabled" enalbledKey="facet.label.disabled.ENABLED" disabledKey="facet.label.disabled.DISABLED"/></th>
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
				<tag:detailsBox titleKeyDetails="facet.details" titleKeyNew="facet.details.new" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true">
					<!-- id -->
					<tag:formItem formField="true" valueKey="id" hidden="true"/>
					
					<!-- facetId -->
	              	<tag:formItem formField="true" valueKey="facetId" labelKey="facet.label.facetId" disabled="true" maxLength="512" tooltipKey="facet.label.facetId.desc" placeholderKey="facet.label.facetId.placeholer"/>
	              	
	              	<!-- alias  -->
	              	<tag:formItem formField="true" valueKey="alias" labelKey="common.alias" maxLength="40" placeholderKey="common.desc.alias"/>
	              	
	              	<!-- majorVer -->
	              	<tag:formItem formField="true" valueKey="majorVer" labelKey="facet.label.majorVer" maxLength="1" disabled="true"/>
	              	
	              	<!-- minorVer -->
	              	<tag:formItem formField="true" valueKey="minorVer" labelKey="facet.label.minorVer" maxLength="1" disabled="true"/>

	              	<!-- disabled  -->
	              	<tag:formItem formField="true" valueKey="disabled" selectOptions="${DisabledStatus}" labelKey="facet.label.disabled" tooltipKey="facet.label.disabled.desc"/>

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
				<tag:detailsBox titleKeyDetails="facet.details" titleKeyNew="facet.details.new" useForm="true" useDelete="true">
					<!-- id -->
					<tag:formItem formField="true" valueKey="id" hidden="true"/>
					
					<!-- facetId -->
	              	<tag:formItem formField="true" valueKey="facetId" labelKey="facet.label.facetId" disabled="true" maxLength="512" tooltipKey="facet.label.facetId.desc" placeholderKey="facet.label.facetId.placeholer"/>
	              	
	              	<!-- alias  -->
	              	<tag:formItem formField="true" valueKey="alias" labelKey="common.alias" maxLength="40" placeholderKey="common.desc.alias"/>
	              	
	              	<!-- majorVer -->
	              	<tag:formItem formField="true" valueKey="majorVer" labelKey="facet.label.majorVer" maxLength="1" disabled="true"/>
	              	
	              	<!-- minorVer -->
	              	<tag:formItem formField="true" valueKey="minorVer" labelKey="facet.label.minorVer" maxLength="1" disabled="true"/>

	              	<!-- disabled  -->
	              	<tag:formItem formField="true" valueKey="disabled" selectOptions="${DisabledStatus}" labelKey="facet.label.disabled" tooltipKey="facet.label.disabled.desc"/>

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