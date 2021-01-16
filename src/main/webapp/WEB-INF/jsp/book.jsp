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

        .slider {
            -webkit-appearance: none;
            width: 80%;
            height: 25px;
            background: #9370DB;
            outline: none;
            opacity: 0.7;
            -webkit-transition: .2s;
            transition: opacity .2s;
        }

        .slider:hover {
            opacity: 1;
        }

        .slider::-webkit-slider-thumb {
            -webkit-appearance: none;
            appearance: none;
            width: 25px;
            height: 25px;
            background: #800080;
            cursor: pointer;
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
            const minsk = {lat: 25.344, lng: 21.036};
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 4,
                center: minsk,
            });

            var bounds = new google.maps.LatLngBounds();
            var infowindow = new google.maps.InfoWindow();

            var locations = [
                <c:forEach var="shop" items="${requestScope.shops}" varStatus="myIndex">
                ['${shop.name}', '${shop.address}', '${shop.id}',
                    ${shop.address.latitude}, ${shop.address.longitude}, ${myIndex.count}],
                </c:forEach>
            ];

            for (i = 0; i < locations.length; i++) {
                var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(locations[i][3], locations[i][4]),
                    map: map
                });
                bounds.extend(marker.position);
                google.maps.event.addListener(marker, 'click', (function (marker, i) {
                    return function () {
                        var html = '<p>' + '<a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_SHOP_COMMAND&shopId='
                            + locations[i][2] + '">' + locations[i][0] + '</a></p>'
                            + '<p>' + locations[i][1] + '</p>';
                        infowindow.setContent(html);
                        infowindow.open(map, marker);
                    }
                })(marker, i));
            }

            //now fit the map to the newly inclusive bounds
            map.fitBounds(bounds);
        }
    </script>
</head>
<body>
<!-- Top panel-->
<c:import url="elements/header.jsp" charEncoding="utf-8"/>
<!--  Columns -->
<div class="w3-cell-row">
    <!-- left panel-->
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
    <c:set var="commandName" value="VIEW_BOOK_COMMAND"/>
    <!-- Center panel -->
    <div class="w3-cell w3-container w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-twothird w3-center">
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${requestScope.book.title}</h4>
            </div>
            <form method="GET" action="${pageContext.request.contextPath}/ControllerURL">
                <input type="hidden" name="command" value="VIEW_AUTHOR_COMMAND">
                <input type="hidden" name="authorId" value="${requestScope.book.author.id}">
                <button class="w3-panel w3-button w3-large w3-purple w3-opacity" type="submit" style="width: 100%">
                    <h4>${requestScope.book.author.firstName} ${requestScope.book.author.lastName}</h4>
                </button>

            </form>
            <c:choose>
                <c:when test="${not empty requestScope.book.photoLink}">
                    <img src="${requestScope.book.photoLink}" alt="book picture"
                         class="w3-image">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/images/book_cover.jpg"
                         alt="default book picture" class="w3-image">
                </c:otherwise>
            </c:choose>
            <c:if test="${not empty requestScope.book.description}">
                <div class="w3-panel w3-medium w3-purple w3-opacity">
                    <h5>${requestScope.book.description}</h5>
                </div>
            </c:if>
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${requestScope.book.price} BYN</h4>
            </div>
        </div>
        <c:if test="${not empty sessionScope.currentUser}">
            <c:if test="${sessionScope.currentUser.status.id > 1}">
                <div class="w3-card-4 w3-third w3-center w3-padding">
                    <br/>
                    <br/>
                    <form>
                        <input type="hidden" name="command" value="EDIT_BOOK_MENU_COMMAND">
                        <input type="hidden" name="bookId" value="${requestScope.book.id}">
                        <button class="w3-button w3-purple w3-ripple w3-large w3-opacity-off w3-hover-deep-purple"
                                type="submit">
                            <fmt:message key="change"></fmt:message>
                        </button>
                    </form>
                </div>
            </c:if>
        </c:if>
        <!-- positions map -->
        <c:if test="${not empty requestScope.shops}">
            <div id="map"></div>
            <!-- position table -->
            <table class="w3-table-all w3-purple w3-opacity-min">
                <tr class="w3-deep-purple">
                    <th><fmt:message key="shop"/></th>
                    <th><fmt:message key="shop.address"/></th>
                    <th><fmt:message key="quantity"/></th>
                    <c:if test="${not empty sessionScope.currentUser}">
                        <c:choose>
                            <c:when test="${sessionScope.currentUser.status.id == 2}">
                                <th></th>
                            </c:when>
                            <c:when test="${sessionScope.currentUser.status.id >2 }">
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
                                    <c:out value="${position.shop.name}"/></a></td>
                            <td><c:out value="${position.shop.address}"/></td>
                            <td><c:out value="${position.quantity}"/></td>
                            <c:if test="${not empty sessionScope.currentUser}">
                                <c:choose>
                                    <c:when test="${sessionScope.currentUser.status.id == 2}">
                                        <td>
                                            <a href="${pageContext.request.contextPath}/ControllerURL?command=PROCESS_POSITION_COMMAND&shopId=${position.shop.id}&positionId=${position.id}"
                                               class="w3-button w3-hover-purple"><fmt:message key="position.book"/></a>
                                        </td>
                                    </c:when>
                                    <c:when test="${sessionScope.currentUser.status.id > 2}">
                                        <td><fmt:message key="${position.status}"/></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/ControllerURL?command=PROCESS_POSITION_COMMAND&shopId=${position.shop.id}&positionId=${position.id}"
                                               class="w3-button w3-hover-purple"><fmt:message
                                                    key="position.process"/></a>
                                        </td>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:if>
        <!-- Pagination          -->
        <c:import url="elements/pagination.jsp"/>
    </div>
    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>