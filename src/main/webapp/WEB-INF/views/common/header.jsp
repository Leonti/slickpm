<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title><c:out value="${param.title}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->

	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/themes/base/jquery-ui.css" type="text/css" media="all" />
    <link href="../resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../resources/css/main.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

	<script data-main="../resources/js/<c:out value="${param.js}" default="main" />" src="../resources/js/require-jquery.js"></script>

  </head>
  <body>
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
            <ul class="nav">
	            <li><a href="/project/list"><spring:message code="navigation.projects" /></a></li>	                       
            </ul>
            
            <ul class="nav pull-right">
	            <li class="divider-vertical"></li>  
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<spring:message code="navigation.administration" />
						<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li><a href="/user/list"><spring:message code="navigation.users" /></a></li>
						<li><a href="/taskstage/list"><spring:message code="navigation.taskStages" /></a></li>  
						<!-- <li class = "divider"></li>
						<li><a href="/auth/logout"></a></li> -->
					</ul> 
				</li>            
            </ul>	
             
          </div><!--/.nav-collapse -->
        </div>
      </div>
      </div>

  
  
    
