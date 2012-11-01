<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="taskStage.listPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">

	    <div class="row">
	    	<div class="span12">
		    	<a href="/taskstage/add"><spring:message code="taskStage.addLink" /></a>    	
	    	</div>
		    <div class="span12">
		    	<table class="table table-striped">
		    		<thead>
		    			<tr>
		    				<th><spring:message code="taskStage.title" /></th>
		    				<th><spring:message code="taskStage.description" /></th>
		    				<th></th>
		    				<th></th>
		    			</tr>
		    		</thead>
		    		<tbody>
		    		<c:forEach var="taskStage" items="${list}">
		    			<tr>
		    				<td>${taskStage.title}</td>
		    				<td>${taskStage.description}</td>
		    				<td><a href="/taskstage/edit?id=${taskStage.id}"><spring:message code="taskStage.editLink" /></a></td>
		    				<td><a href="/taskstage/delete?id=${taskStage.id}"><spring:message code="taskStage.deleteLink" /></a></td>
		    			</tr>	
		    		</c:forEach>
		    		</tbody>
		    	</table>
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />