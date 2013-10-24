<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" flush="true">
	<jsp:param name="script" value="main-built" />
</jsp:include>

<div id="dashboard" class="container"></div>

<div class="container-fluid" id="taskboard"></div>

<div id="projectView" class="container" style="display: none;">

	<div id="task" class="span6 offset3"></div>

	<div class="row">
		<div id="projectHeader"></div>
	</div>

	<div class="row">
		<div class="span6"></div>
		<div class="span6"></div>
	</div>

	<div class="row project-controls">
		<div class="span6">
			<span class="caption">Backlog</span>
			<a class="addTaskLink pull-right btn btn-primary" href="#" style="display: none;"
			title="Add new task to backlog"><i class="icon-plus"></i>
			Add task</a>
		</div>

		<div class="span6">
			<span class="caption">Iteartions</span> 
			<a class="addIterationLink pull-right btn btn-primary" href="#" style="display: none;"
			title="Add new iteration to the project"><i class="icon-plus"></i>
			Add iteration</a>
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
		<div id="backlog" class="span6"></div>
		<div id="iterations" class="span6"></div>
	</div>
</div>

<jsp:include page="footer.jsp" flush="true" />
