package func;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter{

    private final String datePattern = "dd.MM";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String s) throws ParseException {
        return dateFormatter.parseObject(s);
    }

    @Override
    public String valueToString(Object o) throws ParseException {
        if (o != null) {
            Calendar cal = (Calendar) o;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }

    public String dateFormat(Date date) {
        return dateFormatter.format(date);
    }
}
