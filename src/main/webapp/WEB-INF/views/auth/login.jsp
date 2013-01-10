<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 

<jsp:include page="../header_no_menu.jsp" flush="true" />

    <div class="container">

	<c:if test="${not empty param.error}">
	    <div class="row">
	    	<div class="offset3 span6 alert alert-error">
	  		   Authentication error	    	
	    	</div>
	    </div>
	</c:if>	    
	    <div class="row">

		    <div class="span4 offset4">
				<form method="POST" class="well" action="../j_spring_security_check">
			
				    <div class="control-group">
				        <label class="control-label" for="j_username">Username</label>
				        <div class="controls">
				            <input id="j_username" name="j_username" type="text" />
				        </div>
				    </div>
				    <div class="control-group">
				        <label class="control-label" for="j_password">Password</label>
				        <div class="controls">
				            <input id="j_password" name="j_password" type="password" />
				        </div>
				    </div>			
			
					<div class="control-group">
						<div class="controls">
						<label class="checkbox">
							<input type="checkbox" name='_spring_security_remember_me' checked="checked"> 
							Remember me
						</label>
						</div>
					</div>
					
					<div class="form-actions">					 
						<input type="submit" value="Login" class="btn btn-primary btn-large" /><br><br>
						<a href="/account/forgot">Forgot your password?</a>
					</div>	
				</form>	    
		    </div>
	    </div>

    </div> <!-- /container -->
    
<jsp:include page="../footer.jsp" flush="true" />    