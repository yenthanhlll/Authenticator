<%--
	//	Tag for Page list and pagination

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="cols" required="true" type="java.lang.Integer"%><%--
--%><%@attribute name="noListMsgKey" required="true" type="java.lang.String"%><%--
--%><div class="box-body"><%-- 
	--%><table class="table table-bordered table-hover mmth-pageList" id="mmth-pageList"><%-- 
		--%><thead><%-- 
			--%><tr><%-- 
				--%><jsp:doBody/><%-- 
			--%></tr><%-- 
		--%></thead><%-- 
		--%><tbody id="mmth-page-body"></tbody><%-- 
		--%><tbody id="mmth-list-empty-Message" class="text-muted hidden"><%-- 
			--%><tr><%-- 
				--%><td colspan="${cols}"><fmt:message key="${noListMsgKey }"/></td><%-- 
			--%></tr><%-- 
		--%></tbody><%-- 
	--%></table><%-- 
--%></div><!-- /.box-body --><%-- 
--%><div id="paginationFooter" class="box-footer clearfix hidden"><%-- 
	--%><ul class="pagination pagination-sm no-margin pull-right" id="mmth-page-pagination"></ul><%-- 
--%></div><!-- /.box-footer-->