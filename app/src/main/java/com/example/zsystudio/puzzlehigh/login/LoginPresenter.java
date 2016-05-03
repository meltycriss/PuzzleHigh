package com.example.zsystudio.puzzlehigh.login;

import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.LoginResponse;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;

/**
 * Created by Criss on 2016/5/3.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;

    public void login(String _username, String _password) {
        //mView.toastMsg("username: "+_username+"\npassword: "+_password);
        LoginResponse lr = OKHttpUtil.login(_username, _password);
        //if(lr==null) mView.toastMsg("null");
        if (lr.getSuccess() == 1) {
            User.setIsLogin(true);
            User.setUserName(_username);
            mView.hidePrompt();
            mView.toastMsg("success");
        } else {
            mView.showPrompt("fail to login");
        }
    }

    @Override
    public void start() {

    }

    public LoginPresenter(LoginContract.View _loginView) {
        mView = _loginView;
        mView.setPresenter(LoginPresenter.this);
    }
}
