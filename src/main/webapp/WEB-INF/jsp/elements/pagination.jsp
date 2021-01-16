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
<div class="w3-bar w3-purple w3-opacity-min w3-center w3-stretch">
    <c:choose>
        <c:when test="${requestScope.pageQuantity > 1}">
            <c:forEach begin="1" end="${requestScope.pageQuantity}" var="p">
                <c:choose>
                    <c:when test="${requestScope.currentPage == p}">
                        <button class="w3-button w3-indigo" form="searchForm"
                                type="submit" name="page" value="${p}">${p}</button>
                    </c:when>
                    <c:otherwise>
                        <button class="w3-button w3-purple" form="searchForm"
                                type="submit" name="page" value="${p}">${p}</button>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:when>
    </c:choose>
</div>
</body>
</html>
