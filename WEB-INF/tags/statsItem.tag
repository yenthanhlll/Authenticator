<%--
	//	Tag for stats item

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="msgKey" required="true" type="java.lang.String"%><%--
--%><%@attribute name="labelColor" required="true" type="java.lang.String"%><%--
--%><%@attribute name="statsKey" required="true" type="java.lang.String"%><%--
--%><li><fmt:message key="${msgKey}"/> <span class="statsItem label label-${labelColor}" data-key="${statsKey}"></span></li>