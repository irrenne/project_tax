<%@page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="../error.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Register</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mdb.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/authentication.css">
</head>
<body>
<ul>
    <li><a href="?sessionLocale=en"><fmt:message key="label.lang.en"/></a></li>
    <li><a href="?sessionLocale=uk"><fmt:message key="label.lang.uk"/></a></li>

</ul>
<div class="register">
    <div class="container" style="color: #4f5354;">
        <div class="card">
            <div class="card-header" style="font-size: 27px ;"><fmt:message key="label.createAccount"/></div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/register" method="post">

                    <div class="form-group row">
                        <label for="name" class="col-sm-2 col-form-label"><fmt:message key="label.name"/>: </label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="name" id="name"
                                   placeholder="<fmt:message key="label.enterName"/>">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="surname" class="col-sm-2 col-form-label"><fmt:message
                                key="label.surname"/>: </label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="surname" id="surname"
                                   placeholder="<fmt:message key="label.enterSurname"/>">
                        </div>
                    </div>

                    <div class=" form-group row">
                        <label for="login" class="col-sm-2 col-form-label"><fmt:message key="label.login"/>: </label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="login" id="login"
                                   placeholder="<fmt:message key="label.enterLogin"/>">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="password" class="col-sm-2 col-form-label"><fmt:message
                                key="label.password"/>: </label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="<fmt:message key="label.enterPassword"/>">
                        </div>
                    </div>
                    <div class="d-flex flex-row justify-content-center mt-auto">
                        <button type="submit" class="btn btn-primary"><fmt:message key="label.register"/></button>
                    </div>
                    <a class="already" href="<c:url value="/login"/>"><fmt:message key="label.loginMessage"/></a>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
