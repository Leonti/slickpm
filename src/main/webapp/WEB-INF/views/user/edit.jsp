<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="user.editPageTitle" var="title"/>    
<jsp:include page="../common/header.jsp" flush="true" >
	<jsp:param name="title" value="${title}" />
	<jsp:param name="js" value="user" /> 
</jsp:include>

    <div class="container">

	    <div class="row">

		    <div class="span4 offset4">
				<form:form modelAttribute="userForm" method="POST" class="well">
				
					<bootstrap:input path="name" labelMessage="user.name" />
					
					<div class="form-actions">
						<spring:message code="user.editSubmit" var="submit" /> 
						<input type="submit" value="${submit}" class="btn btn-primary btn-large" />
					</div>		
				</form:form>

				<form id="avatarupload" action="/file/upload" method="POST" enctype="multipart/form-data" data-userid="${userId}">
					<div class="avatar">
						<div class="image fileinput-button">
							<div class="change"><spring:message code="user.changeAvatar" /></div>
							<img class="background" src="${avatarImage}" />
							<input type="file" name="file" multiple>
						</div>
					</div>
				</form>
    
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../common/footer.jsp" flush="true" />