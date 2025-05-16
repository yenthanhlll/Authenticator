<%--
	//	Tag for column field filter tag

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="id" required="true" type="java.lang.String"%><%--
--%><div class="input-group input-group-sm mmth-searchFieldFilter"><%--
	--%><div class="input-group-btn"><%--
		--%><button type="button" id="${id}" data-key="" class="btn bg-gray dropdown-toggle searchLabel" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span></span> &nbsp;&nbsp;<span class="caret"></span></button><%--
		--%><ul class="dropdown-menu" aria-labelledby="searchLabel"><%-- 
			--%><jsp:doBody/><%-- 
		--%></ul><%--
	--%></div><%-- 
	--%><input type="text" class="form-control searchText" placeholder="<fmt:message key="common.desc.search" />"><%-- 
	--%><span class="input-group-btn"><%-- 
		--%><button class="btn mmth-filter-btn btn-sm btn-search"><fmt:message key="btn.search"/>&nbsp; <i class="fa fa-search"></i></button><%-- 
	--%></span><%-- 
--%></div>