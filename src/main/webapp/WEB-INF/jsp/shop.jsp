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
<!-- Top panel-->
<c:import url="elements/header.jsp" charEncoding="utf-8"/>
<div class="w3-cell-row">
    <!-- left panel-->
    <c:import url="elements/leftPanel.jsp" charEncoding="utf-8"/>
    <!-- middle panel-->
    <c:set var="commandName" value="VIEW_SHOP_COMMAND"/>
    <div class="w3-cell w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-half w3-center">
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${sessionScope.shop.name}</h4>
            </div>

            <c:choose>
                <c:when test="${not empty requestScope.shop.photoLink}">
                    <img src="${requestScope.shop.photoLink}"
                         class="w3-image" alt="shop picture">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/images/shop.jpg"
                         class="w3-image" alt="default shop picture">
                </c:otherwise>
            </c:choose>
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${requestScope.shop.address}</h4>
            </div>
        </div>
        <div class="w3-card-4 w3-half w3-center">
            <form>
                <!-- positions map -->
                <div id="map"></div>
                <br/>
                <c:if test="${sessionScope.currentUser.status.id == 4}">
                    <input type="hidden" name="command" value="OPTIMIZE_SHOP_COMMAND">
                    <input type="hidden" name="shopId" value="${requestScope.shop.id}">
                    <br/>
                    <button class="w3-button w3-purple w3-large w3-ripple w3-opacity-off" type="submit">
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
                        <input type="hidden" name="shopId" value="${requestScope.shop.id}">
                        <button class="w3-button w3-purple w3-ripple w3-large w3-opacity-off" type="submit">
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
                            <a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_BOOK_COMMAND&shopId=${position.book.id}">
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
        <c:import url="elements/pagination.jsp"/>
    </div>
    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>