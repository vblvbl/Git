<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>欢迎界面</title>
<style type="text/css">
body {
	text-align: center;
}

#title {
	text-align: center;
}

#choose {
	margin-top: 0%;
	margin-left: 50%;
	width: 60%;
	height: 40%;
	margin-left: 50%;
}

#chooser {
	margin-top: 0%;
	margin-left: 50%;
	padding-left:15%;
	width: 60%;
	height: 40%;
}

#choose a {
	background-color: red;
}
</style>
</head>
<body>
	<div id="title">
		<h1>学习困难生服务系统</h1>
	</div>
	<c:if test="${user!=null}">
		<div id="chooser">
			${user.username},欢迎您 <a
				href="${pageContext.request.contextPath}/RegistOutServlet">注销</a>
		</div>
	</c:if>
	<c:if test="${user==null}">
		<div id="choose">
			<a href="${pageContext.request.contextPath}/LandingServlet">登陆</a> <a
				href="${pageContext.request.contextPath}/RegistServlet">注册</a>
		</div>
	</c:if>
	<hr>
</body>
</html>
