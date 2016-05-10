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

        void getNetPicList();

        void getLocalPic();

        void dealWithResult(int requestCode, int resultCode, Intent data);

        void imageDownload(final Context context, final ImageItem imageItem, final SelectImagePresenter.DownloadCallback downloadCallback);

    }

    interface View extends BaseView{

        void showNativePicList();

        void showNetPicList(ArrayList<ImageItem> arrayList);

    }
}
