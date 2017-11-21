/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment.realm;

import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * RealmObject class to store customer information in a Realm database.
 *
 * Created by Samuel on 17/11/2017.
 */

public class RealmLoginDetails extends RealmObject {

    private static final String TAG = "RealmLoginDetails";

    @PrimaryKey
    @Required
    private String mUsername;

    @Required
    private String mPassword;

    @Required
    private String mEmail;

    public RealmLoginDetails() {

    }

    public RealmLoginDetails(String username, String password, String email) {

        this.mUsername = username;
        this.mPassword = password;
        this.mEmail = email;
        Log.v(TAG, "entry for "+username+"created");
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }
}
