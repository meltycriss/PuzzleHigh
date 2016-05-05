package com.example.zsystudio.puzzlehigh.select_image;

import com.example.zsystudio.puzzlehigh.util.BasePresenter;
import com.example.zsystudio.puzzlehigh.util.BaseView;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;

import java.util.ArrayList;

/**
 * Created by liaoyt on 16-5-4.
 */
public interface SelectImageContract {

    interface Presenter extends BasePresenter{

        void getNetPicList();

        void selectLocalPic();
    }

    interface View extends BaseView{

        void createPopupMenu();

        void showNativePicList();

        void showNetPicList(ArrayList<ImageItem> arrayList);

    }
}
