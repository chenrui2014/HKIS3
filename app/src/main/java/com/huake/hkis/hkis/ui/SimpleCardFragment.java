package com.huake.hkis.hkis.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.huake.hkis.hkis.ChangeMDActivity;
import com.huake.hkis.hkis.OnFragmentListener;
import com.huake.hkis.hkis.R;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private Integer index;

    private String checkType="1";

    private EditText checkNOTv;
    private EditText wareHouseNOTv;
    private Button checkBt;

    private OnFragmentListener fListener;

    public static SimpleCardFragment getInstance(int index) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.index = index;
        sf.checkType = ++index + "";
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = R.layout.fragment_check_mp;

        switch(this.index){
            case 0:
                layoutId = R.layout.fragment_check_mp;
            case 1:
                layoutId = R.layout.fragment_check_ap;
            case 2:
                layoutId = R.layout.fragment_check_ms;
            default:layoutId = R.layout.fragment_check_mp;
        }

        View view = inflater.inflate(layoutId, null);

        wareHouseNOTv = (EditText) view.findViewById(R.id.et_ckh);
        checkNOTv = (EditText) view.findViewById(R.id.et_pddh);
        checkBt = (Button) view.findViewById(R.id.bt_confirm);
        checkNOTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wareHouseNO = wareHouseNOTv.getText().toString().trim();
                String checkNO = checkNOTv.getText().toString().trim();

                Map<String,String> params = new HashMap<String, String>();

                if(wareHouseNO.isEmpty()){
                    wareHouseNO = "2017052511";
                }
                params.put("wareHouseNO",wareHouseNO);
                params.put("checkNO",checkNO);
                params.put("checkType",checkType);
                fListener.onFragmentAction(params, ChangeMDActivity.class);
            }
        });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            fListener = (OnFragmentListener)  context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}