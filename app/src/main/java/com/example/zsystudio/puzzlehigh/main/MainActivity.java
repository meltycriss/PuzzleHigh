package com.example.zsystudio.puzzlehigh.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;

public class MainActivity extends SingleFragmentActivity {
    public static final String EXTRA_IS_LOGIN = "com.example.zsystudio.puzzlehigh.main.isLogined";

    private boolean mIsLogin;

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance(mIsLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsLogin = getIntent().getBooleanExtra(EXTRA_IS_LOGIN,false);
        super.onCreate(savedInstanceState);
    }
}
