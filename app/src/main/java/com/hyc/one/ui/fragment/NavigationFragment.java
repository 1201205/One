package com.hyc.one.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyc.one.R;
import com.hyc.one.ui.MusicActivity;
import com.hyc.one.ui.ReadingActivity;
import com.hyc.one.ui.RecyclerListActivity;
import com.hyc.one.utils.AppUtil;
import com.hyc.one.utils.S;

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
                AppUtil.startActivityWithType(S.MOVIE_LIST, getContext(), RecyclerListActivity.class, R.string.movie);
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
