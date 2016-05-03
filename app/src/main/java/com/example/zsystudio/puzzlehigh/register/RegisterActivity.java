package com.example.zsystudio.puzzlehigh.register;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;

public class RegisterActivity extends SingleFragmentActivity {
    private RegisterFragment mRegisterFragment;

    @Override
    protected Fragment createFragment() {
        return mRegisterFragment = RegisterFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RegisterPresenter(mRegisterFragment);
    }
}
