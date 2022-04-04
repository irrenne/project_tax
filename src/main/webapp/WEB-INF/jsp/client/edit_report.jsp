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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>


</head>
<body style="text-align: center">
<c:set var="update" value="update=${report.id}&" />
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
            <c:if test="${errMsg != null}">
                <div class="alert alert-primary alert-dismissible fade show" role="alert">
                    <fmt:message key="label.msg"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <c:remove var="errMsg" scope="session"/>
            </c:if>
            <form action="<c:url value="/update-report"/>" method="post" enctype="multipart/form-data">
                <div class=" form-group row">
                    <label for="type" class="col-sm-2 col-form-label"><fmt:message key="label.type"/>: </label>
                    <div class="col-sm-7">
                        <select class="form-control" name="type" id="type">
                            <option value=""></option>
                            <c:forEach items="${types}" var="value">
                                <c:choose>
                                    <c:when test="${lang == 'en'}">
                                        <option value="${value}" ${value == selectedType ? 'selected="selected"' : ''}>${fn:toLowerCase(value)}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${value}" ${value == selectedType ? 'selected="selected"' : ''}>${value.localization}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="date" class="col-sm-2 col-form-label"><fmt:message key="label.date"/>: </label>
                    <div class="col-sm-7">
                        <input type="date" class="form-control" name="date" id="date"
                               placeholder="Enter Date" value="${report.dateOfCreation}">
                    </div>
                </div>
                <div class="d-flex justify-content-center">
                    <input type="file" id="file" name="file" accept="text/xml">
                </div>
                <div class="d-flex flex-row justify-content-end mt-auto">
                    <button type="submit" class="btn blue-gradient btn-md" id="button1" disabled><fmt:message
                            key="label.editReportButton"/></button>
                </div>
                <fmt:message key="label.downloadPrevious"/><a
                    href="${pageContext.request.contextPath}/download"><fmt:message key="label.download"/></a>
            </form>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        var $submit = $('#button1');
        var $file = $('#file');

        $file.change(
            function () {
                $submit.attr('disabled', (!$(this).val()));
            }
        );
    });
</script>

</body>
</html>
