<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <div class="container">

	    <div class="row">
	    	<div class="offset3 span6 alert alert-block">
	  		   <spring:message code="${messageCode}" />	    	
	    	</div>
	    	<div class="offset3 span6">
	    		<form:form method="POST">
	    		
					<spring:message code="confirmationOk" var="submit" /> 
					<input type="submit" value="${submit}" class="btn btn-primary btn-large" />

					<a href="${backUrl}" class="btn"><spring:message code="confirmationCancel" /></a>							    		
	    		</form:form>
	    	</div>
	    </div>
    </div> <!-- /container -->