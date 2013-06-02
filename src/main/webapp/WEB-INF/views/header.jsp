<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>   
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
  
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Slick PM</title>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">   
    
    <link href="../resources/css/main.css" rel="stylesheet">
    <link href="../resources/css/jquery.jqplot.css" rel="stylesheet">
    <link href="../resources/css/custom-theme/jquery-ui.min.css" rel="stylesheet">

	<script data-main="../resources/js/<c:out value="${param.script}"/>" src="../resources/js/lib/require/require.js"></script>
</head>
<body>
	<div id="user" data-user='${user}'></div>
	
  	<div class="wrapper">
      <div class="navbar navbar-fixed-top">
	      <div class="navbar-inner">
	        <div class="container-fluid">
	
	          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </a>
	          <a class="brand" href="/"><spring:message code="navigation.brand" /></a>
	          <div class="nav-collapse">
	            
	            <ul class="nav pull-right">
	            	<li><a href="/admin"><spring:message code="navigation.administration" /></a></li>
	            	<sec:authorize access="hasRole('ROLE_USER')">	 
			            <li class="divider-vertical"></li>  
						<li><a href="/auth/logout"><spring:message code="navigation.logout" /></a></li>
					</sec:authorize>
	            </ul>	
	             
	          </div><!--/.nav-collapse -->
	        </div>
	      </div>
      </div>	