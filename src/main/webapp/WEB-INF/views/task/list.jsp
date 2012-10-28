<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="task.listPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">

	    <div class="row">
	    	<div class="span12">
		    	<a href="/task/add?projectId=${projectId}"><spring:message code="task.addLink" /></a>    	
	    	</div>
		    <div class="span12">
		    	<table class="table table-striped">
		    		<thead>
		    			<tr>
		    				<th><spring:message code="task.title" /></th>
		    				<th><spring:message code="task.description" /></th>
		    				<th><spring:message code="task.type" /></th>
		    				<th></th>
		    				<th></th>
		    			</tr>
		    		</thead>
		    		<tbody>
		    		<c:forEach var="task" items="${list}">
		    			<tr>
		    				<td>${task.title}</td>
		    				<td>${task.description}</td>
		    				<td>${task.taskType.title}</td>
		    				<td><a href="/task/edit?id=${task.id}"><spring:message code="task.editLink" /></a></td>
		    				<td><a href="/task/delete?id=${task.id}"><spring:message code="task.deleteLink" /></a></td>
		    			</tr>	
		    		</c:forEach>
		    		</tbody>
		    	</table>
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />