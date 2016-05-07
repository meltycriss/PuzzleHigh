package com.example.zsystudio.puzzlehigh.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;


public class GameActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        mDifficulty = getIntent().getIntExtra(EXTRA_DIFFICULTY, 3);
        return GameFragment.newInstance(mDifficulty);
    }

    public static final String EXTRA_DIFFICULTY = "GameActivity.difficulty";

    private int mDifficulty;

    public static void actionStart(Context _context, int _difficulty){
        Intent intent = new Intent(_context,GameActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY,_difficulty);
        _context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
