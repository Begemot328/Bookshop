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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<html>
<head>
    <title><fmt:message key="login_page.title"/></title>
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
<div class="w3-container w3-stretch">
    <div class="w3-cell-row w3-purple w3-opacity-min">
        <div class="w3-cell w3-container" style="width: 65%">
            <h1 class="w3-jumbo" style="text-shadow: 5px 5px indigo;">B.B.B.</h1>
            <h3 class="w3-xxlarge" style="text-shadow: 5px 5px indigo;">Best Belorussian Bookshop</h3>
        </div>
        <div class="w3-cell w3-container w3-cell-middle">
            <div class="w3-cell-row">
                <div class="w3-dropdown-hover">
                    <button class="w3-button w3-purple w3-opacity-min">
                        <i class="material-icons">search</i>
                    </button>
                    <div class="w3-dropdown-content w3-bar-block w3-deep-purple">
                        <form class="w3-bar-item">
                            <button class="w3-button  w3-ripple w3-hover-purple">
                                <span><fmt:message key="find.book"/></span>
                            </button>
                        </form>
                        <form class="w3-bar-item">
                            <button class="w3-button  w3-ripple w3-hover-purple">
                                <span><fmt:message key="find.author"/></span>
                            </button>
                        </form>
                        <form class="w3-bar-item">
                            <button class="w3-button  w3-ripple w3-hover-purple">
                                <span><fmt:message key="find.shop"/></span>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="w3-dropdown-hover">
                    <button class="w3-button w3-purple w3-opacity-min">
                        <i class="material-icons">person</i>
                        <c:if test="${sessionScope.user != null}">
                            <span><c:out value="${sessionScope.user.firstName}"/> <c:out
                                    value="${sessionScope.user.lastName}"/></span>
                        </c:if>
                    </button>
                    <div class="w3-dropdown-content w3-bar-block w3-deep-purple">
                        <c:if test="${sessionScope.user == null}">
                            <form class="w3-bar-item">
                                <button class="w3-button  w3-ripple w3-hover-purple">
                                    <span><fmt:message key="register"/></span>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.user == null}">
                            <form class="w3-bar-item" method="POST"
                                  action="${pageContext.request.contextPath}/ControllerURL">
                                <input type="hidden" name="command" value="SIGNIN_COMMAND">
                                <button class="w3-button  w3-ripple w3-hover-purple" type="submit">
                                    <span><fmt:message key="signin"/></span>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                            <form class="w3-bar-item">
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
                        <form class="w3-bar-item" method="POST"
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
    <div class="w3-cell w3-padding-large" style="width:70%">
        <form class="w3-row-padding" method="POST" action="${pageContext.request.contextPath}/ControllerURL">
            <input type="hidden" name="command" value="LOGIN_COMMAND">
            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="text" name="login" placeholder="<fmt:message key="login"/>">
            </div>
            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="password" name="password"
                       placeholder="<fmt:message key="password"/>">
            </div>
            <c:if test="${errorMessage != null}">
                <div class="w3-container" style="width:50%">
                    <p class="w3-large w3-text-red w3-animate-opacity"><fmt:message key="${errorMessage}"/></p>
                </div>
            </c:if>
            <div class="w3-btn" style="width:10%">
                <input class="w3-btn w3-deep-purple w3-ripple w3-hover-purple" type="submit"
                       value="<fmt:message key="signin"/>!">
            </div>

        </form>
    </div>

    <div class="w3-cell w3-deep-purple w3-opacity-min w3-cell" style="width:15%">
        <div class="w3-bar-block">
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message key="books"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message key="shops"/></button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple"><fmt:message key="users"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>