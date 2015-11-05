package servlets.saveData;

import beans.*;
import hdalayer.beans.impl.HolidayBean;
import hdalayer.dao.impl.CountryDaoImpl;
import hdalayer.dao.impl.HolidayDaoImpl;
import model.*;
import util.PathConstants;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = "saveInXML")
public class SaveInXMLServlet extends HttpServlet{
    @EJB
    private CountryWorking countryBean;
    @EJB
    private HolidayWorking holidayBean;
    @EJB
    private TypeWorking typeBean;
    @EJB
    private CommentsWorking commentsBean;
    @EJB
    private TraditionWorking traditionBean;
    @EJB
    private  UserWorking userBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        String type = request.getParameter("type");
        String[] elements = request.getParameterValues(type);
        List<Resource> elementsList = getList(type);
        for (int i = 0; i < elements.length; i++) {
            try {
                elementsList.add(getElementList(type, request).get(Integer.parseInt(elements[i])));
            } catch (SQLException e) {
                //TODO fix.
                System.err.println("");
            }
        }
        try {
            saveInXML(type, elementsList);
        } catch (Exception e) {
            //TODO fix it.
            e.printStackTrace();
        }
        response.sendRedirect("/Heretic/index.jsp?xml");
    }

    private List<Resource> getList(String type) {
        if (type.equals("country") || type.equals("holiday")) {
            return new LinkedList<>();
        } else {
            return new ArrayList<>();
        }
    }

    //TODO SelectElementServlet.
    private List<? extends Resource> getElementList(String beanType,
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

    private List<Tradition> getAdditionalTraditionList(String type, String[] names)
            throws SQLException{
        ArrayList<Tradition> result = new ArrayList<>();
        if (type.equals("country")) {
            for (int i = 0; i < names.length; i++)
            {
                Country c = new CountryDaoImpl().getCountry(names[i]);
                ArrayList<Tradition> traditionList = (ArrayList)
                        countryBean.getCountryTraditions(c.getId());
                for (int j = 0; j < traditionList.size(); j++) {
                        result.add(traditionList.get(j));
                }
            }
        } else if (type.equals("holiday")) {
            for (int i = 0; i < names.length; i++)
            {
                Holiday h = new HolidayDaoImpl().getHoliday(names[i]);
                ArrayList<Tradition> traditionList = (ArrayList)
                        countryBean.getCountryTraditions(h.getId());
                for (int j = 0; j < traditionList.size(); j++) {
                    result.add(traditionList.get(j));
                }
            }
        }
        return result;
    }

    private Country getAdditionalCountry(Integer traditionId) throws  SQLException{
        return traditionBean.getTraditionCountry(traditionId);
    }

    private LinkedList<Country> getAdditionalCountryList(List<Tradition> traditionList)
            throws SQLException{
        LinkedList<Country> result = new LinkedList<>();
        for (int i = 0; i < traditionList.size(); i++) {
            result.add(getAdditionalCountry(traditionList.get(i).getId()));
        }
        return result;
    }

    private void saveAdditionalTradition(List<? extends Resource> elementList)
            throws SQLException, IOException{
        String[] names = new String[elementList.size()];
        for (int i = 0; i < elementList.size(); i++) {
            names[i] = elementList.get(i).getName();
        }
        ArrayList<Tradition> traditionList = (ArrayList<Tradition>)
                getAdditionalTraditionList("country", names);

        traditionBean.saveTradition(traditionList, PathConstants.TRADITION_PATH_XML);
        //Additional comments.
        saveComments(traditionList);
    }

    private void saveAdditionalType(Holiday holiday) throws SQLException, IOException{
        //TODO change direct.
        //typeBean.saveTypeXML(holidayBean.selectHolidayType(holiday), PathConstants.TYPE_PATH_XML);
    }

    private void saveAdditionalTypeList(List<Holiday> elementList)
            throws SQLException, IOException{
        for (int i = 0; i < elementList.size(); i++) {
            saveAdditionalType(elementList.get(i));
        }
    }

    private String[] getNames(List<? extends Resource>elementList) {
        String[] result = new String[elementList.size()];
        for (int i = 0; i < elementList.size(); i++) {
            result[i] = elementList.get(i).getName();
        }
        return result;
    }

    private void saveComments(List<Tradition> traditionList) throws IOException, SQLException {
        for (int i = 0; i < traditionList.size(); i++) {
            commentsBean.saveCommentsXML(commentsBean.selectTraditionComments(
                    //TODO change direct.
                    traditionList.get(i).getId()), PathConstants.COMMENTS_PATH_XML);
        }
    }

    private void saveInXML(String type, List<? extends Resource> elementList)
            throws IOException, SQLException{
        if (type.equals("country")) {
            countryBean.saveCountryXML((List<Country>) elementList, PathConstants.COUNTRY_PATH_XML);
            //Additional tradition
            saveAdditionalTradition(elementList);
        } else if (type.equals("holiday")) {
            //Save holiday.
            holidayBean.saveHolidaysXML((List<Holiday>) elementList, PathConstants.HOLIDAY_PATH_XML);
            //Additional tradition.
            ArrayList<Tradition> traditionList = (ArrayList<Tradition>)getAdditionalTraditionList("holiday",
                    getNames(elementList));
            traditionBean.saveTradition(getAdditionalTraditionList("holiday",
                    getNames(elementList)), PathConstants.TRADITION_PATH_XML);
            //Additional types.
            saveAdditionalTypeList((List<Holiday>) elementList);
            //Additional statistic.
            //Additional comments.
            saveComments(traditionList);
        } else if (type.equals("tradition")) {
            traditionBean.saveTradition((List<Tradition>) elementList, PathConstants.TRADITION_PATH_XML);
            //Additional holiday.
            //holidayBean.saveHolidaysXML(getAdditionalHoliday((ArrayList<Tradition>) elementList),
              //      PathConstants.HOLIDAY_PATH_XML);
            //Additional country.
            countryBean.saveCountryXML(getAdditionalCountryList(
                    (ArrayList<Tradition>)elementList), PathConstants.HOLIDAY_PATH_XML);
            //Additional comments.
            saveComments((ArrayList<Tradition>)elementList);
        }
    }
}
