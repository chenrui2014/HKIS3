package com.huake.hkis.hkis.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.huake.hkis.hkis.MainApplication;
import com.huake.hkis.hkis.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by florentchampigny on 18/05/2017.
 */

@Component(modules = AppModule.class)
@Singleton
public abstract class AppComponent {

    public static AppComponent from(@NonNull Context context){
        return ((MainApplication) context.getApplicationContext()).getAppComponent();
    }

    public abstract void inject(MainFragment mainFragment);
}
