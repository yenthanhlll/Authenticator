<%-- 
	#
	#	system settings page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init">
<jsp:attribute name="styles">
<style type="text/css">
.borderless {
	border: 0 !important;
}
#btn-doPurgeData {
	width: 100%;
}

</style>
</jsp:attribute>
<jsp:attribute name="javascripts">
<script type="text/javascript">
var urlPrefix = '${context_path}/web/manager/systemSettings/rest/';
var initUrl = urlPrefix + 'init';
var getUrl = urlPrefix + 'get/';
var saveUrl = urlPrefix + 'save/';
var cache_countryCodes;
function init() {
	$('#sysSettingsType > li:FIRST-CHILD').addClass('active');
	$('#sysSettings-APPLICATION').show();
	settingsDetailsBox();
	configList();
	restInit();
	configPurgeData();
}

function restInit(){
	// init url을 호출하여 페이지 리스트와 수치 데이터를 조회한다.
	var caller = ajaxUtil(initUrl);
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			setContextFieldData('APPLICATION', resp.contextData, resp.data)
		}
	});
	caller.execute();	
}

function restGet(clazzifySettings){
	var caller = ajaxUtil(getUrl + clazzifySettings);
	
	removeContextMessages();
	
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			
			setContextFieldData(clazzifySettings, resp.contextData, resp.data);
			
			$('.mmth-details-box').boxWidget('collapse');
			$('#sysSettings-' + clazzifySettings).boxWidget('expand');
		}
	});
	caller.execute();	
}

function restSave(clazzifySettings, data){
	var caller = ajaxUtil(saveUrl + clazzifySettings);
	caller.setContextFormId(clazzifySettings);
	caller.setData(data);
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			setContextFieldData(clazzifySettings, resp.contextData, resp.data);

			BootstrapDialog.alert({
			    type: BootstrapDialog.TYPE_SUCCESS,
			    message: MMTHUtils.bundle['common.desc.saved']
			});
		}
	});
	caller.execute();	
}


function saveSettingValue(clazzifySettings) {
	
	var data = {};
	
	$('#'+clazzifySettings).find('.formField').each(function(){
		data[this.name] = this.value;
	});
	
	if ('APPLICATION' == clazzifySettings) {
		
		var arr = getListviewContents('#listview_ipWhitelist');
		
		if (arr.length < 1) {
			BootstrapDialog.alert({
			    type: BootstrapDialog.TYPE_WARNING,
			    message: '<fmt:message key="validate.required.arg"><fmt:param><fmt:message key="systemSettings.application.ipWhitelist"/></fmt:param></fmt:message>'
			});
			
			return;
		}
		
		data.application_ipWhitelist = arr.join(',');

		restSave(clazzifySettings, data);

	} else if ('INTEGRATE_SETTINGS' == clazzifySettings){
		
		if (data.integrate_authLifetimePeriodType) {
			data.integrate_authLifetimePeriods = $('#integrate_authLifetimePeriods_'+data.integrate_authLifetimePeriodType).val();
		}
		
		if (data.integrate_checkAuthResultLifetimePeriodType) {
			data.integrate_checkAuthResultLifetimePeriods = $('#integrate_checkAuthResultLifetimePeriods_'+data.integrate_checkAuthResultLifetimePeriodType).val();
		}
		
		if (data.integrate_issueCodeLifetimePeriodType) {
			data.integrate_issueCodeLifetimePeriods = $('#integrate_issueCodeLifetimePeriods_'+data.integrate_issueCodeLifetimePeriodType).val();
		}
		
		restSave(clazzifySettings, data);

	} else if ('FIDO_SETTINGS' == clazzifySettings){
		if (data.fido_issuranceExpiredEnabled == 'ENABLED') {
			
			if (data.fido_issuranceExpiredPeriodType) {
				data.fido_issuranceExpiredPeriods = $('#fido_issuranceExpiredPeriods_'+data.fido_issuranceExpiredPeriodType).val();
			}
		} else {
			delete data.fido_issuranceExpiredPeriodType;
		}
		
		restSave(clazzifySettings, data);
	} else if ('BIOTP_SETTINGS' == clazzifySettings) {
		if (data.biotp_issuranceExpiredEnabled == 'ENABLED') {
			
			if (data.fido_issuranceExpiredPeriodType) {
				data.biotp_issuranceExpiredPeriods = $('#biotp_issuranceExpiredPeriods_'+data.fido_issuranceExpiredPeriodType).val();
			}
		} else {
			delete data.biotp_issuranceExpiredPeriodType;
		}
		
		restSave(clazzifySettings, data);
		
	} else if ('HWOTP_SETTINGS' == clazzifySettings) {
// 		if (data.biotp_issuranceExpiredEnabled == 'ENABLED') {
			
// 			if (data.fido_issuranceExpiredPeriodType) {
// 				data.biotp_issuranceExpiredPeriods = $('#biotp_issuranceExpiredPeriods_'+data.fido_issuranceExpiredPeriodType).val();
// 			}
// 		} else {
// 			delete data.biotp_issuranceExpiredPeriodType;
// 		}
		console.log(data);
		restSave(clazzifySettings, data);
		
	} else if ('SAOTP_SETTINGS' == clazzifySettings){
		if (data.saotp_issuranceExpiredEnabled == 'ENABLED') {
			
			if (data.saotp_issuranceExpiredPeriodType) {
				data.saotp_issuranceExpiredPeriods = $('#saotp_issuranceExpiredPeriods_'+data.saotp_issuranceExpiredPeriodType).val();
			}
		} else {
			delete data.saotp_issuranceExpiredPeriodType;
		}

		restSave(clazzifySettings, data);
		
	} else if ('EXTERNAL_API_SETTINGS' == clazzifySettings){
		
		restSave(clazzifySettings, data);
	} else if ('ADVANCED_SETTINGS' == clazzifySettings){
		
		if (data.advanced_fidoEnabled != 'ENABLED') {
			delete data.advanced_payloadParserFido;
		}
		
		if (data.advanced_biotpEnabled != 'ENABLED') {
			delete data.advanced_payloadParserBiotp;
		}
		
		if (data.advanced_saotpEnabled != 'ENABLED') {
			delete data.advanced_payloadParserSaotp;
			delete data.advanced_otpKeybased;
		}
		
		if (data.advanced_purgeDataEnabled != 'ENABLED') {
			delete data.advanced_purgeCronExpression;
			delete data.advanced_purgeDataLifecyle;
		}
		
		
		if (data.advanced_saotpEnabled != 'ENABLED') {
			delete data.advanced_payloadParserSaotp;
			delete data.advanced_otpKeybased;
		}

		if (data.advanced_publisherEnabled == 'ENABLED') {

			var arr = getListviewContents('#listview_publisherTarget');
			
			if (arr.length < 1) {
				BootstrapDialog.alert({
				    type: BootstrapDialog.TYPE_WARNING,
				    message: '<fmt:message key="validate.required.arg"><fmt:param><fmt:message key="systemSettings.advanced.publisherTarget"/></fmt:param></fmt:message>'
				});
				
				return;
			}
			
			data.advanced_publisherTarget = arr.join(',');
		}
	
		restSave(clazzifySettings, data);
	}
}

function setContextFieldData(clazzifySettings, contextData, commonData) {
	
	$('#'+clazzifySettings).find('.form-tp').hide();
	$('#'+clazzifySettings).find('.form-changeTp').hide();
	
	
	if ('APPLICATION' == clazzifySettings) {
		if (contextData.application_ipWhitelist) {
			createListview('#listview_ipWhitelist', contextData.application_ipWhitelist.split(','));
		}
	} else if ('INTEGRATE_SETTINGS' == clazzifySettings){
		// 
		$('#ctx-integrate_authLifetimePeriodType').show();
		$('#integrate_authLifetimePeriods_' + contextData.integrate_authLifetimePeriodType).val(contextData.integrate_authLifetimePeriods);
		$('#ctx-integrate_authLifetimePeriods_' + contextData.integrate_authLifetimePeriodType).show();
		
		$('#ctx-integrate_checkAuthResultLifetimePeriodType').show();
		$('#integrate_checkAuthResultLifetimePeriods_' + contextData.integrate_checkAuthResultLifetimePeriodType).val(contextData.integrate_checkAuthResultLifetimePeriods);
		$('#ctx-integrate_checkAuthResultLifetimePeriods_' + contextData.integrate_checkAuthResultLifetimePeriodType).show();
		
		$('#ctx-integrate_issueCodeLifetimePeriodType').show();
		$('#integrate_issueCodeLifetimePeriods_' + contextData.integrate_issueCodeLifetimePeriodType).val(contextData.integrate_issueCodeLifetimePeriods);
		$('#ctx-integrate_issueCodeLifetimePeriods_' + contextData.integrate_issueCodeLifetimePeriodType).show();
		
		
	} else if ('FIDO_SETTINGS' == clazzifySettings){
		
		$('#fido_issuranceExpiredPeriods_' + contextData.fido_issuranceExpiredPeriodType).val(contextData.fido_issuranceExpiredPeriods);
		
		if (contextData.fido_issuranceExpiredEnabled == 'ENABLED') {
			$('#ctx-fido_issuranceExpiredPeriodType').show();
			$('#ctx-fido_issuranceExpiredPeriods_' + contextData.fido_issuranceExpiredPeriodType).show();
		} 
		
	} else if ('BIOTP_SETTINGS' == clazzifySettings){
		
		$('#biotp_issuranceExpiredPeriods_' + contextData.biotp_issuranceExpiredPeriodType).val(contextData.biotp_issuranceExpiredPeriods);
		
		if (contextData.biotp_issuranceExpiredEnabled == 'ENABLED') {
			$('#ctx-biotp_issuranceExpiredPeriodType').show();
			$('#ctx-biotp_issuranceExpiredPeriods_' + contextData.biotp_issuranceExpiredPeriodType).show();
		} 
		
	} else if ('HWOTP_SETTINGS' == clazzifySettings){
		
// 		$('#hwiotp_issuranceExpiredPeriods_' + contextData.hwotp_issuranceExpiredPeriodType).val(contextData.hwotp_issuranceExpiredPeriods);		
// 		$('#ctx-hwotp_issuranceExpiredPeriodType').show();
// 		$('#ctx-hwotp_issuranceExpiredPeriods_' + contextData.hwotp_issuranceExpiredPeriodType).show();
		
	} else if ('SAOTP_SETTINGS' == clazzifySettings){
		$('#saotp_issuranceExpiredPeriods_' + contextData.saotp_issuranceExpiredPeriodType).val(contextData.saotp_issuranceExpiredPeriods);
		
		if (contextData.saotp_issuranceExpiredEnabled == 'ENABLED') {
			$('#ctx-saotp_issuranceExpiredPeriodType').show();
			$('#ctx-saotp_issuranceExpiredPeriods_' + contextData.saotp_issuranceExpiredPeriodType).show();
		} 

	} else if ('ADVANCED_SETTINGS' == clazzifySettings){
		$('.service-hidden').hide();
		var cnt = 0;
		if (contextData.advanced_fidoEnabled == 'ENABLED') {
			$('#ctx-advanced_payloadParserFido').show();
			cnt++;
		}
		if (contextData.advanced_biotpEnabled == 'ENABLED') {
			$('#ctx-advanced_payloadParserBiotp').show();
			cnt++;
		}
		if (contextData.advanced_saotpEnabled == 'ENABLED') {
			$('#ctx-advanced_payloadParserSaotp').show();
			$('#ctx-advanced_otpKeybased').show();
			
			cnt++;
		}
		
		if (cnt > 1) {
			$('#ctx-advanced_multiAuthMethodsEnabled').show();	
		}
		
		if (contextData.advanced_purgeDataEnabled == 'ENABLED') {
			$('#ctx-advanced_purgeCronExpression').show();
			$('#ctx-advanced_purgeDataLifecyle').show();
		}
		
		if (contextData.advanced_publisherEnabled == 'ENABLED') {
			$('#ctx-advanced_publisherTarget').show();
		}
		
		if (contextData.advanced_publisherTarget) {
			createListview('#listview_publisherTarget', contextData.advanced_publisherTarget.split(','));
		}
		
	} else if ('COUNTRYCODE_SETTINGS' == clazzifySettings){
		cache_countryCodes = commonData.countryCodes_all;
		var selectedValue = commonData.customKeys_countryCode;
		relocationCountryCodes(selectedValue);

	} else if ('EXTERNAL_API_SETTINGS' == clazzifySettings){
		$('.service-hidden').hide();
		
		if (contextData.externalApi_serviceType == 'GPTWR') {
			$('#ctx-externalApi_serviceSharedKey').show();
			$('#ctx-externalApi_serivceAddress').show();
		} else if (contextData.externalApi_serviceType == 'MIRAE_ASSET_VT') {
			$('#ctx-externalApi_qrCodeDimension').show();
		}
		
	}

}


function configList(){
	$('#sysSettingsType > li > a').on('click', function(){
		$('#sysSettingsType > li.active').removeClass('active');
		$(this.offsetParent).addClass('active');
		restGet($(this).data('value'));
	});
}

function settingsDetailsBox(){

	$('.mmth-details-box').on('expanded.boxwidget', function(params){
		$(params.currentTarget).show();
		location.hash = params.currentTarget.id; 
	}).on('collapsed.boxwidget', function(params){
		$(params.currentTarget).hide();
		location.hash = '#mmth-settingContents';
	});
	
	$('.mmth-details-box').find('.form-control[data-readonly]').attr('readonly', 'readonly');
	$('.mmth-details-box').find('.form-control[data-disabled]').attr('disabled', 'disabled');
	$('.mmth-details-box').find('.form-tp').hide();
	$('.mmth-details-box').find('.form-tp select > option:FIRST-CHILD').attr('selected', 'selected');
	
	$('.form-changeTp select').on('change', function(){
		var target = $(this).data('target');
		$('div[id^="ctx-'+ target +'"]').hide();
		$('#ctx-' + target + this.value).show();
	});
	
	$('.issuranceExpiredEnabled select').on('change', function(){
		var target = $(this).data('target');
		var $target = $('#'+ target);
		var subTarget = $target.data('target');

		if (this.value == 'ENABLED') {			
			var value = $target.val();
			$('#ctx-'+ target).show();
			$('div[id^="ctx-' +subTarget+'"]').hide();
			$('#ctx-' + subTarget + value).show();
		} else {
			$('#ctx-'+ target).hide();
			$('div[id^="ctx-' +subTarget+'"]').hide();
		}
		
	});
	
	$('.serviceEnabled select').on('change', function(){
		var target = $(this).data('target');
		var $target = $('#ctx-' + target);
		if (this.value == 'ENABLED') {			
			$target.show();
		} else {
			$target.hide();
		}
		
	});
	
	
	$('#advanced_purgeDataEnabled').on('change', function(){
		if (this.value == 'ENABLED') {			
			$('#ctx-advanced_purgeCronExpression').show();
			$('#ctx-advanced_purgeDataLifecyle').show();
			
		} else {
			$('#ctx-advanced_purgeCronExpression').hide();
			$('#ctx-advanced_purgeDataLifecyle').hide();
		}
		
	});
	
	$('#externalApi_serviceType').on('change', function(){
		if (this.value == 'GPTWR') {			
			$('#ctx-externalApi_serviceSharedKey').show();
			$('#ctx-externalApi_serivceAddress').show();
			
		} else {
			$('#ctx-externalApi_serviceSharedKey').hide();
			$('#ctx-externalApi_serivceAddress').hide();
		}
		
		if (this.value == 'MIRAE_ASSET_VT') {
			$('#ctx-externalApi_qrCodeDimension').show();
		} else {
			$('#ctx-externalApi_qrCodeDimension').hide();
		}
		
	});
	
	$('form .box-footer button').on('click', function(){
		var form = $(this).parents('form');
		saveSettingValue(form.attr('id'));
	});
	
	$('#btn-COUNTRYCODE_SETTINGS').on('click', function(){
		saveCountryCodes();
	});
	
}

function addCountryCodes(){

	var from = elmt('country_code_all');	
	var selectedValues = from.val();
	
	if (selectedValues.length < 1) {
		BootstrapDialog.alert('<fmt:message key="systemSettings.countryCode.noSelect"/>');
		return;
	}
	
	var target = elmt('country_code_registered');
	var targetElement = target.get();
	
	var children = targetElement.children;
	
	for (var i = 0, len = children.length; i < len; i++) {
		selectedValues.push(children[i].value);
	}

	relocationCountryCodes(selectedValues);
}

function removeCountryCodes() {
	var target = elmt('country_code_registered');
	var children = target.get().children;
	
	var selectedValues = [];
	for (var i = 0, len = children.length; i < len; i++) {
		
		if (!children[i].selected)
			selectedValues.push(children[i].value);
	}
	
	relocationCountryCodes(selectedValues);
	
}

function relocationCountryCodes(selectedValues) {
	var selectAll = elmt('country_code_all');
	var selectReg = elmt('country_code_registered');
	
	// 모든 element 지우기 
	// 
	selectAll.removeChildren();
	selectReg.removeChildren();
	
	for (var i = 0, len = cache_countryCodes.length; i < len; i++){
		var include = false;
		var cc = cache_countryCodes[i];

		for (var j = 0, jlen = selectedValues.length; j < jlen; j++){
			if (cc.key == selectedValues[j]) {
				include = true;
				break;
			}
		}
		
		if (include) {
			selectReg.appendAndGetChild('option', {value : cc.key}).setValue('(' + cc.key +') ' + cc.value);
		} else {
			selectAll.appendAndGetChild('option', {value : cc.key}).setValue('(' + cc.key +') ' + cc.value);
		}
		
	}
}

function saveCountryCodes() {
	var target = elmt('country_code_registered');
	var children = target.get().children;
	
	var selectedValues = [];
	for (var i = 0, len = children.length; i < len; i++) {
		selectedValues.push(children[i].value);
	}
	
	if (selectedValues.length < 1) {
		BootstrapDialog.alert('<fmt:message key="systemSettings.countryCode.noSelect"/>');
		return;
	}
	
	
	var data = { 'customKeys_countryCode' : selectedValues.join(',')};
	
	restSave('COUNTRYCODE_SETTINGS', data);
}



function createListview(target, list){
	
	$(target).empty();
	
	for (var i = 0, len = list.length; i < len; i++) {
		addListviewElement(target, list[i]);
	}
	
}

function addListviewElement(targetElement, contents){
	
	var htmlTxt = '<li class="list-group-item"><span class="listview_item">' + contents +'</span><span class="pull-right"><button class="btn btn-xs btn-info mmth-btn radius-10" onclick="removeListview(this)"><i class="fa fa-minus"></i></button></span></li>';
	$(targetElement).append(htmlTxt);
}

function addListview(contentElement, targetElement){
	var contents = $(contentElement).val();
	
	var regexPattern = new RegExp($(contentElement).data('regex'));
	   
	if (!regexPattern.test(contents)) {
		BootstrapDialog.alert({
		    type: BootstrapDialog.TYPE_WARNING,
		    message: MMTHUtils.bundle['common.desc.invalid']
		});
		
		return;
	}

	contents = contents.trim();
	
	var arr = getListviewContents(targetElement);
	
	for (var it = 0; it < arr.length; it++) {
		
		if (arr[it] == contents) {
			BootstrapDialog.alert({
			    type: BootstrapDialog.TYPE_WARNING,
			    message: MMTHUtils.bundle['common.desc.duplicate']
			});
			
			return;
		}
	}

	addListviewElement(targetElement, contents);
	$(contentElement).val('');
}

function removeListview(element){
	$(element).offsetParent('li').remove();
}

function getListviewContents(targetElement){
	
	var arr = [];
	$(targetElement).find('.listview_item').each(function(){
		arr.push(this.innerText);
	});
	
	return arr;
}

function getPurgeData(){

	cleanPurgeDataStat();

	var caller = ajaxUtil(urlPrefix + 'dataPurgeStats');
	caller.setData({months : $('#purgePeriodMonths').val()});

	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			setPurgeDataStat(resp.data);
		}
	});
	caller.execute();
}

function doPurgeData(){

	$('#doPurgeData-panel').hide();
	$('#doPurgeData-warn').show();
	
	var caller = ajaxUtil(urlPrefix + 'doDataPurge');
	caller.setData({months :  $('#purgePeriodMonths').val()});
	caller.setResponseDataHandler(function(resp){
		cleanPurgeDataStat();
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			setPurgeDataStat(resp.data);			

			BootstrapDialog.alert({
			    type: BootstrapDialog.TYPE_SUCCESS,
			    message: MMTHUtils.bundle['common.desc.deleted']
			});
		}
		
	});
	caller.execute();
}

function setPurgeDataStat(data){
	console.log(data);
	
	var purgeDataContents = data.purgeDataContents;
	
	if (purgeDataContents.totalRows || purgeDataContents.totalRows == 0) {
		$('#purgeQuery-result-data').append('<dt><fmt:message key="systemSettings.application.serviceLogPurge.totalRows"/></dt><dl class="text-right">'+purgeDataContents.totalRows.format()+'</dl>');
	}

	if (purgeDataContents.totalSize) {
		$('#purgeQuery-result-data').append('<dt><fmt:message key="systemSettings.application.serviceLogPurge.totalSize"/></dt><dl class="text-right">'+(purgeDataContents.totalSize/1024).round(2).format()+'</dl>');
	}
	
	if (purgeDataContents.targetRows) {
		$('#purgeQuery-result-data').append('<dt><fmt:message key="systemSettings.application.serviceLogPurge.targetRows"><fmt:param>' +(purgeDataContents.tsBase)+ '</fmt:param></fmt:message></dt><dl class="text-right text-orange">'+purgeDataContents.targetRows.format()+'</dl>');
	}	
	
	if (purgeDataContents.targetSize) {
		$('#purgeQuery-result-data').append('<dt><fmt:message key="systemSettings.application.serviceLogPurge.targetSize"/></dt><dl class="text-right text-orange">'+(purgeDataContents.targetSize/1024).round(2).format()+'</dl>');
	}
	

	if (purgeDataContents.targetRows) {
		$('#doPurgeData-panel').show();	
	} else {
		$('#doPurgeData-none').show();
	}
	
	$('#purgeQuery-result').show();
}

function cleanPurgeDataStat(){
	$('#purgeQuery-result').hide();
	$('#doPurgeData-panel').hide();
	$('#doPurgeData-warn').hide();
	$('#doPurgeData-none').hide();
	$('#purgeQuery-result-data').html('');
	
}

function configPurgeData(){
	$('#purgePeriodMonths').on('change', function(){
		cleanPurgeDataStat();
	});
	
	$('#btn-getPurgeData').on('click', getPurgeData);
	$('#btn-doPurgeData').on('click', doPurgeData);
}



</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1><fmt:message key="systemSettings"/> <small><fmt:message key="systemSettings.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.env"/></li>
			<li class="active"><i class="fa fa-cogs"></i> <fmt:message key="nav.env.settings"/> </li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="row">
		
        <div class="col-md-3">

        	<div class="box box-solid">
        		<div class="box-header with-border">
	             <h3 class="box-title"><fmt:message key="systemSettings"/></h3>
	
	             <div class="box-tools">
	               <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	             </div>
	            </div>
	            <div class="box-body no-padding">
	            	<ul class="nav nav-tab nav-pills nav-stacked mmth-nav-light" id="sysSettingsType">
	            		<li><a href="#mmth-settingContents" data-value="APPLICATION" data-target="#sysSettings-APPLICATION"><fmt:message key="systemSettings.application"/></a></li>
	            		<li><a href="#mmth-settingContents" data-value="INTEGRATE_SETTINGS" data-target="#sysSettings-INTEGRATE_SETTINGS"><fmt:message key="systemSettings.biotp"/></a></li>
	            		<c:if test="${AUTH_METHOD_FIDO_ENABLED and WEB_API_POLICY != 'MIRAE_ASSET_VT' }">
	            		<li><a href="#mmth-settingContents" data-value="FIDO_SETTINGS" data-target="#sysSettings-FIDO_SETTINGS"><fmt:message key="systemSettings.fido"/></a></li></c:if>
	            		<c:if test="${AUTH_METHOD_BIOTP_ENABLED and WEB_API_POLICY != 'MIRAE_ASSET_VT' } ">
	            		<li><a href="#mmth-settingContents" data-value="BIOTP_SETTINGS" data-target="#sysSettings-BIOTP_SETTINGS"><fmt:message key="systemSettings.biotp"/></a></li></c:if>
	            		<li><a href="#mmth-settingContents" data-value="HWOTP_SETTINGS" data-target="#sysSettings-HWOTP_SETTINGS"><fmt:message key="systemSettings.hwotp"/></a></li>
	            		<c:if test="${AUTH_METHOD_SAOTP_ENABLED and WEB_API_POLICY == 'NONE' }">
	            		<li><a href="#mmth-settingContents" data-value="SAOTP_SETTINGS" data-target="#sysSettings-SAOTP_SETTINGS"><fmt:message key="systemSettings.saotp"/></a></li></c:if>
	            		<c:if test="${WEB_API_POLICY != 'MIRAE_ASSET_VT' }">
	            		<li><a href="#mmth-settingContents" data-value="EXTERNAL_API_SETTINGS" data-target="#sysSettings-EXTERNAL_API_SETTINGS"><fmt:message key="systemSettings.externalApi"/></a></li></c:if>
	            		<c:if test="${WEB_API_POLICY != 'MIRAE_ASSET_VT' }">
	            		<sec:authorize access="hasAnyAuthority('SUPER', 'DEV')">
	            		<li><a href="#mmth-settingContents" data-value="ADVANCED_SETTINGS" data-target="#sysSettings-ADVANCED_SETTINGS"><fmt:message key="systemSettings.advanced"/></a></li></sec:authorize></c:if>
	            		<c:if test="${WEB_API_POLICY eq 'GLOBAL_WIBEE' }">
	            		<li><a href="#mmth-settingContents" data-value="COUNTRYCODE_SETTINGS" data-target="#sysSettings-COUNTRYCODE_SETTINGS"><fmt:message key="systemSettings.countrycode"/></a></li>
	            		</c:if>
	            	</ul>
	            </div>
            <!-- /.box-body -->
        	</div>
        </div>
        <!-- /.col -->

		<div class="col-md-6" id="mmth-settingContents">
       		<tag:detailsBox titleKeyDetails="systemSettings.application" detailBoxId="sysSettings-APPLICATION" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-APPLICATION" detailBoxFormId="APPLICATION">
       			<tag:formItem formField="false"  readonly="true" valueKey="application_version" labelKey="systemSettings.application.version"/>
       			<tag:formItem formField="false"  readonly="true" valueKey="application_databaseSchemaVersion" labelKey="systemSettings.application.databaseSchemaVersion" tooltipKey="systemSettings.application.databaseSchemaVersion.desc"/>
       			<tag:formItem formField="false"  readonly="true" valueKey="application_since" labelKey="systemSettings.application.since" tooltipKey="systemSettings.application.since.desc"/>
       			<tag:formItem formField="false"  readonly="true" valueKey="application_currentLocalAddress" labelKey="systemSettings.application.currentLocalAddress" tooltipKey="systemSettings.application.currentLocalAddress.desc"/>
       			<tag:formItem formField="true" valueKey="application_instanceName" labelKey="systemSettings.application.instanceName" tooltipKey="systemSettings.application.instanceName.desc"/>
       			<tag:formItem formField="true" selectOptionsRaw="${supportedLanguages}" valueKey="application_sysLanguage" labelKey="systemSettings.application.sysLanguage" tooltipKey="systemSettings.application.sysLanguage.desc"/>
       			<tag:formItem formField="true" selectOptionMin="10" selectOptionMax="60" selectOptionStep="10" selectOptionWrapKey="systemSettings.tp.MINUTE.wrap" valueKey="application_sessionTimeout" labelKey="systemSettings.application.sessionTimeout" tooltipKey="systemSettings.application.sessionTimeout.desc"/>
       			<tag:formItem formField="false" hasBodyContents="true" valueKey="application_ipWhitelist" labelKey="systemSettings.application.ipWhitelist" tooltipKey="systemSettings.application.ipWhitelist.desc">
       				<div class="panel panel-default">
					  <div class="panel-body">
					   	<div class="input-group">
						     <input type="text" class="form-control" placeholder="192.168.0.*" id="add_ipWhitelist" data-regex="^(\*|(25[0-5]|2[0-4][0-9]|1?[0-9][0-9]{1,2}))((\.(\*|(25[0-5]|2[0-4][0-9]|1?[0-9]{1,2}))){2})(\.(\*|(25[0-5]|2[0-4][0-9]|1?[0-9]{1,2})|(25[0-5]|2[0-4][0-9]|1?[0-9]{1,2})\-(25[0-5]|2[0-4][0-9]|1?[0-9]{1,2})))$">
						     <span class="input-group-btn">
						       <button class="btn btn-primary mmth-btn" type="button" onclick="addListview($(this).data('form'), $(this).data('listview'))" data-form="#add_ipWhitelist" data-listview="#listview_ipWhitelist" ><span class="fa fa-plus"></span></button>
						     </span>
						</div>
					  </div>
					</div>

       				<ul class="list-group" id="listview_ipWhitelist"></ul>
       			</tag:formItem>
       			<%--<c:if test="${WEB_API_POLICY == 'GLOBAL_WIBEE' }"> --%>
       			<tag:formItem formField="false" hasBodyContents="true" valueKey="application_serviceLogPurge" labelKey="systemSettings.application.serviceLogPurge" tooltipKey="systemSettings.application.serviceLogPurge.desc">
       				<div class="panel panel-default">
						<div class="panel-body">
							<div class="input-group">
								<select class="form-control" id="purgePeriodMonths">
									<option value="3" selected="selected"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>3</fmt:param></fmt:message></option>
									<option value="6"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>6</fmt:param></fmt:message></option>
									<option value="12"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>12</fmt:param></fmt:message></option>
									<option value="18"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>18</fmt:param></fmt:message></option>
									<option value="24"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>24</fmt:param></fmt:message></option>
									<option value="30"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>30</fmt:param></fmt:message></option>
									<option value="36"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>36</fmt:param></fmt:message></option>
									<option value="48"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>48</fmt:param></fmt:message></option>
									<option value="60"><fmt:message key="systemSettings.application.serviceLogPurge.olderThan"><fmt:param>60</fmt:param></fmt:message></option>
								</select>
								<span class="input-group-btn">
									<button class="btn btn-primary mmth-btn" type="button" id="btn-getPurgeData"><fmt:message key="btn.query"/></button>
								</span>
							</div>
       					</div>
       					<div class="panel-footer"  id="purgeQuery-result" style="display: none;">
       						<dl id="purgeQuery-result-data"></dl>
              				<div class="input-group" id="doPurgeData-panel" style="display: none;">
              					<span class="input-group-addon text-red borderless"><fmt:message key="systemSettings.application.serviceLogPurge.confirm"/></span>
              					<span class="input-group-btn">
									<button class="btn btn-danger" type="button" id="btn-doPurgeData"><fmt:message key="btn.execute"/></button>
								</span>
              				</div>
              				
              				<div class="input-group"  id="doPurgeData-warn" style="display: none;">
              					<span class="input-group-addon text-orange borderless"><fmt:message key="systemSettings.application.serviceLogPurge.warn"/></span>
              				</div>		
              					
              				<div class="input-group"  id="doPurgeData-none" style="display: none;">
              					<span class="input-group-addon text-blue borderless"><fmt:message key="systemSettings.application.serviceLogPurge.none"/></span>
              				</div>					
       					</div>
       				</div>
       			</tag:formItem><%--</c:if> --%>
       		</tag:detailsBox>
       		
       		<tag:detailsBox titleKeyDetails="systemSettings.biotp" detailBoxId="sysSettings-INTEGRATE_SETTINGS" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-INTEGRATE_SETTINGS" detailBoxFormId="INTEGRATE_SETTINGS">
       			<tag:formItem formField="true"  clazzName="form-changeTp" selectChangedTarget="integrate_authLifetimePeriods_" selectOptions="${lifetimePeriodTypes}" valueKey="integrate_authLifetimePeriodType" labelKey="systemSettings.integrate.authLifetimePeriodType" tooltipKey="systemSettings.integrate.authLifetimePeriodType"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="20" selectOptionMax="100" selectOptionStep="5" selectOptionWrapKey="systemSettings.tp.SECOND.wrap" valueKey="integrate_authLifetimePeriods_SECOND" labelKey="systemSettings.integrate.authLifetimePeriods" tooltipKey="systemSettings.integrate.authLifetimePeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="60" selectOptionWrapKey="systemSettings.tp.MINUTE.wrap" valueKey="integrate_authLifetimePeriods_MINUTE" labelKey="systemSettings.integrate.authLifetimePeriods" tooltipKey="systemSettings.integrate.authLifetimePeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="24" selectOptionWrapKey="systemSettings.tp.HOUR.wrap" valueKey="integrate_authLifetimePeriods_HOUR" labelKey="systemSettings.integrate.authLifetimePeriods" tooltipKey="systemSettings.integrate.authLifetimePeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="1" selectOptionWrapKey="systemSettings.tp.DAY.wrap" valueKey="integrate_authLifetimePeriods_DAY" labelKey="systemSettings.integrate.authLifetimePeriods" tooltipKey="systemSettings.integrate.authLifetimePeriods.desc"/>
       			<tag:formItem formField="false" disabled="true" readonly="true" selectOptions="${AuthManagementScope}" valueKey="integrate_authMgmtScope" labelKey="systemSettings.integrate.authMgmtScope" tooltipKey="systemSettings.integrate.authMgmtScope.desc"/>
       			<tag:formItem formField="true" selectOptionMin="1" selectOptionMax="10" valueKey="integrate_authMaxFailCount" labelKey="systemSettings.integrate.authMaxFailCount" tooltipKey="systemSettings.integrate.authMaxFailCount.desc"/>
<%--        			<tag:formItem formField="true" selectOptionMin="1" selectOptionMax="10" valueKey="integrate_pinMaxFailCount" labelKey="systemSettings.integrate.pinMaxFailCount" tooltipKey="systemSettings.integrate.pinMaxFailCount.desc"/> --%>
       			<tag:formItem formField="true" selectOptionMin="1" selectOptionMax="10" valueKey="integrate_biotpMaxFailCount" labelKey="systemSettings.integrate.biotpMaxFailCount" tooltipKey="systemSettings.integrate.biotpMaxFailCount.desc"/>
       			<hr/>
       			<tag:formItem formField="true"  clazzName="form-changeTp" selectChangedTarget="integrate_checkAuthResultLifetimePeriods_" selectOptions="${lifetimePeriodTypes}" valueKey="integrate_checkAuthResultLifetimePeriodType" labelKey="systemSettings.integrate.checkAuthResultLifetimePeriodType" tooltipKey="systemSettings.integrate.checkAuthResultLifetimePeriodType"/>
				<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="20" selectOptionMax="100" selectOptionStep="5" selectOptionWrapKey="systemSettings.tp.SECOND.wrap" valueKey="integrate_checkAuthResultLifetimePeriods_SECOND" labelKey="systemSettings.integrate.checkAuthResultLifetimePeriods" tooltipKey="systemSettings.integrate.checkAuthResultLifetimePeriods"/>
				<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="60" selectOptionWrapKey="systemSettings.tp.MINUTE.wrap" valueKey="integrate_checkAuthResultLifetimePeriods_MINUTE" labelKey="systemSettings.integrate.checkAuthResultLifetimePeriods" tooltipKey="systemSettings.integrate.checkAuthResultLifetimePeriods"/>
				<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="24" selectOptionWrapKey="systemSettings.tp.HOUR.wrap" valueKey="integrate_checkAuthResultLifetimePeriods_HOUR" labelKey="systemSettings.integrate.checkAuthResultLifetimePeriods" tooltipKey="systemSettings.integrate.checkAuthResultLifetimePeriods"/>
				<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="1" selectOptionWrapKey="systemSettings.tp.DAY.wrap" valueKey="integrate_checkAuthResultLifetimePeriods_DAY" labelKey="systemSettings.integrate.checkAuthResultLifetimePeriods" tooltipKey="systemSettings.integrate.checkAuthResultLifetimePeriods"/>
				       			
       			<hr/>
       			<tag:formItem formField="true" clazzName="form-changeTp" selectChangedTarget="integrate_issueCodeLifetimePeriods_" selectOptions="${lifetimePeriodTypes}" valueKey="integrate_issueCodeLifetimePeriodType" labelKey="systemSettings.integrate.issueCodeLifetimePeriodType" tooltipKey="systemSettings.integrate.issueCodeLifetimePeriodType.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="20" selectOptionMax="100" selectOptionStep="5" selectOptionWrapKey="systemSettings.tp.SECOND.wrap" valueKey="integrate_issueCodeLifetimePeriods_SECOND" labelKey="systemSettings.integrate.issueCodeLifetimePeriods" tooltipKey="systemSettings.integrate.issueCodeLifetimePeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="60" selectOptionWrapKey="systemSettings.tp.MINUTE.wrap" valueKey="integrate_issueCodeLifetimePeriods_MINUTE" labelKey="systemSettings.integrate.issueCodeLifetimePeriods" tooltipKey="systemSettings.integrate.issueCodeLifetimePeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="24" selectOptionWrapKey="systemSettings.tp.HOUR.wrap" valueKey="integrate_issueCodeLifetimePeriods_HOUR" labelKey="systemSettings.integrate.issueCodeLifetimePeriods" tooltipKey="systemSettings.integrate.issueCodeLifetimePeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="30" selectOptionWrapKey="systemSettings.tp.DAY.wrap" valueKey="integrate_issueCodeLifetimePeriods_DAY" labelKey="systemSettings.integrate.issueCodeLifetimePeriods" tooltipKey="systemSettings.integrate.issueCodeLifetimePeriods.desc"/>
       			<tag:formItem formField="true" selectOptionMin="1" selectOptionMax="10" valueKey="integrate_issueCodeMaxFailCount" labelKey="systemSettings.integrate.issueCodeMaxFailCount" tooltipKey="systemSettings.integrate.issueCodeMaxFailCount.desc"/>
       			<hr/>
       			<tag:formItem formField="true" valueKey="integrate_rpServerUri" labelKey="systemSettings.integrate.rpServerUri" tooltipKey="systemSettings.integrate.rpServerUri.desc"/>
       			<tag:formItem formField="true" valueKey="integrate_trustedFacetUrl" labelKey="systemSettings.integrate.trustedFacetUrl" tooltipKey="systemSettings.integrate.trustedFacetUrl.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptionMin="1000" selectOptionMax="50000" selectOptionStep="1000" valueKey="integrate_tokenThresholdCnt" labelKey="systemSettings.integrate.tokenThresholdCnt" tooltipKey="systemSettings.integrate.tokenThresholdCnt.desc"/>
       			
       		</tag:detailsBox>
       		<c:if test="${AUTH_METHOD_FIDO_ENABLED }">
       		<tag:detailsBox titleKeyDetails="systemSettings.fido" detailBoxId="sysSettings-FIDO_SETTINGS" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-FIDO_SETTINGS" detailBoxFormId="FIDO_SETTINGS">
       			<tag:formItem formField="true" selectOptions="${AppIDPolicy}" valueKey="fido_appIdPolicy" labelKey="systemSettings.fido.appIdPolicy" tooltipKey="systemSettings.fido.appIdPolicy.desc"/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="fido_valdiateChannelBindingEnabled" labelKey="systemSettings.fido.valdiateChannelBindingEnabled" tooltipKey="systemSettings.fido.valdiateChannelBindingEnabled.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${MultiDevicePolicy}" valueKey="fido_multiDevicePolicy" labelKey="systemSettings.multiDevicePolicy" tooltipKey="systemSettings.multiDevicePolicy.desc"/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="fido_multiAppEnabled" labelKey="systemSettings.multiAppEnabled" tooltipKey="systemSettings.multiAppEnabled.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" clazzName="issuranceExpiredEnabled" selectChangedTarget="fido_issuranceExpiredPeriodType" valueKey="fido_issuranceExpiredEnabled" labelKey="systemSettings.issuranceExpiredEnabled" tooltipKey="systemSettings.issuranceExpiredEnabled.desc"/>
       			<tag:formItem formField="true" clazzName="form-changeTp" selectChangedTarget="fido_issuranceExpiredPeriods_" selectOptions="${expiredPeriodTypes}" valueKey="fido_issuranceExpiredPeriodType" labelKey="systemSettings.issuranceExpiredPeriodType" tooltipKey="systemSettings.issuranceExpiredPeriodType.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="24" selectOptionWrapKey="systemSettings.tp.HOUR.wrap" valueKey="fido_issuranceExpiredPeriods_HOUR" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="30" selectOptionWrapKey="systemSettings.tp.DAY.wrap" valueKey="fido_issuranceExpiredPeriods_DAY" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="30" selectOptionWrapKey="systemSettings.tp.MONTH.wrap" valueKey="fido_issuranceExpiredPeriods_MONTH" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="5" selectOptionWrapKey="systemSettings.tp.YEAR.wrap" valueKey="fido_issuranceExpiredPeriods_YEAR" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="fido_issueCodeEnabled" labelKey="systemSettings.issueCodeEnabled" tooltipKey="systemSettings.issueCodeEnabled.desc"/>

       		</tag:detailsBox></c:if>
       		
       		<c:if test="${AUTH_METHOD_BIOTP_ENABLED }">
       		<tag:detailsBox titleKeyDetails="systemSettings.biotp" detailBoxId="sysSettings-BIOTP_SETTINGS" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-BIOTP_SETTINGS" detailBoxFormId="BIOTP_SETTINGS">
       			<tag:formItem formField="true" selectOptions="${AppIDPolicy}" valueKey="biotp_appIdPolicy" labelKey="systemSettings.fido.appIdPolicy" tooltipKey="systemSettings.fido.appIdPolicy.desc"/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="biotp_valdiateChannelBindingEnabled" labelKey="systemSettings.fido.valdiateChannelBindingEnabled" tooltipKey="systemSettings.fido.valdiateChannelBindingEnabled.desc"/>
       			<hr/>
       			
       			<tag:formItem formField="true" selectOptions="${MultiDevicePolicy}" valueKey="biotp_multiDevicePolicy" labelKey="systemSettings.multiDevicePolicy" tooltipKey="systemSettings.multiDevicePolicy.desc"/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="biotp_multiAppEnabled" labelKey="systemSettings.multiAppEnabled" tooltipKey="systemSettings.multiAppEnabled.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" clazzName="issuranceExpiredEnabled" selectChangedTarget="biotp_issuranceExpiredPeriodType" valueKey="biotp_issuranceExpiredEnabled" labelKey="systemSettings.issuranceExpiredEnabled" tooltipKey="systemSettings.issuranceExpiredEnabled.desc"/>
       			<tag:formItem formField="true" clazzName="form-changeTp" selectChangedTarget="biotp_issuranceExpiredPeriods_" selectOptions="${expiredPeriodTypes}" valueKey="biotp_issuranceExpiredPeriodType" labelKey="systemSettings.issuranceExpiredPeriodType" tooltipKey="systemSettings.issuranceExpiredPeriodType.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="24" selectOptionWrapKey="systemSettings.tp.HOUR.wrap" valueKey="biotp_issuranceExpiredPeriods_HOUR" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="30" selectOptionWrapKey="systemSettings.tp.DAY.wrap" valueKey="biotp_issuranceExpiredPeriods_DAY" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="30" selectOptionWrapKey="systemSettings.tp.MONTH.wrap" valueKey="biotp_issuranceExpiredPeriods_MONTH" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="5" selectOptionWrapKey="systemSettings.tp.YEAR.wrap" valueKey="biotp_issuranceExpiredPeriods_YEAR" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="biotp_issueCodeEnabled" labelKey="systemSettings.issueCodeEnabled" tooltipKey="systemSettings.issueCodeEnabled.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${TokenSharePolicy}" valueKey="biotp_tokenSharePolicy" labelKey="systemSettings.biotp.tokenSharePolicy" tooltipKey="systemSettings.biotp.tokenSharePolicy.desc"/>

       		</tag:detailsBox></c:if>
       		
       		<tag:detailsBox titleKeyDetails="systemSettings.hwotp" detailBoxId="sysSettings-HWOTP_SETTINGS" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-HWOTP_SETTINGS" detailBoxFormId="HWOTP_SETTINGS">
				<tag:formItem formField="true" valueKey="name" labelKey="systemSettings.hwotp.name" tooltipKey="systemSettings.hwotp.name.desc"/>
				<tag:formItem formField="true" valueKey="normAuthTmSkew" labelKey="systemSettings.hwotp.normAuthTmSkew" tooltipKey="systemSettings.hwotp.normAuthTmSkew.desc"/>
				<tag:formItem formField="true" valueKey="userSyncTmSkew" labelKey="systemSettings.hwotp.userSyncTmSkew" tooltipKey="systemSettings.hwotp.userSyncTmSkew.desc"/>       		
				<tag:formItem formField="true" valueKey="adminSyncTmSkew" labelKey="systemSettings.hwotp.adminSyncTmSkew" tooltipKey="systemSettings.hwotp.adminSyncTmSkew.desc"/>
				<tag:formItem formField="true" valueKey="initAuthTmSkew" labelKey="systemSettings.hwotp.initAuthTmSkew" tooltipKey="systemSettings.hwotp.initAuthTmSkew.desc"/>
				<tag:formItem formField="true" valueKey="maxAuthFailCnt" labelKey="systemSettings.hwotp.maxAuthFailCnt" tooltipKey="systemSettings.hwotp.maxAuthFailCnt.desc"/>
				
       		</tag:detailsBox>
       		
       		<c:if test="${AUTH_METHOD_SAOTP_ENABLED }">
       		<tag:detailsBox titleKeyDetails="systemSettings.saotp" detailBoxId="sysSettings-SAOTP_SETTINGS" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-SAOTP_SETTINGS" detailBoxFormId="SAOTP_SETTINGS">
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="saotp_serverPinEnabled" labelKey="systemSettings.saotp.serverPinEnabled" tooltipKey="systemSettings.saotp.serverPinEnabled.desc"/>
       			<tag:formItem formField="true" valueKey="saotp_serverPinMaxFailCnt" labelKey="systemSettings.saotp.serverPinMaxFailCnt" tooltipKey="systemSettings.saotp.serverPinMaxFailCnt.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${MultiDevicePolicy}" valueKey="saotp_multiDevicePolicy" labelKey="systemSettings.multiDevicePolicy" tooltipKey="systemSettings.multiDevicePolicy.desc"/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="saotp_multiAppEnabled" labelKey="systemSettings.multiAppEnabled" tooltipKey="systemSettings.multiAppEnabled.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" clazzName="issuranceExpiredEnabled" selectChangedTarget="saotp_issuranceExpiredPeriodType" valueKey="saotp_issuranceExpiredEnabled" labelKey="systemSettings.issuranceExpiredEnabled" tooltipKey="systemSettings.issuranceExpiredEnabled.desc"/>
       			<tag:formItem formField="true" clazzName="form-changeTp" selectChangedTarget="saotp_issuranceExpiredPeriods_" selectOptions="${expiredPeriodTypes}" valueKey="saotp_issuranceExpiredPeriodType" labelKey="systemSettings.issuranceExpiredPeriodType" tooltipKey="systemSettings.issuranceExpiredPeriodType.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="24" selectOptionWrapKey="systemSettings.tp.HOUR.wrap" valueKey="saotp_issuranceExpiredPeriods_HOUR" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="30" selectOptionWrapKey="systemSettings.tp.DAY.wrap" valueKey="saotp_issuranceExpiredPeriods_DAY" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="30" selectOptionWrapKey="systemSettings.tp.MONTH.wrap" valueKey="saotp_issuranceExpiredPeriods_MONTH" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<tag:formItem formField="false" clazzName="form-tp" selectOptionMin="1" selectOptionMax="5" selectOptionWrapKey="systemSettings.tp.YEAR.wrap" valueKey="saotp_issuranceExpiredPeriods_YEAR" labelKey="systemSettings.issuranceExpiredPeriods" tooltipKey="systemSettings.issuranceExpiredPeriods.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="saotp_issueCodeEnabled" labelKey="systemSettings.issueCodeEnabled" tooltipKey="systemSettings.issueCodeEnabled.desc"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${TokenSharePolicy}" valueKey="saotp_tokenSharePolicy" labelKey="systemSettings.saotp.tokenSharePolicy" tooltipKey="systemSettings.saotp.tokenSharePolicy.desc"/>
				
       		</tag:detailsBox></c:if>
       		
       		<tag:detailsBox titleKeyDetails="systemSettings.externalApi" detailBoxId="sysSettings-EXTERNAL_API_SETTINGS" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-EXTERNAL_API_SETTINGS" detailBoxFormId="EXTERNAL_API_SETTINGS">
       			<tag:formItem formField="true" selectOptionArrays="${ExternalWebApiTypes }" valueKey="externalApi_serviceType" labelKey="systemSettings.externalApi.serviceType" tooltipKey="systemSettings.externalApi.serviceType.desc"/>
       			<tag:formItem formField="false" clazzName="service-hidden" readonly="true" valueKey="externalApi_serviceSharedKey" labelKey="systemSettings.externalApi.serviceSharedKey" tooltipKey="systemSettings.externalApi.serviceSharedKey.desc"/>
       			<tag:formItem formField="true" clazzName="service-hidden" valueKey="externalApi_serivceAddress" labelKey="systemSettings.externalApi.serivceAddress" tooltipKey="systemSettings.externalApi.serivceAddress.desc" placeholderKey="systemSettings.externalApi.serivceAddress.placeHolder"/>
       			<c:set var="periodsItems" value="${fn:split('1,2,3,4,5,10,15,20,25,30', ',')}" />      			
       			<tag:formItem formField="true" selectOptionArrays2="${periodsItems }" selectOptionWrapKey="systemSettings.tp.MINUTE.wrap" valueKey="externalApi_transactionLifeTimePeriods" labelKey="systemSettings.externalApi.transactionLifeTimePeriods" tooltipKey="systemSettings.externalApi.transactionLifeTimePeriods.desc"/>
       		</tag:detailsBox>

			<sec:authorize access="hasAnyAuthority('SUPER', 'DEV')">
       		<tag:detailsBox titleKeyDetails="systemSettings.advanced" detailBoxId="sysSettings-ADVANCED_SETTINGS" useForm="true" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-ADVANCED_SETTINGS" detailBoxFormId="ADVANCED_SETTINGS">
       			<tag:formItem formField="true" clazzName="serviceEnabled" selectChangedTarget="advanced_payloadParserFido" selectOptions="${SettingEnabled}" valueKey="advanced_fidoEnabled" labelKey="systemSettings.advanced.fidoEnabled"/>
       			<tag:formItem formField="true" clazzName="service-hidden" selectOptionArrays="${FidoPayloadParser}" valueKey="advanced_payloadParserFido" labelKey="systemSettings.advanced.payloadParserFido"/>
       			<tag:formItem formField="true" clazzName="serviceEnabled" selectChangedTarget="advanced_payloadParserBiotp" selectOptions="${SettingEnabled}" valueKey="advanced_biotpEnabled" labelKey="systemSettings.advanced.biotpEnabled"/>
       			<tag:formItem formField="true" clazzName="service-hidden" selectOptionArrays="${BiotpPayloadParser}" valueKey="advanced_payloadParserBiotp" labelKey="systemSettings.advanced.payloadParserBiotp"/>
       			<tag:formItem formField="true" clazzName="serviceEnabled" selectChangedTarget="advanced_payloadParserSaotp" selectOptions="${SettingEnabled}" valueKey="advanced_saotpEnabled" labelKey="systemSettings.advanced.saotpEnabled"/>
       			<c:if test="${AUTH_METHOD_SAOTP_ENABLED }">
       			<tag:formItem formField="true" clazzName="service-hidden" selectOptionArrays="${SaotpPayloadParser}" valueKey="advanced_payloadParserSaotp" labelKey="systemSettings.advanced.payloadParserSaotp"/>
       			<tag:formItem formField="true" clazzName="service-hidden" selectOptions="${SettingEnabled}" valueKey="advanced_multiAuthMethodsEnabled" labelKey="systemSettings.advanced.multiAuthMethodsEnabled"/>
       			<tag:formItem formField="true" clazzName="service-hidden" selectOptionArrays="${OTPKeyBased}" valueKey="advanced_otpKeybased" labelKey="systemSettings.advanced.otpKeybased"/>
       			<hr/>
       			<tag:formItem formField="true" selectOptions="${ServerPinPolicy}" valueKey="advanced_serverPinPolicy" labelKey="systemSettings.advanced.serverPinPolicy" tooltipKey="systemSettings.advanced.serverPinPolicy.desc"/>
       			<tag:formItem formField="true" selectOptionArrays="${ServerPinValidationParser}" valueKey="advanced_serverPinValidationParser" labelKey="systemSettings.advanced.serverPinValidationParser"/>
       			<hr/></c:if>
       			
       			<c:if test="${WEB_API_POLICY != 'GLOBAL_WIBEE' }">
       			<tag:formItem formField="true" selectOptions="${SettingEnabled}" valueKey="advanced_purgeDataEnabled" labelKey="systemSettings.advanced.purgeDataEnabled"/>
       			<tag:formItem formField="true" clazzName="service-hidden" valueKey="advanced_purgeCronExpression" labelKey="systemSettings.advanced.purgeCronExpression"/>
       			<tag:formItem formField="true" clazzName="service-hidden" selectOptionMin="1" selectOptionMax="5" selectOptionWrapKey="systemSettings.tp.YEAR.wrap" valueKey="advanced_purgeDataLifecyle" labelKey="systemSettings.advanced.purgeDataLifecyle" tooltipKey="systemSettings.advanced.purgeDataLifecyle.desc"/>
       			<hr/>
       			</c:if>
       			<tag:formItem formField="true" clazzName="serviceEnabled" selectChangedTarget="advanced_publisherTarget" selectOptions="${SettingEnabled}" valueKey="advanced_publisherEnabled" labelKey="systemSettings.advanced.publisherEnabled" tooltipKey="systemSettings.advanced.publisherEnabled.desc" />
       			
       			<!-- list view -->
       			<tag:formItem valueKey="advanced_publisherTarget" clazzName="service-hidden" formField="false" hasBodyContents="true" labelKey="systemSettings.advanced.publisherTarget">
       				<div class="panel panel-default">
					  <div class="panel-body">
					   	<div class="input-group">
						     <input type="text" class="form-control" placeholder="<fmt:message key="systemSettings.advanced.publisherTarget.placeholder"/>" id="add_publisherTarget" data-regex="^(http|https)(\:\/\/)">
						     <span class="input-group-btn">
						       <button class="btn btn-primary mmth-btn" type="button" onclick="addListview($(this).data('form'), $(this).data('listview'))" data-form="#add_publisherTarget" data-listview="#listview_publisherTarget"><span class="fa fa-plus"></span></button>
						     </span>
						</div>
					  </div>
					</div>

       				<ul class="list-group" id="listview_publisherTarget"></ul>
       			</tag:formItem>
       			
       			
       		</tag:detailsBox></sec:authorize>  
       		
       		<tag:detailsBox titleKeyDetails="systemSettings.countrycode" detailBoxId="sysSettings-COUNTRYCODE_SETTINGS" useForm="false" excludeCollapse="true" excludeEdit="true" saveBtnId="btn-COUNTRYCODE_SETTINGS" detailBoxFormId="COUNTRYCODE_SETTINGS">
       		
       		<div class="row" id="ctx-customKeys_countryCode">
       			<div class="col-md-5">
       				<label class="control-label"><fmt:message key="systemSettings.countrycode.all"/></label>
       				<select id="country_code_all" class="mmth-form form-control" multiple="multiple" size="20" ></select>
       			</div>
       			<div class="col-md-2 text-center">
       				<br/><br/>
       				<button class="btn btn-primary mmth-btn" onclick="addCountryCodes()"><fmt:message key="systemSettings.countrycode.add"/> <i class="fa fa-chevron-right"></i></button><br/><br/>
       				<button class="btn btn-info mmth-btn" onclick="removeCountryCodes()"><i class="fa fa-chevron-left"></i> <fmt:message key="systemSettings.countrycode.remove"/></button>
       			</div>
       			<div class="col-md-5">
       				<label class="control-label"><fmt:message key="systemSettings.countrycode.registered"/></label>
       				<select id="country_code_registered" class="mmth-form form-control" multiple="multiple" size="20"></select>
       			</div>
       		</div>
       		
       		</tag:detailsBox>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
		
	</section>
	<!-- /.content -->

</jsp:body>
</tag:page>