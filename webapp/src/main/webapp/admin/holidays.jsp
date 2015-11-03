<%@ page import="model.Holiday" %>
<%@ page import="hdalayer.dao.impl.HolidayDaoImpl" %>
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
<table class="table table-hover table-bordered table-responsive">
  <thead>
  <tr>
    <th>Name</th>
    <th>Start Date</th>
    <th>End Date</th>
    <th>Remove</th>
  </tr>
  </thead>
  <tbody>
  <% List<Holiday> holidays = new HolidayDaoImpl().getHolidayList();
    for (Holiday h : holidays) {
  %>
  <tr>
    <td>
      <%=h.getName()%>
    </td>
    <td>
      <%=h.getStartDate().toString()%>
    </td>
    <td>
      <%=h.getEndDate() == null ? h.getStartDate() : h.getEndDate().toString()%>
    </td>
    <td><a href="?holidays&removeh=<%=h.getId()%>" class="fa fa-times"></a></td>

      <%}%>
  </tbody>
</table>
</body>
</html>

