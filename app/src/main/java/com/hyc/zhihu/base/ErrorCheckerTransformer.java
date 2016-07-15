package com.hyc.zhihu.base;

import com.hyc.zhihu.beans.BaseBean;
import com.hyc.zhihu.utils.S;

import java.util.List;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created by hyc on 2016/7/7.
 */
public class ErrorCheckerTransformer<T extends BaseBean<R>, R>
        implements Observable.Transformer<T, R> {


    @Override
    public Observable<R> call(Observable<T> observable) {
        return observable.map(new Func1<T, R>() {
            @Override
            public R call(T t) {
                if (null == t || null == t.getData()||(t.getData() instanceof List && ((List) t.getData()).size()==0)) {
                        throw new NoThingGetException();
                }
                return t.getData();
            }
        });
    }
}
