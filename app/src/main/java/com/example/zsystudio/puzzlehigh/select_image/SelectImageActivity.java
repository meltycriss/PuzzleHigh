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


    @Override
    protected Fragment createFragment() {
        mSelectImageFragment = SelectImageFragment.newInstance();
        return mSelectImageFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SelectImagePresenter(this, mSelectImageFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理选择的图片
        mSelectImageFragment.getPresenter().dealWithResult(requestCode, resultCode, data);
    }
}
