package tieorange.edu.realmdbtest.POJO;

import org.joda.time.DateTime;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by tieorange on 31/01/16.
 */
public class ReadingEntry extends RealmObject {
    private int currentPage;
    private Date date;
//    private BookGoal bookGoal;
//    private DateTime dateTime;

    public ReadingEntry() {
    }

    public ReadingEntry(int currentPage, Date date) {
        this.currentPage = currentPage;
        this.date = date;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
