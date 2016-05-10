package com.example.zsystudio.puzzlehigh.game;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.select_image.SelectImageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameResultFragment extends DialogFragment implements DialogInterface.OnClickListener{
    public static final String TAG = "GameResultFragment";
    public static final String EXTRA_CURR_SCORE = "GameResult.currScore";
    public static final String EXTRA_TOTAL_SCORE = "GameResult.totalScore";
    public static final String EXTRA_REST_TIME_S = "GameResult.restTimeS";
    public static final String EXTRA_REST_TIME_MS = "GameResult.restTimeMS";

    private int mCurrScore;
    private int mTotalScore;
    private int mRestTimeS;
    private int mRestTimeMS;

    private TextView mTvCurrScore;
    private TextView mTvTotalScore;
    private TextView mTvRestTime;

    public static GameResultFragment newInstance(int _currScore, int _totalScore, int _restTimeS, int _restTimeMS){
        Bundle args = new Bundle();
        args.putInt(EXTRA_CURR_SCORE,_currScore);
        args.putInt(EXTRA_TOTAL_SCORE,_totalScore);
        args.putInt(EXTRA_REST_TIME_S,_restTimeS);
        args.putInt(EXTRA_REST_TIME_MS,_restTimeMS);
        GameResultFragment fragment = new GameResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrScore = getArguments().getInt(EXTRA_CURR_SCORE);
        mTotalScore = getArguments().getInt(EXTRA_TOTAL_SCORE);
        mRestTimeS = getArguments().getInt(EXTRA_REST_TIME_S);
        mRestTimeMS = getArguments().getInt(EXTRA_REST_TIME_MS);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_game_result,null);
        mTvCurrScore = (TextView) v.findViewById(R.id.game_result_curr_score);
        mTvTotalScore = (TextView) v.findViewById(R.id.game_result_total_score);
        mTvRestTime = (TextView) v.findViewById(R.id.game_result_rest_time);
        mTvCurrScore.setText(String.valueOf(mCurrScore));
        mTvTotalScore.setText(String.valueOf(mTotalScore));
        mTvRestTime.setText(String.valueOf(mRestTimeS)+"s"+String.valueOf(mRestTimeMS)+"ms");

        return new AlertDialog.Builder(getContext())
                .setView(v)
                .setTitle("游戏结果")
                .setPositiveButton(android.R.string.ok,GameResultFragment.this)
                .setCancelable(false)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        //Toast.makeText(getContext(),"OK",Toast.LENGTH_SHORT).show();
/*        Intent intent = new Intent(getContext(), SelectImageActivity.class);
        startActivity(intent);*/
        getActivity().finish();
    }
}
