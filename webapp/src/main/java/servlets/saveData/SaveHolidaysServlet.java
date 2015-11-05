package servlets.saveData;

import beans.HolidayWorking;
import beans.UserWorking;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by iocz on 11/10/15.
 */
@WebServlet(urlPatterns = "saveHolidaysXML")
public class SaveHolidaysServlet extends HttpServlet {
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private UserWorking userBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        String[] holidays = request.getParameterValues("holiday");
        try {
            holidayBean.saveHolidaysXML(holidayBean.getUserHolidays(
                            (Integer) userBean.getSession(request).getAttribute("userId")),
                    //TODO String const.
                    "/Hereticlication/src/resources/xml/holidaySave.xml");
        } catch (SQLException e) {
            //TODO fix.
            System.err.println("");
        }
        response.sendRedirect("/Heretic/index.jsp?xml");
    }
}
