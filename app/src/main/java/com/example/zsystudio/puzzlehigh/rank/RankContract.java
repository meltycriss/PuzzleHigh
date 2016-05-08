package com.example.zsystudio.puzzlehigh.rank;

import com.example.zsystudio.puzzlehigh.util.BasePresenter;
import com.example.zsystudio.puzzlehigh.util.BaseView;

/**
 * Created by Criss on 2016/5/8.
 */
public interface RankContract {
    interface Presenter extends BasePresenter{
        void getRank();
    }

    interface View extends BaseView<Presenter>{
        void showInfoOfRank(int _x, String _name, String _score);

        void toastMsg(String _msg);
    }
}
