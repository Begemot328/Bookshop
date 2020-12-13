<%--
  Created by IntelliJ IDEA.
  User: WorkPC
  Date: 12.12.2020
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<html>
<head>
    <title>Search books</title>
    <style>
        body {
            display: block;
            background: green url("${pageContext.request.contextPath}/resources/images/library.jpg") no-repeat;
            background-size: cover;
            repeat: fixed;
            width: 100%;
            height: 35em;
        }
    </style>
</head>
<body>

<div class="w3-display-container">
    <div class="w3-row">
        <c:forEach var="book" items="${books}">
            <div class="w3-col w3-border w3-border-white w3-hover-border-red w3-pale-blue l1 m3 s6 w3-center">
                <img src="${pageContext.request.contextPath}/resources/images/book_cover.jpg" class="w3-image" style="width: 100%">
                <p><c:out value="${book.title}"/>" by <c:out value="${book.author.firstName}"/> <c:out
                        value="${book.author.lastName}"/></p>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
