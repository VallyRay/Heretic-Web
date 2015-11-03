package beans;

import model.Holiday;
import model.HolidayType;
import model.Tradition;
import model.dao.DaoFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface HolidayWorking {
    List<Holiday> getUserHolidays(int userId) throws SQLException;

    List<Holiday> holidays(DaoFactory factory) throws SQLException;

    void insertHoliday(String holidayName, int typeId, Date date) throws SQLException;

    void updateHoliday(int id, String holidayName, int typeId, Date holidayDate) throws SQLException;

    HolidayType selectHolidayType(Holiday holiday) throws SQLException;

    List<Tradition> getHolidayTraditions(int holidayId) throws SQLException;

    List<Tradition> getHolidayTraditions(String holidayName) throws SQLException;

    void saveHolidaysXML(List<Holiday> holidays, String direct) throws IOException;
}
