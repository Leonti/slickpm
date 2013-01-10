<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<jsp:include page="header.jsp" flush="true">
	<jsp:param name="script" value="admin" />
</jsp:include>

		<div class="container" id="admin">
			<div class="projects row">
				<h3><a href="#projects">Projects</a></h3>
				<div class="listContainer span3"></div>
				<div class="details span3"></div>
			</div>
			<div class="stages row">
				<h3><a href="#stages">Task stages</a></h3>
				<div class="listContainer span3"></div>
				<div class="details span3"></div>
			</div>	
		</div>
	
<jsp:include page="footer.jsp" flush="true" />	