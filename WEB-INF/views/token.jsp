<%-- 
	#
	#	Token page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" viewUriPrefix="/web/manager/token/rest" useFilestyle="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
//cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};

	if (cellNum == 1) {
		attr['class'] = 'text-center text-bold text-muted';
	} else {
		attr['class'] = 'text-center';
	}

	return attr;
};
//Tabel contents generations
MMTHUtils.view.pagination.cellContentFuncs = new Array();

//col0 : idx
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return 0;
});

// col1 : tokenId
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tokenId;
});

// col2 : status
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	var className = 'label-default';
	
	if (rowData.status == '${AVAILABLE}') {
		className = 'label-info';
	} else if (rowData.status == '${OCCUPIED}') {
		className = 'label-primary';
	} else if (rowData.status == '${DISCARD}') {
		className = 'label-danger';
	}

	return '<span class="label '+className+'">' +rowData.statusDesc + '</span>';
});

// col3 : tsReg
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsReg;
});

// col4 : username
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.username == null) {
		return MMTHUtils.bundle['common.blank']
	}

	return rowData.username;
});

// col5 : authType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.authTypeDesc?rowData.authTypeDesc:"";
});

// col6 : tsOccupied
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsOccupied;
});

// col7 : tsDiscard
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsDiscard;
});

//col9 : Lost
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	if (rowData.lost == 'Y') {
		return '<span class="label label-danger">' + rowData.lostDesc + '</span>'
	} else {
		return '<span class="label label-primary">' + rowData.lostDesc + '</span>'
	}
});


//col9 : tsLost
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsLost;
});

function init() {
	
	// modal 창 이벤트
	$('#uploadTokenModal').on('show.bs.modal', function (e) {
		resetUploadModal();
	});
	
	// 업로드 버튼 이벤트
	$('#btnUpload').on('click', function(e){

		var form = document.forms.uploadTokenForm;
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
					resetUploadModal();
				});

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
	});
}

function resetUploadModal() {
	removeContextMessages(byId('uploadTokenContext'));
	// 필드 값 삭제
	$('#uploadTokenContext').find('.form-control').val('');
	$(":file").filestyle('clear');
}
</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1><fmt:message key="token.sw"/> <small><fmt:message key="token.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.biotp"/></li>
			<li class="active"><i class="fa fa-key"></i> <fmt:message key="nav.swtoken" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content"> 
		
		<div class="row">
			<div class="col-md-12" id="statsWrap">
				<!-- Default box -->
				<tag:stats titleMsgKey="token.stats">
					<tag:statsItem statsKey="total" labelColor="default" msgKey="token.stats.total"/>
					<tag:statsItem statsKey="available" labelColor="info" msgKey="token.stats.available"/>
					<tag:statsItem statsKey="occupied" labelColor="primary" msgKey="token.stats.occupied"/>
					<tag:statsItem statsKey="discard" labelColor="danger" msgKey="token.stats.discard"/>
				</tag:stats><!-- ./ statsLocator -->
			</div>
		</div>
	
		<div class="row">
			<div class="col-md-12">
				<!-- Default box -->
				<div class="box mmth-box">
					<div class="box-header">
						<h3 class="box-title"><fmt:message key="token.list"/></h3>
						
						<table class="table mmth-pageSearch-field">
							<colgroup><col width="20%"><col width="50%"><col width="30%"></colgroup>
							
							<thead>
								<tr>
									<th>
										<sec:authorize access="hasAnyAuthority('ADMIN', 'DEV')"> 
										<button class="btn btn-primary mmth-btn btn-sm" id="btn-addNewToken" data-toggle="modal" data-target="#uploadTokenModal"><i class="fa fa-plus"></i>&nbsp;&nbsp;<fmt:message key="btn.add"/></button>
										</sec:authorize>
									</th>
									<th></th>
									<th>
										<tag:searchFilter id="token-searchField">
											<tag:filterItem value="tokenId" msgKey="common.tokenId"/>
											<tag:filterItem value="username" msgKey="token.list.usename"/>
										</tag:searchFilter>
									</th>
								</tr>
							</thead>
						</table>
					</div>
					
					
					<tag:pagination noListMsgKey="token.noList" cols="8">
						<th><fmt:message key="common.idxLabel" /></th>
						<th><fmt:message key="common.tokenId" /></th>
						<th>
							<tag:pageFilter wrappedKey="token.list.status" key="status" id="token-status"><c:forEach items="${TokenStatus}" var="type">
								<tag:filterItem wrappedKey="token.list.status" value="${type.key}" msgKey="${type.value }"/></c:forEach>
							</tag:pageFilter>
						</th>
						<th><fmt:message key="common.regDateTime" /></th>
						<th><fmt:message key="token.list.usename" /></th>
						<th><fmt:message key="token.list.authType" /></th>
						<th><fmt:message key="token.list.occupiedDateTime" /></th>
						<th><fmt:message key="token.list.discardDateTime" /></th>
						<th>
							<tag:pageFilter wrappedKey="token.list.lost" key="lost" id="token-lost"><c:forEach items="${YNStatus}" var="type">
								<tag:filterItem wrappedKey="token.list.lost" value="${type.key}" msgKey="${type.value }"/></c:forEach>
							</tag:pageFilter>
						</th>
						<th><fmt:message key="token.list.lostDateTime" /></th>
					</tag:pagination>

				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
	<!-- /.content -->
	
	
	<!-- Modal upload Token -->
	<div class="modal fade" id="uploadTokenModal" tabindex="-1" role="dialog" aria-labelledby="uploadTokenModallLabel">
		<div class="modal-dialog modal-lg" role="document">
	    	<div class="modal-content">
	    
	      		<div class="modal-header">
	      			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title" id="uploadTokenModalLabel"><fmt:message key="token.upload" /></h4>
	      		</div>
		  
	      		<div class="modal-body">
	      			<form class="form-horizontal" name="uploadTokenForm" id="uploadTokenContext" method="post" enctype="multipart/form-data" accept-charset="UTF-8">

	      				<tag:formItem formField="false" password="true" valueKey="pin" labelKey="token.upload.pin" maxLength="20" tooltipKey="token.upload.pin.desc"/>
						
						<tag:formItem formField="false" valueKey="ttkFile" labelKey="token.upload.ttk" file="true" accept="*.ttk" tooltipKey="token.upload.ttk.desc" />

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