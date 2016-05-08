package com.example.zsystudio.puzzlehigh.select_image;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.game.GameActivity;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.GetPicListResponse;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Response;

/**
 * Created by liaoyt on 16-5-4.
 */
public class SelectImagePresenter implements SelectImageContract.Presenter {

    private final SelectImageContract.View mView;
    private final Activity activity;

    // 图片保存的路径
    private static final String MYDIR = "/PuzzleHigh/";
    private static final String FILEPATH = Environment.getExternalStorageDirectory() + MYDIR;
    private String filename;

    // 选取本地图片时使用的常量
    public static final int TAKE_A_PICTURE = 10;
    public static final int SELECT_A_PICTURE = 20;
    public static final int SET_PICTURE = 30;
    public static final int SET_ALBUM_PICTURE_KITKAT = 40;
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
    private String mAlbumPicturePath = null;

    //版本比较：是否是4.4及以上版本
    final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public SelectImagePresenter(Activity activity, SelectImageContract.View mView) {
        this.mView = mView;
        this.activity = activity;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showNativePicList();
        mView.createPopupMenu();
        this.createMyDir();
    }

    @Override
    public void getNetPicList() {

        OKHttpUtil.getPicList(new OKHttpUtil.HttpCallback() {
            @Override
            public void onFailure(Response response, Throwable throwable) {

            }

            @Override
            public void onSuccess(Response response) throws IOException {

                ArrayList<ImageItem> tempList = new ArrayList<ImageItem>();
                ImageItem imageItem;

                GetPicListResponse getPicListResponse = new Gson().fromJson(response.body().string(), GetPicListResponse.class);

                for (int i = 0; i < getPicListResponse.getList().size(); i++) {
                    GetPicListResponse.ListBean item = getPicListResponse.getList().get(i);
                    imageItem = new ImageItem();
                    imageItem.setText(item.getUsername() + "_" + item.getPostDate());
                    imageItem.setUrl(item.getUrl());
                    tempList.add(imageItem);
                }

                mView.showNetPicList(tempList);

            }
        });
    }

    @Override
    public void getLocalPic() {
        if (mIsKitKat) {
            this.selectImageUriAfterKikat();
        } else {
            this.cropImageUri();
        }
    }

    @Override
    public void dealWithResult(int requestCode, int resultCode, Intent data) {

        Uri imageUri = null;

        if (requestCode == SELECT_A_PICTURE) {
            if (resultCode == Activity.RESULT_OK && null != data) {
                //				Log.i("zou", "4.4以下的");
                imageUri = Uri.fromFile(new File(FILEPATH, filename));
                Log.d("My_uri", imageUri.toString());
            }
        } else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
            if (resultCode == Activity.RESULT_OK && null != data) {
//				Log.i("zou", "4.4以上的");
                mAlbumPicturePath = getPath(activity, data.getData());
                cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
            }
        } else if (requestCode == SET_ALBUM_PICTURE_KITKAT) {
            Log.i("zou", "4.4以上上的 RESULT_OK");

            imageUri = Uri.fromFile(new File(FILEPATH, filename));
            Log.d("My_uri", imageUri.toString());
        }

        if (null != imageUri){
            GameActivity.actionStart(activity, GameActivity.LOCAL, imageUri);
        }
    }

    // 4.4以上裁剪图片方法实现
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKikat() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
    }

    private void cropImageUriAfterKikat(Uri uri) {

        filename = User.getInstance().getUserName() +
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) +
                ".jpg";

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 520);
        intent.putExtra("outputY", 520);
        intent.putExtra("scale", true);
        //		intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(FILEPATH, filename)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
    }

    // 4.4以下的裁剪图片方法实现
    private void cropImageUri() {

        filename = User.getInstance().getUserName() +
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) +
                ".jpg";

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 520);
        intent.putExtra("outputY", 520);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(FILEPATH, filename)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, SELECT_A_PICTURE);
    }

    private void createMyDir() {
        // 创建文件夹
        File file = new File(FILEPATH);
        if (!file.exists())
            file.mkdir();
    }

    @Override
    public Uri imageDownload(final Context context, final ImageItem imageItem) {

        String fileName = FILEPATH + imageItem.getText() + ".jpg";
        Picasso.with(context).load(imageItem.getUrl()).into(getTarget(fileName));

        Uri uri = new Uri.Builder().path(fileName).build();
        return uri;
    }

    private Target getTarget(final String path) {

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        File file = new File(path);
                        try {
                            file.createNewFile();
                            FileOutputStream outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
