package com.muelpatmore.week1assignment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.muelpatmore.week1assignment.ButtonClicked;
import com.muelpatmore.week1assignment.R;
import com.muelpatmore.week1assignment.realm.RealmLoginDetails;

/**
 * Created by Samuel on 21/11/2017.
 */

public class RegisterLoginViewModel extends Fragment {

    private static final String TAG = "CreateAccountActivity";
    private static final Boolean DEBUG = true;
    private static RegisterLoginViewModel mInstance;

    private ButtonClicked mButtonClicked;
    private EditText etUsername, etEmail, etPassword1, etPassword2;
    private Button btnLogin;
    private ImageView ivBack;

    public static RegisterLoginViewModel getInstance()  {
        // double lock to prevent two processes creating a SplashViewModel simultaneously
        synchronized (RegisterLoginViewModel.class) {
            // create RealmController instance if none exists
            if (mInstance == null) {
                synchronized (LoginViewModel.class) {
                    mInstance = new RegisterLoginViewModel();
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
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword1 = view.findViewById(R.id.etPassword1);
        etPassword2 = view.findViewById(R.id.etPassword2);

        btnLogin = view.findViewById(R.id.btnRegisterLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLogin(v);
            }
        });
    }
    private void registerLogin(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword1.getText().toString();
        String email = etEmail.getText().toString();

        //ToDo validate fields before allowing app to progress.

        RealmLoginDetails details = new RealmLoginDetails(username, password, email);
        mButtonClicked.loginRegistered(v, details);
    }

}
