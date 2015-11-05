package servlets;

import beans.HolidayWorking;
import beans.UserWorking;
import model.Holiday;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by iocz on 08/10/15.
 */
@WebServlet(urlPatterns = "/getUserHolidays")
public class UserHolidayServlet extends HttpServlet {
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private UserWorking userBean;

    @Override
    protected void doPost(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException{
        try {
            responseMethod(response, holidayBean.getUserHolidays((Integer)userBean.getSession(request).getAttribute("userId")));
        }catch (SQLException e){
            //TODO fix it.
            e.printStackTrace();
        }
    }

    private void responseMethod(HttpServletResponse response,
                                List<Holiday> list) throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        out.print("<div class=holidays>");
        out.print("<ul>");
        for (Holiday h: list) {
            out.print("<li>");
            out.print("<a href = \"index.jsp?holidayName=" + h.getName() + " \">" + h.getName() + "</a>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</div>");
        }
    }
}
