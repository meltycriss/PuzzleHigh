package com.example.zsystudio.puzzlehigh.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;

public class MainActivity extends SingleFragmentActivity {
    private final String TAG = "MainActivity";

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","tets");
    }
}
