<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/21/2020
  Time: 9:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="row">
    <div class="col-sm-12 text-center">
        <h4>SilentTravel BlogSite </h4>
    </div>
</div>
<%--START profile section--%>
<div class="row profile">
    <div class="col-sm-12">
        <%--        <div class="profile-userpic text-center">--%>
        <%--            <a href="${pageContext.request.contextPath}/user/profile">--%>
        <%--                <img src="${user.profilePictureUrl}" class="img-responsive align-self-center" alt="">--%>
        <%--            </a>--%>
        <%--        </div>--%>
        <div class="profile-usertitle">
            <div class="profile-usertitle-name">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.username" />
                </sec:authorize>
            </div>
            <div class="profile-usertitle-job">

                <sec:authorize access="hasRole('SUPER_ADMIN')">
                    SUPER ADMIN
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    ADMIN
                </sec:authorize>
                <sec:authorize access="hasRole('TEAM_MANAGER')">
                    TEAM_MANAGER
                </sec:authorize>
                <sec:authorize access="hasRole('CAPTAIN')">
                    CAPTAIN
                </sec:authorize>
                <sec:authorize access="hasRole('COACHING_STAFF')">
                    COACHING STAFF
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    USER
                </sec:authorize>

            </div>
        </div>
    </div>
</div>
<%--END profile section--%>


<%--Menu section--%>
<div class="row">
    <div class="col-sm-12 left-menu">
        <ul class="list-group">
            <li class="list-group-item">
                <a href="${pageContext.request.contextPath}/dashbord">Dashboard</a>
            </li>
            <li class="list-group-item" >
                <a>Country</a>

                <ul class="list-group">
                    <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN')">
                        <li class="list-group-item"><a href="${pageContext.request.contextPath}/country/add">Add Country</a></li>
                    </sec:authorize>

                    <li class="list-group-item"><a href="${pageContext.request.contextPath}/country/show-all">Show Countries</a></li>
                </ul>
            </li>
            <%--        <li class="list-group-item">--%>
            <%--            <a >Team</a>--%>
            <%--            <ul>--%>
            <%--                <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN')">--%>
            <%--                    <li class="list-group-item"><a href="${pageContext.request.contextPath}/team/add">Add Team</a></li>--%>
            <%--                </sec:authorize>--%>
            <%--                <li class="list-group-item"><a href="${pageContext.request.contextPath}/team/show-all">Show teams</a></li>--%>
            <%--            </ul>--%>
            <%--        </li>--%>
            <li class="list-group-item">
                <a >Post</a>
                <ul>
                    <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN','PLAYER')">
                        <li class="list-group-item"><a href="${pageContext.request.contextPath}/post/write-post">Add post</a></li>
                    </sec:authorize>

                    <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN')">
                        <li class="list-group-item"><a href="${pageContext.request.contextPath}/post/show-all"> Approve post</a></li>
                    </sec:authorize>
<%--                    <li class="list-group-item"><a href="${pageContext.request.contextPath}/post/show-all">Show post</a></li>--%>
                </ul>
            </li>
            <li class="list-group-item">
                <a >Blogger</a>
                <ul>
                    <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN','TEAM_MANAGER','COACHING_STAFF')">
                        <li class="list-group-item"><a href="${pageContext.request.contextPath}/player/add">Add Blogger</a></li>
                    </sec:authorize>
                    <li class="list-group-item"><a href="${pageContext.request.contextPath}/player/show-all">Show Blogger</a></li>
                </ul>
            </li>
            <li class="list-group-item">
                <a >Admin</a>
                <ul>
                    <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN')">
                        <li class="list-group-item"><a href="${pageContext.request.contextPath}/staff/add">Add Admin</a></li>
                    </sec:authorize>
                    <li class="list-group-item"><a href="${pageContext.request.contextPath}/staff/show-all">Show Admins</a></li>
                </ul>
            </li>
            <li class="list-group-item">
                <a >User</a>
                <ul>
                    <sec:authorize access="hasRole('SUPER_ADMIN')">
                        <li class="list-group-item"><a href="${pageContext.request.contextPath}/user/add-admin">Add admin</a></li>
                    </sec:authorize>

                </ul>
            </li>
        </ul>
    </div>
</div>