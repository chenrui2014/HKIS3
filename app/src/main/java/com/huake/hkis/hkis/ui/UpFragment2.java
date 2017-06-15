package com.huake.hkis.hkis.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huake.hkis.hkis.InStoreSummaryActivity;
import com.huake.hkis.hkis.OnFragmentListener;
import com.huake.hkis.hkis.R;
import com.huake.hkis.hkis.WareHousingSummaryActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2017/6/3.
 */

public class UpFragment2 extends Fragment {

    private PopupWindow mPopupWindow;
    private TextView tvTitle;
    private String documentsType = "入库单";

    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;

    private Button upBtn;
    private EditText taskNOEt;

    private OnFragmentListener fListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View popupView = getLayoutInflater(savedInstanceState).inflate(R.layout.up_pop_window, null);

        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        mPopupWindow.setOutsideTouchable(true);
        //设置可以获取焦点，否则弹出菜单中的EditText是无法获取输入的
        mPopupWindow.setFocusable(true);
        //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        bt1 = (Button) popupView.findViewById(R.id.button5);
        bt2 = (Button) popupView.findViewById(R.id.button);
        bt3 = (Button) popupView.findViewById(R.id.button2);
        bt4 = (Button) popupView.findViewById(R.id.button3);

        TextView confirm = (TextView) popupView.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupWindow != null && mPopupWindow.isShowing()){
                    mPopupWindow.dismiss();
                }

            }
        });

        TextView reset = (TextView) popupView.findViewById(R.id.tv_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentsType = bt1.getText().toString();
                tvTitle.setText(bt1.getText());
                bt1.setTextColor(Color.WHITE);
                bt2.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt3.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt4.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt1.setBackground(getResources().getDrawable(R.drawable.shape_corner_press2));
                bt2.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt3.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt4.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));

            }
        });

        documentsType = bt1.getText().toString();
        bt1.setTextColor(Color.WHITE);
        bt1.setBackground(getResources().getDrawable(R.drawable.shape_corner_press2));
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentsType = bt1.getText().toString();
                tvTitle.setText(bt1.getText());
                bt1.setTextColor(Color.WHITE);
                bt2.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt3.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt4.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt1.setBackground(getResources().getDrawable(R.drawable.shape_corner_press2));
                bt2.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt3.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt4.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentsType = bt2.getText().toString();
                tvTitle.setText(bt2.getText());
                bt2.setTextColor(Color.WHITE);
                bt1.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt3.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt4.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt2.setBackground(getResources().getDrawable(R.drawable.shape_corner_press2));
                bt1.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt3.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt4.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentsType = bt3.getText().toString();
                tvTitle.setText(bt3.getText());
                bt3.setTextColor(Color.WHITE);
                bt2.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt1.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt4.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt3.setBackground(getResources().getDrawable(R.drawable.shape_corner_press2));
                bt2.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt1.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt4.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentsType = bt4.getText().toString();
                tvTitle.setText(bt4.getText());
                bt4.setTextColor(Color.WHITE);
                bt2.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt3.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt1.setTextColor(getResources().getColor(R.color.up_condition_font));
                bt4.setBackground(getResources().getDrawable(R.drawable.shape_corner_press2));
                bt2.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt3.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
                bt1.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal2));
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fv = inflater.inflate(R.layout.fragment_up, container, false);

        tvTitle = (TextView) fv.findViewById(R.id.tv_title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAsDropDown(v);
            }
        });
        taskNOEt = (EditText) fv.findViewById(R.id.up_et);
        upBtn = (Button)fv.findViewById(R.id.up_btn);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskNO = taskNOEt.getText().toString().trim();

                Map<String,String> params = new HashMap<String, String>();

                if(taskNO.isEmpty()){
                   taskNO = "2017052511";
                }
                params.put("taskNO",taskNO);
                params.put("documentsType",documentsType);
                fListener.onFragmentAction(params,InStoreSummaryActivity.class);

            }
        });
        taskNOEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(taskNOEt.getText().toString().isEmpty()){
                    upBtn.setBackground(getResources().getDrawable(R.drawable.shape_corner_normal1));
                    upBtn.setTextColor(getResources().getColor(R.color.black_bt_tx));
                }else{
                    upBtn.setBackground(getResources().getDrawable(R.drawable.shape_corner_press2));
                    upBtn.setTextColor(Color.WHITE);
                }

            }
        });
        return fv;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fListener = (OnFragmentListener)context;
    }
}
