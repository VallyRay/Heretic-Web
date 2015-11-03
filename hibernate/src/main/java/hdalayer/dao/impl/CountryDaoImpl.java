package hdalayer.dao.impl;

import model.Tradition;
import model.User;
import model.dao.CountryDao;
import model.Country;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class CountryDaoImpl implements CountryDao{

    @Override
    public void addCountry(Country country) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(country);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении страны");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void changeCountry(String countryName, int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Country country = (Country)session.get(Country.class, id);
            country.setName(countryName);
            session.update(country);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при изменении страны");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteCountry(Country country) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(country);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении страны");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Country getCountry(int id) {
        Session session = null;
        Country country = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            country = (Country) session.get(Country.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка поиска страны по id");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return country;
    }

    @Override
    public Country getCountry(String name) {
        Session session = null;
        Country country = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String query = "select country from Country country where country.name = :name";
            country = (Country) session.createQuery(query).setString("name", name).uniqueResult();
        } catch (Exception e) {
            System.err.println("Ошибка поиска страны по name");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return country;
    }

    @Override
    public List<Country> getCountryList() {
        Session session = null;
        List<Country> countryList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            countryList = session.createCriteria(Country.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка получения списка стран");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return countryList;
    }
}
