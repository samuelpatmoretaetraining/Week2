package com.muelpatmore.week1assignment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muelpatmore.week1assignment.ButtonClicked;
import com.muelpatmore.week1assignment.R;

/**
 * Created by Samuel on 21/11/2017.
 */

public class SplashViewModel extends Fragment {


    private static final String TAG = "SplashViewModel";
    private static SplashViewModel instance = null;
    private ButtonClicked buttonClicked;

    private Button btnLogin, btnRegister;

    public static SplashViewModel getInstance()  {
        // double lock to prevent two processes creating a SplashViewModel simultaneously
        synchronized (SplashViewModel.class) {
            // create RealmController instance if none exists
            if (instance == null) {
                synchronized (SplashViewModel.class) {
                    instance = new SplashViewModel();
                }
            }
        }
        return instance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get parent context for handling click events.
        buttonClicked = (ButtonClicked) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_screen, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        //ToDo Check with User Preferences if the user is logged in, if so bypass LoginActivity to  CustomerView

        btnLogin = view.findViewById(R.id.btnSplashLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked.loginClicked(v);
            }
        });
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked.registerLogin(v);
            }
        });
    }
}
