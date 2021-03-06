package com.example.zsystudio.puzzlehigh.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.util.Base64;
import android.util.Log;

import com.example.zsystudio.puzzlehigh.util.JsonBeans.GetPicListResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.GetRankResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.LoginResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.PostPictureResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.PostScoreResponse;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.RegisterResponse;

import java.io.ByteArrayOutputStream;
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
    private static final String host = "http://172.18.42.97/";
    private static final String port = "80";

    private static final String REGISTER = "registe.php";
    private static final String LOGIN = "login.php";
    private static final String POSTPIC = "postPicture.php";
    private static final String POSTSCORE = "postScore.php";
    private static final String GETRANK = "getRank.php";
    private static final String GETPICLIST = "getPicList.php";

    private static final OkHttpClient client = new OkHttpClient();

    // HttpCallback包含两个在UI线程执行的方法，在这里做UI操作
    public interface HttpCallback {

        public void onFailure(final Response response, final Throwable throwable);

        public void onSuccess(final String json) throws IOException;
    }

    private static void doRequest(final Request request, final HttpCallback httpCallback) {

        // enqueue方法异步请求，其中的两个回调方法都是在后台运行的
        client.newCall(request).enqueue(new Callback() {

            // 通过MainLooper将回调post到UI线程执行
            Handler mainHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) {
                if (!response.isSuccessful()) {
                    onFailure(call, null);
                } else {
                    try {
                        final String json = response.body().string();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    httpCallback.onSuccess(json);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (!response.isSuccessful()){
//                            httpCallback.onFailure(response, null);
//                            return;
//                        }
//                        try {
//                            httpCallback.onSuccess(response);
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
//                    }
//                });
            }
        });
    }

    public static void login(final String username, final String password,
                             final HttpCallback httpCallback) {

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(host + LOGIN)
                .post(requestBody)
                .build();

        doRequest(request, httpCallback);
    }

    ;

    public static void register(final String username, final String nickname, final String password,
                                final HttpCallback httpCallback) {

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("nickname", nickname)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(host + REGISTER)
                .post(requestBody)
                .build();

        doRequest(request, httpCallback);
    }

    public static void postPic(final String username, final Uri imageUri,
                               final HttpCallback httpCallback) {

        ImageDecodeHelper helper = new ImageDecodeHelper(username, imageUri, httpCallback);
        helper.encodeImagetoString();

    }

    // 处理图片的压缩与上传
    private static class ImageDecodeHelper {

        private String username;
        private String imgPath;
        private String encodedString;
        private Bitmap bitmap;
        private HttpCallback httpCallback;

        ImageDecodeHelper(final String username, final Uri imageUri,
                          final HttpCallback httpCallback) {
            this.username = username;
            this.imgPath = imageUri.getPath();
            Log.d("ImageDecodeHelper", imgPath);
            this.httpCallback = httpCallback;
            this.bitmap = null;
            this.encodedString = null;
        }

        public void encodeImagetoString() {

            Log.d("ImageDecodeHelper", "encodeImage");

            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... params) {
                    BitmapFactory.Options options = null;
                    options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    bitmap = BitmapFactory.decodeFile(imgPath,
                            options);
                    Log.d("ImageDecodeHelper", "bitmap Btye count" + bitmap.getByteCount());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Must compress the Image to reduce image size to make upload easy
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream);
                    byte[] byte_arr = stream.toByteArray();
                    Log.d("ImageDecodeHelper", "byte array length" + byte_arr.length);
                    // Encode Image to String
                    encodedString = Base64.encodeToString(byte_arr, 0);
                    bitmap.recycle();
                    return "";
                }

                @Override
                protected void onPostExecute(String msg) {
                    Log.d("ImageDecodeHelper", "trigger");
                    triggerImageUpload();
                }
            }.execute(null, null, null);
        }

        public void triggerImageUpload() {
            makeHTTPCall();
        }

        public void makeHTTPCall() {

            Log.d("ImageDecodeHelper", "makeCall");
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("image", encodedString)
                    .build();

            Request request = new Request.Builder()
                    .url(host + POSTPIC)
                    .post(requestBody)
                    .build();

            doRequest(request, httpCallback);
        }
    }

    public static void postScore(final String username, final int score,
                                 final HttpCallback httpCallback) {

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("score", Integer.toString(score))
                .build();

        Request request = new Request.Builder()
                .url(host + POSTSCORE)
                .post(requestBody)
                .build();

        doRequest(request, httpCallback);

    }

    public static void getRank(final int score, final HttpCallback httpCallback) {

        RequestBody requestBody = new FormBody.Builder()
                .add("score", Integer.toString(score))
                .build();

        Request request = new Request.Builder()
                .url(host + GETRANK)
                .post(requestBody)
                .build();

        doRequest(request, httpCallback);

    }

    public static void getPicList(final HttpCallback httpCallback) {

        RequestBody requestBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(host + GETPICLIST)
                .post(requestBody)
                .build();

        doRequest(request, httpCallback);

    }
}
