<%--
	//	Tag for Page list and pagination

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="msgKey" required="true" type="java.lang.String"%><%--
--%><%@attribute name="value" required="true" type="java.lang.Object"%><%--
--%><%@attribute name="wrappedKey" type="java.lang.String"%><%--
--%><%@attribute name="iconClass" required="false" type="java.lang.String"%><%--
--%><c:set var="label"><%--
--%><c:choose><%-- 
	--%><c:when test="${not empty  wrappedKey}"><fmt:message key="${wrappedKey}"><fmt:param><fmt:message key="${msgKey}"/></fmt:param></fmt:message></c:when><%-- 
	--%><c:otherwise><fmt:message key="${msgKey}"/></c:otherwise><%-- 
--%></c:choose><%-- 
--%></c:set><%--
--%><li><%-- 
	--%><a href="#mmth-pageList" data-value="${value}" data-label="${label }"><%-- 
	--%><c:if test="${not empty iconClass}"><i class="${iconClass}"></i> </c:if><%--
	--%>${label }<%-- 
	--%><jsp:doBody/></a><%-- 
--%></li>


