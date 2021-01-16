<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${not empty sessionScope.language}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<c:if test="${empty sessionScope.language}">
    <fmt:setLocale value="EN"/>
</c:if>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title>Pagination</title>
</head>
<body>
<c:if test="${errorMessage != null}">
    <div class="w3-container w3-panel w3-red" style="width:50%">
        <p class="w3-large w3-animate-opacity"><fmt:message key="${errorMessage}"/></p>
    </div>
</c:if>
</body>
</html>
