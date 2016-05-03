package com.example.zsystudio.puzzlehigh.register;

import com.example.zsystudio.puzzlehigh.login.LoginContract;
import com.example.zsystudio.puzzlehigh.util.BasePresenter;
import com.example.zsystudio.puzzlehigh.util.BaseView;

/**
 * Created by Criss on 2016/5/3.
 */
public interface RegisterContract {
    interface Presenter extends BasePresenter{
        void register(String _username,String _nickname, String _password);

        boolean isValid(String _username, String _nickname, String _password, String _password_confirm);
    }

    interface View extends BaseView<Presenter>{
        void toastMsg(String _msg);

        void showPrompt(String _msg);

        void hidePrompt();
    }
}
