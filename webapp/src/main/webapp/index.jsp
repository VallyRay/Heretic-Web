<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Heretic 2.0</title>
    <meta name="keywords" content="Holiday, library">
    <meta name="description" content="Your own holiday library!">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <jsp:directive.page errorPage="error_page.html"/>
</head>
<body>
<div id="page_align" class="b3radius">
    <div id="header">
        <jsp:include page="includes/header.html"/>
        <jsp:include page="includes/header_nav.html"/>
    </div>

    <div id="sidebar" class="f_left">
        <jsp:include page="modules/start_menu.jsp"/>
    </div>
    <div id="content" class="f_right">
        <%
            //region Parameters
            String news = request.getParameter("news");
            String holidayName = request.getParameter("holidayName");
            String countryName = request.getParameter("countryName");
            //endregion

            ArrayList<String> pages = null;
            if ((session.getAttribute("userId") != null && news != null) ||
                    holidayName != null || countryName != null) {
        %>
        <jsp:include page="getPages"/>
        <jsp:include page="getPageNum"/>
        <%
            pages = (ArrayList)request.getAttribute("pages");
            Integer pageNum = (Integer)request.getAttribute("pageNum");
            for (int i = (pageNum - 1) * 10; i < (pageNum * 10 < pages.size() ? pageNum * 10 :
                    pages.size()); i++) {
        %>
        <jsp:include page="<%= pages.get(i)%>"/>
        <%
                }
            } else {
    %>
        <jsp:include page="main"/>
        <%
                String url = (String)request.getAttribute("url");
        %>
        <jsp:include page="<%=url%>"/>
        <%
            }
        %>
</div>

<div class="clr"></div>
<%
    int numPage = pages.size() / 10 + 1;
    if (news != null) {
        StringBuilder footerLinks = new StringBuilder(
                "footer_links.jsp?numPage=".concat(Integer.toString(numPage))
        );
%>
    <jsp:include page="<%= footerLinks.toString()%>"/>
<%
    }
%>
<jsp:include page="includes/footer.html"/>
</div>
</body>
</html>

