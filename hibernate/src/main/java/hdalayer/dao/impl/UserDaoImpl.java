package hdalayer.dao.impl;

import model.dao.UserDao;
import model.User;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public void addUser(User user) {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void changeUser(int id, String login, String pass) {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        user.setLogin(login);
        user.setPass(pass);
        session.update(user);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void deleteUser(User user) {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public User getUser(int id) {
        Session session;
        User user;
        session = HibernateUtil.getSessionFactory().openSession();
        user = (User) session.get(User.class, id);
        if (session.isOpen()) {
            session.close();
        }
        return user;
    }

    @Override
    public User getUser(String login) {
        Session session;
        User user;
        session = HibernateUtil.getSessionFactory().openSession();
        String query = "select user from User user where user.login = :login";
        user = (User) session.createQuery(query).setString("login", login).uniqueResult();
        System.err.println("Ошибка поиска пользователя по login");
        if (session.isOpen()) {
            session.close();
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        Session session;
        List userList;
        session = HibernateUtil.getSessionFactory().openSession();
        userList = session.createCriteria(User.class).list();
        if (session.isOpen()) {
            session.close();
        }
        //noinspection unchecked
        return userList;
    }
}
