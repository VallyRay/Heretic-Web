package servlets.deleteData;

import beans.CommentsWorking;
import beans.StatisticWorking;
import beans.TraditionWorking;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(urlPatterns = "deleteNews")
public class DeleteNewsServlet extends HttpServlet{
    @EJB
    private CommentsWorking commentBean;
    @EJB
    private StatisticWorking statisticBean;
    @EJB
    private TraditionWorking traditionBean;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String delete = request.getParameter("delete");
        if (delete != null) {
            try {
                commentBean.deleteComments(id);
                statisticBean.deleteStatistic(id);
                traditionBean.deleteTradition(id);
            } catch (SQLException e) {
                System.err.println("Ошибка при удалении традиции");
            }
        }
    }
}
