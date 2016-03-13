package tieorange.edu.realmdbtest.Helpers;

import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.realm.Realm;
import tieorange.edu.realmdbtest.POJO.AndriiBook;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 04/03/16.
 */
public class ChartHelper {

    private static final String TAG = "CharHelper";

    // TODO: use template pattern to unite 2 methods in 1
    public static ArrayList<Entry> getChartEntriesList(Realm realm) {
        ArrayList<Entry> entries = new ArrayList<>();
        final TreeMap<Date, List<ReadingEntry>> groupedReadingEntries = RealmHelper.getGroupedReadingEntriesMap(realm);
        int day = 0;
        int yesterdayPagesCount = 0; // pagesCount from previous day (n-1). Current day is "entry"
        /*for (Map.Entry<Date, List<ReadingEntry>> entry : groupedReadingEntries.entrySet()) {
            final List<ReadingEntry> dayReadingEntriesList = entry.getValue(); // all reading entries from 1 day
            final ReadingEntry lastReadingEntry = dayReadingEntriesList.get(dayReadingEntriesList.size() - 1); // last reading entry of the day (show only last chart)
            int currentPage = lastReadingEntry.getCurrentPage();

            entries.add(new Entry(currentPage - yesterdayPagesCount, day)); // set pagesCount,  show the delta of today and yesterday pages on chart (progress)
            yesterdayPagesCount = currentPage; // save yesterday pagesCount
            day++;
        }*/

        Date today = new Date();

        Log.i(TAG, "getChartEntriesList: today date = " + POJOHelper.getDateDayMonthYearString(today));

        Date bookStartReadingDate = AndriiBook.getBookStartReadingDate(); //
        for (Date iteratedDate = bookStartReadingDate;
             iteratedDate.compareTo(today) <= 0;
             iteratedDate = POJOHelper.incrementByOneDay(iteratedDate)){
            boolean entryExistsAtIteratedDate = groupedReadingEntries.containsKey(iteratedDate);

            Log.i(TAG, "getChartEntriesList: day = " + day);
            Log.i(TAG, "getChartEntriesList: current iterated date = " + POJOHelper.getDateDayMonthYearString(iteratedDate));

            if (entryExistsAtIteratedDate){
                List<ReadingEntry> entriesOnIteratedDate = groupedReadingEntries.get(iteratedDate);
                final ReadingEntry lastReadingEntry = entriesOnIteratedDate.get(entriesOnIteratedDate.size() - 1); // last reading entry of the day (show only last chart)
                int currentPage = lastReadingEntry.getCurrentPage();
                entries.add(new Entry(currentPage - yesterdayPagesCount, day));
                yesterdayPagesCount = currentPage;
            } else {
                entries.add(new Entry(0, day));
            }

            day++;
        }

        return entries;
    }

    public static ArrayList<BarEntry> getChartBarEntriesList(Realm realm) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        final TreeMap<Date, List<ReadingEntry>> groupedReadingEntries = RealmHelper.getGroupedReadingEntriesMap(realm);
        int day = 0;
        int yesterdayPagesCount = 0; // pagesCount from previous day (n-1). Current day is "entry"
        for (Map.Entry<Date, List<ReadingEntry>> entry : groupedReadingEntries.entrySet()) {
            final List<ReadingEntry> dayReadingEntriesList = entry.getValue(); // all reading entries from 1 day
            final ReadingEntry lastReadingEntry = dayReadingEntriesList.get(dayReadingEntriesList.size() - 1); // last reading entry of the day (show only last chart)
            int currentPage = lastReadingEntry.getCurrentPage();
            entries.add(new BarEntry(currentPage - yesterdayPagesCount, day)); // set pagesCount,  show the delta of today and yesterday pages on chart (progress)
            yesterdayPagesCount = currentPage; // save yesterday pagesCount
            day++;
        }
        return entries;
    }

    public static ArrayList<String> getChartLabels(int entriesSize) {
        ArrayList<String> labels = new ArrayList<>(); // day

//        entriesSize = 8; // TODO: mock
        if (entriesSize < 7) // to show chart for
            entriesSize = 7;

        for (int i = 1; i <= entriesSize; i++) {
//            labels.add(String.valueOf(i));
            labels.add("3");
        }
        return labels;
    }

    public static ArrayList<String> getChartLabelsFromEntries(ArrayList<Entry> entries){
        ArrayList<String> result = new ArrayList<>();
        Date today = new Date();
        for (   Date iteratedDate = AndriiBook.getBookStartReadingDate();
                iteratedDate.compareTo(today) <= 0;
                iteratedDate = POJOHelper.incrementByOneDay(iteratedDate)) {

            result.add(POJOHelper.getDateDayMonthString(iteratedDate));
        }
        return result;
    }

    public static ArrayList<String> getChartLabelsWithDate(List<ReadingEntry> readingEntryList) {
        ArrayList<String> labels = new ArrayList<>(); // day
        int entriesSize = readingEntryList.size();
//        entriesSize = 8; // TODO: mock
//        if (entriesSize < 7) { // to show chart for
//            entriesSize = 7;
//        }

        int readingEntriesSize = readingEntryList.size();

        for (int i = 1; i <= entriesSize; i++) {
            Date date = null;
            if (i < readingEntriesSize) {
                date = readingEntryList.get(i).getDate();
                String dateDayMonthString = POJOHelper.getDateDayMonthString(date);
                labels.add(dateDayMonthString);
            } else {
                if(date != null) {

                    // increment date by one
                    Date incrementedDate = POJOHelper.incrementByOneDay(date);
                    // to string
                    String dateIncremented;
                    labels.add(String.valueOf(i));
                }

            }

        }
        return labels;
    }
}
