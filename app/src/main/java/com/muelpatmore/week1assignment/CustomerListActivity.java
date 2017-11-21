/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.muelpatmore.week1assignment.realm.RealmController;

/**
 * Class to manage the Customer List Activity. Functions covering click handling, value
 * verification and adding new customers to a Realm database.
 */
public class CustomerListActivity extends AppCompatActivity {

    private static final String sTAG = "CustomerListActivity";
    private RealmController mRealmController;
    private RecyclerView mRVCustomer;
    private CustomerAdapter mCustomerAdapter;
    private String mUsername = null;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain access to user preferences
        mSettings = getSharedPreferences(Constants.PREFS_NAME, 0);

        // Send user a welcome Toast with their username from the login or prefs
        greetUser(savedInstanceState);

        Log.i(sTAG, "setContentView");
        setContentView(R.layout.activity_customer_list);

        // retrieve RealmController
        mRealmController = RealmController.getInstance();

        // bind xml Views to Java objects
        mRVCustomer = findViewById(R.id.rvCustomer);

        // initiate RecyclerView in CustomerListActivity
        mRVCustomer = findViewById(R.id.rvCustomer);
        mCustomerAdapter = new CustomerAdapter(mRealmController.getCustomerList(), 0, getApplicationContext());

        // LayoutManager to arange cards in a vertical scroll.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRVCustomer.setLayoutManager(llm);

        mRVCustomer.setAdapter(mCustomerAdapter);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to do this "+mUsername)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Retrieves username from Login Page or user preferences (depending upon how page was reached,
     * greet the user with a personalised toast.
     */
    private void greetUser(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            String welcomeMessage;
            if(extras != null) {
                mUsername = extras.getString(Constants.USERNAME);
                welcomeMessage = "Welcome "+mUsername;
            } else {
                mUsername = mSettings.getString(Constants.PREFS_USERNAME, null);
                welcomeMessage = "Welcome back "+mUsername;
            }
            Toast.makeText(getApplicationContext(), welcomeMessage, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Multi
     * @param v View in Activity which called the click handler.
     */
    public void clickHandler(View v) {
        switch (v.getId()) {
            case R.id.btnAddCustomer :
                Log.i(sTAG, "Add customer button clicked");
                
                Intent intent = new Intent(CustomerListActivity.this, CreateCustomerActivity.class);
                startActivity(intent);
                break;

            case R.id.btnLogout :
                Log.i(sTAG, "Add customer button clicked");
                logoutButtonClicked();
                break;

        }
    }

    private void logoutButtonClicked() {
        // Remove mUsername and login status in user preferences
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.PREFS_LOGGED_IN, false);

        // Commit the edits!
        editor.apply();

        Intent logoutIntent = new Intent(CustomerListActivity.this, MainActivity.class);
        logoutIntent.putExtra(Constants.USERNAME, mUsername);
        startActivity(logoutIntent);
        finish();
    }
}
