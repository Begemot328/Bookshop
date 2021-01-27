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
    <!-- Sript for photoLink enabling\disabling-->
    <script type="text/javascript">
        function EnableDisable(photoFile) {
            //Reference the file.
            var photoLinkText = document.getElementById("photoLinkText");

            //Verify the File value.
            if (photoFile.files.length == 0) {
                //Enable the TextBox when File is empty.
                photoLinkText.disabled = false;
            } else {
                //Disable the TextBox when File has file.
                photoLinkText.disabled = true;
            }
        }
    </script>
</head>
<body>
<div class="w3-container" style="width:35%">
    <input class="w3-input w3-border" type="url" name="photolink" value="${photolink}" id="photoLinkText"
           placeholder="<fmt:message key="photolink"/>">
</div>
<div class="w3-container" style="width:35%">
    <input class="w3-input w3-border w3-button w3-purple" type="file" name="file" id="photoFile"
           placeholder="<fmt:message key="photolink"/>" onchange="EnableDisable(this)">
</div>
</body>
</html>
