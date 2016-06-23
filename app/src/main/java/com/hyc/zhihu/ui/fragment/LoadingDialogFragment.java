package com.hyc.zhihu.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyc.zhihu.R;
import com.hyc.zhihu.widget.MyLoadingView;

/**
 * Created by hyc on 2016/6/22.
 */
public class LoadingDialogFragment extends DialogFragment {
    private MyLoadingView mLoadingView;
    public static LoadingDialogFragment sFragment;

    public static LoadingDialogFragment getInstance() {
        if (sFragment == null) {
            sFragment = new LoadingDialogFragment();
        }
        return sFragment;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        Log.e("test1", "show--被调用了");
        super.show(manager, tag);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingView.startAnim();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.loading, null);
        mLoadingView = (MyLoadingView) v.findViewById(R.id.loading_lv);
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
