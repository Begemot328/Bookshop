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
            background-image: url("${pageContext.request.contextPath}/resources/images/library.jpg");
            background-repeat: no-repeat;
            background-attachment: fixed;
        }
    </style>
</head>
<body>
<div class="w3-container w3-purple">
    <h1>B.B.B.</h1>
    <h3>Best Belorussian Bookshop</h3>
</div>
<div class="w3-display-container">
    <div class="w3-container w3-twothird w3-display-topmiddle">
    <div class="w3-row-padding">
            <c:forEach var="book" items="${books}">
                <div class="w3-col w3-border w3-border-white w3-hover-border-red l2 m3 s9">
                    <img src="${pageContext.request.contextPath}/resources/images/book_cover.jpg" class="w3-image"
                         style="width: 100%">
                    <p><c:out value="${book.title}"/>" by <c:out value="${book.author.firstName}"/> <c:out
                            value="${book.author.lastName}"/></p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
