<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Webtop Example</title>
	</head>

	<body>
		
		<h1>Please Log Into Your Account</h1>
		<p>
			Please use the form below to log into your account.
		</p>
		<form name="demoLoginForm" action="<c:url value='j_spring_security_check'/>" method="POST">
			<table>
				<tr>
					<td><label for="j_username">Login</label>:</td>
					<td><input id="j_username" name="j_username" size="20" maxlength="50" type="text"/></td>
				</tr>
				<tr>
					<td><label for="j_password">Password</label>:</td>
					<td><input id="j_password" name="j_password" size="20" maxlength="50" type="password"/></td>
				</tr>	
				<tr><td colspan=2><input type="submit" value="Login"/></td></tr>
			</table>
		</form>
	</body>
</html>