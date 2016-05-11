package com.example.zsystudio.puzzlehigh.rank;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.util.TypefaceUtil;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment implements RankContract.View{
    public static final int RANK_USER = 0;
    public static final int RANK_FIRST = 1;
    public static final int RANK_SECOND = 2;
    public static final int RANK_THIRD = 3;


    private RankContract.Presenter mPresenter;

    private TextView mTvUserName;
    private TextView mTvFirstName;
    private TextView mTvSecondName;
    private TextView mTvThirdName;
    private TextView mTvUserScore;
    private TextView mTvFirstScore;
    private TextView mTvSecondScore;
    private TextView mTvThirdScore;
    private TextView mTvUserRank;

    @Override
    public void toastMsg(String _msg) {
        Toast.makeText(getContext(),_msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInfoOfRank(int _type, String _name, String _score) {
        switch (_type){
            case RANK_USER:{
                //for user, _score is the rank
                mTvUserName.setText(User.getInstance().getUserName());
                mTvUserScore.setText(String.valueOf(User.getInstance().getGamePoint()));
                mTvUserRank.setText(_score);
                break;
            }
            case RANK_FIRST:{
                mTvFirstName.setText(_name);
                mTvFirstScore.setText(_score);
                break;
            }
            case RANK_SECOND:{
                mTvSecondName.setText(_name);
                mTvSecondScore.setText(_score);
                break;
            }
            case RANK_THIRD:{
                mTvThirdName.setText(_name);
                mTvThirdScore.setText(_score);
                break;
            }
        }
    }

    @Override
    public void setPresenter(RankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static RankFragment newInstance(){
        RankFragment fragment = new RankFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rank, container, false);

        //TypefaceUtil.overrideFont(getContext().getApplicationContext(), "SERIF", "fonts/miao.ttf");
        //TypefaceUtil.overrideFont(getContext().getApplicationContext(), "Criss", "fonts/miao.ttf");
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/miao.ttf");//根据路径得到Typeface

        mTvUserName = (TextView) v.findViewById(R.id.rank_user_name);
        mTvFirstName = (TextView) v.findViewById(R.id.rank_first_name);
        mTvSecondName = (TextView) v.findViewById(R.id.rank_second_name);
        mTvThirdName = (TextView) v.findViewById(R.id.rank_third_name);
        mTvUserScore = (TextView) v.findViewById(R.id.rank_user_score);
        mTvFirstScore = (TextView) v.findViewById(R.id.rank_first_score);
        mTvSecondScore = (TextView) v.findViewById(R.id.rank_second_score);
        mTvThirdScore = (TextView) v.findViewById(R.id.rank_third_score);
        mTvUserRank = (TextView) v.findViewById(R.id.rank_user_rank);
        mPresenter.getRank();
        return v;
    }

}
