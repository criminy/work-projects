<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="column">	
	<fieldset>		
		<legend>Menu</legend>
		<c:set var="authenticated" value="${false}"/>
		<sec:authorize access="hasRole('ROLE_GAAOCAPPS')"> 
 			<c:set var="authenticated" value="${true}"/>
		</sec:authorize>			
		<c:if test="${authenticated == true}">
			<p><a href="<c:url value="/j_spring_security_logout"/>">Logout (<sec:authentication property="principal.username" />)</a></p>
		</c:if>
		<c:if test="${authenticated == false}">
			<p><a href="<c:url value="/login" />">Login</a></p>
		</c:if>
		<p><a href="<c:url value="/search" />">Search</a></p>
		<p><a href="<c:url value="/reports" />">Reports</a></p>
	</fieldset>
</div>