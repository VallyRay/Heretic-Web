package hdalayer.dao.impl;

import model.HolidayType;
import model.dao.HolidayDao;
import model.Holiday;
import org.hibernate.Query;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HolidayDaoImpl implements HolidayDao {
    @Override
    public void addHoliday(Holiday holiday) {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(holiday);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void deleteHoliday(Holiday holiday) {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(holiday);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public Holiday getHoliday(int id) {
        Session session;
        Holiday holiday;
        session = HibernateUtil.getSessionFactory().openSession();
        holiday = (Holiday) session.get(Holiday.class, id);
        if (session.isOpen()) {
            session.close();
        }
        return holiday;
    }

    @Override
    public Holiday getHoliday(String name) {
        Session session;
        Holiday holiday;
        session = HibernateUtil.getSessionFactory().openSession();
        String query = "select holiday from Holiday holiday where holiday.name = :name";
        holiday = (Holiday) session.createQuery(query).setString("name", name).uniqueResult();
        if (session.isOpen()) {
            session.close();
        }
        return holiday;
    }

    @Override
    public List<Holiday> getHolidayList() {
        Session session;
        List<Holiday> holidayList;
        session = HibernateUtil.getSessionFactory().openSession();
        //noinspection unchecked
        holidayList = session.createCriteria(Holiday.class).list();
        if (session.isOpen()) {
            session.close();
        }
        return holidayList;
    }

    public List<Holiday> getUserHolidays(int userId) throws SQLException {
        Session session;
        List<Holiday> result;
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Holiday holiday WHERE holiday.id IN (" +
                "Tradition tradition WHERE tradition.getUser.id = " + userId + ")";
        Query query = session.createQuery(hql);
        //noinspection unchecked
        result = query.list();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public List<Holiday> getHolidaysByNames(String holidayName)
            throws SQLException {
        Session session;
        List<Holiday> result;
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Holiday holiday WHERE holiday.name = " + holidayName;
        Query query = session.createQuery(hql);
        //noinspection unchecked
        result = query.list();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    @Override
    public void changeHoliday(int id, String holidayName, int typeId, Date holidayDate) {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Holiday holiday = (Holiday) session.get(Holiday.class, id);
        holiday.setName(holidayName);
        holiday.setStartDate(holidayDate);
        holiday.setType(HolidayType.values()[typeId]);
        session.update(holiday);
        session.getTransaction().commit();
        System.err.println("Ошибка при изменении праздника");
        if (session.isOpen()) {
            session.close();
        }
    }
}
