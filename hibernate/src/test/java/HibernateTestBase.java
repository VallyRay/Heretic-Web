import hdalayer.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.*;

/**
 * Created by 1 on 01.11.2015.
 */
@Test
public abstract class HibernateTestBase {

    Transaction tr;
    Session session;

    @BeforeClass
    public void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @AfterClass
    public void closeSession() {
        session.close();
    }

    @BeforeMethod
    public void startTransaction() {
        tr = session.beginTransaction();
    }

    @AfterMethod
    public void commitTransaction() {
        tr.commit();
    }
}
