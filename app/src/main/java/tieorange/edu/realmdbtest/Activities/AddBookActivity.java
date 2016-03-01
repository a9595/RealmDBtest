package tieorange.edu.realmdbtest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.NumberPicker;

import io.realm.Realm;
import tieorange.edu.realmdbtest.DataHelpers.RealmHelper;
import tieorange.edu.realmdbtest.R;

public class AddBookActivity extends AppCompatActivity {

    private Realm mRealm;

    private NumberPicker mUiNumberGoal;
    private NumberPicker mUiNumberPagesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mRealm = Realm.getDefaultInstance();

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



    public void SaveOnClick(View view) {
        int pagesCount = mUiNumberPagesCount.getValue();
        int goal = mUiNumberGoal.getValue();

        if (pagesCount > 0 && goal > 0) {
            RealmHelper.createRealmBookGoal(pagesCount, goal, mRealm);
        }

        Intent intent = new Intent(getApplicationContext(), ChartsActivity.class);
        startActivity(intent);
    }
}
