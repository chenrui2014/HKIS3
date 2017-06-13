package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.AboutUs;
import com.huake.hkis.hkis.repository.HKISRepository;

/**
 * Created by chen on 2017/6/13.
 */

public class AboutUsActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private HKISRepository hkisRep;

    private TextView projectInfoTv;

    private TextView wechatServerTv;
    private TextView officialSiteTv;
    private TextView offocoalMailTv;
    private TextView officialTelTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_about);
        AppModule appModule = new AppModule(getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());

        projectInfoTv = (TextView) findViewById(R.id.about_about);
        wechatServerTv = (TextView) findViewById(R.id.about_wx);
        officialSiteTv = (TextView) findViewById(R.id.about_offic);
        offocoalMailTv = (TextView) findViewById(R.id.about_com);
        officialTelTv = (TextView) findViewById(R.id.about_phone);
        initData();
    }

    private void initData(){
        LiveData<AboutUs> aboutUsData = hkisRep.aboutUs();
        aboutUsData.observe(this,aboutUs ->{

            projectInfoTv.setText(aboutUs.getProjectInfo());
            wechatServerTv.setText(aboutUs.getWebchatServer());
            officialSiteTv.setText(aboutUs.getOfficialSite());
            offocoalMailTv.setText(aboutUs.getOffocoalEmail());
            officialTelTv.setText(aboutUs.getOfficialTel());
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
