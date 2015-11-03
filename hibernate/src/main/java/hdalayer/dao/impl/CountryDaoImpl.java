package hdalayer.dao.impl;

import model.dao.CountryDao;
import model.Country;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class CountryDaoImpl implements CountryDao {

    @Override
    public void addCountry(Country country) throws SQLException {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(country);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void changeCountry(String countryName, int id) throws SQLException {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Country country = (Country) session.get(Country.class, id);
        country.setName(countryName);
        session.update(country);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void deleteCountry(Country country) throws SQLException {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(country);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public Country getCountry(int id) throws SQLException {
        Session session;
        Country country;
        session = HibernateUtil.getSessionFactory().openSession();
        country = (Country) session.get(Country.class, id);
        if (session.isOpen()) {
            session.close();
        }
        return country;
    }

    @Override
    public Country getCountry(String name) {
        Session session;
        Country country;
        session = HibernateUtil.getSessionFactory().openSession();
        String query = "select country from Country country where country.name = :name";
        country = (Country) session.createQuery(query).setString("name", name).uniqueResult();
        if (session.isOpen()) {
            session.close();
        }
        return country;
    }

    @Override
    public List<Country> getCountryList() {
        Session session;
        List<Country> countryList;
        session = HibernateUtil.getSessionFactory().openSession();
        //noinspection unchecked
        countryList = session.createCriteria(Country.class).list();
        if (session.isOpen()) {
            session.close();
        }
        return countryList;
    }
}
