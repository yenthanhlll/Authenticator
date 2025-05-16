<%@ include file="/WEB-INF/views/common/def.jsp" %>
<c:set var="context_path" value="${requestScope['javax.servlet.forward.context_path']}"/>
<tag:page onload="initSql">
<jsp:attribute name="styles">
<style type="text/css">
#query {
	margin-bottom: 20px;
}
</style>
</jsp:attribute>
<jsp:attribute name="javascripts">
<script type="text/javascript">

function sendQuery(){
	
	var query = $('#query').val();
	
	if (query.isEmpty()) {
		BootstrapDialog.confirm({
			type: BootstrapDialog.TYPE_WARNING,
		    message:'The Query is not be empty/null.'
		});
		return;
	}
	
	sendAjaxQuery('${context_path}/web/manager/sql/rest/query', query);

}

function executeQuery(){
	
	var query = $('#query').val();
	
	if (query.isEmpty()) {
		BootstrapDialog.confirm({
			type: BootstrapDialog.TYPE_WARNING,
		    message:'The Query is not be empty/null.'
		});
		return;
	}
	
	sendAjaxQuery('${context_path}/web/manager/sql/rest/execute', query);
}

function sendAjaxQuery(url, query){
	hideResult();
	
	var caller = ajaxUtil(url);
	caller.setData({'query' : query});
	caller.setResponseDataHandler(function(resp){
		
		if (resp.hasMessages && resp.generalMessages) {
			handleGeneralMessage(resp.generalMessages);
		}

		if (!resp.hasMessages) {
			generateData(resp.data.result);
		}
	});
	caller.execute();
}

function generateData(json){
	
	if (json.resultSize > 0) {

		var table = byId('resultTable');
		var haederThead = document.createElement('thead');
		var headerTr = document.createElement('tr');
		var header = json.headers;
		var colLen = header.length;
		var th;
		for (var i=0; i < colLen; i++) {
			th = document.createElement('th');
			th.innerHTML = header[i];
			haederThead.appendChild(headerTr).appendChild(th);
		}
		table.appendChild(haederThead);
		
		var contents = json.data;
		var tbody = document.createElement('tbody');
		var td, tr;
		for (var i=0, len = contents.length; i < len; i++) {
			tr = document.createElement('tr');
			var row = contents[i];
			for (var j=0; j < colLen; j++) {
				td = document.createElement('td');
				td.innerHTML = row[j];
				tr.appendChild(td);
			}
			table.appendChild(tbody).appendChild(tr);
		}
	}

	if (json.updateResult > 0) {
		$('#result-size').text(json.updateResult);	
	} else {
		$('#result-size').text(json.resultSize);
	}	

	showResult();
}

function showResult() {
	$('#resultBox').show();
}

function hideResult(){
	$('#resultBox').hide();
	$('#resultTable').html('');
	$('#result-size').text('');
	removeFieldMessage('query');
}

function initSql(){
	hideResult();
	$('#queryBtn').on('click', sendQuery);
	$('#executeBtn').on('click', executeQuery);
	
}

</script>
</jsp:attribute>
<jsp:body>
  <!-- =============================================== -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			<fmt:message key="sql" /> <small><fmt:message key="sql.desc" /></small>
		</h1>
		<ol class="breadcrumb">
			<li class="active"><fmt:message key="nav.title.env"/></li>
			<li class="active"><i class="fa fa-database"></i> <fmt:message key="nav.env.sql" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">

		<!-- Default box -->
		<div class="box">
			<div class="box-header with-border">
				<h3 class="box-title"><fmt:message key="sql.query"/></h3>

				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
						<i class="fa fa-minus"></i>
					</button>
				</div>
			</div>
			<div class="box-body"  id="ctx-query">
				<textarea rows="5" class="col-xs-12" name="query" id="query"></textarea>
				<button type="button" class="btn btn-primary mmth-btn" name="queryBtn" id="queryBtn"><fmt:message key="btn.query"/></button>
				<button type="button" class="btn btn-warning" name="executeBtn" id="executeBtn"><fmt:message key="btn.execute"/></button>
				<div class="help-block"></div>
			</div>
			<!-- /.box-footer-->
		</div>
		<!-- /.box -->
		
		<div class="box" id="resultBox">
			<div class="box-header with-border">
				<h3 class="box-title"><fmt:message key="sql.result"/></h3>
			</div>
			<div class="box-body">
				<div class="well"><fmt:message key="sql.resultCnt"><fmt:param><em id="result-size"></em></fmt:param></fmt:message></div>
				<div>	
					<table id="resultTable" class="table table-striped table-bordered col-xs-12"></table>
				</div>		
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->

	</section>
	<!-- /.content -->
</jsp:body>
</tag:page>