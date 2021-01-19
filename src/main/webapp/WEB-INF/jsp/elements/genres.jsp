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
    <title>AddPhoto</title>
<script type="text/javascript">
    var genresArray = [];
    <c:forEach var="genre" items="${applicationScope.genres}">
    genresArray[${genre.id}] =  '${genre.name}';
    </c:forEach>


    function addItem(){
        var form = document.getElementById("formId");
        var input = document.createElement("input");
        var removeButton = document.createElement("button");
        var select = document.getElementById("genreSelect");
        var option = document.getElementById(select.value);

        input.setAttribute('id', 'input_' + select.value);
        input.setAttribute('type','hidden');
        input.setAttribute('name', "genreId");
        input.setAttribute('value', select.value);
        removeButton.appendChild(document.createTextNode(genresArray[select.value][0] + " " +
        genresArray[select.value][1]));
        removeButton.setAttribute('id','btn_' + select.value);
        removeButton.setAttribute('onclick',"removeItem(this)");
        form.appendChild(removeButton);
        form.appendChild(input);
        select.removeChild(option);
    }

    function removeItem(item){
        var form = document.getElementById("formId");
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
    <br />
    <select id="genreSelect" name="genreSelect">
        <c:forEach var="genre" items="${applicationScope.genres}">
            <option id="${genre.id}" value="${genre.id}">${genre.name}</option>
        </c:forEach>
    </select>
    <button onclick="addItem()" id="add">add item</button>
</div>
<form id="genreFormId" method="get" action="${pageContext.request.contextPath}/ControllerURL">
    <input name="command" type="hidden" value="GET_VALUES_COMMAND">
</form>
<button form="genreFormId" type="submit">try!</button>

</body>
</html>
