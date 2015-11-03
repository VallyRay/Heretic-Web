package model.dao;

import model.Tradition;

import java.sql.SQLException;
import java.util.List;

public interface TraditionDao {
    void addTradition(Tradition tradition);

    void deleteTradition(Tradition tradition) throws SQLException;

    Tradition getTradition(int id);

    List<Tradition> getTraditionList() throws SQLException;

    List<Integer> getTraditionIdList() throws SQLException;

    List<Integer> getTraditionsHoliday(String holidayName) throws SQLException;

    List<Integer> getTraditionsCountry(String countryName) throws SQLException;

    void changeTradition(int traditionId, int countryId, int holidayId, String description) throws SQLException;
}
