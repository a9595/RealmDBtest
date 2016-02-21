package tieorange.edu.realmdbtest.Activities;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;
import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.R;

public class ChartsActivity extends AppCompatActivity {

    @Bind(R.id.charts_toolbar)
    Toolbar mUiToolbar;
    @Bind(R.id.charts_tab_layout)
    TabLayout mUiTabLayout;
    @Bind(R.id.charts_viewpager)
    ViewPager mUiViewPager;

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
        }
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
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_add_reading_entry){
            // TODO: show dialog
        }

        return super.onOptionsItemSelected(item);
    }

    public BookGoal getBookGoal() {
        return mBookGoal;
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
