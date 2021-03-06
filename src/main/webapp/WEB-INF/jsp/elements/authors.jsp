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
    <title>Authors</title>
<script type="text/javascript">
    var authorsArray = [];
    <c:forEach var="author" items="${requestScope.authors}">
    authorsArray[${author.id}] =  ['${author.firstName}', '${author.lastName}'];
    </c:forEach>


    function addItem(){
        var form = document.getElementById("formId");
        var input = document.createElement("input");
        var removeButton = document.createElement("button");
        var select = document.getElementById("authorsSelect");
        var option = document.getElementById(select.value);

        input.setAttribute('id', 'input_' + select.value);
        input.setAttribute('type','hidden');
        input.setAttribute('name', "authorId");
        input.setAttribute('value', select.value);
        removeButton.appendChild(document.createTextNode(authorsArray[select.value][0] + " " +
        authorsArray[select.value][1]));
        removeButton.setAttribute('id','btn_' + select.value);
        removeButton.setAttribute('onclick',"removeItem(this)");
        form.appendChild(removeButton);
        form.appendChild(input);
        select.removeChild(option);
    }

    function removeItem(item){
        var form = document.getElementById("formId");
        var input = document.getElementById(item.id.replace('btn_', 'input_'));
        var select = document.getElementById("authorsSelect");
        var option = document.createElement("option");
        option.setAttribute('id', item.id.replace('btn_', ''));
        option.setAttribute('value', item.id.replace('btn_', ''));
        option.appendChild(document.createTextNode(authorsArray[item.id.replace('btn_', '')][0] + " "
            + authorsArray[item.id.replace('btn_', '')][1]));
        select.appendChild(option);
        form.removeChild(item);
        form.removeChild(input);
    }

</script>
</head>
<body>
<div>
    <br />
    <select id="authorsSelect" name="authorSelector">
        <c:forEach var="author" items="${authors}">
            <option id="${author.id}" value="${author.id}">${author.firstName} ${author.lastName}</option>
        </c:forEach>
    </select>
    <button onclick="addItem()" id="add">add item</button>
</div>
<form id="formId" method="get" action="${pageContext.request.contextPath}/ControllerURL">
    <input name="command" type="hidden" value="GET_VALUES_COMMAND">
</form>
<button form="formId" type="submit">try!</button>

<c:forEach var="author" items="${requestScope.authorId}">
    <p> ${author}</p>
</c:forEach>
</body>
</html>
