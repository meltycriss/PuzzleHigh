package com.example.zsystudio.puzzlehigh.select_image;

import android.os.Bundle;
import android.support.v4.app.Fragment;

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
        new SelectImagePresenter(mSelectImageFragment).start();
    }
}
