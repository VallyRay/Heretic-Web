//package hdalayer.general;
//
//import model.*;
//import model.dao.*;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class Main {
//    public static void startOracle(String[] args) throws SQLException{
//        HibernateFactory factory = HibernateFactory.getInstance();
//        CountryDao countryDao = factory.getCountryDAO();
//        HolidayDao holidayDao = factory.getHolidayDao();
//        TraditionDao traditionDao = factory.getTraditionDao();
//        CommentsDao commentsDao = factory.getCommentsDao();
//        UserDao userDao = factory.getUserDao();
//        ResourceDao resourceDao = factory.getResourceDao();
//
//        //Comment comment = commentsDao.getComment(100);
//        //System.out.println(comment.getUser().getLogin());
//        //System.out.println(userDao.getUser(1).toString());
//        /*
//        Comment comment = new Comment();
//        comment.setId(1);
//        comment.setText("Hello, World!");
//        comment.setDate(new Date());
//        comment.setTradition(traditionDao.getTradition(1));
//        //comment.setUser(new User("1", "1"));
//        commentsDao.deleteComment(comment);
//        */
//        //Tradition tradition = new Tradition(9, holidayDao.getHoliday(1),
//                //countryDao.getCountry(1), "Some description");
//        //traditionDao.deleteTradition(tradition);
//        //Country country = countryDao.getCountry(1);
//        //Holiday holiday = holidayDao.getHoliday(1);
//        @SuppressWarnings("unchecked") ArrayList<Tradition> tradition = (ArrayList)traditionDao.getTraditionList();
//        for (Tradition trad : tradition) {
//            System.out.println(trad.getName());
//        }
//        //System.out.println(holiday.getName());
//        //System.out.println(tradition.getName());
//    }
//}
