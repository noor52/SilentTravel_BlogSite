<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/18/2020
  Time: 11:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Team members</title>
</head>
<body>
<h2>${team.countryName} : ${team.name}</h2>
<h3>Team Players</h3>
    <table>
        <tr>
            <th>Name</th>
            <th>Age</th>
            <th>DOB</th>
        </tr>
        <c:forEach items="${players}" var="player" >
            <tr>
                <td>${player.name}</td>
                <td>${player.age}</td>
                <td>${player.dob}</td>
            </tr>
        </c:forEach>
    </table>

</br></br>
<h3>Team Coaching Staffs</h3>
<table>
    <tr>
        <th>Name</th>
        <th>Age</th>
        <th>DOB</th>
    </tr>
    <c:forEach items="${staffs}" var="staff" >
        <tr>
            <td>${staff.name}</td>
            <td>${staff.age}</td>
            <td>${staff.dob}</td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
