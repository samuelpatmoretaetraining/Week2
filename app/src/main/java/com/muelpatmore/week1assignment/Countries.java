/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */
package com.muelpatmore.week1assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Class to manage access to Locale libraries list of countries.
 *
 * Created by Samuel on 16/11/2017.
 */

public class Countries {

    public static ArrayList<String> GET_COUNTRY_LIST() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        return countries;
    }
}