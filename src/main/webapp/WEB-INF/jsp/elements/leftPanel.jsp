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
<div class="w3-cell  w3-deep-purple w3-opacity" style="width:15%">
    <div class="w3-bar-block">
        <div class="w3-bar-item w3-large"><fmt:message key="book.genres"/></div>
        <form class="w3-bar-item w3-large w3-hover-purple" method="GET"
              action="${pageContext.request.contextPath}/ControllerURL">
            <input type="hidden" name="command" value="${SEARCH_BOOKS_COMMAND}">
            <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message key="all"/></button>
        </form>
    </div>
</div>
</body>
</html>
