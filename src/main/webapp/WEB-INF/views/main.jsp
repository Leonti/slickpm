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

	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/themes/base/jquery-ui.css" type="text/css" media="all" />
    <link href="../resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../resources/css/main.css" rel="stylesheet">
    <link href="../resources/css/jquery.jqplot.css" rel="stylesheet">

	<script data-main="../resources/js/main" default="main" src="../resources/js/lib/require/require.js"></script>
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
    
    <div id="dashboard" class="container">
    </div>
      
	<div class="container-fluid" id="taskboard"></div>

    <div id="projectView" class="container">

		<div id="task" class="span6 offset3">
		</div>

		<div class="row">
			<div id="projectHeader"></div>
		</div>

		<div class="row">
			<div class="span6">
				<a class="addTaskLink" href="#" style="display: none;">Add task</a>			
			</div>
			<div class="span6">
				<a class="addIterationLink" href="#" style="display: none;">Add iteration</a>			
			</div>			
		</div>
		<div class="row">	
			<div class="span6">
				<div id="addTask"></div>
			</div>
			<div class="span6 offset6">
				<div id="addIteration"></div>
			</div>			
		</div>

	    <div class="row">	    
		    <div id="backlog" class="span6" ></div>    
		    <div id="iterations" class="span6"></div>    	    
	    </div>
	</div>
	    
     		<div class="footerPush"></div>     
     	</div><!-- wrapper div -->

     	<div class="footer">
     		<a href="http://leonti.me">Leonti Bielski</a>
     	</div>
	</body>
</html>