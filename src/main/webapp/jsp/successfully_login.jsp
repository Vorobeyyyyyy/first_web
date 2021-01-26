<jsp:useBean id="user" class="com.vorobyev.first_web.entity.User" scope="session"/>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Profile:
</h1>
<h2>
    User name:  ${user.login}
    <br>
    Name: ${user.firstName} ${user.secondName}
    <br>
    Phone: ${user.phoneNumber}
</h2>
<form action="logout" method="get">
    <input type="hidden" name="command_id" value="logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>