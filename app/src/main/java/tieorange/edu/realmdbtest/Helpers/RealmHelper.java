package tieorange.edu.realmdbtest.Helpers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.realm.Realm;
import io.realm.RealmResults;
import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 26/02/16.
 */
public class RealmHelper {
    private static final String MY_TAG = "MY";

    // Put the reading entry to DB
    public static void createRealmBookGoal(int currentPage, Date date, Realm realm) {
        realm.beginTransaction();

        // Create an object
        ReadingEntry readingEntry = realm.createObject(ReadingEntry.class);

        readingEntry.setCurrentPage(currentPage);
        readingEntry.setDate(date);

        realm.commitTransaction();
    }

    // Return all reading entries in RealmResults<>
    private static RealmResults<ReadingEntry> getReadingEntriesRealmResults(Realm realm) {

        return realm.where(ReadingEntry.class).findAll();
    }

    // MOCK: create dummy entries in realm
    public static void createDummyRealmReadingEntries(Realm realm) {
        final List<ReadingEntry> dummyEntriesList = POJOHelper.getDummyEntriesList();
        for (ReadingEntry entry : dummyEntriesList) {
            createRealmBookGoal(entry.getCurrentPage(), entry.getDate(), realm);
        }
    }


    // Return all reading entries in List<>
    public static List<ReadingEntry> getReadingEntriesList(Realm realm) {
        List<ReadingEntry> readingEntryList = new ArrayList<>();
        final RealmResults<ReadingEntry> readingEntriesRealmResults = getReadingEntriesRealmResults(realm);
        for (ReadingEntry entry : readingEntriesRealmResults) {
            readingEntryList.add(entry);
        }

        return readingEntryList;
    }


    // Creates the goal user set to
    public static void createRealmBookGoal(int pagesCount, int goal, Realm realm) {
        realm.beginTransaction();

        BookGoal bookGoal = realm.createObject(BookGoal.class);

        bookGoal.setPagesCount(pagesCount);
        bookGoal.setGoal(goal);

        realm.commitTransaction();
    }

    // Return the very last reading entry user entered
    public static ReadingEntry getLastReadingEntry(Realm realm) {
        final ReadingEntry last = getReadingEntriesRealmResults(realm).last();
        if (last != null)
            return last;
        else
            return new ReadingEntry(0, POJOHelper.removeTime(new Date())); // return today's 0 reading entry
    }

    // Return first day of reading entry (when user started to read a book)
    public static Date getFirstReadingEntryDate(Realm realm) {
        return getReadingEntriesListLastEveryDay(realm).get(0).getDate();
    }

    // Return all reading entries grouped by day (for chart)
    public static TreeMap<Date, List<ReadingEntry>> getGroupedReadingEntriesMap(Realm realm) {
        final List<ReadingEntry> readingEntriesList = getReadingEntriesList(realm);
        return POJOHelper.getGroupedReadingEntries(readingEntriesList);
    }

    // prints reading entries to log
    public static void printAllReadingEntries(Realm realm) {
        // Reading entries from Realm
        RealmResults<ReadingEntry> readingEntryRealmResults =
                realm.where(ReadingEntry.class).findAll();
        if (readingEntryRealmResults.isEmpty()) return;

        for (ReadingEntry entry : readingEntryRealmResults) {
            Log.d(MY_TAG, POJOHelper.getReadingEntryString(entry));
        }

        Log.d("MY", "size = " + readingEntryRealmResults.size());
    }


    public static List<ReadingEntry> getReadingEntriesListLastEveryDay(Realm realm) {
        List<ReadingEntry> readingEntries = new ArrayList<>();
        final TreeMap<Date, List<ReadingEntry>> groupedReadingEntries = RealmHelper.getGroupedReadingEntriesMap(realm);
        int day = 0;
        int yesterdayPagesCount = 0; // pagesCount from previous day (n-1). Current day is "entry"
        for (Map.Entry<Date, List<ReadingEntry>> entry : groupedReadingEntries.entrySet()) {
            final List<ReadingEntry> dayReadingEntriesList = entry.getValue(); // all reading entries from 1 day
            final ReadingEntry lastReadingEntry = dayReadingEntriesList.get(dayReadingEntriesList.size() - 1); // last reading entry of the day (show only last chart)
            readingEntries.add(lastReadingEntry);
        }
        return readingEntries;
    }


}
