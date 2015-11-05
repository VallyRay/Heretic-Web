package servlets.getData;

import beans.HolidayWorking;
import beans.UserWorking;
import model.Holiday;
import model.User;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = "getHolidays")
public class GetHolidaysServlet extends HttpServlet {
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private UserWorking userBean;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            printResult((LinkedList)getHolidayList(request), response);
        } catch (SQLException e) {
            System.err.println("Ошибка получения списка праздников");
        }
    }

    private List<Holiday> getHolidayList(HttpServletRequest request) throws SQLException{
        HttpSession session = userBean.getSession(request);
        return holidayBean.getUserHolidays((Integer) session.getAttribute("userId"));
    }

    private void printResult(LinkedList<Holiday> holidays,
                             HttpServletResponse response) throws IOException, SQLException{
        PrintWriter out = response.getWriter();
        out.print("<div class=holidays>");
        out.print("<ul>");
        for (int i = 0; i < holidays.size(); i++) {
            String current = holidays.get(i).getName();
            out.print("<li>");
            out.print("<a href=\"index.jsp?holidayName=" + current + "\">" +
                    current + "</a>");
            out.print("</li>");
        } out.print("</ul>");
        out.print("</div>");
    }
}
