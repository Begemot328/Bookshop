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
<div class="w3-cell w3-deep-purple w3-opacity" style="width:15%">
    <div class="w3-bar-block">
        <form class="w3-bar-item w3-large w3-hover-purple">
            <input type="hidden" name="command" value="SEARCH_BOOKS_COMMAND">
            <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                    key="books"/></button>
        </form>
        <form class="w3-bar-item w3-large w3-hover-purple">
            <input type="hidden" name="command" value="SEARCH_SHOPS_COMMAND">
            <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                    key="shops"/></button>
        </form>
        <form class="w3-bar-item w3-large w3-hover-purple">
            <input type="hidden" name="command" value="SEARCH_AUTHORS_COMMAND">
            <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                    key="authors"/></button>
        </form>
        <c:if test="${not empty sessionScope.currentUser && sessionScope.currentUser.status.id > 1}">
            <form class="w3-bar-item w3-large w3-hover-purple">
                <input type="hidden" name="command" value="SEARCH_USERS_COMMAND">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                        key="users"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <input type="hidden" name="command" value="ADD_BOOK_MENU_COMMAND">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                        key="book.add"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <input type="hidden" name="command" value="ADD_AUTHOR_MENU_COMMAND">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                        key="author.add"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <input type="hidden" name="command" value="ADD_POSITION_MENU_COMMAND">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                        key="position.add"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <input type="hidden" name="command" value="ADD_SHOP_MENU_COMMAND">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                        key="shop.add"/></button>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>
