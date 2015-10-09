<%@ page contentType="text/html;charset=utf-8"%>
<html>
<HEAD>
<TITLE>用户登录</TITLE>
<LINK href="${pageContext.request.contextPath}/css/Default.css"
	type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath}/css/xtree.css"
	type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath}/css/User_Login.css"
	type=text/css rel=stylesheet>
</HEAD>
<BODY id=userlogin_body>
	<DIV></DIV>
	<FORM action="${pageContext.request.contextPath}/DoLandingForm" method="POST">
		<DIV id=user_login>
			<DL>
				<DD id=user_top>
					<UL>
						<LI class=user_top_l></LI>
						<LI class=user_top_c></LI>
						<LI class=user_top_r></LI>
					</UL>
				<DD id=user_main>
					<UL>
						<LI class=user_main_l></LI>
						<LI class=user_main_c>
							<DIV class=user_main_box>
								<UL>
									<LI class=user_main_text>用户名：</LI>
									<LI class=user_main_input><INPUT class=TxtUserNameCssClass
										id=TxtUserName maxLength=20 name=TxtUserName></LI>
								</UL>
								<UL>
									<LI class=user_main_text>密 码：</LI>
									<LI class=user_main_input><INPUT class=TxtPasswordCssClass
										id=TxtPassword type=password name=TxtPassword></LI>
								</UL>
								<UL>
									<LI class=user_main_text>Cookie：</LI>
									<LI class=user_main_input><SELECT id=DropExpiration
										name=DropExpiration>
											<OPTION value=None selected>不保存</OPTION>
											<OPTION value=Day>保存一天</OPTION>
											<OPTION value=Month>保存一月</OPTION>
											<OPTION value=Year>保存一年</OPTION>
									</SELECT></LI>
								</UL>
							</DIV>
						</LI>
						<LI class=user_main_r><INPUT class=IbtnEnterCssClass
							style="background-color: fuchsia; width: 40%; height: 25%; margin-top: 10%;"
							id=IbtnEnter type="submit"></LI>
					</UL>
				<DD id=user_bottom>
					<UL>
						<LI class=user_bottom_l></LI>
						<LI class=user_bottom_c></LI>
						<LI class=user_bottom_r></LI>
					</UL>
				</DD>

			</DL>
		</DIV>
		<SPAN id=ValrUserName style="DISPLAY: none; COLOR: red"></SPAN> <SPAN
			id=ValrPassword style="DISPLAY: none; COLOR: red"></SPAN> <SPAN
			id=ValrValidateCode style="DISPLAY: none; COLOR: red"></SPAN>
		<DIV id=ValidationSummary1 style="DISPLAY: none; COLOR: red"></DIV>

		<DIV>${loginWrong}</DIV>

	</FORM>
</BODY>