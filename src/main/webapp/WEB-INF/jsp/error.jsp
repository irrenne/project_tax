<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error page</title>
    <meta charset="utf-8">
</head>
<body>
        <% if (response.getStatus() == 500) { %>
            <span style="color: red; ">Error: <%=exception.getMessage() %>
             </span><br>
        <%} else {%>
            Hi There, error code is <%=response.getStatus() %><br>
            Please go to  <button onclick="history.back()"> Back to Previous Page</button>--%> home page
        <%} %>
</body>
</html>
