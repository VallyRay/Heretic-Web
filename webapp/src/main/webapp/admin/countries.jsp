<%@ page import="hdalayer.dao.impl.CountryDaoImpl" %>
<%@ page import="model.Country" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 29.10.2015
  Time: 20:39
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
              <th>Name</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
              <% List<Country> countries = new CountryDaoImpl().getCountryList();
                for (Country c : countries) {
                  %>
                  <tr>
                    <td>
                      <%=c.getName()%>
                    </td>
                    <td><a href="?countries&removec=<%=c.getId()%>" class="fa fa-times"></a></td>

                      <%}%>
          </tbody>
        </table>
</body>
</html>
