<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 

<jsp:include page="header.jsp" flush="true">
	<jsp:param name="script" value="main" />
</jsp:include>
    
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
	    	<h3 class="span6">Backlog</h3><h3 class="span6">Iteartions</h3>	 		
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
		    <div id="backlog" class="span6"></div>    
		    <div id="iterations" class="span6"></div>    	    
	    </div>
	</div>

<jsp:include page="footer.jsp" flush="true" />	