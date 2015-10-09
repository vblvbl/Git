<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册界面</title>
<style type="text/css">
body {
	color: #CEEAF4;
	width: 100%;
	height: 100%;
	background-color: #15ACD1;
	width: 100%;
}

#contain {
	
}

#form {
	margin-top: 4%;
	font-size: 15px;
	margin-left: 2%;
}

#form div {
	text-align:left;
}

#form input {
	border: 0px;
	text-align: left;
	margin-left: 2%;
	margin-right: 2%;
	width: 15%;
	height: 16px;
	margin-bottom: 3%;
	margin-right: 2%;
}

#btn {
	margin-left: 2%;
	margin-top: 2%;
}

#btn input {
	margin-left: 1%;
	font-size: 18px;
	color: #FFF;
	background-color: #903;
	width: 5%;
	height: 2%;
	border: 0px;
	cursor: hand;
}
</style>
<script type="text/javascript">
	function changeImage(img) {
		img.src = img.src + "?" + new Date().getTime();
	}
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/calendar.js">
	
</script>
</head>
<body>
	<div id="contain">
		<h1>注册须知：</h1>
		<p>
			1.在本站注册的会员。必须遵守《互联网电子公告服务管理规定》，不得在本站发表诽谤他人。侵犯他人隐私，侵犯他人知识产权。传播病毒，政治言论，商业机密等信息。<br />
			2.在所有在本站发表的文章，本站都具有最高编辑权，并且保留用于印刷或第三方发表的权利，如果你的资料不齐全，我们将有权不做任何通知使用你在本站发布的作品。
		<p>
		<div id="form">
			<form action="${pageContext.request.contextPath}/DoRegistForm" method="post">
				<input type="hidden" name="passtid" value="${tid}">
				<div>
					登录帐号<input type="text" name="username" value="${form.username}">用户名由5-20位数字或字母组成
					<span style="color: red;font-size: 11px">${form.errorMap.username}${userHad}</span>
				</div>

				<div>
					输入密码<input type="password" name="password1">密码由7-20位数字或字母组成
					<span style="color: red;font-size: 11px">${form.errorMap.password1}</span>
				</div>

				<div>
					确认密码<input type="password" name="password2">密码由7-20位数字或字母组成
					<span style="color: red;font-size: 11px">${form.errorMap.password2}</span>
				</div>
				<div>
					电子邮件<input type="text" name="email" value="${form.email}">例如：122xxxx@qq.com
					<span style="color: red;font-size: 11px">${form.errorMap.email}</span>
				</div>

				<div>
					论坛昵称<input type="text" name="nickname" value="${form.nickname}">用于论坛显示的昵称,需为2-10位中文
					<span style="color: red;font-size: 11px">${form.errorMap.nickname}</span>
				</div>

				<div>
					生日日期<input id="endtime" type="text" value="${form.birthday}"
						onfocus="setday(this,'yyyy-MM-dd','2010-01-01','2010-12-30',1)"
						name="birthday" readonly="readonly">请选择生日日期
						<span style="color: red;font-size: 11px">${form.errorMap.birthday}</span>
				</div>

				<div id="yanzhengma">
					验证图码<input type="text" name="yanzhengma">
				
						<img src="${pageContext.request.contextPath}/DrawRandom"
							style="cursor: hand;" onclick="changeImage(this)" alt="换一张">
							<span style="color: red;font-size: 11px">${form.errorMap.yanzhengma}</span>
				</div>

				<div id="btn">
					<input type="submit" value="提交"> <input type="button"
						value="重置"> <span style="color: red;">${formDouble}</span>
				</div>
			</form>
		</div>
	</div>
</body>
</html>