package servlets.addData;

import beans.CommentsWorking;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(urlPatterns = "addComment")
public class AddCommentServlet extends HttpServlet{
    @EJB
    private CommentsWorking commentBean;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String comment = request.getParameter("comment");
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer userId = Integer.parseInt(request.getParameter("userId"));

        if (comment != null) {
            try {
                commentBean.insertComment(comment, id, userId);
            } catch (SQLException e) {
                System.err.println("Ошибка при добавлении комментария");
            }
        }
    }
}
