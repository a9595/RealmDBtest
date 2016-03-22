package tieorange.edu.realmdbtest.Helpers;

import android.app.Application;

//import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tieorange on 26/02/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        JodaTimeAndroid.init(this);

        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
