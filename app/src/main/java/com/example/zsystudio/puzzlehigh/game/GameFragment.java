package com.example.zsystudio.puzzlehigh.game;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zsystudio.puzzlehigh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements PuzzleView.GameOverCallBack{
    public static final String EXTRA_DIFFICULTY = "GameFragment.difficulty";

    public static final int MSG_TIMER = 75532;
    public static final int MSG_GAME_CHECKOUT = 75533;

    int countDownS = 200;
    int countDownMS = 00;
    int GameStatus = PuzzleView.GAME_ON;

    private int mDifficulty;

    @Override
    public void onGameOver() {
        GameStatus = PuzzleView.GAME_OVER;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDifficulty = getArguments().getInt(EXTRA_DIFFICULTY);
    }

    public static GameFragment newInstance(int _difficulty) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_DIFFICULTY,_difficulty);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PuzzleView puzzleView = new PuzzleView(getActivity(),mDifficulty,GameFragment.this);
        
        return puzzleView;
    }

}
