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
            // The location of Minsk
            const minsk = {lat: 25.344, lng: 21.036};
            // The map, centered at Uluru
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 4,
                center: minsk,
            });

            //create empty LatLngBounds object
            var bounds = new google.maps.LatLngBounds();
            var infowindow = new google.maps.InfoWindow();

            var locations = [
                <c:forEach var="user" items="${requestScope.users}" varStatus="myIndex">
                ['${user.firstName} ${user.lastName}', '${user.address}', '${user.id}',
                    ${user.address.latitude}, ${user.address.longitude}, ${myIndex.count}],
                </c:forEach>
            ];

            for (i = 0; i < locations.length; i++) {
                var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(locations[i][3], locations[i][4]),
                    map: map
                });

                //extend the bounds to include each marker's position
                bounds.extend(marker.position);

                google.maps.event.addListener(marker, 'click', (function (marker, i) {
                    return function () {
                        var html = '<p>' + '<a href="${pageContext.request.contextPath}/ControllerURL?command=VIEW_USER_COMMAND&userId='
                            + locations[i][2] + '">' + locations[i][0] + '</a></p>'
                            + '<p>' + locations[i][1] + '</p>';
                        infowindow.setContent(html);
                        infowindow.open(map, marker);
                    }
                })(marker, i));
            }

            //now fit the map to the newly inclusive bounds
            map.fitBounds(bounds);
/*
            //(optional) restore the zoom level after the map is done scaling
            var listener = google.maps.event.addListener(map, "idle", function () {
                map.setZoom(20);
                google.maps.event.removeListener(listener);
            }); */
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
    <div class="w3-cell w3-padding-large" style="width:70%">
        <form class="w3-row-padding" method="POST" action="${pageContext.request.contextPath}/ControllerURL">
            <input type="hidden" name="command" value="${commandName}">
            <div class="w3-col" style="width:15%">
                <input class="w3-input w3-border" type="text" name="userFirstname" placeholder="<fmt:message key="firstname"/>">
            </div>
            <div class="w3-col" style="width:15%">
                <input class="w3-input w3-border" type="text" name="userLastname" placeholder="<fmt:message key="lastname"/>">
            </div>
            <div class="w3-col" style="width:10%">
                <input class="w3-btn w3-deep-purple w3-ripple w3-hover-purple" type="submit" value="<fmt:message key="find"/>">
            </div>
        </form>
        <div class="w3-row-padding">
            <c:forEach var="user"
                       items="${requestScope.users}">
                <form class="w3-col l2 m6 s12  w3-center" id="searchForm"
                      method="GET" action="${pageContext.request.contextPath}/ControllerURL">
                    <input type="hidden" name="command" value="${commandName}">
                    <input type="hidden" name="userId" value="${user.id}">
                    <button class="w3-button  w3-ripple">
                        <c:choose>
                            <c:when test = "${not empty user.photoLink}">
                                <img src="${user.photoLink}"
                                     class="w3-image">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/resources/images/user.png"
                                     class="w3-image">
                            </c:otherwise>
                        </c:choose>
                    </button>
                    <p class="w3-signal-blue w3-large w3-opacity-min">
                        <c:out value="${user.firstName}"/>
                        <br/>
                        <c:out value="${user.lastName}"/>
                        <br/>
                    </p>
                </form>
            </c:forEach>
        </div>
        <!-- users map -->
        <div id="map"></div>
        <!-- Pagination          -->
        <c:import url="elements/pagination.jsp"/>
    </div>
    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>