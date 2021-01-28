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
    <title>Left panel</title>
</head>
<body>
<div class="w3-cell w3-container w3-deep-purple w3-opacity" style="width:15%; height:100vh; min-height: content-box">
    <div class="w3-bar-block">
        <c:if test="${not empty sessionScope.currentUser}">
            <c:choose>
                <c:when test="${sessionScope.currentUser.status.id >2 }">
                    <form class="w3-bar-item w3-large w3-hover-purple" method="GET"
                          action="${pageContext.request.contextPath}/ControllerURL">
                        <input type="hidden" name="command" value="GENRE_MENU_COMMAND">
                        <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"
                                type="submit"><fmt:message key="genre.menu"/>
                        </button>
                    </form>
                </c:when>
            </c:choose>
        </c:if>
        <div class="w3-bar-item w3-large"><fmt:message key="book.genres"/></div>
        <c:forEach var="genre" items="${applicationScope.genres}">
            <form class="w3-bar-item w3-large w3-hover-purple" method="GET"
                  action="${pageContext.request.contextPath}/ControllerURL">
                <input type="hidden" name="command" value="SEARCH_BOOKS_COMMAND">
                <input type="hidden" name="genreId" value="${genre.id}">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit">${genre.name}</button>
            </form>
        </c:forEach>
    </div>
</div>
</body>
</html>
