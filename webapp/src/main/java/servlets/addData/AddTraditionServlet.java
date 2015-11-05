package servlets.addData;

import beans.CountryWorking;
import beans.HolidayWorking;
import beans.TraditionWorking;
import beans.UserWorking;
import func.DateLabelFormatter;
import hdalayer.dao.impl.CountryDaoImpl;
import hdalayer.dao.impl.HolidayDaoImpl;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by iocz on 08/10/15.
 */
@WebServlet(urlPatterns = "/addTraditionServlet")
public class AddTraditionServlet extends HttpServlet {
    @EJB
    private CountryWorking countryBean;
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private TraditionWorking traditionBean;
    @EJB
    private UserWorking userBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {
        String countryName = request.getParameter("holidayCountry");

        if (countryName != null) {
            try {
                countryBean.insertCountry(countryName);
            } catch (SQLException e) {
                System.err.println("Ошибка добавления страны");
            }
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        String oldHolidayName = request.getParameter("title");
        String holidayName = request.getParameter("holidayName");
        Integer holidayType = Integer.parseInt(request.getParameter("holidayType"));
        String holidayDate = request.getParameter("holidayDate");
        String traditionDescription = request.getParameter("traditionDescription");

        if (oldHolidayName != null && holidayDate != null) {
            try {
                changeHoliday(id, holidayName, holidayType, holidayDate);
                changeTradition(id, getCountryId(countryName), getHolidayId(holidayName),
                        traditionDescription);
            } catch (SQLException e) {
                System.err.println("Ошибка при изменении праздника/традиции");
            } catch (ParseException e) {
                System.err.println("Ошибка получения даты праздника");
            }
        } else if (holidayName != null && holidayType != null && holidayDate != null) {
            try {
                addNewHoliday(holidayName, holidayType, holidayDate);
                addNewTradition(getCountryId(countryName), getHolidayId(holidayName),
                        traditionDescription, request);
            } catch (SQLException e) {
                System.err.println("Ошибка при добавлении праздника/традиции");
            } catch (ParseException e) {
                System.err.println("Ошибка получения даты праздника");
            }
        }
    }

    private void changeHoliday(Integer id, String holidayName,
                                 Integer holidayType, String holidayDate) throws ParseException, SQLException{
        holidayBean.updateHoliday(id, holidayName, holidayType,
                (Date) new DateLabelFormatter().stringToValue(holidayDate));
    }

    private void addNewHoliday(String holidayName,
                               Integer holidayType, String holidayDate) throws ParseException, SQLException{
        holidayBean.insertHoliday(holidayName, holidayType,
                (Date) new DateLabelFormatter().stringToValue(holidayDate));
    }

    private Integer getHolidayId(String holidayName) {
        return new HolidayDaoImpl().getHoliday(holidayName).getId();
    }

    private Integer getCountryId(String countryName) {
        return new CountryDaoImpl().getCountry(countryName).getId();
    }

    private void changeTradition(Integer id, Integer countryId,
                                 Integer holidayId, String traditionDescription) throws SQLException{
        traditionBean.updateTradition(id, countryId, holidayId, traditionDescription);
    }

    private void addNewTradition(Integer countryId,
                                 Integer holidayId, String traditionDescription,
                                 HttpServletRequest request) throws SQLException {
        traditionBean.insertTradition(countryId, holidayId, (Integer)
                        userBean.getSession(request).getAttribute("userId"), traditionDescription);
    }
}
