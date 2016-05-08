package com.example.zsystudio.puzzlehigh.select_image;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.game.GameActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoyt on 16-5-4.
 */
public class SelectImageFragment extends Fragment
        implements SelectImageContract.View,
        View.OnClickListener,
        RecyclerAdapter.OnItemClickListener {

    private SelectImageContract.Presenter mPresenter;

    private RecyclerView mRclist;
    private RecyclerAdapter mNativeAdapter, mNetAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ImageItem> nativePicList = new ArrayList<ImageItem>();
    private List<ImageItem> netPicList = new ArrayList<ImageItem>();

    private Button loadmore;
    private PopupMenu popupMenu;


    private static int STATE_NATIVE = 100;
    private static int STATE_NET = 101;
    private int currentState;

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (SelectImageContract.Presenter) presenter;
    }

    public SelectImageContract.Presenter getPresenter() {
        return mPresenter;
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

        mRclist = (RecyclerView) v.findViewById(R.id.rc_list);

        loadmore = (Button) v.findViewById(R.id.load_more);
        loadmore.setOnClickListener(this);

        mPresenter.start();

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
                    mPresenter.getLocalPic();
                } else {
                    mPresenter.getNetPicList();
                }
                return true;
            }
        });
    }

    @Override
    public void showNativePicList() {

        mLayoutManager = new LinearLayoutManager(getContext());
//        mLayoutManager = new GridLayoutManager(this, 2);
        mRclist.setLayoutManager(mLayoutManager);
        mRclist.setHasFixedSize(true);

        mRclist.setItemAnimator(new DefaultItemAnimator());
        mRclist.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) != 0)
                    outRect.top = 30;
            }
        });

        for (int i = 0; i < 6; i++) {
            ImageItem imageItem = new ImageItem();
            imageItem.setId(R.drawable.inner_image_01 + i);
            nativePicList.add(imageItem);
        }
        mNativeAdapter = new RecyclerAdapter(getContext(), nativePicList);
        mRclist.setAdapter(mNativeAdapter);
        mNativeAdapter.setOnItemClickListener(this);
        currentState = STATE_NATIVE;
        loadmore.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNetPicList(final ArrayList<ImageItem> arrayList) {

        netPicList = arrayList;
        mNetAdapter = new RecyclerAdapter(getContext(), arrayList);
        mRclist.swapAdapter(mNetAdapter, true);
        mNetAdapter.setOnItemClickListener(this);
        currentState = STATE_NET;
        loadmore.setVisibility(View.GONE);
    }


    @Override
    public void onItemClick(View view, int position) {
        if (currentState == STATE_NATIVE) {

//            Toast.makeText(getContext(), "test" + position, Toast.LENGTH_SHORT).show();

            Uri uri = Uri.parse("android.resource://com.example.zsystudio.puzzlehigh/" + nativePicList.get(position).getId());
            Log.d("My_uri", uri.toString());
            GameActivity.actionStart(getContext(), GameActivity.REMOTE, uri);

        } else if (currentState == STATE_NET) {

//            Toast.makeText(getContext(), netPicList.get(position).getText(), Toast.LENGTH_SHORT).show();

            Uri uri = mPresenter.imageDownload(getContext(), netPicList.get(position));
            Log.d("My_uri", uri.toString());
            GameActivity.actionStart(getContext(), GameActivity.REMOTE, uri);

        }
    }
}

