package com.huake.hkis.hkis.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huake.hkis.hkis.AboutUsActivity;
import com.huake.hkis.hkis.LoginActivity;
import com.huake.hkis.hkis.OnFragmentListener;
import com.huake.hkis.hkis.R;
import com.huake.hkis.hkis.SettingActivity;
import com.huake.hkis.hkis.UpdatePasswordActivity;
import com.huake.hkis.hkis.UpgradeActivity;
import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

/**
 * Created by ysstech on 2017/6/8.
 */

public class UserFragment2 extends Fragment  implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private HKISRepository hkisRep;

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

        AppModule appModule = new AppModule(getActivity().getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());
        updatePwdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fListener.onFragmentAction(null,UpdatePasswordActivity.class);
            }
        });

        aboutUsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fListener.onFragmentAction(null,AboutUsActivity.class);
            }
        });

        updateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fListener.onFragmentAction(null,UpgradeActivity.class);
            }
        });

        exitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences(Constants.SP_STORE_KEY,getActivity().MODE_PRIVATE);
                String userId =sp.getString(Constants.SP_USER_ID_KEY,"");
                LiveData<Boolean> state = hkisRep.exit(userId);
                state.observe(UserFragment2.this, flag ->{
                    if(flag){
                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString(Constants.SP_USER_ID_KEY,"");
                        spe.putBoolean(Constants.SP_ISCHECK_KEY,false);
                        spe.putString(Constants.SP_PWD_KEY,"");
                        spe.putString(Constants.SP_USERNAME_KEY,"");
                        spe.commit();
                        fListener.onFragmentAction(null,LoginActivity.class);
                        Toast.makeText(getActivity().getApplicationContext(),"退出成功",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"退出失败",Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

        return fv;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fListener = (OnFragmentListener)context;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
