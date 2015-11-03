import hdalayer.general.HibernateFactory;
import model.Holiday;
import model.HolidayType;
import model.dao.HolidayDao;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by 1 on 01.11.2015.
 */
public class HolidayTest {

    @Test
    public void testAddHolidayWithNewType() {
        Holiday holiday = new Holiday("Halloween 3", HolidayType.NATIVE.name());
        HolidayDao dao = HibernateFactory.getInstance().getHolidayDao();
        dao.addHoliday(holiday);
        Assert.assertTrue(holiday.getId() != 0);
    }

    @Test
    public void testFindHoliday() {
        String name = "Beltane";
        HolidayDao dao = HibernateFactory.getInstance().getHolidayDao();
        Holiday result = dao.getHoliday(name);
        Assert.assertTrue(name.equals(result.getName()));
    }

    @Test
    public void testAddHoliday() {
        Holiday c = new Holiday("New New Year");
        HolidayDao dao = HibernateFactory.getInstance().getHolidayDao();
        dao.addHoliday(c);
        Assert.assertTrue(dao.getHoliday("New New Year") != null);
    }

    @Test
    public void testChangeHoliday() {
        HolidayDao dao = HibernateFactory.getInstance().getHolidayDao();
        Holiday c = dao.getHoliday("New New Year");
        dao.changeHoliday(c.getId(), "Old New Year", 1, new Date());
        Assert.assertTrue("Old New Year".equals(dao.getHoliday(c.getId()).getName()));
    }

    @Test
    public void testDeleteHoliday() {
        HolidayDao dao = HibernateFactory.getInstance().getHolidayDao();
        Holiday c = dao.getHoliday("Old New Year");
        dao.deleteHoliday(c);
        Assert.assertTrue(dao.getHoliday(c.getId()) == null);
    }
}
