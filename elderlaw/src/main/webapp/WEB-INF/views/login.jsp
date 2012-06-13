<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<title>Login Page</title>
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
	</head>
	<body onload='document.f.j_username.focus();'>
		<div class="container">  
		<%@ include file="header.jsp" %>
			<div class="span-12 last">	
				<fieldset>		
					<legend>Login</legend>
					<form name='f' action='<c:url value="/j_spring_security_check" />' method='POST'>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User:
						<input type='text' name='j_username' value=''>
						<br />
						Password:
						<input type='password' name='j_password' />					
						<br />
						<input name="submit" type="submit" />
						<br />
						<input name="reset" type="reset" />
					</form>
				</fieldset>
			</div>
		</div>
	</body>
</html>