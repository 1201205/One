package com.hyc.zhihu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.SeekBar;

import com.hyc.zhihu.ui.fragment.LoadingDialogFragment;
import com.hyc.zhihu.widget.MyLoadingView;

/**
 * Created by Administrator on 2016/6/22.
 */
public class TestActivity extends AppCompatActivity {
    private MyLoadingView m;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
//        m= (MyLoadingView) findViewById(R.id.score);
//        SeekBar seekBar= (SeekBar) findViewById(R.id.s);
//        findViewById(R.id.d).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                m.startAnim();
//                LoadingDialogFragment.getInstance().show(getSupportFragmentManager(),"11111");
//            }
//        });
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.e("test1",progress+"");
//                m.setFrag(progress/100f);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }
}
