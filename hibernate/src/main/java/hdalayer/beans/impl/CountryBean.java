package hdalayer.beans.impl;

import beans.CountryWorking;
import func.FileWorking;
import hdalayer.dao.impl.CountryDaoImpl;
import hdalayer.general.HibernateFactory;
import model.Country;
import model.dao.CountryDao;
import model.dao.DaoFactory;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CountryBean implements CountryWorking {
    private static CountryDao dao;

    static {
        dao = HibernateFactory.getInstance().getCountryDao();
    }

    @Override
    public List<Country> countries(DaoFactory factory) throws SQLException{
        CountryDao countryDao = factory.getCountryDao();
        return countryDao.getCountryList();
    }

    @Override
    public void insertCountry(String countryName) throws SQLException {
        dao.addCountry(new Country(countryName));
    }

    @Override
    public void updateCountry(int id, String countryName) throws SQLException{
        dao.changeCountry(countryName, id);
    }

    @Override
    public List<Country> getCountries() throws SQLException {
        return dao.getCountryList();
    }

    @Override
    public void saveCountryXML(List<Country> countries, String direct) throws IOException {
        Element root = new Element("countrySave");
        Document doc = new Document(root);
        for (Country country : countries) {
            Element countryElement = new Element("country");
            Element countryId = new Element("countryId");
            Element countryName = new Element("countryName");
            countryId.setText(String.valueOf(country.getId()));
            countryName.setText(country.getName());
            countryElement.addContent(countryId);
            countryElement.addContent(countryName);
            root.addContent(countryElement);
            FileWorking.writeXml(doc, direct);
        }
    }

    @Override
    public List getCountryTraditions(int countryId) throws SQLException {

        Country c = new CountryDaoImpl().getCountry(countryId);
        return (ArrayList)c.getTraditions();
    }

}
