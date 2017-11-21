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
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.muelpatmore.week1assignment.realm.RealmController;

public class MainActivity extends AppCompatActivity implements ButtonClicked {

    private static final String TAG = "MainActivity";

    private RealmController realmController;
    private FrameLayout fragmentFrame;
    private FragmentManager fragmentManager;

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

        setContentView(R.layout.activity_main);
        fragmentFrame = (FrameLayout) findViewById(R.id.fragmentFrame);
        fragmentManager = getSupportFragmentManager();


        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentFrame, SplashViewModel.getSplashView())
                    .commit();
        }

    }

    public void loginClicked(View v) {
        if (v.getId() == R.id.btnLogin) {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            //fragmentManager.beginTransaction().add(R.id.fragmentFrame, new BlankFragment()).commit();

        }
    }
}
