<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<title>Elder Law</title>
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
	</head>
	<body>
		<div class="container">
		<%@ include file="header.jsp" %>
			<div class="span-12 last">	
				<fieldset>		
					<legend>Search</legend>
						<form:form modelAttribute="search" action="results" method="get">
							&nbsp;&nbsp;&nbsp;&nbsp;Last name: 
							<input type="text" name="lastName" />
							<br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;County:
							<form:select path="county" items="${counties}" />
							<br />
							<!-- Subject Area:
							<form:select path="area" items="${areas}" />
							<br />  -->
							<input type="submit" value="Search" />
						</form:form> 
				</fieldset>
			</div>
		</div>
	</body>
</html>