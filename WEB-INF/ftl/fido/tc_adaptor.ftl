<#ftl encoding="utf-8"/>
<#if width gte 1080><#assign fontSize = 72>
<#elseif width gte 960><#assign fontSize = 56>
<#elseif width gte 720><#assign fontSize = 48>
<#elseif width gte 520><#assign fontSize = 42>
<#elseif width gte 480><#assign fontSize = 36>
<#elseif width gte 420><#assign fontSize = 32>
<#elseif width gte 380><#assign fontSize = 30>
<#elseif width gte 350><#assign fontSize = 28>
<#elseif width gte 320><#assign fontSize = 24>
<#elseif width gte 240><#assign fontSize = 20>
<#else><#assign fontSize = 16>
</#if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction confirmation</title>
<style type="text/css">
@font-face { font-family: 'Nanum Gothic'; font-style: normal; font-weight: 700; src: url(../../assets/css/fonts/NanumGothic-Bold.eot); src: url(../../assets/css/fonts/NanumGothic-Bold.eot?#iefix) format('embedded-opentype'), url(../../assets/css/fonts/NanumGothic-Bold.woff) format('woff'), url(../../assets/css/fonts/NanumGothic-Bold.ttf) format('truetype');}
html, body, div, span, h1, h2, h3, h4, h5, h6, p, table, tbody, tfoot, thead, tr, th, td {
	margin: 0; padding: 0; border: 0; font-size: 100%; font: inherit; vertical-align: baseline;
}
html, body {
	height: 100%; font-family: 'Nanum Gothic', 'Helvetica Neue', Helvetica, Arial, sans-serif;
	background :url(https://www.google.co.kr/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjv-rSP4pDRAhXMFJQKHcugDUYQjRwIBw&url=http%3A%2F%2Fwww.intrawallpaper.com%2Fbackground&psig=AFQjCNHHX0nXcpPcecDnz20Ieqb1OrcCHQ&ust=1482804160382427)
}
.page-wrap {
	font-size: ${fontSize}px;
}
.message {
	padding: 5px; background: #efefef; color: #333; text-align: center;
}
table {
	border-collapse: collapse; border-spacing: 0; width: 100%;
}
.title {
	background: #000; color: #efefef;padding: 3px;
}
.logo {
	text-align: right;
}
td.label {
	color:#aeaeae;padding-left:10px;text-align: left; border-bottom: 1px solid #efefef;
}
td.val {
	padding-right:10px;text-align: right;color:#000;border-bottom: 1px solid #efefef;font-weight:bold;
}
.logo small {
	font-size: 0.5em;
}
.message {
	font-size: 0.8em;
}
td.label, td.val {
	font-size: 0.9em;
}
</style>
</head>
<body>
	<div class="page-wrap">
		<div class="title">
			<p>Confirm transaction</p>
			<p class="logo"><small>MIRAE Technology co.,Ltd.</small></p>
		</div>
		<br/>
		<table>
			<tbody class="tranContents">
			<tr>
				<td class="label">Transaction Message</td>
			</tr>
			<tr>
				<td class="val">${contents}</td>
			</tr>
			</tbody>
		</table>
		<br/>
		<div class="message">Do you want to authorize this transaction?</div>
	</div>
</body>
</html>