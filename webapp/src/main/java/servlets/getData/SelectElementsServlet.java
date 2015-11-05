package servlets.getData;

import beans.CountryWorking;
import beans.HolidayWorking;
import beans.TraditionWorking;
import beans.UserWorking;
import model.Resource;

import javax.ejb.EJB;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iocz on 11/10/15.
 */
@WebServlet(urlPatterns = "selectElements")
public class SelectElementsServlet extends HttpServlet {
    @EJB
    private CountryWorking countryBean;
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private TraditionWorking traditionBean;
    @EJB
    private UserWorking userBean;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String beanType = request.getParameter("type");
        PrintWriter out = response.getWriter();
        out.print("<form action=\"" + actionServlet(beanType) + "\" method=\"post\">");
        out.print("<select multiple required=\"10\" class=\"b3radius field\" name=\"" + beanType + "\">");
        out.print("<option disabled>Choose names " + paramName(beanType) + "</option>");
        List<? extends Resource> elementsList = null;
        try {
            formList(beanType, request);
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка элементов");
        }
        for (int i = 0; i < elementsList.size(); i++) {
            out.print("<option value=" + i + ">" +
                    elementsList.get(i).getName() + "</option>");
        }
        out.print("</select>");
        out.print("<br>");
        out.print("<input type=\"submit\" value=\"Save " + beanType + " in XML\"/>");
        out.print("</form>");
    }

    private String actionServlet(String beanType) {
        if (beanType.equals("country")) {
            return "saveInXML?type=country";
        } else if (beanType.equals("holiday")) {
            return "saveInXML?type=holiday";
        } else if (beanType.equals("tradition")) {
            return "saveInXML?type=tradition";
        } else {
            return "o_O";
        }
    }

    private List<? extends Resource> formList(String beanType,
                                              HttpServletRequest request) throws SQLException{
        if (beanType.equals("country")) {
            return countryBean.getCountries();
        } else if (beanType.equals("holiday")) {
            return holidayBean.getUserHolidays((Integer)
                    userBean.getSession(request).getAttribute("userId"));
        } else if (beanType.equals("tradition")) {
            return traditionBean.getUserTraditions((Integer)
                    userBean.getSession(request).getAttribute("userId"));
        } else {
            return new ArrayList<>();
        }
    }

    private String paramName(String beanType) {
        if (beanType.equals("country")) {
            return "countries";
        } else if (beanType.equals("holiday")) {
            return "holidays";
        } else if (beanType.equals("tradition")) {
            return "traditions";
        } else {
            return "o_O";
        }
    }
}
