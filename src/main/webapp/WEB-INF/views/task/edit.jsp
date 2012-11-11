<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="task.editPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">

	    <div class="row">

		    <div class="span4 offset4">		    
				<form:form modelAttribute="taskForm" method="POST" class="well">				
				
					<bootstrap:input path="title" labelMessage="task.title" />
					
					<form:select path="taskTypeId" items="${taskTypeList}" itemValue="id" itemLabel="title" />
					<bootstrap:textarea path="description" labelMessage="project.description" rows="5" />
					<form:select path="pointsId" items="${pointList}" itemValue="id" itemLabel="title" />
					
					<div class="form-actions">
						<spring:message code="task.editSubmit" var="submit" /> 
						<input type="submit" value="${submit}" class="btn btn-primary btn-large" />
					</div>		
				</form:form>
				
				<form:form modelAttribute="taskUserForm" action="/task/assignToUser?id=${task.id}" method="POST" class="form-inline">
					<form:select path="userId" items="${userList}" itemValue="id" itemLabel="name" />							
					<spring:message code="task.assignToUser" var="submit" /> 
					<input type="submit" value="${submit}" class="btn" />		
				</form:form>
				
				<form:form modelAttribute="taskDependencyForm" action="/task/addDependency?id=${task.id}" method="POST" class="form-inline">
					<form:select path="dependencyId" items="${taskList}" itemValue="id" itemLabel="title" />							
					<spring:message code="task.addDependency" var="submit" /> 
					<input type="submit" value="${submit}" class="btn" />		
				</form:form>													        
		    </div>
	    </div>

    </div> <!-- /container -->
    
    

<jsp:include page="../common/footer.jsp" flush="true" />