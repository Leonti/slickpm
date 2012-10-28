<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="taskType.listPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">

	    <div class="row">
	    	<div class="span12">
		    	<a href="/taskType/add"><spring:message code="taskType.addLink" /></a>    	
	    	</div>
		    <div class="span12">
		    	<table class="table table-striped">
		    		<thead>
		    			<tr>
		    				<th><spring:message code="taskType.title" /></th>
		    				<th><spring:message code="taskType.description" /></th>
		    				<th></th>
		    			</tr>
		    		</thead>
		    		<tbody>
		    		<c:forEach var="taskType" items="${list}">
		    			<tr>
		    				<td>${taskType.title}</td>
		    				<td>${taskType.description}</td>
		    				<td><a href="/taskType/edit?id=${taskType.id}"><spring:message code="taskType.editLink" /></a></td>
		    			</tr>	
		    		</c:forEach>
		    		</tbody>
		    	</table>
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />