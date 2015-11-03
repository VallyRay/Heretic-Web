package hdalayer.dao.impl;

import model.dao.UserDao;
import model.User;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public void addUser(User user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении пользоваетля");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void changeUser(int id, String login, String pass) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User)session.get(User.class, id);
            user.setLogin(login);
            user.setPass(pass);
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при изменении пользователя");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public User getUser(int id) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (User) session.get(User.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка поиска пользователя по id");
        } finally {
            if (session != null && session.isOpen()) {
                //TODO fix lazy init error.
                session.close();
            }
        }
        return user;
    }

    @Override
    public User getUser(String login) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String query = "select user from User user where user.login = :login";
            user = (User) session.createQuery(query).setString("login", login).uniqueResult();
        } catch (Exception e) {
            System.err.println("Ошибка поиска пользователя по login");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        Session session = null;
        List userList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            userList = session.createCriteria(User.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка получения списка праздников");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        //noinspection unchecked
        return userList;
    }
}
