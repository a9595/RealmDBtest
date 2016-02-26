package tieorange.edu.realmdbtest.DataHelpers;

import android.util.Log;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 26/02/16.
 */
public class RealmHelper {
    // Put the reading entry to DB
    public static void createRealmReadingEntry(int currentPage, Realm realm, Date date) {
        realm.beginTransaction();

        // Create an object
        ReadingEntry readingEntry = realm.createObject(ReadingEntry.class);

        readingEntry.setCurrentPage(currentPage);
        readingEntry.setDate(date);

        realm.commitTransaction();
    }

    // Return all reading entries
    public RealmResults<ReadingEntry> getReadingEntries(Realm realm) {
        RealmResults<ReadingEntry> readingEntryRealmResults =
                realm.where(ReadingEntry.class).findAll();

        return readingEntryRealmResults;
    }

    // prints reading entries to log
    public static void printAllReadingEntries(Realm realm) {
        // Reading entries from Realm
        RealmResults<ReadingEntry> readingEntryRealmResults =
                realm.where(ReadingEntry.class).findAll();
        if (readingEntryRealmResults.isEmpty()) return;

        for (ReadingEntry entry : readingEntryRealmResults) {
            Log.d("MY", POJOHelper.getReadingEntryString(entry));
        }
    }


}
