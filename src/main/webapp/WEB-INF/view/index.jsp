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
            <li><a href="/messages/form/add"
                   class="nav-link">Add message</a></li>
        </ul>

    </nav>
</header>
<br>


</body>
</html>
