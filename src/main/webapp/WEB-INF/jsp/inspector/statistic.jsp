<%@ page import="com.epam.tax.entities.Status" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom-tag.tld" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<html lang="${sessionScope.lang}">
<head>
  <title>Statistics</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mdb.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reports.css">
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
          <li class="nav-item">
            <a href="<c:url value="/inspector/reports"/>" class="nav-link align-middle px-0">
              <span class="ms-1 d-none d-sm-inline"><fmt:message key="label.reports"/></span>
            </a>
          </li>
          <li class="activePage">
            <a href="<c:url value="/inspector/statistics"/>" class="nav-link px-0 align-middle">
              <i class="fs-4 bi-people"></i> <span
                    class="ms-1 d-none d-sm-inline"><fmt:message key="label.statistics"/></span> </a>
          </li>
        </ul>
        <hr>
      </div>
    </div>

    <div class="col py-3">
      <div class="btn-group">
        <form action="${pageContext.request.contextPath}/inspector/statistics" method="get">
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
              <span class="input-group-btn">
                             <select class="form-control" name="userId" id="userId">
                                 <option value="" selected><fmt:message key="label.filter_clients"/></option>
                                  <c:forEach var="u" items="${users}">
                                    <option value="${u.id}">${u.login}</option>
                                  </c:forEach>
                              </select>
                         </span>
              <div class="d-flex flex-row justify-content-end mt-auto">
                <button id="searchbtn" type="submit" class="form-control"><fmt:message
                        key="label.search"/></button>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div>
        <button type="submit" class="form-control" data-toggle="collapse" data-target="#demo"><fmt:message
                key="label.show"/></button>
      </div>
      <div id="demo" class="collapse">

        <div class="statistics">
          <p class="font-weight-bolder">
            <fmt:message key="label.total"/>
            <c:choose>
              <c:when test="${client != null}">
                <fmt:message key="label.for_client"/> ${client.login}
              </c:when>
              <c:otherwise>
                <fmt:message key="label.forall"/>
              </c:otherwise>
            </c:choose>
          </p>
          <ul class="list-group">
            <li class="list-group-item"><fmt:message key="label.submitted"/> ${submitted}</li>
            <li class="list-group-item"><fmt:message key="label.confirmed"/> ${confirmed}</li>
            <li class="list-group-item"><fmt:message key="label.not_confirmed"/> ${denied}</li>
          </ul>
        </div>
      </div>
      <table class="table table-hover table-light">
        <tr>
          <th><fmt:message key="label.user"/></th>
          <th><fmt:message key="label.type"/></th>
          <th><fmt:message key="label.status"/></th>
          <th><fmt:message key="label.date"/></th>
          <th><fmt:message key="label.inspector"/></th>
          <th><fmt:message key="label.actions"/></th>
        </tr>
        <c:forEach var="user" items="${users}">
          <c:forEach var="report" items="${reports}">
            <c:choose>
              <c:when test="${report.userId == user.id}">
                <tr>
                  <td>${user.login}</td>
                  <td>${report.type}</td>
                  <td>${report.status}</td>
                  <td>${report.dateOfCreation}</td>
                  <c:forEach var="inspector" items="${inspectors}">
                    <c:choose>
                      <c:when test="${report.inspectorId == inspector.id}">
                        <td>${inspector.login}</td>
                      </c:when>
                      <c:otherwise>
                        <td>-&nbsp;</td>
                      </c:otherwise>
                    </c:choose>
                  </c:forEach>
                  <form action="${pageContext.request.contextPath}/pdf" method="get">
                    <td>
                      <button name="view" value="${report.id}"
                              class="btn blue-gradient btn-md">
                        <fmt:message key="label.view"/>
                      </button>
                    </td>
                  </form>
                </tr>
              </c:when>
            </c:choose>
          </c:forEach>
        </c:forEach>
      </table>
      <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
          <c:choose>
            <c:when test="${currentPage != 1}">
              <li class="page-item ">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/${fn:toLowerCase(user.role)}/statistics?page=${currentPage - 1}"
                   tabindex="-1"><fmt:message key="label.previous"/></a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="page-link"
                   href="#"
                   tabindex="-1"><fmt:message key="label.previous"/></a>
              </li>
            </c:otherwise>
          </c:choose>

          <c:forEach begin="1" end="${noOfPages}" var="i">

            <li class="page-item"><a class="page-link"
                                     href="${pageContext.request.contextPath}/${fn:toLowerCase(user.role)}/statistics?${query}&page=${i}">${i}</a>

            </li>

          </c:forEach>
          <c:choose>
            <c:when test="${currentPage lt noOfPages}">
              <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/${fn:toLowerCase(user.role)}/statistics?page=${currentPage + 1}"><fmt:message
                        key="label.next"/></a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="page-link"
                   href="#"><fmt:message key="label.next"/></a>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </nav>
    </div>
  </div>
</div>
</body>
</html>
