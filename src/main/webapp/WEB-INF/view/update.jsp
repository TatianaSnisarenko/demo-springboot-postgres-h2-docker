<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">

            <form action="messages" method="post">

                <caption>
                    <h2>
                        Update Message
                    </h2>
                </caption>

                <input type="hidden" name="id" value="<c:out value='${message.id}' />"/>

                <fieldset class="form-group">
                    <label>Message Title Name</label> <input type="text"
                                                             class="form-control"
                                                             name="title" required="required">
                </fieldset>

                <fieldset class="form-group">
                    <label>Message Body</label> <input type="text"
                                                       class="form-control"
                                                       name="body" required="required">
                </fieldset>

                <br>
                <button type="submit" class="btn btn-success">Save</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
