<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="project.scrumPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">

	    <div class="row">
		    <div class="span6">
		    	<h2><spring:message code="backlog" /></h2>
		    	<a href="/task/add?projectId=${projectId}"><spring:message code="task.addLink" /></a>  

	    		<c:forEach var="task" items="${backlogList}">
	    			<div class="span6">
	    				<a href="/task/details?id=${task.id}">${task.title}</a>
	    			</div>
					<form:form modelAttribute="iterationTaskForm" action="/iteration/addTask?taskId=${task.id}" method="POST" class="form-inline">
						<form:select path="iterationId" items="${iterationList}" itemValue="id" itemLabel="title" />							
						<spring:message code="task.addToIteration" var="submit" /> 
						<input type="submit" value="${submit}" class="btn" />		
					</form:form>						
	    		</c:forEach>
		    </div>
		    <div class="span6">
		    	<h2><spring:message code="iterations" /></h2>
		    	<a href="/iteration/add?projectId=${projectId}"><spring:message code="iteration.addLink" /></a>  
		    	
	    		<c:forEach var="iteration" items="${iterationList}">
	    			<div class="span6">
	    				<a href="/iteration/details?id=${iteration.id}">${iteration.title}</a>
	    			</div>
	    			<c:forEach var="task" items="${iteration.tasks}">
	    				<div class="span6">
	    					<a href="/task/details?id=${task.id}">${task.title}</a>
	    				</div>
	    			</c:forEach>	
	    		</c:forEach>		    			    
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />