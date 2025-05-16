/**
 * MMTH web manager page utility java-scripts
 */

if (typeof Pace != 'undefined') {
	paceOptions = {
	  ajax: {
		    trackMethods: ['POST']
	  }
	};
}

// context variable for web manager pages..
var MMTHUtils = new Object();
MMTHUtils.bundle = new Object();
MMTHUtils.timePeriodTypes = new Object();
MMTHUtils.view = new Object();
MMTHUtils.view.skinName = 'blue';
MMTHUtils.view.pagination = new Object();
MMTHUtils.view.urlPrefix = '';
MMTHUtils.view.isPkStrType = false;
MMTHUtils.view.initUrl = function(){
	return MMTHUtils.view.urlPrefix + '/init'; 
};
MMTHUtils.view.detailsUrl = function(){
	return MMTHUtils.view.urlPrefix + '/details';
};
MMTHUtils.view.pageListUrl = function(){
	return MMTHUtils.view.urlPrefix + '/pageContents'; 
};
MMTHUtils.view.saveUrl = function(){
	return MMTHUtils.view.urlPrefix + '/save'; 
};
MMTHUtils.view.deleteUrl = function(){
	return MMTHUtils.view.urlPrefix + '/delete'
};
MMTHUtils.view.policyUrl = function(){
	return MMTHUtils.view.urlPrefix + '/policy'
};

MMTHUtils.view.exportUrl = function(){
	return MMTHUtils.view.urlPrefix + '/export'
};

//
// String prototypes
//

// 해당 문자열로 시작하는지
String.prototype.startsWith = function(str) {
    if (str.length > this.length)
        return false;
    for (var i=0; i<str.length; i++) {
        if (str.charAt(i) != this.charAt(i))
            return false;
    }
    return true;
};

// trim
String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g,"");
};

// 모든 공백 제거
String.prototype.compact = function() {
    return this.replace(/(\s*)/g,"");
};

//문자열 타입 천단위 표시
String.prototype.format = function(){
	var num = parseFloat(this);
	if(isNaN(num))
		return "0";
	return num.format();
};

//문자열 타입 천단위 제거
String.prototype.unformat = function(){
	var num = parseFloat(this.replace(/,/gi, ""));
	if(isNaN(num))
		return "0";
	return num;
};

//문자열 타입 값을 지정한 소숫점 자리수에 따른 반올림 처리
String.prototype.round = function(precision){
	var num = parseFloat(this);
	if(isNaN(num))
		return "0";
	return num.round(precision);
};

//문자열('true'/'false', '0'/'1')로 입력된 boolean 값을 Boolean으로 반환
String.prototype.parseBoolean = function(){
	if (isNaN(this)){
		return this.toLowerCase() == 'true';
	}else {
		return parseInt(this) == '1';
	}
};

//문자열이존재하는지 여부
String.prototype.isEmpty = function(){
	if(this){
		var tmp = this.trim();
		return !tmp || tmp.length < 1;
	}
	return true;
};

// 문자열을 정해진 사이즈 만큼 축약합니다. 
String.prototype.abbr = function(length){
	
	if (this.isEmpty()) {
		return this;
	}
	
	if (this.length > length) {
		return this.substring(0, length) + '...';
	}else {
		return this;
	}
	
};

//
//	Number prototypes
//

//숫자 타입 천단위 표시
Number.prototype.format = function(){
	if(this == 0)
		return 0;
	var reg = /(^[+-]?\d+)(\d{3})/;
	var n = (this + '');
	while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
	return n;
};

//숫자 타입 천단위 제거
Number.prototype.unformat = function(){
	if(this == 0)
		return 0;
	var n = (this + '');
	return n.unformat();
};

//숫자 타입 값을 지정한 소숫점 자리수에 따른 반올림 처리
Number.prototype.round = function(precision){
	if(this == 0)
		return 0;
	var p = Math.pow(10, precision);
	return Math.round(this * p) / p;
};

// Array를 unique하게 반환
Array.prototype.unique = function() {
    var a={};
    for(var h=0;h<this.length;h++){
    	if(typeof a[this[h]] == "undefined") a[this[h]] = 1;
    }
    this.length = 0;
    
    for(var i in a)
    	this[this.length] = i;

    return this;
};

//특수문자 체크
function checkSymbol(string) {
	var stringRegx = /[~!@\#$%<>^&*\()\-=+_\']/gi; 
	var isValid = true;
	if(stringRegx.test(string)){
		isValid = false;
	}
	return isValid;
};

//특수문자 제거
function removeSymbol(string) {
	return string.replace(/[~!@\#$%<>^&*\()\-=+_\']/gi,"_");
};

//콤마(,) 제거
function removeComma(string) {
	var stringRegx = /[,]/gi;
	if(stringRegx.test(string)){
		return (string + '').unformat();
	}
	return string;
}

//싱글쿼터(')를 더블쿼터(")로 변경 
function replaceApostrophe(string) {
	var stringRegx = /[\']/gi;
	if(stringRegx.test(string)){
		return string.replace(/[\']/gi, "\"");
	}
	return string;
}

function parseBoolean(val){
	
	if (typeof val == 'undefined') {
		return false;
	}
	
	if (typeof val == 'boolean') {
		return val;
	}
	
	if (typeof val == 'string') {
		return val.parseBoolean();
	}
	
	if (typeof val == 'number') {
		return val == 1;
	} 
	
	return false;
}

Date.prototype.formatDate = function() {
    var yyyy = this.getFullYear().toString();
    var mm = (this.getMonth() + 1).toString();
    var dd = this.getDate().toString();
    
    return yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + (dd[1] ? dd : '0' + dd[0]);
}

Date.prototype.formatTime = function() {
    var HH = this.getHours().toString();
    var MM = this.getMinutes().toString();
    var ss = this.getSeconds().toString();
    
    return HH + ':' + (MM[1] ? MM : '0' + MM[0]) + ':' + (ss[1] ? ss : '0' + ss[0]);
}

Date.prototype.formatDateTime = function() {
    var date = this.formatDate();
    var time = this.formatTime();
    
    return date + ' ' + time;
}

//
//	String 형태의 function이름을 실행시킴. 
//
function executeFunctionByName(functionName, context /* , args */) {
	
	var args = [].slice.call(arguments).splice(2);
	var namespaces = functionName.split(".");
	var func = namespaces.pop();
	for (var i = 0; i < namespaces.length; i++) {
		context = context[namespaces[i]];
	}
	return context[func].apply(context, args);
}

function byId(id) {
	var node = (typeof (id) == 'string') ? document.getElementById(id) : id;
	return node;
}


function isHTMLElement(ele, nodeName){
	
	if (ele instanceof HTMLElement){
		if (!nodeName) return true;
		
		if (nodeName && typeof nodeName == 'string')
			return ele.tagName == nodeName.toUpperCase();
		
		if (nodeName && Array.isArray(nodeName)){
			for (var i = 0, len = nodeName.length; i < len; i++) {
				if (ele.tagName == nodeName[i].toUpperCase()) {
					return true;
				}
			}
		}
	}
	
	return false;
}

/**
 * HTML의 Element를 제어하기 위한 wrapper 함수.
 */
function ElementWrapper(element) {
	this.targetElement = byId(element);
	this.elementNotFound;
	if (!this.targetElement){
		this.elementNotFound = true;
	}
}
ElementWrapper.prototype = new Object();
/**
 * document.getElementById 로 DOM의 Element 객체를 반환한다.
 * 
 * @type Element
 * @returns Element
 */
ElementWrapper.prototype.get = function() {
	return this.targetElement;
};
ElementWrapper.prototype.getParent = function() {
	return this.elementNotFound ? null : this.targetElement.parentElement;
};
/**
 * @type String
 * @returns Element Tag Name
 */
ElementWrapper.prototype.getTagName = function() {
	return this.elementNotFound ? null : this.targetElement.tagName;
};
/**
 * @returns 해당 Element의 Value를 리턴합니다.
 */
ElementWrapper.prototype.getValue = function() {
	if(this.elementNotFound) return;
	
	var tagName = this.getTagName();
	var val;
	if (tagName == 'INPUT') {
		var type = this.targetElement.type;
		if (type == 'checkbox') {
			val = this.targetElement.checked;
		} else {
			val = this.targetElement.value;
		}
	} else if (tagName == 'SELECT') {
		if (this.targetElement.multiple) {
			val = new Array();
			var selected = this.targetElement.selectedOptions;
			for ( var i = 0; i < selected.length; i++) {
				val.push(selected[i].value);
			}
		} else {
			val = this.targetElement.value;
		}
	} else if(tagName == 'TEXTAREA'){
		val = this.targetElement.value;
	} else {
		val = this.targetElement.innerHTML;
	}
	
	if(val && typeof val == 'string') {
		val = val.trim();
	}
	return val;
};
/**
 * @param val
 *            Element의 Value를 설정합니다.
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.setValue = function(val) {
	
	if(this.elementNotFound) return;
	
	var tagName = this.getTagName();
	if (tagName == 'INPUT') {
		var type = this.targetElement.type;
		if (type == 'checkbox') {
			this.targetElement.checked = val ? 'checked' : '';
		} else {
			this.targetElement.value = val;
		}
	} else if (tagName == 'SELECT') {
		if (Array.isArray(val)) {
			var options = this.targetElement.options;

			for ( var i = 0, opt; i < options.length; i++) {
				opt = options[i];
				opt.selected = false;
				for ( var j = 0; j < val.length; j++) {
					if (options[j].value == val[i]) {
						opt.selected = true;
						break;
					}
				}
			}
		} else {
			this.targetElement.value = val;
		}
	} else if(tagName == 'TEXTAREA'){
		this.targetElement.value = val;
	} else {
		this.targetElement.innerHTML = val;
	}
	return this;
};
/**
 * val(); 호출 시 argument의 유무에 따라 없는 경우에는 getValue()의 값을 리턴하고, 있는 경우는 해당 argument를
 * setValue()합니다.
 * 
 * @param arg
 * @returns arg 없는 경우 해당 element의 value, 없는 경우는 ElementWrapper 객체
 */
ElementWrapper.prototype.val = function(arg) {
	if(this.elementNotFound) return;
	if (typeof (arg) == 'undefined') {
		return this.getValue();
	} else {
		return this.setValue(arg);
	}
};
/**
 * Element에 적용된 클래스 이름을 반환합니다.
 * 
 * @type String
 * @returns
 */
ElementWrapper.prototype.getClasses = function() {
	return this.elementNotFound ? null : this.targetElement.className;
};
/**
 * 찾고자 하는 클래스이름이 존재하는지 boolean값으로 반환합니다.
 * 
 * @type Boolean
 * @param name
 * @returns
 */
ElementWrapper.prototype.hasClass = function(name) {
	if(this.elementNotFound) return;
	return new RegExp('(\\s|^)' + name + '(\\s|$)').test(this.getClasses());
};
/**
 * 스타일 클래스를 추가합니다.
 * 
 * @param name
 *            클래스이름
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.addClass = function(name) {
	if(this.elementNotFound) return;
	if (!this.hasClass(name)) {
		this.targetElement.className += (this.getClasses() ? ' ' : '') + name;
	}
	return this;
};
/**
 * 스타일 클래스를 제거합니다.
 * 
 * @param name
 *            클래스이름
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.removeClass = function(name) {
	if(this.elementNotFound) return;
	if (this.hasClass(name)) {
		this.targetElement.className = this.getClasses().replace(
				new RegExp('(\\s|^)' + name + '(\\s|$)'), ' ').replace(
				/^\s+|\s+$/g, '');
	}
	return this;
};
ElementWrapper.prototype.removeAllClasses = function() {
	if(this.elementNotFound) return;
	this.removeClass(this.getClasses());
	return this;
};
/**
 * 해당 Element 를 disabled 할 것인가?
 * 
 * @param disabled
 *            여부
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.disabled = function(disabled) {
	if(this.elementNotFound) return;
	this.targetElement.disabled = disabled;
	return this;
};
/**
 * @param b
 *            display 여부
 * @param styleType
 *            display 속성 값
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.display = function(b, styleType) {
	if(this.elementNotFound) return;
	if (!styleType)
		styleType = '';
	this.targetElement.style.display = b ? styleType : 'none';
	return this;
};
/**
 * 해당 Element를 보여줌
 * 
 * @param styleType
 *            display 속성 값
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.show = function(styleType) {
	this.display(true, styleType);
	return this;
};
/**
 * 해당 Eelement를 숨김
 * 
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.hide = function() {
	this.display(false);
	return this;
};

ElementWrapper.prototype.appendAndGetChild = function(child, attributes) {
	if(this.elementNotFound) return;
	child = (child instanceof Element) ? child : document.createElement(child);

	if (attributes) {
		for ( var i in attributes) {
			if (attributes.hasOwnProperty(i)) {
				child.setAttribute(i, attributes[i]);
			}
		}
	}
	this.targetElement.appendChild(child);

	return elmt(child);
};

/**
 * 해당 Element의 자식 노드를 추가한다.
 * 
 * @param child
 *            태그이름/Element
 * @param attributes
 *            태그 속성 Json object
 * @returns {ElementWrapper}
 */
ElementWrapper.prototype.append = function(child, attributes) {
	if(this.elementNotFound) return;
	this.appendAndGetChild(child, attributes);
	return this;
};
ElementWrapper.prototype.getAttribute = function(name) {
	if(this.elementNotFound) return;
	return this.targetElement.getAttribute(name);
};
ElementWrapper.prototype.setAttribute = function(name, value) {
	if(this.elementNotFound) return;
	this.targetElement.setAttribute(name, value);
	return this;
};
ElementWrapper.prototype.setAttributes = function(attributes) {
	if(this.elementNotFound) return;
	if (attributes) {
		for ( var i in attributes) {
			if (attributes.hasOwnProperty(i)) {
				this.setAttribute(i, attributes[i]);
			}
		}
	}
	return this;
};
ElementWrapper.prototype.removeChild = function(child) {
	if(this.elementNotFound) return;
	if (typeof child == 'string'){
		child = byId(child);
	}
	
	if(child instanceof Node){
		this.targetElement.removeChild(child);
	}
	
	return this;
};
ElementWrapper.prototype.removeChildren = function(){
	if(this.elementNotFound) return;
	var child;
	while((child = this.targetElement.firstChild)){
		this.removeChild(child);
	}
	return this;
};
/**
 * ElementWrapper를 사용하기 위한 Call 함수
 */
function elmt(e) {
	if (e instanceof ElementWrapper)
		return e;
	
	return new ElementWrapper(e);
}

//
// Table handler
//

function TableElementWrapper(el) {
	this.targetElement = elmt(el);
	this.target = this.targetElement.get();
	if(!isHTMLElement(this.target, ['table', 'tbody', 'thead', 'tfoot'])){
		alert("Check this Element whether is SELECT tag. Element = " + el);
	}
	
}
TableElementWrapper.prototype = new Object();

TableElementWrapper.prototype.removeAllRows = function(){
	this.targetElement.removeChildren();
	return this;
};

TableElementWrapper.prototype.getRowCount = function(){
	return this.target.childElementCount;	
};
TableElementWrapper.prototype.addRow = function(rowData, cellContentFuncs, rowAttrFunc, cellAttrFunc, rowNum){
	var rowAttr, tr, td, cellAttr, cellData, cellNum, cellFn;
	if (typeof rowAttrFunc == 'function'){
		rowAttr = rowAttrFunc(rowData, rowNum);
	} else {
		rowAttr = rowAttrFunc || {};
	}
	
	tr = this.targetElement.appendAndGetChild('tr', rowAttr);
	
	if (Array.isArray(cellContentFuncs)){
		for (cellNum = 0, len = cellContentFuncs.length; cellNum < len; cellNum++) {
			cellFn = cellContentFuncs[cellNum];

			if (typeof cellAttrFunc == 'function'){
				cellAttr = cellAttrFunc(rowData, cellNum, rowNum);
			} else {
				cellAttr = cellAttrFunc || {};
			}
			
			td = tr.appendAndGetChild('td', cellAttr);
			
			cellData = (typeof (cellFn) == 'function') ? cellFn(rowData, cellNum, rowNum) : '';
			
			td.val(cellData);
		}
	}else {
		td = tr.appendAndGetChild('td');
		td.val(rowData);
	}
};
TableElementWrapper.prototype.addRows = function(tableData, cellContentFuncs, rowAttrFunc, cellAttrFunc) {
	var rowNum, size;
	if (Array.isArray(tableData)) {
		for (rowNum = 0, size = tableData.length; rowNum < size; rowNum++) {
			this.addRow(tableData[rowNum], cellContentFuncs, rowAttrFunc, cellAttrFunc, rowNum);
		}
	}
};


function table(el){
	return new TableElementWrapper(el);
}


function AjaxCaller(reqUri){
	if (!reqUri) {
		alert('Invalid Request URI.');
		return;
	}

	var defaultSuccessHandler = function(response){
		
		if (response.contextMessages) {
			setContextMessages(response.contextMessages);
		}

		if (response.contextData) {
			setContextData(response.contextData);
		}

		if (utils && utils.responseDataHandler) {
			utils.responseDataHandler(response);
		}
	};
	
	this.transformObject = new Object();
	this.transformObject.type = 'POST';
	this.transformObject.cache = false;
	this.transformObject.dataType = 'json';
	this.transformObject.url = reqUri;
	this.transformObject.success = defaultSuccessHandler;
	this.transformObject.beforeSend = function() {
		if (utils.transformObject.contextFormId) {
			removeContextMessages(byId(utils.transformObject.contextFormId));
		}
		
		$('#overlay').show();
	
	};
	this.transformObject.complete = function() {
		$('#overlay').hide();
	};
	this.transformObject.error = function(request, status, error){
		if (MMTHUtils.isDebug) {
			console.log(request);
		}
		
		$('#overlay').hide();
	};
	
	var utils = this;
}

AjaxCaller.prototype = new Object();
AjaxCaller.prototype.setData = function(data){
	this.transformObject.data = data;
};
AjaxCaller.prototype.setContextFormId = function(id){
	// context message를 ajax caller 시 없애기 위한 것.
	this.transformObject.contextFormId = id;
};
AjaxCaller.prototype.setMethodType = function(type){
	this.transformObject.type = type;
};
AjaxCaller.prototype.setCache = function(cache){
	this.transformObject.cache = cache;
};
AjaxCaller.prototype.setDataType = function(dataType){
	this.transformObject.dataType = dataType;
};
AjaxCaller.prototype.setSuccessHandler = function(successHandler){
	this.transformObject.success = successHandler;
};
AjaxCaller.prototype.setResponseDataHandler = function(responseDataHandler){
	this.responseDataHandler = responseDataHandler;
};
AjaxCaller.prototype.setErrorHandler = function(errorHandler){
	this.transformObject.error = errorHandler;
};
AjaxCaller.prototype.setContentType = function(contentType){
	this.transformObject.contentType = contentType;
};
AjaxCaller.prototype.setProcessData = function(processData){
	this.transformObject.processData = processData;
};
AjaxCaller.prototype.execute = function(){
	
	if (typeof Pace != 'undefined') {
		
		var transport = this.transformObject;
		$('#overlay-loading').hide();
		Pace.track(function(){
			$.ajax(transport);
		});
	} else {
		$('#overlay-loading').show();
		$.ajax(this.transformObject);
	}
	
};

function ajaxUtil(reqUri){
	var caller = new AjaxCaller(reqUri);
	return caller;
}

function setContextData(ctxData){
	if (MMTHUtils.isDebug) {
		console.log(ctxData);
	}
	// sanity check
	if (!ctxData) {
		return;
	}
	
	for (var key in ctxData) {
		
		 if (!ctxData.hasOwnProperty(key)) {
			 continue;
		 }
		 
		 var value = ctxData[key];
		 var el = byId(key);
		 
		 if (el) {
			 $(el).val(value);
		 } else {
			 if (MMTHUtils.isDebug) {
				 console.log("Element Id ["+key+"] is not found.")
			 }
		 }
	}
}

function setContextMessages(ctxMsg) {
	// sanity check
	if (!ctxMsg) {
		return;
	}
	
	for (var key in ctxMsg) {
		
		 if (!ctxMsg.hasOwnProperty(key)) {
			 continue;
		 }

		 setFieldMessage(key, ctxMsg[key]);
	}
}

function setFieldMessage(id, message, succeed){
	if (succeed) {
		$('#ctx-'+id).addClass('has-success').find('.help-block').html(message);
	} else {
		$('#ctx-'+id).addClass('has-error').find('.help-block').html(message);
	}
}

function removeFieldMessage(id){
	$('#ctx-'+id).removeClass('has-error has-success').find('.help-block').html('');
}

function removeContextMessages(/** parent element */){
	
	if (arguments.length > 0) {
		$(arguments[0]).find('.help-block').html('');
		$(arguments[0]).find('.has-error').removeClass('has-error');
		$(arguments[0]).find('.has-success').removeClass('has-success');
	} else {
		$('.help-block').text('');
		$('.has-error').removeClass('has-error');
		$('.has-success').removeClass('has-success');
	}
}

function fileCheckExt(file, fileExtArray) {
	var ext = file.split('.').pop().toLowerCase();
	if($.inArray(ext, fileExtArray) == -1) {
		return false;
	} else {
		return true;
	}
}



// Logout Timer
var LogOutTimer = function() {
	
	var logoutTimer = this;
	
	$(document).on('mousemove', function(){
		logoutTimer.reset();
	}).on('keydown', function(){
		logoutTimer.reset();
	});
	
	var obj = {
              timer : null,
              limit : 1000 * 60 * 5,
              fnc   : function() {}
	};
	
	this.setLimit = function(limit) { obj.limit = limit; };
	this.setCallbackFnc = function(fnc) { obj.fnc = fnc; };
	this.start = function(){
		obj.timer = window.setTimeout(function(){
			obj.fnc();
		}, obj.limit);
	};
    
	this.reset = function(){
		 window.clearTimeout(obj.timer);
		 logoutTimer.start();
	};
   
    
    return logoutTimer;
 };
 
 function logoutStarter(limit, fnc) {
	 var timer = new LogOutTimer();
	 timer.setLimit(limit);
	 timer.setCallbackFnc(fnc);
	 timer.start();
 }