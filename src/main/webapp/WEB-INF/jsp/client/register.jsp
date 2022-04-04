<%@page pageEncoding="UTF-8" %>
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
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/authentication.css">
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
<c:if test="${errMsg != null}">
    <div class="alert alert-primary alert-dismissible fade show" role="alert">
        <c:out value="${errMsg}"/>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <c:remove var="errMsg" scope="session"/>
</c:if>
<div class="register">
    <div class="container" style="color: #4f5354;">
        <div class="card">
            <div class="card-header" style="font-size: 27px ;"><fmt:message key="label.createAccount"/></div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/register" method="post" class="was-validated">

                    <div class="form-group row">
                        <label for="name" class="col-sm-2 col-form-label"><fmt:message key="label.name"/>: </label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="name" id="name"
                                   placeholder="<fmt:message key="label.enterName"/>" pattern="^[А-Я][-А-я]+"
                                   title="<fmt:message key='label.req_name'/>" required>
                        </div>
                        <div class="valid-feedback"></div>
                    </div>

                    <div class="form-group row">
                        <label for="surname" class="col-sm-2 col-form-label"><fmt:message
                                key="label.surname"/>: </label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="surname" id="surname"
                                   placeholder="<fmt:message key="label.enterSurname"/>" pattern="^[А-Я][-А-я]+"
                                   title="<fmt:message key='label.req_surname'/>" required>
                        </div>
                        <div class="valid-feedback"></div>
                    </div>

                    <div class=" form-group row">
                        <label for="login" class="col-sm-2 col-form-label"><fmt:message key="label.login"/>: </label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="login" id="login"
                                   placeholder="<fmt:message key="label.enterLogin"/>"
                                   pattern="^(?=.*[A-Za-z])(d*)[A-Za-z\d]{4,32}$"
                                   title="<fmt:message key='label.req_login'/>" required>
                        </div>
                        <div class="valid-feedback"></div>
                    </div>

                    <div class="form-group row">
                        <label for="password" class="col-sm-2 col-form-label"><fmt:message
                                key="label.password"/>: </label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="<fmt:message key="label.enterPassword"/>"
                                   pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,32}$"
                                   title="<fmt:message key='label.req_password'/>" required>
                        </div>
                        <div class="valid-feedback"></div>
                    </div>
                    <div class="d-flex flex-row justify-content-end mt-auto">
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
