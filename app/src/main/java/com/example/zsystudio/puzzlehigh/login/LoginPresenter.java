package com.example.zsystudio.puzzlehigh.login;

import android.content.Context;
import android.content.Intent;

import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.main.MainActivity;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.LoginResponse;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Criss on 2016/5/3.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;

    public static void loginLocal(Context _context, String _username){
        User.getInstance().setIsLogin(true);
        User.getInstance().setUserName(_username);
        User.getInstance().save(_context);
        Intent intent = new Intent(_context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _context.startActivity(intent);
    }

    public void login(final Context _context, final String _username, final String _password) {

        // 调用的时候需要实现一个HttpCallback，才能在UI线程执行更新界面操作
        OKHttpUtil.login(_username, _password, new OKHttpUtil.HttpCallback() {
            @Override
            public void onFailure(Response response, Throwable throwable) {

            }

            @Override
            public void onSuccess(Response response) throws IOException {

                // json解析没有封装，需要自性解析
                LoginResponse lr = new Gson().fromJson(response.body().string(), LoginResponse.class);

                if (lr.getSuccess() == 1) {
                    mView.hidePrompt();
                    LoginPresenter.loginLocal(_context,_username);
//                    mView.toastMsg(User.getInstance().getUserName());
                } else {
                    mView.showPrompt("fail to login");
                }
            }
        });

    }

    @Override
    public void start() {

    }

    public LoginPresenter(LoginContract.View _loginView) {
        mView = _loginView;
        mView.setPresenter(LoginPresenter.this);
    }
}
