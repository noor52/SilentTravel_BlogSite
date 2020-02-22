<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/21/2020
  Time: 9:24 PM
  To change this template use File | Settings | File Templates.
--%>
<div class="row">
    <div class="col-sm-12">
        <h4>ICC WEB</h4>
    </div>
</div>
<div class="row">
<div class="col-sm-12 left-menu">
    <ul class="list-group">
        <li class="list-group-item" >
            <a>Country</a>

            <ul class="list-group">
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/country/add">Add Country</a></li>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/country/show-all">Show Countries</a></li>
            </ul>
        </li>
        <li class="list-group-item">
            <a >Team</a>
            <ul>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/team/add">Add Team</a></li>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/team/show-all">Show teams</a></li>
            </ul>
        </li>
        <li class="list-group-item">
            <a >Player</a>
            <ul>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/player/add">Add player</a></li>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/player/show-all">Show players</a></li>
            </ul>
        </li>
        <li class="list-group-item">
            <a >Staff</a>
            <ul>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/staff/add">Add staff</a></li>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/staff/show-all">Show staffs</a></li>
            </ul>
        </li>
        <li class="list-group-item">
            <a >User</a>
            <ul>
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/user/add-admin">Add admin</a></li>
            </ul>
        </li>
    </ul>
</div>
</div>