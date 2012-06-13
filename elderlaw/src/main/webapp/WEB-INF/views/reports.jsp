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
	</head>
	<body>
		<div class="container"> 
		<%@ include file="header.jsp" %>
			<div class="span-12 last">	
				<fieldset>		
					<legend>Select Date Range and Agency Filter</legend>
					<form action="reports" method="get">
	
							<input type="text" name="startDate" id="startDate" class="date" value="Date" />
							<input type="text" name="endDate" id="endDate" class="date" value="Date" />
							<select name="agency">
								<option name="selectOne" id="selectOne" value="SelectOne">Filter By:</option>
								<option name="AFLIC" id="AFLIC" value="AFLIC">AFLIC</option>
								<option name="GSLH" id="GSLH" value="GSLH">GSLH</option>
								<option name="AOC" id="AOC" value="AOC">AOC</option>
								<option name="ProbonoGA" id="ProbonoGA" value="ProbonoGA">ProbonoGA</option>
								<option name="GLSP_Albany" id="GLSP_Albany" value="GLSP_Albany">GLSP_Albany</option>
								<option name="GLSP_Augusta" id="GLSP_Augusta" value="GLSP_Augusta">GLSP_Augusta</option>
								<option name="GLSP_Columbus" id="GLSP_Columbus" value="GLSP_Columbus">GLSP_Columbus</option>
								<option name="GLSP_Dalton" id="GLSP_Dalton" value="GLSP_Dalton">GLSP_Dalton</option>
								<option name="GLSP_Gainesville" id="GLSP_Gainesville" value="GLSP_Gainesville">GLSP_Gainesville</option>
								<option name="GLSP_Athens" id="GLSP_Athens" value="GLSP_Athens">GLSP_Athens</option>
								<option name="GLSP_Macon" id="GLSP_Macon" value="GLSP_Macon">GLSP_Macon</option>
								<option name="GLSP_Metro" id="GLSP_Metro" value="GLSP_Metro">GLSP_Metro</option>
								<option name="GLSP_Piedmont" id="GLSP_Piedmont" value="GLSP_Piedmont">GLSP_Piedmont</option>
								<option name="GLSP_Savannah" id="GLSP_Savannah" value="GLSP_Savannah">GLSP_Savannah</option>
								<option name="GLSP_Waycross" id="GLSP_Waycross" value="GLSP_Waycross">GLSP_Waycross</option>
								<option name="GLSP_Brunswick" id="GLSP_Brunswick" value="GLSP_Brunswick">GLSP_Brunswick</option>
							</select>
							<br />
							<input type="submit" value="Show Reports" />
					</form>
					<p>Optional: Select agency name to filter.</p>
				</fieldset>
				<fieldset>
				<legend>Reports</legend>
					<c:if test="${report != null}">
						<table>
							<tr>
								<td>Open cases</td>
								<td>Closed cases</td>
								<td>Average case time in days</td>
							</tr>
							<tr>
								<td>${report.totalOpen}</td>
								<td>${report.totalClosed}</td>
								<td>${report.averageTime}</td>
							</tr>
						</table>
						
						<table>
						<tr>
								<td>
								County:
								</td>
								<td>
								Total:
								</td>
								<td>
								Probate:
								</td>
								<td>
								Power of Attorney:
								</td>
								<td>
								Contract:
								</td>
								<td>
								Collections/Garnishment:
								</td>
								<td>
								Other:
								</td>
							</tr>
							<c:forEach var="reports" items="${report.counties}">
							<tr>
								<td>
								${reports.name}
								</td>
								<td>
								${reports.total}
								</td>
								<td>
								${reports.probate}
								</td>
								<td>
								${reports.powerOfAttorney}
								</td>
								<td>
								${reports.contract}
								</td>
								<td>
								${reports.collectionsGarnishment}
								</td>
								<td>
								${reports.other}
								</td>
							</tr>
								</c:forEach>
						</table>
					
					</c:if>
				</fieldset>
			</div>
		</div>
		
	</body>
</html>