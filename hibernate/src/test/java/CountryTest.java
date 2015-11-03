import hdalayer.general.HibernateFactory;
import model.Country;
import model.dao.CountryDao;
import org.apache.solr.client.solrj.response.FacetField;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by 1 on 03.11.2015.
 */
public class CountryTest {

    @Test
    public void testFindCountry() {
        String name = "Russia";
        CountryDao dao = HibernateFactory.getInstance().getCountryDao();
        Country result = dao.getCountry(name);
        Assert.assertTrue(name.equals(result.getName()));
    }

    @Test
    public void testAddCountry() {
        Country c = new Country("France");
        CountryDao dao = HibernateFactory.getInstance().getCountryDao();
        dao.addCountry(c);
        Assert.assertTrue(dao.getCountry("France") != null);
    }

    @Test
    public void testChangeCountry() {
        CountryDao dao = HibernateFactory.getInstance().getCountryDao();
        Country c = dao.getCountry("France");
        dao.changeCountry("Ukraine",c.getId());
        Assert.assertTrue("Ukraine".equals(dao.getCountry(c.getId()).getName()));
    }

    @Test
    public void testDeleteCountry() {
        CountryDao dao = HibernateFactory.getInstance().getCountryDao();
        Country c = dao.getCountry("Ukraine");
        dao.deleteCountry(c);
        Assert.assertTrue(dao.getCountry(c.getId()) == null);
    }
}
