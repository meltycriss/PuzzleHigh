package com.example.zsystudio.puzzlehigh.about;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;

public class AboutActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return AboutFragment.newInstance();
    }

    public static void actionStart(Context _context){
        Intent intent = new Intent(_context,AboutActivity.class);
        _context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
