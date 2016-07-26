package com.hyc.one.ui.adpter;

import android.content.Context;
import android.view.View;

import com.hyc.one.R;
import com.hyc.one.base.BaseRecyclerAdapter;
import com.hyc.one.base.RecyclerViewHolder;
import com.hyc.one.beans.OtherDiary;
import com.hyc.one.ui.PictureActivity;
import com.hyc.one.utils.AppUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class OtherDairyAdapter extends BaseRecyclerAdapter<OtherDiary> {
    private static HashMap<String, Integer> mBackground = new HashMap<>();

    static {
        mBackground = new HashMap<>();
        mBackground.put("多云", R.drawable.weather_cloudy);
        mBackground.put("沙尘暴", R.drawable.weather_duststorm);
        mBackground.put("雾", R.drawable.weather_foggy);
        mBackground.put("小雨", R.drawable.weather_light_rain);
        mBackground.put("阴", R.drawable.weather_overcast);
        mBackground.put("晴", R.drawable.weather_sunny);
        mBackground.put("阵雨", R.drawable.weather_shower);
        mBackground.put("雾霾", R.drawable.weather_haze);
        mBackground.put("大雨", R.drawable.weather_heavy_rain);
        mBackground.put("大雪", R.drawable.weather_heavy_snow);
        mBackground.put("雨冰", R.drawable.weather_ice_rain);
        mBackground.put("小雪", R.drawable.weather_light_snow);
        mBackground.put("中雨", R.drawable.weather_moderate_rain);
        mBackground.put("雨夹雪", R.drawable.weather_sleet);
        mBackground.put("阵雪", R.drawable.weather_snow_flurry);
        mBackground.put("暴风雨", R.drawable.weather_storm);
        mBackground.put("雷阵雨", R.drawable.weather_thundershower);
    }

    public OtherDairyAdapter(Context ctx, List<OtherDiary> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.other_dairy_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final OtherDiary item) {
        holder.setText(R.id.name_tv, item.getContent()).setText(R.id.date_tv, item.getInput_date()).setImageResource(R.id.weather_iv, getID(item.getWeather())).withItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.startActivityWithUrl(item.getPicture(), v.getContext(), PictureActivity.class);
            }
        });
    }

    private int getID(String weather) {
        Integer i = mBackground.get(weather);
        if (i != null) {
            return i;
        }
        return R.drawable.weather_unknown;
    }
}
