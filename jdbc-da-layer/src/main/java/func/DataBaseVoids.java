package func;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataBaseVoids {

    public static Connection con = null;

    public static void startOracle() throws ClassNotFoundException, SQLException {
        Locale.setDefault(Locale.ENGLISH);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "kolobok", "miller");
    }

    //region Select voids
    public static int getIntegerValue(ResultSet resultSet) throws SQLException {
        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        return result;
    }

    public static String getStringValue(ResultSet resultSet) throws SQLException {
        String result = "";
        while (resultSet.next()) {
            result = resultSet.getString(1);
        }
        return result;
    }

    private static ResultSet selectUser(String pass, String login) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT ID FROM USERLIST WHERE LOGIN='" + login + "' AND PASS='" + pass + "'");
        return stmnt.executeQuery();
    }

    private static ResultSet selectUserTraditions(int userID) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM TRADITION WHERE USER_ID = " + userID);
        return stmnt.executeQuery();
    }

    public static ResultSet selectAllTraditions() throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM TRADITION");
        return stmnt.executeQuery();
    }

    private static ResultSet selectCountries() throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM COUNTRY");
        return stmnt.executeQuery();
    }

    public static ResultSet selectHoliday() throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM HOLIDAY");
        return stmnt.executeQuery();
    }

    private static ResultSet getHolidaysType() throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SLEECT NAME FROME TYPE");
        return stmnt.executeQuery();
    }

    public static ResultSet getTraditionComment(int traditionId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT TEXT FROM COMMENTS " +
                "WHERE TRADITION_ID = '" + traditionId + "'");
        return stmnt.executeQuery();
    }

    public static int getReads(int traditionId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT READS FROM STATISTIC " +
                "WHERE TRADITION_ID = '" + traditionId + "'");
        return getIntegerValue(stmnt.executeQuery());
    }

    public static Integer getIdValue(String tableName, String valueName) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT ID FROM " + tableName +
                " WHERE NAME = " + valueName);
        return getIntegerValue(stmnt.executeQuery());
    }

    public static int getLastTraditionID() throws SQLException {
        String request = "SELECT MAX(ID) FROM TRADITION";
        PreparedStatement stmnt = con.prepareStatement(request);
        return getIntegerValue(stmnt.executeQuery());
    }


    public static int getUserId(String login) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT ID FROM USERLIST WHERE LOGIN = '" +
                login + "'");
        return getIntegerValue(stmnt.executeQuery());
    }

    public static String getUserLogin(int id) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT LOGIN FROM USERLIST WHERE ID = '" +
                id + "'");
        return getStringValue(stmnt.executeQuery());
    }

    private static ResultSet selectUserHolidays(int userID) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM HOLIDAY WHERE ID IN (SELECT " +
                "HOLIDAY_ID FROM TRADITION WHERE USER_ID = " + userID + ")");
        return stmnt.executeQuery();
    }

    private static ResultSet selectCountryTraditions(String countryName, int userId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM TRADITION where COUNTRY_ID IN " +
                "(SELECT ID FROM COUNTRY WHERE NAME ='" + countryName +
                "') AND USER_ID =" + userId);
        return stmnt.executeQuery();
    }

    private static ResultSet selectHolidayTraditions(String holidayName, int userId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("SELECT * FROM TRADITION where HOLIDAY_ID IN " +
                "(SELECT ID FROM HOLIDAY WHERE NAME ='" + holidayName +
                "') AND USER_ID =" + userId);
        return stmnt.executeQuery();
    }

    public static ResultSet getUserHolidays(int userId) throws SQLException {
        return selectUserHolidays(userId);
    }

    public static ResultSet getUserTraditions(int userId) throws SQLException {
        return selectUserTraditions(userId);
    }

    public static ResultSet getCountries() throws SQLException {
        return selectCountries();
    }

    public static ResultSet getCountryTraditions(String countryName, int userId) throws SQLException {
        return selectCountryTraditions(countryName, userId);
    }

    private static ResultSet getHolidayTraditions(String holidayName, int userId) throws SQLException {
        return selectHolidayTraditions(holidayName, userId);
    }

    //endregion
    //region Insert voids
    public static ResultSet insertCountry(String countryName) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO COUNTRY (ID, NAME)" +
                "VALUES (country_seq.nextval, '" + countryName + "')");
        return stmnt.executeQuery();
    }

    public static ResultSet insertHoliday(String holidayName, int typeId, String holidayDate) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO HOLIDAY (ID, NAME, " +
                "TYPE_ID, START_DATE) VALUES (holiday_seq.nextval, '" + holidayName + "', '" +
                typeId + "', TO_DATE('" + holidayDate + "', 'DD - MM - YYYY'))");
        return stmnt.executeQuery();
    }

    private static void insertIntoTradition(int countryId, int holidayId, int userId, String description, String imgUrl) throws SQLException {
        String request = "INSERT INTO TRADITION (ID, COUNTRY_ID, " +
                "HOLIDAY_ID, USER_ID, DESCRIPTION, IMG_URL) VALUES (tradition_seq.nextval, '" + countryId +
                "', '" + holidayId + "', '" + userId + "', '" + description + "', '" + imgUrl + "')";
        PreparedStatement stmnt = con.prepareStatement(request);
        stmnt.executeQuery();
    }

    public static void insertTradition(int countryId, int holidayId, int userId, String description, String imgUrl) throws SQLException {
        insertIntoTradition(countryId, holidayId, userId, description, imgUrl);
    }

    public static ResultSet insertComment(String text, int traditionId, int userId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("INSERT INTO COMMENTS (ID, TEXT, " +
                "TRADITION_ID, CUR_DATE, USER_ID) VALUES (comments_seq.nextval, '" + text + "', '"
                + traditionId + "', '" + new java.util.Date() + "', '" + userId + "')");
        return stmnt.executeQuery();
    }

    public static int addUser(String login, String pass) throws SQLException {
        return insertUser(login, pass);
    }

    private static int insertUser(String login, String pass) throws SQLException {
        Statement stmnt = con.createStatement();

        ResultSet set = stmnt.executeQuery("SELECT COUNT (*) as num from ADDRESS");
        int addressCount = 0;
        if (set.next()) addressCount = Integer.parseInt(set.getObject("num").toString()) + 1;
        stmnt.execute("INSERT INTO ADDRESS (ID,COUNTRY_ID, STREET, HOUSE) VALUES ('" + addressCount + "', " + "'1', 'Not defined', '0')");

        int dataCount = 0;
        set = stmnt.executeQuery("Select count(*) as num from USERDATA");
        if (set.next()) dataCount = Integer.parseInt(set.getObject("num").toString()) + 1;
        stmnt.execute("INSERT into USERDATA (ID,NAME, SURNAME, ADDRESS_ID, STATUS_ID) VALUES (" + dataCount + ", 'Not defined', 'Not defined'," + addressCount + ", 2)");

        stmnt.execute("INSERT INTO USERLIST (ID, LOGIN, PASS, DATAID) VALUES (" + dataCount + ", \'" + login + "\', \'" + pass + "\', " + dataCount + ")");
        return dataCount;
    }

    //endregion
    //region Update voids
    public static void updateCountry(int id, String countryName) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("UPDATE COUNTRY SET NAME = '" +
                countryName + "' WHERE ID = '" + id + "'");
        stmnt.executeQuery();
    }

    public static void updateHoliday(int id, String holidayName, int typeId, String holidayDate) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("UPDATE HOLIDAY SET NAME = '" + holidayName +
                "', TYPE_ID = '" + typeId + "', START_DATE = TO_DATE('" + holidayDate + "', 'DD - MM - YYYY') " +
                " WHERE ID = '" + id + "'");
        stmnt.executeQuery();
    }

    public static void updateTradition(int traditionId, int countryId, int holidayId, String description, String imgUrl) throws SQLException {
        String request = "UPDATE TRADITION SET COUNTRY_ID = '" + countryId + "', HOLIDAY_ID = '" +
                holidayId + "', DESCRIPTION = '" + description + "', IMG_URL = '" + imgUrl + "' WHERE " +
                "ID = '" + traditionId + "'";
        PreparedStatement stmnt = con.prepareStatement(request);
        stmnt.executeQuery();
    }

    //endregion
    //region Delete voids
    public static void deleteTradition(int traditionId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("DELETE FROM TRADITION WHERE ID = '" +
                traditionId + "'");
        stmnt.executeQuery();
    }


    public static void deleteComments(int traditionId) throws SQLException {
        PreparedStatement stmnt = con.prepareStatement("DELETE FROM COMMENTS WHERE TRADITION_ID = '" +
                traditionId + "'");
        stmnt.executeQuery();
    }
    //endregion
}
