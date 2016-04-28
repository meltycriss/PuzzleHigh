package com.example.zsystudio.puzzlehigh.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zsystudio.puzzlehigh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final String EXTRA_IS_LOGIN = "com.example.zsystudio.puzzlehigh.main.isLogined";

    private boolean isLogin;

    private Button mBtnStart, mBtnLogin, mBtnRegister, mBtnRank, mBtnSetting, mBtnAbout;

    public static MainFragment newInstance(boolean _isLogin) {
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_IS_LOGIN, _isLogin);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = getArguments().getBoolean(EXTRA_IS_LOGIN);
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
        return v;
    }
}
