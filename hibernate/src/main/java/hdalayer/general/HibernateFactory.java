package hdalayer.general;

import hdalayer.dao.impl.*;
import model.dao.*;

import java.util.Locale;

public class HibernateFactory implements DaoFactory {
    private static CountryDao countryDAO = null;
    private static HolidayDao holidayDao = null;
    private static TraditionDao traditionDao = null;
    private static CommentsDao commentsDao = null;
    private static UserDao userDao = null;
    private static ResourceDao resourceDao = null;
    private static HibernateFactory instance = null;

    public static synchronized HibernateFactory getInstance() {
        Locale.setDefault(new Locale("en", "US"));
        HibernateFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (HibernateFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new HibernateFactory();
                }
            }
        }
        return localInstance;
    }

    public CountryDao getCountryDao() {
        CountryDao localCountryDao = countryDAO;
        if (localCountryDao == null) {
            synchronized (CountryDao.class) {
                localCountryDao = countryDAO;
                if (localCountryDao == null) {
                    countryDAO = localCountryDao = new CountryDaoImpl();
                }
            }
        }
        return localCountryDao;
    }

    public HolidayDao getHolidayDao() {
        if (holidayDao == null) {
            holidayDao = new HolidayDaoImpl();
        }
        return holidayDao;
    }

    public TraditionDao getTraditionDao() {
        if (traditionDao == null) {
            traditionDao = new TraditionDaoImpl();
        }
        return traditionDao;
    }


    public CommentsDao getCommentsDao() {
        if (commentsDao == null) {
            commentsDao = new CommentDaoImpl();
        }
        return commentsDao;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    public ResourceDao getResourceDao() {
        if (resourceDao == null) {
            resourceDao = new ResourceDaoImpl();
        }
        return resourceDao;
    }
}
