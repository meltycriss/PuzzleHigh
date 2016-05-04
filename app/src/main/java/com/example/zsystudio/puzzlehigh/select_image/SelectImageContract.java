package com.example.zsystudio.puzzlehigh.select_image;

import com.example.zsystudio.puzzlehigh.util.BasePresenter;
import com.example.zsystudio.puzzlehigh.util.BaseView;

/**
 * Created by liaoyt on 16-5-4.
 */
public interface SelectImageContract {

    interface Presenter extends BasePresenter{

    }

    interface View extends BaseView{

        void createPopupMenu();

        void showNativePicList();

    }
}
