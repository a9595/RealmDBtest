package tieorange.edu.realmdbtest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.POJOHelper;
import tieorange.edu.realmdbtest.R;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

public class MainActivity extends AppCompatActivity {

    private Realm mMyRealm;
    private BookGoal mBookGoal;
    private NumberPicker mUiNumberCurrentPage;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mMyRealm = Realm.getInstance(this); // setup Realm
        mView = findViewById(android.R.id.content);

        // Check if the book is added (goal, pagesCount)
        RealmResults<BookGoal> bookGoalRealmResults =
                mMyRealm.where(BookGoal.class).findAll();

        // Trying to get book settings
        if (bookGoalRealmResults.size() != 0) {
            mBookGoal = bookGoalRealmResults.get(0);
            setupFAB();
            setupNumberPicker();
            printAllReadingEntries();
        } else { // Setup book settings (Welcome activity)
            Intent intent = new Intent(getApplicationContext(), AddBookActivity.class);
            startActivity(intent);
        }
    }


    // prints reading entries to log
    private void printAllReadingEntries() {
        // Reading entries from Realm
        RealmResults<ReadingEntry> readingEntryRealmResults =
                mMyRealm.where(ReadingEntry.class).findAll();
        if (readingEntryRealmResults.isEmpty()) return;

        for (ReadingEntry entry : readingEntryRealmResults) {
            Log.d("MY", POJOHelper.getReadingEntryString(entry));
        }

        // set number picket to last page user read
        mUiNumberCurrentPage.setValue(readingEntryRealmResults.last().getCurrentPage());
    }

    public RealmResults<ReadingEntry> getReadingEntries(){
        RealmResults<ReadingEntry> readingEntryRealmResults =
                mMyRealm.where(ReadingEntry.class).findAll();

        return readingEntryRealmResults;
    }

    private void setupNumberPicker() {
        // Setup goal
        mUiNumberCurrentPage = (NumberPicker) findViewById(R.id.main_number_current_page);
        mUiNumberCurrentPage.setMinValue(0);
        mUiNumberCurrentPage.setMaxValue(mBookGoal.getPagesCount());
        mUiNumberCurrentPage.setWrapSelectorWheel(true);
        mUiNumberCurrentPage.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
            }
        });
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Entry has been created", Snackbar.LENGTH_SHORT)
                        .setAction("Create", null).show();

                Intent intent = new Intent(getApplicationContext(), ChartsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SaveCurrentPagOnClick(View view) {
        int currentPage = mUiNumberCurrentPage.getValue();
        if (currentPage > 0) { // TODO: check if page is bigger than last save page
            createRealmReadingEntry(currentPage);
            printAllReadingEntries();

            Snackbar.make(view, "Зміни успішно збережено :)", Snackbar.LENGTH_LONG).show();
        }
    }


    // Put the reading entry to DB
    private void createRealmReadingEntry(int currentPage) {
        mMyRealm.beginTransaction();

        // Create an object
        ReadingEntry readingEntry = mMyRealm.createObject(ReadingEntry.class);

        readingEntry.setCurrentPage(currentPage);
        readingEntry.setDate(new Date());

        mMyRealm.commitTransaction();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Realm getMyRealm() {
        return mMyRealm;
    }

    public BookGoal getBookGoal() {
        return mBookGoal;
    }

}
