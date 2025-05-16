<%--
	//	Tag for nav list filter tag

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="id" required="true" type="java.lang.String"%><%--
--%><%@attribute name="key" required="true" type="java.lang.String"%><%--
--%><%@attribute name="clazzNames" type="java.lang.String"%><%--
--%><%@attribute name="useAll" type="java.lang.Boolean"%><%--
--%><%@attribute name="allIconClazz" type="java.lang.String"%><%--
--%><%@attribute name="useStats" type="java.lang.Boolean"%><%--
--%><ul class="nav nav-tab nav-pills nav-stacked mmth-listFilter ${clazzNames}<c:out value="${useStats ? ' mmth-statsInfo' : ''}"/>" data-key="${key }" id="${id}"><%-- 
	--%><c:if test="${useAll }"><li><a href="#mmth-pageList" data-value=""><i class="fa <c:out value="${ not empty allIconClazz ? allIconClazz : 'fa-list-alt'}"/>"></i> <fmt:message key="common.all"/><%-- 
		--%><c:if test="${useStats}"><span class="statsItem label bg-gray pull-right" data-key="total"></span></c:if><%-- 
	--%></a></li></c:if><%--
	--%><jsp:doBody/><%--
--%></ul>