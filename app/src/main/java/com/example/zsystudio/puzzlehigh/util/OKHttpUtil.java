package com.example.zsystudio.puzzlehigh.util;

import com.example.zsystudio.puzzlehigh.util.JsonBeans.GetPicListResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.GetRankResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.LoginResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.PostPictureResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.PostScoreResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.RegisterResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liaoyt on 16-5-1.
 */
public class OKHttpUtil {
    private static RegisterResponse registerResponse;
    private static LoginResponse loginResponse;
    private static PostPictureResponse postPictureResponse;
    private static PostScoreResponse postScoreResponse;
    private static GetRankResponse getRankResponse;
    private static GetPicListResponse getPicListResponse;
    private final String host = "http://172.18.42.97/";
    private final String port = "80";

    private static final String REGISTER = "registe.php";
    private static final String LOGIN = "login.php";
    private static final String POSTPIC = "postPic.php";
    private static final String POSTSCORE = "postScore.php";
    private static final String GETRANK = "getRank.php";
    private static final String GETPICLIST = "getPicList.php";

    private static final OkHttpClient client = new OkHttpClient();

    public static LoginResponse login(String username, String password){

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(host + LOGIN)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                loginResponse = new Gson().fromJson(response.body().string(), LoginResponse.class);
            }
        });
        return loginResponse;
    };

    public static RegisterResponse register(String username, String nickname, String password){

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("nickname", nickname)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(host + REGISTER)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                registerResponse = new Gson().fromJson(response.body().string(), RegisterResponse.class);
            }
        });
        return registerResponse;
    }

    public static PostPictureResponse postPic(String username, String image){

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("image", image)
                .build();

        Request request = new Request.Builder()
                .url(host + POSTPIC)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                postPictureResponse = new Gson().fromJson(response.body().string(), PostPictureResponse.class);
            }
        });

        return postPictureResponse;
    }

    public static PostScoreResponse postScore(String username, int score){

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("score", Integer.toString(score))
                .build();

        Request request = new Request.Builder()
                .url(host + POSTSCORE)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                postScoreResponse = new Gson().fromJson(response.body().string(), PostScoreResponse.class);
            }
        });

        return postScoreResponse;
    }

    public static GetRankResponse getRank(int score){

        RequestBody requestBody = new FormBody.Builder()
                .add("score", Integer.toString(score))
                .build();

        Request request = new Request.Builder()
                .url(host + GETRANK)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getRankResponse = new Gson().fromJson(response.body().string(), GetRankResponse.class);
            }
        });

        return getRankResponse;
    }

    public static GetPicListResponse getPicList(){

        RequestBody requestBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(host + GETRANK)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getPicListResponse = new Gson().fromJson(response.body().string(), GetPicListResponse.class);
            }
        });

        return getPicListResponse;
    }
}
