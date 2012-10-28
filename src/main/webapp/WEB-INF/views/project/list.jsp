<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="project.listPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">

	    <div class="row">
	    	<div class="span12">
		    	<a href="/project/add"><spring:message code="project.addLink" /></a>    	
	    	</div>
		    <div class="span12">
		    	<table class="table table-striped">
		    		<thead>
		    			<tr>
		    				<th><spring:message code="project.title" /></th>
		    				<th><spring:message code="project.description" /></th>
		    				<th></th>
		    				<th></th>
		    			</tr>
		    		</thead>
		    		<tbody>
		    		<c:forEach var="project" items="${list}">
		    			<tr>
		    				<td>${project.title}</td>
		    				<td>${project.description}</td>
		    				<td><a href="/project/scrum?id=${project.id}"><spring:message code="project.scrumView" /></a></td>
		    				<td><a href="/project/edit?id=${project.id}"><spring:message code="project.editLink" /></a></td>
		    			</tr>	
		    		</c:forEach>
		    		</tbody>
		    	</table>
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />