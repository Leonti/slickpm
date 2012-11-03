<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="iteration.taskboardPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">
		<h2>${iteration.title}</h2>
		<div class="row">
			<div class="span2">
				<spring:message code="iteration.pointsPlanned" />: ${plannedPoints}<br> 
				<spring:message code="iteration.pointsDone" />: ${donePoints}
			</div>
		</div>

	    <div class="row">
			<c:forEach var="taskStage" items="${taskStageList}">
		    <div class="span2">		    
	        	<h3>${taskStage.title}</h3>
	        	<c:forEach var="task" items="${tasks[taskStage]}">
	        		<div>${task.title}</div>
					<form:form modelAttribute="taskTaskStageForm" action="/iteration/changeTaskStage?taskId=${task.id}" method="POST" class="form-inline">
						<form:select path="taskStageId" items="${taskStageList}" itemValue="id" itemLabel="title" />							
						<spring:message code="iteration.changeTaskStage" var="submit" /> 
						<input type="submit" value="${submit}" class="btn" />		
					</form:form>						        		
	        	</c:forEach>
		    </div>
			</c:forEach>
	    </div>

    </div> <!-- /container -->
    
    

<jsp:include page="../common/footer.jsp" flush="true" />