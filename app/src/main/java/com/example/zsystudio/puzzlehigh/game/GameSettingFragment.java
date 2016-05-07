package com.example.zsystudio.puzzlehigh.game;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.select_image.RecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameSettingFragment extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener,View.OnClickListener{
    public static final String EXTRA_IMAGE_URI = "GameSettingFragment.image_uri";
    public static final String EXTRA_SOURCE = "GameSettingFragment.source";

    private int mSource;
    private Uri mImageUri;

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
        mSpinnerDifficulty = (Spinner) v.findViewById(R.id.game_setting_difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.game_setting_difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDifficulty.setAdapter(adapter);
        mSpinnerDifficulty.setOnItemSelectedListener(GameSettingFragment.this);
        mSpinnerDifficulty.setSelection(adapter.getPosition(String.valueOf(mDifficulty)));

        mSwitchIsUpload = (Switch) v.findViewById(R.id.game_setting_is_upload);
        mSwitchIsUpload.setOnCheckedChangeListener(GameSettingFragment.this);
        mSwitchIsUpload.setChecked(mIsUpload);

        mIvImage = (ImageView) v.findViewById(R.id.game_setting_image);
        mIvImage.setImageURI(mImageUri);

        mBtnStart = (Button) v.findViewById(R.id.game_setting_start);
        mBtnStart.setOnClickListener(GameSettingFragment.this);

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s = (String) parent.getItemAtPosition(position);
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.game_setting_is_upload: {
                if (isChecked) {
                    mIsUpload = true;
                    Toast.makeText(getContext(), "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    mIsUpload = false;
                    Toast.makeText(getContext(), "unChecked", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.game_setting_start:{
//                Toast.makeText(getContext(), String.valueOf(mDifficulty)+"\n"+String.valueOf(mIsUpload), Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                GameResultFragment dialog =  GameResultFragment.newInstance(100,1000);
                dialog.show(fm,"game_result");
                break;
            }
            default:break;
        }

    }
}
