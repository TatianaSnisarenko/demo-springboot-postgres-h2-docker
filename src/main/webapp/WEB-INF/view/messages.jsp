<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Message System</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <div>
            <a href="<%=request.getContextPath()%>/" class="navbar-brand"> Message System </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="/messages"
                   class="nav-link">Messages</a></li>
        </ul>

        <ul class="navbar-nav">
            <li><a href="/new"
                   class="nav-link">Add message</a></li>
        </ul>

    </nav>
</header>
<br>

<div class="row">
    <!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

    <div class="container">
        <h3 class="text-center">List of messages</h3>
        <hr>

        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Body</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <!--   for (Todo todo: todos) {  -->
            <c:forEach var="message" items="${messages}">
                <tr>
                    <td><c:out value="${message.id}" /></td>
                    <td><c:out value="${message.title}" /></td>
                    <td><c:out value="${message.body}" /></td>

                    <td><a href="<%=request.getContextPath()%>/update?id=<c:out value='${message.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a
                                href="<%=request.getContextPath()%>/delete?id=<c:out value='${message.id}' />">Delete</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>

        </table>
    </div>
</div>
</body>
</html>
