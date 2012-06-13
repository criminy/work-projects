<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
					<legend>Welcome to the Elder Law Application</legend>
					<c:set var="authenticated" value="${false}"/>
		<sec:authorize access="hasRole('ROLE_GAAOCAPPS')"> 
 			<c:set var="authenticated" value="${true}"/>
		</sec:authorize>			
		<c:if test="${authenticated == false}">
					<p>Please Login to begin.</p>
		</c:if>
		<c:if test="${authenticated == true}">
		<p>Please select from the menu to the left to begin.</p>
		</c:if>
				</fieldset>
			</div>
		</div>
	</body>
</html>