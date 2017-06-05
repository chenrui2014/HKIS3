package com.huake.hkis.hkis.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.huake.hkis.hkis.OnScanListener;
import com.huake.hkis.hkis.R;

/**
 * Created by chen on 2017/6/5.
 */

public class ChangeFragment2 extends Fragment {

    private OnScanListener scanListener;

    private EditText etScan;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_change, container, false);
        etScan = (EditText) fv.findViewById(R.id.et_change);
        etScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanListener.onScanListener();
            }
        });
        return fv;
    }

    public void setEtScan(String barCode){
        this.etScan.setText(barCode);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            scanListener = (OnScanListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}
