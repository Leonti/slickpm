<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<spring:message code="confirmationSentTitle" var="title"/>

    <div class="container">

	    <div class="row">
	    	<div class="offset3 span6 alert alert-info">
	  		   <spring:message code="confirmationSent" />	    	
	    	</div>
	    </div>
    </div> <!-- /container -->
