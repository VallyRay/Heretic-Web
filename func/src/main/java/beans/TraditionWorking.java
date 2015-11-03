package beans;

import model.Comment;
import model.Country;
import model.Holiday;
import model.Tradition;
import model.dao.DaoFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public interface TraditionWorking {
    List<Tradition> traditions(DaoFactory factory) throws SQLException;

    Tradition getTradition(int id) throws SQLException;

    Tradition getTradition(DaoFactory factory, int id) throws SQLException;

    void insertTradition(int countryId, int holidayId, int userId, String description) throws SQLException;

    void updateTradition(int traditionId, int countryId, int holidayId, String description) throws SQLException;

    void deleteTradition(int traditionId) throws SQLException;

    List<Tradition> getUserTraditions(int userId) throws SQLException;

    void saveTradition(List<Tradition> traditions, String direct) throws IOException;

    Country getTraditionCountry(int traditionId) throws SQLException;
}
