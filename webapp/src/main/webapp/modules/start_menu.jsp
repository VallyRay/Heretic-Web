<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 04/11/15
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String registration = request.getParameter("registration");
  Integer userId = (Integer)session.getAttribute("userId");
  if (userId != null && userId > 0) {
%>
  <jsp:include page="menu.html"/>
<%

} else if (registration != null) {
%>
  <jsp:include page="/includes/registration.html"/>
<%

} else if (userId == null) {
  String url = request.getParameter("wrong") != null ?
          "/includes/login.jsp?wrong" : "/includes/login.jsp";
%>
  <jsp:include page="<%=url%>"/>
<%
  } else if (userId != null && userId == 0) {
    response.sendRedirect("admin/admin.jsp");
  }
%>
