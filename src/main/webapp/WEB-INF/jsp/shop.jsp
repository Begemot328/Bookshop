<%--
  Created by IntelliJ IDEA.
  User: WorkPC
  Date: 12.12.2020
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<c:if test="${not empty sessionScope.language}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>

<c:if test="${empty sessionScope.language}">
    <fmt:setLocale value="EN"/>
</c:if>

<fmt:setBundle basename="locale"/>

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-signal.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<html>
<head>
    <title>Search books</title>
    <style>
        body {
            display: block;
            background-image: url("${pageContext.request.contextPath}/resources/images/library.jpg");

            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
            backdrop-filter: sepia(30%);
        }

        body, h1, h2, h3, h4, h5, h6 {
            font-family: Arial, Helvetica, sans-serif;
        }

    </style>
</head>
<body>
<!-- Top panel -->
<div class="w3-container w3-stretch">
    <div class="w3-cell-row w3-purple w3-opacity-min">
        <div class="w3-cell w3-container" style="width: 65%">
            <h1 class="w3-jumbo" style="text-shadow: 5px 5px indigo;">B.B.B.</h1>
            <h3 class="w3-xxlarge" style="text-shadow: 5px 5px indigo;">Best Belorussian Bookshop</h3>
        </div>
        <div class="w3-cell w3-container w3-cell-middle">
            <div class="w3-cell-row">
                <!-- Dropdown menus -->
                <div class="w3-dropdown-hover">
                    <button class="w3-button w3-purple w3-opacity-min">
                        <i class="material-icons">search</i>
                    </button>
                    <div class="w3-dropdown-content w3-bar-block w3-deep-purple">
                        <form class="w3-bar-item" action="${pageContext.request.contextPath}/ControllerURL">
                            <input type="hidden" name="command" value="SEARCH_BOOKS_COMMAND">
                            <button class="w3-button  w3-ripple w3-hover-purple">
                                <span><fmt:message key="find.book"/></span>
                            </button>
                        </form>
                        <form class="w3-bar-item" action="${pageContext.request.contextPath}/ControllerURL">
                            <input type="hidden" name="command" value="SEARCH_AUTHORS_COMMAND">
                            <button class="w3-button  w3-ripple w3-hover-purple">
                                <span><fmt:message key="find.author"/></span>
                            </button>
                        </form>
                        <form class="w3-bar-item" method="POST"
                              action="${pageContext.request.contextPath}/ControllerURL">
                            <input type="hidden" name="command" value="SEARCH_SHOPS_COMMAND">
                            <button class="w3-button  w3-ripple w3-hover-purple">
                                <span><fmt:message key="find.shop"/></span>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="w3-dropdown-hover">
                    <button class="w3-button w3-purple w3-opacity-min">
                        <i class="material-icons">person</i>
                        <c:if test="${sessionScope.currentUser != null}">
                            <span><c:out value="${sessionScope.currentUser.firstName}"/> <c:out
                                    value="${sessionScope.currentUser.lastName}"/></span>
                        </c:if>
                    </button>
                    <div class="w3-dropdown-content w3-bar-block w3-deep-purple">
                        <c:if test="${sessionScope.currentUser == null}">
                            <form class="w3-bar-item" method="POST"
                                  action="${pageContext.request.contextPath}/ControllerURL">
                                <input type="hidden" name="command" value="REGISTER_MENU_COMMAND">
                                <button class="w3-button  w3-ripple w3-hover-purple">
                                    <span><fmt:message key="register"/></span>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.currentUser == null}">
                            <form class="w3-bar-item" method="POST"
                                  action="${pageContext.request.contextPath}/ControllerURL">
                                <input type="hidden" name="command" value="SIGNIN_COMMAND">
                                <button class="w3-button  w3-ripple w3-hover-purple" type="submit">
                                    <span><fmt:message key="signin"/></span>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.currentUser != null}">
                            <form class="w3-bar-item" method="POST"
                                  action="${pageContext.request.contextPath}/ControllerURL">
                                <input type="hidden" name="command" value="LOGOUT_COMMAND">
                                <button class="w3-button  w3-ripple w3-hover-purple">
                                    <span><fmt:message key="signout"/></span>
                                </button>
                            </form>
                        </c:if>
                    </div>
                </div>
                <div class="w3-dropdown-hover">
                    <button class="w3-button w3-purple w3-opacity-min">
                        <i class="fa fa-globe"></i>
                    </button>
                    <div class="w3-dropdown-content w3-bar-block w3-deep-purple">
                        <form class="w3-bar-item" method="GET"
                              action="${pageContext.request.contextPath}/ControllerURL">
                            <input type="hidden" name="command" value="CHANGE_LOCALE_COMMAND">
                            <input type="hidden" name="language" value="RU">
                            <button class="w3-button  w3-ripple w3-hover-purple" type="submit">
                                <span><fmt:message key="language.ru"/></span>
                            </button>
                        </form>
                        <form class="w3-bar-item" method="POST"
                              action="${pageContext.request.contextPath}/ControllerURL">
                            <input type="hidden" name="command" value="CHANGE_LOCALE_COMMAND">
                            <input type="hidden" name="language" value="EN">
                            <button class="w3-button  w3-ripple w3-hover-purple" type="submit">
                                <span><fmt:message key="language.en"/></span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="w3-cell-row">
    <div class="w3-cell  w3-deep-purple w3-opacity" style="width:15%">
        <div class="w3-bar-block">
            <div class="w3-bar-item w3-large"><fmt:message key="book.genres"/></div>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message key="all"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message
                        key="book.detectives"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message
                        key="book.fantastic"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message
                        key="book.classic"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message
                        key="book.romance"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message key="book.humor"/></button>
            </form>
        </div>
    </div>
    <div class="w3-cell w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-half w3-center">
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${sessionScope.shop.name}</h4>
            </div>

            <c:choose>
                <c:when test="${not empty sessionScope.shop.photoLink}">
                    <img src="${sessionScope.shop.photoLink}"
                         class="w3-image" alt="shop picture">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/images/shop.jpg"
                         class="w3-image" alt="default shop picture">
                </c:otherwise>
            </c:choose>
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${sessionScope.shop.address}</h4>
            </div>
        </div>
        <div class="w3-card-4 w3-half w3-center">
            <form>
                <br/>
                <c:if test="${sessionScope.currentUser.status.id == 4}">
                    <input type="hidden" name="command" value="OPTIMIZE_SHOP_COMMAND">
                    <br/>
                    <button class="w3-button w3-purple w3-ripple w3-opacity-min" type="submit">
                        <fmt:message key="shop.optimize"/></button>
                </c:if>
            </form>
        </div>
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
                       begin="${sessionScope.firstElement}"
                       end="${sessionScope.lastElement}"
                       items="${sessionScope.positions}">
                <c:if test="${position.status.id == 1
                || sessionScope.currentUser.status.id == 3
                || sessionScope.currentUser.status.id == 4}">
                    <tr class="w3-deep-purple">
                        <td>
                            <a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_SHOP_COMMAND&shop-id=${position.shop.id}">
                                <c:out value="${position.book.title}"/></a></td>
                        <td><c:out value="${position.book.author.firstName} ${position.book.author.lastName}"/></td>
                        <td><c:out value="${position.quantity}"/></td>
                        <c:if test="${not empty sessionScope.currentUser}">
                            <c:choose>
                                <c:when test="${sessionScope.currentUser.status.id == 2}">
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ControllerURL?command=PROCESS_POSITION_COMMAND&shop-id=${position.shop.id}&position-id=${position.id}"
                                           class="w3-button"><fmt:message key="position.book"/></a></td>
                                </c:when>
                                <c:when test="${sessionScope.currentUser.status.id > 2}">
                                    <td><fmt:message key="${position.status}"/></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ControllerURL?command=PROCESS_POSITION_COMMAND&shop-id=${position.shop.id}&position-id=${position.id}"
                                           class="w3-button"><fmt:message key="position.process"/></a></td>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
        <div class="w3-bar w3-purple w3-opacity-min w3-center w3-stretch">
            <c:choose>
                <c:when test="${sessionScope.pageQuantity} > 1">
                    <c:forEach begin="1" end="${sessionScope.pageQuantity}" var="p">
                        <c:choose>
                            <c:when test="${sessionScope.currentPage == p}">
                                <a class="w3-button w3-indigo"
                                   href="${pageContext.request.contextPath}/ControllerURL?command=CHANGE_PAGE_COMMAND&page=${p}">${p}</a>
                            </c:when>
                            <c:otherwise>
                                <a class="w3-button w3-purple"
                                   href="${pageContext.request.contextPath}/ControllerURL?command=CHANGE_PAGE_COMMAND&page=${p}">${p}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>
            </c:choose>
        </div>
    </div>
    <!--  right panel bar       -->
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
                <input type="hidden" name="command" value="SEARCH_AUTHOR_COMMAND">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                        key="authors"/></button>
            </form>
            <c:if test="${not empty sessionScope.currentUser && sessionScope.currentUser.status.id > 1}">
                <form class="w3-bar-item w3-large w3-hover-purple">
                    <input type="hidden" name="command" value="SEARCH_USERS_COMMAND">
                    <button class="w3-button w3-bar-item w3-ripple w3-hover-purple" type="submit"><fmt:message
                            key="users"/></button>
                </form>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>