package model.dao;

/**
 * Created by 1 on 01.11.2015.
 */
public interface DaoFactory {

    CommentsDao getCommentsDao();

    CountryDao getCountryDao();

    HolidayDao getHolidayDao();

    ResourceDao getResourceDao();

    TraditionDao getTraditionDao();

    UserDao getUserDao();
}
