package tieorange.edu.realmdbtest.DataHelpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 02/02/16.
 */
public class POJOHelper {

    public static String getReadingEntryString(ReadingEntry readingEntry) {
        return "Current page = " + readingEntry.getCurrentPage() + "\tDate = " + readingEntry.getDate();

    }

    public static String getBookGoalString(BookGoal bookGoal) {
        return "Pages count = " + bookGoal.getPagesCount() + "\tGoal = " + bookGoal.getGoal();
    }

    public static List<ReadingEntry> getDummyEntriesList() {
        // TODO
        List<ReadingEntry> readingEntries = new ArrayList<>();

        readingEntries.add(new ReadingEntry(1, parseDate("01.01.2016"))); // 1st group
        readingEntries.add(new ReadingEntry(10, parseDate("02.01.2016"))); // 2nd group
        readingEntries.add(new ReadingEntry(15, parseDate("02.01.2016")));
        readingEntries.add(new ReadingEntry(20, parseDate("03.01.2016"))); // 3nd group
        readingEntries.add(new ReadingEntry(40, parseDate("04.01.2016"))); // 4th group
        readingEntries.add(new ReadingEntry(45, parseDate("04.01.2016")));


        return readingEntries;
    }

    public static Map<Date, List<ReadingEntry>> getGroupedReadingEntries(List<ReadingEntry> readingEntriesList) {
        TreeMap<Date, List<ReadingEntry>> hashMap = new TreeMap<>();
        for (ReadingEntry entry : readingEntriesList) {
            Date key = entry.getDate();
            if (hashMap.containsKey(key)) { // TODO: check if day is the same (ignoring time)
                List<ReadingEntry> list = hashMap.get(key);
                list.add(entry);
            } else {
                List<ReadingEntry> list = new ArrayList<>();
                list.add(entry);
                hashMap.put(key, list);
            }
        }
        return hashMap;
    }
    public void sortMap(){
        HashMap<Integer, String> hmap = new HashMap<Integer, String>();
        hmap.put(5, "A");
        hmap.put(11, "C");
        hmap.put(4, "Z");
        hmap.put(77, "Y");
        hmap.put(9, "P");
        hmap.put(66, "Q");
        hmap.put(0, "R");

        System.out.println("Before Sorting:");
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        Map<Integer, String> map = new TreeMap<Integer, String>(hmap);
        System.out.println("After Sorting:");
        Set set2 = map.entrySet();
        Iterator iterator2 = set2.iterator();
        while(iterator2.hasNext()) {
            Map.Entry me2 = (Map.Entry)iterator2.next();
            System.out.print(me2.getKey() + ": ");
            System.out.println(me2.getValue());
        }
    }


    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd.mm.yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
