package model.dao;

import model.Country;

import java.sql.SQLException;
import java.util.List;

public interface CountryDao {
    void addCountry(Country country) throws SQLException;

    void changeCountry(String countryName, int id)  throws SQLException;

    void deleteCountry(Country country) throws SQLException;

    Country getCountry(int id) throws SQLException;

    Country getCountry(String name) throws SQLException;

    List<Country> getCountryList() throws SQLException;
}
