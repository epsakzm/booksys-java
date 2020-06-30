package booksys.presentation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    private static CurrentState state = CurrentState.getInstance();

    public String format(LogRecord rec) {

        if (state.getLoggedIn()) {
            String ad_id = state.getAdmin().getId();
            return String.format("(%s) %s : %s\n",
                    ad_id,
                    calcDate(rec.getMillis()),
                    rec.getMessage());
        }
        return String.format("%s : %s\n",
                calcDate(rec.getMillis()),
                rec.getMessage());
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}