package hdalayer.beans.impl;

import beans.TraditionWorking;
import func.FileWorking;
import hdalayer.dao.impl.CountryDaoImpl;
import hdalayer.dao.impl.HolidayDaoImpl;
import hdalayer.dao.impl.UserDaoImpl;
import hdalayer.general.HibernateFactory;
import model.Country;
import model.Tradition;
import model.dao.DaoFactory;
import model.dao.TraditionDao;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TraditionBean implements TraditionWorking {
    private static TraditionDao dao;

    static {
        dao = HibernateFactory.getInstance().getTraditionDao();
    }

    @Override
    public List<Tradition> traditions(DaoFactory factory) throws SQLException {
        return dao.getTraditionList();
    }

    @Override
    public Tradition getTradition(int id) throws SQLException{
        return dao.getTradition(id);
    }
    @Override
    public Tradition getTradition(DaoFactory factory, int id) throws SQLException {
        return dao.getTradition(id);
    }

    @Override
    public void insertTradition(int countryId, int holidayId, int userId, String description) throws SQLException {
        dao.addTradition(new Tradition(new HolidayDaoImpl().getHoliday(holidayId),
                new CountryDaoImpl().getCountry(countryId),
                description,
                new UserDaoImpl().getUser(userId)));
    }

    public void updateTradition(int traditionId, int countryId, int holidayId, String description) throws SQLException {
        dao.changeTradition(traditionId,countryId,holidayId,description);
    }

    public void deleteTradition(int traditionId) throws SQLException{
        dao.deleteTradition(dao.getTradition(traditionId));
    }

    public List getUserTraditions(int userId) throws SQLException {
        return (ArrayList)new UserDaoImpl().getUser(userId).getTraditions();
    }

    @Override
    public void saveTradition(List<Tradition> traditions, String direct) throws IOException {
        Element root = new Element("traditionSave");
        Document doc = new Document(root);
        for (Tradition tradition : traditions) {
            Element traditionElement = new Element("tradition");

            Element traditionDescription = new Element("traditionDescription");
            traditionDescription.setText(tradition.getDescription());
            traditionElement.addContent(traditionDescription);

            Element traditionId = new Element("traditionId");
            traditionId.setText(String.valueOf(tradition.getId()));
            traditionElement.addContent(traditionId);

            //TODO add holiday bean.
            Element holidayElement = new Element("holiday");

            Element holidayName = new Element("holidayName");
            holidayName.setText(tradition.getHoliday().getName());
            holidayElement.addContent(holidayName);

            Element holidayStartDate = new Element("holidayStartDate");
            DateLabelFormatter formatter = new DateLabelFormatter();
            holidayStartDate.setText(formatter.dateFormat(tradition.getHoliday().getStartDate()));
            holidayElement.addContent(holidayStartDate);

            Element holidayType = new Element("holidayType");
            holidayType.setText(tradition.getHoliday().getType().toString());
            holidayElement.addContent(holidayType);

            traditionElement.addContent(holidayElement);

            Element elementCountry = new Element("country");

            Element countryName = new Element("countryName");
            countryName.setText(tradition.getCountry().getName());
            elementCountry.addContent(countryName);

            traditionElement.addContent(elementCountry);

            root.addContent(traditionElement);
            FileWorking.writeXml(doc, direct);
        }
    }

    @Override
    public Country getTraditionCountry(int traditionId) throws SQLException {
        return dao.getTradition(traditionId).getCountry();
    }
}
