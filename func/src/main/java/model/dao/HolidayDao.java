package model.dao;

import model.Holiday;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface HolidayDao {
    void addHoliday(Holiday holiday);

    void deleteHoliday(Holiday holiday);

    Holiday getHoliday(int id);

    Holiday getHoliday(String name);

    List<Holiday> getHolidayList() throws SQLException;

    List<Holiday> getUserHolidays(int userId) throws SQLException;

    List<Holiday> getHolidaysByNames(String holidayName) throws SQLException;

    void changeHoliday(int id, String holidayName, int typeId, Date holidayDate);
}
