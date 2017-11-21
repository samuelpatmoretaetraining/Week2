/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.muelpatmore.week1assignment.realm.RealmController;
import com.muelpatmore.week1assignment.realm.RealmLoginDetails;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to manage the Create Account Activity. Functions covering click handling, value
 * verification and adding new users to a Realm database.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "CreateAccountActivity";
    private static final Boolean DEBUG = true;

    private RealmController realmController;
    private EditText etUsername, etEmail, etPassword1, etPassword2;
    private Button btnLogin;
    private ImageView ivBack;

    /**
     * Actions to be taken in upon activity creation. Set content view to activity_create_account,
     * bind logic Views to java objects and initialize access to the Realm database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate()");

        setContentView(R.layout.activity_create_account);

        // bind views to Java objects
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword1 = findViewById(R.id.etPassword1);
        etPassword2 = findViewById(R.id.etPassword2);
        btnLogin = findViewById(R.id.btnSplashLogin);
        ivBack = findViewById(R.id.ivBack);

        realmController = RealmController.getInstance();

    }

    /**
     * Click handler for all Buttons in content view, passing to appropriate funtion to handle
     * response.
     *
     * @param v View object which called this function in response to a user click.
     */
    public void clickHandler(View v) {
        switch (v.getId()) {
            case R.id.btnSplashLogin:
                loginButtonClicked();
                break;

            case R.id.ivBack :
                backButtonClicker();
                break;
        }
    }

    /**
     * Actions to take when login button clicked. input values are verified and if correct the
     * values are passed to be added to the login details Realm database.
     */
    private void loginButtonClicked() {
        Log.i(TAG, "loginButtonClicked");
        String username = etUsername.getText().toString();
        if(DEBUG) {Log.v(TAG, "USERNAME: + "+username);}

        String email = etEmail.getText().toString();
        if(DEBUG) {Log.v(TAG, "EMAIL: " + email);}

        String password1 = etPassword1.getText().toString();
        if(DEBUG) {Log.v(TAG, "PASS1: " + password1);}

        String password2 = etPassword2.getText().toString();
        if(DEBUG) {Log.v(TAG, "PASS2: " + password2);}

        if (validateUsername(username) &&
                validateEmail(email) &&
                validatePasswords(password1, password2)) {

            RealmLoginDetails loginDetais = new RealmLoginDetails(username, password1, email);
            Toast.makeText(getApplicationContext(), "User added to database.", Toast.LENGTH_LONG).show();
            Intent toLoginScreen = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(toLoginScreen);

            realmController.saveLoginDetails(loginDetais);
        } else {
            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Function to handle click on back button. Returns user to SplashScreenActivity.
     */
    private void backButtonClicker() {
        Log.i(TAG, "backButtonClicked");
        Intent returnToSplashScreen = new Intent(CreateAccountActivity.this, MainActivity.class);
        startActivity(returnToSplashScreen);
    }


    /**
     * Validate username against format RegEx String and ensure uniqueness in Realm database.
     * @param username String to validate
     * @return Boolean: true if username String is valid and not presenct in database.
     */
    private boolean validateUsername(String username) {
        String regEx  = "^[a-zA-Z0-9_.]{2,12}$";
        boolean validUsername = username.matches(regEx);
        Log.i(TAG, "VALID USERNAME: "+validUsername);

        if(realmController.userExists(username)) validUsername = false;
        Log.i(TAG, "UNIQUE USERNAME: "+validUsername);

        return validUsername;
    }

    /**
     * Validate emailaddress against format RegEx String.
     * @param eMail String to validate
     * @return Boolean: true if email String is valid.
     */
    private boolean validateEmail(String eMail) {
        /**
         * Regex explanation
         * [A-Z0-9._%+-] substring of letters, numbers and valid special characters for user
         * @[A-Z0-9._-] substring starting with @ followed by letters, numbers and valid special characters for domain
         * .[A-Z] substring starting with . of letters with a length in range 2-6
         */
        Pattern pattern =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(eMail);
        Boolean result = matcher.find();
        Log.i(TAG, "VALID EMAIL: " + result);
        return result;
    }

    /**
     * Compares two passwords against a RegEx and each other to ensure password is valis and has
     * been repeated accurately.
     *
     * @param pass1 First password entered
     * @param pass2 Second password entered
     * @return Boolean, true if both passwords match are and valid.
     */
    private boolean validatePasswords(String pass1, String pass2) {
        // Compare first password against RegEx
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}";
        Boolean correctFormat = pass1.matches(pattern);

        // Compare two passwords
        Boolean passwordMatch = pass1.equals(pass2);

        return correctFormat && passwordMatch;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Realm.close();
    }
}
