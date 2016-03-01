package tieorange.edu.realmdbtest.DataHelpers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import io.realm.Realm;
import io.realm.RealmResults;
import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 26/02/16.
 */
public class RealmHelper {
    public static final String MY_TAG = "MY";

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
    public static RealmResults<ReadingEntry> getReadingEntriesRealmResults(Realm realm) {
        RealmResults<ReadingEntry> readingEntryRealmResults =
                realm.where(ReadingEntry.class).findAll();

        return readingEntryRealmResults;
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

    public static void removeAllRealmData(Realm realm) {
        final RealmResults<ReadingEntry> readingEntriesRealmResults = getReadingEntriesRealmResults(realm);
        readingEntriesRealmResults.removeAll(readingEntriesRealmResults);

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
        return last;
    }

    // Return all reading entries grouped by day (for chart)
    public static TreeMap<Date, List<ReadingEntry>> getGroupedReadingEntriesMap(Realm realm) {
        final List<ReadingEntry> readingEntriesList = getReadingEntriesList(realm);
        final TreeMap<Date, List<ReadingEntry>> groupedReadingEntries = POJOHelper.getGroupedReadingEntries(readingEntriesList);
        return groupedReadingEntries;
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
    }


}
