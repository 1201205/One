package com.hyc.zhihu.helper;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/7/26.
 */
public class PicassoHelper {

    public static void load(Context context, String url, ImageView view, int holder) {
        if (TextUtils.isEmpty(url)) {
            Picasso.with(context).load(holder).into(view);
        } else {
            Picasso.with(context).load(url).placeholder(holder).fit().into(view);
        }
    }

    public static void load(Context context, String url, ImageView view) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load(url).fit().into(view);
        }
    }
}
