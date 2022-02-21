<%@ page import="com.epam.tax.entities.Status" %>
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
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:set var="NotConfirmedType" value="<%=Status.NOT_CONFIRMED%>"/>
<c:set var="ConfirmedType" value="<%=Status.CONFIRMED%>"/>
<c:set var="SubmittedType" value="<%=Status.SUBMITTED%>"/>
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
            <%--            <h1 style="text-align: center">Reports</h1>--%>
            <form class="border-light" action="${pageContext.request.contextPath}/client/reports" method="get">

                <table class="table table-light">
                    <thead class="thread-dark">
                    <tr>
                        <th>ID</th>
                        <th><fmt:message key="label.type"/></th>
                        <th><fmt:message key="label.status"/></th>
                        <th><fmt:message key="label.date"/></th>
                        <th><fmt:message key="label.actions"/></th>
                    </tr>
                    </thead>
                    <tbody class="reports-container">

                    <c:forEach var="report" items="${reports}">

                        <tr>
                            <td>${report.id}</td>
                            <td>${report.type}</td>
                            <td>${report.status}
                                <c:forEach var="inspector" items="${inspectors}">
                                    <c:choose>
                                        <c:when test="${report.status == NotConfirmedType &&  report.inspectorId == inspector.id}">
                                            <button type="button" class="btn btn-info  pull-right" data-toggle="modal"
                                                    data-target="#myModal${report.id}"><fmt:message key="label.info"/>
                                            </button>
                                            <div class="modal fade" id="myModal${report.id}" role="dialog">
                                                <div class="modal-dialog">

                                                    <div class="alert alert-info alert-dismissible">
                                                        <a class="close" data-dismiss="modal"
                                                           aria-label="close">&times;</a>
                                                            ${report.comment} Checked by ${inspector.name}
                                                    </div>

                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                            <td>${report.dateOfCreation}</td>
                            <c:choose>
                                <c:when test="${report.status == NotConfirmedType}">
                                    <form action="${pageContext.request.contextPath}/update-report">
                                        <td>
                                            <button name="update" value="${report.id}" class="btn blue-gradient btn-md">
                                                <fmt:message key="label.edit"/>
                                            </button>
                                        </td>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/delete-report" method="post">
                                        <td>
                                            <button name="delete" value="${report.id}" class="btn blue-gradient btn-md">
                                                <fmt:message key="label.delete"/>
                                            </button>
                                        </td>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/pdf" method="get">
                                        <td>
                                            <button name="view" value="${report.id}" class="btn blue-gradient btn-md">
                                                <fmt:message key="label.view"/>
                                            </button>
                                        </td>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <button name="update" value="${report.id}" class="btn blue-gradient btn-md"
                                                disabled>
                                            <fmt:message key="label.edit"/>
                                        </button>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/delete-report" method="post">
                                        <td>
                                            <button name="delete" value="${report.id}" class="btn blue-gradient btn-md">
                                                <fmt:message key="label.delete"/>
                                            </button>
                                        </td>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/pdf" method="get">
                                        <td>
                                            <button name="view" value="${report.id}" class="btn blue-gradient btn-md">
                                                <fmt:message key="label.view"/>
                                            </button>
                                        </td>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <%@ include file="/WEB-INF/jspf/pagination.jspf" %>
            </form>
        </div>
    </div>
</div>

</body>
</html>