<%--
  Created by IntelliJ IDEA.
  User: !!!!!!!
  Date: 31.05.2015
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  String id = request.getParameter("traditionId");
%>
  <jsp:include page="fullNews"/>
<%
  String title = (String)request.getAttribute("title");
  String img = (String)request.getAttribute("img");
  String text = (String)request.getAttribute("text");
  String country = (String)request.getAttribute("country");
  Integer reads = (Integer)request.getAttribute("reads");
  Long comments = (Long)request.getAttribute("commentsCount");
%>
<table class="table">
  <tr>
    <h2><a href="index.jsp?traditionId=<%=id%>"><%=title%></a></h2>
    <hr width="98%" border="1" color="#f2f2f0"/>
    <div class="small">
      Country: <%=country%>  Reads: <%=reads%> Comments: <%=comments%>
    </div>
  </tr>
  <tr>
    <td><img src="/Heretic/images/str/<%=img%>" width="80" height="65" alt="Images"></td>
    <td>
      <%=text%>
    </td>
  </tr>
</table>
</body>
</html>
