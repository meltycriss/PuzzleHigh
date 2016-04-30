package com.example.zsystudio.puzzlehigh.data;

/**
 * Created by Criss on 2016/4/30.
 */

public class User {
    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        User.isLogin = isLogin;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        User.nickName = nickName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static int getGamePoint() {
        return gamePoint;
    }

    public static void setGamePoint(int gamePoint) {
        User.gamePoint = gamePoint;
    }

    private static User ourInstance = new User();

    private static boolean isLogin = false;

    private static String nickName;

    private static String userName;

    private static int gamePoint;

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
