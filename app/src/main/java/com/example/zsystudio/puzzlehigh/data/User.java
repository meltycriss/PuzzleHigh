package com.example.zsystudio.puzzlehigh.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.util.JsonBeans.PostScoreResponse;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Criss on 2016/4/30.
 */

public class User {
    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGamePoint() {
        return gamePoint;
    }

    public void setGamePoint(int gamePoint) {
        this.gamePoint = gamePoint;
    }

    public void init(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        isLogin = sharedPreferences.getBoolean("isLogin", false);
        userName = sharedPreferences.getString("userName", "zhangsan");
        nickName = sharedPreferences.getString("nickName", "zhangsan");
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

    public void update(){

        OKHttpUtil.postScore(userName, gamePoint, new OKHttpUtil.HttpCallback() {
            @Override
            public void onFailure(Response response, Throwable throwable) {

            }

            @Override
            public void onSuccess(String json) throws IOException {

                PostScoreResponse postScoreResponse = new Gson().fromJson(json, PostScoreResponse.class);

                Log.d("PostScore", postScoreResponse.getMessage());
            }
        });
    }
}
