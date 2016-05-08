package com.example.zsystudio.puzzlehigh.game;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.util.IOUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements PuzzleView.GameOverCallBack{
    private final String TAG = "GameFragment";

    public static final String EXTRA_DIFFICULTY = "GameFragment.difficulty";
    public static final String EXTRA_IMAGE_URI = "GameFragment.image_uri";

    public static final int MSG_TIMER = 75532;
    public static final int MSG_GAME_CHECKOUT = 75533;

    private Handler mCheckoutHandler;
    private Handler mCountDownHandler;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private TextView mTvCountDown;
    private PuzzleView mPuzzleView;

    int countDownS = 200;
    int countDownMS = 00;
    int GameStatus = PuzzleView.GAME_ON;

    private int mDifficulty;
    private Uri mImageUri;

    @Override
    public void onGameOver() {
        GameStatus = PuzzleView.GAME_OVER;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDifficulty = getArguments().getInt(EXTRA_DIFFICULTY);
        //mImageUri = Uri.parse(getArguments().getString(EXTRA_IMAGE_URI));
        mImageUri = getArguments().getParcelable(EXTRA_IMAGE_URI);
//        mImageUri = Uri.parse("android.resource://"+getContext().getPackageName()+"/drawable/testpic");

        mCheckoutHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_GAME_CHECKOUT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("游戏结束");
                    builder.setMessage("你的得分：" + countDownS);
                    builder.show();
                }
            }
        };

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
                        mTvCountDown.setTextSize(28);
                        GameStatus = PuzzleView.GAME_OVER;
                        mTvCountDown.setText("游戏结束");
                    }
                }
                if (GameStatus == PuzzleView.GAME_OVER) {
                    Message msg2 = new Message();
                    msg2.what = MSG_GAME_CHECKOUT;
                    mCheckoutHandler.sendMessage(msg2);
                    GameStatus = PuzzleView.GAME_CHECKOUT;
                }
            }
        };

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
        //args.putString(EXTRA_IMAGE_URI,_imageUri.toString());
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment,container,false);
        mPuzzleView = new PuzzleView(getContext(),mDifficulty,mImageUri,GameFragment.this);
        ((FrameLayout) v).addView(mPuzzleView,0); //addView(View, layerIndex)

        mTvCountDown = new TextView(getContext());
        mTvCountDown.setTextSize(33);
        mTvCountDown.setText("" + countDownS + ":" + countDownMS);
        FrameLayout.LayoutParams lpCountDown = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpCountDown.setMargins(500, 130, 0, 0);
        ((FrameLayout) v).addView(mTvCountDown,1,lpCountDown);
        return v;
    }

}
