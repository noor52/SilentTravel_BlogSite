<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/17/2020
  Time: 11:11 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>team list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>


<section class="content">
    <div class="container-fluid">
        <div class="row">
            <%--left Menu START--%>
            <div class="left-menu-section col-lg-2 col-md-3 col-sm-4" >

                <jsp:include page="../includes/homemenu.jsp"/>

            </div>
            <%--left Menu END--%>

            <%--content body section START--%>
            <div class="right-body-section col-lg-10 col-md-9 col-sm-8">

                <%--nav section START--%>
                <div class="row">
                    <div class="col-sm-12">
                        <nav class="navbar navbar-light bg-light justify-content-between">
                            <a class="navbar-brand">ICC WEB</a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
                        </nav>
                    </div>
                </div>
                <%--nav section END--%>

                <div class="row">
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <div class="card" style="margin-top: 10px">
                            <div class="card-header"> List of team</div>

                            <table class="table table-bordered">
                                <thead >
                                <tr>
                                    <th>Team Name</th>
                                    <th>Country</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${team_list}" var="team">
                                    <tr>
                                        <td>${team.name}</td>
                                        <td>${team.countryName}</td>
                                        <td><a class="btn-info" href="${pageContext.request.contextPath}/team/add-team-player?team_id=${team.id}">Add Team Player</a></td>
                                        <td><a class="btn-info" href="${pageContext.request.contextPath}/team/add-team-staff?team_id=${team.id}">Add Coaching Staff</a></td>
                                        <td><a class="btn-warning" href="${pageContext.request.contextPath}/team/edit?team_id=${team.id}">Edit</a></td>
                                        <td><a class="btn-danger" href="${pageContext.request.contextPath}/team/delete?team_id=${team.id}">Delete</a></td>

                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>



                <%--footer START --%>
                <%--footer END--%>
            </div>
        </div>
        <%--content body section END--%>

    </div>
</section>


</body>
</html>


