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

    public void init(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        isLogin = sharedPreferences.getBoolean("isLogin", false);
        userName = sharedPreferences.getString("userName", "");
        nickName = sharedPreferences.getString("nickName", "");
        gamePoint = sharedPreferences.getInt("gamePoint", 0);

    }

    public void save(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.putString("userName", userName);
        editor.putString("nickName", nickName);
        editor.putInt("gamePoint", gamePoint);
        editor.commit();
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
