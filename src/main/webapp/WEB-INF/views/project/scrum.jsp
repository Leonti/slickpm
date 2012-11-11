<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="project.scrumPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" />
	<jsp:param name="js" value="scrum" /> 
</jsp:include>

    <div class="container">

	    <div class="row">
		    <div class="span6">
		    	<h2><spring:message code="backlog" /></h2>
		    	<a href="/task/add?projectId=${projectId}"><spring:message code="task.addLink" /></a>  

				<div id="backlog" class="taskList">
		    		<c:forEach var="task" items="${backlogList}">
		        		<div class="task" id="backlogTask-${task.id}">
		        			<div class="user">
		        				<img src="http://www.abload.de/img/me_gusta_moar_random_s9u9s.png" />
		        			</div>
		        			<div class="details">
		        				<a href="/task/details?id=${task.id}" class="title">${task.title}</a> 
		        			</div>
		        		</div>							
		    		</c:forEach>				
				</div>
		    </div>
		    <div class="span6">
		    	<h2><spring:message code="iterations" /></h2>
		    	<a href="/iteration/add?projectId=${projectId}"><spring:message code="iteration.addLink" /></a>  
		    	
		    	<div id="iterations">
		    		<c:forEach var="iteration" items="${iterationList}">
		    			<div class="iteration">
		    				<div class="title">
		    					<a href="/iteration/taskboard?id=${iteration.id}">${iteration.title}</a>		    				
		    				</div>
		    			</div>
		    			<div class="taskList" data-iterationid="${iteration.id}"> 			
		    			<c:forEach var="task" items="${sortedIterationTasks[iteration]}">		    			
			        		<div class="task" id="iterationTask-${task.id}">
			        			<div class="user">
			        				<img src="http://www.abload.de/img/me_gusta_moar_random_s9u9s.png" />
			        			</div>
			        			<div class="details">
			        				<a href="/task/details?id=${task.id}" class="title">${task.title}</a> 
			        			</div>
			        		</div>			    				
		    			</c:forEach>
		    			</div>		    				
		    		</c:forEach>		    	
		    	</div>		    			    
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />