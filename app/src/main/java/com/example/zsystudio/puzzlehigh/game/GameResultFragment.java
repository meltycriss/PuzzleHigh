package com.example.zsystudio.puzzlehigh.game;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameResultFragment extends DialogFragment implements DialogInterface.OnClickListener{
    public static final String EXTRA_CURR_SCORE = "GameResult.currScore";
    public static final String EXTRA_TOTAL_SCORE = "GameResult.totalScore";

    private int mCurrScore;
    private int mTotalScore;

    private TextView mTvCurrScore;
    private TextView mTvTotalScore;

    public static GameResultFragment newInstance(int _currScore, int _totalScore){
        Bundle args = new Bundle();
        args.putInt(EXTRA_CURR_SCORE,_currScore);
        args.putInt(EXTRA_TOTAL_SCORE,_totalScore);
        GameResultFragment fragment = new GameResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrScore = getArguments().getInt(EXTRA_CURR_SCORE);
        mTotalScore = getArguments().getInt(EXTRA_TOTAL_SCORE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_game_result,null);
        mTvCurrScore = (TextView) v.findViewById(R.id.game_result_curr_score);
        mTvTotalScore = (TextView) v.findViewById(R.id.game_result_total_score);
        mTvCurrScore.setText(String.valueOf(mCurrScore));
        mTvTotalScore.setText(String.valueOf(mTotalScore));

        return new AlertDialog.Builder(getContext())
                .setView(v)
                .setTitle("游戏结果")
                .setPositiveButton(android.R.string.ok,GameResultFragment.this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(getContext(),"OK",Toast.LENGTH_SHORT).show();
    }
}
