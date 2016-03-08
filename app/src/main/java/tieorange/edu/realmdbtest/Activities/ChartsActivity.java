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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;
import tieorange.edu.realmdbtest.Helpers.POJOHelper;
import tieorange.edu.realmdbtest.Helpers.RealmHelper;
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
    MonthFragment mMonthFragment;

    Realm mRealm;
    BookGoal mBookGoal;
    private YearFragment mYearFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        mUiView = findViewById(android.R.id.content); // get's the root view of activity
        mUiViewPager = (ViewPager) findViewById(R.id.charts_viewpager);
        mUiTabLayout = (TabLayout) findViewById(R.id.charts_tab_layout);

        setupViewPager();
        mUiTabLayout.setupWithViewPager(mUiViewPager);

        // setup Realm:
        mRealm = Realm.getDefaultInstance();
        RealmResults<BookGoal> bookGoalRealmResults = mRealm.where(BookGoal.class).findAll();
        if (bookGoalRealmResults.size() > 0) {
            mBookGoal = bookGoalRealmResults.get(0);
            //RealmHelper.printAllReadingEntries(mRealm);
        } else {
            Intent intent = new Intent(this, AddBookActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // use can't go back
            startActivity(intent);
            finish();
        }

        // FOR DEBUG
        final List<ReadingEntry> dummyEntriesList = RealmHelper.getReadingEntriesList(mRealm);
        final Map<Date, List<ReadingEntry>> groupedReadingEntries = POJOHelper.getGroupedReadingEntries(dummyEntriesList);
        Map<Date, List<ReadingEntry>> treeMap = new TreeMap<Date, List<ReadingEntry>>(groupedReadingEntries);
        final int size = treeMap.size();

        //RealmHelper.removeAllRealmData(mRealm);
//        RealmHelper.createDummyRealmReadingEntries(mRealm);
        RealmHelper.printAllReadingEntries(mRealm);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mMonthFragment = MonthFragment.newInstance();
        mYearFragment = YearFragment.newInstance();

        adapter.addFragment(mMonthFragment, getString(R.string.line_chart));
        adapter.addFragment(mYearFragment, getString(R.string.bar_chart));
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
        return mRealm;
    }

    // User added reading entry from dialog
    @Override
    public void onFinishEntryDialog(int currentPage, Date date) {
        String message = "Current page = " + currentPage;
        Snackbar.make(mUiView, message, Snackbar.LENGTH_LONG)
                .setAction("Cancel", null)
                .show();

        // MOCK: every time user add new entry. it simulates a new day
//        final List<ReadingEntry> readingEntriesList = RealmHelper.getReadingEntriesList(mRealm);
//        Date lastEntryDate = new Date();
//        try {
//            lastEntryDate = RealmHelper.getReadingEntriesRealmResults(mRealm).last().getDate();
//        } catch (Exception ex) {
//        }
//        Date lastEntryDateIncremented = POJOHelper.incrementByOneDay(lastEntryDate);
        // /MOCK

        // Real situation:
        Date dateWithoutTime = POJOHelper.removeTime(date); // create today's date without time (to group by date)

        RealmHelper.createRealmBookGoal(currentPage, dateWithoutTime, mRealm); // create entry with new day (last + 1)

        // invalidate charts
        mMonthFragment.setupLineChart();
        mYearFragment.setupBarChart();

        RealmHelper.printAllReadingEntries(mRealm);

        final TreeMap<Date, List<ReadingEntry>> groupedReadingEntriesMap = RealmHelper.getGroupedReadingEntriesMap(mRealm);
        groupedReadingEntriesMap.size();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
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
