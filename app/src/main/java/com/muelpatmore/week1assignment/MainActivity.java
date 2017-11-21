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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.muelpatmore.week1assignment.realm.RealmController;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RealmController realmController;
    private Button btnSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        realmController = RealmController.getInstance();

        // Check user preferences and skip splash & and login screens if the user is logged in.
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        if (settings.getBoolean(Constants.PREFS_LOGGED_IN, false)) {
            Intent customerViewIntent = new Intent(MainActivity.this, CustomerListActivity.class);
            startActivity(customerViewIntent);
            finish();
        }

        setContentView(R.layout.splash_screen);

        btnSplash = findViewById(R.id.btnSplash);


    }

    public void clickHandler(View v) {
        switch (v.getId()) {
            case  R.id.btnSplash :
//
                Log.i(TAG, "Creat account button clicked");
                Intent intentCreate = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intentCreate);
                break;

            case  R.id.btnLogin :
//
                Log.i(TAG, "Login button clicked");
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;
        }
    }
}
