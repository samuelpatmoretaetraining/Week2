/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Samuel on 17/11/2017.
 */

public class MyApp extends Application {

    public static final String DB_NAME = "SECURE_REALM";

    private static final String sTAG = "MyApp";


    private SharedPreferences mSettings;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(sTAG, "onCreate");

        Realm.init(getApplicationContext());


        mSettings = getSharedPreferences(Constants.PREFS_NAME, 0);
        byte[] key;
        String rawKey = mSettings.getString(Constants.PREF_ENCRYPTION_KEY, null);

        if (rawKey == null) {
            // create new encryption key
            byte[] newkey = new byte[64];
            new SecureRandom().nextBytes(newkey);

            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(Constants.PREF_ENCRYPTION_KEY,
                    Base64.encodeToString(newkey, Base64.DEFAULT));
            editor.apply();
            key = newkey;
        } else {
            key = Base64.decode(rawKey, Base64.DEFAULT);
        }
        Log.i(sTAG, "KEY: "+key+", KEY LENGTH:"+key.length);

        // secure
        RealmConfiguration configuration= new RealmConfiguration.Builder()
                .name(DB_NAME) // can be set to any string
                .schemaVersion(1) //update number
                .deleteRealmIfMigrationNeeded() // rebuild DB on version number change to prevent crash
                .encryptionKey(key)
                .build();


//        RealmConfiguration configuration= new RealmConfiguration.Builder()
//                .name(Realm.DEFAULT_REALM_NAME) // can be set to any string
//                .schemaVersion(1) //update number
//                .deleteRealmIfMigrationNeeded() // rebuild DB on version number change to prevent crash
//                .build();

        Realm.setDefaultConfiguration(configuration);
    }


}
