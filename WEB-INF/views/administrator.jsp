<%-- 
	#
	#	BIOTP Administrator page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" viewUriPrefix="/web/manager/administrator/rest" hasDetailsBox="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
//cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};

	if (cellNum == 3) {
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

// col1 : username
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	if (rowData.username == '${sessionScope["MMTH.ADMIN.USERNAME"]}') {
		return rowData.username + ' <span class="label mmth-bg-light"><fmt:message key="administrator.list.mine"/></span>' 
	}
	
	return rowData.username;
});

//col2 : adminType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.adminTypeDesc;
});

//col3 : disabled
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.disabled == '${DISABLED}') {
		return '<span class="text-danger" data-toggle="tooltip" title="'+rowData.disabledDesc+'"><i class="fa fa-ban"></i></span>';
	} else {
		return '<span class="text-success" data-toggle="tooltip" title="'+rowData.disabledDesc+'"><i class="fa fa-check-circle-o"></i></span>';
	}
});

//col4 : tsLastLogin
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsLastLogin;
});

//col5 : lastRemoteAddr
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.lastRemoteAddr;
});

// col4 : tsReg
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsReg;
});


//col5 : details
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	if (rowData.username == '${sessionScope["MMTH.ADMIN.USERNAME"]}') {
		return '<a href="#" class="btn btn-sm btn-primary mmth-btn" data-toggle="modal" data-target="#adminProfileModal"><fmt:message key="header.admin.profile"/></a>' 
	}
	return '<button type="button" class="btn btn-sm btn-primary mmth-btn btn-detailsBox" data-id="'+rowData.id+'"><fmt:message key="btn.details"/></button>';
});

function init(){
	
	$('#password, #passwordConfirm').keyup(function() {
		var value = $('#password').val();
		var confirm = $('#passwordConfirm').val();
		
		if (value == confirm) {
			removeFieldMessage('passwordConfirm');
			setFieldMessage('passwordConfirm', '<fmt:message key="administrator.validate.password.match"/>', true);
		} else {
			setFieldMessage('passwordConfirm', '<fmt:message key="administrator.validate.password.missmatch"/>');
		}
	});
	
	$('#change-password, #change-passwordConfirm').keyup(function() {
		var value = $('#change-password').val();
		var confirm = $('#change-passwordConfirm').val();
		
		if (value == confirm) {
			removeFieldMessage('change-passwordConfirm');
			setFieldMessage('change-passwordConfirm', '<fmt:message key="administrator.validate.password.match"/>', true);
		} else {
			setFieldMessage('change-passwordConfirm', '<fmt:message key="administrator.validate.password.missmatch"/>');
		}
	});
	
	$('#passwordChange').on('click', function(){
		$('#changePasswordModal').modal('show')
	});
	
	$('#changePasswordModal').on('show.bs.modal', function (e) {
		removeContextMessages(byId('changePasswordContext'));
		$('#change-password, #change-passwordConfirm').val('');
	});
	
	// 변경 버튼 이벤트
	$('#btn-changePassword').on('click', function(e){
		
		var pwd = $('#change-password').val();
		
		if (!pwd || pwd != $('#change-passwordConfirm').val()) {
			return false;
		}

		
		var obj = new Object();
		obj.id = $('#mmth-detailsContext #id').val();
		obj.passwordChanged = pwd;
		
		var caller = ajaxUtil(MMTHUtils.view.urlPrefix + '/forceChangePassword');
		
		caller.setContextFormId('changePasswordContext');
		caller.setData(obj);
		caller.setResponseDataHandler(function(resp){

			if (resp.hasMessages && resp.generalMessages) {
				handleGeneralMessage(resp.generalMessages);
			} else if (!resp.hasMessages) {
				BootstrapDialog.alert({
				    type: BootstrapDialog.TYPE_SUCCESS,
				    message: MMTHUtils.bundle['common.desc.changed'],
				    callback : function(){
				    	$('#changePasswordModal').modal('hide');
				    }
				});
			}

		});
		caller.execute();
		
	});	
	
}

function saveDetailsImp(object){
	
	if (object.id == '${NEW_ID}') {
		
		if (!object.password) {
			return false
		} 
		
		return object.password == byId('passwordConfirm').value;
	}
	
	return true;
}

function fillDetailsImp(details){
	
	var isNew = details.id == '${NEW_ID}';
	
	show('#ctx-username', true);
	show('#ctx-password', isNew);
	show('#ctx-passwordConfirm', isNew);
	show('#ctx-passwordChange', !isNew);
	show('#ctx-adminType', true);
	show('#ctx-disabled', !isNew);
	show('#ctx-tsLastLogin', !isNew);
	show('#ctx-lastRemoteAddr', !isNew);
	show('#ctx-tsReg', !isNew);
	show('#ctx-tsUpdated', !isNew);
	
	if (isNew) {
		$('#username').removeAttr('disabled');
	}
	
	$('#username').focus();
	
}
</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			<fmt:message key="administrator" /> <small><fmt:message key="administrator.desc" /></small>
		</h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.env"/></li>
			<li class="active"><i class="fa fa-black-tie"></i> <fmt:message key="nav.env.admin" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="row">
			<div class="col-md-12" id="statsWrap">
				<!-- Default box -->
				<tag:stats titleMsgKey="administrator.stats">
					<tag:statsItem statsKey="total" labelColor="default" msgKey="administrator.stats.total"/>
				</tag:stats><!-- ./ statsLocator -->
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
			
				<!-- Default box -->
				<div class="box mmth-box">
					<div class="box-header">
						<h3 class="box-title"><fmt:message key="administrator.list"/></h3>
						
						<table class="table mmth-pageSearch-field">

							<colgroup><col width="20%"><col width="50%"><col width="30%"></colgroup>
							
							<thead>
								<tr>
									<th><button class="btn btn-primary mmth-btn btn-sm btn-detailsBox" data-id="${NEW_ID}"><i class="fa fa-plus"></i>&nbsp;&nbsp;<fmt:message key="btn.add"/></button></th>
									<th></th>
									<th>
										<tag:singleSearchFilter fieldKey="username" msgKey="administrator.label.username" id="adminstrator-searchField"></tag:singleSearchFilter>
									</th>
								</tr>
							</thead>
						</table>

					</div>
					
					<tag:pagination noListMsgKey="administrator.noList" cols="8">
						<th><fmt:message key="common.idxLabel" /></th>
						<th><fmt:message key="administrator.label.username"/></th>
						<th>
							<tag:pageFilter key="adminType" wrappedKey="administrator.list.adminType" id="administrator-adminType"><c:forEach items="${AdministratorTypes}" var="type">
								<tag:filterItem wrappedKey="administrator.list.adminType" value="${type.key}" msgKey="${type.value}"></tag:filterItem>
								</c:forEach>
							</tag:pageFilter>
						</th>
						<th><tag:disabledFilter wrappedKey="administrator.list.disabled" id="administrator-disabled"/></th>
						<th><fmt:message key="administrator.label.tsLastLogin" /></th>
						<th><fmt:message key="administrator.label.lastRemoteAddr" /></th>
						<th><fmt:message key="common.regDateTime" /></th>
						<th>&nbsp;</th>
					</tag:pagination>
		
				</div>
				<!-- /.box -->

			</div>
		</div>
		
		
		
		<div class="row">
			<div class="col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
				<tag:detailsBox titleKeyDetails="administrator.details" titleKeyNew="administrator.details.new" useForm="true">
					<!-- id -->
					<tag:formItem formField="true" valueKey="id" hidden="true"/>
					
					<!-- username -->
	              	<tag:formItem formField="true" valueKey="username" labelKey="administrator.label.username" disabled="true" maxLength="100" placeholderKey="administrator.label.username.placeholer"/>
	              	
	              	<!-- password  -->
	              	<tag:formItem formField="true" valueKey="password" password="true" labelKey="administrator.label.password" maxLength="100" placeholderKey="administrator.label.password.placeholer"/>
	              	
	              	<!-- password confirm -->
	              	<tag:formItem formField="false" valueKey="passwordConfirm" password="true" labelKey="administrator.label.password.confirm" maxLength="100"/>
	              	
	              	<!-- password change -->
	              	<tag:formItem formField="false" valueKey="passwordChange" buttonKey="administrator.label.password.change" labelKey="administrator.label.password.change" tooltipKey="administrator.label.password.change.desc"/>
	              	
	              	<!-- adminType  -->
	              	<tag:formItem formField="true" valueKey="adminType" selectOptions="${AdministratorTypes}" labelKey="administrator.label.adminType" tooltipKey="administrator.label.adminType.desc"/>
	              	
	              	<!-- disabled  -->
	              	<tag:formItem formField="true" valueKey="disabled" selectOptions="${DisabledStatus}" labelKey="administrator.label.disabled" tooltipKey="administrator.label.disabled.desc"/>

					<!-- tsLastLogin -->
					<tag:formItem formField="false" valueKey="tsLastLogin" labelKey="administrator.label.tsLastLogin" readonly="true"/>
					
					<!-- lastRemoteAddr -->
					<tag:formItem formField="false" valueKey="lastRemoteAddr" labelKey="administrator.label.lastRemoteAddr" readonly="true"/>
					
					<!-- tsReg -->
					<tag:formItem formField="false" valueKey="tsReg" labelKey="common.regDateTime" readonly="true"/>
					
					<!-- tsUpdate -->
					<tag:formItem formField="false" valueKey="tsUpdated" labelKey="common.updateDateTime" readonly="true"/>
				</tag:detailsBox>
			</div>
		</div>
		
		
	</section>
	<!-- /.content -->
	
	<!-- Modal ChangePwd -->
	<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel">
		<div class="modal-dialog modal-lg" role="document">
	    	<div class="modal-content">
	    
	      		<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title" id="changePasswordModalLabel"><fmt:message key="administrator.changePassword" /></h4>
	      		</div>
		  		<form class="form-horizontal" id="changePasswordContext">
	      		<div class="modal-body"><br/>
	      			<div class="col-md-offset-1 col-md-10" >
	      				<div class="alert alert-warning"><h4><i class="icon fa fa-warning"></i> <fmt:message key="common.alert"/></h4><fmt:message key="administrator.label.password.change.desc"/></div>
	      			</div><br/><br/><br/>
					<!-- password  -->
	              	<tag:formItem formField="false" valueKey="change-password" password="true" labelKey="administrator.changePassword.password" maxLength="100" placeholderKey="administrator.label.password.placeholer"/>
	              	
	              	<!-- password confirm -->
	              	<tag:formItem formField="false" valueKey="change-passwordConfirm" password="true" labelKey="administrator.label.password.confirm" maxLength="100"/>
			
	      		</div>

		      	<div class="modal-footer"  id="changePasswordBtnContext"> <!-- data-dismiss="modal" -->
		      		<div  class="col-md-offset-1 col-md-10">
		      		<button type="button" class="btn btn-default pull-left" id="btnClose" data-dismiss="modal"><fmt:message key="btn.close"/></button>
		        	<button type="button" class="btn btn-primary" id="btn-changePassword"><i class="fa fa-save"></i> <fmt:message key="btn.update"/></button>
		        	</div>
		      	</div>
				</form>
	    	</div>
		</div>
	</div>


</jsp:body>
</tag:page>