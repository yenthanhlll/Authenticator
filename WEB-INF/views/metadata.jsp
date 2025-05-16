<%--

	// page for metadata
	 
--%><%@ include file="/WEB-INF/views/common/def.jsp" %><%-- 
--%><tag:page onload="init" viewUriPrefix="/web/manager/metadata/rest" hasDetailsBox="true" useFilestyle="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
// Tabel contents generations
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};

	if (cellNum == 1) {
		attr['class'] = 'text-center text-bold text-muted';
	} else if (cellNum == 2) {
		attr['class'] = 'text-left';
	} else if (cellNum == 4 ) {
		attr['class'] = 'lead text-center';
	} else {
		attr['class'] = 'text-center';
	}

	return attr;
};

MMTHUtils.view.pagination.cellContentFuncs = new Array();

//col0 : idx
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return 0;
});

// col1 : aaid
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.aaid;
});

// col2 : alias
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	if (rowData.alias) {
		return '<span data-toggle="tooltip" title="'+ rowData.alias + '">' + rowData.alias.abbr(20) + '</span>';
	}

	return '';
});

//col3 : 인증방법
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.userVerificationDesc;
});

// col4 : diabled
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.disabled == '${DISABLED}') {
		return '<span class="text-danger" data-toggle="tooltip" title="<fmt:message key="metadata.label.disabled.DISABLED"/>"><i class="fa fa-ban"></i></span>';
	} else {
		return '<span class="text-success" data-toggle="tooltip" title="<fmt:message key="metadata.label.disabled.ENABLED"/>"><i class="fa fa-check-circle-o"></i></span>';
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

function init(){
	// 현재 정책 확인하기
	$('#btnCurrentPolicy').popover({
		html : true,
	    placement:'bottom',
	    delay: { "show": 500, "hide": 100 },
	    content : function(){
	    	
	    	var caller = ajaxUtil(MMTHUtils.view.policyUrl());
	    	caller.setResponseDataHandler(function(resp){
	    		
	    		if (resp.data) {
	    			var contents = JSON.stringify(JSON.parse(resp.data.policy), null, 2); 
	    			elmt('currentPolicy').val(contents);	
	    		} else {
	    			elmt('currentPolicy').val('<fmt:message key="metadata.validate.invalidPolicy"/>');
	    		}
	    		
	    		
	    	});
	    	
	    	caller.execute();
	    	
	    
	        return '<pre id="currentPolicy"></pre>';
	    }
	}).on('shown.bs.popover', function(e){
		
		// 버튼 위치를 기준으로 popover 위치를 재조정한다.
		var left = $(this).offset().left - 50;
		var popover = $('.popover'); 
		if(popover.hasClass('in')){
			var posLeft = popover.css('left');
		    popover.css({'display': 'inline-table', 'left' : left + 'px' });
		}
		
	});
	
	$('#metadataUploadModal').on('show.bs.modal', function (e) {
		resetUploadForm();
	});
	
	$('#btnUpload').on('click', function(){
		
		var form = document.forms.uploadMetadataForm;
		var formData = new FormData(form);
	
		var caller = ajaxUtil(MMTHUtils.view.urlPrefix + '/upload');
		caller.setData(formData);
		caller.setContentType(false);
		caller.setProcessData(false)
		caller.setContextFormId('uploadMetadataContext');
		caller.setResponseDataHandler(function(resp){
			
			if (resp.hasMessages) {
				if (resp.generalMessages)
					handleGeneralMessage(resp.generalMessages);
			} else {
				$('#metadataUploadModal').modal('hide').on('hidden.bs.modal', function(){
					resetUploadForm();
				});
				
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
				    message: MMTHUtils.bundle['common.desc.saved'],
				    callback : function(){
				    	$('#mmth-detailsBox').boxWidget('expand');
				    }
				});
			}

		});
		caller.execute();
	});

}

function resetUploadForm(){
	removeContextMessages(byId('uploadMetadataContext'));
	// 필드 값 삭제
	$('#uploadMetadataContext').find('.form-control').val('');
	$(":file").filestyle('clear');
}

</script>
</jsp:attribute>
<jsp:body>
	<!-- =============================================== -->
 
	<!-- Content Header (Page header) -->
	<sec:authorize access="hasAnyAuthority('ADMIN')">
	<section class="content-header">
		<h1><fmt:message key="metadata"/> <small><fmt:message key="metadata.admin.desc"/></small></h1>
		<ol class="breadcrumb">		
			<li class="active"><fmt:message key="nav.title.admin.policy"/></li>
			<li class="active"><i class="fa fa-certificate"></i> <fmt:message key="nav.policy.admin.metadata" /></li	>
		</ol>
	</section>
	</sec:authorize>

	<sec:authorize access="hasAnyAuthority('DEV')">
	<section class="content-header">
		<h1><fmt:message key="metadata"/> <small><fmt:message key="metadata.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.policy"/></li>
			<li class="active"><i class="fa fa-certificate"></i> <fmt:message key="nav.policy.metadata" /></li>
		</ol>
	</section>
	</sec:authorize>
	
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-12" id="statsWrap">
				<!-- Default box -->
				<tag:stats titleMsgKey="metadata.stats">
					<tag:statsItem statsKey="total" labelColor="default" msgKey="metadata.stats.total"/>
					<tag:statsItem statsKey="allowed" labelColor="success" msgKey="metadata.stats.allowed"/>
				</tag:stats>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
			
				<!-- Default box -->
				<div class="box mmth-box">
					<div class="box-header">
						<h3 class="box-title"><fmt:message key="metadata.list"/>&nbsp;&nbsp;<button id="btnCurrentPolicy" class="btn btn-xs mmth-policy-btn" data-container="body" data-toggle="popover"><i class="fa fa-get-pocket"></i> <fmt:message key="btn.currentPolicy"/></button></h3>
						
						<table class="table mmth-pageSearch-field">
							<colgroup><col width="20%"><col width="50%"><col width="30%"></colgroup>
							
							<thead>
								<tr>
									<th>
									<sec:authorize access="hasAuthority('DEV')">
									<button class="btn btn-primary mmth-btn btn-sm" id="btn-addNewMetadata" data-toggle="modal" data-target="#metadataUploadModal"><i class="fa fa-plus"></i>&nbsp;&nbsp;<fmt:message key="btn.add"/></button>
									</sec:authorize>
									</th>
									<th></th>
									<th>
										<tag:searchFilter id="aaid-searchField">
											<tag:filterItem value="aaid" msgKey="common.aaid"/>
											<tag:filterItem value="alias" msgKey="common.alias"/>
										</tag:searchFilter>
									</th>
								</tr>
							</thead>
						</table>
					</div>
					
					<tag:pagination noListMsgKey="metadata.noList" cols="7">
						<th><fmt:message key="common.idxLabel" /></th>
						<th><fmt:message key="common.aaid" /></th>
						<th><fmt:message key="common.alias" /></th>
						<sec:authorize access="hasAuthority('ADMIN')">
						<th> <fmt:message key="metadata.label.userVerification" /></th>
						</sec:authorize>
						<sec:authorize access="hasAuthority('DEV')">
						<th>
							<tag:pageFilter key="userVerification" wrappedKey="metadata.list.userVerification" id="metadata-userVerification"><c:forEach items="${UserVerificationMethods}" var="type">
								<tag:filterItem wrappedKey="metadata.list.userVerification" value="${type.key}" msgKey="${type.value}"></tag:filterItem>
								</c:forEach>
							</tag:pageFilter>
							
						</th>
						</sec:authorize>
						<th><tag:disabledFilter wrappedKey="metadata.list.disabled" id="metadata-disabled" enalbledKey="metadata.label.disabled.ENABLED" disabledKey="metadata.label.disabled.DISABLED"/></th>
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
				<tag:detailsBox titleKeyDetails="metadata.details" titleKeyNew="metadata.details.new" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true">

					<!-- id -->
					<tag:formItem formField="true" valueKey="id" hidden="true"/>
					
					<!-- aaid -->
	              	<tag:formItem formField="true" valueKey="aaid" labelKey="common.aaid" disabled="true" maxLength="9"/>
	              	
	              	<!-- alias  -->
	              	<tag:formItem formField="true" valueKey="alias" labelKey="common.alias" maxLength="40" placeholderKey="common.desc.alias"/>
	              	
	              	<!-- userVerification -->
	              	<tag:formItem formField="true" valueKey="userVerification" selectOptions="${UserVerificationMethods}" labelKey="metadata.label.userVerification"/>
	              	
	              	<!-- disabled  -->
	              	<tag:formItem formField="true" valueKey="disabled" selectOptions="${DisabledStatus}" labelKey="metadata.label.disabled" tooltipKey="metadata.label.disabled.desc"/>
	              
					<!-- description -->
					<tag:formItem formField="true" valueKey="description" labelKey="common.description" textarea="true" maxLength="150"/>
					
					<!-- tsReg -->
					<tag:formItem formField="false" valueKey="tsReg" labelKey="common.regDateTime" readonly="true"/>
					
					<!-- metadataAsJson -->
					<tag:formItem formField="false" valueKey="metadataAsJson" labelKey="metadata.label.metadataAsJson" textarea="true" readonly="true" rows="15"/>
					
				</tag:detailsBox>
			</div>
		</div>
		</sec:authorize>
		
		<sec:authorize access="hasAnyAuthority('DEV')">	
		<div class="row">
			<div class="col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
				<tag:detailsBox titleKeyDetails="metadata.details" titleKeyNew="metadata.details.new" useForm="true" useDelete="true" deleteTooltipKey="metadata.label.delete.desc">

					<!-- id -->
					<tag:formItem formField="true" valueKey="id" hidden="true"/>
					
					<!-- aaid -->
	              	<tag:formItem formField="true" valueKey="aaid" labelKey="common.aaid" disabled="true" maxLength="9"/>
	              	
	              	<!-- alias  -->
	              	<tag:formItem formField="true" valueKey="alias" labelKey="common.alias" maxLength="40" placeholderKey="common.desc.alias"/>
	              	
	              	<!-- userVerification -->
	              	<tag:formItem formField="true" valueKey="userVerification" selectOptions="${UserVerificationMethods}" labelKey="metadata.label.userVerification"/>
	              	
	              	<!-- disabled  -->
	              	<tag:formItem formField="true" valueKey="disabled" selectOptions="${DisabledStatus}" labelKey="metadata.label.disabled" tooltipKey="metadata.label.disabled.desc"/>
	              
					<!-- description -->
					<tag:formItem formField="true" valueKey="description" labelKey="common.description" textarea="true" maxLength="150"/>
					
					<!-- tsReg -->
					<tag:formItem formField="false" valueKey="tsReg" labelKey="common.regDateTime" readonly="true"/>
					
					<!-- tsUpdate -->
					<tag:formItem formField="false" valueKey="tsUpdated" labelKey="common.updateDateTime" readonly="true"/>

					<!-- metadataAsJson -->
					<tag:formItem formField="false" valueKey="metadataAsJson" labelKey="metadata.label.metadataAsJson" textarea="true" readonly="true" rows="15"/>
					
				</tag:detailsBox>
			</div>
		</div>
		</sec:authorize>
	</section>
	<!-- /.content -->
	
	
	<!-- Modal metadata add -->
	<div class="modal fade" id="metadataUploadModal" tabindex="-1" role="dialog" aria-labelledby="metadataUploadModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="metadataUploadModalLabel"> <fmt:message key="metadata.details.new"/></h4>
			    </div>
			    <div class="modal-body">
					<form class="form-horizontal" name="uploadMetadataForm" id="uploadMetadataContext" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
						<tag:formItem formField="false" valueKey="add-alias" labelKey="common.alias" maxLength="40" placeholderKey="common.desc.alias"/>
						
						<tag:formItem formField="false" valueKey="metadataFile" labelKey="metadata.label.file" file="true" accept="*.json" />
											
						<tag:formItem formField="false" valueKey="add-description" labelKey="common.description" textarea="true" maxLength="150"/>

					</form>
			    </div>
			    <div class="modal-footer"  id="addBtnContext">
			    	<button type="button" data-dismiss="modal" aria-label="Close" class="btn btn-md btn-default mmth-btn col-md-5 col-sm-6"><fmt:message key="btn.close"/></button>
			    	<button type="button" class="btn btn-md btn-primary mmth-btn col-md-7 col-sm-6" id="btnUpload"><i class="fa fa-save"></i> <fmt:message key="btn.save"/></button>
			    </div>
			</div>
		</div>
	</div>
	
</jsp:body>
</tag:page>