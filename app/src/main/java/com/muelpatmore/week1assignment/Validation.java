package com.muelpatmore.week1assignment;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Samuel on 21/11/2017.
 */

public class Validation {

    public static final String REGEX_USERNAME = "^[a-zA-Z0-9_.]{2,12}$";
    public static final String REGEX_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String REGEX_PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}";

    private static final String sTAG = "Validation";
    private static final Boolean sDebug = false;

    /**
     * Validate input String against allowable username RegEx  and return result;
     */
    private boolean validateUsername(String username) {
        if (sDebug) Log.d(sTAG, "validateUsername("+username+")");
        return username.matches(REGEX_USERNAME);
    }

    /**
     * Validate input String against allowable email format RegEx and return result.
     */
    private boolean validateEmail(String email) {
        if (sDebug) Log.d(sTAG, "validateEmail("+email+")");
        Pattern pattern =
                Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return  matcher.find();
    }

    /**
     * Compares two passwords against a RegEx and each other to ensure password they conform to
     * set password standards.
     * @param pass1 First password entered
     * @param pass2 Second password entered
     */
    private boolean validatePasswords(String pass1, String pass2) {
        if (sDebug) Log.d(sTAG, "validatePasswords("+pass1+", "+pass2+")");
        return pass1.matches(REGEX_PASSWORD) && pass1.equals(pass2);
    }
}
