package beans;

import java.io.IOException;
import java.sql.SQLException;

public interface UserWorking {
    
    boolean isAuthenticate(String login, String pass) throws SQLException;

    int getUserId(String login) throws SQLException;

    int addUser(String login, String pass) throws SQLException;

    String getUserLogin(int id) throws SQLException;

    void saveUsers(String direct) throws IOException, SQLException;

    void updateUser(int id, String login, String pass);
}
