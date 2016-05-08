package com.example.zsystudio.puzzlehigh.game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;


public class GameActivity extends SingleFragmentActivity {
    public static final int LOCAL = 1;
    public static final int REMOTE = 2;

    public static final String EXTRA_SOURCE = "GameActivity.source";
    public static final String EXTRA_IMAGE_URI = "GameActivity.image_uri";

    private int mSource; //whether the uri from local or remote
    private Uri mImageUri; //image uri

    @Override
    protected Fragment createFragment() {
        mSource = getIntent().getIntExtra(EXTRA_SOURCE,LOCAL);
        mImageUri = getIntent().getParcelableExtra(EXTRA_IMAGE_URI);
        return GameSettingFragment.newInstance(mSource,mImageUri);
    }

    public static void actionStart(Context _context, int _source, Uri _imageUri){
        Intent intent = new Intent(_context,GameActivity.class);
        intent.putExtra(EXTRA_SOURCE,_source);
        intent.putExtra(EXTRA_IMAGE_URI,_imageUri);
        _context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
