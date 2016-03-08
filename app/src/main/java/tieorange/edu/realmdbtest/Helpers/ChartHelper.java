package tieorange.edu.realmdbtest.Helpers;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.realm.Realm;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 04/03/16.
 */
public class ChartHelper {

    // TODO: use template pattern to unite 2 methods in 1
    public static ArrayList<Entry> getChartEntriesList(Realm realm) {
        ArrayList<Entry> entries = new ArrayList<>();
        final TreeMap<Date, List<ReadingEntry>> groupedReadingEntries = RealmHelper.getGroupedReadingEntriesMap(realm);
        int day = 0;
        int yesterdayPagesCount = 0; // pagesCount from previous day (n-1). Current day is "entry"
        for (Map.Entry<Date, List<ReadingEntry>> entry : groupedReadingEntries.entrySet()) {
            final List<ReadingEntry> dayReadingEntriesList = entry.getValue(); // all reading entries from 1 day
            final ReadingEntry lastReadingEntry = dayReadingEntriesList.get(dayReadingEntriesList.size() - 1); // last reading entry of the day (show only last chart)
            int currentPage = lastReadingEntry.getCurrentPage();

            entries.add(new Entry(currentPage - yesterdayPagesCount, day)); // set pagesCount,  show the delta of today and yesterday pages on chart (progress)
            yesterdayPagesCount = currentPage; // save yesterday pagesCount
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
            labels.add(String.valueOf(i));
        }
        return labels;
    }
}
