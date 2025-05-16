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
#btn-hwotpManager{
	width: 20%;
	margin-left: 71.5%;
	margin-right: 40%
}

#btn-hwotpLost{
	width: 20%;
	margin-left: 71.5%;
	margin-right: 40%
}

#btn-hwotpResetCnt{
	width: 20%;
	margin-left: 71.5%;
	margin-right: 40%
}

#btn-hwotpSync{
	width: 20%;
	margin-left: 71.5%;
	margin-right: 40%
}

#btn-hwotpInfo{
	width: 20%;
	margin-left: 71.5%;
	margin-right: 40%
}

 table {
    width: 100%;
    border: 1px solid #444444;
    text-align: center;
    border-collapse:collapse;
  }
  
 table.tg {
        empty-cells: show;
        text-align: center;
        border-collapse: collapse;
  }
  
th, td {
    border: 1px solid #444444;
    text-align: center;
  }
</style>
</jsp:attribute>
<jsp:attribute name="javascripts">
<head>
<script src="${context_path}/static/resources/js/base64util.js"></script>
</head> 
<script type="text/javascript">
var urlPrefix = '${context_path}/web/manager/branch/rest/';
var initUrl = urlPrefix + 'init';
var getUrl = urlPrefix + 'get/';
var saveUrl = urlPrefix + 'save/';
var cache_countryCodes;
var returnCodeMap;
document.addEventListener('keydown', function(event) {
    if (event.keyCode === 13) {
        event.preventDefault();
    }
}, true);

function init() {
	
	$('#operationType').change(function(){
		if ($('#operationType option:selected').val() == 3)
		{
			$('#comment').attr('disabled', true);
		}else {
			$('#comment').attr('disabled', false);
		}
	});
	
	$('#sysSettingsType > li:LAST-CHILD').addClass('active');
	$('#branch-HWOTP_INFO_MENU').show();
	settingsDetailsBox();
	configList();
	restInit();
	initOTPInfoField();
	getReturnCodeMap();
}

function restInit(){
	// init url을 호출하여 페이지 리스트와 수치 데이터를 조회한다.
	
	$('#btn-hwotpManager').on('click', hwotpManager);
	$('#btn-hwotpLost').on('click', hwotpLost);
	$('#btn-hwotpResetCnt').on('click', hwotpResetCnt);
	$('#btn-hwotpSync').on('click', hwotpSync);
	$('#btn-hwotpInfo').on('click', hwotpInfo);
	
	var caller = ajaxUtil(initUrl);
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			setContextFieldData('HWOTP_INFO_MENU', resp.contextData, resp.data)
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
			$('#branch-' + clazzifySettings).boxWidget('expand');
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


function setContextFieldData(clazzifySettings, contextData, commonData) {
	
	$('#'+clazzifySettings).find('.form-tp').hide();
	$('#'+clazzifySettings).find('.form-changeTp').hide();
	
	if ('HWOTP_MANAGER_MENU' == clazzifySettings) {
		$('#operationUser').val('');
		$('#operationOtpsn').val('');
		$('#comment').val('');
	} else if ('HWOTP_LOST_MENU' == clazzifySettings){
		$('#lostUser').val('');
	} else if ('HWOTP_RESET_COUNT_MENU' == clazzifySettings){
		$('#resetUser').val('');	
	} else if ('HWOTP_SYNC_MENU' == clazzifySettings){
		$('#syncUser').val('');	
	} else if ('HWOTP_INFO_MENU' == clazzifySettings){
		initOTPInfoField();
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
		location.hash = '#mmth-branchContents';
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

function hwotpManager(){
	
	$("#issuanceDate").text("");
	$("#disuseDate").text("");
	
	var user =  $('#operationUser').val();
	var tokenId = $('#operationOtpsn').val();
	var type =  $('#operationType').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	
	if(tokenId.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.otpsn"/>'
		});
		return ;
	}
	if(type==1){
		BootstrapDialog.confirm({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.user"/>' +' : ' + user + '\n' +'<fmt:message key="branch.otpsn"/>' +' : ' + tokenId,
			callback: function(result) {
	        	if(result) {
	            	getUserInfo();
	            }
	        }

		});
	}
	else if(type==2){	
		BootstrapDialog.confirm({
			 type: BootstrapDialog.TYPE_WARNING,
			 message: '<fmt:message key="branch.user"/>' +' : ' + user + '\n' +'<fmt:message key="branch.otpsn"/>' +' : ' +  tokenId,
			 callback: function(result) {
	                if(result) {
	                	getCheckToken();
	                }
	            }

		});
	}
	else if(type==3){		
		BootstrapDialog.confirm({
			 type: BootstrapDialog.TYPE_WARNING,
			 message: '<fmt:message key="branch.user"/>' +' : ' + user + '\n' +'<fmt:message key="branch.otpsn"/>' +' : ' +  tokenId,
			 callback: function(result) {
	                if(result) {
	                	disuseOtp();
	                }
	            }

		});
	}
	
}

function hwotpLost(){

	var user =  $('#lostUser').val();
	var type =  $('#lostType').val();

	if(type==1){
		BootstrapDialog.confirm({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.user"/>' +' : ' + user,
			callback: function(result) {
	        	if(result) {
	               	lost();
	            }
	        }
		});
	}
	else if(type==2){
		BootstrapDialog.confirm({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.user"/>' +' : ' + user,
			callback: function(result) {
	        	if(result) {
	            	clear();
	            }
	        }
		});	
	}
}

function swotpForceDereg() {
	var user =  $('#lostUser').val();
	
	BootstrapDialog.confirm({
		type: BootstrapDialog.TYPE_WARNING,
		message: '<fmt:message key="branch.user"/>' +' : ' + user,
		callback: function(result) {
        	if(result) {
        		var sendData = {
       				userName : window.btoa(user)					
       			};

       			jQuery.ajax('/rpserver/webapi/BIOTP/ForceDereg', {
					headers : {
						'Accept' : 'application/rp+json; charset=utf-8',
						'Content-type' : 'application/rp+json; charset=utf-8',
					},
					data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
					async: false,
					method: 'POST'
				}).done(function(recievedData) { // 응답 성공 시
					var messageKey = returnCodeMap[recievedData.returnCode];
					if (recievedData.returnCode == "0000"){
						BootstrapDialog.alert({
							type: BootstrapDialog.TYPE_SUCCESS,
							message: messageKey
						});
					}
					else
					{
						BootstrapDialog.alert({
							type: BootstrapDialog.TYPE_WARNING,
							message: messageKey
						});
					}
				})
				.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
					console.log(jqXHR, textStatus, errorThrown);
					alert(JSON.stringify(textStatus));
				});
        	}
		}
	});
}

function hwotpResetCnt(){
	var user =  $('#resetUser').val();
	var type =  $('#resetType').val();
	
	BootstrapDialog.confirm({
		type: BootstrapDialog.TYPE_WARNING,
		message: '<fmt:message key="branch.user"/>' +' : ' + user,
		callback: function(result) {
        	if(result) {
            	getUserProductType();
            }
        }
	});
}

function hwotpSync(){
	
	var user =  $('#syncUser').val();
	var otp =  $('#otp').val();
	var nextotp =  $('#nextotp').val();
	
	BootstrapDialog.confirm({
		type: BootstrapDialog.TYPE_WARNING,
		message: '<fmt:message key="branch.user"/>' +' : ' + user + '\n' +'<fmt:message key="branch.sync.otp"/>' +' : ' + otp + '\n' +'<fmt:message key="branch.sync.nextotp"/>' +' : ' + nextotp,
		callback: function(result) {
        	if(result) {
        		syncAdmin();
            }
        }
	});
}

function hwotpInfo(){
	var user = $('#infoUser').val();
	getCheckUser(user, null, 0);
}

function issueOtp(type){
	var user = $('#operationUser').val();
	var otpsn = $('#operationOtpsn').val();
	var comment = Base64.encode($('#comment').val());
		
	var sendData = {
			userName : window.btoa(user),
		    tokenId : window.btoa(otpsn),
		    comment : comment,
		    branchId : "${sessionScope['MMTH.ADMIN.USERNAME']}"
		};
						

		jQuery.ajax('/rpserver/webapi/HWOTP/IssueOtp', {
			headers : {
				'Accept' : 'application/rp+json; charset=utf-8',
				'Content-type' : 'application/rp+json; charset=utf-8',
			},
			data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
			async: false,
			method: 'POST'
		}).done(function(recievedData) { // 응답 성공 시
			console.log('recievedData = ', recievedData);
			var messageKey = returnCodeMap[recievedData.returnCode];
			if (recievedData.returnCode != "0000")
			{
				if (type == 1) {
// 					deleteUser();
				}
				BootstrapDialog.alert({
					type: BootstrapDialog.TYPE_WARNING,
					message: messageKey
				});
			}
			else {
				getCheckUser(user, null, 1);	
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
			console.log(jqXHR, textStatus, errorThrown);
			alert(JSON.stringify(textStatus));
		});
}
			
function disuseOtp(){
	var user = $('#operationUser').val();
	var otpsn = $('#operationOtpsn').val();
	var type =  $('#operationType').val();
	
	var sendData = {
			userName : window.btoa(user),				
			tokenId : (type != 2) ? window.btoa(otpsn) : null
		};

		jQuery.ajax('/rpserver/webapi/HWOTP/DisuseOtp', {
			headers : {
				'Accept' : 'application/rp+json; charset=utf-8',
				'Content-type' : 'application/rp+json; charset=utf-8',
			},
			data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
			async: false,
			method: 'POST'
		})
		.done(function(recievedData) { // 응답 성공 시
			
			var messageKey = returnCodeMap[recievedData.returnCode];
			if (recievedData.returnCode == "0000" || recievedData.returnCode == "8004"){
				if(type == 2){ //H/W OTP 재발급시
					issueOtp(type);
				}
				else if(type == 3){	// H/W OTP 폐기시				
					getCheckUser(user, otpsn, 2);
				}
			}
			else
			{
				BootstrapDialog.alert({
					type: BootstrapDialog.TYPE_WARNING,
					message: messageKey
				});
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
			console.log(jqXHR, textStatus, errorThrown);
			alert(JSON.stringify(textStatus));
		});
}

function findUser() {
	var user = $('#operationUser').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		
		return ;
	}
	
	var sendData = {
		userName : window.btoa(user)
	};
	
	jQuery.ajax('/rpserver/webapi/BIOTP/GetUserInfo', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		console.log('findUser recievedData = ', recievedData);
		if (recievedData.returnCode == "0000"){
			$("#operationAccountName").val(recievedData.accountName);	
		}
		else
		{
			alert("Please add Account name. before issuancing.");
			$('#operationAccountName').val('');
			$('#operationAccountName').focus();
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function saveUser() {
	var user = $('#operationUser').val();
	var accountName = $("#operationAccountName").val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	
// 	if(accountName.length == 0) {
// 		BootstrapDialog.alert({
// 			type: BootstrapDialog.TYPE_WARNING,
// 			message: 'accountName is null'
// 		});
// 		return ;
// 	}
	var arr = new Array();
	var obj = new Object(); 
    obj.userName = window.btoa(user);
    obj.productTypeCode = window.btoa(64);
    arr.push(obj);
	
	jQuery.ajax('/rpserver/webapi/BIOTP/RegUser', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(arr), // JSON 객체를 String으로 보냄
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		if (recievedData.returnCode == '0000'){ // 사용자 추가 성공 시, HW OTP 발급 프로세스 수행
			issueOtp(1);
		}
		else
		{
			BootstrapDialog.alert({
				type: BootstrapDialog.TYPE_WARNING,
				message: 'Register user error'
			});
			return ;
		}
	
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function deleteUser(){
	var user = $('#operationUser').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	

	var arr = new Array();
	var obj = new Object(); 
    obj.userName = window.btoa(user);
    arr.push(obj);
	
	jQuery.ajax('/rpserver/webapi/BIOTP/DeleteUser', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(arr), // JSON 객체를 String으로 보냄
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		if (recievedData.returnCode == '0000' || recievedData.returnCode == '1401'){
			
		}
		else
		{
			BootstrapDialog.alert({
				type: BootstrapDialog.TYPE_WARNING,
				message: 'Delete user error'
			});
			return ;
		}
	
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function getUserInfo(){
	var user = $('#operationUser').val();
	var type =  $('#operationType').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	
	var sendData = {
		userName : window.btoa(user)
	};

	jQuery.ajax('/rpserver/webapi/BIOTP/GetUserInfo', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		var messageKey = returnCodeMap[recievedData.returnCode];
		if (recievedData.returnCode == "0000")
		{
			if(type == 1){ // H/W OTP 발급
				issueOtp(type);
			}
			else if(type == 2){ //H/W OTP 재발급
				if(recievedData.productType == 8){  // H/W OTP 사용 중인 경우 강제 해지
					disuseOtp(); // 폐기한 후, issueOtp() 호출함
				}
				else {  // 그 외 인증 매체 사용자인 경우
					BootstrapDialog.alert({
						type: BootstrapDialog.TYPE_WARNING,
						message: '<fmt:message key="ReturnCodes.ISSUE_NOT_AVAILABLE"/>'
					});
				}
			} 
			else { // H/W OTP 페기
				if(recievedData.productType == 8){  // H/W OTP 사용 중인 경우 강제 해지
					disuseOtp();
				}else {
					BootstrapDialog.alert({
						type: BootstrapDialog.TYPE_WARNING,
						message: '<fmt:message key="ReturnCodes.DISUSE_NOT_AVAILABLE"/>'
					});
				}
			}
		}
		else 
		{
			if(type == 1){ // H/W OTP 발급
				saveUser(); //사용자 저장 후, issueOtp() 호출함
			}
			else if(type == 2){ //H/W OTP 재발급
				BootstrapDialog.alert({
					type: BootstrapDialog.TYPE_WARNING,
					message: '<fmt:message key="ReturnCodes.ISSUE_NOT_AVAILABLE"/>'
				});
			}
			else {
				BootstrapDialog.alert({
					type: BootstrapDialog.TYPE_WARNING,
					message: '<fmt:message key="ReturnCodes.DISUSE_NOT_AVAILABLE"/>'
				});
			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function getUserProductType(){

	var user = $('#resetUser').val();
	var type =  $('#resetType').val();
	
	if(user.length == 0) {
			BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	

	var sendData = {
		userName : window.btoa(user)
	};
					
	jQuery.ajax('/rpserver/webapi/BIOTP/GetUserInfo', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		
		var messageKey = returnCodeMap[recievedData.returnCode];
		if (recievedData.returnCode == "0000"){
				if(recievedData.productType == 8)  //H/W OTP 사용 중인 경우
					resetUnlockCnt();
				else if(recievedData.productType == 2)  // S/W OTP 사용 중인 경우
					resetAuthFailCnt();
		}	
		else
		{
			BootstrapDialog.alert({
				type: BootstrapDialog.TYPE_WARNING,
				message: messageKey
			});
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}
		
function getReturnCodeMap(){
	jQuery.ajax('/rpserver/webapi/HWOTP/ReturnCodes', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		returnCodeMap = recievedData; 
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function initOTPInfoField()
{
	$('#infoUser').val('');
	
	$('#infoOtpsn').text("-");
	$('#infoIssueDate').text("-");
	$('#infoStatus').text("-");
	$('#infoOtp').text("-");
	$('#infoOtpPin').text("-");
	$('#infoOtpFailCnt').text("-");
	$('#infoOtpType').text("-");
	$('#infoComment').text("-");
	$('#infoBranchId').text("-");
	
}

function getCheckUser(username, otpsn, opType){

	var user = username;
	var tokenId = otpsn;
	var type = opType;
	var sendData = null;
	
	if(user.length == 0) {
			BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	
	if(tokenId == null){
  	  sendData = {
			userName : window.btoa(user)
		};
	}
	else{
	    sendData = {
			userName : window.btoa(user),
			tokenId : window.btoa(tokenId)
		};
	}
	
	jQuery.ajax('/rpserver/webapi/HWOTP/CheckUser', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시

		if(type == 1 && recievedData.tsOccupied != null){ //H/W OTP 발급/재발급
			$('#issuanceDate').val(changeDate(recievedData.tsOccupied));
		}
		else if(type == 2 && recievedData.tsDiscard != null){ //H/W OTP /폐기
			$('#disuseDate').val(changeDate(recievedData.tsDiscard));
		}
		else if(type == 3 && recievedData.tsLost != null){ //H/W OTP 사고신고/회복
			$('#lostDate').val(changeDate(recievedData.tsLost));
		}
		else { //H/W OTP 조회
			initOTPInfoField();
			if(recievedData.length == 0){
				BootstrapDialog.alert({
					type: BootstrapDialog.TYPE_WARNING,
					message: '<fmt:message key="branch.errormsg.not.use.otp"/>'
				});
			}
			else
			{
				if (recievedData.type === undefined){
					$('#infoOtpType').text('<fmt:message key="AuthMethodTypes.BIOTP"/>');
					$('#infoOtpPin').text(recievedData.authFailCnt);
					$('#infoOtpFailCnt').text(recievedData.biotpFailCnt);
				}else{
					$('#infoOtpType').text('<fmt:message key="AuthMethodTypes.HWOTP"/>');
					$('#infoOtpPin').text('-')
					$('#infoOtpFailCnt').text(recievedData.unlockCnt);
				}
				$('#infoOtpsn').text(recievedData.tokenId);
				if(recievedData.tsOccupied != null)
					$('#infoIssueDate').text(changeDate(recievedData.tsOccupied));
				if (recievedData.status == 'AVAILABLE'){
					$('#infoStatus').text('<fmt:message key="TokenStatus.AVAILABLE"/>');
				}
				else if (recievedData.status == 'OCCUPIED'){
					$('#infoStatus').text('<fmt:message key="TokenStatus.OCCUPIED"/>');
				}else if (recievedData.status == 'LOST'){
					$('#infoStatus').text('<fmt:message key="TokenStatus.LOST"/>');
				}else {
					$('#infoStatus').text('<fmt:message key="TokenStatus.DISCARD"/>');
				}
				
				$('#infoComment').text(recievedData.comment);
				$('#infoBranchId').text(recievedData.branchId);
				$('#infoUser').val(recievedData.username);

			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}					

function getCheckToken(){

	var tokenId = $('#operationOtpsn').val();
	var sendData = null;
	
	if(tokenId.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	
	var sendData = {
		tokenId : window.btoa(tokenId)
	};
	
	jQuery.ajax('/rpserver/webapi/HWOTP/CheckToken', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON ê°ì²´ë¥¼ Stringì¼ë¡ ë³´ë
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // ìëµ ì±ê³µ ì
		
		if(recievedData.length != 0){
			if(recievedData.status == "AVAILABLE"){
				getUserInfo();
			}
			else {
				BootstrapDialog.alert({
					type: BootstrapDialog.TYPE_WARNING,
					message: '<fmt:message key="ReturnCodes.ISSUE_NOT_AVAILABLE"/>'
				}); // 토큰 발급상태가 아님
			}	
		}
		else
		{
			BootstrapDialog.alert({
				type: BootstrapDialog.TYPE_WARNING,
				message: '<fmt:message key="ReturnCodes.ISSUE_TOKEN_ID_NONE"/>'
			}); // 토큰 데이터가 없음
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // ìëµ ì¤í¨ ì
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}
		
function lost(){

	var user = $('#lostUser').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}

	var sendData = {
		userName : window.btoa(user)
	};
					

	jQuery.ajax('/rpserver/webapi/BIOTP/GetUserInfo', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		
		var messageKey = returnCodeMap[recievedData.returnCode];
		if (recievedData.returnCode == "0000"){

				if(recievedData.productType == 8)  //H/W OTP 사고신고
					setLost();
				else if(recievedData.productType == 2)  // S/W OTP 강제해지
					swotpForceDereg();		
		}
		else{
			BootstrapDialog.alert({
				type: BootstrapDialog.TYPE_WARNING,
				message: messageKey
			});
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function clear(){

	var user = $('#lostUser').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}

	var sendData = {
		userName : window.btoa(user)
	};

	jQuery.ajax('/rpserver/webapi/BIOTP/GetUserInfo', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		console.log('recievedData = ', recievedData);
		var messageKey = returnCodeMap[recievedData.returnCode];
		if (recievedData.returnCode == "0000"){
			if(recievedData.productType == 8){  //H/W OTP 사고신고회복
				clearLost();
			}
			else if(recievedData.productType == 2){ // S/W OTP 회복불가메시지
				BootstrapDialog.alert({
					type: BootstrapDialog.TYPE_WARNING,
					message: '<fmt:message key="branch.errormsg.swuser.clear"/>'
				});
			}
		}
		else
		{
			BootstrapDialog.alert({
				type: BootstrapDialog.TYPE_WARNING,
				message: messageKey
			});
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}
		
function setLost(){

	var user = $('#lostUser').val();
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	

	var sendData = {
		userName : window.btoa(user)
	};
					

	jQuery.ajax('/rpserver/webapi/HWOTP/SetLost', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		
		var messageKey = returnCodeMap[recievedData.returnCode];
		
		if (recievedData.returnCode == "0000"){
			getCheckUser(user, null, 3);
		}
		else
		{
			BootstrapDialog.alert({
				type: BootstrapDialog.TYPE_WARNING,
				message: messageKey
			});
		}
    									
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}		
		
function clearLost(){

	var user = $('#lostUser').val();
	var type =  $('#lostType').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}

	var sendData = {
		userName : window.btoa(user)
	};		

	jQuery.ajax('/rpserver/webapi/HWOTP/ClearLost', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		
		var messageKey = returnCodeMap[recievedData.returnCode];
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: messageKey
		});		
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function resetUnlockCnt(){

	var user =  $('#resetUser').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			 message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}

	var sendData = {
		userName : window.btoa(user)
	};
					

	jQuery.ajax('/rpserver/webapi/HWOTP/ResetUnlockCnt', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		var messageKey = returnCodeMap[recievedData.returnCode];
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: messageKey
		});
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}	
		
function resetAuthFailCnt(){

	var user = $('#resetUser').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			 message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	
	var sendData = {
		userName : window.btoa(user)
	};
					
	jQuery.ajax('/rpserver/webapi/BIOTP/ResetAuthFail', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		
		var messageKey = returnCodeMap[recievedData.returnCode];
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: messageKey
		});
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}
		
function resetPin(){

	var user = $('#resetUser').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}
	

	var sendData = {
		userName : window.btoa(user),
		productType : 64,
		productAuthType : 2
	};

	jQuery.ajax('/rpserver/httpapi/authtype/Reset', {
		headers : {
			'Accept' : 'application/fido+uaf; charset=utf-8',
			'Content-type' : 'application/fido+uaf; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		
		var result = JSON.parse(recievedData);
		var messageKey = returnCodeMap[result.returnCode];
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: messageKey
		});
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

function syncAdmin(){
	
	var user = $('#syncUser').val();
	var otp =  $('#otp').val();
	var nextotp =  $('#nextotp').val();
	
	if(user.length == 0) {
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: '<fmt:message key="branch.errormsg.user"/>'
		});
		return ;
	}

	var sendData = {
		userName : window.btoa(user),
		otp : window.btoa(otp),
		nextotp : window.btoa(nextotp)
	};		

	jQuery.ajax('/rpserver/webapi/HWOTP/SyncAdmin', {
		headers : {
			'Accept' : 'application/rp+json; charset=utf-8',
			'Content-type' : 'application/rp+json; charset=utf-8',
		},
		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
		async: false,
		method: 'POST'
	})
	.done(function(recievedData) { // 응답 성공 시
		
		var messageKey = returnCodeMap[recievedData.returnCode];
		BootstrapDialog.alert({
			type: BootstrapDialog.TYPE_WARNING,
			message: messageKey
		});	
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}
		
function changeDate(datel){
	var date = new Date(datel);
    var yyyy = date.getFullYear().toString();
    var mm = (date.getMonth() + 1).toString();
    var dd = date.getDate().toString();
	var HH = date.getHours().toString();
	var MM = date.getMinutes().toString();
	var ss = date.getSeconds().toString();
	
    return yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + (dd[1] ? dd : '0' + dd[0]) + ' ' +  HH + ':' + (MM[1] ? MM : '0' + MM[0]) + ':' + (ss[1] ? ss : '0' + ss[0]);
}

</script>
</jsp:attribute>
	<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			<sec:authorize access="hasAnyAuthority('OPTEAM')">
				<fmt:message key="nav.branch" /> <small><fmt:message key="branch.desc" /></small>
			</sec:authorize>
			<sec:authorize access="hasAnyAuthority('CSTEAM')">
				<fmt:message key="nav.csteam" /> <small><fmt:message key="csteam.desc" /></small>
			</sec:authorize>
		</h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.biotp"/></li>
			<li class="active"><i class="fa fa-users"></i> <fmt:message key="nav.manager" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="row">
		
        <div class="col-md-3">

        	<div class="box box-solid">
        		<div class="box-header with-border">
	            	<h3 class="box-title">
	             		<sec:authorize access="hasAnyAuthority('OPTEAM')">
							<fmt:message key="nav.branch" />
						</sec:authorize>
						<sec:authorize access="hasAnyAuthority('CSTEAM')">
							<fmt:message key="nav.csteam" />
						</sec:authorize>
					</h3>
		            <div class="box-tools">
	                	<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	             	</div>
	            </div>
	            <div class="box-body no-padding">
	            	<ul class="nav nav-tab nav-pills nav-stacked mmth-nav-light" id="sysSettingsType">
	            	    <sec:authorize access="hasAnyAuthority('OPTEAM')">
	            		<li><a href="#mmth-branchContents" data-value="HWOTP_MANAGER_MENU" data-target="#branch-HWOTP_MANAGER_MENU"><fmt:message key="branch.hwotp.manager" /></a></li>
	            		</sec:authorize>
	            		<sec:authorize access="hasAnyAuthority('OPTEAM', 'CSTEAM')">
	            		<li><a href="#mmth-branchContents" data-value="HWOTP_LOST_MENU" data-target="#branch-HWOTP_LOST_MENU"><fmt:message key="branch.hwotp.lost" /></a></li>
	            		</sec:authorize>
              		    <sec:authorize access="hasAnyAuthority('OPTEAM', 'CSTEAM')">
              		    <li><a href="#mmth-branchContents" data-value="HWOTP_RESET_COUNT_MENU" data-target="#branch-HWOTP_RESET_COUNT_MENU"><fmt:message key="branch.hwotp.resetcnt" /></a></li>
              		    </sec:authorize>
              		     <sec:authorize access="hasAnyAuthority('OPTEAM')">
	            		<li><a href="#mmth-branchContents" data-value="HWOTP_SYNC_MENU" data-target="#branch-HWOTP_SYNC_MENU"><fmt:message key="branch.hwotp.sync" /></a></li>
	            		</sec:authorize>
	            		<sec:authorize access="hasAnyAuthority('OPTEAM', 'CSTEAM')">
	            		<li><a href="#mmth-branchContents" data-value="HWOTP_INFO_MENU" data-target="#branch-HWOTP_INFO_MENU"><fmt:message key="branch.hwotp.info" /></a></li>
	            		</sec:authorize>
	            	</ul>
	            </div>
            <!-- /.box-body -->
        	</div>
        </div>
        <!-- /.col -->

		<div class="col-md-6" id="mmth-branchContents">
       		<tag:detailsBox titleKeyDetails="branch.hwotp.manager" detailBoxId="branch-HWOTP_MANAGER_MENU" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true" detailBoxFormId="HWOTP_MANAGER_MENU" excludeFooter="true">
       			<tag:formItem formField="false" hasBodyContents="true" valueKey="operation" labelKey="branch.operation">
           			<select class="form-control" id="operationType">
						<option value="1" selected="selected"><fmt:message key="branch.operation.issue">
						<fmt:param>1</fmt:param>
						</fmt:message></option>
						<option value="2"><fmt:message key="branch.operation.reissue">
						<fmt:param>2</fmt:param>
						</fmt:message></option>
				    	<option value="3"><fmt:message key="branch.operation.disuse">
						<fmt:param>3</fmt:param>
						</fmt:message></option>
					</select>
				</tag:formItem>
           			
				<div class="form-group " id="ctx-operationUser">
       				<label class="col-md-5 col-sm-6 control-label" for="comment"><fmt:message key="branch.user" /></label>
       				<div class="col-md-6 col-sm-5">
       					<input type="text" class="mmth-form form-control formField" name="operationUser" id="operationUser"/>
       				</div>
<%--        				<button class="btn btn-primary mmth-btn" type="button" id="btn-usercheck"><fmt:message key="branch.btn.ok" /></button> --%>
<!--        				<div class="col-md-3 col-sm-5"> -->
<!--        					<input type="text" placeholder="(Account name)" class="mmth-form form-control formField" name="operationAccountName" id="operationAccountName"/> -->
<!--        				</div> -->
       			</div>
       			<tag:formItem formField="true" valueKey="operationOtpsn" labelKey="branch.otpsn" />
       			<div class="form-group " id="ctx-comment">
       				<label class="col-md-5 col-sm-6 control-label" for="comment"><fmt:message key="branch.info.comment" /></label>
       				<div class="col-md-6 col-sm-5">
       					<p><textarea style="resize: none;" class="mmth-form form-control formField" id="comment" placeholder="Input some text." maxlength="60"></textarea></p>
       				</div>
       			</div>
			
				<span class="input-group-btn">
					<button class="btn btn-primary mmth-btn" type="button" id="btn-hwotpManager"><fmt:message key="branch.btn.ok" /></button>
				</span>
				<hr />
         		<tag:formItem formField="true" valueKey="issuanceDate" labelKey="branch.operation.issuance.date" readonly="true" />
         		<tag:formItem formField="true" valueKey="disuseDate" labelKey="branch.operation.disuse.date" readonly="true" />
       		</tag:detailsBox>
       		
       		<tag:detailsBox titleKeyDetails="branch.hwotp.lost" detailBoxId="branch-HWOTP_LOST_MENU" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true" detailBoxFormId="HWOTP_LOST_MENU" excludeFooter="true">
       			<tag:formItem formField="false" hasBodyContents="true" valueKey="lost" labelKey="branch.operation">
           			<select class="form-control" id="lostType">
						<option value="1" selected="selected"><fmt:message key="branch.lost.setlost">
						<fmt:param>1</fmt:param>
						</fmt:message></option>
						<option value="2"><fmt:message key="branch.lost.clearlost">
						<fmt:param>2</fmt:param>
						</fmt:message></option>
					</select>
				</tag:formItem>
           		<tag:formItem formField="true" valueKey="lostUser" labelKey="branch.user" />
				<span class="input-group-btn">
					<button class="btn btn-primary mmth-btn" type="button" id="btn-hwotpLost">
					<fmt:message key="branch.btn.ok" />
					</button>
			    </span>
				<hr />
		        <tag:formItem formField="true" valueKey="lostDate" labelKey="branch.lost.date" readonly="true" />
				<p style="margin:10px 20px 10px 0;"><fmt:message key="branch.errormsg.swuser.clear.desc" /></p>
       		</tag:detailsBox>

       		<tag:detailsBox titleKeyDetails="branch.hwotp.resetcnt" detailBoxId="branch-HWOTP_RESET_COUNT_MENU" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true" detailBoxFormId="HWOTP_RESET_COUNT_MENU" excludeFooter="true">
      			<tag:formItem formField="true" valueKey="resetUser" labelKey="branch.user" />
				<span class="input-group-btn">
					<button class="btn btn-primary mmth-btn" type="button" id="btn-hwotpResetCnt">
						<fmt:message key="branch.btn.ok" />
					</button>
				</span>
       		</tag:detailsBox>
       		
       		<tag:detailsBox titleKeyDetails="branch.hwotp.sync" detailBoxId="branch-HWOTP_SYNC_MENU" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true" detailBoxFormId="HWOTP_SYNC_MENU" excludeFooter="true">
           			<tag:formItem formField="true" valueKey="syncUser" labelKey="branch.user" />
           			<tag:formItem formField="true" valueKey="otp" labelKey="branch.sync.otp" />
           			<tag:formItem formField="true" valueKey="nextotp" labelKey="branch.sync.nextotp" />
					<span class="input-group-btn">
						<button class="btn btn-primary mmth-btn" type="button" id="btn-hwotpSync">
						<fmt:message key="branch.btn.sync" />
						</button>
					</span>
       		</tag:detailsBox>
       		
       		<tag:detailsBox titleKeyDetails="branch.hwotp.info" detailBoxId="branch-HWOTP_INFO_MENU" useForm="true" useDelete="false" excludeCollapse="true" excludeEdit="true" detailBoxFormId="HWOTP_INFO_MENU" excludeFooter="true">
       			<tag:formItem formField="true" valueKey="infoUser" labelKey="branch.user" />
				<span class="input-group-btn">
					<button class="btn btn-primary mmth-btn" type="button" id="btn-hwotpInfo">
						<fmt:message key="btn.query" />
					</button>
				</span>
				<hr />
				<div class="box-body">			
					<table class="table table-bordered mmth-pageList tg">
						<tr style="background:#ecf0f5;">
							<th class="text-center text-bold tg-0pky" rowspan="2"><fmt:message key="branch.info.otptype" /></th>
							<th class="text-center text-bold tg-0pky" rowspan="2"><fmt:message key="branch.info.otpsn" /></th>
							<th class="text-center text-bold tg-0pky" rowspan="2"><fmt:message key="branch.info.issuedate" /></th>
							<th class="text-center text-bold tg-0pky" rowspan="2"><fmt:message key="branch.info.branchId"/></th>
							<th class="text-center text-bold tg-0pky" rowspan="2"><fmt:message key="branch.info.status" /></th>
							<th class="text-center text-bold tg-0pky" colspan="2"><fmt:message key="branch.info.failcnt" /></th>
							<th class="text-center text-bold tg-0pky" rowspan="2"><fmt:message key="branch.info.comment" /></th>
						</tr>
						<tr style="background:#ecf0f5;">
							<th class="text-center text-bold tg-0pky"><fmt:message key="branch.info.failcnt.otppin" /></th>
							<th class="text-center text-bold tg-0pky"><fmt:message key="branch.info.failcnt.otp" /></th>
						</tr>
						<tr>
							<td class="tg-0pky"><span id="infoOtpType">-</span></td>
							<td class="tg-0pky"><span id="infoOtpsn">-</span></td>
							<td class="tg-0pky"><span id="infoIssueDate">-</span></td>
							<td class="tg-0pky"><span id="infoBranchId">-</span></td>
							<td class="tg-0pky"><span id="infoStatus">-</span></td>
							<td class="tg-0pky"><span id="infoOtpPin">-</span></td>
							<td class="tg-0pky"><span id="infoOtpFailCnt">-</span></td>
							<td class="tg-0pky"><span id="infoComment">-</span></td>
						</tr>
					</table>
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