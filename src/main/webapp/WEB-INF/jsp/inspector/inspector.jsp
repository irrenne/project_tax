<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html lang="${sessionScope.lang}">
<html>
    <head>
        <title>Inspector</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mdb.min.css">
    </head>
    <body style="text-align: center">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <h1><fmt:message key="label.welcome"/>, <c:out value="${sessionScope.user.login}"/></h1>
    </body>
</html>