package com.example.zsystudio.puzzlehigh.game;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.data.User;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements PuzzleView.GameOverCallBack{
    public static final String TAG = "GameFragment";

    public static final String EXTRA_DIFFICULTY = "GameFragment.difficulty";
    public static final String EXTRA_IMAGE_URI = "GameFragment.image_uri";

    public static final int MSG_TIMER = 75532;

    private Handler mCountDownHandler;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private TextView mTvCountDown;
    private PuzzleView mPuzzleView;

    int countDownS = 200; //total game time in second
    int countDownMS = 00; //total game time in microsecond
    int GameStatus = PuzzleView.GAME_ON;

    private int mDifficulty;
    private Uri mImageUri;

    /*
     * input: remaining time
     * output: score for this game
     */
    private int getCurrScore(){
        return (int) (mDifficulty*(countDownS+countDownMS*0.001));
    }

    /*
     * function: add the current score to the total score
     * output: total score
     */
    private int getTotalScore(){
        return User.getInstance().getGamePoint();
        //return (int) (Math.random()*1000);
    }

    /*
     * triggered by: time up or puzzle success
     * function: deal with result
     */
    @Override
    public void onGameOver() {
        GameStatus = PuzzleView.GAME_OVER;
        mPuzzleView.setGameStatus(PuzzleView.GAME_OVER);

        //update user info
        User.getInstance().update();
        User.getInstance().setGamePoint(User.getInstance().getGamePoint() + getCurrScore());


        int currScore = getCurrScore();
        int totalScore = getTotalScore();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        GameResultFragment dialog = GameResultFragment.newInstance(currScore, totalScore, countDownS, countDownMS);
        dialog.show(fm, GameResultFragment.TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDifficulty = getArguments().getInt(EXTRA_DIFFICULTY);
        mImageUri = getArguments().getParcelable(EXTRA_IMAGE_URI);

        //count down display control
        mCountDownHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_TIMER && GameStatus == PuzzleView.GAME_ON) {
                    countDownMS--;
                    if (countDownMS < 0) {
                        countDownS--;
                        countDownMS += 10;
                    }
                    if (countDownS >= 100)
                        mTvCountDown.setText("" + countDownS + ":" + countDownMS);
                    else if (countDownS >= 10)
                        mTvCountDown.setText("0" + countDownS + ":" + countDownMS);
                    else if (countDownS > 0 || countDownMS > 0)
                        mTvCountDown.setText("00" + countDownS + ":" + countDownMS);
                    else {
                        GameStatus = PuzzleView.GAME_OVER;
                        onGameOver();
                    }
                }
            }
        };

        //heartbeat
        mTimer = new Timer(true);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MSG_TIMER;
                mCountDownHandler.sendMessage(msg);
            }
        };
        mTimer.schedule(mTimerTask, 0, 100);
    }

    public static GameFragment newInstance(int _difficulty, Uri _imageUri) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_DIFFICULTY,_difficulty);
        args.putParcelable(EXTRA_IMAGE_URI,_imageUri);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment,container,false);
        mPuzzleView = new PuzzleView(getContext(),mDifficulty,mImageUri,GameFragment.this);
        ((FrameLayout) v).addView(mPuzzleView,0); //addView(View, layerIndex), layerIndex control which one is on top

        int textSize = 33;
        mTvCountDown = new TextView(getContext());
        mTvCountDown.setTextSize(textSize);
        mTvCountDown.setText("" + countDownS + ":" + countDownMS);
        mTvCountDown.setBackgroundResource(R.drawable.time);
        mTvCountDown.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams lpCountDown = new FrameLayout.LayoutParams(mPuzzleView.getmTimeBgWidth(), mPuzzleView.getmTimeBgHeight());
        lpCountDown.setMargins(mPuzzleView.getTimeBgOffsetX(), mPuzzleView.getTmerBgOffsetY(), 0, 0);
        ((FrameLayout) v).addView(mTvCountDown,1,lpCountDown);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTimer.cancel();
    }
}
