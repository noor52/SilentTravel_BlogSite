<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/26/2020
  Time: 4:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>File Upload View</title>
</head>
<body>

<h2>Submitted File</h2>
<table>
    <tr>
        <td>File name:</td>
        <td>${fileName}</td>
    </tr>

    <tr>
        <td>Picture: </td>
        <td><img src="/profile/images/${fileName}" alt="profile picture" width="300" height="300"></td>
    </tr>
</table>


</body>
</html>
