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

public class CommentDaoImpl implements CommentsDao {
    @Override
    public void addComment(Comment comment) throws SQLException {
        Session session;

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(comment);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void deleteComment(Comment comment) throws SQLException {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(comment);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public Comment getComment(int id) throws SQLException {
        Session session;
        Comment comment;
        session = HibernateUtil.getSessionFactory().openSession();
        comment = (Comment) session.get(Comment.class, id);
        if (session.isOpen()) {
            session.close();
        }
        return comment;
    }

    public Long getCommentsCount(Tradition tradition) throws SQLException {
        Session session;
        Long result;
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT COUNT(comment) FROM Comment comment " +
                "WHERE comment.tradition.id = " + tradition.getId().toString();
        Query query = session.createQuery(hql);
        result = (Long) query.uniqueResult();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public List<Comment> getCommentsList(int traditionId) throws SQLException {
        Session session;
        List<Comment> commentsList;
        session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Comment comment WHERE " +
                "comment.tradition.id = " + String.valueOf(traditionId);
        Query query = session.createQuery(hql);
        //noinspection unchecked
        commentsList = (ArrayList<Comment>) query.list();
        if (session.isOpen()) {
            session.close();
        }
        return commentsList;
    }
}
