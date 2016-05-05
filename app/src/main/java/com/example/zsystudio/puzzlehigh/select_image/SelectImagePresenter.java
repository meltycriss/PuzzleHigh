package com.example.zsystudio.puzzlehigh.select_image;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.PopupMenu;

import com.example.zsystudio.puzzlehigh.util.JsonBeans.GetPicListResponse;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by liaoyt on 16-5-4.
 */
public class SelectImagePresenter implements SelectImageContract.Presenter {

    private final SelectImageContract.View mView;


    public SelectImagePresenter(SelectImageContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showNativePicList();
        mView.createPopupMenu();
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

                for (int i = 0; i < getPicListResponse.getList().size(); i++){
                    GetPicListResponse.ListBean item = getPicListResponse.getList().get(i);
                    imageItem = new ImageItem();
                    imageItem.setText(item.getUsername() + " " + item.getPostDate());
                    imageItem.setUrl(item.getUrl());
                    tempList.add(imageItem);
                }

                mView.showNetPicList(tempList);

            }
        });
    }

    @Override
    public void selectLocalPic() {

    }
}
