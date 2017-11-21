package com.muelpatmore.week1assignment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.muelpatmore.week1assignment.fragments.LoginViewModel;

/**
 * Created by Samuel on 21/11/2017.
 */

public class CreateCustomerViewModel extends Fragment {

    private static CreateCustomerViewModel mInstance;
    private CreateCustomerModel mCreateCustomerModel;

    private ButtonClicked mButtonClicked;
    private EditText etForename, etSurname, etDOB, etAddress;
    private RadioGroup rgGender;
    private ImageView ivPhoto;
    private Spinner spNationality;
    private Button btnDate, btnSubmit, btnPhoto;


    public static CreateCustomerViewModel getInstance()  {
        // double lock to prevent two processes creating a SplashViewModel simultaneously
        synchronized (CreateCustomerViewModel.class) {
            // create RealmController instance if none exists
            if (mInstance == null) {
                synchronized (CreateCustomerViewModel.class) {
                    mInstance = new CreateCustomerViewModel();
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
        return inflater.inflate(R.layout.activity_create_customer, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        // text Views
        etForename = view.findViewById(R.id.etForname);
        etSurname = view.findViewById(R.id.etSurname);
        etDOB = view.findViewById(R.id.etDOB);
        etAddress = view.findViewById(R.id.etAddress);
        // spinners
        spNationality = view.findViewById(R.id.spNationality);
        // radio group
        rgGender = view.findViewById(R.id.rgGender);
        // images
        ivPhoto = view.findViewById(R.id.ivPhoto);

        // buttons
        btnPhoto = view.findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap photo = mCreateCustomerModel.takePhoto();
                if (photo != null) {
                    ivPhoto.setImageBitmap(photo);
                }
            }
        });

        btnDate = view.findViewById(R.id.btnDate);
        btnSubmit = view.findViewById(R.id.btnSubmit);


    }
}
