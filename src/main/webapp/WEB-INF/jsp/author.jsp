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
    <title><c:out value="${requestScope.author.firstname} ${requestScope.author.lastname}"/></title>
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
<!--  Columns -->
<div class="w3-cell-row">
    <!-- left panel-->
    <c:import url="elements/leftPanel.jsp" charEncoding="utf-8"/>

    <c:set var="commandName" value="VIEW_AUTHOR_COMMAND"/>
    <!-- Center panel -->
    <div class="w3-cell w3-container w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-twothird w3-center">
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${requestScope.author.firstName} ${requestScope.author.lastName}</h4>
            </div>
            <c:choose>
                <c:when test="${not empty requestScope.author.photoLink}">
                    <img src="${requestScope.author.photoLink}" alt="author picture"
                         class="w3-image">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/images/author.jpg"
                    alt="default author picture"  class="w3-image">
                </c:otherwise>
            </c:choose>
            <c:if test="${not empty requestScope.author.description}">
                <div class="w3-panel w3-medium w3-purple w3-opacity">
                    <h5>${requestScope.author.description}</h5>
                </div>
            </c:if>
        </div>
        <c:if test="${not empty sessionScope.currentUser}">
            <c:if test="${sessionScope.currentUser.status.id > 1}">
                <div class="w3-card-4 w3-third w3-center w3-padding">
                    <br/>
                    <br/>
                    <form>
                        <input type="hidden" name="command" value="EDIT_AUTHOR_MENU_COMMAND">
                        <input type="hidden" name="authorId" value="${requestScope.author.id}">
                        <button class="w3-button w3-purple w3-ripple w3-large w3-hover-deep-purple"
                                type="submit">
                            <fmt:message key="change"/>
                        </button>
                    </form>
                </div>
            </c:if>
        </c:if>
        <!-- Book plate          -->
        <c:import url="elements/bookPlate.jsp"/>
        <!-- Pagination          -->
        <c:import url="elements/pagination.jsp"/>
    </div>
    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>