package func;

import java.util.*;

/**
 * Created by iocz on 14/09/15.
 */
public class Content {

    public static ArrayList<String> getPages() {
        ArrayList<String> pages = new ArrayList<>();
        for (int i = Calendar.getTraditions().size() - 1; i > -1; i--) {
            pages.add("htable.jsp?title=".concat(
                    Calendar.getTraditions().get(i).getHoliday().getName()).concat(
                    "&img=/WebApp/images/str/").concat(Integer.toString(i)).concat(
                    "&text=").concat(Calendar.getTraditions().get(i).getDescription()).concat(
                    "&country=").concat(Calendar.getTraditions().get(i).getCountry().getName()));
        }
        return pages;
    }
}
