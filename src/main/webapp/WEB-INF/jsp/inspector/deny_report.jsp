<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Reports</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mdb.min.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<div class="container-fluid">
    <div class="row flex-nowrap">
        <div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-light">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">

                <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
                    <li class="nav-item">
                        <a href="<c:url value="/inspector/reports"/>" class="nav-link align-middle px-0">
                            <span class="ms-1 d-none d-sm-inline"><fmt:message key="label.reports"/></span>
                        </a>
                    </li>
                    <li>
                        <a href="<c:url value="/inspector/statistics"/>" class="nav-link px-0 align-middle">
                            <i class="fs-4 bi-people"></i> <span
                                class="ms-1 d-none d-sm-inline"><fmt:message key="label.statistics"/></span> </a>
                    </li>
                </ul>
                <hr>
            </div>
        </div>
        <div class="col py-3">
            <form action="<c:url value="/inspector/deny_report"/>" method="post">
                <div class="form-group">
                    <label for="reason"><fmt:message key="label.enterReason"/></label>
                    <textarea class="form-control" name="reason" id="reason" rows="3" maxlength="255" required></textarea>
                </div>
                <div class="d-flex flex-row justify-content-end mt-auto">
                    <button type="submit" class="btn btn-primary"><fmt:message key="label.submitReason"/></button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>