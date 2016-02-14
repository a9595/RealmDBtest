package tieorange.edu.realmdbtest.POJO;

import io.realm.RealmObject;

/**
 * Created by tieorange on 01/02/16.
 */
public class BookGoal extends RealmObject {
    private int pagesCount;
    private int goal;

    public BookGoal() {
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

}
