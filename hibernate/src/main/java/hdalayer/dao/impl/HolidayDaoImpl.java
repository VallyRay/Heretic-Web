package hdalayer.dao.impl;

import model.HolidayType;
import model.dao.HolidayDao;
import model.Holiday;
import org.hibernate.Query;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HolidayDaoImpl implements HolidayDao {
    @Override
    public void addHoliday(Holiday holiday) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(holiday);
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
    public void deleteHoliday(Holiday holiday) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(holiday);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении праздника");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Holiday getHoliday(int id) {
        Session session = null;
        Holiday holiday = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            holiday = (Holiday) session.get(Holiday.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return holiday;
    }

    @Override
    public Holiday getHoliday(String name) {
        Session session = null;
        Holiday holiday = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String query = "select holiday from Holiday holiday where holiday.name = :name";
            holiday= (Holiday) session.createQuery(query).setString("name", name).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return holiday;
    }

    @Override
    public List<Holiday> getHolidayList() throws SQLException {
        Session session = null;
        List<Holiday> holidayList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            holidayList = session.createCriteria(Holiday.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка получения списка праздников");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return holidayList;
    }

    public List<Holiday> getUserHolidays(int userId) throws SQLException {
        Session session = null;
        List<Holiday> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Holiday holiday WHERE holiday.id IN (" +
                    "Tradition tradition WHERE tradition.getUser.id = " + userId + ")";
            Query query = session.createQuery(hql);
            result = query.list();
        } catch (Exception e) {
            System.err.println("Ошибка получения праздников пользователя");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    public List<Holiday> getHolidaysByNames(String holidayName)
            throws SQLException {
        Session session = null;
        List<Holiday> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Holiday holiday WHERE holiday.name = " + holidayName;
            Query query = session.createQuery(hql);
            result = query.list();
        } catch (Exception e) {
            System.out.println("Ошибка получени праздников по имени");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public void changeHoliday(int id, String holidayName, int typeId, Date holidayDate) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Holiday holiday = (Holiday)session.get(Holiday.class, id);
            holiday.setName(holidayName);
            holiday.setStartDate(holidayDate);
            holiday.setType(HolidayType.values()[typeId]);
            session.update(holiday);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при изменении праздника");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
