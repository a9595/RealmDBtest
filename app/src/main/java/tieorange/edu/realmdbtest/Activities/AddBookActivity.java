package tieorange.edu.realmdbtest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.NumberPicker;

import io.realm.Realm;
import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.R;

public class AddBookActivity extends AppCompatActivity {

    private Realm mMyRealm;
    private NumberPicker mUiNumberGoal;
    private NumberPicker mUiNumberPagesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mMyRealm = Realm.getInstance(this); // setup Realm

        // Setup pages count
        mUiNumberPagesCount = (NumberPicker) findViewById(R.id.add_number_pages_count);
        mUiNumberPagesCount.setMinValue(0);
        mUiNumberPagesCount.setMaxValue(5000);
        mUiNumberPagesCount.setWrapSelectorWheel(true);
        mUiNumberPagesCount.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                mUiNumberGoal.setMaxValue(newVal);
            }
        });

        // Setup goal
        mUiNumberGoal = (NumberPicker) findViewById(R.id.add_number_goal);
        mUiNumberGoal.setMinValue(0);
        mUiNumberGoal.setMaxValue(100);
        mUiNumberGoal.setWrapSelectorWheel(true);
        mUiNumberGoal.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {

            }
        });


    }

    private void createRealmReadingEntry(int pagesCount, int goal) {
        mMyRealm.beginTransaction();

        BookGoal bookGoal = mMyRealm.createObject(BookGoal.class);

        bookGoal.setPagesCount(pagesCount);
        bookGoal.setGoal(goal);

        mMyRealm.commitTransaction();

    }

    public void SaveOnClick(View view) {
        int pagesCount = mUiNumberPagesCount.getValue();
        int goal = mUiNumberGoal.getValue();

        if (pagesCount > 0 && goal > 0) {
            createRealmReadingEntry(pagesCount, goal);
        }

        Intent intent = new Intent(getApplicationContext(), ChartsActivity.class);
        startActivity(intent);
    }
}
