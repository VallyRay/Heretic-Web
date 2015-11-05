package hdalayer.beans.impl;

import beans.HolidayWorking;
import func.FileWorking;
import hdalayer.general.HibernateFactory;
import model.Holiday;
import model.HolidayType;
import model.Tradition;
import model.dao.DaoFactory;
import model.dao.HolidayDao;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Stateless
public class HolidayBean implements HolidayWorking {

    private static HolidayDao dao;

    static {
        dao = HibernateFactory.getInstance().getHolidayDao();
    }

    @Override
    public List<Holiday> holidays(DaoFactory factory) throws SQLException {
        return dao.getHolidayList();
    }

    @Override
    public void insertHoliday(String holidayName, int typeId, Date date) throws SQLException {
        dao.addHoliday(new Holiday(holidayName, date, HolidayType.values()[typeId].name()));
    }

    @Override
    public List<Holiday> getUserHolidays(int userId) throws SQLException {
        return dao.getUserHolidays(userId);
    }

    @Override
    public void updateHoliday(int id, String holidayName, int typeId, Date holidayDate) throws SQLException{
        dao.changeHoliday(id,holidayName,typeId,holidayDate);
    }

    @Override
    public HolidayType selectHolidayType(Holiday holiday) throws SQLException {
        return holiday.getType();
    }

    @Override
    public List<Tradition> getHolidayTraditions(int holidayId) throws SQLException {
        return (List)dao.getHoliday(holidayId).getTraditions();
    }

    @Override
    public List<Tradition> getHolidayTraditions(String holidayName) throws SQLException {
        return (List)dao.getHoliday(holidayName).getTraditions();
    }

    @Override
    public void saveHolidaysXML(List<Holiday> holidays, String direct) throws IOException {
        Element root = new Element("holidaysSave");
        Document doc = new Document(root);
        for (Holiday holiday : holidays) {
            Element holidayElement = new Element("holiday");
            Element holidayId = new Element("holidayId");
            holidayId.setText(String.valueOf(holiday.getId()));
            holidayElement.addContent(holidayId);

            Element holidayName = new Element("holidayName");
            holidayName.setText(holiday.getName());
            holidayElement.addContent(holidayName);

            Element holidayStartDate = new Element("holidayStartDate");
            DateLabelFormatter formatter = new DateLabelFormatter();
            holidayStartDate.setText(formatter.dateFormat(holiday.getStartDate()));
            holidayElement.addContent(holidayStartDate);

            Element holidayType = new Element("holidayType");
            holidayType.setText(holiday.getType().toString());
            holidayElement.addContent(holidayType);

            root.addContent(holidayElement);
            FileWorking.writeXml(doc, direct);
        }
    }

}
