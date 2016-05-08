package com.example.zsystudio.puzzlehigh.game;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.zsystudio.puzzlehigh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameSettingFragment extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    public static final String TAG = "GameSettingFragment";
    public static final String EXTRA_IMAGE_URI = "GameSettingFragment.image_uri";
    public static final String EXTRA_SOURCE = "GameSettingFragment.source";

    private int mSource; //where the image uri from, local or remote, deciding whether showing the uploading switch
    private Uri mImageUri; //image uri

    private Spinner mSpinnerDifficulty;
    private Switch mSwitchIsUpload;
    private ImageView mIvImage;
    private Button mBtnStart;

    private int mDifficulty;
    private boolean mIsUpload;

    public static GameSettingFragment newInstance(int _source, Uri _imageUri) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_IMAGE_URI, _imageUri);
        args.putInt(EXTRA_SOURCE, _source);
        GameSettingFragment fragment = new GameSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSource = getArguments().getInt(EXTRA_SOURCE);
        mImageUri = getArguments().getParcelable(EXTRA_IMAGE_URI);
        mDifficulty = 3;
        mIsUpload = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_setting, container, false);
        //spinner for difficulty
        mSpinnerDifficulty = (Spinner) v.findViewById(R.id.game_setting_difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.game_setting_difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDifficulty.setAdapter(adapter);
        mSpinnerDifficulty.setOnItemSelectedListener(GameSettingFragment.this);
        mSpinnerDifficulty.setSelection(adapter.getPosition(String.valueOf(mDifficulty)));

        //switch for upload
        mSwitchIsUpload = (Switch) v.findViewById(R.id.game_setting_is_upload);
        mSwitchIsUpload.setOnCheckedChangeListener(GameSettingFragment.this);
        mSwitchIsUpload.setChecked(mIsUpload);
        if(mSource == GameActivity.REMOTE) mSwitchIsUpload.setVisibility(View.INVISIBLE);

        //preview the image
        mIvImage = (ImageView) v.findViewById(R.id.game_setting_image);
        mIvImage.setImageURI(mImageUri);

        //game launcher
        mBtnStart = (Button) v.findViewById(R.id.game_setting_start);
        mBtnStart.setOnClickListener(GameSettingFragment.this);
        return v;
    }

    //spinner control
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mDifficulty = Integer.valueOf((String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //switch control
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.game_setting_is_upload: {
                if (isChecked) {
                    mIsUpload = true;
                } else {
                    mIsUpload = false;
                }
                break;
            }
            default:
                break;
        }
    }

    //game launcher control
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_setting_start: {
                GameFragment fragment = GameFragment.newInstance(mDifficulty,mImageUri);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, GameFragment.TAG)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            default:
                break;
        }
    }
}
