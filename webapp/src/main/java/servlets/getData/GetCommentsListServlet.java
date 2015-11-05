package servlets.getData;

import beans.CommentsWorking;
import model.Comment;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Integer;import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "getCommentsList")
public class GetCommentsListServlet extends HttpServlet{
    @EJB
    private CommentsWorking commentsBean;

    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            int traditionId = Integer.parseInt(request.getParameter("traditionId"));
            request.setAttribute("commentList", commentsBean.getCommentsList(traditionId));
        } catch (SQLException e) {
            System.err.println("Ошибка в GetCommentsListServlet");
        }
    }
}
