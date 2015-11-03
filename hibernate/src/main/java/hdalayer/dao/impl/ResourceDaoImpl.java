package hdalayer.dao.impl;

import org.hibernate.Session;
import model.dao.ResourceDao;
import model.Resource;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class ResourceDaoImpl implements ResourceDao {

    @Override
    public void addResource(Resource resource) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(resource);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении ресурса");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteResource(Resource resource) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(resource);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении ресурса");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    //TODO придумать передачу класса.
    @Override
    public Resource getResource(int id) throws SQLException {
        Session session = null;
        Resource resource = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            resource = (Resource) session.get(Resource.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка поиска ресурса по id");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return resource;
    }

    @Override
    public List<? extends Resource> getResourceList() throws SQLException {
        Session session = null;
        List<? extends Resource> countryList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            countryList = session.createCriteria(Resource.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка получения списка ресурсов");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return countryList;
    }
}
