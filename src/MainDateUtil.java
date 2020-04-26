import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainDateUtil {
    public static void main(String[] args) {
        LocalDate localDate = DateUtil.stringToLocalDate("10/2013");
    }
}
