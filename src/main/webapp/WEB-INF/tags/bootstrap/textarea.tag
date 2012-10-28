<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="label" required="false" type="java.lang.String"%>
<%@attribute name="rows" required="false" type="java.lang.Integer"%>
<%@attribute name="labelMessage" required="false" type="java.lang.String"%>


<c:if test="${not empty labelMessage}">
	<spring:message code="${labelMessage}" var="message"/>
    <c:set var="label" value="${message}" />
</c:if>

<c:if test="${empty rows}">
    <c:set var="rows" value="3" />
</c:if>
<spring:bind path="${path}">
    <div class="control-group ${status.error ? 'error' : '' }">
        <label class="control-label" for="${path}">${label}</label>
        <div class="controls">
            <form:textarea path="${path}" cssClass="${empty cssClass ? 'span3' : cssClass}" rows="${rows}" />
            <c:if test="${status.error}">
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</spring:bind>