package com.hyc.zhihu.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.ui.MovieListActivity;
import com.hyc.zhihu.ui.MusicActivity;
import com.hyc.zhihu.ui.OtherMusicActivity;
import com.hyc.zhihu.ui.ReadingActivity;
import com.hyc.zhihu.ui.adpter.ColorAdapter;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.utils.SPUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/29.
 */
public class NavigationFragment extends Fragment {
    private TextView mMusicTV, mMoveTV, mReadTV;
    private View mMainBg;
//    private RecyclerView mColorGV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment, null);
//        mHomeTV = (TextView) view.findViewById(R.id.home_tv);
        mMusicTV = (TextView) view.findViewById(R.id.music_tv);
        mMoveTV = (TextView) view.findViewById(R.id.movie_tv);
        mReadTV = (TextView) view.findViewById(R.id.read_tv);
//        mColorGV = (RecyclerView) view.findViewById(R.id.color_rv);
        mMainBg = view.findViewById(R.id.main_ll);
        //mMainBg.setBackgroundColor(SPUtil.get( S.THEME, AppUtil.getColor(R.color.google_blue)));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.parseColor("#4285F4"));
//        colors.add(Color.parseColor("#EA4335"));
//        colors.add(Color.parseColor("#34A853"));
//        colors.add(Color.parseColor("#FBBC05"));
//        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
//        ColorAdapter colorAdapter = new ColorAdapter(colors);
//        colorAdapter.setItemClickListener(new ColorAdapter.ItemClickListener() {
//            @Override
//            public void OnItemClicked(int color) {
//                SPUtil.put(S.THEME, color);
//                mMainBg.setBackgroundColor(color);
//                ((BaseActivity) getActivity()).changeColor();
//            }
//        });
//        mColorGV.setLayoutManager(manager);
//        mColorGV.setAdapter(colorAdapter);
        mMusicTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MusicActivity.class));
            }
        });
        mMoveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.startActivityWithType(S.MOVIE_LIST,getContext(), OtherMusicActivity.class,R.string.movie);
            }
        });
        mReadTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReadingActivity.class));
            }
        });
    }
}
