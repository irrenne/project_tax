<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Edit Report</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mdb.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/create.css">


</head>
<body style="text-align: center">
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container-fluid">
    <div class="row flex-nowrap">
        <div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-light">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">

                <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
                    <li class="nav-item active">
                        <a href="<c:url value="/client/reports"/>" class="nav-link align-middle px-0">
                            <span class="ms-1 d-none d-sm-inline"><fmt:message key="label.reports"/></span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/create_report"/>" class="nav-link px-0 align-middle">
                            <span class="ms-1 d-none d-sm-inline"><fmt:message key="label.createReport"/></span> </a>
                    </li>
                </ul>
                <hr>
            </div>
        </div>
        <div class="col py-3">

            <%--            <form action="create_report" method="post">--%>
            <form action="<c:url value="/update-report"/>" method="post" enctype="multipart/form-data">

                <div class=" form-group row">
                    <label for="type" class="col-sm-2 col-form-label"><fmt:message key="label.type"/>: </label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" name="type" id="type"
                               placeholder="Enter type" value="${report.type}">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="date" class="col-sm-2 col-form-label"><fmt:message key="label.date"/>: </label>
                    <div class="col-sm-7">
                        <input type="date" class="form-control" name="date" id="date"
                               placeholder="Enter Date" value="${report.dateOfCreation}">
                    </div>
                </div>

                <button type="submit" class="btn blue-gradient btn-md" id="button1"><fmt:message
                        key="label.editReportButton"/></button>
                <div class="form-group">
                    <input type="file" class="form-control-file" id="file" name="file">
                </div>
                <fmt:message key="label.downloadPrevious"/><a
                    href="${pageContext.request.contextPath}/download"><fmt:message key="label.download"/></a>
            </form>
        </div>
    </div>
</div>
</div>

</body>
</html>
