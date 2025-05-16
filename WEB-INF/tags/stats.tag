<%--
	//	Tag for Stats

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="titleMsgKey" required="true" type="java.lang.String"%><%--
--%><div class="box mmth-box-light box-inline-stat mmth-statsInfo"><%-- 
	--%><div class="box-header"><%-- 
		--%><h3 class="box-title"><fmt:message key="${titleMsgKey}"/></h3><%-- 
		--%><div class="well well-sm pull-right"><%-- 
			--%><ul class="item-seperator"><jsp:doBody/></ul><%-- 
		--%></div><%-- 
	--%></div><%--
--%></div>