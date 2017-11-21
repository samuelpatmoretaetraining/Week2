/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.muelpatmore.week1assignment.realm.RealmController;
import com.muelpatmore.week1assignment.realm.RealmLoginDetails;


/**
 * Created by Samuel on 21/11/2017.
 */

public class LoginViewModel extends Fragment{

    private final static String TAG = "LoginViewModel";

    private static LoginViewModel instance;
    private ButtonClicked mButtonClicked;

    private EditText etUsername, etPassword;
    //private Button btnLogin;
    private CheckBox cbRememberMe;

    public static LoginViewModel getInstance()  {
        // double lock to prevent two processes creating a SplashViewModel simultaneously
        synchronized (LoginViewModel.class) {
            // create RealmController instance if none exists
            if (instance == null) {
                synchronized (LoginViewModel.class) {
                    instance = new LoginViewModel();
                }
            }
        }
        return instance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get parent context for handling click events.
        mButtonClicked = (ButtonClicked) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        //btnLogin = findViewById(R.id.btnLogin);
        cbRememberMe = view.findViewById(R.id.cbRememberMe);
    }
    
    private void loginAttempted() {
        Toast.makeText(getActivity(),"Login submitted",Toast.LENGTH_SHORT).show();
    }
}