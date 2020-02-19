<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/18/2020
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add team Player</title>

    <form:form action="${pageContext.request.contextPath}/team/add-team-staff" method="post" modelAttribute="idList">

        <label> Team: ${team.name}</label>
        <input type="hidden" name="team_id" value="${team.id}"/>
        <form:select multiple="true" path="ids" items="${staffs}" itemValue="id" itemLabel="name"/>

        <input type="submit" name="submit" value="submit"/>

    </form:form>

</head>
<body>


</body>
</html>
