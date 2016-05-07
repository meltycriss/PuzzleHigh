package com.example.zsystudio.puzzlehigh.select_image;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.zsystudio.puzzlehigh.util.BasePresenter;
import com.example.zsystudio.puzzlehigh.util.BaseView;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by liaoyt on 16-5-4.
 */
public interface SelectImageContract {

    interface Presenter extends BasePresenter{

        void createMyDir();

        void getNetPicList();

        void getLocalPic();

        void selectImageUriAfterKikat();

        void cropImageUriAfterKikat(Uri uri);

        void cropImageUri();

        Uri dealWithResult(int requestCode, int resultCode, Intent data);

        Uri imageDownload(final Context context, final ImageItem imageItem);

        Target getTarget(final String path);
    }

    interface View extends BaseView{

        void createPopupMenu();

        void showNativePicList();

        void showNetPicList(ArrayList<ImageItem> arrayList);

    }
}
