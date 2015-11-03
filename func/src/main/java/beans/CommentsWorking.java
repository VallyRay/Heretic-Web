package beans;

import model.Comment;
import model.Tradition;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CommentsWorking {
    Long getCommentsCount(Tradition tradition) throws SQLException;

    List<Comment> selectTraditionComments(int traditionId) throws SQLException;

    void saveCommentsXML(List<Comment> comments, String direct) throws IOException, SQLException;

    List<Comment> getCommentsList(int traditionId) throws SQLException;

    void insertComment(String text, int traditionId, int userId) throws SQLException;

    void deleteComments(int traditionId) throws SQLException;

    int getCommentCount(int traditionId) throws SQLException;

    List<Comment> getTraditionComment(int traditionId) throws SQLException;
}
