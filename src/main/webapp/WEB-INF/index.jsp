<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Hello World!</h2>


<form name="start" method="POST" action="${pageContext.request.contextPath}/ControllerURL">
    <input type="hidden" name="command" value="SEARCH_BOOKS">
    <input type="submit" text>
</form>

</body>
</html>
