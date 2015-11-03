package func;

import model.Country;
import model.Holiday;
import model.Tradition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Calendar {
    private static List<Holiday> holidays = new LinkedList<>();
    private static List<Country> countries = new LinkedList<>();
    private static ArrayList<Tradition> traditions = new ArrayList<>();

    private static int userId = -1;
    private static boolean isWrongLoginPass = false;
    private static boolean showNews = false;

    public static void setUserId(int userId) {
        Calendar.userId = userId;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setIsWrongLoginPass(boolean isWrongLoginPass) {
        Calendar.isWrongLoginPass = isWrongLoginPass;
    }

    public static void setShowNews(boolean showNews) {
        Calendar.showNews = showNews;
    }

    public static void setCountries(List<Country> countryList) {
        countries = countryList;
    }

    public static void setHolidays(List<Holiday> holidayList) {
        holidays = holidayList;
    }

    public static void setTraditions(List<Tradition> traditionList) {
        //noinspection unchecked
        traditions = (ArrayList) traditionList;
    }

    public static void setTraditionsForUser(List<Tradition> traditions) {
        Iterator it = traditions.iterator();
        while (it.hasNext()) {
            if (((Tradition) it.next()).getUser().getId() != Calendar.userId) it.remove();
        }
    }

    public static boolean getIsWrongLoginPass() {
        return isWrongLoginPass;
    }

    public static boolean getShowNews() {
        return showNews;
    }

    public static List<Holiday> getHolidays() {
        return holidays;
    }

    public static List<Country> getCountries() {
        return countries;
    }

    public static List<Tradition> getTraditions() {
        return traditions;
    }

    public static void logout() {
        Calendar.setUserId(-1);
        Calendar.setIsWrongLoginPass(false);
    }
}
