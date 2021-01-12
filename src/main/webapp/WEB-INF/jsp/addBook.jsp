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

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/w3.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/w3-colors-signal.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/materialIcons.css">
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

    <script>
        $('#i_file').change(function (event) {
            var tmppath = URL.createObjectURL(event.target.files[0]);
            $("img").fadeIn("fast").attr('src', URL.createObjectURL(event.target.files[0]));

            $("#disp_tmp_path").html("Temporary Path(Copy it and try pasting it in browser address bar) --> <strong>[" + tmppath + "]</strong>");
        });
    </script>
</head>
<body>
<!-- Top panel-->
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
                        <i class="material-icons w3-xxlarge">search</i>
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
                        <form class="w3-bar-item" action="${pageContext.request.contextPath}/ControllerURL">
                            <input type="hidden" name="command" value="SEARCH_SHOPS_COMMAND">
                            <button class="w3-button  w3-ripple w3-hover-purple">
                                <span><fmt:message key="find.shop"/></span>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="w3-dropdown-hover">
                    <button class="w3-button w3-purple w3-opacity-min">
                        <i class="material-icons w3-xxlarge">person</i>
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
                            <form class="w3-bar-item" method="POST"
                                  action="${pageContext.request.contextPath}/ControllerURL">
                                <input type="hidden" name="command" value="VIEW_USER_COMMAND">
                                <input type="hidden" name="userId" value="${sessionScope.currentUser.id}">
                                <button class="w3-button  w3-ripple w3-hover-purple">
                                    <span><fmt:message key="cabinet"/></span>
                                </button>
                            </form>
                        </c:if>
                    </div>
                </div>
                <div class="w3-dropdown-hover">
                    <button class="w3-button w3-purple w3-opacity-min">
                        <svg style="width:32px;height:32px" viewBox="0 0 24 24">
                            <path fill="currentColor"
                                  d="M17.9,17.39C17.64,16.59 16.89,16 16,16H15V13A1,1 0 0,0 14,12H8V10H10A1,1 0 0,0 11,9V7H13A2,2 0 0,0 15,5V4.59C17.93,5.77 20,8.64 20,12C20,14.08 19.2,15.97 17.9,17.39M11,19.93C7.05,19.44 4,16.08 4,12C4,11.38 4.08,10.78 4.21,10.21L9,15V16A2,2 0 0,0 11,18M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z"/>
                        </svg>
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
    <!-- middle panel-->
    <div class="w3-cell w3-padding-large w3-center" style="width:70%">
        <br/>
        <form class="w3-row-padding" method="POST" action="${pageContext.request.contextPath}/ControllerURL"
              id="searchForm" enctype="multipart/form-data">
            <!--  enctype="multipart/form-data" -->
            <input type="hidden" name="command" value="ADD_BOOK_COMMAND">
            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="text" name="title" required value="${title}"
                       placeholder="<fmt:message key="book.title"/>">
            </div>
            <br/>
            <div class="w3-container" style="width:35%">
                <select id="users" name="authorId">
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}">${author.firstName} ${author.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <br/>

            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="number" step="0.01" min="0" name="price" required
                       value="${price}"
                       placeholder="<fmt:message key="price"/>">
            </div>
            <br/>
            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="text" name="description" value="${description}"
                       placeholder="<fmt:message key="description"/>">
            </div>
            <br/>
            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="url" name="photolink" value="${photolink}"
                       placeholder="<fmt:message key="photolink"/>">
            </div>

            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border w3-button w3-purple" type="file" name="file" id="i_file"
                       placeholder="<fmt:message key="photolink"/>">
            </div>
            <br/>
            <c:if test="${errorMessage != null}">
                <div class="w3-container" style="width:50%">
                    <p class="w3-large w3-text-red w3-animate-opacity"><fmt:message key="${errorMessage}"/></p>
                </div>
            </c:if>
            <div class="w3-btn" style="width:10%">
                <input class="w3-btn w3-deep-purple w3-ripple w3-hover-purple" type="submit"
                       value="<fmt:message key="create"/>!">
            </div>
        </form>
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
</div>
</body>
</html>