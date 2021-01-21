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
</head>
<body>
<!-- Top panel-->
<c:import url="elements/header.jsp" charEncoding="utf-8"/>
<div class="w3-cell-row">
    <!-- left panel-->
    <c:import url="elements/leftPanel.jsp" charEncoding="utf-8"/>

    <!-- Center panel-->
    <div class="w3-cell w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-center">
            <div class="w3-panel w3-large w3-red">
                <h4><fmt:message key="error.spotted"/></h4>
                <h4><c:out value="${requestScope.error}"/></h4>
                <c:forEach var="trace" items="${sessionScope.error.stackTrace}">
                    <p><c:out value="${trace.toString()}"/></p>
                </c:forEach>
            </div>
        </div>
    </div>
    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>