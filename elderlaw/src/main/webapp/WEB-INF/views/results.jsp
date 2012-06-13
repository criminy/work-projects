<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	
		<%!
		int openCases;
		int closedCases;
		%>
		
		<div class="container"> 
		<%@ include file="header.jsp" %>
			<div class="span-12 last">	
				<fieldset>		
					<legend>Results</legend>
						<table>
							<tr>
								<td>Name:</td>
								<td>County:</td>
								<!-- <td>Areas:</td>  -->
								<td>Open Cases:</td>
								<td>Closed Cases:</td>
								<td>View:</td>
							</tr>
							<c:forEach var="lawyer" items="${results}">
								<%
								openCases = 0;
								closedCases = 0;
								%>
								<c:forEach var="count" items="${lawyer.statuses}">
									<c:if test="${count.status == 'true'}">
										<% openCases++; %>
									</c:if>
									<c:if test="${count.status == 'false'}">
										<% closedCases++; %>
									</c:if>
								</c:forEach>
								<tr>
									<td>${lawyer.name}</td>
									<td>
										<ul>
											<c:forEach var="county" items="${lawyer.counties}">
												<li>${county.name}</li>
											</c:forEach>
										</ul>
									</td>
								<!--	<td>
										<ul>
											<c:forEach var="area" items="${lawyer.areas}">
												<li>${area.name}</li>
											</c:forEach>
										</ul>
									</td> -->
									<td>
										<% out.println(openCases); %>
									</td>
									<td>
										<% out.println(closedCases); %>
									</td>
									<td><a href="profile?lawyer=${lawyer.id}">View</a></td>
								</tr>
							</c:forEach>
						</table>
				</fieldset>
			</div>
		</div>
	</body>
</html>