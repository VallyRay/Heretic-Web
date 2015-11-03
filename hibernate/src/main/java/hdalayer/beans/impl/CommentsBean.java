package hdalayer.beans.impl;

import beans.CommentsWorking;
import func.FileWorking;
import hdalayer.dao.impl.TraditionDaoImpl;
import hdalayer.dao.impl.UserDaoImpl;
import hdalayer.general.HibernateFactory;
import model.Comment;
import model.Tradition;
import model.dao.CommentsDao;
import model.dao.TraditionDao;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//
@Stateless
public class CommentsBean implements CommentsWorking {
    private static CommentsDao dao;

    static {
        dao = HibernateFactory.getInstance().getCommentsDao();
    }

    @Override
    public Long getCommentsCount(Tradition tradition) throws SQLException {
        return dao.getCommentsCount(tradition);
    }

    @Override
    public List<Comment> getCommentsList(int traditionId) throws SQLException {
        return dao.getCommentsList(traditionId);
    }

    @Override
    public List<Comment> selectTraditionComments(int traditionId) throws SQLException {

        Tradition tr = new TraditionDaoImpl().getTradition(traditionId);
        return (ArrayList)tr.getComments();
    }

    @Override
    public void saveCommentsXML(List<Comment> comments, String direct)
            throws IOException, SQLException {
        Element root = new Element("commentsSave");
        Document doc = new Document(root);
        for (Comment com : comments){
            Element commentElement = new Element("comment");
            Element commentId = new Element("commentId");
            Element commentText = new Element("commentText");
            Element traditionId = new Element("traditionId");
            Element userId = new Element("userId");
            Element currentDate = new Element("currentDate");
            commentId.setText(com.getId().toString());
            commentText.setText(com.getText());
            traditionId.setText(com.getTradition().getId().toString());
            userId.setText(com.getUser().getId().toString());
            currentDate.setText(com.getDate().toString());
            commentElement.addContent(commentId);
            commentElement.addContent(commentText);
            commentElement.addContent(traditionId);
            commentElement.addContent(userId);
            commentElement.addContent(currentDate);
            root.addContent(commentElement);
            FileWorking.writeXml(doc, direct);
        }
    }

    @Override
    public void insertComment(String text, int traditionId, int userId) throws SQLException {
        Comment comment = new Comment(text, new TraditionBean().getTradition(traditionId),
                new UserDaoImpl().getUser(userId), new Date());
        dao.addComment(comment);
    }

    @Override
    public void deleteComments(int traditionId) throws SQLException {
        Tradition tr = new TraditionBean().getTradition(traditionId);
        for (Comment c : tr.getComments()) {
            dao.deleteComment(c);
        }
    }

    @Override
    public int getCommentCount(int traditionId) throws SQLException {
        TraditionDao tr_dao = new TraditionDaoImpl();
        return tr_dao.getTradition(traditionId).getComments().size();
    }

    @Override
    public List<Comment> getTraditionComment(int traditionId) throws SQLException {
        TraditionDao tr_dao = new TraditionDaoImpl();
        return (List)tr_dao.getTradition(traditionId).getComments();
    }
}
