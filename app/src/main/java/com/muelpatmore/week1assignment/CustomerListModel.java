package com.muelpatmore.week1assignment;

import com.muelpatmore.week1assignment.realm.RealmController;
import com.muelpatmore.week1assignment.realm.RealmCustomer;

import java.util.ArrayList;

/**
 * Created by Samuel on 21/11/2017.
 */

public class CustomerListModel {

    private RealmController mRealmController;

    public CustomerListModel() {
        // retrieve RealmController
        mRealmController = RealmController.getInstance();
    }

    public ArrayList<RealmCustomer> getCustomerData() {
        return mRealmController.getCustomerList();
    }
}
