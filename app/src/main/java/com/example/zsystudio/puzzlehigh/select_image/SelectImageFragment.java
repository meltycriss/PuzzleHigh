package com.example.zsystudio.puzzlehigh.select_image;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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

    private LinearLayout mLayoutLoadMore;
    private LinearLayout mLayoutGoBack;
    private Button mBtnLoadLocalPic;
    private Button mBtnLoadNetPIc;
    private Button mBtnGoBack;


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

        mLayoutLoadMore = (LinearLayout) v.findViewById(R.id.layout_load_more);
        mLayoutGoBack = (LinearLayout) v.findViewById(R.id.layout_go_back);

        mBtnLoadNetPIc = (Button) v.findViewById(R.id.btn_load_net_pic);
        mBtnLoadLocalPic = (Button) v.findViewById(R.id.btn_load_local_pic);
        mBtnGoBack = (Button) v.findViewById(R.id.btn_go_back);
        mBtnGoBack.setOnClickListener(this);
        mBtnLoadLocalPic.setOnClickListener(this);
        mBtnLoadNetPIc.setOnClickListener(this);

        mPresenter.start();

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_go_back) {
            currentState = STATE_NATIVE;
            mRclist.swapAdapter(mNativeAdapter, false);
            mLayoutLoadMore.setVisibility(View.VISIBLE);
            mLayoutGoBack.setVisibility(View.GONE);
        } else if (id == R.id.btn_load_net_pic) {
            mPresenter.getNetPicList();
        } else if (id == R.id.btn_load_local_pic) {
            mPresenter.getLocalPic();
        }
    }

    @Override
    public void showNativePicList() {

//        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRclist.setLayoutManager(mLayoutManager);
        mRclist.setHasFixedSize(true);

        mRclist.setItemAnimator(new DefaultItemAnimator());
        mRclist.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) % 2 == 0) {
                    outRect.left = 50;
                    outRect.right = 10;
                } else {
                    outRect.right = 30;
                    outRect.left = 10;
                }
                if (!(parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1))
                    outRect.top = 20;
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

        mLayoutLoadMore.setVisibility(View.VISIBLE);
        mLayoutGoBack.setVisibility(View.GONE);
    }

    @Override
    public void showNetPicList(final ArrayList<ImageItem> arrayList) {

        netPicList = arrayList;
        mNetAdapter = new RecyclerAdapter(getContext(), arrayList);
        mRclist.swapAdapter(mNetAdapter, false);
        mNetAdapter.setOnItemClickListener(this);

        currentState = STATE_NET;

        mLayoutLoadMore.setVisibility(View.GONE);
        mLayoutGoBack.setVisibility(View.VISIBLE);
    }


    @Override
    public void onItemClick(View view, int position) {
        if (currentState == STATE_NATIVE) {

//            Toast.makeText(getContext(), "testpic01" + position, Toast.LENGTH_SHORT).show();

            Uri uri = Uri.parse("android.resource://com.example.zsystudio.puzzlehigh/" + nativePicList.get(position).getId());
            Log.d("My_uri", uri.toString());
            GameActivity.actionStart(getContext(), GameActivity.REMOTE, uri);

        } else if (currentState == STATE_NET) {

//            Toast.makeText(getContext(), netPicList.get(position).getText(), Toast.LENGTH_SHORT).show();

            mPresenter.imageDownload(getContext(), netPicList.get(position), new SelectImagePresenter.DownloadCallback() {
                @Override
                public void onDownloadFinish(Uri uri) {

                    Log.d("My_uri", uri.toString());
                    GameActivity.actionStart(getContext(), GameActivity.REMOTE, uri);
                }
            });


        }
    }
}

