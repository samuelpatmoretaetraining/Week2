package com.muelpatmore.week1assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.muelpatmore.week1assignment.realm.RealmController;

/**
 * Created by Samuel on 21/11/2017.
 */

public class CreateCustomerModel extends AppCompatActivity{

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    private RealmController mRealmController;
    private Bitmap mBitmap = null;

    public CreateCustomerModel() {
        // retrieve RealmController
        mRealmController = RealmController.getInstance();
    }

    public Bitmap takePhoto() {
        mBitmap = null;
        dispatchTakePictureIntent();
        return mBitmap;
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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mBitmap =  (Bitmap) extras.get("data");
        } else {
        }
    }
}
