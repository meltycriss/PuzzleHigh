package com.example.zsystudio.puzzlehigh.select_image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoyt on 16-5-4.
 */
public class SelectImageFragment extends Fragment implements SelectImageContract.View, View.OnClickListener {

    private SelectImageContract.Presenter mPresenter;

    private RecyclerView mRclist;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ImageItem> mData = new ArrayList<ImageItem>();

    private Button loadmore;
    private PopupMenu popupMenu;

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (SelectImageContract.Presenter) presenter;
    }

    public static SelectImageFragment newInstance() {
        Bundle args = new Bundle();
        SelectImageFragment fragment = new SelectImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_select_image, container, false);

        mRclist = (RecyclerView) getActivity().findViewById(R.id.rc_list);

        loadmore = (Button) getActivity().findViewById(R.id.load_more);
        loadmore.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.load_more) {
            popupMenu.show();
        }
    }

    @Override
    public void createPopupMenu() {

        popupMenu = new PopupMenu(getActivity(), loadmore);
        popupMenu.getMenuInflater().inflate(R.menu.loadmore_pic, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.native_pic) {
//                    Intent intent = new Intent(getActivity(), SelectPictureActivity.class);
//                    intent.putExtra("mode", 1);
//                    startActivity(intent);
                } else {
//                    Intent intent = new Intent(PictureListActivity.this, NetPictureListActivity.class);
//                    intent.putExtra("mode", 2);
//                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public void showNativePicList() {

        mLayoutManager = new LinearLayoutManager(getActivity());
//        mLayoutManager = new GridLayoutManager(this, 2);
        mRclist.setLayoutManager(mLayoutManager);
        mRclist.setHasFixedSize(true);

        mRclist.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 6; i++) {
            ImageItem imageItem = new ImageItem();
            imageItem.setId(R.drawable.main_logo);
            mData.add(imageItem);
        }
        mAdapter = new RecyclerAdapter(getActivity(), mData);
        mRclist.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(
                new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, int position) {
                        Toast.makeText(getActivity(), "test" + position, Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(getActivity(), SelectPictureActivity.class);
//                        intent.putExtra("mode", 0);
//                        intent.putExtra("position", position);
//                        startActivity(intent);
                    }
                });
    }
}

