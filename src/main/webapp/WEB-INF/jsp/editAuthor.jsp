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
    <!-- middle panel-->
    <div class="w3-cell w3-padding-large w3-center" style="width:70%">
        <br/>
        <form class="w3-card-4 w3-row-padding" method="POST"
              action="${pageContext.request.contextPath}/ControllerURL"
              enctype="multipart/form-data">
            <input type="hidden" name="command" value="EDIT_AUTHOR_COMMAND">
            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="text" name="authorFirstname" required
                       value="${author.firstName}" placeholder="<fmt:message key="author.firstname"/>">
            </div>
            <div class="w3-container" style="width:35%">
                <input class="w3-input w3-border" type="text" name="authorLastname" required
                       value="${author.lastName}" placeholder="<fmt:message key="author.lastname"/>">
            </div>
            <br/>
            <div class="w3-container" style="width:35%">
                <textarea class="w3-input w3-border" type="text" name="description" value="${sessionScope.author.description}"
                          placeholder="<fmt:message key="description"/>" style="height: 100px" ></textarea>
            </div>
            <br/>
            <!-- photo link adding-->
            <c:import url="elements/addPhoto.jsp" charEncoding="utf-8"/>
            <br/>
            <!-- error message box! -->
            <c:import url="elements/errorMessage.jsp" charEncoding="utf-8"/>
            <div class="w3-btn" style="width:10%">
                <input class="w3-btn w3-deep-purple w3-ripple w3-hover-purple" type="submit"
                       value="<fmt:message key="apply"/>!">
            </div>
        </form>
    </div>

    <!--  right panel bar       -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>