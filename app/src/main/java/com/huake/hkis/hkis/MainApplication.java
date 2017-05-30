package com.huake.hkis.hkis;

import android.app.Application;

import com.huake.hkis.hkis.dagger.AppComponent;
import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.dagger.DaggerAppComponent;

public class MainApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
