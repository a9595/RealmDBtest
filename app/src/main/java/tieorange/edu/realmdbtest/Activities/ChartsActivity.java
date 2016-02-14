package tieorange.edu.realmdbtest.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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
    TabLayout muiTabLayout;
    @Bind(R.id.charts_viewpager)
    ViewPager muiViewPager;

    Realm mMyRealm;
    BookGoal mBookGoal;

    public BookGoal getBookGoal() {
        return mBookGoal;
    }

    private int[] tabIcons = {
            R.drawable.ic_charts,
            R.drawable.ic_app_icon_clock
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        muiViewPager = (ViewPager) findViewById(R.id.charts_viewpager);
        muiTabLayout = (TabLayout) findViewById(R.id.charts_tab_layout);

        setupViewPager(muiViewPager);
        //setupTabIcons();
        muiTabLayout.setupWithViewPager(muiViewPager);


        // Realm:
        mMyRealm = Realm.getInstance(this);

        RealmResults<BookGoal> bookGoalRealmResults = mMyRealm.where(BookGoal.class).findAll();

        if (bookGoalRealmResults.size() > 0) {
            mBookGoal = bookGoalRealmResults.get(0);
        }
    }

    private void setupTabIcons() {
        muiTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        muiTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonthFragment(), "Month");
        adapter.addFragment(YearFragment.newInstance("", ""), "Year");
        muiViewPager.setAdapter(adapter);
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
