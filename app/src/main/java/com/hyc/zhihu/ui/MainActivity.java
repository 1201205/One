package com.hyc.zhihu.ui;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.presenter.MainPresenter;
import com.hyc.zhihu.ui.fragment.LoadingDialogFragment;
import com.hyc.zhihu.ui.fragment.PictureFragment;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.TestView;

public class MainActivity extends BaseActivity<MainPresenter>
    implements TestView, LoaderManager.LoaderCallbacks<MainPresenter> {
    private int ID = 1001;
    private RecyclerView mRecyclerView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("act-hyc", "onCreate");
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_content, new PictureFragment())
            .commit();
        mRecyclerView = (RecyclerView) findViewById(R.id.picture_rv);
        findViewById(R.id.music_tv).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MusicActivity.class));
            }
        });
        findViewById(R.id.movie_tv).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MovieListActivity.class));
            }
        });
        findViewById(R.id.reading_tv).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReadingActivity.class));
            }
        });
    }


    @Override
    protected void handleIntent() {

    }


    @Override
    protected void initView() {

    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("act-hyc", "onStart");
        //        Requests.getApi().getPictureIds("0").map(new Func1<IDList, Observable<OnePicture>>() {
        //            @Override
        //            public Observable<OnePicture> call(IDList onePictureList) {
        //                return Requests.getApi().getPictureById(onePictureList.getData().get(0));
        //            }
        //        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
        //                subscribe(new Action1<Observable<OnePicture>>() {
        //            @Override
        //            public void call(Observable<OnePicture> onePictureObservable) {
        //                onePictureObservable.subscribeOn(Schedulers.io()).subscribe(new Action1<OnePicture>() {
        //                    @Override
        //                    public void call(OnePicture onePicture) {
        //                        Log.e("tes1",onePicture.getData().getHp_content());
        //                    }
        //                });
        //            }
        //        });
        //         Requests.getApi().getPictureIds("0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<IDList>() {
        //             @Override
        //             public void call(IDList onePictureList) {
        //                 Log.e("test1", onePictureList.getData().get(0));
        //             }
        //         }, new Action1<Throwable>() {
        //             @Override
        //             public void call(Throwable throwable) {
        //                 Log.e("test1",throwable.getMessage());
        //             }
        //         });
    }

    @Override
    protected void initLoader() {
        getSupportLoaderManager().initLoader(AppUtil.getID(), null, this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onLoadFinished();
        Log.d("act-hyc", "onResume----" + mPresenter.toString());

    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.d("act-hyc", "onCreateLoader");
        return new PresenterLoader(this, new PresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MainPresenter(MainActivity.this);
            }
        });
    }


    @Override
    public void onLoadFinished(Loader loader, MainPresenter data) {
        mPresenter = data;
        Log.d("act-hyc", "onLoadFinished");
    }


    @Override
    public void onLoaderReset(Loader loader) {
        mPresenter = null;
        Log.d("act-hyc", "onLoaderReset");
    }


    @Override
    public void showToast() {
        Toast.makeText(this, "我下载完了", Toast.LENGTH_LONG).show();
    }

}
