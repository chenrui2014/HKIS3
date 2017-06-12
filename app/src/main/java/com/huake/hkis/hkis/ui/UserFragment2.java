package com.huake.hkis.hkis.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huake.hkis.hkis.OnFragmentListener;
import com.huake.hkis.hkis.R;
import com.huake.hkis.hkis.UpdatePasswordActivity;

/**
 * Created by ysstech on 2017/6/8.
 */

public class UserFragment2 extends Fragment {

    private OnFragmentListener fListener;

    private TextView updatePwdTv;
    private TextView aboutUsTv;
    private TextView updateTv;
    private TextView exitTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_user, container, false);
        updatePwdTv = (TextView) fv.findViewById(R.id.user_pwd);
        aboutUsTv = (TextView)fv.findViewById(R.id.user_about);
        updateTv = (TextView) fv.findViewById(R.id.user_update);
        exitTv = (TextView) fv.findViewById(R.id.user_exit);
        updatePwdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fListener.onFragmentAction(null,UpdatePasswordActivity.class);
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
