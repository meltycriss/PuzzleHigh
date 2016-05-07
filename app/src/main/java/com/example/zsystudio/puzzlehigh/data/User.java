package com.example.zsystudio.puzzlehigh.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Criss on 2016/4/30.
 */

public class User {
    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        isLogin = isLogin;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public int getGamePoint() {
        return gamePoint;
    }

    public void setGamePoint(int gamePoint) {
        gamePoint = gamePoint;
    }

    private static User ourInstance = new User();

    private boolean isLogin;

    private String nickName;

    private String userName;

    private int gamePoint;

    public static User getInstance() {
        return ourInstance;
    }

    private User() {

        this.isLogin = false;
        this.nickName = "";
        this.userName = "";
        this.gamePoint = 0;
    }
}
