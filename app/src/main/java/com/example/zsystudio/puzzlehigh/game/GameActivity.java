package com.example.zsystudio.puzzlehigh.game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.util.SingleFragmentActivity;


public class GameActivity extends SingleFragmentActivity {
    public static final int LOCAL = 1;
    public static final int REMOTE = 2;

    public static final String EXTRA_SOURCE = "GameActivity.source";
    public static final String EXTRA_IMAGE_URI = "GameActivity.image_uri";

    private int mSource;
    private Uri mImageUri;

    @Override
    protected Fragment createFragment() {
        mSource = getIntent().getIntExtra(EXTRA_SOURCE,LOCAL);
        //mImageUri = Uri.parse(getIntent().getStringExtra(EXTRA_IMAGE_URI));
        mImageUri = getIntent().getParcelableExtra(EXTRA_IMAGE_URI);
        return GameFragment.newInstance(2,mImageUri);
    }

    public static void actionStart(Context _context, int _source, Uri _imageUri){
        Intent intent = new Intent(_context,GameActivity.class);
        intent.putExtra(EXTRA_SOURCE,_source);
        //intent.putExtra(EXTRA_IMAGE_URI,_imageUri.toString());
        intent.putExtra(EXTRA_IMAGE_URI,_imageUri);
        _context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
