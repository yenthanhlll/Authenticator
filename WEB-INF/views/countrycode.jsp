<%-- 
	#
	#	Audit alarms page
	#
--%><%@ include file="/WEB-INF/views/common/def.jsp" %>
<tag:page onload="init" useDateRange="true">
<jsp:attribute name="javascripts">
<script type="text/javascript">
//cell attribute
MMTHUtils.view.pagination.cellAttrFunc = function(rowData, cellNum, rowNum) {
	var attr = {};
	if (cellNum == 1) {
		attr['class'] = 'text-muted text-center';
	
	} else {
		attr['class'] = 'text-center';
	}

	return attr;
};
//Tabel contents generations
MMTHUtils.view.pagination.cellContentFuncs = new Array();

// col0 : idx
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.countryName;
});

// col1 : username
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.reg;
});

// col2 : issueType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.dereg;
});

// col3 : authType
MMTHUtils.view.pagination.cellContentFuncs.push(function(rowData, cellNum, rowNum){
	return rowData.auth;
});



function init() {
	// init url을 호출하여 페이지 리스트와 수치 데이터를 조회한다.
	var caller = ajaxUtil('/web/manager/countrycode/rest/init');
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			if (resp.data.result) {
				setTableContents(resp.data.result);
			}
		}
	});
	caller.execute();	
}

function getDateRangeResult(){
	var $dr = $('.mmth-dateRangeFieldFilter').find('input.dateRangePicker');

	var param = {};

	if ($dr && !$dr.val().isEmpty()) {
		var dateRange = $dr.data('daterangepicker');
		param[$dr.data('startkey')] = dateRange.startDate._d.formatDate();
		param[$dr.data('endkey')] = dateRange.endDate._d.formatDate();
	}
	
	var caller = ajaxUtil('/web/manager/countrycode/rest/search');
	caller.setData(param);
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		} else if(!resp.hasMessages){
			if (resp.data.result) {
				setTableContents(resp.data.result);
			}
		}
	});
	caller.execute();
}


function setTableContents(pageContents){

	var tableHandler = table('country_code_stats-body').removeAllRows();
	
	var contents = pageContents.contents;
	tableHandler.addRows(contents, MMTHUtils.view.pagination.cellContentFuncs, null, MMTHUtils.view.pagination.cellAttrFunc);
	setDateRange(pageContents);
	
	var reg = 0, dereg = 0, auth = 0;
	for (var i = 0, len = contents.length; i < len; i++){
		reg += contents[i].reg;
		dereg += contents[i].dereg;
		auth += contents[i].auth;
	}
	
	$('#summary-reg').html(''+ reg);
	$('#summary-dereg').html(''+ dereg);
	$('#summary-auth').html(''+ auth);
}
</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1><fmt:message key="countrycode"/> <small><fmt:message key="countrycode.desc"/></small></h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.log"/></li>
			<li class="active"><i class="fa fa-globe"></i> <fmt:message key="nav.countrycode" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<div class="col-md-12">
        	<div class="box mmth-box">
            	<div class="box-header with-border">
              	<h3 class="box-title"><fmt:message key="countrycode.result"/></h3>
				<table class="table mmth-pageSearch-field">
					<colgroup><col width="40%"><col width="60%"></colgroup>
					<thead>
						<tr>
							<th><tag:dateRangeFieldFilter startDateFieldKey="startDate" labelMsgKey="common.regDate" endDateFieldKey="endDate" id="issuance-dateRange" /></th>
							<th></th>
						</tr>
					</thead>
				</table>
              <!-- /.box-tools -->
            </div>
            <!-- /.box-header -->
            
            
            <div class="box-body">
            	<table class="table table-bordered table-hover mmth-pageList" id="country_code_stats">
            		<thead>
            			<tr> 
							<th><fmt:message key="countrycode.country" /></th>
							<th><fmt:message key="countrycode.issuance" /></th>
							<th><fmt:message key="countrycode.discard"/></th>
							<th><fmt:message key="countrycode.authentication"/></th>
						</tr>
            		</thead>
            		<tbody id="country_code_stats-body"></tbody>
            		<tbody id="country_code_stats-summary" class="text-muted">
            			<tr>
            				<td class="text-center"><fmt:message key="countrycode.summary"/></td>
            				<td id="summary-reg" class="text-center"></td>
            				<td id="summary-dereg" class="text-center"></td>
            				<td id="summary-auth" class="text-center"></td>
            			</tr>
            		</tbody>  
            	</table>
            </div>
          </div> 
          <!-- /. box -->
        </div>
		
	</section>
	<!-- /.content -->

</jsp:body>
</tag:page>