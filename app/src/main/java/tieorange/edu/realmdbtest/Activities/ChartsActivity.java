package tieorange.edu.realmdbtest.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;
import tieorange.edu.realmdbtest.DataHelpers.POJOHelper;
import tieorange.edu.realmdbtest.DataHelpers.RealmHelper;
import tieorange.edu.realmdbtest.Fragments.AddReadingEntryDialogFragment;
import tieorange.edu.realmdbtest.Fragments.MonthFragment;
import tieorange.edu.realmdbtest.Fragments.YearFragment;
import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;
import tieorange.edu.realmdbtest.R;

public class ChartsActivity extends AppCompatActivity implements AddReadingEntryDialogFragment.AddReadingEntryDialogListener {

    @Bind(R.id.charts_toolbar)
    Toolbar mUiToolbar;
    @Bind(R.id.charts_tab_layout)
    TabLayout mUiTabLayout;
    @Bind(R.id.charts_viewpager)
    ViewPager mUiViewPager;
    View mUiView;

    Realm mMyRealm;
    BookGoal mBookGoal;

    private int[] tabIcons = {
            R.drawable.ic_charts,
            R.drawable.ic_app_icon_clock
    };
    private MonthFragment mMonthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        mUiView = findViewById(android.R.id.content); // get's the root view of activity
        mUiViewPager = (ViewPager) findViewById(R.id.charts_viewpager);
        mUiTabLayout = (TabLayout) findViewById(R.id.charts_tab_layout);

        setupViewPager();
        //setupTabIcons();
        mUiTabLayout.setupWithViewPager(mUiViewPager);

        // Realm:
        mMyRealm = Realm.getInstance(this);
        RealmResults<BookGoal> bookGoalRealmResults = mMyRealm.where(BookGoal.class).findAll();
        if (bookGoalRealmResults.size() > 0) {
            mBookGoal = bookGoalRealmResults.get(0);
            //RealmHelper.printAllReadingEntries(mMyRealm);
        } else {
            Intent intent = new Intent(this, AddBookActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // use can't go back
            startActivity(intent);
            finish();
        }

        // FOR DEBUG
        final List<ReadingEntry> dummyEntriesList = RealmHelper.getReadingEntriesList(mMyRealm);
        final Map<Date, List<ReadingEntry>> groupedReadingEntries = POJOHelper.getGroupedReadingEntries(dummyEntriesList);
        Map<Date, List<ReadingEntry>> treeMap = new TreeMap<Date, List<ReadingEntry>>(groupedReadingEntries);
        final int size = treeMap.size();

        //RealmHelper.removeAllRealmData(mMyRealm);
        RealmHelper.printAllReadingEntries(mMyRealm);
        //RealmHelper.createDummyRealmReadingEntries(mMyRealm);
        Log.d("MY", "size = " + size);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mMonthFragment = MonthFragment.newInstance("", "");
        adapter.addFragment(mMonthFragment, "Month");
        adapter.addFragment(YearFragment.newInstance("", ""), "Year");
        mUiViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_reading_entry) {
            // TODO: show dialog
            if (mBookGoal != null) {
                showEntryDialog();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEntryDialog() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        AddReadingEntryDialogFragment dialogFragment = AddReadingEntryDialogFragment.newInstance(mBookGoal.getPagesCount());
        dialogFragment.show(fragmentManager, "Sample Fragment");
    }

    public BookGoal getBookGoal() {
        return mBookGoal;
    }

    public Realm getRealm() {
        return mMyRealm;
    }

    // User added reading entry from dialog
    @Override
    public void onFinishEntryDialog(int currentPage) {
        String message = "Current page = " + currentPage;
        Snackbar.make(mUiView, message, Snackbar.LENGTH_LONG)
                .setAction("Cancel", null)
                .show();

        // MOCK: every time user add new entry. it simulates a new day
        final List<ReadingEntry> readingEntriesList = RealmHelper.getReadingEntriesList(mMyRealm);
        Date lastEntryDate = new Date();
        try {
            lastEntryDate = RealmHelper.getReadingEntriesRealmResults(mMyRealm).last().getDate();
        } catch (Exception ex) {
        }
        Date lastEntryDateIncremented = POJOHelper.incrementByOneDay(lastEntryDate);

        RealmHelper.createRealmReadingEntry(currentPage, lastEntryDateIncremented, mMyRealm); // create entry with new day (last + 1)
        mMonthFragment.setupLineChart();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
