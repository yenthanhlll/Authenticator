<%--
	//	Tag for column field filter tag

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="id" required="true" type="java.lang.String"%><%--
--%><%@attribute name="labelMsgKey" required="true" type="java.lang.String"%><%--
--%><%@attribute name="startDateFieldKey" required="true" type="java.lang.String"%><%--
--%><%@attribute name="endDateFieldKey" required="true" type="java.lang.String"%><%--
--%><div class="input-group input-group-sm mmth-dateRangeFieldFilter"><%-- 
	--%><div class="input-group-addon bg-gray"><fmt:message key="${labelMsgKey}"/>&nbsp; <i class="fa fa-calendar"></i></div><%-- 
	--%><input type="text" class="form-control dateRangePicker" id="${id}" aria-describedby="addon-calendar" data-startkey="${startDateFieldKey}" data-endkey="${endDateFieldKey}"><%-- 
--%></div>

