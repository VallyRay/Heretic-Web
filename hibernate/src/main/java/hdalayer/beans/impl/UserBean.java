package hdalayer.beans.impl;

import beans.UserWorking;
import func.FileWorking;
import hdalayer.general.HibernateFactory;
import model.User;
import model.dao.UserDao;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.SQLException;

@Stateless
public class UserBean implements UserWorking {

    private static UserDao dao;

    static {
        dao = HibernateFactory.getInstance().getUserDao();
    }

    @Override
    public boolean isAuthenticate(String login, String pass) throws SQLException {
        User u = dao.getUser(login);
        if ((u!=null)&&(pass.equals(u.getPass()))) {
            Calendar.setUserId(u.getId());
            return true;
        }
        return false;
    }

    @Override
    public int getUserId(String login) throws SQLException {
        return dao.getUser(login).getId();
    }

    @Override
    public int addUser(String login, String pass) throws SQLException {
        User u = new User(pass, login);
        dao.addUser(u);
        return u.getId();
    }

    @Override
    public String getUserLogin(int id) throws SQLException {
        return dao.getUser(id).getLogin();
    }

    @Override
    public void saveUsers(String direct) throws IOException, SQLException {
        Element root = new Element("userSave");
        Document doc = new Document(root);
        for (User u : dao.getUsers()) {
            Element userElement = new Element("user");
            Element userId = new Element("userId");
            Element userName = new Element("userName");
            Element userPass = new Element("userPass");

            userId.setText(u.getId().toString());
            userName.setText(u.getLogin());
            userPass.setText(u.getPass());
            userElement.addContent(userName);
            userElement.addContent(userPass);
            root.addContent(userElement);
            FileWorking.writeXml(doc, direct);
        }
    }

    @Override
    public void updateUser(int id, String login, String pass) {
        dao.changeUser(id, login, pass);
    }
}
