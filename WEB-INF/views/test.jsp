<!DOCTYPE html>
<html lang="ko-KR">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
<!-- Favicon  -->	
<link rel="icon" type="image/x-ico" href="../../static/resources/images/favicon/motp-favicon.ico?"/> 
<link rel="shortcut icon" type="image/x-icon" href="../../static/resources/images/favicon/motp-favicon.ico?"/>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<!-- Latest compiled and minified JavaScript -->
    <style type="text/css">
        
        body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;

        }
        /*TAB CSS*/

        ul.tabs {
            margin: 0;
            padding: 0;
            float: left;
            list-style: none;
            height: 32px; /*--Set height of tabs--*/
            border-bottom: 1px solid #999;
            border-left: 1px solid #999;
            width: 100%;
        }
        ul.tabs li {
            float: left;
            margin: 0;
            padding: 0;
            height: 31px; /*--Subtract 1px from the height of the unordered list--*/
            line-height: 31px; /*--Vertically aligns the text within the tab--*/
            border: 1px solid #999;
            border-left: none;
            margin-bottom: -1px; /*--Pull the list item down 1px--*/
            overflow: hidden;
            position: relative;
            background: #e0e0e0;
        }
        ul.tabs li a {
            text-decoration: none;
            color: #000;
            display: block;
            font-size: 1.2em;
            padding: 0 20px;
            /*--Gives the bevel look with a 1px white border inside the list item--*/
            border: 1px solid #fff; 
            outline: none;
        }
        ul.tabs li a:hover {
            background: #ccc;
        }
        html ul.tabs li.active, html ul.tabs li.active a:hover  {
             /*--Makes sure that the active tab does not listen to the hover properties--*/
            background: #fff;
            /*--Makes the active tab look like it's connected with its content--*/
            border-bottom: 1px solid #fff; 
        }

        /*Tab Conent CSS*/
        .tab_container {
            border: 1px solid #999;
            border-top: none;
            overflow: hidden;
            clear: both;
            float: left; 
            width: 100%;
            background: #fff;
        }
        .tab_content {
            padding: 20px;
            font-size: 1.2em;
        }
        
    </style>



    <script type="text/javascript">
    
    function currentDate(){
    	var cur = new Date();
        var yyyy = cur.getFullYear().toString();
        var mm = (cur.getMonth() + 1).toString();
        var dd = cur.getDate().toString();
        
        return yyyy + (mm[1] ? mm : '0' + mm[0]) + (dd[1] ? dd : '0' + dd[0]);
    }
    function currentTime(){
    	var cur = new Date();
    	var HH = cur.getHours().toString();
    	var MM = cur.getMinutes().toString();
    	var ss = cur.getSeconds().toString();
    	   
    	return HH + (MM[1] ? MM : '0' + MM[0]) + (ss[1] ? ss : '0' + ss[0]);
    }

    function SHA256(s){
        
        var chrsz   = 8;
        var hexcase = 0;
      
        function safe_add (x, y) {
            var lsw = (x & 0xFFFF) + (y & 0xFFFF);
            var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
            return (msw << 16) | (lsw & 0xFFFF);
        }
      
        function S (X, n) { return ( X >>> n ) | (X << (32 - n)); }
        function R (X, n) { return ( X >>> n ); }
        function Ch(x, y, z) { return ((x & y) ^ ((~x) & z)); }
        function Maj(x, y, z) { return ((x & y) ^ (x & z) ^ (y & z)); }
        function Sigma0256(x) { return (S(x, 2) ^ S(x, 13) ^ S(x, 22)); }
        function Sigma1256(x) { return (S(x, 6) ^ S(x, 11) ^ S(x, 25)); }
        function Gamma0256(x) { return (S(x, 7) ^ S(x, 18) ^ R(x, 3)); }
        function Gamma1256(x) { return (S(x, 17) ^ S(x, 19) ^ R(x, 10)); }
      
        function core_sha256 (m, l) {
             
            var K = new Array(0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5, 0x3956C25B, 0x59F111F1,
                0x923F82A4, 0xAB1C5ED5, 0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3,
                0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174, 0xE49B69C1, 0xEFBE4786,
                0xFC19DC6, 0x240CA1CC, 0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
                0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7, 0xC6E00BF3, 0xD5A79147,
                0x6CA6351, 0x14292967, 0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13,
                0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85, 0xA2BFE8A1, 0xA81A664B,
                0xC24B8B70, 0xC76C51A3, 0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070,
                0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5, 0x391C0CB3, 0x4ED8AA4A,
                0x5B9CCA4F, 0x682E6FF3, 0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208,
                0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2);
 
            var HASH = new Array(0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A, 0x510E527F, 
                       0x9B05688C, 0x1F83D9AB, 0x5BE0CD19);
 
            var W = new Array(64);
            var a, b, c, d, e, f, g, h, i, j;
            var T1, T2;
      
            m[l >> 5] |= 0x80 << (24 - l % 32);
            m[((l + 64 >> 9) << 4) + 15] = l;
      
            for ( var i = 0; i<m.length; i+=16 ) {
                a = HASH[0];
                b = HASH[1];
                c = HASH[2];
                d = HASH[3];
                e = HASH[4];
                f = HASH[5];
                g = HASH[6];
                h = HASH[7];
      
                for ( var j = 0; j<64; j++) {
                    if (j < 16) W[j] = m[j + i];
                    else W[j] = safe_add(safe_add(safe_add(Gamma1256(W[j - 2]), W[j - 7]), Gamma0256(W[j - 15])), W[j - 16]);
      
                    T1 = safe_add(safe_add(safe_add(safe_add(h, Sigma1256(e)), Ch(e, f, g)), K[j]), W[j]);
                    T2 = safe_add(Sigma0256(a), Maj(a, b, c));
      
                    h = g;
                    g = f;
                    f = e;
                    e = safe_add(d, T1);
                    d = c;
                    c = b;
                    b = a;
                    a = safe_add(T1, T2);
                }
      
                HASH[0] = safe_add(a, HASH[0]);
                HASH[1] = safe_add(b, HASH[1]);
                HASH[2] = safe_add(c, HASH[2]);
                HASH[3] = safe_add(d, HASH[3]);
                HASH[4] = safe_add(e, HASH[4]);
                HASH[5] = safe_add(f, HASH[5]);
                HASH[6] = safe_add(g, HASH[6]);
                HASH[7] = safe_add(h, HASH[7]);
            }
            return HASH;
        }
      
        function str2binb (str) {
            var bin = Array();
            var mask = (1 << chrsz) - 1;
            for(var i = 0; i < str.length * chrsz; i += chrsz) {
                bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (24 - i%32);
            }
            return bin;
        }
      
        function Utf8Encode(string) {
            string = string.replace(/\r\n/g,"\n");
            var utftext = "";
      
            for (var n = 0; n < string.length; n++) {
      
                var c = string.charCodeAt(n);
      
                if (c < 128) {
                    utftext += String.fromCharCode(c);
                }
                else if((c > 127) && (c < 2048)) {
                    utftext += String.fromCharCode((c >> 6) | 192);
                    utftext += String.fromCharCode((c & 63) | 128);
                }
                else {
                    utftext += String.fromCharCode((c >> 12) | 224);
                    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                    utftext += String.fromCharCode((c & 63) | 128);
                }
      
            }
      
            return utftext;
        }
      
        function binb2hex (binarray) {
            var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
            var str = "";
            for(var i = 0; i < binarray.length * 4; i++) {
                str += hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8+4)) & 0xF) +
                hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8  )) & 0xF);
            }
            return str;
        }
      
        s = Utf8Encode(s);
        return binb2hex(core_sha256(str2binb(s), s.length * chrsz));
      
    }

    function confirmQrSession(tid){
    	
    	var sendData = {
    			tid : tid
    		};

    	jQuery.ajax('/rpserver/webapi/BIOTP/CheckAuthResult', {
    		headers : {
    			'Accept' : 'application/rp+json; charset=utf-8',
    			'Content-type' : 'application/rp+json; charset=utf-8',
    		},
    		data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
    	
    		method: 'POST'
    	})
    	.done(function(recievedData) { // 응답 성공 시
    		if(recievedData.returnCode == '0000'){
    			document.getElementById("c").innerHTML =  "인증성공";
    		}
    		else{
    			document.getElementById("c").innerHTML =  "인증실패";
    		}
    		//alert(recievedData.returnCode);
    	})
    	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
    		console.log(jqXHR, textStatus, errorThrown);
    		alert(JSON.stringify(textStatus));
    	});
    }
    
        $(document).ready(function() {

            //When page loads...
            $(".tab_content").hide(); //Hide all content
            $("ul.tabs li:first").addClass("active").show(); //Activate first tab
            $(".tab_content:first").show(); //Show first tab content

            //On Click Event
            $("ul.tabs li").click(function() {

                $("ul.tabs li").removeClass("active"); //Remove any "active" class
                $(this).addClass("active"); //Add "active" class to selected tab
                $(".tab_content").hide(); //Hide all tab content

                var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
                $(activeTab).fadeIn(); //Fade in the active ID content
                return false;
            });
          
            
            // id / password login
        	$('#btn-login').on('click', function(){
        		var user = document.getElementById("user").value;
        		var otp = document.getElementById("password").value;
        		var user2 = document.getElementById("user2").value;
        		var otp2 = document.getElementById("password2").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		if(otp.length == 0) {
        			alert("TYPE을 입력하세요");
        			return
        		}
        		
        	       var arr = new Array(); 
        	       var obj = new Object(); 

        	       obj.userName = window.btoa(user);
        	       obj.productTypeCode = window.btoa(otp);
        	       arr.push(obj);
        	        if(user2.length != 0){
        	       obj = new Object();
        	       obj.userName = window.btoa(user2);
        	       obj.productTypeCode = window.btoa(otp2);
        	       arr.push(obj);
        			}
        		var sendData = {
        				userName : window.btoa(user),
        				productTypeCode : window.btoa(otp)
            		};
            		
            		console.log('sendData=', JSON.stringify(arr));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/webapi/BIOTP/RegUser', {
            				headers : {
            					'Accept' : 'application/rp+json; charset=utf-8',
            					'Content-type' : 'application/rp+json; charset=utf-8',
            				},
            				data: JSON.stringify(arr), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
            				alert(JSON.stringify(recievedData));
            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
        		//document.getElementById("a").innerHTML =  "인증 성공";
        	});
            
            // 사용자 아이디 정보
        	$('#btn-userinfo').on('click', function(){
        		var user = document.getElementById("user").value;
        		var otp = document.getElementById("password").value;

        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		
        		var sendData = {
        				userName : window.btoa(user)
            		};
            			
            		//console.log('sendData=', JSON.stringify(arr));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/webapi/BIOTP/GetUserInfo', {
            				headers : {
            					'Accept' : 'application/rp+json; charset=utf-8',
            					'Content-type' : 'application/rp+json; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
            				alert(JSON.stringify(recievedData));
            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
        		//document.getElementById("a").innerHTML =  "인증 성공";
        	});
            
            // 사용자 멀티로그인 정책
        	$('#btn-delete').on('click', function(){
        		var user = document.getElementById("user").value;
        		var user2 = document.getElementById("user2").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		
     	       var arr = new Array(); 
    	       var obj = new Object(); 

    	       obj.userName = window.btoa(user);
    	       arr.push(obj);
    	        if(user2.length != 0){
    	       obj = new Object();
    	       obj.userName = window.btoa(user2);
    	       arr.push(obj);
    			}
    	        
        		var sendData = {
        				userName : window.btoa(user)
            		};
            		//console.log('sendData=', JSON.stringify(arr));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/webapi/BIOTP/DeleteUser', {
            				headers : {
            					'Accept' : 'application/rp+json; charset=utf-8',
            					'Content-type' : 'application/rp+json; charset=utf-8',
            				},
            				data: JSON.stringify(arr), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
            				alert(JSON.stringify(recievedData));
            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
        		//document.getElementById("a").innerHTML =  "인증 성공";
        	});
            
            // 사용자 멀티로그인 정책
        	$('#btn-multi').on('click', function(){
        		var user = document.getElementById("user").value;
        		var otp = document.getElementById("password").value;
        		var multi = document.getElementById("multi").value;

        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		
        		var sendData = {
        				userName : window.btoa(user),
        				multiLoginYN : window.btoa(multi)
            		};
            		//console.log('sendData=', JSON.stringify(arr));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/webapi/BIOTP/UpdateMultiLoginPolicy', {
            				headers : {
            					'Accept' : 'application/rp+json; charset=utf-8',
            					'Content-type' : 'application/rp+json; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
            				alert(JSON.stringify(recievedData));
            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
        		//document.getElementById("a").innerHTML =  "인증 성공";
        	});
            
            
            // hw otp login
        	$('#btn-otpLogin').on('click', function(){
        		var user = document.getElementById("otpuser").value;
        		var otp = document.getElementById("otp").value;

        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		if(otp.length == 0) {
        			alert("OTP를 입력하세요");
        			return
        		}
        		var sendData = {
        				userName : window.btoa(user),
        				otp : window.btoa(otp),
        				ip : 3232235833,
        				macAddress : window.btoa('11:22:33:44:55:66')
            		};
        		
        		jQuery.ajax('/rpserver/webapi/HWOTP/VerifyOtp',{
    				headers : {
    					'Accept' : 'application/rp+json; charset=utf-8',
    					'Content-type' : 'application/rp+json; charset=utf-8',
    				},
    				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
    			
    				method: 'POST'
    			})

        			.done(function(recievedData) { // 응답 성공 시
        				console.log('recievedData = ', recievedData);
        				alert(JSON.stringify(recievedData));
        				if(recievedData.returnCode=="0000"){
        					document.getElementById("b").innerHTML =  "인증 성공";
        					}
        					else if(recievedData.returnCode=="6000"){ 
        					document.getElementById("b").innerHTML =  "인증 실패";
        					} 
        					else if(recievedData.returnCode=="6001"){ 
        					document.getElementById("b").innerHTML =  "인증 실패 (동일 OTP 응답값 재사용시)";
        					}
        					else if(recievedData.returnCode=="6002"){ 
        					document.getElementById("b").innerHTML =  "인증 실패 (보정 필요)";
        					}
        					else if(recievedData.returnCode=="6007"){ 
        					document.getElementById("b").innerHTML =  "함수 파라미터 입력값 오류";
        					}
        					else if(recievedData.returnCode=="6010"){ 
        					document.getElementById("b").innerHTML =  "OTP 응답값 입력형식 오류";
        					}
        					else if(recievedData.returnCode=="6011"){ 
        					document.getElementById("b").innerHTML =  "질의값 사용시간 초과(AOTP 일때만)";
        					}
        					else if(recievedData.returnCode=="6015"){ 
        					document.getElementById("b").innerHTML =  "OTP 인증데이터 체크섬 불일치";
        					}
        					else if(recievedData.returnCode=="6020"){ 
        					document.getElementById("b").innerHTML =  "오류횟수초과";
        					}
        					else if(recievedData.returnCode=="6021"){ 
        					document.getElementById("b").innerHTML =  "사고신고";
        					}
        					else if(recievedData.returnCode=="6022"){ 
        					document.getElementById("b").innerHTML =  "미발급";
        					}
        					else if(recievedData.returnCode=="6023"){ 
        					document.getElementById("b").innerHTML =  "폐기";
        					}
        			})
        			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
        				console.log(jqXHR, textStatus, errorThrown);
        				alert(JSON.stringify(textStatus));
        			});
        	});           
            
            // sw otp login
        	$('#btn-swotpLogin').on('click', function(){
        		var user = document.getElementById("swotpuser").value;
        		var otp = document.getElementById("swotp").value;
        		var macAddress = "FF:34:54:VC:A2:D3";
        		//var tid = document.getElementById("tid").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		if(otp.length == 0) {
        			alert("OTP를 입력하세요");
        			return
        		}
        		var sendData = {
        				userName : window.btoa(user),
        				otp :window.btoa(otp),
        				ip:3232235828, 
        				macAddress: window.btoa(macAddress)
            		};
            			
            		console.log('sendData=', JSON.stringify(sendData));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/webapi/BIOTP/VerifyOTP', {
            				headers : {
            					'Accept' : 'application/rp+json; charset=utf-8',
            					'Content-type' : 'application/rp+json; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
            				if(recievedData.returnCode=='0000'){
            					document.getElementById("bb").innerHTML =  "인증 성공";
            					}
            				else{
            					document.getElementById("bb").innerHTML =  "인증 실패";
            				}
            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
            	});

            // sw otp 발급코드 생성
        	$('#btn-swotpIssueCode').on('click', function(){
        		var user = document.getElementById("swotpuser").value;
        		var otp = document.getElementById("swotp").value;
        		//var tid = document.getElementById("tid").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}

        		var sendData = {
        				userName : window.btoa(user)
            		};
            			
            		console.log('sendData=', JSON.stringify(sendData));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/webapi/BIOTP/IssueCode', {
            				headers : {
            					'Accept' : 'application/rp+json; charset=utf-8',
            					'Content-type' : 'application/rp+json; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
            				alert(JSON.stringify(recievedData));
            					document.getElementById("bb").innerHTML = window.atob(recievedData.issueCode);

            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
            	});
            
            // sw otp 강제해지
        	$('#btn-swotpForceDereg').on('click', function(){
        		var user = document.getElementById("swotpuser").value;
        		var otp = document.getElementById("swotp").value;
        		//var tid = document.getElementById("tid").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}

        		var sendData = {
        				userName : window.btoa(user)
            		};
            			
            		console.log('sendData=', JSON.stringify(sendData));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/webapi/BIOTP/ForceDereg', {
            				headers : {
            					'Accept' : 'application/rp+json; charset=utf-8',
            					'Content-type' : 'application/rp+json; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
							alert(JSON.stringify(recievedData));
            					//document.getElementById("bb").innerHTML = window.atob(recievedData.issueCode);

            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
            	});
            
			// QR 생성
        	$('#btn-GenQRCode').on('click', function(){
        		
        		$('#img-wrap').html('');
        		var sendData = {
        			qrSessionId: window.btoa(currentDate()+currentTime())
        		};
        			
        		console.log('sendData=', JSON.stringify(sendData));
        			
        		jQuery.ajax('/rpserver/webapi/BIOTP/GenQRCode', {
        				headers : {
        					'Accept' : 'application/rp+json; charset=utf-8',
        					'Content-type' : 'application/rp+json; charset=utf-8',
        				},
        				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
        			
        				method: 'POST'
        			})
        			.done(function(recievedData) { // 응답 성공 시
        				console.log('recievedData = ', recievedData);
        				$('#img-wrap').html('<img src="data:image/png;base64,'+recievedData.qrData+'"/><br/><br/><button type="button" class="btn btn-danger" onclick="confirmQrSession(\''+recievedData.tid+ '\')">로그인</button>');
        			})
        			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
        				console.log(jqXHR, textStatus, errorThrown);
        				alert(JSON.stringify(textStatus));
        			});
        	});
			
			// PIN발급
        	$('#btn-pinreg').on('click', function(){
        		
        		var user = document.getElementById("pinuser").value;
        		var pin = document.getElementById("pin").value;
        		//var tid = document.getElementById("tid").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		if(pin.length == 0) {
        			alert("PIN을 입력하세요");
        			return
        		}
        		var sendData = {
        				userName : window.btoa(user),
        				authData :  window.btoa(SHA256(pin)),
        				productType : 64,
        				productAuthType : 2,
        				ip : 3232235833,
        				macAddress : window.btoa('11:22:33:44:55:66')
            		};
            			
            		console.log('sendData=', JSON.stringify(sendData));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/httpapi/authtype/Reg', {
            				headers : {
            					'Accept' : 'application/fido+uaf; charset=utf-8',
            					'Content-type' : 'application/fido+uaf; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
							alert(JSON.stringify(recievedData));
            					//document.getElementById("bb").innerHTML = window.atob(recievedData.issueCode);

            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
        	});
			
			// PIN인증
        	$('#btn-pinauth').on('click', function(){
        		
        		var user = document.getElementById("pinuser").value;
        		var pin = document.getElementById("pin").value;
        		//var tid = document.getElementById("tid").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		if(pin.length == 0) {
        			alert("PIN을 입력하세요");
        			return
        		}
        		var sendData = {
        				userName : window.btoa(user),
        				authData :  window.btoa(SHA256(pin)),
        				productType : 64,
        				productAuthType : 2,
        				ip : 3232235833,
        				macAddress : window.btoa('11:22:33:44:55:66')
            		};
            			
            		console.log('sendData=', JSON.stringify(sendData));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/httpapi/authtype/Auth', {
            				headers : {
            					'Accept' : 'application/fido+uaf; charset=utf-8',
            					'Content-type' : 'application/fido+uaf; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
							alert(JSON.stringify(recievedData));
            					//document.getElementById("bb").innerHTML = window.atob(recievedData.issueCode);

            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
        	});
			
			// PIN변경
        	$('#btn-pinchange').on('click', function(){
        		
        		var user = document.getElementById("pinuser").value;
        		var pin = document.getElementById("pin").value;
        		var newpin = document.getElementById("newpin").value;
        		//var tid = document.getElementById("tid").value;
        		if(user.length == 0) {
        			alert("사용자아이디를 입력하세요");
        			return
        		}
        		if(pin.length == 0) {
        			alert("PIN을 입력하세요");
        			return
        		}
        		if(newpin.length == 0) {
        			alert("NEW PIN을 입력하세요");
        			return
        		}
        		var sendData = {
        				userName : window.btoa(user),
        				authData :  window.btoa(SHA256(pin)),
        				newAuthData :  window.btoa(SHA256(newpin)),
        				productType : 64,
        				productAuthType : 2,
        				ip : 3232235833,
        				macAddress : window.btoa('11:22:33:44:55:66')
            		};
            			
            		console.log('sendData=', JSON.stringify(sendData));
            		//alert(JSON.stringify(sendData));
            		jQuery.ajax('/rpserver/httpapi/authtype/Update', {
            				headers : {
            					'Accept' : 'application/fido+uaf; charset=utf-8',
            					'Content-type' : 'application/fido+uaf; charset=utf-8',
            				},
            				data: JSON.stringify(sendData), // JSON 객체를 String으로 보냄
            			
            				method: 'POST'
            			})
            			.done(function(recievedData) { // 응답 성공 시
            				console.log('recievedData = ', recievedData);
							alert(JSON.stringify(recievedData));
            					//document.getElementById("bb").innerHTML = window.atob(recievedData.issueCode);

            			})
            			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
            				console.log(jqXHR, textStatus, errorThrown);
            				alert(JSON.stringify(textStatus));
            			});
        	});
			
        });
    </script>
</head>
<body>

<div id="wrapper">    
    <!--탭 메뉴 영역 -->
    <ul class="tabs">
        <li><a href="#tab1">ID / PASSWORD</a></li>
        <li><a href="#tab2">H/W OTP</a></li>
        <li><a href="#tab3">S/W OTP</a></li>
        <li><a href="#tab4">QR</a></li>
        <li><a href="#tab5">PIN</a></li>
    </ul>

    <!--탭 콘텐츠 영역 -->
    <div class="tab_container">

        <div id="tab1" class="tab_content">
            <!--Content-->
            
<table style='width:1000px; border:0px solid #dcdcdc;padding:20px'>
	<tr>
		<td style='width:300px' valign=top>
			
			<table>
			 <tr>
				<td class='item'>ID</td>
				<td style = ' padding: 10px'><input type=text name='user' id='user' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			 <tr>
				<td class='item'>TYPE</td>
				<td style = ' padding: 10px'><input type=text name='password' id='password' style="height:30px; width:300px; font-size : 20px"></td>
				<td class='type'>(64: NONE)</td>
			 </tr>
			 <tr>
				<td class='item'>ID2</td>
				<td style = ' padding: 10px'><input type=text name='user2' id='user2' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			 <tr>
				<td class='item'>TYPE2</td>
				<td style = ' padding: 10px'><input type=text name='password2' id='password2' style="height:30px; width:300px; font-size : 20px"></td>
				<td class='type'>(64: NONE)</td>
			 </tr>
			  <tr>
				<td class='item'>결과</td>
				<td style = ' padding: 10px'><div id="a" style = 'border: 1px; solid #333333'>0</div></td>
			 </tr>
			 <tr>
			 	<td class='item' >멀티</td>
				<td style = ' padding: 10px'>
			 	<select name="multi" id="multi">

    				<option value="N">N</option>
    				<option value="Y" selected="selected">Y</option>
    				</select>
    				</td>
			
			 </tr>
			</table>

		</td>

			
		<td style='width:600px' valign=bottom>
			<button id="btn-login" class="btn btn-lg btn-primary" type="button">사용자등록</button>
		</td>
		<td style='width:600px' valign=bottom>
			<button id="btn-userinfo" class="btn btn-lg btn-primary" type="button">정보</button>
		</td>
		<td style='width:600px' valign=bottom>
			<button id="btn-delete" class="btn btn-lg btn-primary" type="button">삭제</button>
		</td>
		<td style='width:600px' valign=bottom>
			<button id="btn-multi" class="btn btn-lg btn-primary" type="button">로그인정책업데이트</button>
		</td>
	</tr>
	</table>
        </div>

        <div id="tab2" class="tab_content">
           <!--Content-->
<table style='width:1000px; border:0px solid #dcdcdc;padding:20px'>
	<tr>
		<td style='width:300px' valign=top>
			
			<table>
			 <tr>
				<td class='item'>ID</td>
				<td style = ' padding: 10px'><input type=text name='otpuser' id='otpuser' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			 <tr>
				<td class='item'>OTP</td>
				<td style = ' padding: 10px'><input type=text name='otp' id='otp'  maxlength='6' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			  <tr>
				<td class='item'>결과</td>
				<td style = ' padding: 10px'><div id="b" style = 'border: 1px; solid #333333'>0</div></td>
			 </tr>
			</table>

		</td>

			
		<td style='width:600px' valign=bottom>
			<button id="btn-otpLogin" class="btn btn-lg btn-primary" type="button">로그인</button>
		</td>
	</tr>
	</table>
        </div>
        
        <div id="tab3" class="tab_content">
           <!--Content-->
<table style='width:1000px; border:0px solid #dcdcdc;padding:20px'>
	<tr>
		<td style='width:300px' valign=top>
			
			<table>
			 <tr>
				<td class='item'>ID</td>
				<td style = ' padding: 10px'><input type=text name='swotpuser' id='swotpuser' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			 <tr>
				<td class='item'>OTP</td>
				<td style = ' padding: 10px'><input type=text name='swotp' id='swotp'  maxlength='6' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			 <!--
			  <tr>
				<td class='item'>TID</td>
				<td style = ' padding: 10px'><input type=text name='tid' id='tid'   style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			 -->
			  <tr>
				<td class='item'>결과</td>
				<td style = ' padding: 10px'><div id="bb" style = 'border: 1px; solid #333333'>0</div></td>
			 </tr>
			</table>

		</td>

			
		<td style='width:100px' valign=bottom>
			<button id="btn-swotpLogin" class="btn btn-lg btn-primary" type="button">로그인</button>
		</td>
	   <td style='width:100px' valign=bottom>
			<button id="btn-swotpIssueCode" class="btn btn-lg btn-primary" type="button">발급코드생성</button>
		</td>
			   <td style='width:100px' valign=bottom>
			<button id="btn-swotpForceDereg" class="btn btn-lg btn-primary" type="button">강제해지</button>
		</td>
	</tr>
	</table>
        </div>


        <div id="tab4" class="tab_content">
           <!--Content-->
<table style='width:1000px; border:0px solid #dcdcdc;padding:20px'>
	<tr>
		<td style='width:300px' valign=top>
			
			<table>
			 <tr>
				<td class='item'>ID</td>
				<td style = ' padding: 10px'><input type=text name='qruser' id='qruser' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>

			  <tr>
				<td class='item'>결과</td>
				<td style = ' padding: 10px'><div id="c" style = 'border: 1px; solid #333333'>0</div></td>
			 </tr>
			 
			
			</table>
			
		 <div class="img-wrap" id="img-wrap"></div>
		</td>

			
		<td style='width:600px' valign=bottom>
			<button id="btn-GenQRCode" class="btn btn-lg btn-primary" type="button">QR코드 생성</button>
		</td>
	</tr>
	</table>
        </div>
        
        
         <div id="tab5" class="tab_content">
           <!--Content-->
<table style='width:1000px; border:0px solid #dcdcdc;padding:20px'>
	<tr>
		<td style='width:300px' valign=top>
			
			<table>
			 <tr>
				<td class='item'>ID</td>
				<td style = ' padding: 10px'><input type=text name='pinuser' id='pinuser' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			 <tr>
				<td class='item'>PIN</td>
				<td style = ' padding: 10px'><input type=text name='pin' id='pin'  maxlength='6' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>
			  <tr>
				<td class='item'>NEW PIN</td>
				<td style = ' padding: 10px'><input type=text name='newpin' id='newpin'  maxlength='6' style="height:30px; width:300px; font-size : 20px"></td>
			 </tr>

			  <tr>
				<td class='item'>결과</td>
				<td style = ' padding: 10px'><div id="ee" style = 'border: 1px; solid #333333'>0</div></td>
			 </tr>
			</table>

		</td>

			
		<td style='width:100px' valign=bottom>
			<button id="btn-pinreg" class="btn btn-lg btn-primary" type="button">발급</button>
		</td>
	   <td style='width:100px' valign=bottom>
			<button id="btn-pinauth" class="btn btn-lg btn-primary" type="button">인증</button>
		</td>
			   <td style='width:100px' valign=bottom>
			<button id="btn-pinchange" class="btn btn-lg btn-primary" type="button">변경</button>
		</td>
	</tr>
	</table>
        </div>
        
        
    </div>

</div>

</body>
</html>