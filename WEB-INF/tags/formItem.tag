<%--
	//	Tag for form input group
	- valueKey* : input form ID 및 Name
	- formField* : 서버단으로 넘어가는 필드 데이터 (수정 가능한 필드데이터)
	- labelKey : 라벨 메시지 키
	- disabled (if formField) : 수정 불가능한 필드
	
	- tooltipKey : tooltip 메시지 키

	- hidden : hidden 타입 필드
	- checkbox : checkbox 타입 필드
	- textarea : textarea 타입필드
	- dateTime : 시간 형식 YYYY-MM-DD hh:mm:ss 형식 (long to formatDate)
	- maxLength : text 필드 maxLength
	- passwdType : input type password 여부

--%><%@include file="/WEB-INF/tags/include/def.tagf"%><%--
--%><%@attribute name="valueKey" required="true" type="java.lang.String"%><%--
--%><%@attribute name="formField" required="true" type="java.lang.Boolean"%><%--
--%><%@attribute name="labelKey" type="java.lang.String"%><%--
--%><%@attribute name="tooltipKey" type="java.lang.String"%><%--
--%><%@attribute name="readonly" type="java.lang.Boolean"%><%--
--%><%@attribute name="disabled" type="java.lang.Boolean"%><%--
--%><%@attribute name="placeholderKey" type="java.lang.String"%><%--
--%><%@attribute name="clazzName" type="java.lang.String"%><%--
--%><%@attribute name="hasBodyContents" type="java.lang.Boolean"%><%--

--%><%@attribute name="hidden" type="java.lang.Boolean"%><%--
--%><%@attribute name="password" type="java.lang.Boolean"%><%--
--%><%@attribute name="selectOptions" type="java.util.List"%><%--
--%><%@attribute name="selectOptionsRaw" type="java.util.List"%><%--
--%><%@attribute name="selectOptionArrays" type="java.util.List"%><%--
--%><%@attribute name="selectOptionArrays2" type="java.lang.String[]"%><%--
--%><%@attribute name="selectOptionMin" type="java.lang.Integer"%><%--
--%><%@attribute name="selectOptionMax" type="java.lang.Integer"%><%--
--%><%@attribute name="selectOptionStep" type="java.lang.Integer"%><%--
--%><%@attribute name="selectOptionWrapKey" type="java.lang.String"%><%--
--%><%@attribute name="selectChangedTarget" type="java.lang.String"%><%--


--%><%@attribute name="buttonKey" type="java.lang.String"%><%--
--%><%@attribute name="textarea" type="java.lang.Boolean"%><%--
--%><%@attribute name="file" type="java.lang.Boolean"%><%--

--%><%@attribute name="maxLength" type="java.lang.Integer"%><%--
--%><%@attribute name="buttonClass" type="java.lang.String"%><%--
--%><%@attribute name="accept" type="java.lang.String"%><%--

--%><%@attribute name="checkbox" type="java.lang.Boolean"%><%--
--%><%@attribute name="rows" type="java.lang.Integer"%><%--


--%><c:choose><%-- 
	
	// hidden
	--%><c:when test="${not empty hidden and hidden}"><%-- 
		--%><input type="hidden" class="mmth-form form-control<c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}"/><%--
	--%></c:when><%--
	--%><c:otherwise><%--
	
		--%><div class="form-group ${clazzName}" id="ctx-${valueKey }"><%-- 
			--%><label class="col-md-5 col-sm-6 control-label" for="${valueKey}"><%-- 
				--%><fmt:message key="${labelKey}" /><%--
				--%><c:if test="${not empty tooltipKey}"><%-- 
					--%> &nbsp;<span data-toggle="tooltip" data-container="body" data-html="true" data-placement="bottom" title="<fmt:message key="${tooltipKey}"/>"><i class="fa fa-info-circle" ></i></span><%--
				--%></c:if><%--
			--%></label><%-- 
			--%><div class="col-md-6 col-sm-5"><%--
				
				--%><c:choose><%--
				
					--%><c:when test="${not empty hasBodyContents and hasBodyContents}"><%--
						--%><jsp:doBody/><%--
					--%></c:when><%--
					
					// password
					--%><c:when test="${not empty password and password}"><%--
						 --%><input type="password" autocomplete="off" class="mmth-form form-control<%-- 
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" <%--
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${not empty placeholderKey}"> placeholder="<fmt:message key="${placeholderKey}"/>"</c:if> <%--
							--%><c:if test="${not empty maxLength}"> maxlength="${maxLength}"</c:if>/><%-- 
					--%></c:when><%--
					
					// select
					--%><c:when test="${not empty selectOptions}"><%-- 
						--%><select class="mmth-form form-control<%--
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" data-target="${selectChangedTarget}"<%--
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${disabled}"> data-disabled</c:if>> <%--  
							--%><c:forEach items="${selectOptions}" var="opt"><%-- 
							--%><option value="${opt.key}"><fmt:message key="${opt.value}"/></option></c:forEach><%-- 
						--%></select><%-- 
					--%></c:when><%--
					
					// select
					--%><c:when test="${not empty selectOptionsRaw}"><%-- 
						--%><select class="mmth-form form-control<%--
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" data-target="${selectChangedTarget}"<%--
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${disabled}"> data-disabled</c:if>> <%--  
							--%><c:forEach items="${selectOptionsRaw}" var="opt"><%-- 
							--%><option value="${opt.key}">${opt.value}</option></c:forEach><%-- 
						--%></select><%-- 
					--%></c:when><%--
					
					// select
					--%><c:when test="${not empty selectOptionArrays}"><%-- 
						--%><select class="mmth-form form-control<%--
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" data-target="${selectChangedTarget}"<%--
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${disabled}"> data-disabled</c:if>> <%--  
							--%><c:forEach items="${selectOptionArrays}" var="opt"><%-- 
							--%><option value="${opt}"><c:set var="optLable" value="${opt}"/><%--
								--%><c:if test="${not empty selectOptionWrapKey }"><c:set var="optLable"><fmt:message key="${selectOptionWrapKey }"><fmt:param>${opt}</fmt:param></fmt:message></c:set></c:if><%-- 
								--%>${optLable}<%--
							 --%></option><%-- 
							--%></c:forEach><%-- 
						--%></select><%-- 
					--%></c:when><%--
					
					// select
					--%><c:when test="${not empty selectOptionArrays2}"><%-- 
						--%><select class="mmth-form form-control<%--
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" data-target="${selectChangedTarget}"<%--
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${disabled}"> data-disabled</c:if>> <%--  
							--%><c:forEach items="${selectOptionArrays2}" var="opt"><%-- 
							--%><option value="${opt}"><c:set var="optLable" value="${opt}"/><%--
								--%><c:if test="${not empty selectOptionWrapKey }"><c:set var="optLable"><fmt:message key="${selectOptionWrapKey }"><fmt:param>${opt}</fmt:param></fmt:message></c:set></c:if><%-- 
								--%>${optLable}<%--
							 --%></option><%-- 
							--%></c:forEach><%-- 
						--%></select><%-- 
					--%></c:when><%--
					
					// select
					--%><c:when test="${not empty selectOptionMin and not empty selectOptionMax}"><%-- 
						--%><select class="mmth-form form-control<%--
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}"  data-target="${selectChangedTarget}"<%--
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${disabled}"> data-disabled</c:if>> <%--  
							--%><c:forEach begin="${selectOptionMin }" end="${selectOptionMax }" step="${not empty selectOptionStep ? selectOptionStep : 1 }" var="opt"><%--
							--%><option value="${opt}"><c:set var="optLable" value="${opt}"/><%--
								--%><c:if test="${not empty selectOptionWrapKey }"><c:set var="optLable"><fmt:message key="${selectOptionWrapKey }"><fmt:param>${opt}</fmt:param></fmt:message></c:set></c:if><%-- 
								--%>${optLable}<%--
							 --%></option><%-- 
							--%></c:forEach><%-- 
						--%></select><%-- 
					--%></c:when><%--
					
					// Button
					--%><c:when test="${not empty buttonKey}"><%-- 
						--%><button type="button" id="${valueKey}" class="btn ${not empty buttonClass ? buttonClass : 'btn-default'} btn-sm"><fmt:message key="${buttonKey}"/></button><%-- 
					--%></c:when><%--
					
						// File
					--%><c:when test="${not empty file and file}"><%-- 
						--%><input type="file" class="mmth-form form-control<%-- 
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" <%-- 
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${disabled}"> data-disabled</c:if> <%--
							--%><c:if test="${not empty accept}"> accept="${accept }"</c:if> <%--
							--%><c:if test="${not empty placeholderKey}"> placeholder="<fmt:message key="${placeholderKey}"/>"</c:if> <%--
							--%> /><%-- 
					--%></c:when><%--
					
					// TEXT AREA
					--%><c:when test="${not empty textarea and textarea}"><%-- 
					--%><textarea class="mmth-form form-control<%-- 
						--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" <%-- 
						--%> rows="${not empty rows ? rows : 5}"<%--
						--%><c:if test="${not empty maxLength}"> maxlength="${maxLength}"</c:if><%--
						--%><c:if test="${readonly}"> data-readonly</c:if> <%--
						--%><c:if test="${disabled}"> data-disabled</c:if>></textarea><%--
					--%></c:when><%--
					
					// text
					--%><c:otherwise><%-- 
						--%><input type="text" class="mmth-form form-control<%-- 
							--%><c:if test="${formField}"> formField</c:if>" name="${valueKey}" id="${valueKey}" <%-- 
							--%><c:if test="${readonly}"> data-readonly</c:if> <%--
							--%><c:if test="${disabled}"> data-disabled</c:if> <%--
							--%><c:if test="${not empty placeholderKey}"> placeholder="<fmt:message key="${placeholderKey}"/>"</c:if> <%--
							--%><c:if test="${not empty maxLength}"> maxlength="${maxLength}"</c:if> /><%-- 
					--%></c:otherwise><%--				
				--%></c:choose><%--	
				--%><span class="help-block"></span><%-- 
			--%></div><%-- 
		--%></div><%--
	 
	--%></c:otherwise><%--
--%></c:choose>
