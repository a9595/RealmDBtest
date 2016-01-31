package tieorange.edu.realmdbtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm mMyRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Entry has been created", Snackbar.LENGTH_SHORT)
                        .setAction("Create", null).show();

            }
        });


        mMyRealm = Realm.getInstance(this);
        createRealmReadingEntry();

        // Writing Queries
        RealmResults<ReadingEntry> results =
                mMyRealm.where(ReadingEntry.class).findAll();
        for (ReadingEntry entry : results) {
            Log.d("MY", String.valueOf(entry.getCurrentPage()));
        }

        // HOW TO CREATE YOUR OWN REALM FILE?
//        Realm myOtherRealm = Realm.getInstance(
//                new RealmConfiguration.Builder(this)
//                        .name("myOtherRealm.realm")
//                        .build()
//        );
    }

    private void createRealmReadingEntry() {
        mMyRealm.beginTransaction();

        // Create an object
        ReadingEntry readingEntry = mMyRealm.createObject(ReadingEntry.class);

        readingEntry.setCurrentPage(64);
        readingEntry.setDate(new Date());

        mMyRealm.commitTransaction();

//
//        // or if we want to use a constructor use realm.CopyToRealm(readingEntry)
//        ReadingEntry readingEntry2 = new ReadingEntry();
//        readingEntry2.setDate(new Date());
//        readingEntry2.setCurrentPage(78);
//
//        mMyRealm.beginTransaction();
//        ReadingEntry copyOfReadingEntry = mMyRealm.copyToRealm(readingEntry2);
//        mMyRealm.commitTransaction();
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
}
