package servlets.saveData;

import beans.CountryWorking;
import model.Country;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by iocz on 10/10/15.
 */
@WebServlet(urlPatterns = "/saveCountriesXML")
public class SaveCountriesServlet extends HttpServlet {
    @EJB
    private CountryWorking countryBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        String[] countries = request.getParameterValues("country");
        LinkedList<Country> countryList = new LinkedList<>();
        for (int i = 0; i < countries.length; i++) {
            try {
                countryList.add(countryBean.getCountries().get(Integer.parseInt(countries[i])));
            } catch (SQLException e) {
                //TODO fix.
                System.err.println("");
            }
        }
        //TODO fix ""
        countryBean.saveCountryXML(countryList, "");
            //FileWorking.saveCountryXML(Calendar.getCountries(), "/home/iocz/Документы/Hereticlication/src/resources/xml/countrySave.xml");
        response.sendRedirect("/Heretic/index.jsp?xml");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

    }
}
