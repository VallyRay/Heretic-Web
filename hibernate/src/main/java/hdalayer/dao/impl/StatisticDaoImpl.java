package hdalayer.dao.impl;

import model.Statistic;
import model.dao.StatisticDao;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;

public class StatisticDaoImpl implements StatisticDao{
    @Override
    public void addStatistic(Statistic statistic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(statistic);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении статистики традиции");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteStatistic(Statistic statistic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(statistic);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении статистики традиции");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Statistic getStatistic(int id) throws SQLException {
        Session session = null;
        Statistic statistic = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            statistic = (Statistic) session.get(Statistic.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка поиска статистики традиции по id");
        } finally {
            if (session != null && session.isOpen()) {
                //TODO fix lazy init error.
                //session.close();
            }
        }
        return statistic;
    }
}
