package tieorange.edu.realmdbtest.POJO;

import java.util.Date;
import java.util.GregorianCalendar;

public class AndriiBook {

    private static final GregorianCalendar bookStartReadingDate = new GregorianCalendar(2016, 2, 1);

    public static Date getBookStartReadingDate(){
        return bookStartReadingDate.getTime();
    }

}
