<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mdb.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/login.css">
</head>
<body>
<div class="d-flex flex-row justify-content-end mt-auto">
    <div class="dropdown">
        <button class="btn btn-white btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true"
                aria-expanded="false">
            <fmt:message key="label.lang"/>
        </button>

        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
            <a class="dropdown-item" href="?sessionLocale=en"><fmt:message key="label.lang.en"/></a>
            <a class="dropdown-item" href="?sessionLocale=uk"><fmt:message key="label.lang.uk"/></a>
        </div>
    </div>
</div>
<h1><fmt:message key="label.sessionLocaleContent"/></h1>
<div class="login">
    <div class="container">
        <div class="card">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/login" method="post" class="was-validated">

                    <div class=" form-group row needs-validation">
                        <label for="login" class="col-sm-2 col-form-label"><fmt:message key="label.login"/>: </label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="login" id="login"
                                   placeholder="<fmt:message key="label.enterLogin"/>" required>
                        </div>
                        <div class="valid-feedback blue"></div>
                    </div>

                    <div class="form-group row">
                        <label for="password" class="col-sm-2 col-form-label"><fmt:message
                                key="label.password"/>: </label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="<fmt:message key="label.enterPassword"/>" required>
                        </div>
                        <div class="valid-feedback"></div>
                    </div>
                    <div class="d-flex flex-row justify-content-end mt-auto">
                        <button type="submit" class="btn btn-primary"><fmt:message key="label.submit"/></button>
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <a class="already" href="<c:url value="/register"/>"><fmt:message key="label.registerMessage"/></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

