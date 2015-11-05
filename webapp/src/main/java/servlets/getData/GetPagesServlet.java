package servlets.getData;

import beans.TraditionWorking;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/getPages")
public class GetPagesServlet extends HttpServlet{
    @EJB
    private TraditionWorking traditionBean;

    public void service(HttpServletRequest request, HttpServletResponse response)
                                                    throws IOException, ServletException{
        String holidayName = request.getParameter("holidayName");
        String countryName = request.getParameter("countryName");

        ArrayList<Integer> traditionIdList = getTraditionIdList(holidayName, countryName);

        ArrayList<String> pages = getPages(traditionIdList);
        request.setAttribute("pages", pages);
    }

    private ArrayList<Integer> getTraditionIdList(String holidayName, String countryName){
        ArrayList<Integer> traditionIdList = null;
        if (holidayName != null) {
            traditionIdList = getTraditionsByHoliday(holidayName);
        } else if (countryName != null) {
            traditionIdList = getTraditionsByCountry(countryName);
        } else traditionIdList = getAllTraditionsId();
        return traditionIdList;
    }

    private ArrayList<Integer> getTraditionsByHoliday(String holidayName) {
        ArrayList<Integer> result = null;
        try {
            result = (ArrayList)
                    traditionBean.getTraditionsByHolidayName(holidayName);
        } catch (SQLException e) {
            System.err.println("Ошибка получения списка традиций праздника " + holidayName);
        }
        return result;
    }

    private ArrayList<Integer> getTraditionsByCountry(String countryName) {
        ArrayList<Integer> result = null;
        try {
            result = (ArrayList)
                    traditionBean.getTraditionsByCountryName(countryName);
        } catch (SQLException e) {
            System.err.println("Ошибка получения списка традиций страны " + countryName);
        }
        return result;
    }

    private ArrayList<Integer> getAllTraditionsId(){
        ArrayList<Integer> result = null;
        try {
            result = (ArrayList) traditionBean.getTraditionsId();
        } catch (SQLException e) {
            System.err.println("Ошибка получения списка id всех традиций");
        }
        return result;
    }

    private ArrayList<String> getPages(ArrayList<Integer> idList) {
        ArrayList<String> pages = new ArrayList<>();
        for (int i = idList.size() - 1; i > -1; i--) {
            pages.add("news/holiday_table.jsp?traditionId=".concat(Integer.toString(idList.get(i))));
        }
        return pages;
    }
}
