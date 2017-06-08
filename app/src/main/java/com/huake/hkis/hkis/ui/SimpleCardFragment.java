package com.huake.hkis.hkis.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huake.hkis.hkis.R;

@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private Integer index;

    public static SimpleCardFragment getInstance(int index) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.index = index;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_check_mp, null);
        switch(this.index){
            case 0:
                v = inflater.inflate(R.layout.fragment_check_mp, null);
            case 1:
                v = inflater.inflate(R.layout.fragment_check_ap, null);
            case 2:
                v = inflater.inflate(R.layout.fragment_check_ms, null);
            default:
        }

        return v;
    }
}