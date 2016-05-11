package com.example.zsystudio.puzzlehigh.rank;

import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.util.JsonBeans.GetRankResponse;
import com.example.zsystudio.puzzlehigh.util.OKHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Criss on 2016/5/8.
 */
public class RankPresenter implements RankContract.Presenter{

    private final RankContract.View mView;

    public RankPresenter(RankContract.View _rankView){
        mView = _rankView;
        mView.setPresenter(RankPresenter.this);
    }

    @Override
    public void getRank() {
//        mView.toastMsg("success");

        OKHttpUtil.getRank(User.getInstance().getGamePoint(), new OKHttpUtil.HttpCallback() {
            @Override
            public void onFailure(Response response, Throwable throwable) {

            }

            @Override
            public void onSuccess(String json) throws IOException {
                // json解析没有封装，需要自性解析
                GetRankResponse rr = new Gson().fromJson(json, GetRankResponse.class);

                if (rr.getSuccess() == 1) {
                    mView.showInfoOfRank(RankFragment.RANK_USER,"",rr.getMyRank());
                    mView.showInfoOfRank(RankFragment.RANK_FIRST,rr.getTopthree().get(0).getTopUsername(),rr.getTopthree().get(0).getTopScore());
                    mView.showInfoOfRank(RankFragment.RANK_SECOND,rr.getTopthree().get(1).getTopUsername(),rr.getTopthree().get(1).getTopScore());
                    mView.showInfoOfRank(RankFragment.RANK_THIRD,rr.getTopthree().get(2).getTopUsername(),rr.getTopthree().get(2).getTopScore());
//                    mView.toastMsg("success");
                } else {
//                    mView.toastMsg("fail to login");
                }
            }
        });

    }

    @Override
    public void start() {

    }
}
