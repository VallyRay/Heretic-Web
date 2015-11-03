package hdalayer.dao.impl;

import model.dao.CommentsDao;
import model.Comment;
import model.Tradition;
import org.hibernate.Query;
import org.hibernate.Session;
import hdalayer.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentsDao{
    @Override
    public void addComment(Comment comment) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(comment);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении комментария");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteComment(Comment comment) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(comment);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении комментария");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Comment getComment(int id) throws SQLException {
        Session session = null;
        Comment comment = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            comment = (Comment) session.get(Comment.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            //System.err.println("Ошибка поиска комментария по id");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return comment;
    }

    public Long getCommentsCount(Tradition tradition) throws SQLException {
        Session session = null;
        Long result = 0l;
        try {

            /*
            sessionFactory = getHibernateTemplate().getSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    Query query = session
            .createQuery("select value from table where ...");
    query.setParameters("param1", value1);
    result = (Type) query.uniqueResult();
             */

            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT COUNT(comment) FROM Comment comment " +
                    "WHERE comment.tradition.id = " + tradition.getId().toString();
            Query query = session.createQuery(hql);
            result = (Long)query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    public List<Comment> getCommentsList(int traditionId) throws SQLException {
        Session session = null;
        List<Comment> commentsList = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Comment comment WHERE " +
                    "comment.tradition.id = " + String.valueOf(traditionId);
            Query query = session.createQuery(hql);
            commentsList = (ArrayList<Comment>)query.list();
        } catch (Exception e) {
            System.err.println("Ошибка получения списка комментариев");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return commentsList;
    }
}
