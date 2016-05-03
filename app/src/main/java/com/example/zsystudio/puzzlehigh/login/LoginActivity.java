package com.example.zsystudio.puzzlehigh.login;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;

public class LoginActivity extends SingleFragmentActivity {
    private LoginFragment mLoginFragment;

    @Override
    protected Fragment createFragment() {
        return mLoginFragment = LoginFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoginPresenter(mLoginFragment);
    }
}
