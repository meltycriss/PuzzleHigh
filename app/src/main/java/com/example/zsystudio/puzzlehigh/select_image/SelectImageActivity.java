package com.example.zsystudio.puzzlehigh.select_image;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;

/**
 * Created by liaoyt on 16-5-4.
 */
public class SelectImageActivity extends SingleFragmentActivity {

    private SelectImageFragment mSelectImageFragment;

    private static int RESULT_LOAD_IMG = 1;
    private String imgPath, fileName;
    private Bitmap bitmap;

    @Override
    protected Fragment createFragment() {
        mSelectImageFragment = SelectImageFragment.newInstance();
        return mSelectImageFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SelectImagePresenter(mSelectImageFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();

                Log.d("imgPath", imgPath);

                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                Toast.makeText(this, fileName, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
