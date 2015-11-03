package model.dao;

import model.Country;

import java.util.List;

public interface CountryDao {
    void addCountry(Country country);

    void changeCountry(String countryName, int id);

    void deleteCountry(Country country);

    Country getCountry(int id);

    Country getCountry(String name);

    List<Country> getCountryList();
}
