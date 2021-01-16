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
    <script>
        let slider = document.getElementById("quantity");
        let output = document.getElementById("quantityOut");
        output.innerHTML = slider.value;
        slider.oninput = function () {
            output.innerHTML = this.value;
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
    <div class="w3-cell w3-padding-large w3-center" style="width:70%">
        <div class="w3-card-4 w3-half w3-center">
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${sessionScope.book.title}</h4>
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/ControllerURL">
                <input type="hidden" name="command" value="VIEW_AUTHOR_COMMAND">
                <input type="hidden" name="authorId" value="${sessionScope.book.author.id}">
                <button class="w3-panel w3-button w3-large w3-purple w3-opacity" type="submit" style="width: 100%">
                    <h4>${sessionScope.position.book.author.firstName} ${sessionScope.position.book.author.lastName}</h4>
                </button>
            </form>
            <c:choose>
                <c:when test="${not empty sessionScope.book.photoLink}">
                    <img src="${sessionScope.book.photoLink}" alt="book picture"
                         class="w3-image">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/images/book_cover.jpg"
                         alt="default book picture" class="w3-image">
                </c:otherwise>
            </c:choose>
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4>${sessionScope.position.book.price} BYN</h4>
            </div>
            <div class="w3-panel w3-large w3-purple w3-opacity">
                <h4><fmt:message key="quantity"/>: ${sessionScope.position.quantity}</h4>
            </div>
        </div>
        <div class="w3-card-4 w3-half w3-center w3-opacity-min w3-purple w3-padding">
            <form>
                <br/>
                <c:if test="${sessionScope.position.status.id == 1}">
                <input type="hidden" name="command" value="BOOK_BOOK_COMMAND">
                <label for="quantity"><fmt:message key="quantity"/> :
                    <span id="quantityOut"></span>
                </label>
                <input type="range" id="quantity" name="quantity" min="1" max="${sessionScope.position.quantity}"
                       value="1" step="1" class="slider">
                <br/>
                <button class="w3-button w3-purple w3-ripple w3-opacity" type="submit">
                    <fmt:message key="position.book" />
                </button>
                </c:if>
                <c:if test="${sessionScope.position.status.id == 3}">
                    <br/>
                    <input type="radio" class="w3-radio" id="cancel-book" name="command" value="CANCEL_BOOK_COMMAND">
                    <label for="cancel-book"><fmt:message key="position.cancel.book"/></label>
                    <br/>
                    <br/>
                    <button class="w3-button w3-purple w3-ripple w3-opacity" type="submit">
                        <fmt:message key="position.process" />
                    </button>
                </c:if>
            </form>
        </div>
    </div>
    <!--  right panel bar  -->
    <c:import url="elements/rightPanel.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>