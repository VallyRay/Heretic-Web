import hdalayer.dao.impl.CountryDaoImpl;
import hdalayer.dao.impl.HolidayDaoImpl;
import hdalayer.dao.impl.UserDaoImpl;
import hdalayer.general.HibernateFactory;
import model.Tradition;
import model.dao.TraditionDao;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by 1 on 03.11.2015.
 */
public class TraditionTest {

    @Test
    public void testFindTradition() {
        int id = 0;
        TraditionDao dao = HibernateFactory.getInstance().getTraditionDao();
        Tradition result = dao.getTradition(0);
        Assert.assertTrue(0 == result.getId());
    }

    @Test
    public void testAddTradition() {
        Tradition c = new Tradition(new HolidayDaoImpl().getHoliday("Beltane"), new CountryDaoImpl().getCountry("Germany"), "TestDescription");
        c.setUser(new UserDaoImpl().getUser(0));
        TraditionDao dao = HibernateFactory.getInstance().getTraditionDao();
        dao.addTradition(c);
        Assert.assertTrue(dao.getTradition(c.getId()) != null);
    }
}
