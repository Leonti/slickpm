<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="task.detailsPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" /> 
</jsp:include>

    <div class="container">

	    <div class="row">
	    	<h2>${task.title}</h2>
	    	<div class="span12 well">
	    		${task.description}<br>
	    		${task.user.name}
	    	</div>
		    
		    <c:forEach var="comment" items="${task.comments}">
			<div class="span6">
				${comment.content}
			</div>
		    </c:forEach>
		    
		    Dependencies:
		    <c:forEach var="dependency" items="${task.dependsOn}">
			<div class="span12">
				${dependency.title}
			</div>
		    </c:forEach>		    
	    </div>
	    <div class="row">
	    	<div class="span12">
				<form:form modelAttribute="commentForm" method="POST" class="well" action="/task/addComment?id=${task.id}">
				
					<bootstrap:textarea path="content" labelMessage="comment.content" rows="5" />
					
					<div class="form-actions">
						<spring:message code="comment.addSubmit" var="submit" /> 
						<input type="submit" value="${submit}" class="btn" />
					</div>		
				</form:form>				   	
	    	</div>	    	
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />