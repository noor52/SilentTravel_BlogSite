<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/17/2020
  Time: 7:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <title>Post list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <script src="${pageContext.request.contextPath }/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>

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
                            <a class="navbar-brand">SilentTravel BlogSite</a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
                        </nav>
                    </div>
                </div>
                <%--nav section END--%>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="card" style="margin-top: 10px">
                            <div class="card-header"> List of Active  Posts</div>

                            <table class="table table-bordered">
                                <thead >
                                <tr>
                                    <th>Blogg Post</th>


                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${post_list}" var="post">
                                    <tr>
                                        <td>${post.postText}</td>
                                        <sec:authorize access="hasAnyRole('SUPER_ADMIN','TEAM_MANAGER','PLAYER')">
                                            <td><a class="btn btn-warning" href="${pageContext.request.contextPath}/post/edit?id=${post.id}">Edit</a></td>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('SUPER_ADMIN','TEAM_MANAGER')">
                                            <td><a class="btn btn-danger" href="${pageContext.request.contextPath}/post/delete?id=${post.id}">Delete</a></td>
                                        </sec:authorize>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>

                </br>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="card" style="margin-top: 10px">
                            <div class="card-header"> List of Deactive Posts</div>

                            <table class="table table-bordered">
                                <thead >
                                <tr>
                                    <th> Deactive Blogger Post</th>


                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${deactive_post_list}" var="post">
                                    <tr>
                                        <td>${post.postText}</td>
                                        <sec:authorize access="hasAnyRole('SUPER_ADMIN','TEAM_MANAGER','PLAYER')">
                                            <td><a class="btn btn-warning" href="${pageContext.request.contextPath}/post/edit?id=${post.id}">Approve</a></td>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('SUPER_ADMIN','TEAM_MANAGER')">
<%--                                            <td><a class="btn btn-danger" href="${pageContext.request.contextPath}/post/delete?id=${post.id}">Delete</a></td>--%>
                                        </sec:authorize>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
                </br>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="card">
                            <div class="card-header">Search Blogger by name</div>
                            <form:form  class="form-control form-group"  action="${pageContext.request.contextPath}/post/search" method="GET">

                                <div class="row">
                                    <div class="col-sm-12 form-group">
                                        <label for="search_name">Blogger name</label>
                                        <input id="search_name" type="text" class="form-control player_query"  placeholder="Player name">
                                    </div>
                                    <a class="btn btn-primary float-right player_query_submit ">Search</a>
                                </div>
                            </form:form>

                        </div>

                    </div>
                </div>


                <div class="row">
                    <div class="col-sm-12">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>Id </th>
                                <th>Name</th>
                                <th>Country</th>
                            </tr>
                            </thead>
                            <tbody class="search_result">

                            </tbody>

                        </table>
                    </div>
                </div>



                <%--footer START --%>
                <%--footer END--%>
            </div>
        </div>
        <%--content body section END--%>

    </div>
</section>

<script>
    $(document).ready(function () {
        $(".player_query_submit").click(function () {
            var playername = $(".player_query").val();

            console.info(playername);

            $.get("/api/v1/player/search?playername="+playername, function (data) {
                var searchContainer = $(".search_result");
                searchContainer.html("");

                debugger;
                if (data.length > 0){

                    $.each(data,function (index, value){

                        var tableRow = "<tr>\n " +
                            "<td class='player_id'>"+value.id+"</td>" +
                            "<td class='player_name'>"+value.name+"</td>" +
                            "<td class='player_country'>"+value.countryName+"</td>" +
                            " \n</tr>";
                        searchContainer.append(tableRow);

                    });


                } else {
                    searchContainer.html("<th>Sorry, no player found!</th>")
                }

            });

        });
    });
</script>


</body>
</html>

