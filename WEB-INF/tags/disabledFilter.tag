<%--
	//	Tag for column disabled filter tag

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="id" required="true" type="java.lang.String"%><%--
--%><%@attribute name="wrappedKey" required="true" type="java.lang.String"%><%--
--%><%@attribute name="enalbledKey" type="java.lang.String"%><%--
--%><%@attribute name="disabledKey" type="java.lang.String"%><%--
--%><div class="btn-group mmth-fieldFilter"><%-- 
	--%><button id="${id}" class="btn mmth-filter-btn btn-xs dropdown-toggle fieldFilter" data-key="disabled" data-value="" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><%-- 
		--%><span><fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="common.all"/></fmt:param></fmt:message></span> &nbsp;&nbsp;<span class="caret"></span><%-- 
	--%></button><%-- 
	--%><ul class="dropdown-menu"><%--
		--%><li><a href="javscript:void(0)" data-value="" data-label="<fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="common.all"/></fmt:param></fmt:message>"><fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="common.all"/></fmt:param></fmt:message></a></li><%--
		--%><li><a href="javscript:void(0)" data-value="ENABLED" data-label="<fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="${not empty enalbledKey ? enalbledKey : 'DisabledStatus.ENABLED'}"/></fmt:param></fmt:message>"><fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="${not empty enalbledKey ? enalbledKey : 'DisabledStatus.ENABLED'}"/></fmt:param></fmt:message></a></li><%--
		--%><li><a href="javscript:void(0)" data-value="DISABLED" data-label="<fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="${not empty disabledKey ? disabledKey : 'DisabledStatus.DISABLED'}"/></fmt:param></fmt:message>"><fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="${not empty disabledKey ? disabledKey : 'DisabledStatus.DISABLED'}"/></fmt:param></fmt:message></a></li><%--
	--%></ul><%-- 
--%></div>