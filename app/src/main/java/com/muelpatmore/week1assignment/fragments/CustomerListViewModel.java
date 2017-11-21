package com.muelpatmore.week1assignment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muelpatmore.week1assignment.ButtonClicked;
import com.muelpatmore.week1assignment.CustomerAdapter;
import com.muelpatmore.week1assignment.R;
import com.muelpatmore.week1assignment.realm.RealmController;
import com.muelpatmore.week1assignment.realm.RealmCustomer;

import java.util.ArrayList;

/**
 * Created by Samuel on 21/11/2017.
 */

public class CustomerListViewModel extends Fragment {

    private static CustomerListViewModel mInstance;

    private CustomerListModel mCustomerListModel;

    private ButtonClicked mButtonClicked;
    private RecyclerView mRecyclerView;

    private CustomerAdapter mCustomerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomerAdapter mAdapter;
    private String mUsername = null;

    private Button btnAddCustomer, btnLogout;

    public static CustomerListViewModel getInstance()  {
        // double lock to prevent two processes creating a SplashViewModel simultaneously
        synchronized (CustomerListViewModel.class) {
            // create RealmController instance if none exists
            if (mInstance == null) {
                synchronized (CustomerListViewModel.class) {
                    mInstance = new CustomerListViewModel();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get parent context for handling click events.
        mButtonClicked = (ButtonClicked) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_customer_list, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        btnAddCustomer = (Button) view.findViewById(R.id.btnAddCustomer);
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonClicked.addCustomer(v);
            }
        });

        btnLogout = (Button) view.findViewById(R.id.btnLogout);


        mCustomerListModel = new CustomerListModel();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCustomer);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new CustomerAdapter(mCustomerListModel.getCustomerData(), 0, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Created by Samuel on 21/11/2017.
     */

    public static class CustomerListModel {

        private RealmController mRealmController;

        public CustomerListModel() {
            // retrieve RealmController
            mRealmController = RealmController.getInstance();
        }

        public ArrayList<RealmCustomer> getCustomerData() {
            return mRealmController.getCustomerList();
        }
    }
}
