package com.example.zsystudio.puzzlehigh.rank;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zsystudio.puzzlehigh.login.LoginFragment;
import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;

public class RankActivity extends SingleFragmentActivity {
    private RankFragment mRankFragment;

    public static void actionStart(Context _context){
        Intent intent = new Intent(_context, RankActivity.class);
        _context.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        return mRankFragment = RankFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RankPresenter(mRankFragment);
    }
}
