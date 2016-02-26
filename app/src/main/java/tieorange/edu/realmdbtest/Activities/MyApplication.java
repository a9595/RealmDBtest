package tieorange.edu.realmdbtest.Activities;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by tieorange on 26/02/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
