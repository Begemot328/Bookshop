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
    <title>Position table</title>
</head>
<body>
<c:if test="${not empty requestScope.positions}">
    <table class="w3-table-all w3-purple w3-opacity-min">
        <tr class="w3-deep-purple">
            <th><fmt:message key="book.title"/></th>
            <th><fmt:message key="book.author"/></th>
            <th><fmt:message key="quantity"/></th>
            <c:if test="${not empty sessionScope.currentUser}">
                <c:choose>
                    <c:when test="${sessionScope.currentUser.status.id == 2}">
                        <th></th>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.status.id >2}">
                        <th><fmt:message key="status"/></th>
                        <th></th>
                    </c:when>
                </c:choose>
            </c:if>
        </tr>

        <c:forEach var="position"
                   items="${requestScope.positions}">
            <c:if test="${position.status.id == 1
                || sessionScope.currentUser.status.id == 3
                || sessionScope.currentUser.status.id == 4}">
                <tr class="w3-deep-purple">
                    <td>
                        <a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_BOOK_COMMAND&bookId=${position.book.id}">
                            <c:out value="${position.book.title}"/></a></td>
                    <td><c:out value="${position.book.author.firstName} ${position.book.author.lastName}"/></td>
                    <td><c:out value="${position.quantity}"/></td>
                    <c:if test="${not empty sessionScope.currentUser}">
                        <c:choose>
                            <c:when test="${sessionScope.currentUser.status.id == 2}">
                                <td>
                                    <a href="${pageContext.request.contextPath}/ControllerURL?command=PROCESS_POSITION_COMMAND&shopId=${position.shop.id}&positionId=${position.id}"
                                       class="w3-button"><fmt:message key="position.book"/></a></td>
                            </c:when>
                            <c:when test="${sessionScope.currentUser.status.id > 2}">
                                <td><fmt:message key="${position.status}"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/ControllerURL?command=PROCESS_POSITION_COMMAND&shopId=${position.shop.id}&positionId=${position.id}"
                                       class="w3-button"><fmt:message key="position.process"/></a></td>
                            </c:when>
                        </c:choose>
                    </c:if>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
