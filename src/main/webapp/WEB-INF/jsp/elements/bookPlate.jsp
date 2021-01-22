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
    <title>Book plate</title>
</head>
<body>
<div class="w3-row-padding">
    <c:forEach var="book"
               items="${requestScope.books}">
        <form class="w3-col l2 m6 s12  w3-center"
              method="GET" action="${pageContext.request.contextPath}/ControllerURL">
            <input type="hidden" name="command" value="VIEW_BOOK_COMMAND">
            <input type="hidden" name="bookId" value="${book.id}">
            <button class="w3-button  w3-ripple">
                <c:choose>
                    <c:when test="${not empty book.photoLink}">
                        <img src="${book.photoLink}"
                             class="w3-image">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/resources/images/book_cover.jpg"
                             class="w3-image">
                    </c:otherwise>
                </c:choose>
            </button>
            <p class="w3-signal-blue w3-large w3-opacity-min">
                <c:out value="${book.title}"/>
                <br/>
                <c:out value="${book.author.firstName}"/>
                <c:out value="${book.author.lastName}"/>
                <br/>
                <c:out value="${book.price}"/> BYN
            </p>
        </form>
    </c:forEach>
</div>
</body>
</html>
