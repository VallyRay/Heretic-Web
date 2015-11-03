<%@ page import="func.Calendar" %>
<%@ page import="hdalayer.dao.impl.CountryDaoImpl" %>
<%@ page import="hdalayer.dao.impl.HolidayDaoImpl" %>
<%@ page import="hdalayer.dao.impl.TraditionDaoImpl" %>
<%@ page import="hdalayer.dao.impl.UserDaoImpl" %>
<%@ page import="model.Country" %>
<%@ page import="model.Holiday" %>
<%@ page import="model.Tradition" %>
<%@ page import="model.User" %>
<%@ page import="model.dao.CountryDao" %>
<%@ page import="model.dao.TraditionDao" %>
<%@ page import="java.sql.SQLException" %>
<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 29.10.2015
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Heretic 2.0</title>
    <meta name="keywords" content="Holiday, library">
    <meta name="description" content="Your own holiday library!">
    <link href="../css/main.css" rel="stylesheet" type="text/css">
    <link href="../css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="page_align" class="b3radius">
    <div>
        <jsp:include page="../header.html"/>
        <jsp:include page="admin_header.html"/>
    </div>


        <div style="margin: 20px">
            <%

                if (Calendar.getUserId() != 0) response.sendRedirect("../index.jsp");

                String countriesParam = request.getParameter("countries");
                String holidaysParam = request.getParameter("holidays");
                String usersParam =  request.getParameter("users");
                String logoutParam = request.getParameter("logout");
                if (countriesParam != null) { %>
                    <jsp:include page="countries.jsp"/>
            <%} else if (holidaysParam != null) {%>
                <jsp:include page="holidays.jsp"/>
            <%} else if (usersParam != null) {%>
                <jsp:include page="users.jsp" />
            <%}
            else if (logoutParam != null) {
                Calendar.logout();
                response.sendRedirect("/Heretic/index.jsp");
                return;
            }
            else { %>
            <img src="${pageContext.request.contextPath}/Heretic/images/admins.png" />
            <%}

            String removeUserParam = request.getParameter("removeu");
            String removeHolidayParam = request.getParameter("removeh");
            String removeCountryParam = request.getParameter("removec");

                try {
                    if (removeUserParam != null) {
                        UserDaoImpl dao = new UserDaoImpl();
                        User user = dao.getUser(Integer.parseInt(removeUserParam));
                        TraditionDaoImpl traditionDao = new TraditionDaoImpl();
                        for (Tradition t : user.getTraditions()) {
                            traditionDao.deleteTradition(t);
                        }
                        dao.deleteUser(user);

                        //     <jsp: page="admin.jsp?users"/>
                        response.sendRedirect("admin.jsp?users");
                        return;

                    } else if (removeHolidayParam != null) {
                        HolidayDaoImpl dao = new HolidayDaoImpl();
                        Holiday holiday = dao.getHoliday(Integer.parseInt(removeHolidayParam));
                        TraditionDaoImpl trdao = new TraditionDaoImpl();
                        for (Tradition t : holiday.getTraditions()) {
                            trdao.deleteTradition(t);
                        }
                        dao.deleteHoliday(holiday);
                        response.sendRedirect("admin.jsp?holidays");
                        return;
                    } else if (removeCountryParam != null) {
                        CountryDao countryDao = new CountryDaoImpl();
                        TraditionDao traditionDao = new TraditionDaoImpl();
                        Country country = countryDao.getCountry(Integer.parseInt(removeCountryParam));
                        country.getTraditions().clear();
                        for (Tradition t : country.getTraditions()) {
                            traditionDao.deleteTradition(t);
                        }

                        countryDao.deleteCountry(country);
                        response.sendRedirect("admin.jsp?countries");
                        return;
//                        <jsp:forward page="admin.jsp?countries"/>

                    }
                }
                catch (SQLException ex) {
                    out.print("Can't make the deletion");
                }
            %>
        </div>
</div>
</body>
</html>
