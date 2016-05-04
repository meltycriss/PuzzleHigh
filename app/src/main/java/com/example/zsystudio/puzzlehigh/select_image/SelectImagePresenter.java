package com.example.zsystudio.puzzlehigh.select_image;

import android.support.v7.widget.PopupMenu;

/**
 * Created by liaoyt on 16-5-4.
 */
public class SelectImagePresenter implements SelectImageContract.Presenter {

    private final SelectImageContract.View mView;

    private PopupMenu popupMenu;


    public SelectImagePresenter(SelectImageContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showNativePicList();
        mView.createPopupMenu();
    }
}
