<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="surname" required="true" %>
<c:out value="${name}"/> <c:out value="${surname}"/>