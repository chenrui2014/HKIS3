package com.huake.hkis.hkis.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.huake.hkis.hkis.ChangeMDActivity;
import com.huake.hkis.hkis.InStoreSummaryActivity;
import com.huake.hkis.hkis.InventorySummaryActivity;
import com.huake.hkis.hkis.OnFragmentListener;
import com.huake.hkis.hkis.OnScanListener;
import com.huake.hkis.hkis.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2017/6/5.
 */

public class ChangeFragment2 extends Fragment {

    private OnScanListener scanListener;

    private EditText etScan;

    private Button changeBt;

    private OnFragmentListener fListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_change, container, false);
        etScan = (EditText) fv.findViewById(R.id.et_change);
        changeBt = (Button) fv.findViewById(R.id.bt_change);
        etScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanListener.onScanListener();
            }
        });
        changeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wareHouseNO = etScan.getText().toString().trim();

                Map<String,String> params = new HashMap<String, String>();

                if(wareHouseNO.isEmpty()){
                    wareHouseNO = "2017052511";
                }
                params.put("wareHouseNO",wareHouseNO);
                fListener.onFragmentAction(params,InventorySummaryActivity.class);
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
            fListener = (OnFragmentListener)  context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}
