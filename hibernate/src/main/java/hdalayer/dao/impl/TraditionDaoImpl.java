package hdalayer.dao.impl;

import model.dao.TraditionDao;
import model.Holiday;
import model.Tradition;
import org.hibernate.Query;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class TraditionDaoImpl implements TraditionDao {
    @Override
    public void addTradition(Tradition tradition) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tradition);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteTradition(Tradition tradition) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(tradition);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении традиции");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Tradition getTradition(int id) {
        Session session = null;
        Tradition tradition = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tradition = (Tradition) session.get(Tradition.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                //TODO закрыть сессию.
                session.close();
            }
        }
        return tradition;
    }

    @Override
    public List<Tradition> getTraditionList() throws SQLException {
        Session session = null;
        List<Tradition> traditionList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            traditionList = session.createCriteria(Tradition.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка получения списка традиций");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return traditionList;
    }

    public List<Integer> getTraditionIdList() throws SQLException {
        Session session = null;
        List<Integer> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT tradition.id FROM Tradition tradition";
            Query query = session.createQuery(hql);
            result = query.list();
        } catch (Exception e) {
            System.out.println("Ошибка получения id традиций");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    public List<Integer> getTraditionsHoliday(String holidayName)
            throws SQLException {
        Session session = null;
        List<Integer> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT tradition.id FROM Tradition tradition " +
                    "WHERE tradition.holiday.name = '" + holidayName + "'";
            Query query = session.createQuery(hql);
            result = query.list();
        } catch (Exception e) {
            System.out.println("Ошибка получения традиций праздника " + holidayName);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    public List<Integer> getTraditionsCountry(String countryName)
            throws SQLException {
        Session session = null;
        List<Integer> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT tradition.id FROM Tradition tradition " +
                    "WHERE tradition.country.name = '" + countryName + "'";
            Query query = session.createQuery(hql);
            result = query.list();
        } catch (Exception e) {
            System.out.println("Ошибка получения традиций страны " + countryName);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public void changeTradition(int traditionId, int countryId, int holidayId, String description) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Tradition tradition = (Tradition)session.get(Tradition.class, traditionId);
            tradition.setCountry(new CountryDaoImpl().getCountry(countryId));
            tradition.setHoliday(new HolidayDaoImpl().getHoliday(holidayId));
            tradition.setDescription(description);
            session.update(tradition);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при изменении традиции");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
