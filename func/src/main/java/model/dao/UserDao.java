package model.dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void addUser(User user);

    void deleteUser(User user);

    User getUser(int id);

    User getUser(String login);

    List<User> getUsers();

    void changeUser(int id, String login, String pass);
}
