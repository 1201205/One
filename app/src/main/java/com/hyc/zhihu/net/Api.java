package com.hyc.zhihu.net;

import com.hyc.zhihu.beans.Comments;
import com.hyc.zhihu.beans.QuestionContent;
import com.hyc.zhihu.beans.QuestionWrapper;
import com.hyc.zhihu.beans.Questions;
import com.hyc.zhihu.beans.ReadingListItems;
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
    Observable<OnePictureList> getPictureIds(@Path("id") String id);

    @GET("/api/hp/detail/{id}")
    Observable<OnePicture> getPictureById(@Path("id") String id);

    @GET("/api/hp/bymonth/{date}")
    Observable<OnePictureByMonth> getPictureByMonth(@Path("date") String date);

    @GET("/api/reading/carousel/")
    Observable<HeadItems> getScrollHeads();

    @GET("/api/reading/index/{index}")
    Observable<Readings> getReadingList(@Path("index") int index);

    @GET("/api/reading/carousel/{id}")
    Observable<ReadingListItems> getEssayListByID(@Path("id") String id);
//    @GET("/api/reading/carousel/")
//    Observable<ReadingListItems> getEssayList();
    //点击问题需要发送的请求有：

    /**
     * 点击问题需要发送的请求有：
     * http://v3.wufazhuce.com:8000/api/question/1356  查看问题内容
     * http://v3.wufazhuce.com:8000/api/question/update/1356/2016-05-18%2016:41:51?  更新问题的评论数量和查看数量
     * http://v3.wufazhuce.com:8000/api/related/question/1356  查看与之相关的推荐
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/question/1356/0 评论列表
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/question/1356/16697 在16697后面的评论
     * <p/>
     * 点击连载需要发送的请求：
     * http://v3.wufazhuce.com:8000/api/serialcontent/111  查看连载内容
     * http://v3.wufazhuce.com:8000/api/serialcontent/update/111/2016-05-12%2017:59:40 更新连载的评论数量和查看数量
     * http://v3.wufazhuce.com:8000/api/related/serial/111 查看与之相关推荐
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/serial/111/0 评论列表
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/serial/111/15635
     * 点击短篇需要发送的请求：
     * http://v3.wufazhuce.com:8000/api/essay/1411 内容
     * http://v3.wufazhuce.com:8000/api/essay/update/1411/2016-05-17%2020:30:47 更新
     * http://v3.wufazhuce.com:8000/api/related/essay/1411 推荐
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/essay/1411/0 评论
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/essay/1411/14582 评论
     */
    @GET("/api/question/{id}")
    Observable<QuestionWrapper> getQuestionContentByID(@Path("id") String id);

    @GET("/api/related/question/{id}")
    Observable<Questions> getQuestionRelateByID(@Path("id") String id);

    @GET("/api/comment/praiseandtime/question/{id}/{index}")
    Observable<Comments> getQuestionCommentsByIndex(@Path("id") String id, @Path("index") String index);
}
