package com.hyc.zhihu.net;

import com.hyc.zhihu.beans.HeadItems;
import com.hyc.zhihu.beans.OnePicture;
import com.hyc.zhihu.beans.OnePictureByMonth;
import com.hyc.zhihu.beans.OnePictureList;
import com.hyc.zhihu.beans.Readings;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ray on 16/4/19.
 */
public interface Api {
    //获取one图片  后面是id
    //http://v3.wufazhuce.com:8000/api/hp/detail/1334
    //获取前面8个图片id
    //http://v3.wufazhuce.com:8000/api/hp/idlist/0
    //获取每个月份的图片 从2012-10-1开始
    //http://v3.wufazhuce.com:8000/api/hp/bymonth/2016-05-01%2000:00:00
    //获取阅读的轮播
    //http://v3.wufazhuce.com:8000/api/reading/carousel/
    //获取轮播图片与实体
    //http://v3.wufazhuce.com:8000/api/reading/carousel/pv/73
    //轮播点击时候发送的请求 返回轮播对应的每一个内容
    //http://v3.wufazhuce.com:8000/api/reading/carousel/73
    //获取1396的文章
    //http://v3.wufazhuce.com:8000/api/essay/1396
    //获取1335的问题
    //http://v3.wufazhuce.com:8000/api/question/1335?
    //获取连载的内容
    //http://v3.wufazhuce.com:8000/api/serialcontent/104?
    //获取阅读list
    //http://v3.wufazhuce.com:8000/api/reading/index/0?
    @GET("/api/hp/idlist/{id}")
    Observable<OnePictureList> getPictureIds(@Path("id") String id );
    @GET("/api/hp/detail/{id}")
    Observable<OnePicture> getPictureById(@Path("id") String id );
    @GET("/api/hp/bymonth/{date}")
    Observable<OnePictureByMonth> getPictureByMonth(@Path("date") String date );
    @GET("/api/reading/carousel/")
    Observable<HeadItems> getScrollHeads();
    @GET("/api/reading/index/{index}")
    Observable<Readings> getReadingList(@Path("index") int index );

}
