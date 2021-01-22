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
    <title><c:out value="${requestScope.user.firstName} ${requestScope.user.lastName}"/></title>
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
            // The location of Minsk
            const address = {lat: ${requestScope.user.address.latitude}, lng: ${requestScope.user.address.longitude}};
            // The map, centered at Uluru
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 8,
                center: address,
            });

            var marker = new google.maps.Marker({
                position: address,
                map: map,
            });

            var infowindow = new google.maps.InfoWindow();

            google.maps.event.addListener(marker, 'click', (function (marker) {
                return function () {
                    var html = '<p>${requestScope.user.address}</p>';
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
    <c:set var="commandName" value="VIEW_USER_COMMAND"/>
    <div class="w3-cell w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-half w3-center">
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${requestScope.user.firstName} ${requestScope.user.lastName}</h4>
            </div>

            <c:choose>
                <c:when test="${not empty requestScope.user.photoLink}">
                    <img src="${requestScope.user.photoLink}" alt="user picture"
                         class="w3-image">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/images/user.png"
                    alt="default author picture"  class="w3-image">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="w3-card-4 w3-half w3-center">
            <!-- positions map -->
            <div id="map"></div>
        </div>
        <!-- position table -->
        <table class="w3-table-all w3-purple w3-opacity-min">
            <tr class="w3-deep-purple">
                <th><fmt:message key="shop"/></th>
                <th><fmt:message key="book.author"/></th>
                <th><fmt:message key="book.title"/></th>
                <th><fmt:message key="quantity"/></th>
                <th><fmt:message key="price"/></th>
                <th><fmt:message key="date"/></th>
                <th><fmt:message key="status"/></th>
                <c:if test="${sessionScope.currentUser.status.id > 2}">
                    <c:choose>
                        <c:when test="${requestScope.user.status.id == 2}">
                            <th><fmt:message key="seller"/></th>
                        </c:when>
                        <c:when test="${requestScope.user.status.id > 2}">
                            <th><fmt:message key="buyer"/></th>
                        </c:when>
                    </c:choose>
                </c:if>
            </tr>
            <c:forEach var="action"
                       items="${requestScope.actions}">
                    <tr class="w3-deep-purple">
                        <td><a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_SHOP_COMMAND&shopId=${action.shop.id}">
                                <c:out value="${action.shop.name}"/></a></td>
                        <td><a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_BOOK_COMMAND&bookId=${action.initialPosition.book.id}">
                            <c:out value="${action.initialPosition.book.title}"/></a></td>
                        <td><a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_AUTHOR_COMMAND&authorId=${action.initialPosition.book.author.id}">
                            <c:out value="${action.initialPosition.book.author.firstName}
                        ${action.initialPosition.book.author.lastName}"/></a></td>
                        <td><c:out value="${action.quantity}"/></td>
                        <td><c:out value="${action.currentPrice}"/></td>
                        <td><c:out value="${action.date}"/></td>
                        <td><fmt:message key="${action.initialStatus}.${action.finalStatus}"/></td>
                        <c:if test="${sessionScope.currentUser.status.id > 2}">
                            <c:choose>
                                <c:when test="${requestScope.user.status.id == 2}">
                                    <th><c:out value="${action.seller.firstName}
                                         ${action.seller.lastName}"/></th>
                                </c:when>
                                <c:when test="${requestScope.user.status.id > 2}">
                                    <th><c:out value="${action.buyer.firstName}
                                     ${action.buyer.lastName}"/></th>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </tr>
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