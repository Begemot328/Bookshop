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
    <title><fmt:message key="authors"/></title>
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
    <c:set var="commandName" value="SEARCH_AUTHORS_COMMAND"/>
    <div class="w3-cell w3-padding-large" style="width:70%">
        <form class="w3-row-padding" method="POST" action="${pageContext.request.contextPath}/ControllerURL">
            <input type="hidden" name="command" value="SEARCH_AUTHORS_COMMAND">
            <div class="w3-col" style="width:15%">
                <input class="w3-input w3-border" type="text" name="author-firstname"
                       placeholder="<fmt:message key="author.firstname"/>">
            </div>
            <div class="w3-col" style="width:15%">
                <input class="w3-input w3-border" type="text" name="author-lastname"
                       placeholder="<fmt:message key="author.lastname"/>">
            </div>
            <div class="w3-col" style="width:25%">
                <input class="w3-input w3-border" type="text" name="title"
                       placeholder="<fmt:message key="book.title"/>">
            </div>
            <div class="w3-col" style="width:10%">
                <input class="w3-input w3-border" type="number" name="min-price"
                       placeholder="<fmt:message key="book.price.min"/>">
            </div>
            <div class="w3-col" style="width:10%">
                <input class="w3-input w3-border" type="number" name="max-price"
                       placeholder="<fmt:message key="book.price.min"/>">
            </div>
            <div class="w3-col" style="width:10%">
                <input class="w3-btn w3-deep-purple w3-ripple w3-hover-purple" type="submit"
                       value="<fmt:message key="find"/>">
            </div>
        </form>
        <div class="w3-row-padding">
            <c:forEach var="author"
                       items="${requestScope.authors}">
                <form class="w3-col l2 m6 s12  w3-center"
                      method="GET" action="${pageContext.request.contextPath}/ControllerURL">
                    <input type="hidden" name="command" value="VIEW_AUTHOR_COMMAND">
                    <input type="hidden" name="authorId" value="${author.id}">
                    <button class="w3-button  w3-ripple">
                        <c:choose>
                            <c:when test = "${not empty author.photoLink}">
                                <img src="${author.photoLink}"
                                     class="w3-image">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/resources/images/author.jpg"
                                     class="w3-image">
                            </c:otherwise>
                        </c:choose>
                    </button>
                    <p class="w3-signal-blue w3-large w3-opacity-min">
                        <c:out value="${author.firstName}"/>
                        <br/>
                        <c:out value="${author.lastName}"/>
                        <br/>
                    </p>
                </form>
            </c:forEach>
        </div>
        <!-- Pagination          -->
        <c:import url="elements/pagination.jsp"/>
    </div>
    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>