<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<jsp:include page="../header.jsp" flush="true" />

    <div class="container">

	    <div class="row">
		    
		    <div class="span4 offset4">
				<form:form modelAttribute="changePasswordForm" method="POST" action="savepassword" class="well">
				
					<bootstrap:input path="password" labelMessage="register.password" type="password" />
					<bootstrap:input path="repeatPassword" labelMessage="register.repeatPassword" type="password" />
					
					<div class="form-actions">
						<spring:message code="edit.changePassword" var="submit" /> 
						<input type="submit" value="${submit}" class="btn btn-primary btn-large" />
					</div>		
				</form:form>	        
		    </div>		    
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../footer.jsp" flush="true" />    
