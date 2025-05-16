<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<!-- Favicon  -->	
<link rel="icon" type="image/x-ico" href="../../static/resources/images/favicon/motp-favicon.ico?"/> 
<link rel="shortcut icon" type="image/x-icon" href="../../static/resources/images/favicon/motp-favicon.ico?"/>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<style>
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

.form-signin {
  max-width: 400px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
  text-align : center;
}
.btn-wrap, .img-wrap {
	text-align : center;
}
</style>
</head>
<body>

	 <div class="container">

      <form class="form-signin">
        <h2 class="form-signin-heading">QR코드를 생성해주세요</h2><br>
		<div class="btn-wrap">
			<button id="btn-GenQRCode" class="btn btn-lg btn-primary" type="button">QR코드 생성</button>
		</div>
		<br/><br/>
		<div class="img-wrap" id="img-wrap"></div>
      </form>

    </div> <!-- /container -->


<script type="text/javascript">

// 

$(document).ready(function(){
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
				$('#img-wrap').html('<img src="data:image/png;base64,'+recievedData.qrData+'"/><br/><br/><button type="button" class="btn btn-danger" onclick="confirmQrSession(\''+recievedData.tid+ '\')">확인</button>');
			})
			.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
				console.log(jqXHR, textStatus, errorThrown);
				alert(JSON.stringify(textStatus));
			});
	});
});

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
			alert("인증성공");
		}
		else{
			alert("인증실패");
		}
		//alert(recievedData.returnCode);
	})
	.fail(function(jqXHR, textStatus, errorThrown) { // 응답 실패 시
		console.log(jqXHR, textStatus, errorThrown);
		alert(JSON.stringify(textStatus));
	});
}

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
</script>
</body>
</html>