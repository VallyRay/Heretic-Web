import hdalayer.general.HibernateFactory;
import model.User;
import model.dao.UserDao;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by 1 on 03.11.2015.
 */
public class UserTest {

    @Test
    public void testAddUser() {
        User c = new User("new", "NewUser");
        UserDao dao = HibernateFactory.getInstance().getUserDao();
        dao.addUser(c);
        Assert.assertTrue(dao.getUser("NewUser") != null);
    }

    @Test
    public void testChangeUser() {
        UserDao dao = HibernateFactory.getInstance().getUserDao();
        User c = dao.getUser("NewUser");
        dao.changeUser(c.getId(), "OldUser", "old");
        Assert.assertTrue("OldUser".equals(dao.getUser(c.getId()).getLogin()));
    }

    @Test
    public void testDeleteUser() {
        UserDao dao = HibernateFactory.getInstance().getUserDao();
        User c = dao.getUser("OldUser");
        dao.deleteUser(c);
        Assert.assertTrue(dao.getUser(c.getId()) == null);
    }
}
