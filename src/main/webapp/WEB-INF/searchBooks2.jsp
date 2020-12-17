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
    </style>
</head>
<body>
<div class="w3-container w3-stretch">
    <div class="w3-cell-row w3-purple w3-opacity-min">
        <div class="w3-cell w3-container" style="width: 75%">
            <h1 class="w3-jumbo" style="text-shadow: 5px 5px indigo;">B.B.B.</h1>
            <h3 class="w3-xxlarge" style="text-shadow: 5px 5px indigo;">Best Belorussian Bookshop</h3>
        </div>
        <div class="w3-cell w3-container w3-cell-middle">
            <div class="w3-dropdown-hover">
                <button class="w3-button w3-purple w3-opacity-min">
                    <i class="material-icons">search</i>
                </button>
                <div class="w3-dropdown-content w3-bar-block w3-deep-purple">
                    <form class="w3-bar-item">
                        <button class="w3-button  w3-ripple w3-hover-purple">
                            <span>find book</span>
                        </button>
                    </form>
                    <form class="w3-bar-item">
                        <button class="w3-button  w3-ripple w3-hover-purple">
                            <span>find shop</span>
                        </button>
                    </form>
                </div>
            </div>
            <div class="w3-dropdown-hover">
                <button class="w3-button w3-purple w3-opacity-min">
                    <i class="material-icons">person</i>
                </button>
                <div class="w3-dropdown-content w3-bar-block w3-deep-purple">
                    <form class="w3-bar-item">
                        <button class="w3-button  w3-ripple w3-hover-purple">
                            <span>sign in</span>
                        </button>
                    </form>
                    <form class="w3-bar-item">
                        <button class="w3-button  w3-ripple w3-hover-purple">
                            <span>register</span>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="w3-cell-row">
    <div class="w3-cell  w3-deep-purple w3-opacity" style="width:15%">
        <div class="w3-bar-block">
            <div class="w3-bar-item w3-large">Жанры</div>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Все</button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Детективы</button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Ужасы</button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Фантастика</button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Романы</button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Классика</button>
            </form>
        </div>
    </div>
    <div class="w3-cell w3-padding-large" style="width:70%">
        <div class="w3-row-padding">
            <c:forEach var="book" items="${books}">
                <form class="w3-col l2 m6 s12  w3-center"
                      action="https://www.w3schools.com/w3css/w3css_web_tmp_architect.asp"
                      target="_self">
                    <button class="w3-button  w3-ripple">
                        <img src="${pageContext.request.contextPath}/resources/images/book_cover.jpg"
                             class="w3-image">
                    </button>
                    <p class="w3-signal-blue w3-large w3-opacity-min">
                        <c:out value="${book.title}"/>"
                        <br/>
                        <c:out value="${book.author.firstName}"/> <c:out
                            value="${book.author.lastName}"/></p>
                </form>
            </c:forEach>
        </div>
        <div class="w3-bar w3-purple w3-opacity-min w3-center">
            <a href="#" class="w3-button">&laquo;</a>
            <a href="#" class="w3-button w3-indigo">1</a>
            <a href="#" class="w3-button">2</a>
            <a href="#" class="w3-button">3</a>
            <a href="#" class="w3-button">4</a>
            <a href="#" class="w3-button">5</a>
            <a href="#" class="w3-button">&raquo;</a>
        </div>
    </div>

    <div class="w3-cell w3-deep-purple w3-opacity-min w3-cell" style="width:15%">
        <div class="w3-bar-block">
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Книги</button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Магазины</button>
            </form>
            <form class="w3-bar-item w3-large w3-hover-purple">
                <button class="w3-button w3-bar-item w3-ripple w3-hover-purple">Пользователи</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>