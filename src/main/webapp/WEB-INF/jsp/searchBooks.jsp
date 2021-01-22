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
    <title><fmt:message key="books"/></title>
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
    <!-- middle panel-->
    <c:set var="commandName" value="SEARCH_BOOKS_COMMAND"/>
    <c:set var="formId" value="searchForm" scope="request"/>
    <c:set var="divId" value="searchForm" scope="request"/>
    <div class="w3-cell w3-padding-large" style="width:70%">
        <form method="GET" action="${pageContext.request.contextPath}/ControllerURL" id="${formId}">
            <input type="hidden" name="command" value="${commandName}">
            <div class="w3-row-padding">
                <div class="w3-col" style="width:15%">
                    <input class="w3-input w3-border" type="text" name="authorFirstname" value="${authorFirstname}"
                           placeholder="<fmt:message key="author.firstname"/>">
                </div>
                <div class="w3-col" style="width:15%">
                    <input class="w3-input w3-border" type="text" name="authorLastname" value="${authorLastname}"
                           placeholder="<fmt:message key="author.lastname"/>">
                </div>
                <div class="w3-col" style="width:25%">
                    <input class="w3-input w3-border" type="text" name="title" value="${title}"
                           placeholder="<fmt:message key="book.title"/>">
                </div>
                <div class="w3-col" style="width:10%">
                    <input class="w3-input w3-border" type="number" min="0" name="minPrice" value="${minPrice}"
                           placeholder="<fmt:message key="book.price.min"/>">
                </div>
                <div class="w3-col" style="width:10%">
                    <input class="w3-input w3-border" type="number" min="0" name="maxPrice" value="${maxPrice}"
                           placeholder="<fmt:message key="book.price.max"/>">
                </div>

                <div class="w3-col" style="width:10%">
                    <input class="w3-btn w3-deep-purple w3-ripple w3-hover-purple" type="submit"
                           value="<fmt:message key="find"/>">
                </div>
            </div>
            <br/>
            <!-- for genres -->
                <c:forEach var="genre" items="${requestScope.bookGenres}">
                    <input name="genreId" form="${formId}" id="input_${genre.id}" type="hidden" value="${genre.id}"/>
                    <button class="w3-button w3-padding w3-deep-purple w3-ripple w3-hover-purple"
                            id="btn_${genre.id}" value="btn_${genre.id}"
                            onclick="removeItem(this)">${genre.name}</button>
                </c:forEach>
        </form>
        <c:import url="elements/genres.jsp" />
        <br/>

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