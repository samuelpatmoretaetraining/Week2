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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.muelpatmore.week1assignment.realm.RealmController;
import com.muelpatmore.week1assignment.realm.RealmLoginDetails;


/**
 * Created by Samuel on 21/11/2017.
 */

public class LoginViewModel extends Fragment {

    private final static String TAG = "LoginActivity";
    private SharedPreferences settings;
    private RealmController realmController;

    private EditText etUsername, etPassword;
    //private Button btnLogin;
    private CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRealm();

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        //btnLogin = findViewById(R.id.btnLogin);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        // Obtain access to User Preferences
        settings = getSharedPreferences(Constants.PREFS_NAME, 0);

        if(settings.getBoolean(Constants.PREFS_LOGIN_SAVED, false)) {
            String username = settings.getString(Constants.PREFS_USERNAME, null);
            String password = settings.getString(Constants.PREFS_PASSWORD, null);
            if(username != null && password != null) {
                etUsername.setText(username);
                etPassword.setText(password);
            }
        }
    }


    public void initRealm() {
        realmController = RealmController.getInstance();
    }

    public void clickHandler(View v) {
        switch (v.getId()) {
            case R.id.btnLogin :
                Log.i(TAG, "Login button clicked");

                String username = etUsername.getText().toString();
                Log.i(TAG, "ENTERED USERNAME: "+username);

                String password = etPassword.getText().toString();
                Log.i(TAG, "ENTERED PASSWORD: "+password);

                RealmLoginDetails account = realmController.getUser(username);


                if (account != null) {
                    Log.i(TAG, account.toString());

                    String accountUsername = account.getUsername();
                    Log.i(TAG, "ACCOUNT USERNAME: "+accountUsername);
                    String accountPassword = account.getPassword();
                    Log.i(TAG, "ACCOUNT PASSWORD: "+accountPassword);

                    if(account.getUsername().equals(username)) {
                        // On successful login move user to CustomerListActivity with username attached to Intent
                        Log.i(TAG, "Login sucsessful");

                        // Set username and login status in user preferences
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean(Constants.PREFS_LOGGED_IN, true);
                        editor.putString(Constants.PREFS_USERNAME, username);

                        // Store password in user preferences for next login
                        if (cbRememberMe.isEnabled()) {
                            editor.putBoolean(Constants.PREFS_LOGIN_SAVED, true);
                            editor.putString(Constants.PREFS_PASSWORD, password);
                        }

                        // Commit the edits!
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, CustomerListActivity.class);
                        intent.putExtra(Constants.USERNAME, username);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.i(TAG, "Login unsucsessful - wrong password");
                        Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i(TAG, "Login unsucsessful - no such user");
                    //Toast.makeText(this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                }

        }
    }

}