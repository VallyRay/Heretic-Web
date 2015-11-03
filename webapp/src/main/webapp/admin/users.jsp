<%@ page import="hdalayer.dao.impl.UserDaoImpl" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 29.10.2015
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<table class="table table-hover table-bordered">
    <thead>
    <tr>
        <th>Login</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <% List<User> users = new UserDaoImpl().getUsers();
        for (User u : users) { if (u.getId() != 0) {
    %>
    <tr>
        <td>
            <%=u.getLogin()%>
        </td>
        <td><a href="?users&removeu=<%=u.getId()%>" class="fa fa-times"></a></td>

            <%}
            }
            %>
    </tbody>
</table>
</body>
</html>