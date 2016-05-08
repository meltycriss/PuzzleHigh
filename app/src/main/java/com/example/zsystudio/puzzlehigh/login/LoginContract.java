package com.example.zsystudio.puzzlehigh.login;

import android.content.Context;

import com.example.zsystudio.puzzlehigh.util.BasePresenter;
import com.example.zsystudio.puzzlehigh.util.BaseView;

/**
 * Created by Criss on 2016/4/30.
 */
public interface LoginContract {
    interface Presenter extends BasePresenter{
        void login(Context _context, String _username, String _password);
    }

    interface View extends BaseView<Presenter>{
        void toastMsg(String _msg);

        void showPrompt(String _msg);

        void hidePrompt();
    }
}
