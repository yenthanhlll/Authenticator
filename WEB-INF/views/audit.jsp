<%-- 
	#
	#	Audit alarms page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %> 
<tag:page onload="init"  viewUriPrefix="/web/manager/audit/rest" useDateRange="true" useIcheck="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
//cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};
	if (cellNum == 3) {
		attr['class'] = 'lead text-center';
	} else if (cellNum == 4) {
		attr['class'] = 'word-break';
	} else if (cellNum == 6) {
		attr['class'] = 'text-center';
		attr['data-toggle'] = 'tooltip';
		
		if (rowData.ack == '${NONE}') {
			attr['title'] = '<fmt:message key="audit.label.noAck.desc"/>';
		} else {
			attr['title'] = rowData.ackDesc;
		}
		
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

// col1 : auditType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.auditTypeDesc;
});

// col2 : actionType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.actionTypeDesc;
});

// col3 : alarmLevel
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	var className;
	if (rowData.alarmLevel == '${INFORMATION}') {
		className = 'text-light-blue';
	} else if (rowData.alarmLevel == '${URGENT}') {
		className = 'text-yellow';
	} else if (rowData.alarmLevel == '${CRITICAL}') {
		className = 'text-red';
	} else if (rowData.alarmLevel == '${LIFE_SAFETY}') {
		className = 'text-purple';
	} else {
		className = 'text-muted';
	}

	return '<span class="'+className+'"  data-toggle="tooltip" title="'+rowData.alarmLevelDesc+'"><i class="fa fa-flag"></i></span>';
});

// col4 : description .word-break
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	var desc ='<p>' +rowData.description+ '</p>'
	
	if (rowData.ack == '${ACK}') {
		desc += ('<div class="text-right ackDesc"><em><fmt:message key="audit.ack.desc"><fmt:param>'+rowData.ackAdmin+'</fmt:param><fmt:param>'+rowData.tsAck+'</fmt:param></fmt:message></em></div>');
	}
	return desc;
});

// col5 : tsReg
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.tsReg;
});

//col6 : ack
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	
	if (rowData.ack == '${NONE}') {
		return '<input type="checkbox" data-ack="true" data-id="' +rowData.id + '"/>';	
	} else {
		return '<span class="checked_square"><i class="fa fa-check"></i></span>';
	}
});

function init() {

	$('#btn-ackAll').on('click', function(){
		var ids = []; 
		$('input[type=checkbox][data-ack="true"]').each(function(){
			ids.push($(this).data('id'));
		});
		
		if (ids.length < 1) {
			BootstrapDialog.alert({
			    type: BootstrapDialog.TYPE_WARNING,
			    message: '<fmt:message key="audit.noAck.none"/>'
			});
			return;
		}
		
		acknowledgeAlarms(ids);
	});
	

}

function setTableContentDataImp(pageContents){
	
	if (pageContents.numOfContents > 0) {
		$('input[type=checkbox][data-ack]').iCheck({
			 checkboxClass: 'icheckbox_square-' + MMTHUtils.view.skinName,
		}).on('ifChecked', function(event){
			acknowledgeAlarms([$(event.currentTarget).data('id')]);
		});	
		
		$('#btn-ackAll').show();
	} else {
		$('#btn-ackAll').hide();
	}
	
}
function initRestPageImp(data){
	setLatestTableData(data);
	setNoAckCount(data);
}

function setLatestTableData(data){
	var tableHandler = table('audit-latest-body').removeAllRows();
	
	if (data.latest) {
		var cellAttrFunc = function(rowData, cellNum, rowNum) {
			var attr = {};
			if (cellNum == 2) {
				attr['class'] = 'lead text-center';
			}if (cellNum == 3) {
				attr['class'] = 'word-break';
			} else if (cellNum == 5) {
				attr['class'] = 'text-center';
				attr['data-toggle'] = 'tooltip';
				
				if (rowData.ack == '${NONE}') {
					attr['title'] = '<fmt:message key="audit.label.noAck.desc"/>';
				} else {
					attr['title'] = rowData.ackDesc;
				}
			} else {
				attr['class'] = 'text-center';
			}

			return attr;
		};
		var latestCells = MMTHUtils.view.pagination.cellContentFuncs.slice(1,6);
		// 테이블 데이터 채우기
		tableHandler.addRows(data.latest, latestCells, null, cellAttrFunc);
		
		$('#audit-latest-body').removeClass('hidden');
		$('#audit-latest-body-Message').addClass('hidden');
	} else {
		$('#audit-latest-body').addClass('hidden');
		$('#audit-latest-body-Message').removeClass('hidden');
	}
}


function acknowledgeAlarms(ids) {

	var caller = ajaxUtil(MMTHUtils.view.urlPrefix + '/acknowledgeAlarms');
	caller.setData({'ids' : ids});
	caller.setResponseDataHandler(function(resp){

		if (resp.hasMessages) {
			if (resp.generalMessages)
				handleGeneralMessage(resp.generalMessages);
		} else {
			
			if (resp.data.noAckCnt) {
				setNoAckCount(resp.data);
			}
			
			if (resp.data.latest){
				setLatestTableData(resp.data);
			}
			
			getPageList(MMTHUtils.view.pagination.currentPage);
		}
	});
	caller.execute();
}

function setNoAckCount(data){
	if (data.noAckCnt) {
		$('#unAcknowledgeCnt').text(data.noAckCnt.noAck);
	}
	

}

</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1><fmt:message key="audit"/> <small><fmt:message key="audit.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.env"/></li>
			<li class="active"><i class="fa fa-flag"></i> <fmt:message key="nav.env.audit" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="row">
			<div class="col-md-12">
				<!-- Default box -->
				<div class="box mmth-box-light">
					<div class="box-header">
						<h3 class="box-title"><fmt:message key="audit.latest"/></h3>
					</div>
					<div class="box-body">
					<table id="audit-latest" class="table table-bordered table-condensed mmth-latest">
						<thead>
							<tr>
								<th class="text-center col-md-2"><fmt:message key="audit.label.auditType" /></th>
								<th class="text-center col-md-2"><fmt:message key="audit.label.actionType" /></th>
								<th class="text-center col-md-1"><fmt:message key="audit.label.alarmLevel" /></th>
								<th class="text-center col-md-6"><fmt:message key="common.description" /></th>
								<th class="text-center col-md-1"><fmt:message key="common.regDateTime" /></th>
							</tr>
						</thead>
						<tbody id="audit-latest-body"></tbody>
						<tbody id="audit-latest-body-Message" class="text-muted">
							<tr><td colspan="6"><fmt:message key="audit.noList"/></td></tr>
						</tbody>
					</table>
					</div>
				</div>
				<!-- /.box -->
			</div>
		</div> 
		
		<div class="row">
		
        <div class="col-md-2">

        	<div class="box box-solid">
            <div class="box-body no-padding">
            	<tag:listFilter key="ack" id="mmth-ackList" clazzNames="mmth-nav-light">
            	<li class="active">
                	<a href="#mmth-pageList" data-value="NONE">
                		<i class="fa fa-inbox" data-toggle="tooltip" title="<fmt:message key="audit.noAck.desc"/>"></i> <fmt:message key="audit.noAck"/>
                		<span id="unAcknowledgeCnt" class="label mmth-bg-light pull-right" data-toggle="tooltip" title="<fmt:message key="audit.noAck.count"/>"></span>
                	</a>
               	</li>
            	</tag:listFilter>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        

          <div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title"><fmt:message key="audit.auditType"/></h3>

              <div class="box-tools">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
              </div>
            </div>
            <div class="box-body no-padding">
            	<tag:listFilter key="auditType" id="mmth-auditTypeList" clazzNames="mmth-nav-light" useAll="true">
            		<tag:filterItem value="SYSTEM" msgKey="AuditAlarmTypes.SYSTEM" iconClass="fa fa-window-maximize"/>
            		<tag:filterItem value="ADMIN" msgKey="AuditAlarmTypes.ADMIN" iconClass="fa fa-black-tie"/>
<%--             		<tag:filterItem value="USER" msgKey="AuditAlarmTypes.USER" iconClass="fa fa-users"/> --%>
            		<tag:filterItem value="TOKEN" msgKey="AuditAlarmTypes.TOKEN" iconClass="fa fa-key"/>
<%--             		<tag:filterItem value="METADATA" msgKey="AuditAlarmTypes.METADATA" iconClass="fa fa-certificate"/> --%>
<%--             		<tag:filterItem value="FACET" msgKey="AuditAlarmTypes.FACET" iconClass="fa fa-shield"/> --%>
<%--             		<tag:filterItem value="APP_AGENT" msgKey="AuditAlarmTypes.APP_AGENT" iconClass="fa fa-android"/> --%>
            		<tag:filterItem value="SYSTEM_SETTING" msgKey="AuditAlarmTypes.SYSTEM_SETTING" iconClass="fa fa-cogs"/>
<%--             		<tag:filterItem value="PUBLISHER" msgKey="AuditAlarmTypes.PUBLISHER" iconClass="fa fa-wifi"/> --%>
<%--             		<tag:filterItem value="EXTERNAL_API" msgKey="AuditAlarmTypes.EXTERNAL_API" iconClass="fa fa-external-link"/> --%>
            	</tag:listFilter>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /. box -->

        </div>
        <!-- /.col -->
        
        
        
		<div class="col-md-10">
        	<div class="box mmth-box">
            	<div class="box-header with-border">
              	<h3 class="box-title"><fmt:message key="audit.list"/></h3>
				<table class="table mmth-pageSearch-field ">
					<colgroup><col width="40%"><col width="30%"><col width="30%"></colgroup>
					<thead>
						<tr>
							<th></th>
							<th><tag:dateRangeFieldFilter startDateFieldKey="startDate" labelMsgKey="common.regDate" endDateFieldKey="endDate" id="audit-dateRange" /></th>
							<th><tag:singleSearchFilter fieldKey="description" msgKey="common.description" id="searchField" /></th>
						</tr>
					</thead>
				</table>
              <!-- /.box-tools -->
            </div>
            <!-- /.box-header -->
            <tag:pagination noListMsgKey="audit.noList" cols="7">
				<th class="col-md-1 col-sm-1"><fmt:message key="common.idxLabel" /></th>
				<th class="col-md-1 col-sm-2"><fmt:message key="audit.label.auditType" /></th>
				<th class="col-md-1 col-sm-1"><fmt:message key="audit.label.actionType" /></th>
				<th class="col-md-1 col-sm-2">
					<tag:pageFilter wrappedKey="audit.list.alarmLevel" key="alarmLevel" id="audit-alarmLevel"><c:forEach items="${AlarmLevels}" var="type">
						<tag:filterItem wrappedKey="audit.list.alarmLevel" value="${type.key}" msgKey="${type.value }"/></c:forEach>
					</tag:pageFilter>
				</th>
				<th class="col-md-6 col-sm-4"><fmt:message key="common.description" /></th>
				<th class="col-md-1 col-sm-1"><fmt:message key="common.regDateTime" /></th>
				<th class="col-md-1 col-sm-1"><button id="btn-ackAll" class="checked_square" data-toggle="tooltip" title="<fmt:message key="audit.label.noAckAll.desc"/>"><i class="fa fa-check"></i></button></th>
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