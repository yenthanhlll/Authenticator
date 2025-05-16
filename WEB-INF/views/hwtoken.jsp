<%-- 
	#
	#	Token page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" viewUriPrefix="/web/manager/hwtoken/rest" useFilestyle="true">
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
	} else if (rowData.status == '${SUSPEND}') {
		className = 'label-warning';
	}else if (rowData.status == '${DISCARD}') {
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
	if (rowData.type == '01') {
		//return 'MIRAE';
		return   '<span class="label '+ 'label-danger'+'">' + 'MIRAE' + '</span>';
	}
	else if(rowData.type == '02'){
		//return 'Advanced';
		return   '<span class="label '+ 'label-info'+'">' + 'OATH' + '</span>';
	}
	else if(rowData.type == '03'){
		//return 'Advanced';
		return   '<span class="label '+ 'label-primary'+'">' + 'Advanced' + '</span>';
	}
	else if(rowData.type == '04'){
		//return 'CARD';
		return   '<span class="label '+ 'label-danger'+'">' + 'Card' + '</span>';
	}
	else if(rowData.type == '05'){
		//return 'TOKEN';
		return   '<span class="label '+ 'label-primary'+'">' + 'Token' + '</span>';
	}
	//return rowData.authTypeDesc;
	
});

// col6 : tsOccupied
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsOccupied;
});

//col7 : tsSuspend
//MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
//	if (rowData.status == '${OCCUPIED}') {
//		return "";
//	}
//	return rowData.tsSuspend;
//});

// col8 : tsDiscard
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
		var hwTokenType = $("#hwtoken_type").val();
		
		var formData = new FormData(form);
		
		var caller = ajaxUtil(MMTHUtils.view.urlPrefix + '/upload/' + hwTokenType);
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
	
	$('#btnAllDelete').on('click', function(e){
		var caller = ajaxUtil(MMTHUtils.view.urlPrefix + '/alldelete');
		caller.setResponseDataHandler(function(resp){
			if (resp.data.pageContents) {
				setTableContentData(resp.data.pageContents);
				resetFiledFilter();
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
		<h1><fmt:message key="nav.hwtoken"/> <small><fmt:message key="token.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.biotp"/></li>
			<li class="active"><i class="fa fa-key"></i> <fmt:message key="nav.hwtoken" /></li>
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
					<!--<tag:statsItem statsKey="suspend" labelColor="warning" msgKey="token.stats.suspend"/>-->
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
							<colgroup><col width="10%"><col width="10%"><col width="50%"><col width="30%"></colgroup>
							
							<thead>
								<tr>
									<th>
										<sec:authorize access="hasAnyAuthority('ADMIN', 'DEV')"> 
										<button class="btn btn-primary mmth-btn btn-sm" id="btn-addNewToken" data-toggle="modal" data-target="#uploadTokenModal"><i class="fa fa-plus"></i>&nbsp;&nbsp;<fmt:message key="btn.add"/></button>
										</sec:authorize>
									</th>
									<th>
										<sec:authorize access="hasAnyAuthority('DEV')"> 
										<button class="btn btn-danger mmth-btn btn-sm" id="btn-allDeleteToken" data-toggle="modal" data-target="#allDeleteTokenModal"><fmt:message key="btn.alldel"/></button>
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
						<th><fmt:message key="hwtoken.type" /></th>
						<th><fmt:message key="token.list.occupiedDateTime" /></th>
						<!-- <th><fmt:message key="token.list.suspendDateTime" /></th>-->
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
						<!-- hw token type select 창 추가 -->
						<div class="form-group " id="ctx-hwtoken_type">
							<label class="col-md-5 col-sm-6 control-label" for="hwtoken_type"><fmt:message key="hwtoken.type"/></label>
							<div class="col-md-6 col-sm-5">
								<select class="mmth-form form-control formField" name="hwtoken_type" id="hwtoken_type" data-target=""> 
									<option value="CARD" selected><fmt:message key="hwtoken.label.cardtype"/></option>
									<option value="TOKEN"><fmt:message key="hwtoken.label.tokentype"/></option>
								</select>
								<span class="help-block"></span>
							</div>
						</div>
						<tag:formItem formField="false" valueKey="csvFile" labelKey="token.upload.csv" file="true" accept="*.csv" tooltipKey="token.upload.csv.desc" />

					</form>
	      		</div>

		      	<div class="modal-footer"  id="addBtnContext">
			    	<button type="button" data-dismiss="modal" aria-label="Close" class="btn btn-md btn-default mmth-btn col-md-5 col-sm-6"><fmt:message key="btn.close"/></button>
			    	<button type="button" class="btn btn-md btn-primary mmth-btn col-md-7 col-sm-6" id="btnUpload"><i class="fa fa-save"></i> <fmt:message key="btn.save"/></button>
			    </div>

	    	</div>
		</div>
	</div>
	
	<!-- Modal All Delete Token -->
	<div class="modal fade" id="allDeleteTokenModal" tabindex="-1" role="dialog" aria-labelledby="allDeleteTokenModallLabel">
		<div class="modal-dialog modal-lg" role="document">
	    	<div class="modal-content">
	    
	      		<div class="modal-header">
	      			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title" id="allDeleteTokenModallLabel"><fmt:message key="token.alldel" /></h4>
	      		</div>


		      	<div class="modal-footer"  id="addBtnContext">
			    	<button type="button" data-dismiss="modal" aria-label="Close" class="btn btn-md btn-default mmth-btn col-md-5 col-sm-6"><fmt:message key="btn.close"/></button>
			    	<button type="button" class="btn btn-md btn-danger mmth-btn col-md-7 col-sm-6" id="btnAllDelete"><fmt:message key="btn.alldel"/></button>
			    </div>

	    	</div>
		</div>
	</div>

</jsp:body>
</tag:page>