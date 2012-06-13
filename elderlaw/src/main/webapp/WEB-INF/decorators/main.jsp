<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<title>Elder Law - <sitemesh:write property='title'/></title>
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
		<link rel="stylesheet" href="<c:url value="/styles/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.8/themes/base/jquery-ui.css" type="text/css" media="all" />
			<link rel="stylesheet" href="http://static.jquery.com/ui/css/demo-docs-theme/ui.theme.css" type="text/css" media="all" />
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js" type="text/javascript"></script>
			<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.8/jquery-ui.min.js" type="text/javascript"></script>
			<script src="http://jquery-ui.googlecode.com/svn/tags/latest/external/jquery.bgiframe-2.1.2.js" type="text/javascript"></script>
			<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.8/i18n/jquery-ui-i18n.min.js" type="text/javascript"></script>
		
		<script>
			$(function() {
			$( ".date" ).datepicker();
			$.datepicker.setDefaults($.datepicker.regional['']);
			});
		</script>						
		<sitemesh:write property='head'/>
	</head>
	<body>
	<div class="container">  
		<div class="top">
			<img src="<c:url value="/images/banner.jpg" />" />
			</div>
		<div class="column">	
				<fieldset>		
					<legend>Menu</legend>
						<p><a href="<c:url value="/login" />">Login</a></p>
						<p><a href="<c:url value="/search" />">Search</a></p>
						<p><a href="<c:url value="/reports" />">Reports</a></p>
						<p><a href="<c:url value="/j_spring_security_logout"/>">Logout</a></p>
				</fieldset>
			</div>
		<sitemesh:write property='body'/>
		</div>
	</body>
</html>