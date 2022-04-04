<%@ page import="com.epam.tax.entities.Status" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>


<html lang="${sessionScope.lang}">
<head>
    <title>Reports</title>
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
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:set var="name" value="order"/>
<c:set var="value" value="report_type.type_name"/>

<c:set var="NotConfirmedType" value="<%=Status.NOT_CONFIRMED%>"/>
<c:set var="ConfirmedType" value="<%=Status.CONFIRMED%>"/>
<c:set var="SubmittedType" value="<%=Status.SUBMITTED%>"/>
<div class="container-fluid">
    <div class="row flex-nowrap">
        <div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-light">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">

                <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
                    <li class="activePage">
                        <a href="<c:url value="/client/reports"/>" class="nav-link align-middle px-0">
                            <span class="ms-1 d-none d-sm-inline"><fmt:message key="label.reports"/></span>
                        </a>
                    </li>
                    <li>
                        <a href="<c:url value="/create_report"/>" class="nav-link px-0 align-middle">
                            <span class="ms-1 d-none d-sm-inline"><fmt:message key="label.createReport"/></span> </a>
                    </li>
                </ul>
                <hr>
            </div>
        </div>

        <div class="col py-3">

            <form action="${pageContext.request.contextPath}/client/reports" method="get">
                <div class="form-group">
                    <div class="input-group">
                         <span class="input-group-btn">
                            <select class="form-control" name="status" id="status">
                                <option value="" selected><fmt:message key="label.filter"/></option>
                                <option value="${NotConfirmedType.id}">${NotConfirmedType}</option>
                                <option value="${ConfirmedType.id}">${ConfirmedType}</option>
                                <option value="${SubmittedType.id}">${SubmittedType}</option>
                            </select>
                         </span>
                        <span class="input-group-btn">
                             <select class="form-control" name="order" id="order">
                                 <option value="" selected><fmt:message key="label.sort"/></option>
                                 <option value="report_type.type_name"><fmt:message key="label.type"/></option>
                                 <option value="reports.date"><fmt:message key="label.date"/></option>
                              </select>
                         </span>
                        <div class="d-flex flex-row justify-content-end mt-auto">
                            <button id="searchbtn" type="submit" class="form-control"><fmt:message
                                    key="label.search"/></button>
                        </div>
                    </div>
                </div>
            </form>

            <table class="table table-light" id="myTable">
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
                        <td><c:out value="${report.id}"/></td>
                        <td><c:out value="${report.type}"/></td>
                        <td><c:out value="${report.status}"/>
                            <c:forEach var="inspector" items="${inspectors}">
                                <c:choose>
                                    <c:when test="${report.status == NotConfirmedType &&  report.inspectorId == inspector.id}">
                                        <button type="button" class="btn btn-info  pull-right"
                                                data-toggle="modal"
                                                data-target="#myModal${report.id}"><fmt:message
                                                key="label.info"/>
                                        </button>
                                        <div class="modal fade" id="myModal${report.id}" role="dialog">
                                            <div class="modal-dialog modal-dialog-centered">

                                                <div class="alert alert-info alert-dismissible">
                                                    <a class="close" data-dismiss="modal"
                                                       aria-label="close">&times;</a>
                                                    <h4 class="alert-heading"><fmt:message key="label.reason"/></h4>
                                                    <p><c:out value="${report.comment}"/></p>
                                                    <hr>
                                                    <fmt:message key="label.checked"/> <h:nameFormat name="${inspector.name}" surname="${inspector.surname}"/>
                                                </div>

                                            </div>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </td>
                        <td><c:out value="${report.dateOfCreation}"/></td>
                        <c:choose>
                            <c:when test="${report.status == NotConfirmedType}">
                                <form action="${pageContext.request.contextPath}/update-report" method="get">
                                    <c:set var="update" value="${report.id}"/>
                                    <td>
                                        <button name="update" value="${report.id}"
                                                class="btn blue-gradient btn-md">
                                            <fmt:message key="label.edit"/>
                                        </button>
                                    </td>
                                </form>
                                <form action="${pageContext.request.contextPath}/client/delete-report" method="post">
                                    <td>
                                        <button name="delete" value="${report.id}"
                                                class="btn blue-gradient btn-md">
                                            <fmt:message key="label.delete"/>
                                        </button>
                                    </td>
                                </form>
                                <form action="${pageContext.request.contextPath}/pdf" method="get">
                                    <td>
                                        <button name="view" value="${report.id}"
                                                class="btn blue-gradient btn-md">
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
                                <form action="${pageContext.request.contextPath}/client/delete-report" method="post">
                                    <td>
                                        <button name="delete" value="${report.id}"
                                                class="btn blue-gradient btn-md">
                                            <fmt:message key="label.delete"/>
                                        </button>
                                    </td>
                                </form>
                                <form action="${pageContext.request.contextPath}/pdf" method="get">
                                    <td>
                                        <button name="view" value="${report.id}"
                                                class="btn blue-gradient btn-md">
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
        </div>
    </div>
</div>

</body>
</html>