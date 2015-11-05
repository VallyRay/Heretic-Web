package servlets.saveData;

import beans.StatisticWorking;import model.Statistic;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Integer;import java.lang.System;import java.sql.SQLException;

@WebServlet(urlPatterns = "incReads")
public class IncrementReadsServlet extends HttpServlet{
    @EJB
    private StatisticWorking statisticBean;

    public void service(HttpServletRequest request, HttpServletResponse response) {
        int traditionId = Integer.parseInt(request.getParameter("traditionId"));
        try {
            Statistic statistic = statisticBean.getStatistic(traditionId);
            statistic.setReads(statistic.getReads() + 1);
        } catch (SQLException e) {
            //TODO fix it.
            e.printStackTrace();
        }
    }
}
