package com.muelpatmore.week1assignment;

import android.view.View;

import com.muelpatmore.week1assignment.realm.RealmLoginDetails;

/**
 * Created by Samuel on 21/11/2017.
 */

public interface ButtonClicked {
    void loginClicked(View v);
    void loginSubmitted(View v);
    void registerLogin(View v);
    void loginRegistered(View v, RealmLoginDetails details);
    void addCustomer(View v);
}
