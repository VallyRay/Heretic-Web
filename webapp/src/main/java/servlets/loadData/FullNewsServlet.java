package servlets.loadData;

import beans.CommentsWorking;
import beans.StatisticWorking;
import beans.TraditionWorking;
import model.Tradition;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(urlPatterns = "fullNews")
public class FullNewsServlet extends HttpServlet{
    @Override
    public void service(HttpServletRequest request,
                            HttpServletResponse response) {
            int id = Integer.parseInt(request.getParameter("traditionId"));
            Tradition tradition = getTradition(id);
            int reads = getReads(id);
            int commentCount = getCommentCount(tradition);

            request.setAttribute("title", tradition.getHoliday().getName());
            request.setAttribute("img", tradition.getImgURL());
            request.setAttribute("text", tradition.getDescription());
            request.setAttribute("country", tradition.getCountry().getName());
            request.setAttribute("reads", reads);
            request.setAttribute("commentsCount", commentCount);
    }
    @EJB
    private TraditionWorking traditionBean;
    private Tradition getTradition(int id) {
        Tradition tradition = null;
        try {
            tradition = traditionBean.getTradition(id);
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке традиции в fullNewsServlet");
        }
        return tradition;
    }

    @EJB
    private StatisticWorking statisticBean;
    private Integer getReads(int id) {
        int reads = 0;
        try {
            reads = statisticBean.getStatistic(id).getReads();
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке счетчика просмторов новости в fullNewsServlet");
        }
        return reads;
    }

    @EJB
    private CommentsWorking commentsBean;
    private Integer getCommentCount(Tradition tradition) {
        int result = 0;
        try {
            result = commentsBean.getCommentsCount(tradition).intValue();
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке данных праздника");
        }
        return result;
    }
}
