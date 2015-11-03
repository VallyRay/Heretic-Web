package model.dao;

import model.Comment;
import model.Tradition;

import java.sql.SQLException;
import java.util.List;

public interface CommentsDao {
    Long getCommentsCount(Tradition tradition) throws SQLException;

    void addComment(Comment comment) throws SQLException;

    void deleteComment(Comment comment) throws SQLException;

    Comment getComment(int id) throws SQLException;

    List<Comment> getCommentsList(int traditionId) throws SQLException;
}
