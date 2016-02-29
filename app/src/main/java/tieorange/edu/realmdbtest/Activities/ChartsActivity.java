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
            RealmHelper.printAllReadingEntries(mMyRealm);
        } else {
            Intent intent = new Intent(this, AddBookActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // use can't go back
            startActivity(intent);
            finish();
        }

        final List<ReadingEntry> dummyEntriesList = POJOHelper.getDummyEntriesList();
        final Map<Date, List<ReadingEntry>> groupedReadingEntries = POJOHelper.getGroupedReadingEntries(dummyEntriesList);

        Map<Date, List<ReadingEntry>> treeMap = new TreeMap<Date, List<ReadingEntry>>(groupedReadingEntries);
        final int size = treeMap.size();
    }

    private void setupTabIcons() {
        mUiTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mUiTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonthFragment(), "Month");
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

    // User added reading entry from dialog
    @Override
    public void onFinishEntryDialog(int currentPage) {
        String message = "Current page = " + currentPage;
        Snackbar.make(mUiView, message, Snackbar.LENGTH_LONG)
                .setAction("Cancel", null)
                .show();

        RealmHelper.createRealmReadingEntry(currentPage, mMyRealm, new Date());
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
