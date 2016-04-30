package com.example.zsystudio.puzzlehigh.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private final String TAG = "MainFragment";

    private boolean isLogin;

    private Button mBtnStart, mBtnLogin, mBtnRegister, mBtnRank, mBtnSetting, mBtnAbout;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = User.isLogin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mBtnStart = (Button) v.findViewById(R.id.main_start);
        mBtnLogin = (Button) v.findViewById(R.id.main_login);
        mBtnRegister = (Button) v.findViewById(R.id.main_register);
        mBtnRank = (Button) v.findViewById(R.id.main_rank);
        mBtnSetting = (Button) v.findViewById(R.id.main_setting);
        mBtnAbout = (Button) v.findViewById(R.id.main_about);
        if(isLogin){
            mBtnLogin.setVisibility(View.INVISIBLE);
            mBtnRegister.setVisibility((View.INVISIBLE));
        }
        return v;
    }
}
