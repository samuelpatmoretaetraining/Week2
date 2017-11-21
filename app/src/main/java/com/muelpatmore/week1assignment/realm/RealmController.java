/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment.realm;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.muelpatmore.week1assignment.Constants;

import java.security.SecureRandom;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Controller to manage access and operations with Realm database for Week1Assignment project.
 *
 * Created by Samuel on 17/11/2017.
 */

public class RealmController extends AppCompatActivity {

    // Class TAG for log messages
    private final static String TAG = "RealmController";

    private static RealmController instance = null;
    private final Realm mRealm; //

    private RealmController(Realm realm) {
        this.mRealm = realm;
    }



    /**
     * Singleton function to ensure only one instance of the RealmController is accessible
     * @return RealmController object
     */
    public static RealmController getInstance() {
        // double lock to prevent two processes creating a RealmController simultaneously
        synchronized (RealmController.class) {
            // create RealmController instance if none exists
            if (instance == null) {
                synchronized (RealmController.class) {
                    instance = new RealmController(Realm.getDefaultInstance());
                }
            }
        }
        return instance;
    }


    /**
     * Take login details object and check for the presence of it's username (PK) in the Realm
     * Database. If not found add entry to database.
     *
     * @param mRealmLoginDetails Login details to add to database
     * @return Boolean. True if details successfully added to Realm database.
     */
    public boolean saveLoginDetails(final RealmLoginDetails mRealmLoginDetails) {
        if(userExists(mRealmLoginDetails.getUsername())) {
            Log.e(TAG, "Username already exists in the database. Aborting copyAction");
            return false;
        } else {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(mRealmLoginDetails);
                }
            });
            return true;
        }
    }

    /**
     * Retrieve contents of RealmLoginDetails table in the form of an ArrayList.
     */
    public ArrayList<RealmLoginDetails> getUserList() {

        // array list used to conform to recycler view inputs
        ArrayList<RealmLoginDetails> users = new ArrayList<>();


        RealmResults<RealmLoginDetails> realmLoginDetailsRealmResults =
                mRealm.where(RealmLoginDetails.class).findAll();

        // transfer items from realmResults to customers
        for(RealmLoginDetails realmLoginEntry : realmLoginDetailsRealmResults) {
            users.add(realmLoginEntry);
            Log.i("RealController", realmLoginEntry.getUsername());
        }

        return users;
    }

    /**
     * Take string value to compare to Primary Key of login database, returning the entry if found.
     *
     * @param username String value of username (PK) to compare to database.
     */
    public RealmLoginDetails getUser(String username) {
        String myPrimaryKey = "mUsername";
        RealmLoginDetails user = mRealm.where(RealmLoginDetails.class).equalTo(myPrimaryKey, username).findFirst();
        return user;
    }

    public boolean userExists(String username) {
        Log.i(TAG, "userExists("+username+")");
        return (null != getUser(username));
    }

    /**
     * Take RealmCustomer object and store in Realm database. Numerous matching values are
     * permissible.
     *
     * @param mRealmCustomer customer item to save in Realm database
     */
    public void saveCustomer(final RealmCustomer mRealmCustomer) {
        Log.i(TAG, "saveCustomer");
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mRealmCustomer);
            }
        });
    }

    /**
     * Retrieve contents of RealmCustomer table in the form of an ArrayList.
     */
    public ArrayList<RealmCustomer> getCustomerList() {
        ArrayList<RealmCustomer> customers = new ArrayList<RealmCustomer>();




        RealmResults<RealmCustomer> realmCustomerRealmResults =
                mRealm.where(RealmCustomer.class).findAll();

        // transfer items from realmResults to customers
        for(RealmCustomer realmCustomer : realmCustomerRealmResults) {
            customers.add(realmCustomer);
            Log.i("RealController", realmCustomer.getForename().toString());
        }

        return customers;

    }
}
