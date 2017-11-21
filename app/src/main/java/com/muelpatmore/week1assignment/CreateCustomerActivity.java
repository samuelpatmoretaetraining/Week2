/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.muelpatmore.week1assignment.realm.RealmController;
import com.muelpatmore.week1assignment.realm.RealmCustomer;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

/**
 * Class to manage the Create Customer Activity. Functions covering click handling, value
 * verification and adding new customers to a Realm database.
 */
public class CreateCustomerActivity extends AppCompatActivity implements DatePickerDialogFragment.DatePickerDialogHandler {

    private static final String sTAG = "CreateCustomerActivity";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String REGEX_ADDRESS = "^[a-zA-Z0-9 .-]{2,}$"; // string of minimul length 2, containing letters numbers dots dashes

    private RealmController realmController;

    private EditText etForename, etSurname, etDOB, etAddress;
    private Button btnPhoto, btnDate, btnSubmit;
    private Spinner spNationality;
    private RadioGroup rgGender;
    private ImageView ivPhoto;
    private Bitmap photo;
    private byte[] rawPhoto;

    private String forename, surname, dob, address, gender, nationality;
    //private enum genders{"Male", "Female", "Ocelot", "Not an Ocelot"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(sTAG, "onCreate()");

        setContentView(R.layout.activity_create_customer);

        // bind views to java objects
        bindViews();

        if(savedInstanceState != null) {
            Bitmap bitmap = savedInstanceState.getParcelable("image");
            ivPhoto.setImageBitmap(bitmap);
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,Countries.GET_COUNTRY_LIST());
        spNationality.setAdapter(adapter);

        spNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> av, View v,
                                       int position, long itemId) {
                nationality = av.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> av) {
                // TODO Auto-generated method stub

            }
        });



        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder dpb = new DatePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment);
                Log.i(sTAG, "Date picker launched");
                dpb.show();
        }   });

        realmController = RealmController.getInstance();

    }

    /**
     * Setup function to attach Java View objects to views in attached Activity's XML file
     */
    private void bindViews() {
        // text Views
        etForename = findViewById(R.id.etForname);
        etSurname = findViewById(R.id.etSurname);
        etDOB = findViewById(R.id.etDOB);
        etAddress = findViewById(R.id.etAddress);
        // buttons
        btnPhoto = findViewById(R.id.btnPhoto);
        btnDate = findViewById(R.id.btnDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        // spinners
        spNationality = findViewById(R.id.spNationality);
        // radio group
        rgGender = findViewById(R.id.rgGender);
        // images
        ivPhoto = findViewById(R.id.ivPhoto);
    }

    /**
     * Create instance of RealmController using singleton method.
     */
    private void initRealm() {
        realmController = RealmController.getInstance();
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        Log.i(sTAG, "Date picker value posted");
        etDOB.setText(getString(R.string.date_picker_result_value, year, monthOfYear, dayOfMonth));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(ivPhoto != null) {
            BitmapDrawable drawable = (BitmapDrawable) ivPhoto.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            outState.putParcelable("image", bitmap);
            super.onSaveInstanceState(outState);
        }
    }



    public void clickHandler(View v) {
        switch (v.getId()) {

            case R.id.btnPhoto :
                // implicit intent to the user's chosen camera/photo app
                // Intent intent = new Intent(Intent.ACTION_CAMERA_BUTTON, );
                // startActivity(intent);
                Log.i(sTAG, "btnPhoto clicked");
                dispatchTakePictureIntent();
                break;

            case R.id.btnDate :
                Log.i(sTAG, "btnDate clicked");
                // open better pickers popup to choose a date

                break;

            case R.id.btnSubmit :
                Log.i(sTAG, "btnSubmit");
                // test submission of values and proceed
                if (readFields()) {
                    submitData();
                    Toast.makeText(this, "Customer saved", Toast.LENGTH_SHORT).show();
                    Intent intentCustomerList = new Intent(CreateCustomerActivity.this, CustomerListActivity.class);
                    startActivity(intentCustomerList);
                }
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Photo Capture Failed", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            ivPhoto.setImageBitmap(photo);
        }
    }

    private boolean readFields() {
        // read in field values and validate
        Log.v(sTAG, "readFields()");

        Boolean valid = true;

        if (!validateName()) {
            valid = false;
            Log.i(sTAG, "NAME INVALID: "+forename+" "+surname);
        }
        if (!validateDate()) {
            valid = false;
            Log.i(sTAG, "DOB INVALID: "+dob);
        }
        if (!validateAddress()) {
            valid = false;
            Log.i(sTAG, "ADDRESS INVALID: "+address);
        }
        if (!validateGender()) {
            valid = false;
            Log.i(sTAG, "GENDER INVALID: "+gender);
        }
        if (!validatePhoto()) {
            valid = false;
            Log.i(sTAG, "PHOTO INVALID");
        }

        return valid;
    }

    private boolean validateName() {
        // read in forename and surname from EditText views and
        Log.v(sTAG, "validateName");

        forename = etForename.getText().toString().trim();
        surname = etSurname.getText().toString().trim();

        return !( forename.isEmpty() || surname.isEmpty() );
    }

    private boolean validateDate() {
        // read in date value and validate
        Log.v(sTAG, "validateDate()");

        dob = etDOB.getText().toString();

        return true;
    }

    private boolean validateAddress() {
        // read in address and validate
        Log.v(sTAG, "validateAddress()");

        address = etAddress.getText().toString().trim();

        return address.matches(REGEX_ADDRESS);
    }
    private boolean validateGender() {
        // read in gender value from radio buttons and convert to string
        Log.v(sTAG, "validateGender()");

        gender = ((RadioButton)findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();

        // compare against RegEx
        return true;
    }

    private boolean validatePhoto() {
        Log.v(sTAG, "validatePhoto()");
        if (photo != null) {
            Bitmap bitmap = Bitmap.createScaledBitmap(photo, 100, 100, false);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            rawPhoto = stream.toByteArray();
            return true;
        } else {
            Log.i(sTAG, "no photo to save");
            return false;
        }
    }

    private boolean submitData() {
        Log.v(sTAG, "submitData()");
        RealmCustomer customer = new RealmCustomer(forename, surname, address, nationality, gender, dob, rawPhoto);
        realmController.saveCustomer(customer);
        return true;
    }
}
