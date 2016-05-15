package com.hyc.zhihu.ui;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.base.BasePresenter;
import com.hyc.zhihu.base.PresenterFactory;
import com.hyc.zhihu.base.PresenterLoader;
import com.hyc.zhihu.presenter.MainPresenter;
import com.hyc.zhihu.ui.fragment.PictureFragment;
import com.hyc.zhihu.view.TestView;

public class MainActivity extends BaseActivity implements TestView,LoaderManager.LoaderCallbacks<MainPresenter> {
    private int ID = 1001;
    private MainPresenter mPresenter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(ID, null, this);
        Log.d("act-hyc","onCreate");
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content,new PictureFragment()).commit();
        mRecyclerView= (RecyclerView) findViewById(R.id.picture_rv);
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
        Log.d("act-hyc","onStart");
//        Requests.getApi().getPictureIds("0").map(new Func1<OnePictureList, Observable<OnePicture>>() {
//            @Override
//            public Observable<OnePicture> call(OnePictureList onePictureList) {
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
//         Requests.getApi().getPictureIds("0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<OnePictureList>() {
//             @Override
//             public void call(OnePictureList onePictureList) {
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
    protected void onResume() {
        super.onResume();
        mPresenter.onLoadFinished();
        Log.d("act-hyc","onResume----"+mPresenter.toString());

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.d("act-hyc","onCreateLoader");
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
        Log.d("act-hyc","onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mPresenter = null;
        Log.d("act-hyc","onLoaderReset");
    }

    @Override
    public void showToast() {
        Toast.makeText(this,"我下载完了",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
