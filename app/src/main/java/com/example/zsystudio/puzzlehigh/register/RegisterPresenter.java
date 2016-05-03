package com.example.zsystudio.puzzlehigh.register;

import com.example.zsystudio.puzzlehigh.util.JsonBeans.RegisterResponse;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;

/**
 * Created by Criss on 2016/5/3.
 */
public class RegisterPresenter implements RegisterContract.Presenter{

    private final RegisterContract.View mView;

    public RegisterPresenter(RegisterContract.View _registerView){
        mView = _registerView;
        mView.setPresenter(RegisterPresenter.this);
    }

    @Override
    public void register(String _username, String _nickname, String _password) {
        RegisterResponse rr= OKHttpUtil.register(_username,_nickname,_password);
        if(rr.getSuccess() == 1){
            mView.hidePrompt();
            mView.toastMsg("success");
        }
        else{
            mView.showPrompt("fail to register");
        }
    }

    @Override
    public boolean isValid(String _username, String _nickname, String _password, String _password_confirm) {
        return _password.equals(_password_confirm);
    }

    @Override
    public void start() {

    }
}
