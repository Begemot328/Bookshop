<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${not empty sessionScope.language}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<c:if test="${empty sessionScope.language}">
    <fmt:setLocale value="EN"/>
</c:if>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title>Genres</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/w3.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/w3-colors-signal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/materialIcons.css">

    <script type="text/javascript">
        var genresArray = [];
        <c:forEach var="genre" items="${applicationScope.genres}">
        genresArray[${genre.id}] = '${genre.name}';
        </c:forEach>

        function addItem() {
            var form = document.getElementById("<c:out value="${requestScope.formId}"/>");
            var input = document.createElement("input");
            var removeButton = document.createElement("button");
            var select = document.getElementById("genreSelect");
            var option = document.getElementById(select.value);

            if (option != null) {
                input.setAttribute('id', 'input_' + select.value);
                input.setAttribute('type', 'hidden');
                input.setAttribute('name', "genreId");
                input.setAttribute('value', select.value);
                removeButton.appendChild(document.createTextNode(genresArray[select.value]));
                removeButton.setAttribute('id', 'btn_' + select.value);
                removeButton.setAttribute('onclick', "removeItem(this)");
                removeButton.setAttribute('class',"w3-btn w3-padding w3-deep-purple " +
                    "w3-ripple w3-hover-purple");
                form.appendChild(removeButton);
                form.appendChild(input);
                select.removeChild(option);
            }
        }

        function removeItem(item) {
            var form = document.getElementById("<c:out value="${requestScope.formId}"/>");
            var input = document.getElementById(item.id.replace('btn_', 'input_'));
            var select = document.getElementById("genreSelect");
            var option = document.createElement("option");
            option.setAttribute('id', item.id.replace('btn_', ''));
            option.setAttribute('value', item.id.replace('btn_', ''));
            option.appendChild(document.createTextNode(genresArray[item.id.replace('btn_', '')]));
            select.appendChild(option);
            form.removeChild(item);
            form.removeChild(input);
        }
    </script>
</head>
<body>
<div>
    <select id="genreSelect" name="genreSelect">
        <c:choose>
            <c:when test="${not empty requestScope.genres}">
                <c:forEach var="genre" items="${requestScope.genres}">
                    <option id="${genre.id}" value="${genre.id}">${genre.name}</option>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach var="genre" items="${applicationScope.genres}">
                    <option id="${genre.id}" value="${genre.id}">${genre.name}</option>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </select>
    <button onclick="addItem()" id="add"
            class="w3-btn w3-deep-purple w3-ripple w3-hover-purple">
        <fmt:message>item.add</fmt:message>
    </button>
</div>
</body>
</html>
