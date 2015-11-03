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
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(resource);
        session.getTransaction().commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void deleteResource(Resource resource) throws SQLException {
        Session session = null;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(resource);
        session.getTransaction().commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Override
    public Resource getResource(int id) throws SQLException {
        Session session = null;
        Resource resource = null;
        session = HibernateUtil.getSessionFactory().openSession();
        resource = (Resource) session.get(Resource.class, id);
        if (session != null && session.isOpen()) {
            session.close();
        }
        return resource;
    }

    @Override
    public List<? extends Resource> getResourceList() throws SQLException {
        Session session = null;
        List<? extends Resource> countryList = null;
        session = HibernateUtil.getSessionFactory().openSession();
        //noinspection unchecked
        countryList = session.createCriteria(Resource.class).list();
        if (session != null && session.isOpen()) {
            session.close();
        }
        return countryList;
    }
}
