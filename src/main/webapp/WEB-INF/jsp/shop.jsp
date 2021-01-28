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
    <title><c:out value="${requestScope.shop.name}"/></title>
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
            defer>
    </script>
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
    <!-- Center panel -->
    <c:set var="commandName" value="VIEW_SHOP_COMMAND"/>
    <div class="w3-cell w3-container w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-half w3-center">
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${requestScope.shop.name}</h4>
            </div>
            <c:choose>
                <c:when test="${not empty requestScope.shop.photoLink}">
                    <img src="${requestScope.shop.photoLink}"
                         class="w3-image" alt="shop picture" style="min-height: 500px;min-width: 500px">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/images/shop.jpg"
                         class="w3-image" alt="default shop picture" style="min-height: 500px;min-width: 500px">
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
                            <fmt:message key="change"/>
                        </button>
                    </form>
                </div>
            </c:if>
        </c:if>
        <!-- position table -->
        <c:import url="elements/positionTableShop.jsp"/>
        <!-- Pagination          -->
        <c:import url="elements/pagination.jsp"/>
    </div>
    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>