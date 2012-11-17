<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="iteration.taskboardPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" />
	<jsp:param name="js" value="taskboard" /> 	 
</jsp:include>

    <div class="container-fluid" id="taskboard" data-iterationid="${iteration.id}">
		
		<div class="row-fluid">
			<div class="span10">
			<h2>${iteration.title}</h2>
			</div>
			<div class="span1">
				<spring:message code="iteration.pointsPlanned" />:<br>
				<spring:message code="iteration.pointsDone" />:
			</div>
			<div class="span1">
				<span class="badge plannedPoints"></span><br> 
				<span class="badge donePoints"></span>
			</div>			
		</div>

		<div class="stages">

				<c:forEach var="taskStage" items="${taskStageList}">
			    <div class="column">		    
		        	<h3>${taskStage.title}</h3>
		        	<div class="taskList" data-taskstageid="${taskStage.id}">
			        	<c:forEach var="task" items="${tasks[taskStage]}">
			        		<div class="task" id="task-${task.id}">
			        			<div class="user">
			        				<img src="${avatars[task]}" />
			        			</div>
			        			<div class="details">
			        				<a href="/task/details?id=${task.id}" class="title">${task.title}</a>  
			        			</div>
			        		</div>					        		
			        	</c:forEach>	        	
		        	</div>
			    </div>
				</c:forEach>	

		</div>

    </div> <!-- /container -->
    
    

<jsp:include page="../common/footer.jsp" flush="true" />