package com.huake.hkis.hkis.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huake.hkis.hkis.R;
import com.huake.hkis.hkis.databinding.FragmentMainBinding;

/**
 * Created by chen on 2017/6/1.
 */

public class UserFragment extends LifecycleFragment {
    public static Fragment newInstance() {
        return new UserFragment();
    }

    private FragmentMainBinding viewDataBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //get the databinding from the layout
        this.viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
