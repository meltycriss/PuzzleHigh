package com.example.zsystudio.puzzlehigh.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.register.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {
    private final String TAG = "LoginFragment";

    private EditText mEtUserName, mEtPassword;

    private Button mBtnLogin, mBtnRegister;

    private TextView mTvPrompt;

    private LoginContract.Presenter mPresenter;

    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;

    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mEtUserName = (EditText) v.findViewById(R.id.login_username);
        mEtPassword = (EditText) v.findViewById(R.id.login_password);
        mBtnLogin = (Button) v.findViewById(R.id.login_login);
        mBtnRegister = (Button) v.findViewById(R.id.login_register);
        mTvPrompt = (TextView) v.findViewById(R.id.login_prompt);
        mBtnLogin.setOnClickListener(LoginFragment.this);
        mBtnRegister.setOnClickListener(LoginFragment.this);
        return v;
    }

    @Override
    public void toastMsg(String _msg) {
        Toast.makeText(getContext(), _msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPrompt(String _msg) {
        mTvPrompt.setVisibility(View.VISIBLE);
        mTvPrompt.setText(_msg);
    }

    @Override
    public void hidePrompt() {
        mTvPrompt.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login: {
                mPresenter.login(mEtUserName.getText().toString(), mEtPassword.getText().toString());
                break;
            }
            case R.id.login_register: {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
