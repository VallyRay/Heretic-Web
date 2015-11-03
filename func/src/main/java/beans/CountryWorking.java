package beans;

import model.Country;
import model.Tradition;
import model.dao.DaoFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CountryWorking {
    List<Country> countries(DaoFactory factory) throws SQLException;

    void insertCountry(String countryName) throws SQLException;

    void updateCountry(int id, String countryName) throws SQLException;

    List<Country> getCountries() throws SQLException;

    void saveCountryXML(List<Country> countries, String direct) throws IOException;

    List<Tradition> getCountryTraditions(int countryId) throws SQLException;
}
