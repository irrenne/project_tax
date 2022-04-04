<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error page</title>
    <meta charset="utf-8">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style500.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mdb.min.css">
</head>
<body>
<section class="centered">
    <h1>500 Server Error</h1>
    <div class="container1">
        <div><span class="message">Well gosh! Sadly, pixies got into the server! Beg pardon.</span></div>
        <span class="message">Error: <%=exception.getMessage() %>
        <br>
        Please go to  <button class="btn btn-primary" onclick="history.back()"> Back to Previous Page</button> </span>

    </div>
</section>
</body>
</html>