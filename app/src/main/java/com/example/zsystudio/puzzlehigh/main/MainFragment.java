package com.example.zsystudio.puzzlehigh.main;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.about.AboutActivity;
import com.example.zsystudio.puzzlehigh.data.User;
import com.example.zsystudio.puzzlehigh.game.GameActivity;
import com.example.zsystudio.puzzlehigh.login.LoginActivity;
import com.example.zsystudio.puzzlehigh.rank.RankActivity;
import com.example.zsystudio.puzzlehigh.register.RegisterActivity;
import com.example.zsystudio.puzzlehigh.select_image.SelectImageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "MainFragment";

    private Button mBtnStart, mBtnLogin, mBtnRegister, mBtnRank, mBtnSetting, mBtnAbout, mBtnLogout;
    private TextView mTvUsername;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //User.getInstance().init(getContext());
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
        mTvUsername = (TextView) v.findViewById(R.id.main_username);
        mBtnLogout = (Button) v.findViewById(R.id.main_logout);
        if (User.getInstance().isLogin()) {
            mBtnLogin.setVisibility(View.INVISIBLE);
            mBtnRegister.setVisibility((View.INVISIBLE));
            mTvUsername.setVisibility(View.VISIBLE);
            mTvUsername.setText(User.getInstance().getUserName());
            mBtnLogout.setVisibility(View.VISIBLE);
            mBtnLogout.setOnClickListener(MainFragment.this);
        }
        else{
            mBtnLogin.setVisibility(View.VISIBLE);
            mBtnRegister.setVisibility((View.VISIBLE));
            mTvUsername.setVisibility(View.INVISIBLE);
            mBtnLogout.setVisibility(View.INVISIBLE);

        }
        mBtnLogin.setOnClickListener(MainFragment.this);
        mBtnRegister.setOnClickListener(MainFragment.this);
        mBtnStart.setOnClickListener(MainFragment.this);
        mBtnAbout.setOnClickListener(MainFragment.this);
        mBtnRank.setOnClickListener(MainFragment.this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_login: {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.main_register: {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.main_start: {
                Intent intent = new Intent(getContext(), SelectImageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.main_about:{
                AboutActivity.actionStart(getContext());
                break;
            }
            case R.id.main_rank:{
                RankActivity.actionStart(getContext());
                break;
            }
            case R.id.main_logout:{
                User.getInstance().setIsLogin(false);
                User.getInstance().save(getContext());
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
        }
    }
}
