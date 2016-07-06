package com.hyc.zhihu.net;

import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.beans.CommentWrapper;
import com.hyc.zhihu.beans.Comments;
import com.hyc.zhihu.beans.DateReading;
import com.hyc.zhihu.beans.Essay;
import com.hyc.zhihu.beans.HeadScrollItem;
import com.hyc.zhihu.beans.IDList;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.QuestionContent;
import com.hyc.zhihu.beans.ReadingListItem;
import com.hyc.zhihu.beans.RealArticle;
import com.hyc.zhihu.beans.Serial;
import com.hyc.zhihu.beans.SerialContent;
import com.hyc.zhihu.beans.SerialList;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.beans.movie.MovieContent;
import com.hyc.zhihu.beans.movie.MovieStoryWrapper;
import com.hyc.zhihu.beans.music.Music;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.beans.music.MusicRelate;

import java.util.List;

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
    @GET("/api/hp/idlist/{id}") Observable<IDList> getPictureIds(@Path("id") String id);

    @GET("/api/hp/detail/{id}") Observable<BaseBean<OnePictureData>> getPictureById(
        @Path("id") String id);

    @GET("/api/hp/bymonth/{date}") Observable<BaseBean<List<OnePictureData>>> getPictureByMonth(
        @Path("date") String date);

    @GET("/api/reading/carousel/") Observable<BaseBean<List<HeadScrollItem>>> getScrollHeads();

    @GET("/api/reading/index/{index}") Observable<BaseBean<List<DateReading>>> getReadingList(
        @Path("index") int index);

    @GET("/api/reading/carousel/{id}") Observable<BaseBean<List<ReadingListItem>>> getEssayListByID(
        @Path("id") String id);
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
     * http://v3.wufazhuce.com:8000/api/serialcontent/update/111/2016-05-12%2017:59:40
     * 更新连载的评论数量和查看数量
     * http://v3.wufazhuce.com:8000/api/related/serial/111 查看与之相关推荐
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/serial/111/0 评论列表
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/serial/111/15635
     * http://v3.wufazhuce.com:8000/api/serial/list/29
     * 点击短篇需要发送的请求：
     * http://v3.wufazhuce.com:8000/api/essay/1411 内容
     * http://v3.wufazhuce.com:8000/api/essay/update/1411/2016-05-17%2020:30:47 更新
     * http://v3.wufazhuce.com:8000/api/related/essay/1411 推荐
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/essay/1411/0 评论
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/essay/1411/14582 评论
     */
    @GET("/api/question/{id}") Observable<BaseBean<QuestionContent>> getQuestionContentByID(
        @Path("id") String id);

    @GET("/api/serialcontent/{id}") Observable<BaseBean<SerialContent>> getSerialContentByID(
        @Path("id") String id);

    @GET("/api/essay/{id}") Observable<BaseBean<Essay>> getEssayContentByID(@Path("id") String id);

    @GET("/api/related/question/{id}") Observable<BaseBean<List<Question>>> getQuestionRelateByID(
        @Path("id") String id);

    @GET("/api/related/serial/{id}") Observable<BaseBean<List<Serial>>> getSerialRelateByID(
        @Path("id") String id);

    @GET("/api/related/essay/{id}") Observable<BaseBean<List<RealArticle>>> getEssayRelateByID(
        @Path("id") String id);

    @GET("/api/comment/praiseandtime/question/{id}/{index}")
    Observable<BaseBean<CommentWrapper>> getQuestionCommentsByIndex(
        @Path("id") String id, @Path("index") String index);

    @GET("/api/comment/praiseandtime/serial/{id}/{index}")
    Observable<BaseBean<CommentWrapper>> getSerialCommentsByIndex(
        @Path("id") String id, @Path("index") String index);

    @GET("/api/comment/praiseandtime/essay/{id}/{index}")
    Observable<BaseBean<CommentWrapper>> getEssayCommentsByIndex(
        @Path("id") String id, @Path("index") String index);

    @GET("/api/serial/list/{id}") Observable<BaseBean<SerialList>> getSerialListByID(
        @Path("id") String id);

    /**
     * 音乐相关
     * http://v3.wufazhuce.com:8000/api/music/idlist/0  获取id
     * http://v3.wufazhuce.com:8000/api/music/detail/558  获取对应的详情
     * http://v3.wufazhuce.com:8000/api/related/music/558  获取对应的推荐
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/558/0 获取对应的评论
     * http://v3.wufazhuce.com:8000/api/music/bymonth/2016-05-01%2000:00:00?
     * 电影相关
     * http://v3.wufazhuce.com:8000/api/movie/list/0 获取id
     * http://v3.wufazhuce.com:8000/api/movie/detail/69
     * http://v3.wufazhuce.com:8000/api/movie/69/story/1/0 获取故事
     * http://v3.wufazhuce.com:8000/api/movie/69/story/0/0 获取全部故事
     * http://v3.wufazhuce.com:8000/api/comment/praiseandtime/movie/69/0
     */
    @GET("/api/music/idlist/{id}") Observable<IDList> getMusicIds(@Path("id") String id);

    @GET("/api/music/detail/{id}") Observable<BaseBean<Music>> getMusicContentByID(
        @Path("id") String id);

    @GET("/api/related/music/{id}") Observable<BaseBean<List<MusicRelate>>> getMusicRelateByID(
        @Path("id") String id);

    @GET("/api/comment/praiseandtime/music/{id}/{index}")
    Observable<BaseBean<CommentWrapper>> getMusicCommentsByIndex(
        @Path("id") String id, @Path("index") String index);

    @GET("/api/music/bymonth/{date}") Observable<BaseBean<List<MusicMonthItem>>> getMusicByMonth(
        @Path("date") String date);

    @GET("/api/movie/list/{id}") Observable<BaseBean<List<Movie>>> getMovieList(
        @Path("id") String id);

    @GET("/api/movie/detail/{id}") Observable<BaseBean<MovieContent>> getMovieContentByID(
        @Path("id") String id);

    @GET("/api/comment/praiseandtime/movie/{id}/{index}")
    Observable<Comments> getMovieCommentsByIndex(
        @Path("id") String id, @Path("index") String index);

    @GET("api/movie/{id}/story/{tag}/{index}")
    Observable<BaseBean<MovieStoryWrapper>> getMovieStoryByID(
        @Path("id") String id, @Path("tag") String tag, @Path("index") String index);

    /**
     *获取用户信息
     * http://v3.wufazhuce.com:8000/api/user/info/4744180
     * 获取用户歌单
     * 获取http://v3.wufazhuce.com:8000/api/othercenter/4744180
     * 获取用户收藏的歌单
     * http://v3.wufazhuce.com:8000/api/othercollection/4744180/more/4/0
     * 获取用户收藏的短片
     *http://v3.wufazhuce.com:8000/api/othercollection/4744180/more/1/0
     * 获取用户收藏的连载
     *http://v3.wufazhuce.com:8000/api/othercollection/4744180/more/6/0
     * 获取用户收藏的问题
     * http://v3.wufazhuce.com:8000/api/othercollection/4744180/more/2/0
     *获取用户收藏的图文
     * http://v3.wufazhuce.com:8000/api/othercollection/4744180/more/0/0
     *
     * 获取用户收藏的电影
     * http://v3.wufazhuce.com:8000/api/othercollection/4744180/more/5/0?
     *http://v3.wufazhuce.com:8000/api/othercollection/1914344/more/1/0?
     * 获取用户手记
     * http://v3.wufazhuce.com:8000/api/otherdiary/list/1914344/more/0
     *
     * 搜索相关
     * http://v3.wufazhuce.com:8000/api/search/reading/《内容》
     *  http://v3.wufazhuce.com:8000/api/search/hp/《内容》
     * http://v3.wufazhuce.com:8000/api/search/music/《内容》
     *  http://v3.wufazhuce.com:8000/api/search/movie/《内容》
     *   http://v3.wufazhuce.com:8000/api/search/autor/《内容》
     *    http://v3.wufazhuce.com:8000/api/search/reading/《内容》
     */

}
