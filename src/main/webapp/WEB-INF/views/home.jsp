<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="bootstrap" tagdir="/WEB-INF/tags/bootstrap" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
     
<jsp:include page="common/header.jsp" flush="true" >
	<jsp:param name="title" value="SlickPM home" /> 
</jsp:include>

    <div class="container">
   
	    <div class="row">

			<div class="span8">
				<h1>Welcome to SlickPM</h1>

			</div>

	    </div>

    </div> <!-- /container -->
    
<jsp:include page="common/footer.jsp" flush="true" />
