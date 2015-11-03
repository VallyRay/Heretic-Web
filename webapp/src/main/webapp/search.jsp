<%@ page import="hdalayer.search.Search" %>
<%@ page import="model.Tradition" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 25.10.2015
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
    <link href="css/main.css" rel="stylesheet" type="text/css">
  <%
    String searchString = request.getParameter("topsearch");
    if ((searchString != null) && (searchString.length() > 0)) {
      List<Tradition> result = Search.searchTradition(searchString, true);
      //TODO: Make normal printing as pages
        if (result.size() > 0) {
      for (Tradition tr : result) {
        %>
          <p><h2 align="center"><%=tr.toString()%></h2></p>
        <%
              }
          }
          else {
              %>
                <p><h2 align="center">Nothing was found, try again!</h2></p>
              <%
              }

    } else
    {   %>
         <p><h2 align="center">The searching string was empty, try again!</h2></p>
        <%}
  %>
</head>
<body>

</body>
</html>
