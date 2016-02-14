package tieorange.edu.realmdbtest;

import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 02/02/16.
 */
public class PojoHelper {

    public static String getReadingEntryString(ReadingEntry readingEntry) {
        return "Current page = " + readingEntry.getCurrentPage() + "\tDate = " + readingEntry.getDate();

    }

    public static String getBookGoalString(BookGoal bookGoal) {
        return "Pages count = " + bookGoal.getPagesCount() + "\tGoal = " + bookGoal.getGoal();
    }
}
