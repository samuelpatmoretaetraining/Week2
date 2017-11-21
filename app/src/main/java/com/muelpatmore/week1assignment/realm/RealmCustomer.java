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
import io.realm.annotations.Required;

/**
 * RealmObject class to store customer information in a Realm database.
 *
 * Created by Samuel on 17/11/2017.
 */

public class RealmCustomer extends RealmObject {

    private static final String sTAG = "RealObject";

    @Required
    private String mForename;
    @Required
    private String mSurname;
    private String mAaddress;
    private String mNationality;
    private String mGender;
    private String mDob;
    private byte[] mImage;

    public RealmCustomer() {
    }

    public RealmCustomer(String forename, String surname, String address, String mNationality, String gender, String dob, byte[] image) {
        Log.i(sTAG, "constructor");
        this.mForename = forename;
        this.mSurname = surname;
        this.mAaddress = address;
        this.mNationality = mNationality;
        this.mGender = gender;
        this.mDob = dob;
        this.mImage = image;
    }

    public String getForename() {
        return mForename;
    }

    public void setForename(String mForename) {
        this.mForename = mForename;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public String getAaddress() {
        return mAaddress;
    }

    public void setAaddress(String mAaddress) {
        this.mAaddress = mAaddress;
    }

    public String getNationality() {
        return mNationality;
    }

    public void setmNationality(String mNationality) {
        this.mNationality = mNationality;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        this.mGender = gender;
    }

    public String getDob() {
        return mDob;
    }

    public void setDob(String dob) {
        this.mDob = dob;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        this.mImage = image;
    }
}
