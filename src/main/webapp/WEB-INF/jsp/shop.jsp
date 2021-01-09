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

    <!-- map -->
    <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCwIrGRwhU9pynlNeOMDXPqQYZmwroni4Q&callback=initMap&libraries=&v=weekly"
            defer
    ></script>
    <style type="text/css">
        /* Set the size of the div element that contains the map */
        #map {
            height: 400px;
            /* The height is 400 pixels */
            width: 100%;
            /* The width is the width of the web page */
        }
    </style>
    <script>
        // Initialize and add the map
        function initMap() {
            // The location of address
            const address = {lat: ${sessionScope.shop.address.latitude}, lng: ${sessionScope.shop.address.longitude}};
            // The map, centered at address
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 8,
                center: address,
            });

            var infowindow = new google.maps.InfoWindow();

            var marker = new google.maps.Marker({
                position: address,
                map: map,
            });



            google.maps.event.addListener(marker, 'click', (function (marker) {
                return function () {
                    var html = '<p>${sessionScope.shop.address}</p>';
                    infowindow.setContent(html);
                    infowindow.open(map, marker);
                };
            })(marker));
        }
    </script>
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
                         <svg style="width:32px;height:32px" viewBox="0 0 24 24">
                            <path fill="currentColor" d="M17.9,17.39C17.64,16.59 16.89,16 16,16H15V13A1,1 0 0,0 14,12H8V10H10A1,1 0 0,0 11,9V7H13A2,2 0 0,0 15,5V4.59C17.93,5.77 20,8.64 20,12C20,14.08 19.2,15.97 17.9,17.39M11,19.93C7.05,19.44 4,16.08 4,12C4,11.38 4.08,10.78 4.21,10.21L9,15V16A2,2 0 0,0 11,18M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z" />
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
    <c:set var="commandName" value="VIEW_SHOP_COMMAND"/>
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
                <!-- positions map -->
                <div id="map"></div>
                <br/>
                <c:if test="${sessionScope.currentUser.status.id == 4}">
                    <input type="hidden" name="command" value="OPTIMIZE_SHOP_COMMAND">
                    <br/>
                    <button class="w3-button w3-purple w3-ripple w3-opacity-min" type="submit">
                        <fmt:message key="shop.optimize"/></button>
                </c:if>
            </form>
        </div>
        <c:if test="${not empty sessionScope.currentUser}">
            <c:if test="${sessionScope.currentUser.status.id > 1}">
                <div class="w3-card-4 w3-half w3-center w3-padding">
                    <br/>
                    <br/>
                    <form>
                        <input type="hidden" name="command" value="EDIT_SHOP_MENU_COMMAND">
                        <button class="w3-button w3-purple w3-ripple w3-large w3-opacity" type="submit">
                            <fmt:message key="change"></fmt:message>
                        </button>
                    </form>

                </div>
            </c:if>
        </c:if>
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
                            <a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_SHOP_COMMAND&shopId=${position.shop.id}">
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
        <!-- Pagination          -->
        <div class="w3-bar w3-purple w3-opacity-min w3-center w3-stretch">
            <c:choose>
                <c:when test="${requestScope.pageQuantity} > 1">
                    <c:forEach begin="1" end="${requestScope.pageQuantity}" var="p">
                        <c:choose>
                            <c:when test="${requestScope.currentPage == p}">
                                <a class="w3-button w3-indigo"
                                   href="${pageContext.request.contextPath}/ControllerURL?command=${commandName}&page=${p}">${p}</a>
                            </c:when>
                            <c:otherwise>
                                <a class="w3-button w3-purple"
                                   href="${pageContext.request.contextPath}/ControllerURL?command=${commandName}&page=${p}">${p}</a>
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
            </c:if>
        </div>
    </div>
</div>
</body>
</html>