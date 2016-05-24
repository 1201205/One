package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.DateBean;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.music.Music;
import com.hyc.zhihu.beans.music.MusicRelate;
import com.hyc.zhihu.beans.music.MusicRelateListBean;
import com.hyc.zhihu.ui.MainActivity;
import com.hyc.zhihu.ui.PictureActivity;
import com.hyc.zhihu.widget.ListViewForScrollView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MusicAdapter extends PagerAdapter {
    private String mCurrentId;
    private List<Music> viewBeans;
    private Context mContext;
    private List<MusicRelateListBean> mRelateLists;
    private List<CommentAdapter> mAdapters;

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    private OnLoadMoreListener mLoadMoreListener;

    @Override
    public int getCount() {
        return viewBeans == null ? 0 : viewBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public MusicAdapter(List<MusicRelateListBean> relateBeans, List<Music> viewBean, Context context) {
        super();
        this.viewBeans = viewBean;
        this.mContext = context;
        this.mRelateLists = relateBeans;
        mAdapters = new ArrayList<>();
        for (int i = 0; i < relateBeans.size(); i++) {
            mAdapters.add(new CommentAdapter(mContext));
        }
    }

    @Override
    public int getItemPosition(Object object) {
        View v = (View) object;
        if (mCurrentId.equals(v.getTag())) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
//        int delay=0;
//        if (mRefreshIndex==position) {
//            mRefreshIndex=-1;
//            delay=50;
//        }
        View view;
        Music music = viewBeans.get(position);
        if (position == viewBeans.size() - 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.date_list, null);
            ListView listView = (ListView) view.findViewById(R.id.date_list);
            listView.setAdapter(new DateAdapter(getDateBeans(), mContext));
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_question, null);
            ListView listView = (ListView) view.findViewById(R.id.swipe_target);
            SwipeToLoadLayout swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
            mRelateLists.get(position).setLayout(swipeToLoadLayout);
            View mHeader = LayoutInflater.from(mContext).inflate(R.layout.music_header, null);
            ImageView musicIV = (ImageView) mHeader.findViewById(R.id.music_iv);
            Picasso.with(mContext).load(music.getCover()).fit().into(musicIV);
            ImageView headIV = (ImageView) mHeader.findViewById(R.id.head_iv);
            Picasso.with(mContext).load(music.getAuthor().getWeb_url()).into(headIV);
            TextView mAuthorTV = (TextView) mHeader.findViewById(R.id.name_tv);
            mAuthorTV.setText(music.getAuthor().getUser_name());
            TextView desTV = (TextView) mHeader.findViewById(R.id.des_tv);
            desTV.setText(music.getAuthor().getDesc());
            TextView musicTitleTV = (TextView) mHeader.findViewById(R.id.music_title_tv);
            musicTitleTV.setText(music.getTitle());
            TextView timeTV = (TextView) mHeader.findViewById(R.id.time_tv);
            timeTV.setText("May 23.2016");
            TextView titleTV = (TextView) mHeader.findViewById(R.id.title_tv);
            titleTV.setText(music.getStory_title());
            TextView authorNameTV = (TextView) mHeader.findViewById(R.id.author_name_tv);
            authorNameTV.setText(music.getStory_author().getUser_name());
            final LinearLayout contentLL= (LinearLayout) mHeader.findViewById(R.id.content_ll);
            final TextView contentTV = (TextView) mHeader.findViewById(R.id.content_tv);
            contentTV.setText(Html.fromHtml(music.getStory()));
            final TextView lyricTV = (TextView) mHeader.findViewById(R.id.lyric_tv);
            lyricTV.setText(music.getLyric());
            final TextView infoTV = (TextView) mHeader.findViewById(R.id.info_tv);
            infoTV.setText(music.getInfo());
            TextView editorTV = (TextView) mHeader.findViewById(R.id.editor_tv);
            editorTV.setText(music.getCharge_edt());
            TextView likeNumTV = (TextView) mHeader.findViewById(R.id.like_num_tv);
            likeNumTV.setText(music.getPraisenum() + "");
            TextView commentNumTV = (TextView) mHeader.findViewById(R.id.comment_num_tv);
            commentNumTV.setText(music.getCommentnum() + "");
            TextView shareNumTV = (TextView) mHeader.findViewById(R.id.share_num_tv);
            shareNumTV.setText(music.getSharenum() + "");
            List<MusicRelate> musicRelates = mRelateLists.get(position).getMusics();
            List<Comment> comments = mRelateLists.get(position).getHotComment();
            if (musicRelates != null && musicRelates.size() > 0) {
                RecyclerView r = (RecyclerView) mHeader.findViewById(R.id.relate_rv);
                MusicRelateAdapter a = new MusicRelateAdapter(mContext, musicRelates);
                r.setAdapter(a);
                LinearLayoutManager m = new LinearLayoutManager(mContext);
                m.setOrientation(LinearLayoutManager.HORIZONTAL);
                r.setLayoutManager(m);
            } else {
                View v = mHeader.findViewById(R.id.relate_ll);
                v.setVisibility(View.GONE);
            }
            if (comments != null && comments.size() > 0) {
                ListViewForScrollView hotCommentsLV = (ListViewForScrollView) mHeader.findViewById(R.id.hot_lv);
                CommentAdapter adapter = new CommentAdapter(mContext);
                hotCommentsLV.setAdapter(adapter);
                adapter.refreshComments(comments);
            }
            if (mLoadMoreListener != null) {
                swipeToLoadLayout.setOnLoadMoreListener(new com.aspsine.swipetoloadlayout.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        mLoadMoreListener.loadMore(position, mRelateLists.get(position).getLastIndex());
                    }
                });
            }

//            mAuthorHeaderIV = (CircleImageView) mHeader.findViewById(R.id.author_head_iv);
//            mHeaderIV = (CircleImageView) mHeader.findViewById(R.id.head_iv);
//            mAuthorNameTV = (TextView) mHeader.findViewById(R.id.author_name_tv);
//            mRelateLV = (ListViewForScrollView) mHeader.findViewById(R.id.relate_lv);
//            mRelateLL = (LinearLayout) mHeader.findViewById(R.id.relate_ll);
            listView.addHeaderView(mHeader);
            listView.setAdapter(mAdapters.get(position));
            final ImageView storyIV= (ImageView) mHeader.findViewById(R.id.story_iv);
            ImageView lyricIV= (ImageView) mHeader.findViewById(R.id.lyric_iv);
            final ImageView infoIV= (ImageView) mHeader.findViewById(R.id.info_iv);
//            swipeToLoadLayout.setOnLoadMoreListener(this);
            storyIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contentLL.getVisibility()!=View.VISIBLE) {
                        contentLL.setVisibility(View.VISIBLE);
                        lyricTV.setVisibility(View.GONE);
                        infoTV.setVisibility(View.GONE);
                    }
                }
            });
            lyricIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lyricTV.getVisibility()!=View.VISIBLE) {
                        lyricTV.setVisibility(View.VISIBLE);
                        contentLL.setVisibility(View.GONE);
                        infoTV.setVisibility(View.GONE);
                    }
                }
            });
            infoIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (infoTV.getVisibility()!=View.VISIBLE) {
                        infoTV.setVisibility(View.VISIBLE);
                        lyricTV.setVisibility(View.GONE);
                        contentLL.setVisibility(View.GONE);
                    }
                }
            });
            view.setTag(music.getId());
        }

        ((ViewPager) container).addView(view);
        return view;

    }

    public void setRelate(int page, List<MusicRelate> musicRelates) {
        mCurrentId = mRelateLists.get(page).getId();
        mRelateLists.get(page).setHasRequested(true);
        mRelateLists.get(page).setMusics(musicRelates);
        if (musicRelates.size() > 0) {
            notifyDataSetChanged();
        }
    }

    public boolean neeRequest(int page) {
        return !mRelateLists.get(page).hasRequested();
    }

    public void setComment(int page, List<Comment> hot, List<Comment> normal) {
        mCurrentId = mRelateLists.get(page).getId();
        mRelateLists.get(page).setHasRequested(true);
        mRelateLists.get(page).setHotComment(hot);
        String index = null;
        if (normal.size() > 0) {
            index = normal.get(normal.size() - 1).getId();
        } else {
            index = hot.get(hot.size() - 1).getId();
        }
        mRelateLists.get(page).setLastIndex(index);
        mAdapters.get(page).refreshComments(normal);
        notifyDataSetChanged();
    }

    public void refreshComment(int page, List<Comment> comments) {
        mCurrentId = mRelateLists.get(page).getId();
        mAdapters.get(page).refreshComments(comments);
        String index = null;
        if (comments.size() > 0) {
            index = comments.get(comments.size() - 1).getId();
        }
        mRelateLists.get(page).setLastIndex(index);
        MusicRelateListBean bean = mRelateLists.get(page);

        SwipeToLoadLayout v = bean.getLayout();
        if (v != null) {
            v.setLoadingMore(false);
        }
    }

    private View.OnClickListener getOnclickListener(final String title, final String url, final View vol) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityOptionsCompat compat=  ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) mContext,new Pair<View, String>(v,PictureActivity.SHARE_TITLE),new Pair<View, String>(vol,PictureActivity.SHARE_PICTURE));

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) mContext, v, PictureActivity.SHARE_PICTURE);
                Intent intent = PictureActivity.newIntent(mContext, url, title);
//                ((MainActivity) mContext).getWindow().setSharedElementEnterTransition(new ChangeImageTransform(mContext, null));
                ActivityCompat.startActivity((MainActivity) mContext, intent, compat.toBundle());
//                mContext.startActivity(intent, compat.toBundle());
            }
        };
    }


    public void setCurrentItem(String id, OnePictureData data) {
//        Log.e("test1", "显示信息");
//        mCurrentId = id;
//        for (int i = 0; i < viewBeans.size(); i++) {
//            if (viewBeans.get(i).id.equals(id)) {
//                viewBeans.get(i).data = data;
//                viewBeans.get(i).state = PictureViewBean.NORMAL;
//                Log.e("test1", "notifyDataSetChanged");
//                DelayHandle.delay(150, new Runnable() {
//                    @Override
//                    public void run() {
//                       notifyDataSetChanged();
//                    }
//                });
//                break;
//            }
//        }

    }

    private int mCurrentPage;

    public void setCurrentPage(int page) {
        mCurrentPage = page;
    }

    private List<DateBean> getDateBeans() {
        List<DateBean> dateBeans = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        GregorianCalendar temp = new GregorianCalendar(2016, 1, 1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        while (temp.before(calendar)) {
            String s = format.format(temp.getTime());
            dateBeans.add(new DateBean(s, s + "%2000:00:00"));
            temp.add(GregorianCalendar.MONTH, 1);
        }
        Collections.reverse(dateBeans);
        dateBeans.get(0).date = "本月";
        return dateBeans;
    }

    public static void main(String[] args) {
        GregorianCalendar calendar = new GregorianCalendar();
        GregorianCalendar temp = new GregorianCalendar(2012, 9, 1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        while (temp.before(calendar)) {
            System.out.println(format.format(temp.getTime()));
            temp.add(GregorianCalendar.MONTH, 1);
        }

    }
    public void clear(){
        viewBeans.clear();
        mAdapters.clear();
        mRelateLists.clear();
    }
    public interface OnLoadMoreListener {
        void loadMore(int page, String lastIndex);
    }
}
