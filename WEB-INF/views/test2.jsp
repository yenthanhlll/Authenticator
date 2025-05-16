<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
<title>H/W OTP Test</title>


<script type="text/javascript">
var xmlHttp;
//var url = "https://1.209.234.60:5443";
var url = "http://1.209.234.60:5050";
var url2 = "http://localhost:8080";

function createXMLHttpRequest() {
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    } 
    else if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    }
}
    
function GetRequest() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.open("GET", "gugu.jsp?num=5", true);
    xmlHttp.send(null);//get 방식일 경우, null
}

function PostRequestIssueOtp() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeIssue;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/IssueOtp", true);
	//xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("userReg").value;
	var tokenid = document.getElementById("tokenid").value;
	var param = "user=" + user + "&tokenId=" + tokenid;
	
	var sendData = {
			userName : window.btoa(user),
			tokenId : window.btoa(tokenid),
			ip : 3232235833,
			macAddress : window.btoa('11:22:33:44:55:66')
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
			
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}

//이용등록
function PostRequestRegOtp() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeReg;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/RegOtp", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("userReg").value;
	var param = "user=" + user;
	
	var sendData = {
			userName : window.btoa(user),
			ip : 3232235833,
			macAddress : window.btoa('11:22:33:44:55:66')
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//alert(param);
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  

//이용등록해지
function PostRequestSuspendOtp() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeSuspend;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/SuspendOtp", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("userReg").value;
	var param = "user=" + user;
	//alert(param);
	
		var sendData = {
			userName : window.btoa(user),
			ip : 3232235833,
			macAddress : window.btoa('11:22:33:44:55:66')
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  


function PostRequestDisuse() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeDisuse;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/DisuseOtp", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("userReg").value;
	var otp = document.getElementById("otp").value;
	var param = "user=" + user + "&otp=" + otp;
	
	var sendData = {
			userName : window.btoa(user),
			otp : window.btoa(otp),
			ip : 3232235833,
			macAddress : window.btoa('11:22:33:44:55:66')
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//alert(param);
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  

function PostRequest2() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeGenChallenge;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/GenChallenge", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("user").value;
	var tokenid = document.getElementById("tokenid").value;
	var param = "user=" + user + "&tokenId=" + tokenid;
	
	var sendData = {
			userName : window.btoa(user),
			tokenId : window.btoa(tokenid)
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//alert(param);
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  

function PostRequest3() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeVerify;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/VerifyOtp", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("user").value;
	var otp = document.getElementById("otp").value;
	var param = "user=" + user + "&otp=" + otp;
	var sendData = {
			userName : window.btoa(user),
			otp : window.btoa(otp),
			ip : 3232235833,
			macAddress : window.btoa('11:22:33:44:55:66')
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));

	//alert(json); //test
	//alert(param);
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  


function PostRequest5() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeUnlock;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/GenUnlockcode", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("user").value;
	var otp = document.getElementById("otp").value;
	var param = "user=" + user + "&otp=" + otp;
	
	var sendData = {
			userName : window.btoa(user),
			otp : window.btoa(otp)
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//alert(param);
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  

function PostRequest6() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/ResetUnlockCnt", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("user").value;
	var otp = document.getElementById("otp").value;
	var param = "user=" + user + "&otp=" + otp;
	//alert(param);
		var sendData = {
			userName : window.btoa(user),
			otp : window.btoa(otp)
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  

function PostRequest7() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/SetLost", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("user").value;
	var param = "user=" + user;
	//alert(param);
		var sendData = {
			userName : window.btoa(user),
			ip : 3232235833,
			macAddress : window.btoa('11:22:33:44:55:66')
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}  


function PostRequest8() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.open("POST", "/rpserver/webapi/HWOTP/ClearLost", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("user").value;
	var param = "user=" + user;
	//alert(param);
			var sendData = {
			userName : window.btoa(user),
			ip : 3232235833,
			macAddress : window.btoa('11:22:33:44:55:66')
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}

function PostRequestCheck() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChangeCheck;
    xmlHttp.open("POST","/rpserver/webapi/HWOTP/CheckUser", true);
	xmlHttp.setRequestHeader("Accept", "application/rp+json; charset=utf-8"); 
	xmlHttp.setRequestHeader("Content-Type", "application/rp+json; charset=utf-8"); 
    //xmlHttp.send("user="+ user + "&tokenId=" + id);
	//var p = $('input[@name="user"]').val();
	//var s = $('#user').val()
	var user = document.getElementById("user").value;
	var param = "user=" + user;
	var sendData = {
			userName : window.btoa(user)
		};
	
	//alert(JSON.stringify(sendData));
	xmlHttp.send(JSON.stringify(sendData));
	//alert(param);
	//xmlHttp.send(param);

	//xmlHttp.send("user=user&tokenId=99040003");

}


function handleStateChange() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
			document.getElementById("b").innerHTML = xmlHttp.responseText;
        }
    }
}

function handleStateChangeIssue() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	var jsonResponse =  JSON.parse(xmlHttp.responseText);
			if(jsonResponse.returnCode=='0000'){
			document.getElementById("issue").innerHTML =  "Issuance Success";
			}
			else if(jsonResponse.returnCode=='5001'){ 
			document.getElementById("issue").innerHTML =  "Issuance Failed (사용자아이디가 없음, 중복)";
			} 
			else if(jsonResponse.returnCode=='5002'){ 
			document.getElementById("issue").innerHTML =  "Issuance Failed (OTP Token아이디가 없음)";
			}
			else if(jsonResponse.returnCode=='5003'){ 
			document.getElementById("issue").innerHTML =  "Issuance Failed (키등록 Data가 없음)";
			}
			else if(jsonResponse.returnCode=='5004'){ 
			document.getElementById("issue").innerHTML =  "Issuance Failed (발급상태, Disuse상태)";
			}
		
			document.getElementById("b").innerHTML = xmlHttp.responseText;
        }
    }
}

function handleStateChangeReg() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	var jsonResponse =  JSON.parse(xmlHttp.responseText);
			if(jsonResponse.returnCode=='0000'){
			document.getElementById("reg").innerHTML =  "Issuance Success";
			}
			else if(jsonResponse.returnCode=='5001'){ 
			document.getElementById("reg").innerHTML =  "Issuance Failed (사용자아이디가 없음, 중복)";
			} 
			else if(jsonResponse.returnCode=='5002'){ 
			document.getElementById("reg").innerHTML =  "Issuance Failed (OTP Token아이디가 없음)";
			}
			else if(jsonResponse.returnCode=='5003'){ 
			document.getElementById("reg").innerHTML =  "Issuance Failed (키등록 Data가 없음)";
			}
			else if(jsonResponse.returnCode=='5004'){ 
			document.getElementById("reg").innerHTML =  "Issuance Failed (발급상태, Disuse상태)";
			}
		
			document.getElementById("b").innerHTML = xmlHttp.responseText;
        }
    }
}

function handleStateChangeSuspend() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	var jsonResponse =  JSON.parse(xmlHttp.responseText);
			if(jsonResponse.returnCode=='0000'){
			document.getElementById("suspend").innerHTML =  "이용정지 Success";
			}
			else if(jsonResponse.returnCode=='7001'){ 
			document.getElementById("suspend").innerHTML =  "이용정지 Failed (사용자아이디가 없음, 중복)";
			} 
			else if(jsonResponse.returnCode=='7002'){ 
			document.getElementById("suspend").innerHTML =  "이용정지 Failed (OTP Token아이디가 없음)";
			}
			else if(jsonResponse.returnCode=='7003'){ 
			document.getElementById("suspend").innerHTML =  "이용정지 Failed (키등록 Data가 없음)";
			}
			else if(jsonResponse.returnCode=='7004'){ 
			document.getElementById("suspend").innerHTML =  "이용정지 Failed (미발급상태, Disuse상태)";
			}
			
			document.getElementById("b").innerHTML = xmlHttp.responseText;
        }
    }
}


function handleStateChangeDisuse() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	var jsonResponse =  JSON.parse(xmlHttp.responseText);
			if(jsonResponse.returnCode=='0000'){
			document.getElementById("disuse").innerHTML =  "Disuse Success";
			}
			else if(jsonResponse.returnCode=='8001'){ 
			document.getElementById("disuse").innerHTML =  "Disuse Failed (사용자아이디가 없음, 중복)";
			} 
			else if(jsonResponse.returnCode=='8002'){ 
			document.getElementById("disuse").innerHTML =  "Disuse Failed (OTP Token아이디가 없음)";
			}
			else if(jsonResponse.returnCode=='8003'){ 
			document.getElementById("disuse").innerHTML =  "Disuse Failed (키등록 Data가 없음)";
			}
			else if(jsonResponse.returnCode=='8004'){ 
			document.getElementById("disuse").innerHTML =  "Disuse Failed (미발급상태, Disuse상태)";
			}
			
			document.getElementById("b").innerHTML = xmlHttp.responseText;
        }
    }
}

function handleStateChangeVerify() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        var jsonResponse =  JSON.parse(xmlHttp.responseText);
		if(jsonResponse.returnCode=='0000'){
		document.getElementById("b").innerHTML =  "Authentication Success";
		}
		else if(jsonResponse.returnCode=='6000'){ 
		document.getElementById("b").innerHTML =  "Authentication Failed";
		} 
		else if(jsonResponse.returnCode=='6001'){ 
		document.getElementById("b").innerHTML =  "Authentication Failed (when the same OTP is reused)";
		}
		else if(jsonResponse.returnCode=='6002'){ 
		document.getElementById("b").innerHTML =  "Authentication Failed (Time Resync required)";
		}
		else if(jsonResponse.returnCode=='6003'){ 
			document.getElementById("b").innerHTML =  "Time Resync failure";
		}
		else if(jsonResponse.returnCode=='6004'){ 
			document.getElementById("b").innerHTML =  "Time Resync failure (when the same is Time Resync OTP reused)";
		}
		else if(jsonResponse.returnCode=='6007'){ 
		document.getElementById("b").innerHTML =  "함수 파라미터 입력값 오류";
		}
		else if(jsonResponse.returnCode=='6010'){ 
		document.getElementById("b").innerHTML =  "OTP 응답값 입력형식 오류";
		}
		else if(jsonResponse.returnCode=='6011'){ 
		document.getElementById("b").innerHTML =  "질의값 사용시간 초과(AOTP 일때만)";
		}
		else if(jsonResponse.returnCode=='6015'){ 
		document.getElementById("b").innerHTML =  "OTP AuthenticationData 체크섬 불일치";
		}
		else if(jsonResponse.returnCode=='6019'){ 
			document.getElementById("b").innerHTML =  "OTP authentication error count has been exceeded";
			}
		else if(jsonResponse.returnCode=='6020'){ 
		document.getElementById("b").innerHTML =  "OTP authentication error count has been exceeded";
		}
		else if(jsonResponse.returnCode=='1016'){ 
		document.getElementById("b").innerHTML =  "Missing status";
		}
		else if(jsonResponse.returnCode=='6022'){ 
		document.getElementById("b").innerHTML =  "미발급";
		}
		else if(jsonResponse.returnCode=='6023'){ 
		document.getElementById("b").innerHTML =  "Disuse";
		}
			//document.getElementById("b").innerHTML = xmlHttp.responseText;
        }
    }
}

function handleStateChangeGenChallenge() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	var jsonResponse =  JSON.parse(xmlHttp.responseText);
		document.getElementById("b").innerHTML = "challenge : " + jsonResponse.challenge;
		document.getElementById("gen").innerHTML = jsonResponse.challenge;
		}
	}
}

function handleStateChangeUnlock() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	var jsonResponse =  JSON.parse(xmlHttp.responseText);
		document.getElementById("b").innerHTML = "unlock code : " +  jsonResponse.unlockCode;
		document.getElementById("unlock").innerHTML = jsonResponse.unlockCode;
		}
	}
}

function handleStateChangeCheck() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
		//document.getElementById("b").innerHTML = xmlHttp.responseText;
		if(xmlHttp.responseText.length == 0){
			document.getElementById("tokenidInfo").innerHTML = "정보없음";
			document.getElementById("typeInfo").innerHTML = "정보없음";
			document.getElementById("failCntInfo").innerHTML = "정보없음";
			document.getElementById("lostInfo").innerHTML = "정보없음";
		}
		var jsonObj = JSON.parse(xmlHttp.responseText);
		document.getElementById("tokenidInfo").innerHTML = jsonObj.tokenId;
		document.getElementById("failCntInfo").innerHTML = jsonObj.unlockCnt;
		document.getElementById("lostInfo").innerHTML = jsonObj.lost;
		
		if(jsonObj.type == "01"){
			document.getElementById("typeInfo").innerHTML = "미래 OTP";
		}
		else if(jsonObj.type == "02"){
			document.getElementById("typeInfo").innerHTML = "OATH";
		}
		else if(jsonObj.type == "03"){
			document.getElementById("typeInfo").innerHTML = "Advanced OTP";
		}
		}
	}
}

</script>
</head>

<body>
<center>
	<br><br><br><br>
    <form action="#">
		
		<table style="border: 1px solid; border-collapse: collapse;">
		<tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">Account ID</th>
		
			<td style="border: 1px solid;">
				<input type="text" name="userReg" id ="userReg" size="50" MAXLENGTH=12 style="width:100%;" border: 0;>
			</td>
		</tr>
		
		<tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">TOKEN S/N</th>
		
			<td style="border: 1px solid;">
				<input type="text" name="tokenid" id ="tokenid" size="50" MAXLENGTH=12 style="width:100%;" border: 0;>
			</td>
		</tr> 
		
		<tr style="border: solid 1px ;">
					
			<td style="border: 1px solid;">
				<input type="button" value="User Issuance" style="width:100%" onclick="PostRequestIssueOtp();"/>
			</td>
			
			<td id="issue">-</td>
			
		</tr>
		<!-- 
				<tr style="border: solid 1px ;">
					
			<td style="border: 1px solid;">
				<input type="button" value="이용등록" style="width:100%" onclick="PostRequestRegOtp();"/>
			</td>
			
			<td id="reg">-</td>
			
		</tr>   
		
				<tr style="border: solid 1px ;">
					
			<td style="border: 1px solid;">
				<input type="button" value="이용정지" style="width:100%" onclick="PostRequestSuspendOtp();"/>
			</td>
			
			<td id="suspend">-</td>
		</tr>   
		 -->
		<tr style="border: solid 1px ;">
					
			<td style="border: 1px solid;">
				<input type="button" value="Disuse" style="width:100%" onclick="PostRequestDisuse();"/>
			</td>
			
			<td id="disuse">-</td>
		</tr>
		

		</table><br><br>
		
		
       <table style="border: 1px solid; border-collapse: collapse;">
		<tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">Account ID</th>
		
			<td style="border: 1px solid;">
				<input type="text" name="user" id ="user" size="30" MAXLENGTH=12 style="width:100%;" border: 0;>
			</td>
		</tr>   
		<tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">OTP</th>
			
			<td style="border: 1px solid;">
				<input type="text" name="otp" id="otp" size="30" MAXLENGTH=6 style="width:100%;" border: 0;>
			</td>
		</tr>
		
		<tr style="border: solid 1px ;">
			<td style="border: 1px solid;" colspan="2">
				<input type="button" value="전송" style="width:100%" onclick="PostRequest3();" />
			</td>
		</tr>
		
		<tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">OTP Token S/N</th>
		
			<td id="tokenidInfo">-</td>
		
		</tr>
		
		<tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">OTP Token type</th>

			
			<td id="typeInfo">-</td>
		</tr>	
		
		<tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">Fail Count</th>

			
			<td id="failCntInfo">-</td>
		</tr>
		
	    <tr style="border: solid 1px ;">
			<th style="border: solid 1px ;">Lost or stolen Status</th>

			
			<td id="lostInfo">-</td>
		</tr>	
			
		<tr style="border: solid 1px ;">
			<td style="border: 1px solid;" colspan="2">
				<input type="button" value="Informantion" style="width:100%" onclick="PostRequestCheck();" />
			</td>
		</tr>
		</table><br><br>
		
		<!--  
		<table style="border: 1px solid; border-collapse: collapse;">
		<tr style="border: solid 1px ;">
					
			<td style="border: 1px solid;">
				<input type="button" value="질의값 생성" style="width:100%" onclick="PostRequest2();"/>
			</td>
			
			<td id="gen">000000</td>
		</tr>   
		<tr style="border: solid 1px ;">
					
			<td style="border: 1px solid;">
				<input type="button" value="Unlock Code 생성" style="width:100%" onclick="PostRequest5();"/>
			</td>
			
			<td id="unlock">000000</td>
		</tr>
		

		</table><br><br>
		-->
   </form>
   


	<table border="1" width="400" height="100">
	결과값 <td width="400" bgcolor="white"><div id="b">0</div></td></tr>
	</table><br><br>
	<input type="button" value="오류횟수 초기화" onclick="PostRequest6();"/><br><br>
	<input type="button" value="사고신고" onclick="PostRequest7();"/><input type="button" value="사고신고회복" onclick="PostRequest8();"/><br><br><br><br>
</center>
</body>
</html>

