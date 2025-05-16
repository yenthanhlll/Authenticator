<%--
	//	Tag for column list filter tag

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="id" required="true" type="java.lang.String"%><%--
--%><%@attribute name="key" required="true" type="java.lang.String"%><%--
--%><%@attribute name="wrappedKey" required="true" type="java.lang.String"%><%--
--%><div class="btn-group mmth-fieldFilter"><%-- 
	--%><button id="${id}" class="btn mmth-filter-btn btn-xs dropdown-toggle fieldFilter" data-key="${key }" data-value="" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><%-- 
		--%><span><fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="common.all"/></fmt:param></fmt:message></span> &nbsp;&nbsp;<span class="caret"></span><%-- 
	--%></button><%-- 
	--%><ul class="dropdown-menu"><%--
		--%><li><a href="javscript:void(0)" data-value="" data-label="<fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="common.all"/></fmt:param></fmt:message>"><fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="common.all"/></fmt:param></fmt:message></a></li><%--
		--%><jsp:doBody/><%--
	--%></ul><%-- 
--%></div>