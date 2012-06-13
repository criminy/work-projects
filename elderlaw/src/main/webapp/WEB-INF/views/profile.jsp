<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Elder Law</title>
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
	
		<script type="text/javascript">
		function clearText(field){

		    if (field.defaultValue == field.value) field.value = '';
		    else if (field.value == '') field.value = field.defaultValue;

		}

		</script>
		<SCRIPT LANGUAGE="JavaScript">
      function confirmCloseAction() {
        return confirm("Are you sure you want to close this case?")
      }
   
</SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
      function confirmOpenAction() {
        return confirm("Is all the case information correct?")
      }
   
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
      function confirmDeleteAction() {
        return confirm("Are you sure you wish to delete this?")
      }
   
</SCRIPT>
	</head>
	<body>
		<div class="container">  
		<%@ include file="header.jsp" %>
			<div class="span-12 last">	
				<fieldset>		
					<legend>Profile</legend>
					<table>
							<tr>
								<td>Name:</td>
								<td>Counties:</td>
								<!-- <td>Legal Areas:</td>-->
								<td>Address:</td>
								<td>Email:</td>
								<td>Phone:</td>
								<td>BarID:</td>
								<td>State Bar Standing:</td>
								<td>Malpractice Insurance:</td>
								<td>Willing to Speak/Prepare Presentation</td>
							</tr>
							<tr>
								<td>
									<ul><li>${lawyerProfile.name}</li></ul>
								</td>
								<td>
									<ul>
										<c:forEach var="county" items="${lawyerProfile.counties}">
											<li>${county.name}</li>
										</c:forEach>
									</ul>
								</td>
							<!-- <td>
									<ul>
										<c:forEach var="area" items="${lawyerProfile.areas}">
											<li>${area.name}</li>
										</c:forEach>
									</ul>
								</td> -->
								<td>
									<ul><li>${lawyerProfile.address}</li></ul>
								</td>
								<td>
									<ul><li>${lawyerProfile.email}</li></ul>
								</td>
								<td>
									<ul><li>${lawyerProfile.phone}</li></ul>
								</td>
								<td>
									<ul><li>${lawyerProfile.barNumber}</li></ul>
								</td>
								<td>
									<ul><li>${lawyerProfile.standing}</li></ul>
								</td>
								<td>
									<c:if test="${lawyerProfile.insurance == 'false'}">
										<ul><li>No</li></ul>
									</c:if>
									<c:if test="${lawyerProfile.insurance == 'true'}">
										<ul><li>Yes</li></ul>
									</c:if>
								</td>
								<td>
									<c:if test="${lawyerProfile.presentation == 'false'}">
										<ul><li>No</li></ul>
									</c:if>
									<c:if test="${lawyerProfile.presentation == 'true'}">
										<ul><li>Yes</li></ul>
									</c:if>
								</td>
							</tr>
						</table>
						<div>
							<fieldset>		
								<legend>Closed Cases</legend>
						<ul>
							<c:forEach var="closed" items="${lawyerProfile.statuses}">
								<c:if test="${closed.status == 'false'}">
									
										<li><form action="case/delete?lawyer=${lawyerProfile.id}&id=${closed.id}" method="post" onSubmit="return confirmDeleteAction()">${closed.firstName}&nbsp;${closed.lastName}&nbsp;-&nbsp;${closed.agency}&nbsp;-&nbsp;${closed.county.name} County&nbsp;-&nbsp;${closed.area.name} Case: ${closed.startDate}&nbsp;-&nbsp;${closed.endDate}&nbsp;-&nbsp;Story:&nbsp;-&nbsp;<c:if test="${closed.story == 'true'}">Yes</c:if><c:if test="${closed.story == 'false'}">No</c:if><input type="submit" value="Delete" /></form></li>
									
								</c:if>
							</c:forEach>
							</ul>
							</fieldset>
						</div>
							<div>
							<fieldset>		
								<legend>Open Cases - Close/Complete a Case</legend>
							<ul>
							<c:forEach var="open" items="${lawyerProfile.statuses}">
								<c:if test="${open.status == 'true'}">
									
										<li><form action="case/close?lawyer=${lawyerProfile.id}&id=${open.id}" method="post" onSubmit="return confirmCloseAction()">${open.firstName}&nbsp;${open.lastName}&nbsp;-&nbsp;${open.agency}&nbsp;-&nbsp;${open.county.name} County&nbsp;-&nbsp;${open.area.name} Case: ${open.startDate}&nbsp;Story:&nbsp;<select name="story"><option>Yes</option><option>No</option></select><input type="text" name="date" class="date" value="Date" /><input type="submit" value="Close" /></form></li>
									
								</c:if>
							</c:forEach>
						</ul>
						</fieldset>
						</div>
						<div>
							<fieldset>		
								<legend>New Cases - Assign/Open a Case</legend>
						<form action="case/open?lawyer=${lawyerProfile.id}" method="post" onSubmit="return confirmOpenAction()">
							Client Name:
							<input type="text" name="firstName" value="First Name" onFocus="clearText(this)" onBlur="clearText(this)" />
							<input type="text" name="lastName" value="Last Name" onFocus="clearText(this)" onBlur="clearText(this)"/>
							<select name="county">
								<c:forEach var="county" items="${lawyerProfile.counties}">
									<option>${county.name}</option>
								</c:forEach>
							</select>
							<select name="area">
								<c:forEach var="area" items="${lawyerProfile.areas}">
									<option>${area.name}</option>
								</c:forEach>
							</select>
							<input type="text" name="date" class="date" value="Date" />
							<input type="submit" value="Open" />
						</form>
						</fieldset>
						</div>
						<fieldset>		
								<legend>Comments</legend>
								<table>
								<c:forEach var="comment" items="${lawyerProfile.comments}">
									<tr>
										<td>${comment.date} - ${comment.user}: ${comment.comment}</td>
									</tr>
								</c:forEach>
								</table>
						
						<form action="comment?lawyer=${lawyerProfile.id}" method="post">
							<textarea rows="10" cols="30" name="comment"></textarea>
							<br />
							<input type="submit" value="Submit" />
						</form>
						</fieldset>
						</div>
				</fieldset>
			</div>
		</div>
	</body>
</html>