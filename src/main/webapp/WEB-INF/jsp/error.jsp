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
    <title><fmt:message key="error.title"/></title>
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