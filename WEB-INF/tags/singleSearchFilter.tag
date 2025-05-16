<%--
	//	Tag for column field filter tag

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="id" required="true" type="java.lang.String"%><%--
--%><%@attribute name="msgKey" required="true" type="java.lang.String"%><%--
--%><%@attribute name="fieldKey" required="true" type="java.lang.String"%><%--
--%><div class="input-group input-group-sm mmth-searchFieldFilter"><%--
	--%><div class="input-group-addon searchLabel bg-gray" data-key="${fieldKey}"><fmt:message key="${msgKey}" /></div><%--
	--%><input type="text" class="form-control searchText" placeholder="<fmt:message key="common.desc.search" />"><%-- 
	--%><span class="input-group-btn"><%-- 
		--%><button class="btn mmth-filter-btn btn-sm btn-search"><fmt:message key="btn.search"/>&nbsp; <i class="fa fa-search"></i></button><%-- 
	--%></span><%-- 
--%></div>

