<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Comment" %>
<%@ page import="javax.ejb.EJB" %>
<%@ page import="beans.UserWorking" %>
<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 08/09/15
  Time: 00:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    Integer id = Integer.parseInt(request.getParameter("traditionId"));
%>
<jsp:include page="fullNews"/>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    String traditionName = (String)request.getAttribute("title");
    String img = (String)request.getAttribute("img");
    String country = (String)request.getAttribute("country");
    String text = (String)request.getAttribute("text");
    Integer reads = (Integer)request.getAttribute("reads");
    StringBuilder url = new StringBuilder("index.jsp?traditionId=".concat(id.toString()));
%>
    <jsp:include page="incReads"/>
    <jsp:include page="getCommentsList"/>
<%
    ArrayList<Comment> commentsList = new ArrayList<>();
    if (request.getAttribute("commentList") != null) {
        commentsList = (ArrayList) request.getAttribute("commentList");
    }
%>
<table class="table">
    <tr>
        <h2><%=traditionName%></h2>
        <hr width="98%" border="1" color="#f2f2f0"/>
        <div class="small">
            Country: <%=country%>  Reads: <%=reads + 1%>
        </div>
    </tr>
    <tr>
        <td><img src="/WebApp/images/str/<%=img%>" width="80" height="65" alt="Images"></td>
        <td>
            <%=text%>
        </td>
    </tr>
</table>
<hr class="dotted" border="1" color="#f2f2f0" width="98%"/>
<table>
    <tr>
        <form method="post">
            <a>Comments count: <%=commentsList.size()%></a>
            &nbsp;&nbsp;
            <input value=" Delete holiday " type="submit" name="delete">
            &nbsp;&nbsp;
        </form>
        <form action="index.jsp?addNews=true&id=<%=id%>&title=<%=traditionName%>&country=
        <%=country%>&description=<%=text%>" method="post">
            <input value=" Change holiday " type="submit">
        </form>
    </tr>
</table>
<hr class="dotted" border="1" color="#f2f2f0" width="98%"/>
<%!
    @EJB
    private UserWorking userBean;
%>
<%
    String userName = userBean.getUserLogin(userId);
    for (int i = 0; i < commentsList.size(); i++) {
        StringBuilder commentPages = new StringBuilder("comment.jsp?userName=".concat(
                userName).concat("&text=".concat(commentsList.get(i).getText())
        ));
%>
    <jsp:include page="<%=commentPages.toString()%>"/>
<%
    }
%>
<iframe action="<%=url.toString()%>" name="iframe" style="position: absolute; left: -9999px;"></iframe>
<form method = "post" target="iframe">
    <div>
        <h3>Add comment</h3>
        <textarea name="comment" class="b3radius field descriptionNewsField" required>
        </textarea>

        <br>
        <div align="left"><button style="width: 20%" class="sb_header b3radius">Add holiday</button></div>
    </div>
</form>
        <jsp:include page="deleteNews"/>
        <jsp:include page="addComment"/>
</body>
</html>