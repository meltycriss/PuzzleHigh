package com.example.zsystudio.puzzlehigh.register;


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
import com.example.zsystudio.puzzlehigh.login.LoginContract;
import com.example.zsystudio.puzzlehigh.login.LoginFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View, View.OnClickListener {
    private final String TAG = "RegisterFragment";

    private EditText mEtUserName, mEtNickName, mEtPassword, mEtPasswordComfirm;

    private Button mBtnRegister;

    private TextView mTvPrompt;

    private RegisterContract.Presenter mPresenter;

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static RegisterFragment newInstance(){
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register,container,false);
        mEtUserName = (EditText) v.findViewById(R.id.register_username);
        mEtNickName = (EditText) v.findViewById(R.id.register_nickname);
        mEtPassword = (EditText) v.findViewById(R.id.register_password);
        mEtPasswordComfirm = (EditText) v.findViewById(R.id.register_password_confirm);
        mBtnRegister = (Button) v.findViewById(R.id.register_register);
        mTvPrompt = (TextView) v.findViewById(R.id.register_prompt);
        mBtnRegister.setOnClickListener(RegisterFragment.this);
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
        switch (v.getId()){
            case R.id.register_register:{
                String username = mEtUserName.getText().toString(),
                        nickname = mEtNickName.getText().toString(),
                        password = mEtPassword.getText().toString(),
                        password_confirm = mEtPasswordComfirm.getText().toString();
                if(mPresenter.isValid(username,nickname,password,password_confirm)){
                    mPresenter.register(getContext(),username,nickname,password);
                }
                break;
            }
        }

    }
}
